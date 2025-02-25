package com.shamil.model;

public class CustomerRole implements Role{
    @Override
    public String getRoleName() {
        return "CUSTOMER";
    }
}
