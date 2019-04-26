package com.example.budget2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Expense implements Parcelable {
    private double amount;
    private String date;
    private int category;
    private String note;
    private String subCategory;
    Integer id;

    public Expense(double amount, String date, int category, String note, String subCategory, Integer id) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = note;
        this.subCategory = subCategory;
        this.id = id;
    }

    protected Expense(Parcel in) {
        amount = in.readDouble();
        date = in.readString();
        category = in.readInt();
        note = in.readString();
        subCategory = in.readString();
        id = in.readInt();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public int getCategory() {
        return category;
    }
    public void setCategory(int category) {
        category = category;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getSubCategory() {
        return subCategory;
    }
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    public Integer getId() {return id;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(date);
        dest.writeInt(category);
        dest.writeString(note);
        dest.writeString(subCategory);
        dest.writeInt(id);
    }
}
