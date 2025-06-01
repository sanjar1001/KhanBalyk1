package com.example.khanbalyk.controller;

import com.example.khanbalyk.entity.Booking;
import com.example.khanbalyk.entity.BookingReport;
import com.example.khanbalyk.entity.BookingToday;
import com.example.khanbalyk.repository.BookingRepository;
import com.example.khanbalyk.repository.BookingTotalRepository;
import com.example.khanbalyk.repository.PriceRepository;
import com.example.khanbalyk.service.BookingService;
import com.example.khanbalyk.service.PriceService;
import com.example.khanbalyk.service.TodayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/khan")
@RequiredArgsConstructor
public class AdminController {

    private final BookingRepository bookingRepository;
    private final BookingTotalRepository bookingTotalRepository;
    private final PriceRepository priceRepository;
    private final BookingService bookingService;
    private final PriceService priceService;
    private final TodayService todayService;

    @GetMapping("/list/bookings") //Получить все записи
    public List<Booking> home() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/add/booking") //Добавление Записи
    public ResponseEntity<?> addBooking(@RequestBody Booking bookingAdd) {
        String carNumber = bookingAdd.getCarNumber();
        String dayType = bookingAdd.getDayType();
        String fishingType = bookingAdd.getFishingType();
        int fishermanCount = bookingAdd.getFishermanCount();
        int pensioInvalidCount = bookingAdd.getPensioInvalidCount();
        int visitorCount = bookingAdd.getVisitorCount();
        String paymentType = bookingAdd.getPaymentType();

        try {
            Booking entity = bookingService.addBooking(carNumber,dayType,fishingType,fishermanCount,pensioInvalidCount,visitorCount,paymentType);
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Есть не заполненные поля", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Есть какое то ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/report/booking/month") //Получить отчет по диапазонам
    public ResponseEntity<?> reportBookingMonth(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam boolean fisherman,
            @RequestParam boolean pensio_invalid,
            @RequestParam boolean visitor
    ){

        try {
            BookingReport report = bookingService.reportBooking(startDate,endDate,fisherman,pensio_invalid,visitor);
            return new ResponseEntity<>(report, HttpStatus.CREATED);
        }catch (NullPointerException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


     }

    @PostMapping("/get/price") //Получить цену по параметрам
    public ResponseEntity<?> getPriceBooking(
            @RequestParam(name = "dayType") String dayType,
            @RequestParam(name = "fishingType") String fishingType,
            @RequestParam(name = "fishermanCount") int fishermanCount,
            @RequestParam(name = "pensioInvalidCount") int pensioInvalidCount,
            @RequestParam(name = "visitorCount") int visitorCount
    ) {
        try {
            Double getPrice = priceService.getPrice(dayType,fishingType,fishermanCount,pensioInvalidCount,visitorCount);
            return new ResponseEntity<>(getPrice, HttpStatus.CREATED);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Есть не заполненные поля", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Есть какое то ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/report/booking/today")
    public ResponseEntity<?> reportBookingToday(){
        BookingToday today = todayService.findBookingToday();
        return new ResponseEntity<>(today, HttpStatus.CREATED);
    }



}
