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

    public Reservation(String reservationId, Space space, Customer customer, LocalDateTime startTime, LocalDateTime endTime, double totalPrice) {
        this.reservationId = reservationId;
        this.space = space;
        this.customer = customer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationStatus = ReservationStatus.CONFIRMED;
        this.totalPrice = totalPrice;
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
