package service.impl;

import entity.impl.Category;
import entity.impl.Product;
import service.CatetoryService;
import service.ProductService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;

public class CategoryServiceImpl implements CatetoryService {
    private static List<Category> dataCategory = new ArrayList<>();

    public CategoryServiceImpl() {
        load();
    }

    @Override
    public void add(Category category) {
        dataCategory.add(category);
        save();

    }

    @Override
    public Category findId(int id) {
        for (Category c : dataCategory) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void update(int id, Scanner sc) {
        Category categoryUpdate = findId(id);
        if (categoryUpdate != null) {
            categoryUpdate.inputData(sc);
            dataCategory.set(dataCategory.indexOf(findId(id)), categoryUpdate);
            System.out.println("-> Cập nhật Id: "+id+" thành công");
        } else {
            System.out.println("Không tìm thấy id: "+id);
        }
    }

    @Override
    public void remove(int id, ProductService products) {
        Category categoryRemove = findId(id);
        if (categoryRemove != null) {
            for (Product x : products.findAll()) {
                if (x.getCategoryId() == id) {
                    System.out.println("-> Danh mục đang có sản phẩm tham chiếu, không xóa được");
                    return;
                }
            }
            dataCategory.remove(categoryRemove);
            save();
            System.out.println("-> Xoá thành công");
        } else
            System.out.println("-> Không tìm thấy danh mục có Id: " + id);
    }

    @Override
    public List<Category> findByName(String name) {
        List<Category> result = new ArrayList<>();
        for (Category x : dataCategory) {
            if (x.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(x);
            }
        }
        return result;
    }

    @Override
    public List<Category> statisticsByQuantity() {
        return null;
    }

    @Override
    public List<Category> showCategory() {
        return dataCategory;
    }

    @Override
    public boolean findAny(Predicate<Category> predicate) {
        for (Category x : dataCategory) {
            if (predicate.test(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void load() {
        try {
            File file = new File("categories.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            dataCategory = (List<Category>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception ex) {
            dataCategory = new ArrayList<>();
        }
    }

    @Override
    public boolean save() {
        try {
            File file = new File("categories.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(dataCategory);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
