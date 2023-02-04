package com.example.cacvasbe.services.transaction;

import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.WalletTransaction;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.error_handler.CustomNotFoundException;
import com.example.cacvasbe.pojo.TransactionResponse;
import com.example.cacvasbe.repository.PortalUserRepository;
import com.example.cacvasbe.repository.TransactionRepository;
import com.example.cacvasbe.security.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Builder
public class TransactionServiceImpl implements TransactionService{

    private final PortalUserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionResponse> getUserTransactions(int offset, int pageSize) {

            String email = UserDetails.getLoggedInUserDetails();

           Optional<PortalUsers> user = userRepository.findByUsernameIgnoreCaseAndStatus(email, GenericStatusConstant.ACTIVE);
            if(user.isEmpty()){
                throw new CustomNotFoundException("User not found");
            }


        Pageable pageable = PageRequest.of(offset,pageSize);
        Page<WalletTransaction> pageList = transactionRepository.findWalletTransactionByUsernameOrderByCreatedTime( pageable, user.get());
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        pageList.forEach(page->{
            TransactionResponse transactionResponse1 = TransactionResponse.builder()
                    .transactionType(page.getTransactionType())
                    .amount(page.getAmount())
                    .description(page.getDescription())
                    .createdAt((page.getCreatedTime()))
//                    .usertId(page.getUser().getId())
//                    .email(page.getUser().getUsername())
//                    .rcNumber(page.getUser().getRcNumber())
                    .transactionId(page.getId())
                    .build();
            transactionResponses.add(transactionResponse1);

        });
        return transactionResponses;


    }

    @Override
    public List<WalletTransaction> allTransaction() {
        String username = UserDetails.getLoggedInUserDetails();
       Optional<PortalUsers> user = userRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);

       if (user.isEmpty()) {
           throw  new CustomNotFoundException("User not found");
       }

       return transactionRepository.transactionList(user.get().getId());

    }

    @Override
    public BigDecimal totalAmount() {
        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> user = userRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);

        if (user.isEmpty()) {
            throw  new CustomNotFoundException("User not found");
        }
        List<BigDecimal> amountList = transactionRepository.transactionList(user.get().getId()).stream().map(WalletTransaction::getAmount).collect(Collectors.toList());
        BigDecimal result = new BigDecimal(0);
        for (BigDecimal i: amountList) {
            result = result.add(i);
        }
        return result;
    }

    @Override
    public Integer totalTransaction() {
        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> user = userRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);

        if (user.isEmpty()) {
            throw  new CustomNotFoundException("User not found");
        }

        List<WalletTransaction> list = transactionRepository.transactionList(user.get().getId());
        return list.size();
    }


}
