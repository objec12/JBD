package com.objectia.JBD_HandsOnLearning.Controllers;

import com.objectia.JBD_HandsOnLearning.DTO.AccountRequestDTO;
import com.objectia.JBD_HandsOnLearning.DTO.AccountResponseDTO;
import com.objectia.JBD_HandsOnLearning.models.Account;
import com.objectia.JBD_HandsOnLearning.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;


@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create-account")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountRequestDTO accountRequestDTO) throws Exception {
            Account account =  modelMapper.map(accountRequestDTO,Account.class);
            Account createdAccount = accountService.createAccount(account);
            AccountResponseDTO accountResponseDTO = modelMapper.map(createdAccount,AccountResponseDTO.class);
            accountResponseDTO.setAccountHolderId(createdAccount.getAccountHolder().getId());
            return new ResponseEntity<>(accountResponseDTO, HttpStatus.CREATED);

    }


    @PutMapping("/update-account/{accountId}")
    public void updateAccount(@RequestBody Account account, @PathVariable UUID accountId) throws Exception {
        accountService.updateAccount(account,accountId);
    }

    @PatchMapping("/patch-account/{id}/{balance}")
    public ResponseEntity<BigDecimal> patchAccount(@PathVariable UUID id,@PathVariable BigDecimal balance) throws Exception {
        BigDecimal newBalance = accountService.modifyBalance(balance,id);
        return new ResponseEntity<>(newBalance,HttpStatus.ACCEPTED);

    }




}
