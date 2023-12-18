package com.company.springframework.service;

import com.company.springframework.model.Departamento;

import java.util.List;

public interface DepartamentoService {

    Departamento guardarDepartamento(Departamento departamento);

    Departamento actualizarDepartamento(Departamento departamento);

    void eliminarDepartamento(Long id);

    Departamento obtenerDepartamento(Long id);

    List<Departamento> listarTodosLosDepartamentos();
}

