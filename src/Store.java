import entity.impl.Category;
import entity.impl.Product;
import service.CatetoryService;
import service.ProductService;
import service.impl.CategoryServiceImpl;
import service.impl.ProductServiceImpl;

import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CatetoryService catetoryService = new CategoryServiceImpl();
        ProductService productService = new ProductServiceImpl();
        boolean isExist = true;
        do {
            System.out.println("---------QUẢN LÝ KHO---------");
            System.out.println("1- Quản lý danh mục");
            System.out.println("2- Quản lý sản phẩm");
            System.out.println("3- Thoát");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    showBoardCategory(sc, catetoryService, productService);
                    break;
                case 2:
                    showBoardProduct(sc, productService, catetoryService);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Hãy nhập từ 1-3");
                    break;
            }
        } while (isExist);

    }

    public static void showBoardCategory(Scanner sc, CatetoryService catetoryService, ProductService productService) {
        boolean isExist = true;
        do {
            System.out.println("---------QUẢN LÝ DANH MỤC---------");
            System.out.println("1- Thêm mới danh mục");
            System.out.println("2- Cập nhật danh mục");
            System.out.println("3- Xóa danh mục");
            System.out.println("4- Tìm kiếm danh mục theo tên danh mục");
            System.out.println("5- Thống kê số lượng SP đang có trong danh mục");
            System.out.println("6- Hiển thị danh sách");
            System.out.println("7- Quay lại");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.print("Tổng số danh mục muốn nhập: ");
                    int numberCategory = Integer.parseInt(sc.nextLine());
                    for (int i = 0; i < numberCategory; i++) {
                        System.out.println("- Danh mục thứ " + (i + 1) + ":");
                        Category category = new Category();
                        category.inputData(sc);
                        catetoryService.add(category);
                    }
                    System.out.println("-> Thêm thành công");
                    break;
                case 2:
                    System.out.print("Nhập id danh mục cập nhật: ");
                    int idUpdate = Integer.parseInt(sc.nextLine());
                    catetoryService.update(idUpdate, sc);
                    catetoryService.save();
                    productService.updateCategoryName(idUpdate, catetoryService);
                    break;
                case 3:
                    try {                           // dễ nhập nhầm chữ và số nên bỏ try vào
                        System.out.print("Nhập id danh mục muốn xóa: ");
                        int idRemove = Integer.parseInt(sc.nextLine());
                        catetoryService.remove(idRemove, productService);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Nhập tên danh mục muốn tìm kiếm: ");
                    String nameFind = sc.nextLine();
                    for (Category x : catetoryService.findByName(nameFind)) {
                        x.displayData();
                    }
                    break;
                case 5:
                    catetoryService.statisticsByQuantity(productService);
                    break;
                case 6:
                    for (Category x : catetoryService.showCategory()) {
                        x.displayData();
                    }
                    break;
                case 7:
                    isExist = false;
                    break;
                default:
                    System.out.println("Hãy nhập từ 1-7");
            }
        } while (isExist);
    }

    public static void showBoardProduct(Scanner sc, ProductService productService, CatetoryService catetoryService) {
        boolean isExist = true;
        do {
            System.out.println("---------QUẢN LÝ SẢN PHẨM---------");
            System.out.println("1- Thêm sản phẩm");
            System.out.println("2- Cập nhật sản phẩm");
            System.out.println("3- Xóa sản phẩm");
            System.out.println("4- Hiển thị sản phẩm theo tên A-Z");
            System.out.println("5- Hiển thị sản phẩm theo lợi nhuận từ cao - thấp");
            System.out.println("6- Tìm kiếm sản phẩm");
            System.out.println("7- Quay lại");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.print("Tổng số sản phẩm muốn nhập: ");
                    int numberProduct = Integer.parseInt(sc.nextLine());
                    for (int i = 0; i < numberProduct; i++) {
                        System.out.println("- Sản phẩm thứ " + (i + 1) + ":");
                        Product productObj = new Product();
                        productObj.inputData(sc, catetoryService);
                        productService.add(productObj);
                    }
                    System.out.println("-> Thêm thành công");
                    break;
                case 2:
                    System.out.print("Nhập Id SP muốn cập nhật: ");
                    String idUpdate = sc.nextLine();
                    productService.update(idUpdate, sc, catetoryService);
                    productService.save();
                    break;
                case 3:
                    System.out.print("Nhập Id sản phẩm muốn xóa: ");
                    String idRemove = sc.nextLine();
                    productService.remove(idRemove);
                    productService.save();
                    break;
                case 4:
                    for (Product x : productService.showProductAtoZ()) {
                        x.displayData();
                    }
                    break;
                case 5:
                    for (Product x : productService.showProductByProfit()) {
                        x.displayData();
                    }
                    break;
                case 6:
                    System.out.print("Nhập từ khóa tìm kiếm: ");
                    String inputFind = sc.nextLine();
                    for (Product p : productService.findProductByNameOrPrice(inputFind)) {
                        p.displayData();
                    }
                    if (productService.findProductByNameOrPrice(inputFind).size() == 0) {
                        System.out.println("Không tìm thấy sản phẩm với từ khóa: " + inputFind);
                    }
                    break;
                case 7:
                    isExist = false;
                    break;
                default:
                    System.out.println("Hãy nhập từ 1-7");
            }
        } while (isExist);
    }
}
