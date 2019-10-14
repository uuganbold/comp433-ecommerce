package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.CustomerDto;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.Customer;
import edu.luc.comp433.persistence.AddressRepository;
import edu.luc.comp433.persistence.CustomerRepository;
import edu.luc.comp433.persistence.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @Configuration
    static class ContextConfiguration {

        @Bean(name = "customerService")
        protected CustomerService customerService() {

            return new CustomerServiceImpl(customerRepository(), addressRepository(), paymentRepository());
        }

        @Bean
        protected CustomerRepository customerRepository() {
            return Mockito.mock(CustomerRepository.class);
        }

        @Bean
        protected AddressRepository addressRepository() {
            return Mockito.mock(AddressRepository.class);
        }

        @Bean
        protected PaymentRepository paymentRepository() {
            return Mockito.mock(PaymentRepository.class);
        }
    }

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @AfterEach
    void resetMocks() {
        reset(customerRepository);
        reset(addressRepository);
        reset(paymentRepository);
    }

    @Test
    void shouldReturnNullWhenNotFound() {
        //given
        long id = 1;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        //when
        CustomerDto dto = customerService.getCustomer(id);

        //then
        Assertions.assertNull(dto);
    }

    @Test
    void shouldReturnWhatItGet() {
        //given
        long id = 1;
        Customer customer = new Customer();
        customer.setFirstName("Some name");
        customer.setLastName("Lastname");
        customer.setEmail("email@sd.com");
        customer.setPhonenumber("234 phonenumber");
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        //when
        CustomerDto dto = customerService.getCustomer(id);

        //then
        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getFirstName(), dto.getFirstName());
        assertEquals(customer.getLastName(), dto.getLastName());
        assertEquals(customer.getEmail(), dto.getEmail());
        assertEquals(customer.getPhonenumber(), dto.getPhonenumber());

    }

    @Test
    void shouldPersistWhatItGet() throws DuplicatedEntryException {
        //given
        long id = 2;
        CustomerDto dto = new CustomerDto("Firstname", "LastName", "email", "Phonenum");
        when(customerRepository.save(any(Customer.class))).then(invocationOnMock -> {
            Customer c = (Customer) invocationOnMock.getArguments()[0];

            //then
            assertEquals(dto.getFirstName(), c.getFirstName());
            assertEquals(dto.getLastName(), c.getLastName());
            assertEquals(dto.getEmail(), c.getEmail());
            assertEquals(dto.getPhonenumber(), c.getPhonenumber());
            c.setId(id);
            return c;
        });

        //when
        CustomerDto d1 = customerService.createCustomer(dto);

        //then
        assertEquals(id, d1.getId());
    }

    @Test
    void shouldTranslateException() {
        //given
        long id = 3;
        CustomerDto dto = new CustomerDto("Firstname", "LastName", "email", "Phonenum");
        when(customerRepository.save(any(Customer.class))).thenThrow(DataIntegrityViolationException.class);

        //when
        assertThrows(DuplicatedEntryException.class, () -> customerService.createCustomer(dto));
    }

    @Test
    void shouldPersistWhatItGetWhenSave() throws EntryNotFoundException, DuplicatedEntryException {
        //given not found
        CustomerDto dto = new CustomerDto(123L, "Fist", "Last", "eadf@mail.com", "asdf");
        when(customerRepository.findById(dto.getId())).thenReturn(Optional.empty());

        //when
        assertThrows(EntryNotFoundException.class, () -> customerService.save(dto));

        //given found
        when(customerRepository.findById(dto.getId())).then(invocationOnMock -> {
            Customer c = new Customer();
            c.setId(dto.getId());
            c.setFirstName("Another First");
            c.setLastName("Another Last");
            return Optional.of(c);
        });

        //then
        when(customerRepository.save(any(Customer.class))).then(invocationOnMock -> {
            Customer cc = invocationOnMock.getArgument(0);

            assertEquals(dto.getId(), cc.getId());
            assertEquals(dto.getFirstName(), cc.getFirstName());
            assertEquals(dto.getLastName(), cc.getLastName());
            assertEquals(dto.getPhonenumber(), cc.getPhonenumber());
            assertEquals(dto.getEmail(), cc.getEmail());
            return cc;
        });

        //when
        customerService.save(dto);

        //given duplicated email
        when(customerRepository.save(any(Customer.class))).thenThrow(DataIntegrityViolationException.class);

        //when then
        assertThrows(DuplicatedEntryException.class, () -> customerService.save(dto));

    }

    @Test
    void deleteTest() throws EntryNotFoundException, NotRemovableException {
        //given
        long id = 1;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        //when then
        assertThrows(EntryNotFoundException.class, () -> customerService.delete(id));

        //given found
        Customer c = new Customer();
        c.setId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(c));

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        doNothing().when(customerRepository).delete(captor.capture());

        //when
        customerService.delete(id);
        assertEquals(c, captor.getValue());

        //given not removable
        doThrow(DataIntegrityViolationException.class).when(customerRepository).delete(any(Customer.class));

        assertThrows(NotRemovableException.class, () -> customerService.delete(id));
    }

    @Test
    void listAddressTest() {
        //when
        long id = 234;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        //when then
        assertThrows(EntryNotFoundException.class, () -> customerService.listAddresses(id));
    }
}
