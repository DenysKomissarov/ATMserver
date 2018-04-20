package com.dkom.atm.configuration;

import com.dkom.atm.controller.CardController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Configuration
public class MockDependencies {
    @Bean
    public MockMvc mvc() {
        return MockMvcBuilders.standaloneSetup(new CardController()).build();
    }
}
