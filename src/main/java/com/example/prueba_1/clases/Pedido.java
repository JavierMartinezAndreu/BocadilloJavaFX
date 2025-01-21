package com.example.prueba_1.clases;

import java.util.Date;

public class Pedido {
    //Atributos del pedido
    private String id_alumno;
    private String id_bocadillo;
    private String precio_pagado;
    private Date fecha;
    private Date fecha_recogida;

    //Constructores
    public Pedido(String id_alumno, String id_bocadillo, String precio_pagado, Date fecha, Date fecha_recogida) {
        this.id_alumno = id_alumno;
        this.id_bocadillo = id_bocadillo;
        this.precio_pagado = precio_pagado;
        this.fecha = fecha;
        this.fecha_recogida = fecha_recogida;
    }

    //Getters y Setters
    public String getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(String id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getId_bocadillo() {
        return id_bocadillo;
    }

    public void setId_bocadillo(String id_bocadillo) {
        this.id_bocadillo = id_bocadillo;
    }

    public String getPrecio_pagado() {
        return precio_pagado;
    }

    public void setPrecio_pagado(String precio_pagado) {
        this.precio_pagado = precio_pagado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha_recogida() {
        return fecha_recogida;
    }

    public void setFecha_recogida(Date fecha_recogida) {
        this.fecha_recogida = fecha_recogida;
    }
}