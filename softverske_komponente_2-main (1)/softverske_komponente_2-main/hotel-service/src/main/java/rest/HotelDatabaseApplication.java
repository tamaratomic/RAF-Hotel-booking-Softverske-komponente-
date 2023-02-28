package rest;

import initializers.DatabaseInitializer;
import mappers.HotelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import repositories.HotelRepo;

import javax.jms.ConnectionFactory;

@SuppressWarnings("all")

@EnableJms
@EntityScan(basePackages = {"models"})
@EnableJpaRepositories(basePackages = {"repositories"})
@SpringBootApplication
public class HotelDatabaseApplication {

    @Autowired
    private HotelRepo hotelRepo;

    public static void main(String[] args) {
        SpringApplication.run(HotelDatabaseApplication.class, args);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        jmsListenerContainerFactory.setConcurrency("5-10");
        return jmsListenerContainerFactory;
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    InitializingBean addHotels(){
        return () -> {
            hotelRepo.saveAll(DatabaseInitializer.initialHotels());
        };
    }

}
