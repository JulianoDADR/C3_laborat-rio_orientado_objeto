package br.com.nutricao.model;
import java.util.*;

public class RefeicaoPlano {
    private Long id;
    private String tipo;
    private String horario;
    private List<AlimentoRefeicao> alimentos;
    
    public RefeicaoPlano() {
        this.alimentos = new ArrayList<>();
    }
    
    public void adicionarAlimento(AlimentoRefeicao alimento) {
        alimentos.add(alimento);
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getHorario() { return horario; }
    public void setHorario(String h) { this.horario = h; }
    public List<AlimentoRefeicao> getAlimentos() { return alimentos; }
    public void setAlimentos(List<AlimentoRefeicao> a) { this.alimentos = a; }
}
