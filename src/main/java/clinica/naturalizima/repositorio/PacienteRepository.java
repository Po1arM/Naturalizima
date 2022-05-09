package clinica.naturalizima.repositorio;

import clinica.naturalizima.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    Paciente getPacienteById(long id);
    List<Paciente> getPacientesByNombreContainsIgnoreCase(String nombre);
}
