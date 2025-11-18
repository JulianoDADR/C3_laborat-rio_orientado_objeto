package br.com.nutricao.model;
import java.time.LocalDateTime;
import java.util.*;

public class PlanoAlimentar {
    private Long id;
    private Long pacienteId;
    private Long nutricionistaId;
    private LocalDateTime dataCriacao;
    private boolean ativo;
    private String objetivo;
    private List<RefeicaoPlano> refeicoes;
    
    public PlanoAlimentar() {
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
        this.refeicoes = new ArrayList<>();
    }
    
    public void adicionarRefeicao(RefeicaoPlano ref) {
        refeicoes.add(ref);
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long id) { this.pacienteId = id; }
    public Long getNutricionistaId() { return nutricionistaId; }
    public void setNutricionistaId(Long id) { this.nutricionistaId = id; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String obj) { this.objetivo = obj; }
    public List<RefeicaoPlano> getRefeicoes() { return refeicoes; }
    public void setRefeicoes(List<RefeicaoPlano> r) { this.refeicoes = r; }
}