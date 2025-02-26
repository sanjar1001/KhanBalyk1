package com.example.khanbalyk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@RequiredArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "car_number")
    private String carNumber;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "booking_date")
    @CreationTimestamp
    private LocalDate bookingDate;
    @Column(name = "fisherman_count")
    private int fishermanCount; // Количество рыбаков
    @Column(name = "pensioInvalid_Count")
    private int pensioInvalidCount; // Количество пенсионеров
    @Column(name = "visitor_count")
    private int visitorCount; // Количество отдыхающих
    @Column(name = "day_type")
    private String dayType; // Тип дня: Дневной, Вечерний,
    @Column(name = "fishing_type")
    private String fishingType; // Тип рыбалки: Будни, Выходной
    @Column(name = "paymentType")
    private String paymentType; // Тип рыбалки: Будни, Выходной
    @Column(name = "total_price")
    private Double totalPrice; // Итоговая сумма, вычисленная для этой заявки

}
