package br.com.nutricao.model;

public class Alimento {
    private Long id;
    private String nome;
    private String categoria;
    private double calorias;
    private double proteinas;
    private double carboidratos;
    private double gorduras;
    private String medidaCaseira;
    
    public Alimento() {}
    
    public Alimento(String nome, double calorias, String medida) {
        this.nome = nome;
        this.calorias = calorias;
        this.medidaCaseira = medida;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String cat) { this.categoria = cat; }
    public double getCalorias() { return calorias; }
    public void setCalorias(double cal) { this.calorias = cal; }
    public double getProteinas() { return proteinas; }
    public void setProteinas(double p) { this.proteinas = p; }
    public double getCarboidratos() { return carboidratos; }
    public void setCarboidratos(double c) { this.carboidratos = c; }
    public double getGorduras() { return gorduras; }
    public void setGorduras(double g) { this.gorduras = g; }
    public String getMedidaCaseira() { return medidaCaseira; }
    public void setMedidaCaseira(String m) { this.medidaCaseira = m; }
}