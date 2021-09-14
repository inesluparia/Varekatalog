package com.example.varekatalog.controller;

import com.example.varekatalog.model.Product;
import com.example.varekatalog.service.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    ProductServices productServices;

    @GetMapping("/")
    public String renderIndex(Model model) {
        model.addAttribute("products", productServices.getProducts());
        return "index.html";
    }

    @GetMapping("/add")
    public String addProductForm(){
        return "add-product.html";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, Model model) throws Exception {
    productServices.createProduct(product);
    return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        productServices.deleteProduct(id);
        return "redirect:/";

    }

    @GetMapping("/edit/{id}")
        public String getEditFrom(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productServices.getProduct(id));
        return "edit-product";
        }

    @PostMapping("/edit")
        public String editProduct(@ModelAttribute Product product){
        /*
            String name = request.getParameter("product-name");
            String price = request.getParameter("product-price");
            int productId = (int) request.getAttribute("productId", 1);
            boolean success = productServices.editProduct(name, price, productId);
          */
        productServices.editProduct(product);
        return "redirect:/";
    }


    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        model.addAttribute("stack", exception.toString());
        return "error.html";
    }

}
