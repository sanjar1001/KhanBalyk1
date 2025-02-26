package com.example.khanbalyk.service;

import com.example.khanbalyk.entity.Booking;
import com.example.khanbalyk.entity.BookingReport;
import com.example.khanbalyk.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PriceService priceService;



    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    } //Получить всех записи с базы данных


    public Booking addBooking(String car_number, String dayType, String fishingType, int fishermanCount, int pensioInvalidCount, int visitorCount, String paymentType) {


        double totalPrice = priceService.getPrice(dayType, fishingType, fishermanCount, pensioInvalidCount, visitorCount);

        Booking booking = new Booking();
        booking.setCarNumber(car_number);
        booking.setDayType(dayType);
        booking.setFishermanCount(fishermanCount);
        booking.setPensioInvalidCount(pensioInvalidCount);
        booking.setFishingType(fishingType);
        booking.setVisitorCount(visitorCount);
        booking.setPaymentType(paymentType);
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);

    }

    public BookingReport reportBooking(String startDate, String endDate, boolean includeFishermen, boolean includeVisitor, boolean includePensio_invalid) {
        // Преобразуем строки в LocalDate
        LocalDate start = LocalDate.parse(startDate); // из String(int) в LocalDate
        LocalDate end = LocalDate.parse(endDate); // из String(int) в LocalDate

        // Извлекаем все бронирования в указанном диапазоне
        List<Booking> bookings = bookingRepository.findByBookingDateBetween(start, end); //От метода на репозитории

        // Подсчет оборота
        System.out.println();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalFishermenAmount = 0;
        int totalVisitorAmount = 0;
        int totalPensio_invalid = 0;

        for (Booking booking : bookings) {
            System.out.println("Booking: " + booking); // Проверяем содержимое бронирований
            if (includeFishermen) {
                totalFishermenAmount += booking.getFishermanCount(); // Количество рыбаков
            }
            if (includeVisitor) {
                totalVisitorAmount += booking.getVisitorCount(); // Количество гостей
            }
            if (includePensio_invalid) {
                totalPensio_invalid += booking.getPensioInvalidCount(); // Количество Пенсионер и Инвалид
            }

            if (booking.getTotalPrice() != null){
                totalAmount = totalAmount.add(BigDecimal.valueOf(booking.getTotalPrice()));
            }

        }




        return new BookingReport(totalAmount, totalFishermenAmount, totalVisitorAmount,totalPensio_invalid);
    }



}
