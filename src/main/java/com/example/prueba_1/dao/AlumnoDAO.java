package com.example.prueba_1.dao;

import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AlumnoDAO {
    public void save(Alumno alumno){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(alumno);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Alumno> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Alumno", Alumno.class).list();
        }
    }

    public List<Alumno> getPaginated(int page, int offset) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Alumno> query = session.createQuery("FROM Alumno", Alumno.class);
            query.setFirstResult((page - 1) * offset);
            query.setMaxResults(offset);
            return query.list();
        }
    }

    public long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Long> query = session.createQuery("SELECT COUNT(a) FROM Alumno a", Long.class);
            return query.getSingleResult();
        }
    }

}
