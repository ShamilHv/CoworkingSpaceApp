package com.shamil.model;

import java.io.Serializable;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    public Admin() {
        super();
        setRole(new AdminRole());
    }

    public Admin(String id, String username, String password) {
        super(id, username, password, new AdminRole());
    }

}
