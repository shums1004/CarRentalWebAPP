package com.neu.shubham.carRental.dao;

import com.neu.shubham.carRental.pojo.Car;
import com.neu.shubham.carRental.pojo.CarPhoto;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class CarPhotoDAO extends DAO {

    public CarPhoto create(CarPhoto carPhoto) throws Exception {
        try {
            //save user object in the database
            begin();
            getSession().save(carPhoto);
            commit();
            close();

            return carPhoto;
        } catch (HibernateException e) {
            rollback();
            throw new Exception("Error while adding Photo: " + e.getMessage());
        }
    }

    public List<CarPhoto> getCarPhotos(int carId) throws Exception {
        try {
            begin();
            Criteria criteria = getSession().createCriteria(CarPhoto.class);
            Criterion c1  = Restrictions.eq("carId", carId);
            criteria.add(c1);
            List<CarPhoto> list = criteria.list();

            commit();
            close();
            return list;

        } catch (HibernateException e) {
            rollback();
            throw new Exception("Car Picture Not Found " + carId, e);
        }
    }
}
