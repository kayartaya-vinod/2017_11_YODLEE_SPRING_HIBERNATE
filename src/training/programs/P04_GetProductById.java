package training.programs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import training.entity.Product;
import training.util.HibernateUtil;

public class P04_GetProductById {

	public static void main(String[] args) {
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		
		try {
			Session session = factory.openSession();
			Product p1 = session.get(Product.class, 22);
			session.close();
			
			System.out.println("Product name = " + p1.getName());
			System.out.println("Price = " + p1.getUnitPrice());
			System.out.println("Brand = " + p1.getBrand().getName());
			System.out.println("Category = " + p1.getCategory().getName());
		} finally {
			factory.close();
		}
		
	}
}
