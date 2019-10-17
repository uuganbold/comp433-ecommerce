package edu.luc.comp433.api.workflow;

import edu.luc.comp433.business.SellerService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SellerActivityTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        protected SellerActivity sellerActivity() {
            return new SellerActivity(sellerService());
        }

        private SellerService sellerService() {
            return Mockito.mock(SellerService.class);
        }
    }


}
