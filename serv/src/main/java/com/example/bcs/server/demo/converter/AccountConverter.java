package com.example.bcs.server.demo.converter;

import com.example.bcs.server.demo.dto.AccountDto;
import com.example.bcs.server.demo.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AccountConverter {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    public AccountDto convertToDto(Account account) {
        AccountDto accountDto = mapper.map(account, AccountDto.class);
        if (account.getCreateDateTime() != null) {
            accountDto.setCreatedAt(dateTimeFormatter.format(account.getCreateDateTime()));
        } else {
            accountDto.setCreatedAt("");
        }
        return accountDto;
    }

    public Account convertToEntity(AccountDto accountDto) {
        Account account = mapper.map(accountDto, Account.class);
        if (!StringUtils.isEmpty(accountDto.getCreatedAt())) {
            account.setCreateDateTime(LocalDateTime.parse(accountDto.getCreatedAt(), dateTimeFormatter));
        }
        return account;
    }

}
