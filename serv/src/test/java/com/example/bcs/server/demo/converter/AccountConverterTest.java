package com.example.bcs.server.demo.converter;

import com.example.bcs.server.demo.dto.AccountDto;
import com.example.bcs.server.demo.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountConverterTest {

    @Autowired
    private AccountConverter accountConverter;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    @Test
    public void convertToDto() {
        Account account = new Account();
        account.setId(100L);
        account.setName("NewAccount");
        account.setCreateDateTime(LocalDateTime.now());
        account.setEmail("test@example.com");

        AccountDto resultAccountDto = accountConverter.convertToDto(account);
        assertEquals(account.getId(), resultAccountDto.getId());
        assertEquals(account.getName(), resultAccountDto.getName());
        assertEquals(account.getEmail(), resultAccountDto.getEmail());
        assertEquals(dateTimeFormatter.format(account.getCreateDateTime()), resultAccountDto.getCreatedAt());
    }

    @Test
    public void convertToEntity() {
        AccountDto accountDto = new AccountDto(100L, "NewAccount", "2012-01-12 10:22:40", "test@example.com");

        Account resultAccountEntity = accountConverter.convertToEntity(accountDto);
        assertEquals(accountDto.getId(), resultAccountEntity.getId());
        assertEquals(accountDto.getName(), resultAccountEntity.getName());
        assertEquals(accountDto.getEmail(), resultAccountEntity.getEmail());
        assertEquals(accountDto.getCreatedAt(), dateTimeFormatter.format(resultAccountEntity.getCreateDateTime()));
    }
}