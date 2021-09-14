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

    @Autowired
    ProductMapper productMapper;

    public ArrayList<Product> getProducts(){
        return productMapper.findAll();
    }

    public boolean createProduct(Product product) throws Exception {
        int generatedId = productMapper.insert(product);
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

    public boolean editProduct(Product product){
        return productMapper.update(product);
    }
}
