package com.neu.shubham.carRental.controller;

import com.neu.shubham.carRental.dao.CarDao;
import com.neu.shubham.carRental.pojo.Car;
import com.neu.shubham.carRental.pojo.Customer;
import com.neu.shubham.carRental.validator.AddCarValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class AddCarController {


    @Autowired
    Customer customer;


    @GetMapping("/addCar.htm")
    public String addCarGET(ModelMap model, Car car,HttpSession session, HttpServletRequest request){

        try{
            System.out.println("getcar");
            model.addAttribute("car",car);
            return"addCar";

        }
        catch(Exception e){
            System.out.println("No user Logged in"+e);
            return"login-form";
        }

    }

    @PostMapping("/addCar.htm")
    public String addCarPost(HttpSession session , @ModelAttribute("car") Car car, SessionStatus status, HttpServletRequest request, BindingResult result) throws Exception{

        AddCarValidator addCarValidator = new AddCarValidator();
        addCarValidator.validate(car,result);

        if(result.hasErrors()){
            return "addCar";
        }

        Customer customer1 = (Customer) session.getAttribute("customer");
        CarDao carDao = new CarDao();
        List<Car> carList = carDao.list();
        System.out.println("post car");

//        for(int i=0; i<carList.size();i++){
//            if(request.getParameter("licenseNo").equals(carList.get(i).getLicenseNo()))
//                request.setAttribute("error", "Invalid License Plate Number");
//            return "errorPage2";
//        }

            try{
//               userCars.setUserId(customer.getId());
//               userCars.setCarId(car.getId());
//               userCarsDAO.createUserCars(userCars)
//                List<Car> list1 = customer.getCarsList();
//                list1.add(car);
//                customer.setCarsList(list1);
//                customerDAO.updateCustomer(customer);
                car.setEmail(customer1.getEmail());
//                car.setCustomer(customer1);
                car.setAvailable(true);
                carDao.createCar(car);

            }
            catch (Exception e){
                System.out.println("Exception:" + e.getMessage() );
            }
        status.setComplete();
        return "carAdded";
    }

    @GetMapping("/viewMyCars.htm")
    public String viewMyCars(ModelMap model,HttpSession session, HttpServletRequest request) throws Exception {

        request.getSession();
        Customer customer1 = (Customer) session.getAttribute("customer");
        CarDao carDao = new CarDao();
        try{
           List<Car> mycars = carDao.ownersCarList(customer1.getEmail());
           if(mycars.isEmpty()){
               return "noCarsCo";
           }
            model.addAttribute("myCarList", mycars);
        }
        catch (Exception e){
            System.out.println("Error" + e);
            return "errorPage2";
        }
        return "viewMyCars";
    }

    @PostMapping("/viewMyCars.htm")
    public String deleteMyCar(@ModelAttribute("myCarList") Car car,HttpSession session, HttpServletRequest request) throws Exception{

        CarDao carDao = new CarDao();
        Car car1 =  carDao.getCarByLicenseNo(request.getParameter("check"));
        if(request.getParameter("check").isEmpty()){
            request.setAttribute("error", "Car Not Selected");
            return "errorPage2";
        }
        try{
            carDao.deleteCar(car1);
        }
        catch (Exception e){
            System.out.println("ERROR" + e);
            return "errorPage2";
        }

        return "carDeleted";
    }

    @GetMapping("/updateMyCar.htm")
    public String updateMyCarGet(ModelMap model,HttpSession session, HttpServletRequest request ) throws Exception{
        request.getSession();
        Customer customer2 = (Customer) session.getAttribute("customer");
        CarDao carDao = new CarDao();
        try{
            List<Car> myCarsList = carDao.ownersCarList(customer2.getEmail());
            if(myCarsList.isEmpty()){
                return "noCarsCo";
            }
            model.addAttribute("carsList", myCarsList);
        }
        catch (Exception e){
            System.out.println("Error" + e);
            return "errorPage2";
        }
        return "updateCarDetails";
    }

    @PostMapping("/updateMyCar.htm")
    public String updateMyCarPost(@ModelAttribute("myCarList") Car car,HttpSession session, HttpServletRequest request) throws Exception{

        CarDao carDao = new CarDao();
        Car car1 =  carDao.getCarByLicenseNo(request.getParameter("check"));

        if(request.getParameter("check").isEmpty()){
            request.setAttribute("error", "Car Not Selected");
            return "errorPage2";
        }

        double updatedPrice = Double.parseDouble(request.getParameter("newPrice"));
        try{
            car1.setPrice(updatedPrice);
            carDao.updateCar(car1);
        }
        catch (Exception e){
            System.out.println("Error" + e);
            return "errorPage2";
        }

        return "updateSuccess";
    }

}
