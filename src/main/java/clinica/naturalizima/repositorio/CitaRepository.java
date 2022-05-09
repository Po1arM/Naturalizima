package clinica.naturalizima.repositorio;

import clinica.naturalizima.entidades.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita,Long> {
    List<Cita> findAllByIdPaciente(long id);
    Cita getCitaById(long id);
}
