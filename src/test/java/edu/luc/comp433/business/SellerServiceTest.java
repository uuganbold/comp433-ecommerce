package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.SellerDto;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.model.Seller;
import edu.luc.comp433.persistence.AddressRepository;
import edu.luc.comp433.persistence.SellerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SellerServiceTest {

    @Configuration
    static class ContextConfiguration {

        @Bean(name = "sellerService")
        protected SellerService sellerService() {

            return new SellerServiceImpl(sellerRepository(), addressRepository());
        }

        @Bean
        protected SellerRepository sellerRepository() {
            return Mockito.mock(SellerRepository.class);
        }

        @Bean
        protected AddressRepository addressRepository() {
            return Mockito.mock(AddressRepository.class);
        }
    }

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerService sellerService;

    @Test
    void shouldReturnNullWhenNotFound() {
        //given
        long id = 1;
        when(sellerRepository.findById(id)).thenReturn(Optional.empty());

        //when
        SellerDto dto = sellerService.getSeller(id);

        //then
        assertNull(dto);
        reset(sellerRepository);
    }

    @Test
    void shouldCatchDuplicatedEntry() {
        //given
        when(sellerRepository.save(any(Seller.class))).thenThrow(new DataIntegrityViolationException("Some error"));

        //when
        assertThrows(DuplicatedEntryException.class, () -> sellerService.createSeller(new SellerDto()));

        reset(sellerRepository);
    }

    @Test
    void shouldSaveWhatItGet() throws DuplicatedEntryException {
        //given
        SellerDto dto = new SellerDto("some name", "web", "emil");
        when(sellerRepository.save(any(Seller.class))).then(in -> {
            Seller s = (Seller) in.getArguments()[0];

            //then
            assertEquals(dto.getName(), s.getName());
            assertEquals(dto.getWebsite(), s.getWebsite());
            assertEquals(dto.getEmail(), s.getEmail());
            return s;
        });

        //when
        sellerService.createSeller(dto);
        reset(sellerRepository);
    }


}
