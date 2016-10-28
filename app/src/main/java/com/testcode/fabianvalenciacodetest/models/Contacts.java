package com.testcode.fabianvalenciacodetest.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabian on 10/27/16.
 */

public class Contacts implements Parcelable {

    int id;
    String first_name;
    String last_name;
    String phone_number;
    String birth_date;
    String zip_code;

    protected Contacts(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        phone_number = in.readString();
        birth_date = in.readString();
        zip_code = in.readString();
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contacts(int id, String first_name, String last_name, String phone_number,
                            String birth_date, String zip_code){
        this.id=id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.phone_number=phone_number;
        this.birth_date=birth_date;
        this.zip_code=zip_code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(phone_number);
        dest.writeString(birth_date);
        dest.writeString(zip_code);
    }
}
