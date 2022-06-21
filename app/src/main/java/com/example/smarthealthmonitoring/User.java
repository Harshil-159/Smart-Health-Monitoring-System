package com.example.smarthealthmonitoring;

public class User {
    public  String name,email,age,mobile,Password,address,token;
    public User(){

    }
    public User(String name,String age,String address,String email,String mobile,String Password,String token){
        this.name=name;
        this.email=email;
        this.age=age;
        this.mobile=mobile;
        this.Password=Password;
        this.address=address;
        this.token=token;
    }
}
