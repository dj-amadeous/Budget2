package com.example.budget2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setNumRecords(Integer numRecords) {
        this.numRecords = numRecords;
    }

    public String getUserID() {
        return userID;
    }

    public Integer getNumRecords() {
        return numRecords;
    }

    public User(String userID, Integer numRecords) {
        this.userID = userID;
        this.numRecords = numRecords;
    }

    private String userID;
    private Integer numRecords;

    protected User(Parcel in) {
        userID = in.readString();
        if (in.readByte() == 0) {
            numRecords = null;
        } else {
            numRecords = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        if (numRecords == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numRecords);
        }
    }
}
