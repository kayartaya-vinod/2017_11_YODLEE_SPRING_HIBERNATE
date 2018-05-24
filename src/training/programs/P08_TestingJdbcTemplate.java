package training.programs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import training.cfg.AppConfig2;
import training.entity.Product;

public class P08_TestingJdbcTemplate {
	
	private static JdbcTemplate template;
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig2.class);
		
		template = ctx.getBean(JdbcTemplate.class);

		// getProductCount();
		// getProductName(12); // id = 12
		// getProductInfo(34); // id = 34
		// getProductsByPriceRange(15.0, 20.0); // min->15, max-20
		// getProductsByPrice(15.0, 20.0);
		getProductById(19); // id=19
		
		
		ctx.close();
	}

	static void getProductById(int id) {
		String sql = "select * from products where id = ?";
		
		Product p1 = template.queryForObject(sql, ( rs,  rowNum) -> {
			Product p = new Product();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setDescription(rs.getString("description"));
			p.setUnitPrice(rs.getDouble("unit_price"));
			p.setDiscount(rs.getDouble("discount"));
			p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
			return p;
		}, id);
		
		System.out.println("Name = " + p1.getName());
		System.out.println("Price = Rs." + p1.getUnitPrice());
		
	}

	static void getProductsByPrice(double min, double max) {
		String sql = "select * from products where unit_price between ? and ?";
		List<Product> list = template.query(sql, new ProductRowMapper(), min, max);
		for(Product p: list){
			System.out.printf("%s (%s) --> Rs.%.2f\n",
					p.getName(), p.getDescription(), p.getUnitPrice());
		}
	}

	static void getProductsByPriceRange(double min, double max) {
		String sql = "select * from products where unit_price between ? and ?";
		// query returns multiple rows with multiple columns
		// use queryForList function (list of Map objects, where a Map represents 1 row)
		List<Map<String, Object>> list = template.queryForList(sql, min, max);
		
		for(Map<String, Object> map: list){
			System.out.printf("%s --> Rs.%.2f\n", 
					map.get("name"), map.get("unit_price"));
		}
	}

	static void getProductInfo(int id) {
		String sql = "select * from products where id = ?";
		// query returns 1 row, multiple columns
		// use queryForMap function
		Map<String, Object> map = template.queryForMap(sql, id);
		System.out.println("Name = " + map.get("name"));
		System.out.println("Price = Rs." + map.get("unit_price"));
		System.out.println("Discount = Rs." + map.get("DISCOUNT"));
		System.out.println("Quantity per unit = " + map.get("QUANTITY_per_UNIT"));
	}

	static void getProductName(int id) {
		String sql = "select name from products where id = ?";
		// use "queryForObject()" when the sql returns 1 row 1 column output
		String pname = template.queryForObject(sql, String.class, id);
		System.out.println("Product name = " + pname);
	}

	static void getProductCount() {
		// use "queryForObject()" when the sql returns 1 row 1 column output
		int pc = template.queryForObject("select count(*) from products", Integer.class);
		System.out.println("There are " + pc + " products");
	}
	
	// an interface with a single method in it is called functional interface
	// Comparator, Comparable, ActionListener, Runnable
	static class ProductRowMapper implements RowMapper<Product> {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product p = new Product();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setDescription(rs.getString("description"));
			p.setUnitPrice(rs.getDouble("unit_price"));
			p.setDiscount(rs.getDouble("discount"));
			p.setQuantityPerUnit(rs.getString("quantity_per_unit"));
			return p;
		}
		
	}
}






