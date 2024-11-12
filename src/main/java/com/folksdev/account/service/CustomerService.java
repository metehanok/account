package com.folksdev.account.service;

import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.dto.converter.CustomerDtoConverter;
import com.folksdev.account.exception.CustomerNotFoundException;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    //bir servisin bir repositorysi olacağı için
    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }
    //customer bilgisi dışarıya verilmeyeceği için protected olacak
    protected Customer findCustomerbyId(String id){
        return  customerRepository.findById(id)
                .orElseThrow(

                        () ->new CustomerNotFoundException("Customer could not found by id"+id));
        //customer not found exception classından geliuor hata mesajı
    }

    public CustomerDto getCustomerById(String customerId) {
        return customerDtoConverter.convertToCustomerDto(findCustomerbyId(customerId));
    }

    public List<CustomerDto> getAllCustomer() {
        return (List<CustomerDto>) customerRepository.findAll()
                .stream()
                .map(customerDtoConverter::convertToCustomerDto)
                .collect(Collectors.toSet());
    }
}
