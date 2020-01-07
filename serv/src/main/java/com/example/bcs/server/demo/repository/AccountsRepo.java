package com.example.bcs.server.demo.repository;

import com.example.bcs.server.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepo extends JpaRepository<Account, Long> {

}
