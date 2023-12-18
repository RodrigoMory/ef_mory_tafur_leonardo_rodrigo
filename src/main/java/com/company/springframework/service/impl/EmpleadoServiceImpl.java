package com.company.springframework.service.impl;

import com.company.springframework.model.Departamento;
import com.company.springframework.model.Empleado;
import com.company.springframework.repository.EmpleadoRepository;
import com.company.springframework.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        validarEmpleado(empleado);
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado) {
        validarEmpleado(empleado);
        if (!empleadoRepository.existsById(empleado.getId())) {
            throw new IllegalArgumentException("El empleado con el ID especificado no existe.");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminarEmpleado(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new IllegalArgumentException("El empleado con el ID especificado no existe.");
        }
        empleadoRepository.deleteById(id);
    }

    @Override
    public Empleado obtenerEmpleado(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isEmpty()) {
            throw new IllegalArgumentException("El empleado con el ID especificado no existe.");
        }
        return empleado.get();
    }

    @Override
    public List<Empleado> listarTodosLosEmpleados() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    private void validarEmpleado(Empleado empleado) {
        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del empleado no puede estar vacío.");
        }
        if (empleado.getSalario() == null || empleado.getSalario().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo.");
        }
        if (empleado.getFechaIngreso() == null) {
            throw new IllegalArgumentException("La fecha de ingreso no puede estar vacía.");
        }
        if (empleado.getFechaIngreso().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser futura.");
        }
        if (empleado.getDepartamento() == null || empleado.getDepartamento().getId() == null) {
            throw new IllegalArgumentException("El departamento asignado no es válido.");
        }
    }
}