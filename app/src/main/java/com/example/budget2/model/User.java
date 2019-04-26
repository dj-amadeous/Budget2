package com.example.budget2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String profession;
    private String name;
    private Integer utaID;

    public User(String profession, String name, Integer utaID) {
        this.profession = profession;
        this.name = name;
        this.utaID = utaID;
    }

    protected User(Parcel in) {
        profession = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            utaID = null;
        } else {
            utaID = in.readInt();
        }
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUtaID() {
        return utaID;
    }

    public void setUtaID(Integer utaID) {
        this.utaID = utaID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profession);
        dest.writeString(name);
        if (utaID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(utaID);
        }
    }
}
