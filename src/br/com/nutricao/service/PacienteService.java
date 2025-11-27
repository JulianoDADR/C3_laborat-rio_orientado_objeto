package br.com.nutricao.service;

import br.com.nutricao.model.Paciente;
import br.com.nutricao.exception.*;
import java.util.*;

/**
 * Service para gerenciar pacientes
 * Implementa padrão Singleton para garantir dados persistentes
 */
public class PacienteService {
    
    // ===== SINGLETON =====
    private static PacienteService instancia;
    
    public static PacienteService getInstance() {
        if (instancia == null) {
            instancia = new PacienteService();
        }
        return instancia;
    }
    
    // ===== ATRIBUTOS =====
    private List<Paciente> pacientes;
    private Long proximoId;
    
    // Construtor PRIVADO (Singleton)
    public PacienteService() {
        this.pacientes = new ArrayList<>();
        this.proximoId = 1L;
        System.out.println("✓ PacienteService inicializado");
    }
    
    // ===== MÉTODOS PÚBLICOS =====
    
    public Paciente cadastrar(Paciente p) throws DadosInvalidosException {
        System.out.println("\n=== CADASTRANDO PACIENTE ===");
        
        // Validações
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            throw new DadosInvalidosException("Nome é obrigatório");
        }
        if (p.getEmail() == null || p.getEmail().trim().isEmpty()) {
            throw new DadosInvalidosException("Email é obrigatório");
        }
        
        // Atribuir ID
        p.setId(proximoId++);
        
        // Adicionar à lista
        pacientes.add(p);
        
        System.out.println("✓ Paciente cadastrado com sucesso!");
        System.out.println("  ID: " + p.getId());
        System.out.println("  Nome: " + p.getNome());
        System.out.println("  Email: " + p.getEmail());
        System.out.println("  Total de pacientes: " + pacientes.size());
        System.out.println("=========================\n");
        
        return p;
    }
    
    public Paciente buscarPorId(Long id) throws PacienteNaoEncontradoException {
        return pacientes.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new PacienteNaoEncontradoException("Paciente ID " + id + " não encontrado"));
    }
    
    public List<Paciente> listarAtivos() {
        List<Paciente> ativos = pacientes.stream()
            .filter(Paciente::isAtivo)
            .toList();
        
        System.out.println("📋 Listando pacientes ativos: " + ativos.size());
        return new ArrayList<>(ativos);
    }
    
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }
    
    public void remover(Long id) throws PacienteNaoEncontradoException {
        Paciente p = buscarPorId(id);
        p.setAtivo(false);
        System.out.println("✓ Paciente " + p.getNome() + " desativado");
    }
    
    // Método útil para debug
    public void imprimirTodosPacientes() {
        System.out.println("\n=== DEBUG: TODOS OS PACIENTES ===");
        if (pacientes.isEmpty()) {
            System.out.println("  (Lista vazia)");
        } else {
            for (Paciente p : pacientes) {
                System.out.println("  ID: " + p.getId() + " | " + p.getNome() + " | " + p.getEmail());
            }
        }
        System.out.println("================================\n");
    }
}