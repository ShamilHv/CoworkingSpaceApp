package com.shamil.repository;

import com.shamil.model.Admin;
import com.shamil.model.Customer;
import com.shamil.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public User addUser(User user){
        users.add(user);
        return user;
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User getUserByUsername(String username){
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers(){
        return users;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Customer) {
                customers.add((Customer) user);
            }
        }
        return customers;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Admin) {
                admins.add((Admin) user);
            }
        }
        return admins;
    }

    }
