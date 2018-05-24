package training.programs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import training.entity.Brand;
import training.util.HibernateUtil;

public class P03_UpdateBrandName {

	public static void main(String[] args) {
		
		int brandId = 4;
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		Transaction tx = session.beginTransaction();
		// state: PERSISTENT
		Brand b1 = session.get(Brand.class, brandId);
		b1.setName("Dell Computers");
		System.out.println("---pass 1");
		tx.commit(); // fires UPDATE only if any dirty objects in session
		System.out.println("---pass 2");
		session.close();
		
		// state of b1: DETACHED
		
		System.out.println("---pass 2x");
		Session anotherSession = factory.openSession();
		tx = anotherSession.beginTransaction();
		
		// AFTER THE FOLLOWING STATEMENT, 
		// state of b1: PERSISTENT
		// anotherSession.update(b1); // marks b1 as "dirty"
		// anotherSession.saveOrUpdate(b1); // marks b1 as "dirty"
		anotherSession.merge(b1); 
		System.out.println("---pass 3");
		tx.commit();
		System.out.println("---pass 4");
		anotherSession.close();
		
		
		factory.close();
		
	}
}





