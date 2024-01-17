package entity;

import service.CatetoryService;

import java.util.Scanner;

public interface IProduct {
    float MIN_INTEREST_RATE =0.2F;
    void inputData(Scanner sc, CatetoryService catetoryService);
    void displayData();
    void calProfit();
}
