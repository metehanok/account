package com.folksdev.account.dto.converter;

import com.folksdev.account.dto.AccountCustomerDto;
import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {
    private final CustomerAccountDtoConverter converter;

    public CustomerDtoConverter(CustomerAccountDtoConverter customerAccountDtoConverter) {
        this.converter = customerAccountDtoConverter;
    }

    public AccountCustomerDto converToAccountCustomer(Customer from){
        if(from==null){
            return new AccountCustomerDto("","","");
        }else{

        }
         return new AccountCustomerDto(from.getId(),
                 from.getName(),
                 from.getSurname());

    }
    public CustomerDto convertToCustomerDto(Customer from){
        return new CustomerDto(
                from.getId(),
                from.getName(),
                from.getSurname(),
                from.getAccounts().stream().map(converter::convert).collect(Collectors.toSet())
        );
    }



}
