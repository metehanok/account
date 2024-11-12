package com.folksdev.account.service;

import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.dto.converter.AccountDtoConverter;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.model.Account;
import com.folksdev.account.model.Customer;
import com.folksdev.account.model.Transaction;
import com.folksdev.account.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;//kaydedilen nesneyi geri döner
    private final Clock clock;


    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter accountDtoConverter, Clock clock) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
        this.clock = clock;
    }




    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer=customerService.findCustomerbyId(createAccountRequest.getCustomerId());

        Account account=new Account(
                customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now()
                );

        if(createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO)>0){
            //Transaction transaction=transactionService.initiateMoney(account,createAccountRequest.getInitialCredit());
            Transaction transaction=new Transaction( createAccountRequest.getInitialCredit(),account);
            account.getTransaction().add(transaction);

        }
        return accountDtoConverter.convert(accountRepository.save(account));
        //kaydet ,convert et ve controllerde göster
    }

}
