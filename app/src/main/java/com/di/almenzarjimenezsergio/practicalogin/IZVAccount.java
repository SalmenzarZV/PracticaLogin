package com.di.almenzarjimenezsergio.practicalogin;

import android.os.Parcel;
import android.os.Parcelable;

public class IZVAccount implements Parcelable {
    String email, password;

    public IZVAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected IZVAccount(Parcel in) {
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<IZVAccount> CREATOR = new Creator<IZVAccount>() {
        @Override
        public IZVAccount createFromParcel(Parcel in) {
            return new IZVAccount(in);
        }

        @Override
        public IZVAccount[] newArray(int size) {
            return new IZVAccount[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
    }
}
