package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.dao.DaoException;
import training.dao.ProductDao;
import training.entity.Product;

@Service
public class ProductService {

	@Autowired
	@Qualifier("HibernateProductDao")
	ProductDao dao;

	@Transactional(readOnly = false, rollbackFor = { ServiceException.class })
	public void testTransaction() throws ServiceException {

		try {
			Product p1 = dao.getProduct(1);
			Product p2 = dao.getProduct(2);
			Product p3 = dao.getProduct(3);
			
			p1.setUnitPrice(p1.getUnitPrice()+10);
			p2.setUnitPrice(p2.getUnitPrice()+10);
			p3.setUnitPrice(-10.0);
			
			dao.updateProduct(p1);
			dao.updateProduct(p2);
			dao.updateProduct(p3);
			
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

}
