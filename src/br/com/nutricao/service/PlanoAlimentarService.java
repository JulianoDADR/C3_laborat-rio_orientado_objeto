package br.com.nutricao.service;
import br.com.nutricao.model.PlanoAlimentar;
import java.util.*;

public class PlanoAlimentarService {
    private List<PlanoAlimentar> planos = new ArrayList<>();
    private Long proximoId = 1L;
    
    public PlanoAlimentar criar(PlanoAlimentar plano) {
        plano.setId(proximoId++);
        planos.add(plano);
        return plano;
    }
    
    public PlanoAlimentar buscarAtivo(Long pacienteId) {
        return planos.stream()
            .filter(p -> p.getPacienteId().equals(pacienteId) && p.isAtivo())
            .findFirst()
            .orElse(null);
    }
}