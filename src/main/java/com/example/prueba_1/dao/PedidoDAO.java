package com.example.prueba_1.dao;

import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.model.Usuario;
import com.example.prueba_1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PedidoDAO {
    public void save(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Pedido> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).list();
        }
    }

    public List<Pedido> getPedidosDeHoy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener la fecha actual como Date
            LocalDate hoy = LocalDate.now();
            Date inicioDia = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date finDia = Date.from(hoy.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

            // Consulta HQL para filtrar por rango de fechas
            String hql = "FROM Pedido p WHERE p.fecha BETWEEN :inicioDia AND :finDia";
            return session.createQuery(hql, Pedido.class)
                    .setParameter("inicioDia", inicioDia)
                    .setParameter("finDia", finDia)
                    .list();
        }
    }

    // Método para obtener los pedidos ordenados por precio de un alumno específico
    public List<Pedido> getPedidosOrdenadosPorPrecio(Long id_alumno, boolean ascendente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Pedido p WHERE p.alumno.id = :id_alumno ORDER BY p.precio_pedido " + (ascendente ? "ASC" : "DESC");
            return session.createQuery(hql, Pedido.class)
                    .setParameter("id_alumno", id_alumno)
                    .list();
        }
    }
    public List<Pedido> getPedidosPorTipo(Long id_alumno, String tipo){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Pedido p " +
                    "WHERE p.alumno.id = :id_alumno " +
                    "AND LOWER(p.bocadillo.tipo) = :tipo";
            return session.createQuery(hql, Pedido.class)
                    .setParameter("id_alumno", id_alumno)
                    .setParameter("tipo", tipo.toLowerCase())
                    .list();
            }
        }

    public List<Pedido> getPedidosPorFecha(Long id_alumno, boolean ascendente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Pedido p WHERE p.alumno.id = :id_alumno ORDER BY p.fecha " + (ascendente ? "ASC" : "DESC");
            return session.createQuery(hql, Pedido.class)
                    .setParameter("id_alumno", id_alumno)
                    .list();
        }
    }
}

