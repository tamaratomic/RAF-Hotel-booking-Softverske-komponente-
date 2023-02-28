package rest.configuration;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.Duration;

@Configuration
public class RetryConfiguration {

    @Bean
    public Retry reservationServiceRetry(){
        RetryConfig retryConfig = RetryConfig.custom().intervalFunction(IntervalFunction.ofExponentialBackoff(2000,2))
                .maxAttempts(5).ignoreExceptions(ChangeSetPersister.NotFoundException.class).build();
        RetryRegistry registry = RetryRegistry.of(retryConfig);
        return registry.retry("adjustPointsRetry");
    }

}
