package br.com.nutricao.model;
import java.time.LocalDateTime;

public class Anamnese {
    private Long id;
    private Long pacienteId;
    private LocalDateTime data;
    private String doencas;
    private String medicamentos;
    private String alergias;
    private String restricoes;
    private String queixaPrincipal;
    
    public Anamnese() {
        this.data = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public LocalDateTime getData() { return data; }
    public String getDoencas() { return doencas; }
    public void setDoencas(String doencas) { this.doencas = doencas; }
    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public String getRestricoes() { return restricoes; }
    public void setRestricoes(String restricoes) { this.restricoes = restricoes; }
    public String getQueixaPrincipal() { return queixaPrincipal; }
    public void setQueixaPrincipal(String queixa) { this.queixaPrincipal = queixa; }
}