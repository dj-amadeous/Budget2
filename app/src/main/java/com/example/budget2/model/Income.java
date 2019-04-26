package com.example.budget2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Income implements Parcelable {
    private double amount;
    private String date;
    private int category; //0-one-time    1-monthly
    private String note;
    Integer id;

    public Income(double amount, String date, int category, String note, Integer id) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.note = note;
        this.id = id;
    }

    protected Income(Parcel in) {
        amount = in.readDouble();
        category = in.readInt();
        note = in.readString();
        id = in.readInt();
    }

    public static final Creator<Income> CREATOR = new Creator<Income>() {
        @Override
        public Income createFromParcel(Parcel in) {
            return new Income(in);
        }

        @Override
        public Income[] newArray(int size) {
            return new Income[size];
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
    public Integer getId() {return id;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeInt(category);
        dest.writeString(note);
        dest.writeInt(id);
    }
}
