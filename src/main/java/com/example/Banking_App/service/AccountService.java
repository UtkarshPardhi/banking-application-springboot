package com.example.Banking_App.service;


import com.example.Banking_App.dto.AccountDto;
import com.example.Banking_App.dto.TransactionDto;
import com.example.Banking_App.entity.Account;

import java.util.List;


public interface AccountService {

    AccountDto createAccount(AccountDto accountDto );

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

    List<TransactionDto> getTransactionsByAccountId(Long accountId);

    Account getAccountEntityById(Long id);

}
