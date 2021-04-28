package com.company;

public class Calculator {
    private DiscountStrategy discountStrategy;

    public Calculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public int calcaulate(List<Item> item){
        int sum=0;
        for (Item item : items){
            sum += discountStrategy.getdiscountPrice(item);
        }
        return sum;
    }
}
