package com.eshop.product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.eshop.core.order.model.ProductImage;
import com.eshop.core.product.ProductService;
import com.eshop.core.product.hateoas.ProductResource;
import com.eshop.core.product.model.Product;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

	  @Autowired
	    ProductService productService;

    /*@Autowired
    private StorageService storageService;*/

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired Validator productValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(productValidator);
    }

    @GetMapping
    public List<ProductResource> index() {
        List<Product> res = productService.getProducts();
        List<ProductResource> output = new ArrayList<ProductResource>();
        res.forEach((p)->{
            ProductResource pr = new ProductResource(p);
            pr.add(createHateoasLink(p.getId()));

            output.add(pr);
        });
        return output;
    }

    @PostMapping
    public Product create(@RequestBody @Valid Product product){
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public ResourceSupport view(@PathVariable("id") long id){
        Product p = productService.getProduct(id);

        ProductResource pr = new ProductResource(p);
        pr.add(createHateoasLink(p.getId()));

        return pr;
    }

    @PostMapping(value = "/{id}")
    public Product edit(@PathVariable("id") long id, @RequestBody @Valid Product product){
    	
    	Product updatedProduct = productService.getProduct(id);

        if(updatedProduct == null){
            return null;
        }

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());

        return productService.saveProduct(updatedProduct);
    }

    @GetMapping("/{id}/images")
    public List<ProductImage> viewImages(@PathVariable("id") String productId){
        Session session = sessionFactory.openSession();
        List<ProductImage> list = session.createQuery("FROM ProductImage WHERE product_id = :product_id")
                .setLong("product_id", Long.parseLong(productId))
                .list();
        session.close();
        return list;
    }

   /* @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("id") String id) {

        Session session = sessionFactory.openSession();
        ProductImage image = (ProductImage) session.get(ProductImage.class, Long.parseLong(id));

        session.close();

        // Relative path to StorageProperties.rootLocation
        String path = "product-images/" + image.getProductId() + "/";

        Resource file = storageService.loadAsResource(path+image.getPath());
        String mimeType = "image/png";
        try {
            mimeType = file.getURL().openConnection().getContentType();
        } catch (IOException e){
            System.out.println("Can't get file mimeType. " + e.getMessage());
        }
        return ResponseEntity
                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .body(file);
    }*/

   /* @PostMapping("/{id}/uploadimage")
    public String handleFileUpload(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

        // Relative path to the rootLocation in storageService
        String path = "/product-images/" + id;
        String filename = storageService.store(file, path);

        return productService.addProductImage(id, filename);
    }*/

    // Todo: add delete method
    protected Link createHateoasLink(long id){
        Link link = linkTo(getClass()).slash(id).withSelfRel();
        return link;
    }

}
