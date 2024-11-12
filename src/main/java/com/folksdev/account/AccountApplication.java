package com.folksdev.account;

import com.folksdev.account.model.Account;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import kotlin.collections.SetsKt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {

	private final CustomerRepository customerRepository;

    public AccountApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String description,
								 @Value("${application-version}") String version){
		return new OpenAPI()
				.info(new Info()
						.title("Account API")
						.version(version)
						.description(description)
						.license(new License().name("Account API Licence")));
	}

	/*@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}*/

	@Override
	public void run(String... args) throws Exception {
 		Customer customer=customerRepository.save(new Customer("","Metehan","Ok",new HashSet<>()));
		Customer customer2=customerRepository.save(new Customer("","Ahmet","Ok",new HashSet<>()));
		Customer customer3=customerRepository.save(new Customer("","Kağan","Ok",new HashSet<>()));
		System.out.println(customer);
		System.out.println(customer2);
		System.out.println(customer3);
		//Account a=new Account("a", BigDecimal.ONE, LocalDateTime.now(),null, SetsKt.emptySet());
		//Account b=new Account("a", BigDecimal.ONE, LocalDateTime.now(),null, SetsKt.emptySet());

		//if(a==b)//false döner çünkü heapteki adresleri farklı isimlerinden dolayı
		//a.equals(b)//bu ise aynı döner çünkü hashcode göre çalışır, değerleri karşılaştırır
		//Set<Account>aa=Set.of(a,b);
		//set,hashset,hashmap gibi parametreler nesneyi hashcode'a göre alır,hashcode uniq değerdir

	}
}
