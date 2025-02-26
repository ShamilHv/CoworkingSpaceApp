package com.shamil.service;

import com.shamil.exception.AuthenticationException;
import com.shamil.exception.UserAlreadyExistsException;
import com.shamil.model.Admin;
import com.shamil.model.Customer;
import com.shamil.model.User;
import com.shamil.repository.UserRepository;

import java.util.UUID;

public class UserService {
    private UserRepository userRepository;
    private User currentUser;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        ///Initializing some default users for testing. If not already exists
        if (userRepository.getAllUsers().isEmpty()) {
            initializeDefaultUsers();
        }    }
    private void initializeDefaultUsers() {
        Admin admin = new Admin();
        admin.setId(UUID.randomUUID().toString());
        admin.setUsername("admin");
        admin.setPassword("admin123");
        userRepository.addUser(admin);

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setUsername("user");
        customer.setPassword("user123");
        userRepository.addUser(customer);
    }


    public User login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        }
        throw new AuthenticationException("Invalid username or password");
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Customer registerCustomer(String username, String password) {
        if (userRepository.getUserByUsername(username) != null) {
            throw new UserAlreadyExistsException("User with username '" + username + "' already exists");
        }

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setUsername(username);
        customer.setPassword(password);

        userRepository.addUser(customer);
        return customer;
    }

}
