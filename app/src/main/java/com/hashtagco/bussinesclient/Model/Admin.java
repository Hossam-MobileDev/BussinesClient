package com.hashtagco.bussinesclient.Model;

import java.io.Serializable;

public class Admin implements Serializable {

    private String Password;

    private String email;

    public int getSecretno() {
        return secretno;
    }

    public void setSecretno(int secretno) {
        this.secretno = secretno;
    }

    private int secretno;

    public Admin(String email, String password , int secretno ) {
        this.Password = password;
        this.email = email;
        this.secretno=secretno;
    }


public Admin(){

}

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }






    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
