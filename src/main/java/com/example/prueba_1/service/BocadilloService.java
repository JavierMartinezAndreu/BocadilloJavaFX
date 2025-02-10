package com.example.prueba_1.service;

import com.example.prueba_1.dao.BocadilloDAO;
import com.example.prueba_1.model.Alergeno;
import com.example.prueba_1.model.Bocadillo;
import com.example.prueba_1.model.Usuario;

import java.util.List;

public class BocadilloService {

    private final BocadilloDAO bocadilloDAO = new BocadilloDAO();

    public void save(Bocadillo bocadillo){
        // Validación antes de guardar
        if (bocadillo.getId() == null || bocadillo.getId() == 0) {
            throw new IllegalArgumentException("El ID no puede estar vacío.");
        }

        bocadilloDAO.save(bocadillo);
    }

    public List<Bocadillo> getAll() {
        return bocadilloDAO.getAll();
    }

    public String obtenerAlergenosComoString(Bocadillo bocadillo) {
        List<Alergeno> alergenos = bocadillo.getAlergenos();

        if (alergenos == null || alergenos.isEmpty()) {
            return "No contiene alérgenos";
        }

        StringBuilder alergenos_string = new StringBuilder("ALERGENOS: ");

        for (int i = 0; i < alergenos.size(); i++) {
            alergenos_string.append(alergenos.get(i).getNombre());
            if (i < alergenos.size() - 1) {
                alergenos_string.append(", ");
            }
        }

        return alergenos_string.toString();
    }
}