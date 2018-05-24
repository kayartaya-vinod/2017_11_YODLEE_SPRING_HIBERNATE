package training.cfg;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig2 {

	@Bean(name = { "jt", "template" }, autowire = Autowire.BY_NAME)
	public JdbcTemplate template() {
		return new JdbcTemplate();
	}

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
