package com.example.prueba_1.service;

import com.example.prueba_1.dao.PedidoDAO;
import com.example.prueba_1.dao.UsuarioDAO;
import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.model.Usuario;
import com.example.prueba_1.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    public void save(Pedido pedido){
        // Validación antes de guardar
        if (pedido.getId() == null || pedido.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        pedidoDAO.save(pedido);
    }

    public void guardarOActualizar(Pedido mi_pedido, List<Pedido> lista_pedidos) {
        boolean existe_pedido = false;
        Pedido pedido_existente = null;
        if (!lista_pedidos.isEmpty()){
            for (Pedido pedido: lista_pedidos){
                LocalDate fechaPedido = pedido.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (fechaPedido.equals(LocalDate.now())) {
                    existe_pedido = true;
                    pedido_existente = pedido;
                    break;
                }
            }
        }

        if (existe_pedido) {
            // Si existe, actualizamos el pedido (asumiendo que 'pedido' tiene los nuevos datos)
            // Aquí deberías decidir qué atributos actualizar. Por ejemplo:
            pedido_existente.setBocadillo(mi_pedido.getBocadillo());
            pedido_existente.setPrecio_pedido(mi_pedido.getPrecio_pedido());

            // Guardamos el pedido actualizado
            pedidoDAO.update(pedido_existente);
        } else {
            // Si no existe, guardamos el nuevo pedido
            pedidoDAO.save(mi_pedido);
        }
    }

    public void eliminarPedido(Pedido pedido) {
        if (pedido != null && pedido.getId() != null) {
            pedidoDAO.delete(pedido);
        } else {
            throw new IllegalArgumentException("El pedido no puede ser nulo o sin ID.");
        }
    }

    public List<Pedido> getAll() {
        return pedidoDAO.getAll();
    }

    public List<Pedido> getPedidosDeHoy() {
        return pedidoDAO.getPedidosDeHoy();
    }

    public List<Pedido> getPaginated(int page, int offset, HashMap<String, String> filtros) {
        return pedidoDAO.getPaginated(page, offset, filtros);
    }

    public List<Pedido> getPedidosOrdenadosPorPrecio(Long id_alumno, boolean ascendente) {
        List<Pedido> historial_filtrado = pedidoDAO.getPedidosOrdenadosPorPrecio(id_alumno, ascendente);

        return historial_filtrado;

    }
    public List<Pedido> getPedidosPorTipo(Long id_alumno, String tipo) {
        List<Pedido> historial_filtrado = pedidoDAO.getPedidosPorTipo(id_alumno, tipo );

        return historial_filtrado;

    }
    public List<Pedido> getPedidosPorFecha(Long id_alumno, boolean ascendente) {
        List<Pedido> historial_filtrado = pedidoDAO.getPedidosPorFecha(id_alumno, ascendente);

        return historial_filtrado;

    }

    public long count(HashMap<String, String> filtros) {
        return pedidoDAO.count(filtros);
    }

    public void entregarPedido(Pedido pedido){
        pedidoDAO.update(pedido);
    }



}