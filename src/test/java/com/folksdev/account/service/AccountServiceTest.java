package com.folksdev.account.service;

import com.folksdev.account.TestSupport;
import com.folksdev.account.dto.AccountCustomerDto;
import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.dto.TransactionDto;
import com.folksdev.account.dto.converter.AccountDtoConverter;
import com.folksdev.account.exception.CustomerNotFoundException;
import com.folksdev.account.model.Account;
import com.folksdev.account.model.Customer;
import com.folksdev.account.model.Transaction;
import com.folksdev.account.model.TransactionType;
import com.folksdev.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceTest extends TestSupport {

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;
 //   private Clock clock;

    private AccountService accountService;

    private final Customer customer = generateCustomer();
    private final AccountCustomerDto customerDto = new AccountCustomerDto("customer-id",
            "customer-name",
            "customer-surname");

    @BeforeEach
    public void setup() {
        accountRepository = mock(AccountRepository.class);
        customerService = mock(CustomerService.class);
        accountDtoConverter = mock(AccountDtoConverter.class);
        Clock clock = mock(Clock.class);

        accountService = new AccountService(accountRepository, customerService, accountDtoConverter, clock);

        when(clock.instant()).thenReturn(getCurrentInstant());
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
    }

    @Test
    public void testCreateAccount_whenCustomerIdExistsAndInitialCreditMoreThanZero_shouldCreateAccountWithTransaction() {

        CreateAccountRequest request = generateCreateAccountRequest(100);

        Account account = generateAccount(100);
        Transaction transaction = new Transaction(null, TransactionType.INITIAL, request.getInitialCredit(), getLocalDateTime(), account);
        account.getTransaction().add(transaction);

        TransactionDto transactionDto = new TransactionDto("", TransactionType.INITIAL, new BigDecimal(100), getLocalDateTime());
        AccountDto expected = new AccountDto("account-id", new BigDecimal(100), getLocalDateTime(), customerDto, Set.of(transactionDto));

        when(customerService.findCustomerbyId("customer-id")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);

        when(accountDtoConverter.convert(account)).thenReturn(expected);

        AccountDto result = accountService.createAccount(request);

        assertEquals(result, expected);

    }

    @Test
    public void testCreateAccount_whenCustomerIdExistsAndInitialCreditIsZero_shouldCreateAccountWithoutTransaction() {
        CreateAccountRequest request = generateCreateAccountRequest(0);

        Account account = generateAccount(0);
        AccountDto expected = new AccountDto("account-id", BigDecimal.ZERO, getLocalDateTime(), customerDto, Set.of());

        when(customerService.findCustomerbyId("customer-id")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountDtoConverter.convert(account)).thenReturn(expected);

        AccountDto result = accountService.createAccount(request);

        assertEquals(result, expected);
    }

    @Test
    public void testCreateAccount_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException() {
        CreateAccountRequest request = generateCreateAccountRequest(0);

        when(customerService.findCustomerbyId("customer-id")).thenThrow(new CustomerNotFoundException("test-exception"));

        assertThrows(CustomerNotFoundException.class,
                () -> accountService.createAccount(request));

        verify(customerService).findCustomerbyId(request.getCustomerId());
        verifyNoInteractions(accountRepository);
        verifyNoInteractions(accountDtoConverter);
    }

    private Account generateAccount(int balance) {
        return new Account("", new BigDecimal(balance), getLocalDateTime(), customer, new HashSet<>());
    }

}
