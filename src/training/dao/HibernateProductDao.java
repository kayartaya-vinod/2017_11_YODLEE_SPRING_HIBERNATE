package training.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import training.entity.Brand;
import training.entity.Category;
import training.entity.Product;

@Repository("HibernateProductDao")
@SuppressWarnings("unchecked")
public class HibernateProductDao implements ProductDao {

	@Autowired
	private HibernateTemplate template;

	public HibernateProductDao() {
	}

	@Override
	public void saveProduct(Product p) throws DaoException {
		template.persist(p);
	}

	@Override
	public Product getProduct(Integer id) throws DaoException {
		return template.get(Product.class, id);
	}

	@Override
	public void updateProduct(Product p) throws DaoException {
		if(p.getUnitPrice()<0){
			throw new DaoException("Invalid product price supplied!");
		}
		template.merge(p);
	}

	@Override
	public void deleteProduct(Integer id) throws DaoException {
		Product p = getProduct(id);
		if (p != null)
			template.delete(p); // if problem, throws DataAccessException
		else
			throw new DaoException("No data found for id " + id);
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		return (List<Product>) template.find("from Product");
	}

	@Override
	public List<Product> getProductsByPrice(Double min, Double max) throws DaoException {
		return (List<Product>) template.find("from Product where unitPrice between ? and ?", min, max);
	}

	@Override
	public List<Brand> getAllBrands() throws DaoException {
		return (List<Brand>) template.find("from Brand");
	}

	@Override
	public List<Category> getAllCategories() throws DaoException {
		return (List<Category>) template.find("from Category");
	}

	@Override
	public int getProductCount() throws DaoException {
		Object obj = template.find("select count(p) from Product p").get(0);
		return new Integer(obj.toString());
	}

}






