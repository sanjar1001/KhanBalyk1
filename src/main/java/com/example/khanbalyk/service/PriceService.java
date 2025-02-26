package com.example.khanbalyk.service;

import com.example.khanbalyk.entity.Price;
import com.example.khanbalyk.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceService {


    private final PriceRepository priceRepository;

//    // Получить цену по выбранным параметрам
//    public double getPrice(String dayType, String fishingType, int fishermanCount, int pensioInvalidCount, int visitorCount, int childrenCount) {
//        Price FisherManPrice = priceRepository.findByDayTypeAndFishingTypeAndVisitorType(dayType, fishingType, "Рыбак");
//        Price PensioInvalidPrice = priceRepository.findByDayTypeAndFishingTypeAndVisitorType(dayType, fishingType, "Пенсионер/Инвалид");
//        Price VisitorPrice = priceRepository.findByDayTypeAndFishingTypeAndVisitorType(dayType, fishingType, "Отдыхающий");
//        Price ChildrenPrice = priceRepository.findByDayTypeAndFishingTypeAndVisitorType(dayType, fishingType, "Дети");
//
//        double totalPrice = 0;
//
//        if (FisherManPrice != null){
//            totalPrice += FisherManPrice.getPrice() * fishermanCount; // 10000тг * 2 шт = 20000тг
//        }
//
//        if (PensioInvalidPrice != null){
//            totalPrice += PensioInvalidPrice.getPrice() * pensioInvalidCount; // 10000тг * 2 шт = 20000тг
//        }
//
//        if (VisitorPrice != null){
//            totalPrice += VisitorPrice.getPrice() * visitorCount; // 10000тг * 2 шт = 20000тг
//        }
//
//        if (ChildrenPrice != null){
//            totalPrice += ChildrenPrice.getPrice() * childrenCount; // 10000тг * 2 шт = 20000тг
//        }
//
//        return totalPrice;
//    }


    public double getPrice(String dayType, String fishingType, int fishermanCount, int pensioInvalidCount, int visitorCount) {
        // Один запрос, получаем все цены сразу
        System.out.println(dayType + " " + fishingType );

        List<Price> prices = priceRepository.findAllByDayTypeAndFishingType(dayType, fishingType);


        // Преобразуем список в Map, где ключ – тип посетителя
        Map<String, Double> priceMap = prices.stream()
                .collect(Collectors.toMap(Price::getVisitorType, Price::getPrice));

        // Вычисляем итоговую цену
        double totalPrice = 0;
        totalPrice += priceMap.getOrDefault("Рыбак", 0.0) * fishermanCount;
        totalPrice += priceMap.getOrDefault("Пенсионер/Инвалид", 0.0) * pensioInvalidCount;
        totalPrice += priceMap.getOrDefault("Отдыхающий", 0.0) * visitorCount;
        return totalPrice;
    }





    // Добавление новой цены
    public void addPrice(String dayType, String fishingType, String visitorType, double priceValue) {
        Price price = new Price();
        price.setDayType(dayType);
        price.setFishingType(fishingType);
        price.setVisitorType(visitorType);
        price.setPrice(priceValue);
        priceRepository.save(price);
    }
}
