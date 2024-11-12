package com.folksdev.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folksdev.account.IntegrationTestSupport;
import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.dto.converter.AccountDtoConverter;
import com.folksdev.account.dto.converter.CustomerAccountDtoConverter;
import com.folksdev.account.dto.converter.CustomerDtoConverter;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.AccountRepository;
import com.folksdev.account.repository.CustomerRepository;
import com.folksdev.account.service.AccountService;
import com.folksdev.account.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port=0",
        "command.line.runner.enabled=false"
})
@RunWith(SpringRunner.class)
@DirtiesContext
public class CustomerControllerTest extends IntegrationTestSupport{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Clock clock;

    @Autowired
    private AccountDtoConverter accountDtoConverter;

    @Autowired
    private CustomerDtoConverter customerDtoConverter;

    @Autowired
    private AccountService accountService=new AccountService(accountRepository,customerService,accountDtoConverter, clock);;
    private ObjectMapper mapper=new ObjectMapper();
    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomerDto() throws Exception {

        Customer customer = customerRepository.save(generateCustomer());
        accountService.createAccount(generateCreateAccountRequest(customer.getId(), 100));

        CustomerDto expected = customerDtoConverter.convertToCustomerDto(
                //
                customerRepository.getById(
                        Objects.requireNonNull(customer.getId())));

        this.mockMvc.perform(get(CUSTOMER_API_ENDPOINT + customer.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(expected), false))
                .andReturn();
    }
    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExist_shouldReturnHttpNotFound() throws Exception {

        this.mockMvc.perform(get(CUSTOMER_API_ENDPOINT + "non-exists-customer"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
