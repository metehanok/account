package com.folksdev.account.service;

import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.dto.converter.CustomerDtoConverter;
import com.folksdev.account.exception.CustomerNotFoundException;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
//import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
//@RunWith(MockitoJUnitRunner.class)

public class CustomerServiceTest {
    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter customerDtoConverter;


    @BeforeEach
    public void setUp(){
        customerRepository=mock(CustomerRepository.class);
        customerDtoConverter=mock(CustomerDtoConverter.class);
        customerService=new CustomerService(customerRepository,customerDtoConverter);
    }
    @Test
    public  void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer =new Customer("id","name","surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        Customer result = customerService.findCustomerbyId("id");

        assertEquals(result,
                customer);
    }
    @Test
    public void testFindByCustomerId_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException(){

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.findCustomerbyId("id"));

    }
    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer =new Customer("id","name","surname", Set.of());
        CustomerDto customerDto = new CustomerDto("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        Mockito.when(customerDtoConverter.convertToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerById("customer-id");

        assertEquals(result,
                customerDto);
    }
    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException(){
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById("id"));

        Mockito.verifyNoInteractions(customerDtoConverter);
        //customerdtoconverterın hiçbir metodu çağrılmaz
    }

}