package com.example.cacvasbe.services.wallet;

import com.example.cacvasbe.dto.wallet.FundWalletRequest;
import com.example.cacvasbe.pojo.*;

import java.math.BigDecimal;

public interface WalletService {

    FundWalletResponse fundWallet (FundWalletRequest fundWalletRequest) throws Exception;
    WalletBalanceResponse checkBalance(long id);
    BigDecimal getLicenseFee();
    BigDecimal getTotalWalletMoneySpent(String rcNumber);

//    MakePaymentResponse sendRewardResponse(SendRewardRequest sendRewardRequest) throws Exception;

//    BigDecimal getStudentWalletBalance(PortalUser portalUser);

//    ResponseEntity<Wallet> walletWithdrawal(WalletWithdrawalRequest walletWithdrawalRequest);
}
