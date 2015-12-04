package br.com.altamira.material;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableJms
@EnableWebSocket
@EnableTransactionManagement
@ComponentScan("br.com.altamira.material")
//@EnableConfigurationProperties
@EntityScan(basePackages = "br.com.altamira.material.model")
@EnableJpaRepositories(transactionManagerRef = "TransactionManager", entityManagerFactoryRef = "EntityManagerFactory", basePackages = "br.com.altamira.material.repository")
public class Application {

	/*@Bean
	ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(new ActiveMQConnectionFactory(
				"tcp://192.168.0.212:61616"
				//"tcp://127.0.0.1:61616"
				));
	}*/
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
	/*@Primary
	@Bean(name = "DataSource")
	@ConfigurationProperties(prefix = "datasource")
	public DataSource DataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean EntityManagerFactory(
			final EntityManagerFactoryBuilder builder) {
		return builder.dataSource(DataSource()).build();
	}

	@Primary
	@Bean(name = "TransactionManager")
	public JpaTransactionManager TransactionManager(
			@Qualifier("entityManagerFactory") final EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}*/
}
