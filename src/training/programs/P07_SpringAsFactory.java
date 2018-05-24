package training.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import training.cfg.AppConfig1;
import training.dao.DaoException;
import training.dao.ProductDao;

public class P07_SpringAsFactory {

	public static void main(String[] args) throws DaoException {
		// a variable to represent spring container
		AnnotationConfigApplicationContext ctx;

		// an object of spring container
		ctx = new AnnotationConfigApplicationContext(AppConfig1.class);
		// bean variable
		ProductDao dao;

		dao = ctx.getBean("dao", ProductDao.class);
		int pc = dao.getProductCount();
		System.out.printf("There are %d products\n", pc);
		
		ctx.close();
	}
}
