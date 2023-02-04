package com.example.cacvasbe.controllers;

import com.example.cacvasbe.dto.wallet.FundWalletRequest;
import com.example.cacvasbe.dto.wallet.WalletDto;
import com.example.cacvasbe.dto.wallet.WalletWithdrawalRequest;
import com.example.cacvasbe.entities.Wallet;
import com.example.cacvasbe.pojo.*;
import com.example.cacvasbe.services.wallet.WalletService;
import com.example.cacvasbe.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "**")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/fund-wallet")
    ResponseEntity<FundWalletResponse> fundWallet(@RequestBody FundWalletRequest fundWalletRequest) throws Exception {
        return new ResponseEntity<>(walletService.fundWallet(fundWalletRequest), HttpStatus.OK);
    }

    @GetMapping("/{userId}/wallet")
    public ResponseEntity<?> checkWalletBalance(@PathVariable("userId") Long userId){
        WalletBalanceResponse reponse = walletService.checkBalance(userId);
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
//
//    @PostMapping("/wallet-withdrawal")
//    ResponseEntity<Wallet> walletWithdrawal(@RequestBody WalletWithdrawalRequest walletWithdrawalRequest){
//        return walletService.walletWithdrawal(walletWithdrawalRequest);
//    }

    @GetMapping("/license-fee")
    public ResponseEntity<?> getLicenseFee(){
       return ResponseEntity.ok(walletService.getLicenseFee());
    }

    @GetMapping("/total-wallet-debit/{rcNumber}")
    public ResponseEntity<?> totalWalletDebit(@PathVariable("rcNumber") String rcNumber){
        return ResponseEntity.ok(walletService.getTotalWalletMoneySpent(rcNumber));
    }
}
