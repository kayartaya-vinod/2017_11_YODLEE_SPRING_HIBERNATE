package training.programs;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import training.entity.Brand;
import training.util.HibernateUtil;

public class P02_AddNewBrand {
	public static void main(String[] args) {
		// user created object; nothing to do with hibernate
		// for Hibernate, b1 is a TRANSIENT object
		Brand b1 = new Brand("Dell");
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		
		// begin a transaction
		Transaction tx = session.beginTransaction();
		try {
			session.persist(b1); // or save()
			tx.commit();
			System.out.println("Data saved to DB!");
		} catch (Exception e) {
			tx.rollback();
			System.out.println("Could not save data!");
		}
		
		session.close();
		factory.close();
		// b1 now is a DETACHED object
	}
}
