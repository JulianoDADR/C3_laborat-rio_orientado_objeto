package br.com.nutricao.model;

public class Endereco {
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    
    public Endereco() {}
    
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}