package com.example.cacvasbe.services.products.requests;

import com.example.cacvasbe.dto.product.AffiliateRequest;
import com.example.cacvasbe.dto.product.HistoryOfDeniedNameRequest;
import com.example.cacvasbe.dto.product.SearchForAffiliationsRequest;
import com.example.cacvasbe.entities.*;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.enums.PaymentChannel;
import com.example.cacvasbe.enums.TransactionType;
import com.example.cacvasbe.enums.UserAccountStatus;
import com.example.cacvasbe.error_handler.CustomNotFoundException;
import com.example.cacvasbe.pojo.*;
import com.example.cacvasbe.repository.*;
import com.example.cacvasbe.security.UserDetails;
import com.example.cacvasbe.services.adminService.AdmintService;
import com.example.cacvasbe.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService{

    private final PortalUserRepository portalUserRepository;
    private final AdmintService admintService;
    private final CompanyRepository companyRepository;
    private final AffiliatesRepository affiliatesRepository;
    private final ProcessTypeRepository processTypeRepository;
    private final AffiliateTypeRepository affiliateTypeRepository;
    private final LicensePartnerRepository licensePartnerRepository;
    private final PurchasedProductRepository purchasedProductRepository;
    private final ProcessTaskHistoryRepository processTaskHistoryRepository;
    private final CompanyTypeRepository companyTypeRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final NameAvailabilityRepository nameAvailabilityRepository;


    @Override
    public Company findCompany(String rcNumber) {
        String username = UserDetails.getLoggedInUserDetails();
        PortalUsers admin = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE).get();
        Optional<Company> company = companyRepository.findCompanyByRcNumber(rcNumber);
        if (company.isEmpty()) {
            throw new CustomNotFoundException("Company not found");
        }

        return company.get();
    }

    @Override
    public List<Affiliates> findAffiliate(AffiliateRequest affiliateRequest) {
//        String username = UserDetails.getLoggedInUserDetails();
//        LicensedPartners user = licensePartnerRepository.findLicensedPartnersByUsernameAndAccountStatus(username, UserAccountStatus.VERIFIED)
//                .orElseThrow(() -> new CustomNotFoundException("Invalid User"));
//
//        if (user.getPrivateKey().equals(productRequest.getPrivateKey())) {
//            PurchasedProducts purchasedProduct = purchasedProductRepository.findPurchasedProductsById(productRequest.getProductId())
//                    .orElseThrow(() -> new CustomNotFoundException("Invalid Product"));
//        }

        PurchasedProducts purchasedProduct = purchasedProductRepository.findPurchasedProductsById(affiliateRequest.getProductId())
                .orElseThrow(() -> new CustomNotFoundException("Invalid Product"));

        Optional<AffiliateType> optionalAffiliateType = Optional.empty();
        List<CompanyType> companyTypeList = new ArrayList<>();
        if (purchasedProduct.getId() == 5L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.DIRECTOR);
            CompanyType companyType1 = companyTypeRepository.findCompanyTypeByName("PRIVATE_COMPANY_LIMITED_BY_GUARANTEE")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_COMPANY_LIMITED_BY_GUARANTEE not found"));
            companyTypeList.add(companyType1);
            CompanyType companyType2 = companyTypeRepository.findCompanyTypeByName("PUBLIC_COMPANY_LIMITED_BY_GUARANTEE")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_COMPANY_LIMITED_BY_GUARANTEE not found"));
            companyTypeList.add(companyType2);
            CompanyType companyType3 = companyTypeRepository.findCompanyTypeByName("PRIVATE_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType3);
            CompanyType companyType4 = companyTypeRepository.findCompanyTypeByName("PUBLIC_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType4);
            CompanyType companyType5 = companyTypeRepository.findCompanyTypeByName("PRIVATE_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType5);
            CompanyType companyType6 = companyTypeRepository.findCompanyTypeByName("PUBLIC_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType6);

        }
        else if (purchasedProduct.getId() == 6L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.PROPRIETOR);
            CompanyType companyType = companyTypeRepository.findCompanyTypeByName("BUSINESS_NAME")
                    .orElseThrow(() -> new CustomNotFoundException("BUSINESS_NAME not found"));
            companyTypeList.add(companyType);
        }
        else if (purchasedProduct.getId() == 7L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.TRUSTEE);
            CompanyType companyType = companyTypeRepository.findCompanyTypeByName("INCORPORATED_TRUSTEE")
                    .orElseThrow(() -> new CustomNotFoundException("INCORPORATED_TRUSTEE not found"));
            companyTypeList.add(companyType);
        }
        else if (purchasedProduct.getId() == 8L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.SECRETARY);
            CompanyType companyType = companyTypeRepository.findCompanyTypeByName("INCORPORATED_TRUSTEE")
                    .orElseThrow(() -> new CustomNotFoundException("INCORPORATED_TRUSTEE not found"));
            companyTypeList.add(companyType);
            CompanyType companyType1 = companyTypeRepository.findCompanyTypeByName("PRIVATE_COMPANY_LIMITED_BY_GUARANTEE")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_COMPANY_LIMITED_BY_GUARANTEE not found"));
            companyTypeList.add(companyType1);
            CompanyType companyType2 = companyTypeRepository.findCompanyTypeByName("PUBLIC_COMPANY_LIMITED_BY_GUARANTEE")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_COMPANY_LIMITED_BY_GUARANTEE not found"));
            companyTypeList.add(companyType2);
            CompanyType companyType3 = companyTypeRepository.findCompanyTypeByName("PRIVATE_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType3);
            CompanyType companyType4 = companyTypeRepository.findCompanyTypeByName("PUBLIC_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType4);
            CompanyType companyType5 = companyTypeRepository.findCompanyTypeByName("PRIVATE_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType5);
            CompanyType companyType6 = companyTypeRepository.findCompanyTypeByName("PUBLIC_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType6);

        }
        else if (purchasedProduct.getId() == 9L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.SHAREHOLDER);
            CompanyType companyType3 = companyTypeRepository.findCompanyTypeByName("PRIVATE_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType3);
            CompanyType companyType4 = companyTypeRepository.findCompanyTypeByName("PUBLIC_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType4);
            CompanyType companyType5 = companyTypeRepository.findCompanyTypeByName("PRIVATE_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType5);
            CompanyType companyType6 = companyTypeRepository.findCompanyTypeByName("PUBLIC_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType6);
        }
        else if (purchasedProduct.getId() == 10L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.WITNESS);
            CompanyType companyType1 = companyTypeRepository.findCompanyTypeByName("PRIVATE_COMPANY_LIMITED_BY_GUARANTEE")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_COMPANY_LIMITED_BY_GUARANTEE not found"));
            companyTypeList.add(companyType1);
            CompanyType companyType2 = companyTypeRepository.findCompanyTypeByName("PUBLIC_COMPANY_LIMITED_BY_GUARANTEE")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_COMPANY_LIMITED_BY_GUARANTEE not found"));
            companyTypeList.add(companyType2);
            CompanyType companyType3 = companyTypeRepository.findCompanyTypeByName("PRIVATE_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType3);
            CompanyType companyType4 = companyTypeRepository.findCompanyTypeByName("PUBLIC_UNLIMITED_COMPANY")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_UNLIMITED_COMPANY not found"));
            companyTypeList.add(companyType4);
            CompanyType companyType5 = companyTypeRepository.findCompanyTypeByName("PRIVATE_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PRIVATE_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType5);
            CompanyType companyType6 = companyTypeRepository.findCompanyTypeByName("PUBLIC_COMPANY_LIMITED_BY_SHARES")
                    .orElseThrow(() -> new CustomNotFoundException("PUBLIC_COMPANY_LIMITED_BY_SHARES not found"));
            companyTypeList.add(companyType6);
            CompanyType companyType7 = companyTypeRepository.findCompanyTypeByName("LIMITED_LIABILITY_PARTNERSHIP")
                    .orElseThrow(() -> new CustomNotFoundException("LIMITED_LIABILITY_PARTNERSHIP not found"));
            companyTypeList.add(companyType7);
            CompanyType companyType8 = companyTypeRepository.findCompanyTypeByName("LIMITED_PARTNERSHIP")
                    .orElseThrow(() -> new CustomNotFoundException("LIMITED_PARTNERSHIP not found"));
            companyTypeList.add(companyType8);
        }
        else if (purchasedProduct.getId() == 11L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.CHAIRMAN);
            CompanyType companyType = companyTypeRepository.findCompanyTypeByName("INCORPORATED_TRUSTEE")
                    .orElseThrow(() -> new CustomNotFoundException("INCORPORATED_TRUSTEE not found"));
            companyTypeList.add(companyType);
        }
        else if (purchasedProduct.getId() == 12L) {
            optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.PARTNER);
            CompanyType companyType8 = companyTypeRepository.findCompanyTypeByName("LIMITED_PARTNERSHIP")
                    .orElseThrow(() -> new CustomNotFoundException("LIMITED_PARTNERSHIP not found"));
            companyTypeList.add(companyType8);
        }
        else {
            throw new CustomNotFoundException("Affiliate type does not exist");
        }


        if (optionalAffiliateType.isEmpty()) {
            throw new CustomNotFoundException("Affiliate type not found");
        }
        AffiliateType affiliateType = optionalAffiliateType.get();

//        List<Company> company = companyRepository.findAllByCompanyByRcNumberAndCompanyTypeFk(productRequest.getRcNumber(), companyTypeList);
//        for (Company x: company) {
//            System.out.println("x........ "+ x.getId());
//        }
//        if (company.isEmpty()) {
//            throw new CustomNotFoundException("Company not found");
//        }

//        ProcessTaskHistory processTaskHistory = processTaskHistoryRepository.findProcessTypeHistoryByprocessTypeFk(processTypeId)
//                .orElseThrow(() -> new CustomNotFoundException("Process task history not found"));

//        List<Affiliates> affiliates = affiliatesRepository.findAffiliatesByCompanyFkAndAffiliateTypeFk(company.get().getId(), affiliateType.getId());
//        if (affiliates.isEmpty()) {
//            throw new CustomNotFoundException("Affiliates not found");
//        }

//        return affiliates;
        return null;
    }

    @Override
    public APIRequestResponse<?> findAffiliateByAffiliateType(AffiliateRequest affiliateRequest, String publicKey) {
        String username = UserDetails.getLoggedInUserDetails();
        LicensedPartners user = licensePartnerRepository.findLicensedPartnersByUsernameAndAccountStatus(username, UserAccountStatus.VERIFIED)
                .orElseThrow(() -> new CustomNotFoundException("Invalid User"));

        if (user.getPrivateKey().equals(affiliateRequest.getPrivateKey()) && user.getPublicKey().equals(publicKey)) {
            PurchasedProducts purchasedProduct = purchasedProductRepository.findPurchasedProductsById(affiliateRequest.getProductId())
                    .orElseThrow(() -> new CustomNotFoundException("Invalid Product"));

            Optional<AffiliateType> optionalAffiliateType = Optional.empty();
            List<CompanyType> companyTypeList = new ArrayList<>();
            List<CompanyType> companyType = null;
            if (purchasedProduct.getId() == 5L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.DIRECTOR);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else if (purchasedProduct.getId() == 6L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.PROPRIETOR);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else if (purchasedProduct.getId() == 7L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.TRUSTEE);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else if (purchasedProduct.getId() == 8L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.SECRETARY);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else if (purchasedProduct.getId() == 9L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.SHAREHOLDER);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else if (purchasedProduct.getId() == 10L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.WITNESS);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else if (purchasedProduct.getId() == 11L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.CHAIRMAN);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else if (purchasedProduct.getId() == 12L) {
                optionalAffiliateType = affiliateTypeRepository.findAffiliateTypeById(Constants.PARTNER);
                companyType = companyTypeRepository.findCompanyTypeByClassificationFk(affiliateRequest.getClassificationFk());
            }
            else {
                throw new CustomNotFoundException("Invalid product id");
            }

            if (optionalAffiliateType.isEmpty()) {
                return new APIRequestResponse<>("Affiliate type not found", false, null);
            }
            AffiliateType affiliateType = optionalAffiliateType.get();

            Company company = companyRepository.findAllByCompanyByRcNumberAndCompanyTypeFk(affiliateRequest.getRcNumber(), companyType)
                    .orElseThrow(() -> new CustomNotFoundException("Company not found"));
            System.out.println("x........ "+ company.getId());

            List<Affiliates> affiliates = affiliatesRepository.findAffiliatesByCompanyFkAndAffiliateTypeFk(company.getId(), affiliateType.getId());
            if (affiliates.isEmpty()) {
                return new APIRequestResponse<>("Affiliates not found", false, null);
            }

            List<AffiliateResponse> affiliateResponseList = new ArrayList<>();
            for (Affiliates x: affiliates) {
                AffiliateResponse response = new AffiliateResponse();
                response.setFirstname(x.getFirstname());
                affiliateResponseList.add(response);
            }

            Wallet wallet = walletRepository.findWalletsByUsername(user.getUser()).get();
            if (wallet.getBalance().compareTo(purchasedProduct.getProposed_price()) >= 0) {
                if (purchasedProduct.getProposed_price().compareTo(new BigDecimal("0.00")) <= 0) {
                    return new APIRequestResponse<>("Low Wallet Balance. Fund Wallet to Continue.", false, null);
                }
                wallet.setBalance(wallet.getBalance().subtract(purchasedProduct.getProposed_price()));

                WalletTransaction transaction = new WalletTransaction();
                transaction.setTransactionType(TransactionType.PRODUCT_PURCHASE);
                transaction.setUsername(user.getUser());
                transaction.setModifiedTime(LocalDateTime.now());
                transaction.setWallet(wallet);
                transaction.setEmail(user.getUsername());
                transaction.setAmount(purchasedProduct.getProposed_price());
                transaction.setPaymentChannel(PaymentChannel.WALLET);
                transactionRepository.save(transaction);

            }else {
                return new APIRequestResponse<>("Low Wallet Balance. Fund Wallet to Continue.", false, null);
            }
            return new APIRequestResponse<>("success",true, affiliateResponseList);
        }else {
            return new APIRequestResponse<>("Invalid API Public Key.", false, null);
        }
    }

    @Override
    public APIRequestResponse<?> historyOfDeniedNames(HistoryOfDeniedNameRequest request, String publicKey) {
        String username = UserDetails.getLoggedInUserDetails();
        LicensedPartners user = licensePartnerRepository.findLicensedPartnersByUsernameAndAccountStatus(username, UserAccountStatus.VERIFIED)
                .orElseThrow(() -> new CustomNotFoundException("Invalid User"));

        if (user.getPrivateKey().equals(request.getPrivateKey()) && user.getPublicKey().equals(publicKey)) {
            PurchasedProducts purchasedProduct = purchasedProductRepository.findPurchasedProductsById(request.getProductId())
                    .orElseThrow(() -> new CustomNotFoundException("Invalid Product"));

            List<NameAvailability> result = null;

            if (purchasedProduct.getId() == 3L) {
                result = nameAvailabilityRepository.findAllByOption1IgnoreCaseOrOption2IgnoreCase(request.getDeniedName());
            }
            assert result != null;
            if (result.isEmpty()) {
                return new APIRequestResponse<>("Not found", false, null);
            }

            List<NameDeniedHistoryResponse> nameDeniedHistoryResponseList = new ArrayList<>();
            for (NameAvailability x: result) {
                NameDeniedHistoryResponse response = new NameDeniedHistoryResponse();
                response.setDateDenied(x.getRejection_date());
                response.setOption1(x.getOption1());
                response.setOption2(x.getOption2());
                response.setReasonForDenial(x.getRejectionReason());
                nameDeniedHistoryResponseList.add(response);
            }

            Wallet wallet = walletRepository.findWalletsByUsername(user.getUser()).get();
            if (wallet.getBalance().compareTo(purchasedProduct.getProposed_price()) >= 0) {
                if (purchasedProduct.getProposed_price().compareTo(new BigDecimal("0.00")) <= 0) {
                    return new APIRequestResponse<>("Low Wallet Balance. Fund Wallet to Continue.", false, null);
                }
                wallet.setBalance(wallet.getBalance().subtract(purchasedProduct.getProposed_price()));

                WalletTransaction transaction = new WalletTransaction();
                transaction.setTransactionType(TransactionType.PRODUCT_PURCHASE);
                transaction.setUsername(user.getUser());
                transaction.setModifiedTime(LocalDateTime.now());
                transaction.setWallet(wallet);
                transaction.setEmail(user.getUsername());
                transaction.setAmount(purchasedProduct.getProposed_price());
                transaction.setPaymentChannel(PaymentChannel.WALLET);
                transactionRepository.save(transaction);

            }else {
                return new APIRequestResponse<>("Low Wallet Balance. Fund Wallet to Continue.", false, null);
            }
            return new APIRequestResponse<>("success", true, nameDeniedHistoryResponseList);
        }else {
            return new APIRequestResponse<>("Invalid API Public Key.", false, null);
        }

    }

    @Override
    public APIRequestResponse<?> searchAffiliationsForName(SearchForAffiliationsRequest request, String publicKey) {
        String username = UserDetails.getLoggedInUserDetails();
        LicensedPartners user = licensePartnerRepository.findLicensedPartnersByUsernameAndAccountStatus(username, UserAccountStatus.VERIFIED)
                .orElseThrow(() -> new CustomNotFoundException("Invalid User"));

        if (user.getPrivateKey().equals(request.getPrivateKey()) && user.getPublicKey().equals(publicKey)) {
            PurchasedProducts purchasedProduct = purchasedProductRepository.findPurchasedProductsById(request.getProductId())
                    .orElseThrow(() -> new CustomNotFoundException("Invalid Product"));

            List<Affiliates> result = null;

            if (purchasedProduct.getId() == 14L) {
                result = affiliatesRepository.findAllByFirstnameOrSurnameOrOtherNameOrEmail(request.getKeyword());
            }
            assert result != null;
            if (result.isEmpty()) {
                return new APIRequestResponse<>("Not found", false, null);
            }

            System.out.println("result............................ "+result);

            List<NameDeniedHistoryResponse> nameDeniedHistoryResponseList = new ArrayList<>();
//            for (NameAvailability x: result) {
//                NameDeniedHistoryResponse response = new NameDeniedHistoryResponse();
//                response.setDateDenied(x.getRejection_date());
//                response.setOption1(x.getOption1());
//                response.setOption2(x.getOption2());
//                response.setReasonForDenial(x.getRejectionReason());
//                nameDeniedHistoryResponseList.add(response);
//            }

            Wallet wallet = walletRepository.findWalletsByUsername(user.getUser()).get();
            if (wallet.getBalance().compareTo(purchasedProduct.getProposed_price()) >= 0) {
                if (purchasedProduct.getProposed_price().compareTo(new BigDecimal("0.00")) <= 0) {
                    return new APIRequestResponse<>("Low Wallet Balance. Fund Wallet to Continue.", false, null);
                }
                wallet.setBalance(wallet.getBalance().subtract(purchasedProduct.getProposed_price()));

                WalletTransaction transaction = new WalletTransaction();
                transaction.setTransactionType(TransactionType.PRODUCT_PURCHASE);
                transaction.setUsername(user.getUser());
                transaction.setModifiedTime(LocalDateTime.now());
                transaction.setWallet(wallet);
                transaction.setEmail(user.getUsername());
                transaction.setAmount(purchasedProduct.getProposed_price());
                transaction.setPaymentChannel(PaymentChannel.WALLET);
                transactionRepository.save(transaction);

            }else {
//                return new APIRequestResponse<>("Low Wallet Balance. Fund Wallet to Continue.", false, null);
            }
            return null;
//            return new APIRequestResponse<>("success", true, nameDeniedHistoryResponseList);
        }else {
            return new APIRequestResponse<>("Invalid API Public Key.", false, null);
        }
    }


}
