package mdettlaff.cloudreader.service;

import mdettlaff.cloudreader.persistence.PersistenceConfig;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableScheduling
@ComponentScan(basePackageClasses = ServiceConfig.class)
@Import(PersistenceConfig.class)
public class ServiceConfig {
}
