package com.example.varekatalog.service;

import com.example.varekatalog.model.Product;
import com.example.varekatalog.repository.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Component
public class ProductServices {

    private final ProductMapper productMapper;

    @Autowired
    public ProductServices(ProductMapper pm){
        productMapper = pm;
    }

    public ArrayList<Product> getProducts(){
        return productMapper.findAll();
    }

    public boolean createProduct(String name, String price) throws Exception {
        int priceInInt = Integer.parseInt(price);
        int generatedId = productMapper.insert(new Product(name, priceInInt));
        if (generatedId==-1){
            throw new Exception("Product creation failed");
        } else
            return true;
    }

    public boolean deleteProduct(int id){
        return productMapper.delete(id);
    }

    public Product getProduct(int id){
        return productMapper.findById(id);
    }

    public boolean editProduct(String name, String price, int id){
        int priceInInt = Integer.parseInt(price);
        return productMapper.update(new Product(name, priceInInt, id));
    }
}
