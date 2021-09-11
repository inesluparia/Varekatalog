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

    private final ProductServices productServices;

    @Autowired
    public HomeController(ProductServices ps){
    productServices = ps;
    }

    @GetMapping("/")
    public String renderIndex(Model model) {
        ArrayList<Product> products = productServices.getProducts();
        //products.forEach(System.out::println);
        model.addAttribute("products", products);
        //can't remember, is the model persisting after I change endpoint???
        return "index.html";
    }

    @GetMapping("/add")
    public String addProductForm(){
        return "add-product.html";
    }

    @PostMapping("/add")
    public String addProduct(WebRequest request, Model model) throws Exception {
    String name = request.getParameter("product-name");
    String price = request.getParameter("product-price");
    boolean success = productServices.createProduct(name, price);
    if (success){
        model.addAttribute("message", "\'"+name+"\' er blevet tilføjet til varekataloget.");
    } else {
        model.addAttribute("message", "Something went wrong, please try again.");
    }
    model.addAttribute("products", productServices.getProducts());
    return "index.html";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int productId, Model model){
        boolean success = productServices.deleteProduct(productId);
        if (success){
            model.addAttribute("message", "Varen er belvet sletet fra varekataloget.");
        } else {
            model.addAttribute("message", "Something went wrong, please try again.");
        }
        model.addAttribute("products", productServices.getProducts());
        return "index.html";
    }

    @GetMapping("/edit")
        public String getEditFrom(@RequestParam("id") int productId, WebRequest request, Model model){
        request.setAttribute("productId", productId,1);
        Product product = productServices.getProduct(productId);
        System.out.println(product);
        model.addAttribute("product", product);
        return "edit-product";
        }

    @PostMapping("/edit")
        public String editProduct(WebRequest request, Model model){
            String name = request.getParameter("product-name");
            String price = request.getParameter("product-price");
            int productId = (int) request.getAttribute("productId", 1);
            boolean success = productServices.editProduct(name, price, productId);
            if (success){
                model.addAttribute("message", "\'"+name+"\' er belvet redigeret.");
            } else {
                model.addAttribute("message", "Something went wrong, please try again.");
            }
            model.addAttribute("products", productServices.getProducts());
            return "index";
        }


    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        model.addAttribute("stack", exception.toString());
        return "error.html";
    }

}
