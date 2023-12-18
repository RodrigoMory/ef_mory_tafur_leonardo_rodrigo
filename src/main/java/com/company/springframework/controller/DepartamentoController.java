package com.company.springframework.controller;

import com.company.springframework.model.Departamento;
import com.company.springframework.model.Empleado;
import com.company.springframework.service.DepartamentoService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departamento")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<Departamento> listarTodosLosDepartamentos() {
        return departamentoService.listarTodosLosDepartamentos();
    }

    @PostMapping
    public Departamento guardarDepartamento(@RequestBody Departamento departamento) {
        return departamentoService.guardarDepartamento(departamento);
    }

    @GetMapping("/reporte01")
    public ResponseEntity<byte[]> visualizarReporte01() throws JRException {

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departamentoService.listarTodosLosDepartamentos());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("titulo", "Reporte de Departamentos");
        JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/reportes/reporte01.jasper", parameters, dataSource);

        byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("departamentos.pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(reporte);

    }

    @GetMapping("/reporte02")
    public ResponseEntity<byte[]> descargarReporte01() throws JRException {
        JasperReport report = JasperCompileManager.compileReport("src/main/resources/reportes/reporte01.jrxml");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departamentoService.listarTodosLosDepartamentos());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("titulo", "Reporte de Departamentos");
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

        byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("departamentos.pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(reporte);
    }

}
