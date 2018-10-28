package com.eshop.core.product;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eshop.core.order.model.ProductImage;
import com.eshop.core.product.model.Product;
import com.eshop.core.product.model.ProductGroup;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ProductService {

	    @Autowired
        ProductRepository productRepository;

	   /* @Autowired
	    private SessionFactory sessionFactory;*/
	    
	   /* @PersistenceContext
	    EntityManager em;
*/
	    @Autowired
	    GroupRepository groupRepository;

	    /* PRODUCT */
	    public List<Product> getProducts(){
	        return productRepository.findAll();
	    }
	    public Product getProduct(long id){
	        return productRepository.findById(id).get();
	    }
	    public Product saveProduct(Product product){
	        return productRepository.save(product);
	    }

	 /*   public String addProductImage(final String productId, final String filename){

	        //Session session = sessionFactory.openSession();
	    	
	        //session.beginTransaction();

	        ProductImage image = new ProductImage();
	        image.setProductId(Long.parseLong(productId));
	        image.setPath(filename);

	        try {
	            String res = session.save(image).toString();
	            session.getTransaction().commit();
	            return res;
	        } catch (HibernateException e) {
	            System.out.print(e.getMessage());
	            session.getTransaction().rollback();
	        } finally {
	            session.close();
	        }
	        return "";
	    }*/

	    /* GROUPS */
	    public List<ProductGroup> getGroups(){
	        return groupRepository.findAll();
	    }
	    public ProductGroup getGroup(long id){
	        return groupRepository.findById(id).get();
	    }
	    public ProductGroup saveGroup(ProductGroup group){
	        return groupRepository.save(group);
	    }

	    
}