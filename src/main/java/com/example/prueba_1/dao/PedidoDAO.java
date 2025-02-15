package com.example.prueba_1.dao;

import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.model.Usuario;
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


    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener la fecha actual como Date
            LocalDate hoy = LocalDate.now();
            Date inicioDia = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date finDia = Date.from(hoy.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

            // Construcción de la consulta HQL con JOIN entre Pedido, Alumno y Bocadillo
            StringBuilder hql = new StringBuilder("FROM Pedido p ")
                    .append("JOIN p.alumno a ")
                    .append("JOIN p.bocadillo b ")
                    .append("WHERE p.fecha BETWEEN :inicioDia AND :finDia ");

            // Agregar condiciones dinámicas basadas en el HashMap de filtros
            if (filtros != null) {
                // Filtro por nombre de alumno
                if (filtros.containsKey("nombre")) {
                    hql.append("AND LOWER(a.nombre) LIKE :nombre ");
                }

                // Filtro por tipo de bocadillo
                if (filtros.containsKey("tipoBocadillo")) {
                    hql.append("AND LOWER(b.tipo) = :tipoBocadillo ");
                }

                // Filtro por nombre de curso
                if (filtros.containsKey("nombreCurso")) {
                    hql.append("AND LOWER(a.curso.nombre) LIKE :nombreCurso ");
                }
            }

            Query<Pedido> query = session.createQuery(hql.toString(), Pedido.class);

            // Asignar los valores a los parámetros de la consulta
            query.setParameter("inicioDia", inicioDia);
            query.setParameter("finDia", finDia);

            // Asignar los filtros adicionales
            if (filtros != null) {
                if (filtros.containsKey("nombre")) {
                    query.setParameter("nombre", "%" + filtros.get("nombre").toLowerCase() + "%");
                }
                if (filtros.containsKey("tipoBocadillo")) {
                    query.setParameter("tipoBocadillo", filtros.get("tipoBocadillo").toLowerCase());
                }
                if (filtros.containsKey("nombreCurso")) {
                    query.setParameter("nombreCurso", "%" + filtros.get("nombreCurso").toLowerCase() + "%");
                }
            }

            // Configurar paginación
            query.setFirstResult((page - 1) * offset);
            query.setMaxResults(offset);

            return query.list();
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

    public long count(HashMap<String, String> filtros) {
        // Separando para añadir de forma dinámica los filtros
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener la fecha actual como Date
            LocalDate hoy = LocalDate.now();
            Date inicioDia = Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date finDia = Date.from(hoy.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

            // Construcción de la consulta HQL para contar con JOIN entre Pedido, Alumno y Bocadillo
            StringBuilder hql = new StringBuilder("SELECT COUNT(p) FROM Pedido p ")
                    .append("JOIN p.alumno a ")
                    .append("JOIN p.bocadillo b ")
                    .append("WHERE p.fecha BETWEEN :inicioDia AND :finDia ");

            // Agregar condiciones dinámicas basadas en el HashMap de filtros
            if (filtros != null) {
                // Filtro por nombre de alumno
                if (filtros.containsKey("nombre")) {
                    hql.append("AND LOWER(a.nombre) LIKE :nombre ");
                }

                // Filtro por tipo de bocadillo
                if (filtros.containsKey("tipoBocadillo")) {
                    hql.append("AND LOWER(b.tipo) = :tipoBocadillo ");
                }

                // Filtro por nombre de curso
                if (filtros.containsKey("nombreCurso")) {
                    hql.append("AND LOWER(a.curso.nombre) LIKE :nombreCurso ");
                }
            }

            Query<Long> query = session.createQuery(hql.toString(), Long.class);

            // Asignar los valores a los parámetros de la consulta
            query.setParameter("inicioDia", inicioDia);
            query.setParameter("finDia", finDia);

            // Asignar los filtros adicionales
            if (filtros != null) {
                if (filtros.containsKey("nombre")) {
                    query.setParameter("nombre", "%" + filtros.get("nombre").toLowerCase() + "%");
                }
                if (filtros.containsKey("tipoBocadillo")) {
                    query.setParameter("tipoBocadillo", filtros.get("tipoBocadillo").toLowerCase());
                }
                if (filtros.containsKey("nombreCurso")) {
                    query.setParameter("nombreCurso", "%" + filtros.get("nombreCurso").toLowerCase() + "%");
                }
            }

            return query.getSingleResult();
        }
    }


}

