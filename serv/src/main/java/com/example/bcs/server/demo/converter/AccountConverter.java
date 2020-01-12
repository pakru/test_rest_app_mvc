package com.example.bcs.server.demo.converter;

import com.example.bcs.server.demo.dto.AccountDto;
import com.example.bcs.server.demo.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    @Autowired
    private ModelMapper mapper;

    public AccountDto convertToDto(Account account) {
        return mapper.map(account, AccountDto.class);
    }

    public Account convertToEntity(AccountDto accountDto) {
        return mapper.map(accountDto, Account.class);
    }

}
