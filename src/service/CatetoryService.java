package service;

import entity.impl.Category;
import entity.impl.Product;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public interface CatetoryService {
    void add(Category category);
    Category findId(int id);
    void update(int id, Scanner sc);
    void remove(int id, ProductService products);
    List<Category> findByName(String name);
    List<Category>  statisticsByQuantity();
    List<Category> showCategory();
    boolean findAny(Predicate<Category> predicate);
    void load();
    boolean save();

}
