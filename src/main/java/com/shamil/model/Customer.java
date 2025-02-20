package com.shamil.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{

    List<Reservation> reservations=new ArrayList<>();

    public Customer(String id,String username, String password) {
        super(id, username, password);
    }
    public Customer(){
        super();
    }
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public boolean cancelReservation(String reservationId) {
        return reservations.removeIf(r -> r.getReservationId().equals(reservationId));
    }
}
