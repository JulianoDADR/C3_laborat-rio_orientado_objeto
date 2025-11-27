package br.com.nutricao.service;
import br.com.nutricao.model.DiarioAlimentar;
import java.time.LocalDate;
import java.util.*;

public class DiarioService {
    private List<DiarioAlimentar> registros = new ArrayList<>();
    private Long proximoId = 1L;
    
    public DiarioAlimentar registrar(DiarioAlimentar diario) {
        diario.setId(proximoId++);
        registros.add(diario);
        return diario;
    }
    
    public List<DiarioAlimentar> buscarPorData(Long pacienteId, LocalDate data) {
        return registros.stream()
            .filter(d -> d.getPacienteId().equals(pacienteId) && 
                        d.getDataHora().toLocalDate().equals(data))
            .toList();
    }
}