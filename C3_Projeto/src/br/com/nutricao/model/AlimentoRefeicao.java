package br.com.nutricao.model;

public class AlimentoRefeicao {
    private Long alimentoId;
    private String nomeAlimento;
    private double quantidade;
    private String unidade;
    
    public AlimentoRefeicao() {}
    
    public Long getAlimentoId() { return alimentoId; }
    public void setAlimentoId(Long id) { this.alimentoId = id; }
    public String getNomeAlimento() { return nomeAlimento; }
    public void setNomeAlimento(String nome) { this.nomeAlimento = nome; }
    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double qtd) { this.quantidade = qtd; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String un) { this.unidade = un; }
}
