package entity.impl;

import entity.IProduct;
import exception.InputException;
import service.CatetoryService;
import service.ProductService;
import service.impl.CategoryServiceImpl;
import service.impl.ProductServiceImpl;

import java.io.Serializable;
import java.util.Scanner;

public class Product implements IProduct, Serializable {
    private String id;
    private String name;
    private double importPrice;
    private double exportPrice;
    private double profit;
    private String description;
    private boolean status;
    private int categoryId;
    private String categoryName;

    public Product() {
    }

    public Product(String id, String name, double importPrice, double exportPrice, double profit, String description, boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
    }

    public Product(String id, String name, double importPrice, double exportPrice, double profit, String description, boolean status, String categoryName) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.description = description;
        this.status = status;
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setId(String id) throws Exception {
        if (id == null || id.length() != 4)
            throw new InputException("-> Mã SP phải gồm 4 ký tự");
        if (!id.startsWith("P"))
            throw new InputException("-> Mã SP phải bắt đầu là 'P'");
        ProductService productService = new ProductServiceImpl();
        if (this.id == null) {
            if (productService.findAny(x -> x.getId().equalsIgnoreCase(id)))
                throw new InputException("-> Mã SP đã tồn tại");
        } else {
            if (productService.findAny(x -> x.getId().equalsIgnoreCase(id) && x.getName() != this.name))
                throw new InputException("-> Mã SP đã tồn tại");
        }
        this.id = id;
    }

    public void setName(String name) throws Exception {
        if (name == null || name.length() < 6 || name.length() > 30)
            throw new InputException("-> Tên SP phải gồm 6-30 ký tự");
        ProductService productService = new ProductServiceImpl();
        if (productService.findAny(x -> x.getName().equalsIgnoreCase(name)))
            throw new InputException("-> Tên SP đã tồn tại");

        this.name = name;
    }

    public void setImportPrice(double importPrice) throws Exception {
        if (importPrice <= 0)
            throw new InputException("-> Giá nhập phải lớn hơn 0");
        this.importPrice = importPrice;
    }

    public void setExportPrice(double exportPrice) throws Exception {
        double checkExportPrice = this.importPrice * (1 + MIN_INTEREST_RATE);
        if (exportPrice < checkExportPrice)
            throw new InputException("-> Giá bán phải lớn hơn giá nhập ít nhất 0.2 lần (" + checkExportPrice + ")");
        this.exportPrice = exportPrice;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setDescription(String description) throws Exception {
        if (description.isBlank())
            throw new InputException("-> Phải nhập mô tả SP");
        this.description = description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void inputData(Scanner sc, CatetoryService catetoryService) {
        if (this.id == null) {
            inputProductId(sc);
        }
        inputProductName(sc);
        inputProductImportPrice(sc);
        inputProductExportPrice(sc);
        calProfit();
        inputProductDescription(sc);
        inputProductStatus(sc);
        inputProductCategoryName(sc, catetoryService);
    }

    public void inputProductId(Scanner sc) {
        do {
            try {
                System.out.print("Nhập mã SP: ");
                setId(sc.nextLine());
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
    }

    public void inputProductName(Scanner sc) {
        do {
            try {
                System.out.print("Nhập tên SP: ");
                setName(sc.nextLine());
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
    }

    public void inputProductImportPrice(Scanner sc) {
        do {
            try {
                System.out.print("Giá nhập SP: ");
                setImportPrice(Double.parseDouble(sc.nextLine()));
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
    }

    public void inputProductExportPrice(Scanner sc) {
        do {
            try {
                System.out.print("Nhập giá bán SP: ");
                setExportPrice(Double.parseDouble(sc.nextLine()));
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
    }

    public void inputProductDescription(Scanner sc) {
        do {
            try {
                System.out.print("Nhập mô tả SP: ");
                setDescription(sc.nextLine());
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
    }

    public void inputProductStatus(Scanner sc) {
        do {
            System.out.print("Nhập trạng thái SP: ");
            String checkStatus = sc.nextLine();
            if (checkStatus.equals("true") || checkStatus.equals("false")) {
                setStatus(Boolean.parseBoolean(checkStatus));
                break;
            } else {
                System.out.println("-> Chỉ được nhập true/ false");
            }
        } while (true);
    }

    public void inputProductCategoryName(Scanner sc, CatetoryService catetoryService) {
        boolean checkId = false;
        System.out.println("Danh sách các danh mục");
        for (Category x : catetoryService.showCategory()) {
            x.displayData();
        }
        System.out.print("Nhập mã danh mục Id: ");
        int idCategory = Integer.parseInt(sc.nextLine());
        for (Category x : catetoryService.showCategory()) {
            if (x.getId() == idCategory) {
                setCategoryName(x.getName());
                setCategoryId(idCategory);
                checkId = true;
                break;
            } else {
                checkId = false;
            }
        }
        if (!checkId) {
            System.out.println("-> Không tìm thấy mã danh mục Id: " + idCategory);
            inputProductCategoryName(sc, catetoryService);
        }

    }

    @Override
    public void displayData() {
        System.out.printf("- Mã SP: %s || Tên SP: %s || Giá nhập: %-6s || Giá bán: %-6s || Lợi nhuận: %s \n",
                this.id, this.name, this.importPrice, this.exportPrice, this.profit);
        System.out.printf("  Mô tả SP: %-15s || Trạng thái: %-22s || <Id-%d> Tên danh mục: %s\n",
                this.description, this.status ? "Còn hàng" : "Ngừng kinh doanh",this.categoryId, this.categoryName);
    }

    @Override
    public void calProfit() {
        this.profit = this.exportPrice - this.importPrice;
    }
}
