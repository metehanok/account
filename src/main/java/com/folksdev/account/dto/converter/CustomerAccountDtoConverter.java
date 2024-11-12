package com.folksdev.account.dto.converter;

import com.folksdev.account.dto.CustomerAccountDto;
import com.folksdev.account.dto.TransactionDto;
import com.folksdev.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerAccountDtoConverter {
    private final TransactionDtoConverter converter;

    public CustomerAccountDtoConverter( TransactionDtoConverter converter) {
        this.converter = converter;

    }
    public CustomerAccountDto convert(Account from){
        return new CustomerAccountDto(
                Objects.requireNonNull(from.getId()),
                from.getBalance(),
                from.getTransaction().stream().map(converter::convert).collect(Collectors.toSet()),
                from.getCreationDate()
        );
    }
}
