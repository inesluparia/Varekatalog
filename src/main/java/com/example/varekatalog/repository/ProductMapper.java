package com.example.varekatalog.repository;

import com.example.varekatalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
@Component
public class ProductMapper {

//    @Autowired
//    public ProductMapper(){};

    public int insert(Product p) {
        int productId = -1;
        try {
            Connection connection = com.example.varekatalog.repository.DBManager.getConnection();
            String query = "INSERT INTO products (name, price) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, p.getName());
            preparedStatement.setInt(2, p.getPrice());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                productId = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productId;
    }

    public boolean update(Product p) {
        try {
            Connection connection = com.example.varekatalog.repository.DBManager.getConnection();
            String query = "UPDATE products SET name = ?, price = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
            preparedStatement.setString(1, p.getName());
            preparedStatement.setInt(2, p.getPrice());
            preparedStatement.setInt(3, p.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // TODO: 30/08/2021 find out how to see if an UPDATE was successfull
        return true;
    }

    public Product findById(int id) {
        try {
            Connection connection = com.example.varekatalog.repository.DBManager.getConnection();
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                return new Product(name, price, id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<Product> findAll() {
        ArrayList<Product> productsList = new ArrayList<>();
        try {
            Connection connection = com.example.varekatalog.repository.DBManager.getConnection();
            String query = "SELECT * FROM products";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int id = resultSet.getInt("id");
                productsList.add(new Product(name, price, id));
            } return productsList;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public boolean delete(int id) {
        try {
        String query = "DELETE FROM products WHERE id = ?";
        Connection connection = com.example.varekatalog.repository.DBManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.NO_GENERATED_KEYS);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } // TODO: 31/08/2021 find out how to get a confirmation for deletion
        return true;
    }

}
