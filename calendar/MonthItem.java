package com.example.caucse.calendar;

public class MonthItem {
    private int dayValue;

    public MonthItem(){

    }

    public MonthItem(int dayValue) {
        this.dayValue = dayValue;
    }

    public int getDay(){
        return dayValue;
    }

    public void setDay(int dayValue){
        this.dayValue = dayValue;
    }
}