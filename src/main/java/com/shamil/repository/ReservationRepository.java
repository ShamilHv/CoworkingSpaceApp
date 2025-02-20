package com.shamil.repository;

import com.shamil.model.Customer;
import com.shamil.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private List<Reservation> reservations = new ArrayList<>();

    public Reservation addReservation(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
    public Reservation getReservationById(String reservationId) {
        return reservations.stream().filter(r->r.getReservationId().
                equals(reservationId)).findFirst().orElse(null);
    }

    public List<Reservation> getReservationsByCustomerId(String customerId) {
        List<Reservation> customerReservations = new ArrayList<>();

        reservations.stream().filter(r->r.getCustomer().getId().
                equals(customerId)).forEach(customerReservations::add);
        return customerReservations;
    }

    public boolean removeReservationById(String reservationId) {
        Reservation reservation = getReservationById(reservationId);
        if(reservation != null) {
            reservations.remove(reservation);
            return true;
        }else{
            return false;
        }
    }

}
