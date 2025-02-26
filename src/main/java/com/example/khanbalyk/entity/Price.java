package com.example.khanbalyk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Price")
@Getter
@Setter
@RequiredArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "day_type")
    private String dayType; // Тип дня (Будний/Выходной)
    @Column(name = "fishing_type")
    private String fishingType; // Тип рыбалки (Дневная/Ночная)
    @Column(name = "visitor_type")
    private String visitorType; // Тип посетителя (Рыбак/Отдыхающий/Пенсионер и т.д.)
    @Column(name = "price")
    private double price; // Цена
}
