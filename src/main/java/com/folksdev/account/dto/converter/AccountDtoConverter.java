package com.folksdev.account.dto.converter;

import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountDtoConverter  {
//asıl amaç modellerin dtoya dönüştürülmesini sağlar. dto converter amacı budur.
    private final CustomerDtoConverter customerDtoConverter;
    private final TransactionDtoConverter transactionDtoConverter;
    public AccountDtoConverter(CustomerDtoConverter customerDtoConverter,TransactionDtoConverter transactionDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
        this.transactionDtoConverter=transactionDtoConverter;

    }

    public AccountDto convert(Account from){
        return new AccountDto(from.getId(),
        from.getBalance(),
        from.getCreationDate(),
        customerDtoConverter.converToAccountCustomer(from.getCustomer()),
        from.getTransaction()
                .stream()
                .map(transactionDtoConverter::convert)
                .collect(Collectors.toSet()));
        //for döngüsü ile yazmak yerine stream açarak lambda ile yazdık

    }

}
