package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.CustomerDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.model.Customer;
import edu.luc.comp433.persistence.AddressRepository;
import edu.luc.comp433.persistence.CustomerRepository;
import edu.luc.comp433.persistence.PaymentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTransactionTest {

    @Configuration
    @PropertySource("classpath:application.properties")
    @EnableJpaRepositories("edu.luc.comp433.persistence")
    @EnableTransactionManagement
    static class ContextConfiguration {


        @Bean
        protected CustomerService customerService(CustomerRepository customerRepository, AddressRepository addressRepository, PaymentRepository paymentRepository) {
            return new CustomerServiceImpl(customerRepository, addressRepository, paymentRepository);
        }

        @Bean("dataSource")
        public DataSource h2TestDataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(h2TestDataSource());
            em.setPackagesToScan("edu.luc.comp433.model");

            JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);
            em.setJpaProperties(additionalProperties());

            return em;
        }

        Properties additionalProperties() {
            Properties p = new Properties();
            p.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            p.setProperty("hibernate.hbm2ddl.auto", "create");
            p.setProperty("hibernate.show_sql", "true");
            p.setProperty("hibernate.format_sql", "true");
            return p;
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(emf);
            return transactionManager;
        }

    }

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    @Order(1)
    void shouldReturnNullWhenNotFound() {
        assertNull(customerService.getCustomer(1000L));
    }

    @Test
    @Order(2)
    void shouldPersist() throws DuplicatedEntryException {
        CustomerDTO dto = new CustomerDTO("FirstName", "LastName", "test@test.com", "");
        CustomerDTO dto1 = customerService.createCustomer(dto);
        assertEquals(1L, dto1.getId());

        Customer c = customerRepository.findById(1L).orElse(null);
        assertNotNull(c);
        assertEquals(dto.getFirstName(), c.getFirstName());
        assertEquals(dto.getLastName(), c.getLastName());
        assertEquals(dto.getEmail(), c.getEmail());
        assertEquals(dto.getPhonenumber(), c.getPhonenumber());
    }

    @Test
    @Order(3)
    void shouldThrowErrorWhenDuplicatedEmail() {
        CustomerDTO dto = new CustomerDTO("asdf", "asdfwer", "test@test.com", "2134");
        assertThrows(DuplicatedEntryException.class, () -> customerService.createCustomer(dto));
    }

    @Test
    @Order(4)
    void shouldFindCustomer() {
        Customer customer = new Customer();

    }


}
