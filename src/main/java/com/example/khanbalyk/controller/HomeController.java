package com.example.khanbalyk.controller;

import com.example.khanbalyk.entity.Booking;
import com.example.khanbalyk.entity.BookingReport;
import com.example.khanbalyk.entity.BookingToday;
import com.example.khanbalyk.entity.BookingTotal;
import com.example.khanbalyk.repository.BookingRepository;
import com.example.khanbalyk.repository.PriceRepository;
import com.example.khanbalyk.service.BookingService;
import com.example.khanbalyk.service.PriceService;
import com.example.khanbalyk.service.TodayService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final PriceRepository priceRepository;
    private final PriceService priceService;
    private final TodayService todayService;

    @GetMapping("/") //Главная страница
    public String home(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        LocalDate today = LocalDate.now();
        System.out.println(today);
        return "home";
    }


    @GetMapping(value = "/addFisher") // Страница Для Добовление
    public String addFisherPage(){
        return "add";
    }

    @PostMapping("/addFisher")
    public String addFisherPage(
            @RequestParam(name = "car_number") String carNumber,
            @RequestParam(name = "dayType") String dayType,
            @RequestParam(name = "fishingType") String fishingType,
            @RequestParam(name = "fishermanCount") int fishermanCount,
            @RequestParam(name = "pensioInvalidCount") int pensioInvalidCount,
            @RequestParam(name = "visitorCount") int visitorCount,
            @RequestParam(name = "paymentType") String paymentType,
            RedirectAttributes redirectAttributes
    ) {
        Booking booking = bookingService.addBooking(carNumber, dayType, fishingType, fishermanCount, pensioInvalidCount, visitorCount, paymentType);


        redirectAttributes.addFlashAttribute("successMessage", "Заявка успешно добавлена!");

        return "redirect:/getPrice";
    }



    @GetMapping( value = "/report")
    public String analyticBooking(){
        return "report";
    }


    @PostMapping("/report")//Расчет по месяцам
    public String analyzeBooking(@RequestParam(name = "start_date") String startDate, //Принимает это значение
                                 @RequestParam(name = "end_date") String endDate,//Принимает это значение
                                 @RequestParam(name = "include_fishermen", required = false, defaultValue = "false") boolean includeFishermen,
                                 @RequestParam(name = "include_pensio_invalid", required = false, defaultValue = "false") boolean includePensio_invalid,
                                 @RequestParam(name = "include_visitor", required = false, defaultValue = "false") boolean includeVisitor,
                                 Model model //Это для отправки на html
    ) {


        BookingReport report = bookingService.reportBooking(startDate, endDate, includeFishermen, includeVisitor, includePensio_invalid);


        // Передаем данные на страницу
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("includeFishermen", includeFishermen);
        model.addAttribute("includeVisitor", includeVisitor);
        model.addAttribute("includePensio_invalid", includePensio_invalid);
        model.addAttribute("totalAmount", report.getTotalAmount());
        model.addAttribute("totalFishermenAmount", report.getTotalFishermenAmount());
        model.addAttribute("totalPensio_invalid", report.getTotalPensioInvalidAmount());
        model.addAttribute("totalVisitorAmount", report.getTotalVisitorAmount());

        return "report"; // Возвращаем имя страницы с результатами
    }



    // Получить цену по параметрам
    @PostMapping("/getPrice")
    public String getPrice(
            @RequestParam(name = "car_number") String car_number,
            @RequestParam(name = "dayType") String dayType,
            @RequestParam(name = "fishingType") String fishingType,
            @RequestParam(name = "fishermanCount") int fishermanCount,
            @RequestParam(name = "pensioInvalidCount") int pensioInvalidCount,
            @RequestParam(name = "paymentType") String paymentType,
            @RequestParam(name = "visitorCount") int visitorCount,
            Model model) {

        System.out.println(dayType + " " + fishingType );

        double price = priceService.getPrice(dayType, fishingType, fishermanCount,pensioInvalidCount,visitorCount);
        model.addAttribute("car_number", car_number);
        model.addAttribute("dayType", dayType);
        model.addAttribute("fishingType", fishingType);
        model.addAttribute("fishermanCount", fishermanCount);
        model.addAttribute("pensioInvalidCount", pensioInvalidCount);
        model.addAttribute("paymentType", paymentType);
        model.addAttribute("visitorCount", visitorCount);

        model.addAttribute("price", price);
        return "add"; // представление для отображения цены
    }

    @GetMapping("/getPrice")
    public String getPrice(){
        return "add";
    }


    // Добавление новой цены
    @PostMapping("/addPrice")
    public String addPrice(
            @RequestParam String dayType,
            @RequestParam String fishingType,
            @RequestParam String visitorType,
            @RequestParam double priceValue) {

        priceService.addPrice(dayType, fishingType, visitorType, priceValue);

        return "redirect:/"; // редирект на главную страницу
    }

    @GetMapping("/today")
    public String today(
            Model model
    ){
        BookingToday report = todayService.findBookingToday();
        model.addAttribute("report", report);
        return "today";
    }











}
