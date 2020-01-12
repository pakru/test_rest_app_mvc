package com.example.bcs.server.demo.controller;

import com.example.bcs.server.demo.converter.AccountConverter;
import com.example.bcs.server.demo.dto.AccountDto;
import com.example.bcs.server.demo.entity.Account;
import com.example.bcs.server.demo.exception.AccountNotFoundException;
import com.example.bcs.server.demo.repository.AccountsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountsRepo accountsRepository;

    @Autowired
    private AccountConverter accountConverter;

    private static final Logger logger = LoggerFactory.getLogger(AccountConverter.class);

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> getOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS).build();
    }

    @GetMapping
    Collection<Account> getAllAccounts() {
        logger.info("Get All Accounts request");
        return accountsRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Account> getAccountById(@PathVariable Long id) throws AccountNotFoundException {
        logger.info("Getting account: id = " + id);
        return accountsRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<Account> postNewAccount(@Valid @RequestBody AccountDto account) {
        logger.info("POST new account: " + account);
        Account accountEntity = accountConverter.convertToEntity(account);
        accountEntity.setCreateDateTime(LocalDateTime.now());
        Account savedAccount = accountsRepository.save(accountEntity);

        URI uri =
                MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(savedAccount.getId()).toUri();

        return ResponseEntity.created(uri).body(savedAccount);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        logger.info("Deleting account: id = " + id);
        accountsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Account> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto account) throws AccountNotFoundException {
        logger.info("Updating account: id = " + id + "; with data: " + account);
        return accountsRepository.findById(id).map(e -> {
            Account accountEntity = accountConverter.convertToEntity(account);
            accountEntity.setCreateDateTime(LocalDateTime.now());
            accountEntity.setId(id);
            Account updatedAccount = accountsRepository.save(accountEntity);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
            return ResponseEntity.created(uri).body(updatedAccount);
        }).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    ResponseEntity<VndErrors.VndError> accountNotFoundError(AccountNotFoundException e) {
        logger.info("Account not found: id = " + e.getAccountId());
        return new ResponseEntity<>(new VndErrors.VndError("Account ID: " + e.getAccountId(), e.getMessage(),
                new Link(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<VndErrors.VndError> constraintViolationError(DataIntegrityViolationException e) {
        logger.info("Data Integrity Violation Exception: " + e.getMessage());
        return new ResponseEntity<>(new VndErrors.VndError("Data Integrity Violation Exception. Possible key duplicate.", e.getMessage(),
                new Link(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())), HttpStatus.NOT_ACCEPTABLE);
    }

}
