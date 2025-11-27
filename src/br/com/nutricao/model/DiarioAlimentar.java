package br.com.nutricao.model;
import java.time.LocalDateTime;

public class DiarioAlimentar {
    private Long id;
    private Long pacienteId;
    private LocalDateTime dataHora;
    private String refeicao;
    private String descricao;
    private String caminhoFoto;
    
    public DiarioAlimentar() {
        this.dataHora = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long id) { this.pacienteId = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dt) { this.dataHora = dt; }
    public String getRefeicao() { return refeicao; }
    public void setRefeicao(String ref) { this.refeicao = ref; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String desc) { this.descricao = desc; }
    public String getCaminhoFoto() { return caminhoFoto; }
    public void setCaminhoFoto(String path) { this.caminhoFoto = path; }
}
