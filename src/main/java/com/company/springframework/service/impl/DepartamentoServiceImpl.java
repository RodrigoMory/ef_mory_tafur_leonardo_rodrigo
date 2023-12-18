package com.company.springframework.service.impl;

import com.company.springframework.model.Departamento;
import com.company.springframework.repository.DepartamentoRepository;
import com.company.springframework.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    public Departamento guardarDepartamento(Departamento departamento) {
        validarDepartamento(departamento);
        return departamentoRepository.save(departamento);
    }

    @Override
    public Departamento actualizarDepartamento(Departamento departamento) {
        validarDepartamento(departamento);
        if (!departamentoRepository.existsById(departamento.getId())) {
            throw new IllegalArgumentException("El departamento con el ID especificado no existe.");
        }
        return departamentoRepository.save(departamento);
    }

    @Override
    public void eliminarDepartamento(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new IllegalArgumentException("El departamento con el ID especificado no existe.");
        }
        departamentoRepository.deleteById(id);
    }

    @Override
    public Departamento obtenerDepartamento(Long id) {
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        if (departamento.isEmpty()) {
            throw new IllegalArgumentException("El departamento con el ID especificado no existe.");
        }
        return departamento.get();
    }

    @Override
    public List<Departamento> listarTodosLosDepartamentos() {
        return (List<Departamento>) departamentoRepository.findAll();
    }

    private void validarDepartamento(Departamento departamento) {
        if (departamento.getDescripcion() == null || departamento.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del departamento no puede estar vacía.");
        }       
    }
}
