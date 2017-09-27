package com.example.user.sport58;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



class WriteRegisteredData {
    //寫入會員資料
    public static void AddData(String account, String name, String password, String realname, String phone, String email)
    {
        DatabaseReference databaseReference = null;
        databaseReference= FirebaseDatabase.getInstance().getReference().child("member");
        SaveRegisteredData saveRegisteredData =new SaveRegisteredData(account, name, password, realname, phone, email);
        databaseReference.push().setValue(saveRegisteredData);
    }
////





}
