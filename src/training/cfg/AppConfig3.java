package training.cfg;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import training.entity.Brand;
import training.entity.Category;
import training.entity.Product;

@EnableTransactionManagement
@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackages = {"training.service", "training.dao", "training.aspects" })
public class AppConfig3 {
	
	// depends on sessionFactory, dataSource
	@Bean(autowire=Autowire.BY_NAME)
	public PlatformTransactionManager txManager(){
		return new HibernateTransactionManager();
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

	// auto-wires the above "dataSource" using setDataSource() method in lsfb
	@Bean(name = { "sessionFactory", "sf", "lsfb" }, autowire=Autowire.BY_NAME)
	public LocalSessionFactoryBean lsfb() {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		
		// optional property settings
		Properties props = new Properties();
		props.put("hibernate.show_sql", "false");
		props.put("hibernate.format_sql", "true");
		props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		sf.setHibernateProperties(props);
		
		sf.setAnnotatedClasses(Brand.class, Category.class, Product.class);
		return sf;
	}

	// depends on sessionFactory
	@Bean(name = { "ht", "tempalte", "hibernateTemplate" }, autowire = Autowire.BY_NAME)
	public HibernateTemplate ht() {
		return new HibernateTemplate();
	}

}
