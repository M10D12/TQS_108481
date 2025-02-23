package com.exemplo;

public class Stock {
    private int quantity;
    private String label;

    public Stock(String label, int quantity){
        this.label=label;
        this.quantity=quantity;
    }

    public String getLabel(){
        return label;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setLabel(String label){
        this.label=label;
    }

    public void addQuantity(int quantity){
        this.quantity+=quantity;
    }
}