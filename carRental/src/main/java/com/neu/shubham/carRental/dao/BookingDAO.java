package com.neu.shubham.carRental.dao;

import com.neu.shubham.carRental.exception.CustomerException;
import com.neu.shubham.carRental.pojo.Booking;
import com.neu.shubham.carRental.pojo.Car;
import com.neu.shubham.carRental.pojo.Customer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingDAO extends DAO {

    public BookingDAO(){

    }
    public Booking createBooking(Booking booking) throws Exception {
        try {
            begin();
            getSession().save(booking);
            commit();
            close();
            return booking;
        } catch (HibernateException e) {
            rollback();
            throw new CustomerException("Cannot create Customer " + e.getMessage());
        }
    }

    public Booking getCustomerBooking(String customerEmail) throws Exception{

        Booking booking;
        try {
            Criteria criteria = getSession().createCriteria(Booking.class);
            Criterion c1 =  Restrictions.eq("customerEmail", customerEmail);
            Criterion c2 = Restrictions.eq("status", true);
            criteria.add(c1);
            criteria.add(c2);
            booking = (Booking) criteria.uniqueResult();
        }catch (HibernateException e) {
            rollback();
            throw new Exception("Car Not Found "+ customerEmail, e);
        }
        return booking;
    }

    public List<Booking> list(String customerEmail) throws Exception {
        try {
            Criteria criteria = getSession().createCriteria(Booking.class);
            Criterion c1 = Restrictions.eq("customerEmail", customerEmail);
            criteria.add(c1);
            List<Booking> list = criteria.list();
            return list;
        } catch (HibernateException e) {
            rollback();
            throw new Exception(e);
        }
    }

        public Booking getbyBookingId(int bookingId) throws Exception{

            Booking booking;
            try {
                Criteria criteria = getSession().createCriteria(Booking.class);
                Criterion c1 =  Restrictions.eq("id", bookingId);
                criteria.add(c1);
                booking = (Booking) criteria.uniqueResult();
            }catch (HibernateException e) {
                rollback();
                throw new Exception("Car Not Found "+ bookingId, e);
            }
            return booking;
        }

    public void updateBookingStatus(Booking booking) throws  Exception{
        try{
            begin();
            getSession().update(booking);
            commit();
            close();
        }catch (HibernateException e){
            rollback();
            throw new Exception("Cannot delete Car" + e.getMessage());
        }
    }

    public void deleteBooking(Booking booking) throws Exception{
        try{
            begin();
            getSession().delete(booking);
            commit();
            close();
        }catch (HibernateException e){
            rollback();
            throw new Exception("Cannot delete Car" + e.getMessage());
        }
    }



}
