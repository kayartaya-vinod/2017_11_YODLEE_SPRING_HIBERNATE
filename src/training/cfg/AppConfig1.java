package training.cfg;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Spring instantiates this class when the container is created
// equivalent of <beans ..> .. </beans>
@Configuration
@ComponentScan(basePackages = { "training.dao" })
public class AppConfig1 {

	@Bean(name = { "dbcp", "dataSource" })
	public DataSource ds() {
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("org.h2.Driver");
		bds.setUrl("jdbc:h2:tcp://localhost/~/yodleetraining");
		bds.setUsername("sa");
		bds.setPassword("");

		bds.setInitialSize(10);
		bds.setMaxActive(50);

		return bds;
	}
}







