package training.programs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import training.entity.Brand;
import training.util.HibernateUtil;

public class P01_GetBrandById {
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		// This instance is created by Hibernate, and hence
		// it monitors the data changes
		// PERSISTENT
		Brand b1 = session.get(Brand.class, 1); // id = 1
		session.close();
//		factory.close();
		session = null;
		factory = null;
		
		// b1 is now called DETACHED object
		
		System.out.println("Name = " + b1.getName());
		System.out.println("Id = " + b1.getId());
		
		
	}
}
