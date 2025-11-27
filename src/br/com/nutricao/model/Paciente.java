package br.com.nutricao.model;
import br.com.nutricao.enums.*;
import java.time.LocalDate;

public class Paciente extends Usuario {
    private String cpf;
    private LocalDate dataNascimento;
    private Genero genero;
    private Endereco endereco;
    private String profissao;
    private boolean ativo;
    
    public Paciente() {
        this.tipo = TipoUsuario.PACIENTE;
        this.ativo = true;
    }
    
    public int getIdade() {
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate data) { this.dataNascimento = data; }
    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}