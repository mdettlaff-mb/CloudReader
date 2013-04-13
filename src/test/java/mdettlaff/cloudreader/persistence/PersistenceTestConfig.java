package mdettlaff.cloudreader.persistence;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
@ComponentScan(basePackageClasses = PersistenceConfig.class)
public class PersistenceTestConfig {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().addDefaultScripts().build();
	}

	@Bean
	public PropertyOverrideConfigurer propertyOverrideConfigurer() {
		PropertyOverrideConfigurer bean = new PropertyOverrideConfigurer();
		Properties properties = new Properties();
		properties.setProperty("entityManagerFactory.jpaVendorAdapter.showSql", "true");
		bean.setProperties(properties);
		return bean;
	}
}
