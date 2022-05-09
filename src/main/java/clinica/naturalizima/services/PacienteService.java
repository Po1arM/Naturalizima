package clinica.naturalizima.services;

import clinica.naturalizima.entidades.Paciente;
import clinica.naturalizima.repositorio.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> getAllPacientes(){
        return pacienteRepository.findAll();
    }

    public Paciente getPacienteById(long id){
        return pacienteRepository.getPacienteById(id);
    }

    public List<Paciente> getPacienteByNombre(String nombre){
        return pacienteRepository.getPacientesByNombreContainsIgnoreCase(nombre);
    }

    public Paciente registrar(Paciente paciente){
        pacienteRepository.save(paciente);
        return paciente;
    }

    public void actualizar(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    public void borrar(Paciente paciente){
        pacienteRepository.delete(paciente);
    }


}
