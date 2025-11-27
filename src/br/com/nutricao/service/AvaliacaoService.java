package br.com.nutricao.service;

import br.com.nutricao.model.AvaliacaoAntropometrica;
import br.com.nutricao.enums.*;
import br.com.nutricao.interfaces.CalculoNutricional;
import java.util.*;

/**
 * Service para gerenciar avaliações antropométricas
 * Implementa padrão Singleton e interface CalculoNutricional
 */
public class AvaliacaoService implements CalculoNutricional {
    
    // ===== SINGLETON =====
    private static AvaliacaoService instancia;
    
    public static AvaliacaoService getInstance() {
        if (instancia == null) {
            instancia = new AvaliacaoService();
        }
        return instancia;
    }
    
    // ===== ATRIBUTOS =====
    private List<AvaliacaoAntropometrica> avaliacoes;
    private Long proximoId;
    
    // Construtor PRIVADO (Singleton)
    public AvaliacaoService() {
        this.avaliacoes = new ArrayList<>();
        this.proximoId = 1L;
        System.out.println("✓ AvaliacaoService inicializado");
    }
    
    // ===== MÉTODOS PÚBLICOS =====
    
    public AvaliacaoAntropometrica salvar(AvaliacaoAntropometrica aval) {
        aval.setId(proximoId++);
        avaliacoes.add(aval);
        
        System.out.println("✓ Avaliação salva:");
        System.out.println("  Paciente ID: " + aval.getPacienteId());
        System.out.println("  Peso: " + aval.getPeso() + " kg");
        System.out.println("  Altura: " + aval.getAltura() + " m");
        System.out.println("  IMC: " + String.format("%.2f", aval.calcularIMC()));
        
        return aval;
    }
    
    public List<AvaliacaoAntropometrica> buscarPorPaciente(Long pacienteId) {
        return avaliacoes.stream()
            .filter(a -> a.getPacienteId().equals(pacienteId))
            .toList();
    }
    
    public AvaliacaoAntropometrica buscarUltimaPorPaciente(Long pacienteId) {
        List<AvaliacaoAntropometrica> lista = buscarPorPaciente(pacienteId);
        if (lista.isEmpty()) return null;
        return lista.get(lista.size() - 1);
    }
    
    // ===== IMPLEMENTAÇÃO DA INTERFACE CalculoNutricional =====
    
    @Override
    public double calcularIMC(double peso, double altura) {
        return peso / (altura * altura);
    }
    
    @Override
    public double calcularGET(double peso, double altura, int idade, Genero genero, NivelAtividade atividade) {
        double tmb;
        
        // Fórmula de Harris-Benedict
        if (genero == Genero.MASCULINO) {
            tmb = 88.36 + (13.4 * peso) + (4.8 * altura * 100) - (5.7 * idade);
        } else {
            tmb = 447.6 + (9.2 * peso) + (3.1 * altura * 100) - (4.3 * idade);
        }
        
        // GET = TMB × Fator de Atividade
        return tmb * atividade.getFator();
    }
}