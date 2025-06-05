package com.objectia.JBD_HandsOnLearning.services;

import com.objectia.JBD_HandsOnLearning.dtos.CheckFraudDTO;
import com.objectia.JBD_HandsOnLearning.dtos.TransactionRequestDTO;
import com.objectia.JBD_HandsOnLearning.feign.FraudFeignClient;
import com.objectia.JBD_HandsOnLearning.models.Account;
import com.objectia.JBD_HandsOnLearning.models.AccountCard;
import com.objectia.JBD_HandsOnLearning.models.Card;
import com.objectia.JBD_HandsOnLearning.models.Transaction;
import com.objectia.JBD_HandsOnLearning.repositories.AccountCardRepository;
import com.objectia.JBD_HandsOnLearning.repositories.AccountRepository;
import com.objectia.JBD_HandsOnLearning.repositories.CardRepository;
import com.objectia.JBD_HandsOnLearning.repositories.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Autowired
    CardRepository cardRepository;

    @Autowired
    FraudFeignClient fraudFeignClient;


    public void createTransaction(TransactionRequestDTO transactionRequestDTO) throws Exception {
        Card card = cardRepository.findByCardNumber(transactionRequestDTO.getCardNumber());
        List<AccountCard> accountCard  = accountCardRepository.findByCardId(card.getId());
        AccountCard selectedAccountCard = findByCurrency(accountCard,transactionRequestDTO.getCurrency());
        if(!card.getExpiryDate().after((new Date()))){
            throw new Exception();
        }
        Account account = selectedAccountCard.getAccount();
        if(transactionRequestDTO.getTransactionType().equals("D")){
            if(account.getBalance().subtract(transactionRequestDTO.getAmount()).intValue()<0){
                throw   new Exception();
            }
        }
        UUID transactionId = UUID.randomUUID();
        CheckFraudDTO checkFraudDTO = new CheckFraudDTO();
        checkFraudDTO.setTransactionId(transactionId);
        checkFraudDTO.setTransactionAmount(transactionRequestDTO.getAmount());
        Integer transactionsCount = transactionRepository.findByCardId(card.getId()).size();
        checkFraudDTO.setTrxCount(transactionsCount);
        fraudFeignClient.checkFraud(checkFraudDTO);
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


    public int findTransactionByCard(UUID cardId){
        return transactionRepository.findByCardId(cardId).size();
    }

    public AccountCard findByCurrency(List<AccountCard> accountCards, String currency) throws Exception {
        for (AccountCard accountCard : accountCards) {
            if (accountCard.getAccount().getCurrency().equals(currency)) {
                return accountCard;
            }
        }
        throw new Exception();
    }
}

