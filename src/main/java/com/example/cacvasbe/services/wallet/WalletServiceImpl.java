package com.example.cacvasbe.services.wallet;

import com.example.cacvasbe.dto.email.EmailDetailsDto;
import com.example.cacvasbe.dto.wallet.FundWalletRequest;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.Wallet;
import com.example.cacvasbe.entities.WalletTransaction;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.enums.PaymentChannel;
import com.example.cacvasbe.enums.TransactionType;
import com.example.cacvasbe.error_handler.CustomNotFoundException;
import com.example.cacvasbe.pojo.*;
import com.example.cacvasbe.repository.*;
import com.example.cacvasbe.security.UserDetails;
import com.example.cacvasbe.services.emailService.ConfirmationTokenService;
import com.example.cacvasbe.services.payStack.PayStackVerification;
import com.example.cacvasbe.services.payStack.PaymentService;
import com.example.cacvasbe.utils.Constants;
import com.example.cacvasbe.utils.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private final PortalUserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionsRepository walletTransactionsRepository;
    private final PaymentService paymentService;
    private final PayStackVerification payStackVerification;
    private final LicensePartnerRepository licensePartnerRepository;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final TransactionRepository transactionRepository;


    @Autowired
    public WalletServiceImpl(PortalUserRepository userRepository, WalletRepository walletRepository, WalletTransactionsRepository walletTransactionsRepository, PaymentService paymentService, PayStackVerification payStackVerification, LicensePartnerRepository licensePartnerRepository, EmailService emailService, ConfirmationTokenService confirmationTokenService, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.walletTransactionsRepository = walletTransactionsRepository;
        this.paymentService = paymentService;
        this.payStackVerification = payStackVerification;
        this.licensePartnerRepository = licensePartnerRepository;
        this.emailService = emailService;
        this.confirmationTokenService = confirmationTokenService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public FundWalletResponse fundWallet(FundWalletRequest fundWalletRequest) throws Exception {

        String username = UserDetails.getLoggedInUserDetails();
        System.out.println("username is "+ username);

        Optional<PortalUsers> user = userRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);

        if (user.isEmpty()) {
            throw new CustomNotFoundException("User with username "+ user.get().getUsername()+ " not found");
        }

//        PayStackTransactionRequest payStackTransactionRequest = PayStackTransactionRequest
//                .builder()
//                .email(username)
//                .amount(fundWalletRequest.getAmount())
//                .build();
//
//        PayStackTransactionResponse transactionResponse = paymentService.initTransaction(payStackTransactionRequest);
//
//        if (!transactionResponse.isStatus()) {
//            throw new Exception("Payment not authorized");
//        }

        System.out.println("..........Verifying.............");

//        PayStackVerification payStackVerification1 = payStackVerification.verifyTransaction(transactionResponse.getData().getReference());
//        if(payStackVerification1 == null){
//            throw new Exception("Paystack verification failed");
//        }else {
//
//        }

        Optional<Wallet> wallet = walletRepository.findWalletsByUsername(user.get());
        Wallet wallet2;
        if (wallet.isPresent()) {
            BigDecimal balance = wallet.get().getBalance();
            wallet.get().setBalance(balance.add(fundWalletRequest.getAmount()));

            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(TransactionType.DEPOSITE);
            walletTransaction.setUsername(user.get());
            walletTransaction.setDescription("Wallet funding");
            walletTransaction.setAmount(fundWalletRequest.getAmount());
            walletTransaction.setEmail(user.get().getUsername());
//            walletTransaction.setReference(transactionResponse.getData().getReference());
            walletTransaction.setPaymentChannel(PaymentChannel.PAYSTACK);
            walletTransaction.setModifiedTime(LocalDateTime.now());
            walletTransaction.setCreatedTime(LocalDateTime.now());
            walletTransaction.setWallet(wallet.get());
            Wallet wallet1 = walletRepository.save(wallet.get());
            walletTransactionsRepository.save(walletTransaction);


            EmailDetailsDto emailDetailsRequest = EmailDetailsDto.builder()
                    .msgBody(emailService.WalletFundingEmail(user.get().getUsername(), String.valueOf(fundWalletRequest.getAmount())))
                    .subject("CAC Email")
                    .recipient(user.get().getUsername())
                    .build();
            emailService.sendMailWithAttachment(emailDetailsRequest);

            return new FundWalletResponse(user.get().getRcNumber(), fundWalletRequest.getAmount(), user.get().getUsername());

//            return new PaymentResponse(transactionResponse.getData().getAuthorization_url(), transactionResponse.getData().getReference(), transactionResponse.getData().getAccess_code());

        } else {
            Wallet wallet1 = new Wallet();
            wallet1.setUsername(user.get());
            wallet1.setBalance(fundWalletRequest.getAmount());

            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(TransactionType.DEPOSITE);
            walletTransaction.setAmount(fundWalletRequest.getAmount());
            walletTransaction.setModifiedTime(LocalDateTime.now());
            walletTransaction.setCreatedTime(LocalDateTime.now());
            walletTransaction.setPaymentChannel(PaymentChannel.PAYSTACK);
            walletTransaction.setWallet(wallet1);
            walletTransaction.setUsername(user.get());
            wallet2 = walletRepository.save(wallet1);
            walletTransactionsRepository.save(walletTransaction);

            EmailDetailsDto emailDetailsRequest = EmailDetailsDto.builder()
                    .msgBody(emailService.WalletFundingEmail(user.get().getUsername(), String.valueOf(fundWalletRequest.getAmount())))
                    .subject("CAC Email")
                    .recipient(user.get().getUsername())
                    .build();
            emailService.sendMailWithAttachment(emailDetailsRequest);

        }
        return new FundWalletResponse(user.get().getRcNumber(), fundWalletRequest.getAmount(), user.get().getUsername());
//        return new PaymentResponse(transactionResponse.getData().getAuthorization_url(), transactionResponse.getData().getReference(), transactionResponse.getData().getAccess_code());

    }



    @Override
    public WalletBalanceResponse checkBalance(long id) {
        PortalUsers user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        Wallet wallet = walletRepository.findWalletsByUsername(user).get();
        WalletBalanceResponse balance = new WalletBalanceResponse();
        balance.setCurrentBalance(wallet.getBalance());
        return balance;
    }

