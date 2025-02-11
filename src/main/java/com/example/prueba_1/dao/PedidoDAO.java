package com.example.prueba_1.dao;

import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.model.Usuario;
import com.example.prueba_1.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public List<Pedido> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).list();
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
}
