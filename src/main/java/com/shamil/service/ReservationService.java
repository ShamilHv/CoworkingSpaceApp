package com.shamil.service;

import com.shamil.enums.ReservationStatus;
import com.shamil.exception.InvalidReservationException;
import com.shamil.exception.ResourceNotFoundException;
import com.shamil.exception.TimeConflictException;
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
        if (hours <= 0) {
            throw new InvalidReservationException("Reservation must be for at least 1 hour");
        }
        return hours * space.getPricePerHour();
    }


    public Reservation makeReservation(Customer customer, String spaceName,
                                       LocalDateTime startTime, LocalDateTime endTime) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end times cannot be null");
        }
        if (!startTime.isBefore(endTime)) {
            throw new InvalidReservationException("End time must be after start time");
        }
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new InvalidReservationException("Start time must be in the future");
        }

        Space space = spaceService.getSpaceByName(spaceName);

        if (hasTimeConflict(space, startTime, endTime)) {
            throw new TimeConflictException("Time conflict with existing reservation");
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

        if (reservation == null) {
            throw new ResourceNotFoundException("Reservation with ID '" + reservationId + "' not found");
        }

        if (!reservation.getCustomer().getId().equals(customer.getId())) {
            throw new InvalidReservationException("You can only cancel your own reservations");
        }

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new InvalidReservationException("Only confirmed reservations can be cancelled");
        }

        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        return true;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getReservations();
    }
    public List<Reservation> getCustomerReservations(String customerId) {
        return reservationRepository.getReservationsByCustomerId(customerId);
    }
    public Reservation getReservationById(String reservationId) {
        Reservation reservation = reservationRepository.getReservationById(reservationId);
        if (reservation == null) {
            throw new ResourceNotFoundException("Reservation with ID '" + reservationId + "' not found");
        }
        return reservation;
    }


}
