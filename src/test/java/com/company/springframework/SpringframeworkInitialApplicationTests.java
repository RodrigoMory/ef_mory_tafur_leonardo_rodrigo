package com.company.springframework;

import com.company.springframework.model.Departamento;
import com.company.springframework.model.Empleado;
import com.company.springframework.repository.DepartamentoRepository;

import com.company.springframework.repository.EmpleadoRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class SpringframeworkInitialApplicationTests {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    void insertarDepartamento() {

        Departamento departamento = new Departamento();
        departamento.setDescripcion("Logistica");

        Departamento departamentoRegistrado = departamentoRepository.save(departamento);

        assertThat(departamentoRegistrado).isNotNull();
        assertThat(departamentoRegistrado.getId()).isPositive();
        assertThat(departamentoRegistrado.getDescripcion()).isNotEmpty();

    }

    @Test
    void insertarEmpleado() {

        Empleado empleado = new Empleado();

        // create value for attributes
        empleado.setNombre("Juan Perez");
        empleado.setSalario(new BigDecimal("1000.00"));
        empleado.setFechaIngreso(LocalDate.now());

        // create value for relationship
        Departamento departamento = new Departamento();
        departamento.setDescripcion("Sistemas");
        Departamento departamentoRegistrado = departamentoRepository.save(departamento);

        empleado.setDepartamento(departamentoRegistrado);

        Empleado empleadoRegistrado = empleadoRepository.save(empleado);

        assertThat(empleadoRegistrado).isNotNull();
        assertThat(empleadoRegistrado.getId()).isPositive();
        assertThat(empleadoRegistrado.getNombre()).isNotEmpty();
        assertThat(empleadoRegistrado.getSalario()).isNotNull();
        assertThat(empleadoRegistrado.getFechaIngreso()).isNotNull();
        assertThat(empleadoRegistrado.getDepartamento()).isNotNull();
        assertThat(empleadoRegistrado.getDepartamento().getId()).isPositive();
        assertThat(empleadoRegistrado.getDepartamento().getDescripcion()).isNotEmpty();
    }

    @Test
    void actualizarEmpleado() {

        // editar el empleado con id 1
        Empleado empleado = empleadoRepository.findById(1L).orElse(null);

        // modificar el nombre del empleado
        empleado.setNombre("Dany Cenas");

        // guardar los cambios
        empleadoRepository.save(empleado);

        // verificar que el nombre del empleado se actualizo
        Empleado empleadoActualizado = empleadoRepository.findById(1L).orElse(null);
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Dany Cenas");
    }

    @Test
    void eliminarEmpleado() {

        // eliminar el empleado con id 1
        empleadoRepository.deleteById(1L);

        // verificar que el empleado con id 1 ya no existe
        Empleado empleadoEliminado = empleadoRepository.findById(1L).orElse(null);
        assertThat(empleadoEliminado).isNull();

    }

    @Test
    void listarDepartamentos(){
        Iterable<Departamento> departamentos = departamentoRepository.findAll();
        assertThat(departamentos).isNotEmpty();
    }

    @Test
    void jpa_query_methods(){
        //Iterable<Empleado> empleados = empleadoRepository.findByNombre("Fernando Ruiz");
        //assertThat(empleados).isNotEmpty();

        //Iterable<Empleado> empleados = empleadoRepository.findByNombreContaining("Perez");
        //assertThat(((List<Empleado>) empleados).size()).isEqualTo(2);

        //Iterable<Empleado> empleados = empleadoRepository.findByNombreStartingWith("E");
        //assertThat(((List<Empleado>) empleados).size()).isEqualTo(2);

        //Iterable<Empleado> empleados = empleadoRepository.findBySalario(new BigDecimal("50000"));
        //assertThat(((List<Empleado>) empleados).size()).isEqualTo(2);

        //Iterable<Empleado> empleados = empleadoRepository.findBySalarioGreaterThan(new BigDecimal("58000"));
        //assertThat(((List<Empleado>) empleados).size()).isEqualTo(1);

        //Iterable<Empleado> empleados = empleadoRepository.findBySalarioGreaterThanEqual(new BigDecimal("55000"));
        //assertThat(((List<Empleado>) empleados).size()).isEqualTo(3);

        List<Empleado> empleados = empleadoRepository.findBySalarioBetween(new BigDecimal("50000"), new BigDecimal("55000"));
        //assertThat(empleados).hasSize(2);
        assertThat(empleados).size().isEqualTo(2);


        //List<Empleado> empleados = empleadoRepository.findFirst2BySalarioBetween(new BigDecimal("50000"), new BigDecimal("55000"));
        //assertThat(empleados).hasSize(2);
    }
}
