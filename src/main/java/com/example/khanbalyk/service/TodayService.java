package com.example.khanbalyk.service;

import com.example.khanbalyk.entity.Booking;
import com.example.khanbalyk.entity.BookingReport;
import com.example.khanbalyk.entity.BookingToday;
import com.example.khanbalyk.entity.BookingTotal;
import com.example.khanbalyk.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodayService {
    private final BookingRepository bookingRepository;

    List<Booking> findBookingToday(LocalDate today) {
        return bookingRepository.findByBookingDateBetween(today, today);
    }

    // Отчет за сегодня
    public BookingToday findBookingToday() {


        //Получаем сегодняшний день
        LocalDate dateNow = LocalDate.now();
        List<Booking> findBookingToday = findBookingToday(dateNow);

        BigDecimal totalAmount = BigDecimal.ZERO;
        int fisherManCount = 0;
        int pensioInvalidCount = 0;
        int visitorCount = 0;
        BigDecimal paymentQR = BigDecimal.ZERO;
        BigDecimal paymentCash = BigDecimal.ZERO;

        //Циклом получаем данные
        for (Booking booking : findBookingToday) {
            fisherManCount += booking.getFishermanCount();
            pensioInvalidCount += booking.getPensioInvalidCount();
            visitorCount += booking.getVisitorCount();

            totalAmount = totalAmount.add(BigDecimal.valueOf(booking.getTotalPrice()));

            if ("Наличка".equalsIgnoreCase(booking.getPaymentType())){
                paymentCash = paymentCash.add(BigDecimal.valueOf(booking.getTotalPrice()));
            }

            if ("QR".equalsIgnoreCase(booking.getPaymentType())){
                paymentQR = paymentQR.add(BigDecimal.valueOf(booking.getTotalPrice()));
            }

        }

        System.out.println("Payment Cash" + paymentCash);
        return new BookingToday(totalAmount,fisherManCount,pensioInvalidCount, visitorCount,paymentQR, paymentCash);
    }



}
