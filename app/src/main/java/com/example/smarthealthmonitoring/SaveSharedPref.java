package com.example.smarthealthmonitoring;

public class SaveSharedPref {

    String name ,age,address,phoneno,email,id;

    public SaveSharedPref() {
    }

    public SaveSharedPref(String name, String age, String address, String phoneno, String email, String id) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneno = phoneno;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
