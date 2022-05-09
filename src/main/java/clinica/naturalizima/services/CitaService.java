package clinica.naturalizima.services;

import clinica.naturalizima.entidades.Cita;
import clinica.naturalizima.entidades.Paciente;
import clinica.naturalizima.repositorio.CitaRepository;
import clinica.naturalizima.repositorio.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaService {
    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> getAllCitas(){
        return citaRepository.findAll();
    }

    public List<Cita> getCitasByIdPaciente(long idPaciente){
        return citaRepository.findAllByIdPaciente(idPaciente);
    }
    public Cita getCitaById(long id){
        return citaRepository.getCitaById(id);
    }

    @Transactional
    public Cita registrar(Cita cita){
        citaRepository.save(cita);
        return cita;
    }

    public void actualizar(Cita cita){
        citaRepository.save(cita);
    }

    @Transactional
    public void borrar(Cita cita){
        citaRepository.delete(cita);
    }

}
