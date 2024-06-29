package com.example;




public class User {
    private String userName;
    private String uuid;
    private String cellPhone;

    public User(String userName, String uuid, String cellPhone) {
        this.userName = userName;
        this.uuid = uuid;
        this.cellPhone = cellPhone;
    }

    // Getters and setters
    public String getuserName() { return userName; }
    public void setuserName(String userName) { this.userName = userName; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getcellPhone() { return cellPhone; }
    public void setcellPhone(String cellPhone) { this.cellPhone = cellPhone; }
}
