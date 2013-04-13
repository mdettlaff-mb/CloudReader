package mdettlaff.cloudreader.service;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableScheduling
public class ServiceConfig {
}
