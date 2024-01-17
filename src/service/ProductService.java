package service;

import entity.impl.Product;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public interface ProductService {
    void add(Product product);
    Product findId(String id);
    void update(String id, Scanner sc, CatetoryService catetoryService);
    void remove(String id);
    List<Product> findAll();
    List<Product> showProductAtoZ();
    List<Product> showProductByProfit();
    List<Product>  findProductByAny();
    boolean findAny(Predicate<Product> predicate);

    void load();
    boolean save();
}