//    @Override
//    public ResponseEntity<Wallet> walletWithdrawal(WalletWithdrawalRequest walletWithdrawalRequest) {
//        Wallet wallet = walletRepository.findWalletsByUsername().orElseThrow(()-> new CustomNotFoundException("Kindly input correct email"));
//        if (walletWithdrawalRequest.getAmount().compareTo(wallet.getBalance()) > 0) {
//            throw new ClientRequestException("Insufficient funds");
//        } else {
//            BigDecimal balance = wallet.getBalance();
//            wallet.setBalance(balance.subtract(walletWithdrawalRequest.getAmount()));
//            WalletTransaction walletTransaction = new WalletTransaction();
//            walletTransaction.setTransactionType(TransactionType.WITHDRAWAL);
//            walletTransaction.setAmount(walletWithdrawalRequest.getAmount());
//            walletTransaction.setCreatedTime(LocalDateTime.now());
//            walletTransaction.setWallet(wallet);
//            walletTransactionsRepository.save(walletTransaction);
//            walletRepository.save(wallet);
//        }
//        return ResponseEntity.ok(wallet);
//    }

    @Override
    public BigDecimal getLicenseFee() {
        return Constants.LICENSE_FEE;
    }

    @Override
    public BigDecimal getTotalWalletMoneySpent(String rcNumber) {
        String username = UserDetails.getLoggedInUserDetails();
       Optional<PortalUsers> user = userRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
       if (user.isEmpty()) {
           throw new CustomNotFoundException("Invalid user");
       }

       Wallet walletUser = walletRepository.findWalletsByUsername(user.get()).get();
       return walletUser.getTotalMoneyUsed();
    }

}



