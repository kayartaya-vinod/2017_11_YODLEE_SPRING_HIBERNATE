package training.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.dao.DaoException;
import training.dao.ProductDao;
import training.entity.Product;
import training.entity.ProductList;

@RestController
public class ProductController {

	@Autowired
	@Qualifier("HibernateProductDao")
	private ProductDao dao;

	// http://localhost:8080/products/12
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET,
			produces={"application/xml", "application/json"})
	public Product getOneProduct(@PathVariable("id") Integer id) throws DaoException {
		return dao.getProduct(id);
	}
	
	
	@RequestMapping(value = "/products", method = RequestMethod.GET,
			produces={"application/json"})
	public List<Product> getProductsAsJson() throws DaoException{
		return dao.getAllProducts();
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET,
			produces={"application/xml"})
	public ProductList getProductsAsXml() throws DaoException{
		return new ProductList(dao.getAllProducts());
	}
	
	
	@RequestMapping(value="/products", method=RequestMethod.POST,
			produces = "application/json",
			consumes = {"application/json", "application/xml"})
	public Map<String, Object> addNewProduct(@RequestBody Product p) throws DaoException{
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			dao.saveProduct(p);
			map.put("success", true);
			map.put("id", p.getId());
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
		}
		return map;
	}

}








