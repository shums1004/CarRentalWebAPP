package com.neu.shubham.carRental.controller;


import com.neu.shubham.carRental.dao.BookingDAO;
import com.neu.shubham.carRental.dao.CarDao;
import com.neu.shubham.carRental.pojo.Booking;
import com.neu.shubham.carRental.pojo.Car;
import com.neu.shubham.carRental.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Controller
public class ReturnCarController {

    @Autowired
    CarDao carDao;

    @Autowired
    BookingDAO bookingDAO;


    @GetMapping("/returnCar.htm")
    public String returnCarGet(ModelMap model, Booking booking, HttpSession session, HttpServletRequest request) throws Exception{

        request.getSession();
        Customer customer1 = (Customer)session.getAttribute("customer");

        BookingDAO bookingDAO = new BookingDAO();
        booking = bookingDAO.getCustomerBooking(customer1.getEmail());
        model.addAttribute("bookingDetails",booking);

        return "returnCar";
    }

    @PostMapping("/returnCar.htm")
    public String returnCarPost(HttpSession session, HttpServletRequest request,  @ModelAttribute("bookingDetails") Booking booking)throws Exception{

        request.getSession();
        Customer customer1 = (Customer)session.getAttribute("customer");
        String bookingId = request.getParameter("check");
        try{
            Booking booking1 = bookingDAO.getbyBookingId(Integer.parseInt(bookingId));

            booking1.setStatus(false);
            bookingDAO.updateBookingStatus(booking1);

            Car car = carDao.getCarByLicenseNo(booking1.getCarLicenseNo());
            car.setAvailable(true);
            carDao.updateCar(car);
            return "returnSuccessful";
        }
        catch(Exception e){
            return "errorPage";
        }




    }



}
