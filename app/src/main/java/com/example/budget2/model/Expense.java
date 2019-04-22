package com.example.budget2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Expense implements Parcelable {
    private int amount;
    private Date date;
    private int Category;
    private String note;
    private String subCategory;

    public Expense(int amount, Date date, int category, String note, String subCategory) {
        this.amount = amount;
        this.date = date;
        Category = category;
        this.note = note;
        this.subCategory = subCategory;
    }

    protected Expense(Parcel in) {
        amount = in.readInt();
        Category = in.readInt();
        note = in.readString();
        subCategory = in.readString();
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
    public int getAmount() {
        return amount;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
    public int getCategory() {
        return Category;
    }
    public void setCategory(int category) {
        Category = category;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
        dest.writeInt(Category);
        dest.writeString(note);
        dest.writeString(subCategory);
    }
}
