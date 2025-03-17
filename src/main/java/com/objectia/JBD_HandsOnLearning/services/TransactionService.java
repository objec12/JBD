package com.objectia.JBD_HandsOnLearning.services;

import com.objectia.JBD_HandsOnLearning.DTO.TransactionRequestDTO;
import com.objectia.JBD_HandsOnLearning.models.Account;
import com.objectia.JBD_HandsOnLearning.models.AccountCard;
import com.objectia.JBD_HandsOnLearning.models.Card;
import com.objectia.JBD_HandsOnLearning.models.Transaction;
import com.objectia.JBD_HandsOnLearning.repositories.AccountCardRepository;
import com.objectia.JBD_HandsOnLearning.repositories.AccountRepository;
import com.objectia.JBD_HandsOnLearning.repositories.TransactionRepository;
import org.hibernate.envers.Audited;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TransactionService {


    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountCardRepository accountCardRepository;
    @Autowired
    AccountRepository accountRepository;


    public void createTransaction(TransactionRequestDTO transactionRequestDTO) throws Exception {
        AccountCard accountCard  = accountCardRepository.findById(transactionRequestDTO.getAccountCard()).get();
        Card card = accountCard.getCard();
        if(!card.getExpiryDate().after((new Date()))){
            throw new Exception();
        }
        Account account = accountCard.getAccount();
        if(transactionRequestDTO.getTransactionType().equals("D")){
            if(account.getBalance().subtract(transactionRequestDTO.getAmount()).intValue()<0){
                throw new Exception();
            }
        }

        UUID transactionId = UUID.randomUUID();

        if(transactionRequestDTO.getTransactionType().equals("C")){
            account.setBalance(account.getBalance().add(transactionRequestDTO.getAmount()));
        }
        else{
            account.setBalance(account.getBalance().subtract(transactionRequestDTO.getAmount()));

        }
        accountRepository.save(account);
        Transaction transaction = modelMapper.map(transactionRequestDTO,Transaction.class);
        transaction.setId(transactionId);
        transaction.setCard(card);
        transactionRepository.save(transaction);
    }
    
}

