package com.folksdev.account.controller;

import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//rest controller açtık çünkü bir apı olacak
@RequestMapping("/v1/account")//versiyonlama yapıcaz ,bu da apilerin birden fazla kullanımını sağlar
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping//account create edilecek
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest request){
        return ResponseEntity.ok(accountService.createAccount(request));
    }
}

