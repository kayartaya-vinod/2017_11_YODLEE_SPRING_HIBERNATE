package training.programs;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import training.entity.Brand;
import training.entity.Product;
import training.util.HibernateUtil;

public class P05_HQLDemos {

	private static Session session;

	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			session = factory.openSession();

			// getAllBrands();
			// getProductsByPriceRange(10.0, 15.0); // min,max = 10,15
			// getProductNames();
			// getProductInfo(); // get name, price, brand-name
			// getAveragePricesByCategory();
			getProductsByPage(4); // pagenum = 4, pagesize = 10

			session.close();
		} finally {
			factory.close();
		}
	}

	static void getProductsByPage(int pageNum) {
		int pageSize = 10;
		int skip = (pageNum-1) * pageSize;
		
		Query<Product> qry = session.createQuery(
				"from Product order by id", Product.class);
		
		qry.setMaxResults(pageSize);
		qry.setFirstResult(skip); // offset
		
		List<Product> list = qry.list();
		for(Product p: list){
			System.out.printf("%d --> %s\n", p.getId(), p.getName());
		}
	}

	static void getAveragePricesByCategory() {
		String hql = "select p.category.name, avg(p.unitPrice) from Product p "
				+ "group by p.category.name";
		Query<Object[]> qry = session.createQuery(hql, Object[].class);
		List<Object[]> list = qry.list();
		for(Object[] d: list){
			System.out.printf("%s --> %.2f\n", d[0], d[1]);
		}
					
	}

	static void getProductInfo() {
		String hql = "select name, unitPrice, brand.name from Product";
		Query<Object[]> qry = session.createQuery(hql, Object[].class);
		/*
		  	[
			[name, price, brand]
			[name, price, brand]
			[name, price, brand]
			[name, price, brand]
			]
		 */
		List<Object[]> list = qry.list();
		for(Object[] d: list){
			System.out.printf("%s (%s) -> Rs.%.2f\n", 
					d[0], d[2], d[1]);
		}
	}

	static void getProductNames() {
		String hql = "select name from Product";
		Query<String> qry = session.createQuery(hql, String.class);
		List<String> list = qry.list();
		for(String pname: list){
			System.out.println(pname);
		}
	}

	static void getProductsByPriceRange(double min, double max) {
		// select * from products where unit_price between ? and ?
		String hql = "from Product where unitPrice between :MIN and :MAX order by unitPrice";
		Query<Product> qry = session.createQuery(hql, Product.class);
		qry.setParameter("MIN", min);
		qry.setParameter("MAX", max);
		List<Product> list = qry.list();
		for(Product p: list){
			System.out.printf("%s --> Rs.%.2f\n", p.getName(), p.getUnitPrice());
		}
	}

	static void getAllBrands() {
		// SQL -> SELECT * FROM BRANDS
		// HQL -> SELECT b FROM Brand b
		// --> FROM Brand
		String hql = "from Brand";
		Query<Brand> qry = session.createQuery(hql, Brand.class);
		List<Brand> list = qry.list(); // executes the PreparedStatement here
		for(Brand b: list){
			System.out.printf("Brand %s has %d products\n", 
					b.getName(), b.getProducts().size());
		}
		
	}

}
