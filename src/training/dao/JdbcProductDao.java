package training.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import training.entity.Brand;
import training.entity.Category;
import training.entity.Product;

@Component("dao")
// Service --> classes in the business tier (service classes)
// Repository --> classes in the DAL (persistence mechanism)
// Controller --> Request handler classes in the presentation tier of MVC app 
// RestController --> Request handler classes in a RESTful MVC app
public class JdbcProductDao implements ProductDao {
	// fields; spring does not use these (while using xml config)
	private String driver;
	private String url;
	private String username;
	private String password;

	// data source (connection pool)
	@Autowired(required = false)
	private DataSource dataSource;

	public JdbcProductDao() {
	}

	// for spring to do DI
	public JdbcProductDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// for spring to do DI; writable property "dataSource"
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// spring can inject these values
	// constructor injection
	public JdbcProductDao(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	// getter - accessor; readble property "driver"
	// spring does not use this
	public String getDriver() {
		return driver;
	}

	// setter - mutator; writable property "driver"
	// spring uses this for setter injection
	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// for spring --> writable property "jdbcInfo"
	public void setJdbcInfo(String[] info) {
		driver = info[0];
		url = info[1];
		username = info[2];
		password = info[3];
	}

	// for spring --> writable property "jdbcProperties"
	public void setJdbcProperties(Properties props) {
		driver = props.getProperty("driver-classname");
		url = props.getProperty("connection-url");
		username = props.getProperty("user");
		password = props.getProperty("secret-code");
	}

	private Connection openConnection() throws DaoException {
		try {

			if (dataSource != null) {
				return dataSource.getConnection();
			}

			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public int getProductCount() throws DaoException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = openConnection();
			stmt = conn.prepareStatement("select count(*) from products");
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				throw new DaoException(e2);
			}
		}
	}

	@Override
	public void saveProduct(Product p) throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

	@Override
	public Product getProduct(Integer id) throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

	@Override
	public void updateProduct(Product p) throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

	@Override
	public void deleteProduct(Integer id) throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

	@Override
	public List<Product> getProductsByPrice(Double min, Double max) throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

	@Override
	public List<Brand> getAllBrands() throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

	@Override
	public List<Category> getAllCategories() throws DaoException {
		throw new DaoException("METHOD NOT IMPLEMENTED YET!");
	}

}
