package training.programs;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import training.cfg.AppConfig3;
import training.dao.DaoException;
import training.dao.ProductDao;
import training.entity.Brand;
import training.entity.Category;
import training.entity.Product;

public class P09_TestingHibernateProductDao {

	public static void main(String[] args) throws DaoException {

		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig3.class);

		ProductDao dao = ctx.getBean("HibernateProductDao", ProductDao.class);

		System.out.println("dao is an instanceof " + dao.getClass());

		int pc = dao.getProductCount();
		System.out.printf("There are %d products\n", pc);

		Product p = dao.getProduct(55);
		System.out.println("Before updating the price...");
		System.out.println("Name = " + p.getName());
		System.out.println("Price = " + p.getUnitPrice());

		List<Brand> brands = dao.getAllBrands();
		System.out.printf("There are %d brands\n", brands.size());

		List<Category> categories = dao.getAllCategories();
		System.out.printf("There are %d categories\n", categories.size());

		Double min = 15.0;
		Double max = 25.0;
		List<Product> list = dao.getProductsByPrice(min, max);
		System.out.printf("There are %d products between Rs.%.2f and %.2f\n", list.size(), min, max);

		list = dao.getProductsByPrice(max, min);
		System.out.printf("There are %d products between Rs.%.2f and %.2f\n", list.size(), max, min);

		try {
			dao.deleteProduct(22);
			System.out.println("Product 22 is no longer available");
		} catch (DaoException e) {
			System.out.println(e.getMessage());
		}

		p.setUnitPrice(p.getUnitPrice() + 5);
		dao.updateProduct(p);
		p = dao.getProduct(55);
		System.out.println("After updating the price...");
		System.out.println("Name = " + p.getName());
		System.out.println("Price = " + p.getUnitPrice());

		ctx.close();
	}
}
