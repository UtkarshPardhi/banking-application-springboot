package com.example.Banking_App.mapper;

import com.example.Banking_App.dto.TransactionDto;
import com.example.Banking_App.entity.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToDto(Transaction transaction) {

        TransactionDto dto = new TransactionDto();

        dto.setId(transaction.getId());
        dto.setAccountId(transaction.getAccount().getId());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setTimestamp(transaction.getTimestamp());

        return dto;
    }
}
