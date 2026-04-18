package com.example.Banking_App.service.impl;

import com.example.Banking_App.dto.AccountDto;
import com.example.Banking_App.dto.TransactionDto;
import com.example.Banking_App.entity.Account;
import com.example.Banking_App.entity.Transaction;
import com.example.Banking_App.exception.AccountException;
import com.example.Banking_App.mapper.AccountMapper;
import com.example.Banking_App.mapper.TransactionMapper;
import com.example.Banking_App.repository.AccountRepository;
import com.example.Banking_App.repository.TransactionRepository;
import com.example.Banking_App.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exists"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Transactional
    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exists"));

//        double total = account.getBalance() + amount;
//        account.setBalance(total);
//        Account savedAccount = accountRepository.save(account);
//        return AccountMapper.mapToAccountDto(savedAccount);

       if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

            // ✅ UPDATE BALANCE
        account.setBalance(account.getBalance() + amount);

        // ✅ SAVE ACCOUNT
        accountRepository.save(account);

        // ✅ SAVE TRANSACTION
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        return AccountMapper.mapToAccountDto(account);
    }


    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exists"));

      if (account.getBalance() < amount){
          throw new RuntimeException("Insufficient amount");
      }

//      double total = account.getBalance() - amount;
//      account.setBalance(total);
//      Account savedAccount = accountRepository.save(account);
//
//      return  AccountMapper.mapToAccountDto(savedAccount);

        account.setBalance(account.getBalance() - amount);
      accountRepository.save(account);

      Transaction transaction = new Transaction();
      transaction.setAccount(account);
      transaction.setType("WITHDRAW");
      transaction.setAmount(amount);
      transaction.setTimestamp(LocalDateTime.now());

      transactionRepository.save(transaction);

      return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
       List<Account> accounts =  accountRepository.findAll();
       return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
               .collect(Collectors.toList());
    }

    @Transactional
    @Override

    public void deleteAccount(Long id) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exists"));
        // ✅ DELETE CHILD RECORDS FIRST
        transactionRepository.deleteByAccount_Id(id);

        // ✅ THEN DELETE ACCOUNT
        accountRepository.deleteById(id);

        System.out.println("Deleting transactions for account: " + id);

    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {

        // Check if account exists first
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException("Account does not exist"));

        List<Transaction> transactions =
                transactionRepository.findByAccount_Id(accountId);

        return transactions.stream()
                .map(TransactionMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Account getAccountEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

}
