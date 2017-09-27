package com.example.user.sport58;


class SaveRegisteredData {
    private String account;
    private String name;
    private String password;
    private String realname;
    private String phone;
    private String email;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public SaveRegisteredData(String account, String name, String password, String realname, String phone, String email) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.realname = realname;
        this.phone = phone;
        this.email = email;
    }
}
