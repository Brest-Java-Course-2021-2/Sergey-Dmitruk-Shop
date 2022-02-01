package com.epam.brest.rest;



import com.epam.brest.Product;
import com.epam.brest.dto.ProductDto;
import com.epam.brest.service.ProductDTOService;
import com.epam.brest.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ProductController {
    Logger logger =  LogManager.getLogger(ProductController.class);

    private ProductService productService;
    private ProductDTOService productDTOService;

    public ProductController(ProductService productService, ProductDTOService productDTOService) {
        this.productService = productService;
        this.productDTOService = productDTOService;
    }

    @GetMapping(value = "/products")
    public List<Product> findAllProducts(){
        logger.debug("findAllProducts");
        return productService.findAllProduct();
    }

    @GetMapping(value = "/products/{id}")
    public Product getProductById(@PathVariable Integer id){
        logger.debug("getProductById({})",id);
        return productService.getProductById(id);
    }

    @PutMapping(value = "/products", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> updateProduct( @RequestBody Product product){
        logger.debug("updateProduct({})",product);
        Integer amount = productService.updateProduct(product);
        return new ResponseEntity(amount, HttpStatus.OK);
    }
    @PostMapping(path = "/products", consumes = "application/json", produces = "application/json" )
    public  ResponseEntity<Integer> createProduct( @RequestBody Product product){
        logger.debug("createProduct({})",product);
        Integer id  = productService.createProduct(product);
        return new ResponseEntity(id,HttpStatus.OK);
    }
    @DeleteMapping(path = "/products/{id}",produces = "application/json")
    public ResponseEntity<Integer> deleteProduct(@PathVariable Integer id){
        logger.debug("deleteProduct({})",id);
        Integer amount = productService.deleteProduct(id);
        return new ResponseEntity(amount,HttpStatus.OK);
    }


    @GetMapping(value = "/products_sort")
    public List<ProductDto> sortedProductsByDate(
            @RequestParam(value = "from",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
             @RequestParam(value = "to",required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate to) {
        logger.debug("sortedProductsByDate({}{})",from,to);
        return productDTOService.sortedProductsByDate(from,to);
    }

}
