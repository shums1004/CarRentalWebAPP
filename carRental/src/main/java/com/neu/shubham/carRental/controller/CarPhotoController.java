package com.neu.shubham.carRental.controller;

import com.neu.shubham.carRental.dao.CarDao;
import com.neu.shubham.carRental.dao.CarPhotoDAO;
import com.neu.shubham.carRental.pojo.Car;
import com.neu.shubham.carRental.pojo.CarPhoto;
import com.neu.shubham.carRental.pojo.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Controller
public class CarPhotoController {

    @Autowired
    CarDao carDao;

    @Autowired
    CarPhotoDAO carPhotoDAO;


//    Logger logger = LoggerFactory.getLogger(AddCarController.class);

    @GetMapping("/addCarPhoto.htm")
    public String uploadPhotoGet(ModelMap model, HttpSession session, HttpServletRequest request){

        if (manageSession(session)) return "home";

        request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        CarDao carDao = new CarDao();
        try{
            List<Car> myCarsList = carDao.ownersCarList(customer.getEmail());
            if(myCarsList.isEmpty()){
                return "noCarsCo";
            }
            model.addAttribute("photoCarList", myCarsList);
        }
        catch (Exception e){
            System.out.println("Error" + e);
            return "errorPage2";
        }
        return "addCarPhotos";
    }

    @PostMapping("/addCarPhoto.htm")
    public String uploadPhotoPost(HttpSession session , @RequestParam("carImage") CommonsMultipartFile imagefile, HttpServletRequest request) throws Exception{

        String uploadImage = request.getParameter("uploadImage");
        Customer customer = (Customer) session.getAttribute("customer");

        if(uploadImage != null){
            CarPhoto carPhoto = new CarPhoto();
            carPhoto.setCustomerId(customer.getId());

            String  carLicenseNo = request.getParameter("check");
            Car car = carDao.getCarByLicenseNo(carLicenseNo);

            String fileName = car.getLicenseNo() +  "_img" + System.currentTimeMillis() + ".jpg";
            byte[] data = imagefile.getBytes();
            String path = session.getServletContext().getRealPath("/")+ "images/"+fileName;
            carPhoto.setImageName(fileName);

            carPhoto.setCarId(car.getId());

            try{
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(data);
                fos.close();
                System.out.println("File Uploaded successfully");
            }catch (IOException e) {
                e.printStackTrace();
                System.out.println("File Upload error");
            }
            try{
                carPhotoDAO.create(carPhoto);
            }catch (Exception e){
                System.out.println("Exception: " +e.getMessage());
            }
        }

        return "carAdded";
    }
    private boolean manageSession(HttpSession session) {
        Customer customer = null;
        if(session.getAttribute("customer") != null) {
            customer = (Customer)session.getAttribute("customer");
        }

        if(customer == null) {
            System.out.println("");
            return true;
        }
        if(customer.getCategory().equals("customer")){
            return true;
        }

        return false;
    }


}
