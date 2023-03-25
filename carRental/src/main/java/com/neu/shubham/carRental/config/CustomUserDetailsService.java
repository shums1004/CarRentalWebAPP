package com.neu.shubham.carRental.config;


import com.neu.shubham.carRental.dao.CustomerDAO;
import com.neu.shubham.carRental.pojo.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;



@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private HttpServletRequest request;



    @Override
    public UserDetails loadUserByUsername(String username) {
//        logger.info("Reached loadUserByUsername : " + username);
        HttpSession session = request.getSession();
        Customer customer = null;
        try {
            customer = customerDAO.getCustomerByEmail(username);
        }
        catch(Exception e) {
            System.out.println("Exception: " +e.getMessage());
        }
//        logger.info("Loaded User:" + user);


        if (null == customer ) {
            throw new UsernameNotFoundException("Username not found");
        }


        CustomUserDetails customUserDetails = new CustomUserDetails(customer);
        session.setAttribute("customerName", customer);

        return customUserDetails;
    }

}
