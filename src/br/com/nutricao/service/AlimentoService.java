package br.com.nutricao.service;

import br.com.nutricao.model.Alimento;
import java.util.*;

/**
 * Service para gerenciar alimentos
 * Implementa padrão Singleton
 */
public class AlimentoService {
    
    // ===== SINGLETON =====
    private static AlimentoService instancia;
    
    public static AlimentoService getInstance() {
        if (instancia == null) {
            instancia = new AlimentoService();
        }
        return instancia;
    }
    
    // ===== ATRIBUTOS =====
    private List<Alimento> alimentos;
    private Long proximoId;
    
    // Construtor PRIVADO (Singleton)
    public AlimentoService() {
        this.alimentos = new ArrayList<>();
        this.proximoId = 1L;
        carregarAlimentosBase();
        System.out.println("✓ AlimentoService inicializado com " + alimentos.size() + " alimentos");
    }
    
    // ===== MÉTODOS PRIVADOS =====
    
    private void carregarAlimentosBase() {
        // Alimentos pré-cadastrados
        cadastrar(new Alimento("Arroz branco", 130, "1 colher de servir"));
        cadastrar(new Alimento("Feijão preto", 77, "1 concha"));
        cadastrar(new Alimento("Frango grelhado", 165, "100g"));
        cadastrar(new Alimento("Ovo cozido", 155, "1 unidade"));
        cadastrar(new Alimento("Batata cozida", 87, "1 unidade média"));
        cadastrar(new Alimento("Brócolis", 34, "1 xícara"));
        cadastrar(new Alimento("Banana", 89, "1 unidade"));
        cadastrar(new Alimento("Maçã", 52, "1 unidade"));
        cadastrar(new Alimento("Pão integral", 69, "1 fatia"));
        cadastrar(new Alimento("Leite desnatado", 42, "1 copo (200ml)"));
    }
    
    // ===== MÉTODOS PÚBLICOS =====
    
    public Alimento cadastrar(Alimento alimento) {
        alimento.setId(proximoId++);
        alimentos.add(alimento);
        return alimento;
    }
    
    public List<Alimento> listar() {
        return new ArrayList<>(alimentos);
    }
    
    public List<Alimento> buscarPorNome(String nome) {
        return alimentos.stream()
            .filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase()))
            .toList();
    }
    
    public Alimento buscarPorId(Long id) {
        return alimentos.stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}