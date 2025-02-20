package com.shamil.service;

import com.shamil.enums.ReservationStatus;
import com.shamil.model.Customer;
import com.shamil.model.Reservation;
import com.shamil.model.Space;
import com.shamil.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReservationService {
    private ReservationRepository reservationRepository;
    private SpaceService spaceService;

    public ReservationService(ReservationRepository reservationRepository, SpaceService spaceService) {
        this.reservationRepository = reservationRepository;
        this.spaceService = spaceService;
    }

    public Reservation makeReservation(Customer customer, String spaceId,
                                       LocalDateTime startTime, LocalDateTime endTime) {
        Space space = spaceService.getSpaceByName(spaceId);
        if (space == null || !space.isAvailable()) {
            return null;
        }

        String id = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(id, space, customer, startTime, endTime);

        space.setAvailable(false);
        spaceService.updateSpace(space);

        reservationRepository.addReservation(reservation);

        customer.addReservation(reservation);

        return reservation;
    }

    public boolean cancelReservation(String reservationId, Customer customer) {
        Reservation reservation = reservationRepository.getReservationById(reservationId);

        if (reservation != null &&
                reservation.getCustomer().getId().equals(customer.getId()) &&
                reservation.getStatus() == ReservationStatus.CONFIRMED) {

            reservation.cancelReservation();

            Space space = reservation.getSpace();
            space.setAvailable(true);
            spaceService.updateSpace(space);
            return true;
        }

        return false;

    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.getReservations();
    }
    public List<Reservation> getCustomerReservations(String customerId) {
        return reservationRepository.getReservationsByCustomerId(customerId);
    }
    public Reservation getReservationById(String reservationId) {
        return reservationRepository.getReservationById(reservationId);
    }

}
