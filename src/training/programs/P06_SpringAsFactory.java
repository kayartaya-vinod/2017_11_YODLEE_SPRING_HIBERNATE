package training.programs;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import training.dao.DaoException;
import training.dao.ProductDao;

public class P06_SpringAsFactory {

	public static void main(String[] args) throws DaoException {
		// a variable to represent spring container
		ClassPathXmlApplicationContext ctx;

		// an object of spring container
		ctx = new ClassPathXmlApplicationContext("context1.xml");
		// bean variable
		ProductDao dao;

		dao = ctx.getBean("dao", ProductDao.class);
		System.out.println("dao is an instanceof " + dao.getClass());
		int pc = dao.getProductCount();
		System.out.printf("There are %d products\n", pc);
		
		ctx.close();
	}
}
