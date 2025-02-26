package com.shamil.model;

import java.io.Serializable;

public class CustomerRole implements Role , Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public String getRoleName() {
        return "CUSTOMER";
    }
}
