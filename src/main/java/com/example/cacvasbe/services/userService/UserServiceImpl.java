package com.example.cacvasbe.services.userService;

import com.example.cacvasbe.dto.*;
import com.example.cacvasbe.dto.email.EmailDetailsDto;
import com.example.cacvasbe.dto.registration.UserDto;
import com.example.cacvasbe.email.ConfirmationToken;
import com.example.cacvasbe.entities.*;
import com.example.cacvasbe.enums.*;
import com.example.cacvasbe.error_handler.CustomNotFoundException;
import com.example.cacvasbe.error_handler.EntityAlreadyExistException;
import com.example.cacvasbe.pojo.*;
import com.example.cacvasbe.repository.*;
import com.example.cacvasbe.security.JwtService;
import com.example.cacvasbe.security.PasswordService;
import com.example.cacvasbe.security.UserDetails;
import com.example.cacvasbe.services.emailService.ConfirmationTokenService;
import com.example.cacvasbe.services.sequence.portal_user_id.PortalUserSequenceService;
import com.example.cacvasbe.utils.Constants;
import com.example.cacvasbe.utils.cloudinary.CloudinaryService;
import com.example.cacvasbe.utils.email.EmailService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyPairGenerator;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    private final CloudinaryService cloudinaryService;
    private final PortalUserSequenceService portalUserSequenceService;
    private final EmailService emailService;
    private final WalletRepository walletRepository;
    //    private final ConfirmationTokenService confirmationTokenService;
    private final RoleRepository roleRepository;
    private final PortalUserRepository portalUserRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final ConfirmationTokenService confirmationTokenService;
    private final HttpServletResponse httpServletResponse;
    private final BlacklistService blacklistService;
    private final HttpServletRequest httpServletRequest;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final WalletTransactionsRepository walletTransactionsRepository;
    private final LicensePartnerRepository licensePartnerRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.base-url}")
    private String passwordUrl;

    @Override
    public PortalUsers createUser(UserDto user, PortalUsers createdBy, Role role) throws Exception {

        System.out.println("Cloud " +cloudinaryService.uploadImage(decode(user.getPicture())));

        PortalUsers portalUsers = new PortalUsers();
        portalUsers.setCreatedAt(LocalDateTime.now());
        portalUsers.setUsername(user.getEmail());
        portalUsers.setRcNumber(user.getRcNumber());
        portalUsers.setNationalIdentificationNumber(cloudinaryService.uploadImage(decode(user.getNin())));
        portalUsers.setStatus(GenericStatusConstant.ACTIVE);
        portalUsers.setGeneratedPassword(passwordService.hashPassword(user.getPassword()));
        portalUsers.setDescription(user.getNatureOfBusiness());
        portalUsers.setIndustry_type(user.getIndustry_type());
        portalUsers.setCompanyProfile(cloudinaryService.uploadImage(decode(user.getCompanyProfile())));
        portalUsers.setPicture(cloudinaryService.uploadImage(decode(user.getPicture())));
        portalUsers.setCacCertificate(cloudinaryService.uploadImage(decode(user.getCacCertificate())));
        portalUsers.setRole(role);
        portalUsers.setPaymentType(Payment.DIRECT_PAYMENT);
        portalUsers.setCreatedBy(portalUsers);
        portalUsers.setCreatedBy(portalUsers);
        portalUsers.setRegistrationPaymentStatus(RegistrationPaymentStatus.PENDING);
        portalUsers.setIndustry_type(user.getIndustry_type());


        String token = UUID.randomUUID().toString();
        EmailDetailsDto emailDetailsRequest = EmailDetailsDto.builder()
                .msgBody(emailService.buildVerificationEmail(user.getEmail(), "http://localhost:9001/api/verification?token=" + token))
                .subject("CAC Email")
                .recipient(user.getEmail())
                .build();
        emailService.sendMailWithAttachment(emailDetailsRequest);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now().plusMinutes(15),
                portalUsers
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("0.00"));
        wallet.setUsername(portalUsers);
        walletRepository.save(wallet);

        if (this.cacService(user.getRcNumber())){
            System.out.println("here.....222");
            portalUserRepository.save(portalUsers);
            return portalUsers;

       }else{
            System.out.println("here......");
            throw new EntityAlreadyExistException("Invalid RC NUMBER");
       }

    }

    @Override
    public LoginResponse authenticateUser(LoginRequestDto dto) throws Exception {

        Optional<PortalUsers> user = portalUserRepository.findByUsernameAndRegistrationPaymentStatus(dto.getUsername(), RegistrationPaymentStatus.APPROVED);
        if (user.isEmpty()) {
            return new LoginResponse("Invalid User Details");
        }

        if (passwordService.comparePassword(user.get().getGeneratedPassword(), dto.getPassword())){
            String token = jwtService.generateJwtToken(user.get().getId());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setId(user.get().getId());
            loginResponse.setEmail(user.get().getUsername());
            loginResponse.setRcNumber(user.get().getRcNumber());
            loginResponse.setToken(token);
            loginResponse.setRole(user.get().getRole().getName());
            loginResponse.setPermissions(getPermission(user.get().getRole()));

            return loginResponse;
        }  return new LoginResponse("Invalid Password");
    }

    @Override
    public void resetPassword(PasswordDto dto) throws Exception {
        String username = UserDetails.getLoggedInUserDetails();
        System.out.println("user" + username);

        PortalUsers user = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE)
                .orElseThrow(() -> new Exception("Invalid Username - " + username));

        if (passwordService.comparePassword(user.getGeneratedPassword(), dto.getOldPassword())){
            user.setGeneratedPassword(passwordService.hashPassword(dto.getNewPassword()));
            user.setLastUpdatedAt(LocalDateTime.now());
            portalUserRepository.save(user);
        } else throw new CustomNotFoundException("Invalid old password");
    }

    @Override
    public ResponseEntity<MessageResponse> forgotPassword(ForgotPasswordRequest request) throws MessagingException {
        String email = request.getEmail();
        PortalUsers user = portalUserRepository.findUserByUsername(email)
                .orElseThrow(() -> new CustomNotFoundException(
                        "User with email " + email + " doesn't exist!"));

        String token1 = jwtService.generateJwtToken(user.getId());

        String forgotPasswordURL = passwordUrl + "/api/reset-password/" + token1;
        String content = "<p>Click the below link to Reset Your Password</p>\n" +
                "<a href=\""+forgotPasswordURL+"\" target=\"_blank\">Reset</a>";

//
//        EmailSenderDto emailSenderDto = new EmailSenderDto(
//                request.getEmail(),
//                "Reset password link: ",
//                content
//        );
//        emailSenderService.send(emailSenderDto);

        log.info(forgotPasswordURL);
        return ResponseEntity.ok(new MessageResponse("Kindly check email for reset Link!"));
    }



    @Override
    public String getUniqueId() {
        return String.format("USR_%04d%02d%02d%05d",
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth(),
                portalUserSequenceService.getNextId()
        );
    }

    @Override
    public ApprovalResponse requestReactivation(Long id) {
        PortalUsers user = portalUserRepository.findByIdAndStatus(id, GenericStatusConstant.INACTIVE).get();
        PortalUsers portalUsers = new PortalUsers();
        portalUsers.setRequestForReactivation(true);
        portalUserRepository.save(portalUsers);
        return new ApprovalResponse("Request has been sent, and approval is pending");
    }

    @Override
    public PortalUserPojo get(PortalUsers user) {
        PortalUserPojo pojo = new PortalUserPojo();
        pojo.setRcNumber(user.getRcNumber());
        pojo.setEmail(user.getUsername());
        pojo.setDateCreated(user.getCreatedAt().toLocalDate());
        pojo.setRole(user.getRole());
        pojo.setStatus(user.getStatus());
        pojo.setId(user.getId());
        pojo.setPayment(user.getPaymentType());

        return pojo;
    }


    @Override
    public List<PortalUserPojo> get(List<PortalUsers> users) {
        return users.stream().map(user -> {

            PortalUserPojo pojo = new PortalUserPojo();
            pojo.setRcNumber(user.getRcNumber());
            pojo.setEmail(user.getUsername());
            pojo.setDateCreated(user.getCreatedAt().toLocalDate());
            pojo.setRole(user.getRole());
            pojo.setStatus(user.getStatus());
            pojo.setId(user.getId());
            pojo.setPayment(user.getPaymentType());

            return pojo;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseClass<?> getUserByRCNumber(String rcNumber) {
        PortalUsers user = portalUserRepository.findByRcNumberAndStatus(rcNumber, GenericStatusConstant.ACTIVE).get();
        System.out.println("user.................."+ user.getRcNumber());
       return cacUser(user.getRcNumber());
    }

    private List<PermissionTypeConstant> getPermission(Role role){
        List<Permission> permissions = permissionRepository.findAllByRole(role);

        List<PermissionTypeConstant> permissionTypeConstantList = new ArrayList<>();

        permissions.forEach(permission -> {
            permissionTypeConstantList.add(permission.getPermissionTypeConstant());
        });

        return permissionTypeConstantList;
    }

    @Override
    @Transactional
    public void deactivateUser(DeactivateUserDto deactivateUserDto) {
        String username = UserDetails.getLoggedInUserDetails();
        PortalUsers portalUsers = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE).get();
        LocalDateTime now = LocalDateTime.now();

        portalUsers.setStatus(GenericStatusConstant.INACTIVE);
        portalUsers.setLastUpdatedAt(now);
        portalUsers.setDeactivatedBy(portalUsers);
        portalUsers.setReasonForDeactivation(deactivateUserDto.getReasonForDeactivation());
        portalUserRepository.save(portalUsers);
    }

    @Override
    public boolean cacService(String rcNumber){
        System.out.println("Entering....");
        try {

            String baseUrl = "https://searchapp.cac.gov.ng/searchapp";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json");
            headers.add("Content-Type", "application/json");
            CACSearchLoginRequestPojo cacSearchLoginRequestPojo = new CACSearchLoginRequestPojo();

            cacSearchLoginRequestPojo.setUsername("firscacapi");
            cacSearchLoginRequestPojo.setPassword("firscacapi2021");
            String result = restTemplate.postForObject(baseUrl+"/api/auth/login", cacSearchLoginRequestPojo, String.class);



            Gson gson = new Gson();
            CACSearchLoginResponsePojo responseToken = gson.fromJson(result, CACSearchLoginResponsePojo.class);

            System.out.println("here is the response: " + responseToken.getToken());


            MultiValueMap<String, String> headersAuth = new LinkedMultiValueMap<String, String>();
            Map map = new HashMap<String, String>();
            map.put("Content-Type", "application/json");
            map.put("Authorization", "Bearer "+responseToken.getToken());
            headersAuth.setAll(map);

            System.out.println(responseToken.getToken());
            String url = baseUrl+"/api/report/find/company/info/"+rcNumber+"/1";

            ResponseEntity<JsonResponse> responseRC = null;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(headersAuth);
            try {

                responseRC = restTemplate.exchange(builder.toUriString(),
                        HttpMethod.GET,
                        entity, JsonResponse.class);
                System.out.println("Here is the response:::" + responseRC.getBody());
                if (responseRC.getBody().getMessage().equals("Result Found!")) {
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        catch (Exception e) {
        }
        return false;
    }


    //convert base64 to multipart file
    public File decode(String base64) throws IOException {

        BufferedImage image = null;
        byte[] imageByte;

        Base64 base641 = new Base64();
        imageByte = base641.decode(base64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "jpg", baos );
        baos.flush();

        File outputfile = new File("image.jpg");
        ImageIO.write(image, "jpg", outputfile);

//        MultipartFile multipartFile = new MultipartImage(baos.toByteArray());
        return  outputfile;
    }


    private ResponseClass cacUser(String rcNumber){
        System.out.println("Entering....");
        try {

            String baseUrl = "https://searchapp.cac.gov.ng/searchapp";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json");
            headers.add("Content-Type", "application/json");
            CACSearchLoginRequestPojo cacSearchLoginRequestPojo = new CACSearchLoginRequestPojo();

            cacSearchLoginRequestPojo.setUsername("firscacapi");
            cacSearchLoginRequestPojo.setPassword("firscacapi2021");
            String result = restTemplate.postForObject(baseUrl+"/api/auth/login", cacSearchLoginRequestPojo, String.class);

            Gson gson = new Gson();
            CACSearchLoginResponsePojo responseToken = gson.fromJson(result, CACSearchLoginResponsePojo.class);

            System.out.println("here is the response: " + responseToken.getToken());
            MultiValueMap<String, String> headersAuth = new LinkedMultiValueMap<String, String>();
            Map map = new HashMap<String, String>();
            map.put("Content-Type", "application/json");
            map.put("Authorization", "Bearer "+responseToken.getToken());
            headersAuth.setAll(map);

            System.out.println(responseToken.getToken());
            String url = baseUrl+"/api/report/find/company/info/"+rcNumber+"/1";

            ResponseEntity<ResponseClass> responseRC = null;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(headersAuth);
            try {

                responseRC = restTemplate.exchange(builder.toUriString(),
                        HttpMethod.GET,
                        entity, ResponseClass.class);
                System.out.println("Here is the response:::" + responseRC.getBody());

               ResponseClass<?> responseClass = new ResponseClass();
               responseClass.setStatus(responseRC.getBody().getStatus());
               responseClass.setMessage(responseRC.getBody().getMessage());
               responseClass.setData(responseRC.getBody().getData());
               return responseClass;


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        catch (Exception e) {
        }
        throw new CustomNotFoundException("CAC data not found");
    }

    @Override
    public GenericStatusConstant findStatus(String rcNumber) {
        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> user = portalUserRepository.findUserByUsername(username);
        Optional<PortalUsers> status = portalUserRepository.findByRcNumber(user.get().getRcNumber());
        System.out.println("RC Number "+user.get().getRcNumber());
        return status.get().getStatus();
    }

    @Override
    public APIResponse<?> logout() {
        String token = httpServletRequest.getHeader("Authorization");
        blacklistService.blacklistToken(token);
//        String username = jwtService.detokenizeJwtToken(token);
//        System.out.println("Logged out user: "+username);
        return new APIResponse<>("Logout Successfully", true, null);
    }

    @Override
    public APIResponse<?> buyLisense() throws Exception {
        DecimalFormat formatter = new DecimalFormat("#,###.00");

        String username = UserDetails.getLoggedInUserDetails();
        PortalUsers user = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE)
                .orElseThrow(() -> new CustomNotFoundException("Invalid user"));
        System.out.println("User.........."+ user.getUsername());

        System.out.println("User balance ........................."+ this.getUserWalletBalance(user));


        if (this.getUserWalletBalance(user).compareTo(Constants.LICENSE_FEE) >= 0) {
            if(Constants.LICENSE_FEE.compareTo(new BigDecimal("0.00")) <= 0){
                log.info("Invalid amount");
                throw new IllegalArgumentException("invalid amount");
            }
            Wallet userWallet = walletRepository.findWalletsByUsername(user).get();
            userWallet.setBalance(userWallet.getBalance().subtract(Constants.LICENSE_FEE));
            portalUserRepository.save(user);

            BigDecimal walletUsedMoneyBal = userWallet.getTotalMoneyUsed();
            BigDecimal totalUsedMoney = walletUsedMoneyBal.add(Constants.LICENSE_FEE);
            userWallet.setTotalMoneyUsed(totalUsedMoney);

            LicensedPartners licensedPartners = new LicensedPartners();
            licensedPartners.setUser(user);
            licensedPartners.setRcNumber(user.getRcNumber());
            licensedPartners.setPaymentStatus(LicensePaymentStatus.PENDING);
            licensedPartners.setCreatedAt(LocalDateTime.now());
            licensedPartners.setStatus(GenericStatusConstant.ACTIVE);
            licensedPartners.setCreatedBy(user);
            licensedPartners.setUsername(user.getUsername());
            licensedPartners.setPublicKey(this.createPublicApiKey());
            licensedPartners.setPrivateKey(passwordEncoder.encode(this.createPrivateApiKey()));
            licensedPartners.setAccountStatus(UserAccountStatus.NOT_VERIFIED);
            licensedPartners.setLastUpdatedAt(LocalDateTime.now());
            licensePartnerRepository.save(licensedPartners);

            WalletTransaction transaction = new WalletTransaction();
            transaction.setUsername(user);
            transaction.setWallet(userWallet);
            transaction.setEmail(user.getUsername());
            transaction.setDescription("Purchased annual VAS license");
            transaction.setCreatedTime(LocalDateTime.now());
            transaction.setTransactionType(TransactionType.LICENSE_PURCHASE);
            transaction.setAmount(Constants.LICENSE_FEE);
            transaction.setPaymentChannel(PaymentChannel.WALLET);
//            transaction.setTotalTransValue(totalTrans);
            walletTransactionsRepository.save(transaction);


        }else {
            return new APIResponse<>("Insufficient wallet balance", false);
        }

        EmailDetailsDto emailDetailsRequest = EmailDetailsDto.builder()
               .msgBody(emailService.sendFundsEmail(user.getUsername(), Constants.LICENSE_FEE.toString()))
                .subject("CAC Email")
                .recipient(user.getUsername())
               .build();
        emailService.sendMailWithAttachment(emailDetailsRequest);

        return new APIResponse<>("success", true, Constants.LICENSE_FEE);
    }

    @Override
    public BigDecimal getUserWalletBalance(PortalUsers user) {
        Optional<Wallet> wallet = walletRepository.findWalletsByUsername(user);
        if (wallet.isEmpty()) {
            throw new EntityAlreadyExistException("User wallet does not exist");
        }
        return wallet.get().getBalance();
    }

    @Override
    public String createPublicApiKey() {
        String pub = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);
            byte[] publicKey = keyGen.genKeyPair().getPublic().getEncoded();
            pub = Arrays.toString(publicKey);
            pub = passwordEncoder.encode(pub);
            pub = pub.replaceAll("[/]", "v");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pub;
    }

    @Override
    public String createPrivateApiKey() {
        String priv = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);
            byte[] privateKey = keyGen.generateKeyPair().getPrivate().getEncoded();
            priv =  Arrays.toString(privateKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return priv;
    }

    @Override
    public CreateApiResponse getApiKey() {
        String username = UserDetails.getLoggedInUserDetails();
        LicensedPartners licensedPartner = licensePartnerRepository.findLicensedPartnersByUsernameAndAccountStatus(username, UserAccountStatus.VERIFIED)
                .orElseThrow(() -> new CustomNotFoundException("User not found"));

        return CreateApiResponse.builder()
                .publicKey(licensedPartner.getPublicKey())
                .privateKey(licensedPartner.getPrivateKey())
                .build();
    }



}
