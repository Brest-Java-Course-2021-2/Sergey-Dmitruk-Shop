package com.epam.brest;

import com.epam.brest.service.ProductDTOService;
import com.epam.brest.service.ProductService;
import com.epam.brest.validators.ProductValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class ProductsController {
    private final Logger logger = LogManager.getLogger(ProductsController.class);


    private ProductService productService;
    private ProductDTOService productDTOService;
    private ProductValidator validator;


    public ProductsController(ProductService productService, ProductDTOService productDTOService, ProductValidator validator) {
        this.productService = productService;
        this.productDTOService = productDTOService;
        this.validator = validator;


    }

    @GetMapping(value = "/products")
    public String productsSort( Model model,
                                @RequestParam(value = "from",required = false) String from,
                                @RequestParam(value = "to",required = false)String to) {
        if (from == null | to == null) {
            model.addAttribute("products", productService.findAllProduct());
        }else {
            if (!validator.checkValidateDate(from) || !validator.checkValidateDate(to)) {

                model.addAttribute("error", true);
                model.addAttribute("products", productService.findAllProduct());
            } else {
                model.addAttribute("products", productDTOService.sortedProductsByDate(LocalDate.parse(from), LocalDate.parse(to)));
            }
        }
            return "products";
        }



    @GetMapping(value = "/product")
    public String AddProductPage(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("isNew",true);
        return "product";
    }
    @GetMapping(value = "/product/{id}")
    public String editProduct(@PathVariable Integer id, Model model){
        model.addAttribute("isNew", false);
        model.addAttribute("product", productService.getProductById(id));
        return "product";
    }
    @PostMapping(value = "/product/{id}")
    public String updateProduct(Product product, BindingResult bindingResult){
        validator.validate(product,bindingResult);
        if(bindingResult.hasErrors())
            return "/product";
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/product/{id}/delete")
    public String deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }
    @PostMapping(value = "/product")
    public String createProduct(Product product, BindingResult bindingResult){
        validator.validate(product,bindingResult);
        if(bindingResult.hasErrors())
            return "/product";
        this.productService.createProduct(product);
        return "redirect:/products";
    }
}
