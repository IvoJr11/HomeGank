package com.mindhub.Homebanking;

import com.mindhub.Homebanking.models.*;
import com.mindhub.Homebanking.repositories.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.swing.plaf.synth.ColorType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://127.0.0.1:5500", "https://app-homegank.herokuapp.com/").allowedMethods("*").allowedHeaders("*");
			}
		};
	}
	@Bean
	public CommandLineRunner initData(ClientRepository repository1, AccountRepository repository2, TransactionRepository repository3, LoanRepository repository4, ClientLoanRepository repository5, CardRepository repository6) {
		return (args) -> {

			Client Client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("Melba_uwu"), ClientType.CLIENT);
			Client Client2= new Client("Pedro", "Hernández", "pedrohf@gmail.com", passwordEncoder.encode("SoyUnaContraseña"), ClientType.CLIENT);
			Client admin = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("soyadmin"), ClientType.ADMIN);

			repository1.save(Client1);
			repository1.save(Client2);
			repository1.save(admin);

			Account account1 = new Account("VIN001", 5000.0, LocalDateTime.now(), Client1, true, AccountType.REGULAR);
			Account account2 = new Account("VIN002", 7500.0, LocalDateTime.now().plusDays(1), Client1, true, AccountType.SAVING);
			Account account3 = new Account("VIN003", 9000.0, LocalDateTime.now(), Client2, true, AccountType.REGULAR);
			Account account4 = new Account("VIN004", 10500.0, LocalDateTime.now().plusDays(1), Client2, true,AccountType.SAVING);

			repository2.save(account1);
			repository2.save(account2);
			repository2.save(account3);
			repository2.save(account4);

			Transaction transaction1 = new Transaction(25000.0, "Bought the Microprocessor", LocalDateTime.now(), Type.CREDIT, account1, 200d);
			Transaction transaction2 = new Transaction(950.0, "Bought the Microprocessor", LocalDateTime.now(), Type.DEBIT, account1, 500d);
			Transaction transaction3 = new Transaction(73540.0, "Bought the Motherboard", LocalDateTime.now(), Type.DEBIT, account2, 200d);
			Transaction transaction4 = new Transaction(15000.0, "Bought the Microprocessor", LocalDateTime.now(), Type.CREDIT, account2, 200d);
			Transaction transaction5 = new Transaction(500.0, "Bought the Microprocessor", LocalDateTime.now(), Type.DEBIT, account1, 700d);
			Transaction transaction6 = new Transaction(1200.0, "Bought the Microprocessor", LocalDateTime.now(), Type.CREDIT, account1, 1200d);
			Transaction transaction7 = new Transaction(48300.0, "Bought the Microprocessor", LocalDateTime.now(), Type.DEBIT, account1, 450d);
			Transaction transaction8 = new Transaction(21000.0, "Bought the Microprocessor", LocalDateTime.now(), Type.CREDIT, account1, 5000d);
			Transaction transaction9 = new Transaction(38000.0, "Bought the Microprocessor", LocalDateTime.now(), Type.DEBIT, account1, 200d);
			Transaction transaction10 = new Transaction(38000.0, "Bought the Microprocessor", LocalDateTime.now(), Type.DEBIT, account1, 200d);
			Transaction transaction11 = new Transaction(38000.0, "Bought the Microprocessor", LocalDateTime.now(), Type.DEBIT, account1, 200d);
			Transaction transaction12 = new Transaction(38000.0, "Bought the Microprocessor", LocalDateTime.now(), Type.DEBIT, account1, 200d);

			repository3.save(transaction1);
			repository3.save(transaction2);
			repository3.save(transaction3);
			repository3.save(transaction4);
			repository3.save(transaction5);
			repository3.save(transaction6);
			repository3.save(transaction7);
			repository3.save(transaction8);
			repository3.save(transaction9);
			repository3.save(transaction10);
			repository3.save(transaction11);
			repository3.save(transaction12);

			Loan loan1 = new Loan("Mortgage Loan", 500000d, List.of(12,24,36,48,60), 10d);
			Loan loan2 = new Loan("Personal Loan", 100000d, List.of(6,12,24),20d);
			Loan loan3 = new Loan("Car Loan", 300000d, List.of(6,12,24,36),30d);

			repository4.save(loan1);
			repository4.save(loan2);
			repository4.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000d, 60, loan1, Client1);
			ClientLoan clientLoan2 = new ClientLoan(50000d, 12, loan2, Client1);
			ClientLoan clientLoan7 = new ClientLoan(192084d, 36, loan3, Client1);
			ClientLoan clientLoan3 = new ClientLoan(100000d, 24, loan2, Client2);
			ClientLoan clientLoan4 = new ClientLoan(200000d, 36, loan3, Client2);

			repository5.save(clientLoan1);
			repository5.save(clientLoan2);
			repository5.save(clientLoan3);
			repository5.save(clientLoan4);

			repository5.save(clientLoan7);

			Card card1 = new Card(Client1, CardColor.GOLD, CardType.CREDIT, "2134 1242 8903 1045", 962, LocalDateTime.now(), LocalDateTime.now().plusYears(5), true);
			Card card2 = new Card(Client1, CardColor.TITANIUM, CardType.CREDIT, "0093 7489 0932 9155", 903, LocalDateTime.now(), LocalDateTime.now().plusYears(5), true);
			Card card3 = new Card(Client2, CardColor.SILVER, CardType.DEBIT, "9201 3210 8421 1092", 921, LocalDateTime.now(), LocalDateTime.now().plusYears(20), true);
            Card card4 = new Card(Client1, CardColor.SILVER, CardType.DEBIT, "2130 4564 8943 7598", 903, LocalDateTime.now().plusYears(1), LocalDateTime.now().plusYears(7), true);

			repository6.save(card1);
			repository6.save(card2);
			repository6.save(card3);
            repository6.save(card4);


		};
	}
}
