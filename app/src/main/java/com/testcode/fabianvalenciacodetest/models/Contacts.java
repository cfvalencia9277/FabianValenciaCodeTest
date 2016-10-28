package com.testcode.fabianvalenciacodetest.models;

/**
 * Created by Fabian on 10/27/16.
 */

public class Contacts  {

    int id;
    String first_name;
    String last_name;
    String phone_number;
    String birth_date;
    String zip_code;

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

    public Contacts Contact(int id, String first_name, String last_name, String phone_number,
                            String birth_date, String zip_code){
        this.id=id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.phone_number=phone_number;
        this.birth_date=birth_date;
        this.zip_code=zip_code;
        return null;
    }


}
