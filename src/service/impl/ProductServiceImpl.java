package service.impl;

import entity.impl.Category;
import entity.impl.Product;
import service.CatetoryService;
import service.ProductService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class ProductServiceImpl implements ProductService  {
    private static List<Product> dataProduct = new ArrayList<>();
    public ProductServiceImpl(){
        load();
    }

    @Override
    public void add(Product product) {
        dataProduct.add(product);
        save();
    }

    @Override
    public Product findId(String id) {
        return dataProduct.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return dataProduct;
    }

    @Override
    public void update(String id, Scanner sc, CatetoryService catetoryService) {
        Product productUpdate = findId(id);
        if (productUpdate != null){
            productUpdate.inputData(sc,catetoryService);
            dataProduct.set(dataProduct.indexOf(findId(id)),productUpdate);
            System.out.println("-> Cập nhật Id - "+id+ " thành công");
        }else {
            System.out.println("-> Không tìm thấy Id: " +id);
        }
    }

    @Override
    public void updateCategoryName(int id, CatetoryService catetoryService) {
        String name = "";
        for (Category c : catetoryService.showCategory()){
            if ( c.getId() == id){
                name = c.getName();
            }
        }
        for (Product p : dataProduct){
            if (p.getCategoryId()==id)
                p.setCategoryName(name);
                save();
        }

    }

    @Override
    public void remove(String id) {
        Product product = findId(id);
        if (product != null) {
            dataProduct.remove(product);
            save();
        } else {
            System.out.println("-> Không tìm thấy sản phẩm có Id: " + id);
        }
    }

    @Override
    public List<Product> showProductAtoZ() {
        List<Product> sortByName = new ArrayList<>(dataProduct);
        sortByName.sort((na1,na2)->na1.getName().compareTo(na2.getName()));
        return sortByName;
    }

    @Override
    public List<Product> showProductByProfit() {
        List<Product> sortByProfit = new ArrayList<>(dataProduct);
        sortByProfit.sort((pro1, pro2) -> Double.compare(pro2.getProfit(), pro1.getProfit()));
        return sortByProfit;
    }





    @Override
    public List<Product> findProductByNameOrPrice(String obj) {
        List<Product> result1 = new ArrayList<>();
        for (Product p : dataProduct){
            if (p.getName().toLowerCase().contains(obj.toLowerCase())||String.valueOf(p.getImportPrice()).contains(obj)||String.valueOf(p.getExportPrice()).contains(obj)){
                result1.add(p);

            }
        }

        return result1;
    }



    @Override
    public boolean findAny(Predicate<Product> predicate) {
        for (Product x : dataProduct) {
            if (predicate.test(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void load() {
        try {
            File file = new File("products.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            dataProduct = (List<Product>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }catch (Exception  ex){
            dataProduct = new ArrayList<>();
        }
    }

    @Override
    public boolean save() {
        try {
            File file = new File("products.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(dataProduct);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }
}
