package clinica.naturalizima.controller;

import clinica.naturalizima.entidades.Cita;
import clinica.naturalizima.entidades.Paciente;
import clinica.naturalizima.services.CitaService;
import clinica.naturalizima.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CitaService citaService;

    @GetMapping("/")
    public String paginaInicial(){

        return "redirect:/pacientes";
    }

    @GetMapping("/pacientes")
    public String mostrarPacientes(Model model){
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        model.addAttribute("pacientes",pacientes);
        return "pacientes";
    }

    @GetMapping("/pacientes/ver")
    public String verPaciente(@PathParam("id") long id, Model model){
        Paciente paciente = pacienteService.getPacienteById(id);
        List<Cita> citas = citaService.getCitasByIdPaciente(id);
        model.addAttribute("paciente",paciente);
        model.addAttribute("citas",citas);

        return "verPaciente";
    }

    @GetMapping("/pacientes/crear")
    public String loadCrearPaciente(Model model){
        model.addAttribute("tipo","crear");
        model.addAttribute("paciente",new Paciente());
        return "crear_modPaciente";
    }

    @PostMapping("/pacientes/buscar")
    public String buscarPaciente(WebRequest request, Model model){
        List<Paciente> pacientes = pacienteService.getPacienteByNombre(request.getParameter("nombre"));
        model.addAttribute("pacientes",pacientes);

        return "pacientes";
    }
    @GetMapping("/pacientes/modificar")
    public String loadPaciente(@PathParam("id") long id, Model model){
        Paciente paciente = pacienteService.getPacienteById(id);
        model.addAttribute("paciente",paciente);
        model.addAttribute("tipo","mod");
        return "crear_modPaciente";
    }

    @PostMapping("/pacientes/guardar")
    public String registrarPaciente(WebRequest request){
        Paciente paciente = new Paciente();
        paciente.setNombre(request.getParameter("nombre"));
        paciente.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento")));
        paciente.setTelefono(request.getParameter("telefono"));
        paciente.setPiel(request.getParameter("piel"));
        paciente.setEnfermedadActual(request.getParameter("enfermedadActual"));
        paciente.setAntecedentesPersonales(request.getParameter("antecedentesPersonales"));
        paciente.setAntecedentesFamiliares(request.getParameter("antecedentesFamiliares"));
        paciente.setTratamientosPrevios(request.getParameter("tratamientosPrevios"));
        pacienteService.registrar(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/pacientes/eliminar")
    public String eliminarPaciente(@PathParam("id") long id){
        Paciente paciente = pacienteService.getPacienteById(id);
        pacienteService.borrar(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/citas")
    public String mostrarCitas(Model model){
        List<Cita> citas = citaService.getAllCitas();
        model.addAttribute("citas",citas);
        return "citas";
    }

    @PostMapping("/citas/buscar")
    public String buscarCitasPorPaciente(WebRequest request,Model model){
        String nombre = request.getParameter("nombre");
        List<Cita> citas = new ArrayList<Cita>();
        List<Paciente> pacientes = pacienteService.getPacienteByNombre(nombre);
        for (Paciente paciente: pacientes) {
            List<Cita> aux = citaService.getCitasByIdPaciente(paciente.getId());
            for (Cita cita: aux) {
                citas.add(cita);
            }
        }
        model.addAttribute("citas",citas);
        return "citas";
    }

    @GetMapping("/citas/ver")
    public String verCita(@PathParam("id") long id, Model model){
        Cita cita = citaService.getCitaById(id);
        Paciente paciente = pacienteService.getPacienteById(cita.getIdPaciente());
        System.out.println(paciente.getNombre());
        model.addAttribute("paciente",paciente);
        model.addAttribute("cita",cita);

        return "verCita";
    }
    @GetMapping("/citas/crear")
    public String loadRegistrarCita(@PathParam("id") long id, Model model){
        model.addAttribute("tipo","crear");
        model.addAttribute("paciente",pacienteService.getPacienteById(id));
        return "crear_modCita";
    }

    @GetMapping("/citas/mod")
    public String loadCita(@PathParam("id") long id, Model model){
        Cita cita = citaService.getCitaById(id);
        Paciente paciente = pacienteService.getPacienteById(id);
        model.addAttribute("paciente",paciente);
        model.addAttribute("cita",cita);
        model.addAttribute("tipo","mod");
        return "crear_modCita";
    }

    @PostMapping("/citas/guardar")
    public String registrarCita(WebRequest request){
        Cita aux = new Cita();
        aux.setFecha(LocalDate.parse(request.getParameter("fecha")));
        aux.setTratamiento(request.getParameter("tratamiento"));
        aux.setObservaciones(request.getParameter("observaciones"));
        aux.setDiagnsotico( request.getParameter("diagnostico"));
        aux.setIdPaciente(Long.parseLong(request.getParameter("idPaciente")));
        aux.setNombrePaciente(pacienteService.getPacienteById(aux.getIdPaciente()).getNombre());
        citaService.registrar(aux);
        return "redirect:/citas";
    }

    @GetMapping("/citas/eliminar")
    public String eliminarCita(@PathParam("id") long id){
        Cita cita = citaService.getCitaById(id);
        citaService.borrar(cita);
        return "redirect:/citas";
    }

    @GetMapping("/prueba")
    public String prueba(){
        for(int i = 0; i < 15 ; i ++){
            Paciente aux = new Paciente();
            aux.setNombre("Paciente " + i);
            aux.setFechaNacimiento(LocalDate.of(2000,8,29));
            aux.setAntecedentesFamiliares("Una vaina rara");
            aux.setAntecedentesPersonales("Una vaina aun mas rara");
            aux.setTratamientosPrevios("Nada");
            aux.setPiel("Grasosa");
            aux.setEnfermedadActual("Una vaina rara");
            aux.setTelefono("198-167-6841");
            aux = pacienteService.registrar(aux);
        }
        return "redirect:/pacientes";
    }

}
