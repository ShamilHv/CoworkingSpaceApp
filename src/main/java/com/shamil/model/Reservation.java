package com.shamil.model;

import com.shamil.enums.ReservationStatus;

import java.time.LocalDateTime;

public class Reservation {
    private String reservationId;
    private Space space;
    private Customer customer;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus reservationStatus;
    private double totalPrice;

    public Reservation(String reservationId, Space space, Customer customer, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationId = reservationId;
        this.space = space;
        this.customer = customer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationStatus = ReservationStatus.CONFIRMED;

        //Calculating price based on the time reserved and hourly price
        long hours = java.time.Duration.between(startTime, endTime).toHours();
        this.totalPrice = hours * space.getPricePerHour();
    }

    public void cancelReservation() {
        this.reservationStatus = ReservationStatus.CANCELLED;
        this.space.setAvailable(true);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", space=" + space.getSpaceName() +
                ", customer=" + customer.getUsername() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", reservationStatus=" + reservationStatus +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public String getReservationId() {
        return reservationId;
    }

    public Space getSpace() {
        return space;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public ReservationStatus getStatus() {
        return reservationStatus;
    }
}
