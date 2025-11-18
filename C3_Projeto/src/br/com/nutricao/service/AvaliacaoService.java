package br.com.nutricao.service;
import br.com.nutricao.model.AvaliacaoAntropometrica;
import br.com.nutricao.enums.*;
import br.com.nutricao.interfaces.CalculoNutricional;
import java.util.*;

public class AvaliacaoService implements CalculoNutricional {
    private List<AvaliacaoAntropometrica> avaliacoes = new ArrayList<>();
    private Long proximoId = 1L;
    
    public AvaliacaoAntropometrica salvar(AvaliacaoAntropometrica aval) {
        aval.setId(proximoId++);
        avaliacoes.add(aval);
        return aval;
    }
    
    public List<AvaliacaoAntropometrica> buscarPorPaciente(Long pacienteId) {
        return avaliacoes.stream()
            .filter(a -> a.getPacienteId().equals(pacienteId))
            .toList();
    }
    
    @Override
    public double calcularIMC(double peso, double altura) {
        return peso / (altura * altura);
    }
    
    @Override
    public double calcularGET(double peso, double altura, int idade, Genero genero, NivelAtividade atividade) {
        double tmb;
        if (genero == Genero.MASCULINO) {
            tmb = 88.36 + (13.4 * peso) + (4.8 * altura * 100) - (5.7 * idade);
        } else {
            tmb = 447.6 + (9.2 * peso) + (3.1 * altura * 100) - (4.3 * idade);
        }
        return tmb * atividade.getFator();
    }
}
