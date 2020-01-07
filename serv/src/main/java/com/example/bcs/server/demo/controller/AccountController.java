package com.example.bcs.server.demo.controller;

import com.example.bcs.server.demo.converter.AccountConverter;
import com.example.bcs.server.demo.dto.AccountDto;
import com.example.bcs.server.demo.entity.Account;
import com.example.bcs.server.demo.exception.AccountNotFoundException;
import com.example.bcs.server.demo.repository.AccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountsRepo accountsRepository;

    @Autowired
    private AccountConverter accountConverter;

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> getOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS).build();
    }

    @GetMapping
    Collection<AccountDto> getAllAccounts() {
        return accountsRepository
                .findAll()
                .stream()
                .map(e -> accountConverter.convertToDto(e))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) throws AccountNotFoundException {
        return accountsRepository
                .findById(id)
                .map(e -> accountConverter.convertToDto(e))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    /**
     *
     * @param account
     * @return
     */
    @PostMapping
    ResponseEntity<AccountDto> postNewAccount(@Valid @RequestBody AccountDto account) {
        Account accountEntity = accountConverter.convertToEntity(account);
        accountEntity.setCreateDateTime(LocalDateTime.now());
        Account savedAccount = accountsRepository.save(accountEntity);

        URI uri =
                MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(savedAccount.getId()).toUri();

        return ResponseEntity.created(uri).body(accountConverter.convertToDto(savedAccount));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto account) throws AccountNotFoundException {
        return accountsRepository.findById(id).map(e -> {
            Account accountEntity = accountConverter.convertToEntity(account);
            accountEntity.setCreateDateTime(LocalDateTime.now());
            accountEntity.setId(id);
            Account updatedAccount = accountsRepository.save(accountEntity);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
            return ResponseEntity.created(uri).body(accountConverter.convertToDto(updatedAccount));
        }).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    ResponseEntity<VndErrors.VndError> accountNotFoundError(AccountNotFoundException e) {
        return new ResponseEntity<>(new VndErrors.VndError("Account ID: " + e.getAccountId().toString(), e.getMessage(),
                new Link(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())), HttpStatus.NOT_FOUND);
    }

}
