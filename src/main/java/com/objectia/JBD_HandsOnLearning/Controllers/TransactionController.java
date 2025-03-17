package com.objectia.JBD_HandsOnLearning.Controllers;

import com.objectia.JBD_HandsOnLearning.DTO.TransactionRequestDTO;
import com.objectia.JBD_HandsOnLearning.services.AccountService;
import com.objectia.JBD_HandsOnLearning.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction-controller")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/create-transaction")
    public void createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) throws Exception {
        transactionService.createTransaction(transactionRequestDTO);
    }
}
