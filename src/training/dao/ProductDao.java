package training.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import training.entity.Brand;
import training.entity.Category;
import training.entity.Product;

public interface ProductDao {
	// CRUD OPERATIONS
	@Transactional(readOnly = false)
	public void saveProduct(Product p) throws DaoException;

	public Product getProduct(Integer id) throws DaoException;

	@Transactional(readOnly = false, 
			propagation=Propagation.REQUIRES_NEW,
			rollbackFor={DaoException.class})
	public void updateProduct(Product p) throws DaoException;

	@Transactional(readOnly = false)
	public void deleteProduct(Integer id) throws DaoException;

	// QUERIES

	public List<Product> getAllProducts() throws DaoException;

	public List<Product> getProductsByPrice(Double min, Double max) throws DaoException;

	public List<Brand> getAllBrands() throws DaoException;

	public List<Category> getAllCategories() throws DaoException;

	public int getProductCount() throws DaoException;
}
