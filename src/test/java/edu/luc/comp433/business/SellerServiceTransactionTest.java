package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.AddressDTO;
import edu.luc.comp433.business.dto.SellerDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.persistence.AddressRepository;
import edu.luc.comp433.persistence.SellerRepository;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
class SellerServiceTransactionTest {

    @Configuration
    @PropertySource("classpath:application.properties")
    @EnableJpaRepositories("edu.luc.comp433.persistence")
    @EnableTransactionManagement
    static class ContextConfiguration {

        @Bean
        protected SellerService sellerService(SellerRepository repository, AddressRepository addressRepository) {

            return new SellerServiceImpl(repository, addressRepository);
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

        public Properties additionalProperties() {
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
    SellerService sellerService;

    @Autowired
    AddressRepository addressRepository;

    @Test
    void shouldAddAddress() throws EntryNotFoundException, DuplicatedEntryException {
        //given
        SellerDTO s = new SellerDTO("seller", "web", "email");
        s = sellerService.createSeller(s);

        AddressDTO dto = new AddressDTO("mongolia", "chingeltei", null, "ulaanbaatar", null, 0, null);

        //when
        sellerService.addAddress(s.getId(), dto);

        //then
        assertThat(dto.getId(), greaterThan(0L));
    }

    @Test
    void shouldThrowExceptionWhenSellerNotFound() {
        //given
        long id = 1002L;

        //when then
        assertThrows(EntryNotFoundException.class, () -> sellerService.addAddress(id, new AddressDTO()));


    }

    @Test
    void shouldRemoveAddress() throws EntryNotFoundException, DuplicatedEntryException, NotRemovableException {
        //given
        SellerDTO s = new SellerDTO("sell name", "web", "email");
        s = sellerService.createSeller(s);

        AddressDTO dto = new AddressDTO("mongolia", "chingeltei", null, "ulaanbaatar", null, 0, null);
        sellerService.addAddress(s.getId(), dto);

        //when
        sellerService.removeAddress(s.getId(), dto.getId());

        //then
        assertFalse(addressRepository.findById(dto.getId()).isPresent());

    }

}
