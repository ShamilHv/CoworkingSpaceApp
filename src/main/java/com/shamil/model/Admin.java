package com.shamil.model;

public class Admin extends User {

    public Admin() {
        super();
        setRole(new AdminRole());
    }

    public Admin(String id, String username, String password) {
        super(id, username, password, new AdminRole());
    }

}
