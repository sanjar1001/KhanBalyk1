package com.example.khanbalyk.repository;

import com.example.khanbalyk.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    // Поиск цены по параметрам
    List<Price> findAllByDayTypeAndFishingType(String dayType, String fishingType);
}
