package entity.impl;

import entity.ICategory;
import exception.InputException;
import service.CatetoryService;
import service.impl.CategoryServiceImpl;

import java.io.Serializable;
import java.util.Scanner;

public class Category implements ICategory, Serializable {
    private int id;
    private String name;
    private String description;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(int id) throws Exception{
        if (id < 0)
            throw new InputException("-> Mã danh mục phải là số nguyên lớn hơn 0");
        CatetoryService catetoryService = new CategoryServiceImpl();
        if (catetoryService.findAny(x->x.getId() == id))
            throw new InputException("-> Mã danh mục đã tồn tại");
//        if (this.id == 0){
//
//        } else {
//            if (catetoryService.findAny(x -> x.getId() == id && x.getName() != this.name))
//                throw new InputException("-> Mã danh mục đã tồn tại");
//        }
        this.id = id;
    }

    public void setName(String name) throws Exception{
        if (name.length()<6 || name.length()>30)
            throw new InputException("-> Tên danh mục từ 6-30 ký tự");
        CatetoryService catetoryService = new CategoryServiceImpl();
        if (this.name == null){
            if (catetoryService.findAny(x->x.getName().equalsIgnoreCase(name)))
                throw new InputException("-> Tên danh mục đã tồn tại");
        } else {
            if (catetoryService.findAny(x -> x.getName().equalsIgnoreCase(name)&& x.getId()!=this.id))
                throw new InputException("-> Tên danh mục đã tồn tại");
        }
        this.name = name;
    }

    public void setDescription(String description) throws Exception{
        if (description.isBlank())
            throw new InputException("-> Mô tả danh mục không được bỏ trống");
        this.description = description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void inputData(Scanner sc) {
        if (this.id == 0){
            inputCategoryId(sc);
        }
        inputCategoryName(sc);
        inputCategoryDescription(sc);
        inputCategoryStatus(sc);
    }

    public void inputCategoryId(Scanner sc){
        do {
            try{
                System.out.print("Nhập mã danh mục: ");
                setId(Integer.parseInt(sc.nextLine()));
                break;
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }while (true);
    }
    public void inputCategoryName(Scanner sc){
        do {
            try{
                System.out.print("Nhập tên danh mục: ");
                setName(sc.nextLine());
                break;
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }while (true);
    }
    public void inputCategoryDescription(Scanner sc){
        do {
            try {
                System.out.print("Nhập mô tả danh mục: ");
                setDescription(sc.nextLine());
                break;
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }while (true);
    }
    public void inputCategoryStatus(Scanner sc){
        do {
            System.out.print("Nhập trạng thái danh mục: ");
            String checkStatus = sc.nextLine();
            if (checkStatus.equals("true") || checkStatus.equals("false")){
                setStatus(Boolean.parseBoolean(checkStatus));
                break;
            } else {
                System.out.println("-> Chỉ được nhập true/ false");
            }
        }while (true);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã danh mục: %-10d || Tên danh mục: %-15s || Mô tả: %-20s  || Trạng thái: %s \n",
                this.id, this.name, this.description, this.status?"Hoạt động":"Không hoạt động");
    }
}
