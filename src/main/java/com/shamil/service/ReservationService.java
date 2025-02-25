package com.shamil.service;

import com.shamil.enums.ReservationStatus;
import com.shamil.model.Customer;
import com.shamil.model.Reservation;
import com.shamil.model.Space;
import com.shamil.repository.ReservationRepository;

import java.time.Duration;
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

    private double calculateTotalPrice(Space space, LocalDateTime startTime, LocalDateTime endTime) {
        long hours = Duration.between(startTime, endTime).toHours();
        return hours * space.getPricePerHour();
    }

    public Reservation makeReservation(Customer customer, String spaceId,
                                       LocalDateTime startTime, LocalDateTime endTime) {
        Space space = spaceService.getSpaceByName(spaceId);
        if (space == null) {
            return null;
        }

        if (hasTimeConflict(space, startTime, endTime)) {
            System.out.println("Time-conflict, please choose a different time interval");
            return null;
        }

        double totalPrice = calculateTotalPrice(space, startTime, endTime);

        String id = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(id, space, customer, startTime, endTime, totalPrice);

        reservationRepository.addReservation(reservation);

        customer.addReservation(reservation);

        return reservation;
    }

    private boolean hasTimeConflict(Space space, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> allReservations = reservationRepository.getReservations();

        return allReservations.stream()
                .filter(res -> res.getSpace().getSpaceId().equals(space.getSpaceId()))
                .filter(res -> res.getReservationStatus() == ReservationStatus.CONFIRMED)
                .anyMatch(res ->
                        (startTime.isBefore(res.getEndTime()) && endTime.isAfter(res.getStartTime()))
                );
    }

    public boolean cancelReservation(String reservationId, Customer customer) {
        Reservation reservation = reservationRepository.getReservationById(reservationId);

        if (reservation != null &&
                reservation.getCustomer().getId().equals(customer.getId()) &&
                reservation.getStatus() == ReservationStatus.CONFIRMED) {

            reservation.setReservationStatus(ReservationStatus.CANCELLED);
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
