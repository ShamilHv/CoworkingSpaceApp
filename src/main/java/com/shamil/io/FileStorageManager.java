package com.shamil.io;

import com.shamil.model.*;
import com.shamil.repository.ReservationRepository;
import com.shamil.repository.SpaceRepository;
import com.shamil.repository.UserRepository;

import java.io.*;
import java.util.List;

public class FileStorageManager {
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_FILE = DATA_DIRECTORY + "/users.dat";
    private static final String SPACES_FILE = DATA_DIRECTORY + "/spaces.dat";
    private static final String RESERVATIONS_FILE = DATA_DIRECTORY + "/reservations.dat";

    public FileStorageManager() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void saveApplicationState(UserRepository userRepository,
                                     SpaceRepository spaceRepository,
                                     ReservationRepository reservationRepository) {
        saveUsers(userRepository.getAllUsers());
        saveSpaces(spaceRepository.getAllSpaces());
        saveReservations(reservationRepository.getReservations());
    }

    public void loadApplicationState(UserRepository userRepository,
                                     SpaceRepository spaceRepository,
                                     ReservationRepository reservationRepository) {
        List<User> users = loadUsers();
        if (users != null) {
            userRepository.addAll(users);
        }

        List<Space> spaces = loadSpaces();
        if (spaces != null) {
            spaceRepository.getAllSpaces().clear();
            spaceRepository.addAll(spaces);
        }

        List<Reservation> reservations = loadReservations();
        if (reservations != null) {
            for (Reservation reservation : reservations) {
                Customer customer = (Customer) userRepository.getUserById(reservation.getCustomer().getId());
                Space space = spaceRepository.getSpaceByName(reservation.getSpace().getSpaceName());

                if (customer != null && space != null) {
                    reservationRepository.addReservation(reservation);

                    customer.addReservation(reservation);
                }
            }
        }
    }

    private void saveUsers(List<User> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(USERS_FILE))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<User> loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(USERS_FILE))) {
            return (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
            return null;
        }
    }

    private void saveSpaces(List<Space> spaces) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(SPACES_FILE))) {
            out.writeObject(spaces);
        } catch (IOException e) {
            System.err.println("Error saving spaces: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Space> loadSpaces() {
        File file = new File(SPACES_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(SPACES_FILE))) {
            return (List<Space>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading spaces: " + e.getMessage());
            return null;
        }
    }

    private void saveReservations(List<Reservation> reservations) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(RESERVATIONS_FILE))) {
            out.writeObject(reservations);
        } catch (IOException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Reservation> loadReservations() {
        File file = new File(RESERVATIONS_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(RESERVATIONS_FILE))) {
            return (List<Reservation>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading reservations: " + e.getMessage());
            return null;
        }
    }
}