package com.example.prueba_1.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.example.prueba_1.model.Alumno;
import com.example.prueba_1.model.Pedido;
import com.example.prueba_1.service.PedidoService;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class GenerarPDF {
    public static void generarFactura(Alumno alumno) {
        String destino = "facturas/factura_" + alumno.getId() + ".pdf";
        File directorio = new File("facturas");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        try {
            PdfWriter writer = new PdfWriter(destino);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Factura de Gastos - Último Mes"));
            document.add(new Paragraph("Alumno: " + alumno.getNombre()));
            document.add(new Paragraph("Fecha: " + LocalDate.now()));

            PedidoService pedidoService = new PedidoService();
            List<Pedido> pedidos = pedidoService.getAll();
            LocalDate mesPasado = LocalDate.now().minusMonths(1);

            List<Pedido> pedidosUltimoMes = pedidos.stream()
                    .filter(p -> p.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth() == mesPasado.getMonth())
                    .collect(Collectors.toList());

            float total = 0;
            Table table = new Table(3);
            table.addCell("Fecha");
            table.addCell("Bocadillo");
            table.addCell("Precio");

            for (Pedido pedido : pedidosUltimoMes) {
                table.addCell(pedido.getFecha().toString());
                table.addCell(pedido.getBocadillo().getNombre());
                table.addCell(String.format("%.2f€", pedido.getPrecio_pedido()));
                total += pedido.getPrecio_pedido();
            }

            document.add(table);
            document.add(new Paragraph("Total: " + String.format("%.2f€", total)));
            document.close();

            System.out.println("Factura generada: " + destino);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
