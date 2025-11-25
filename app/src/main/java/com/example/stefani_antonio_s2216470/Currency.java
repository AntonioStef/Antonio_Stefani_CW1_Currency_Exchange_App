package com.example.stefani_antonio_s2216470;

import android.util.EventLogTags;

public class Currency
{
private String title; // e.g GBP to Euros
private String description; // 1 GPP= 1.16 EUR

private double rate; // the exchange rate
private String CurrencyCode; //e.g EUR ,GBP
private String date; // the date and time of the app

    //constructor
    public Currency(String title, String description, double rate, String targetCurrencyCode) {
        this.title = title;
        this.description = description;
        this.rate = rate;
        this.CurrencyCode = targetCurrencyCode;
    }



// getters and setters
    public void setRate(Double rate){
        this.rate=rate;
    }
    public double getRate(){
        return rate;

    }

    public void setTitle(String title) {
        this.title=title;
    }

    public String getCurrencyCode(){
        return CurrencyCode;
    }
    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode=CurrencyCode;

    }

    public void setDescription(String Description) {

        this.description=Description;
    }

    public String getDescription(){
        return description;
    }




}
