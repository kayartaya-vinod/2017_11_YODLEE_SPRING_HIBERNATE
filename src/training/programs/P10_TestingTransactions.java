package training.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import training.cfg.AppConfig3;
import training.service.ProductService;
import training.service.ServiceException;

public class P10_TestingTransactions {

	public static void main(String[] args) throws ServiceException {
		
		AnnotationConfigApplicationContext ctx;
		ctx = new AnnotationConfigApplicationContext(AppConfig3.class);
		
		ProductService service = ctx.getBean(ProductService.class);
		
		service.testTransaction();
		System.out.println("Product prices updated!");
		ctx.close();
	}
}
