package br.com.nutricao.model;

import java.time.LocalDateTime;

public class AvaliacaoAntropometrica {
    private Long id;
    private Long pacienteId;
    private LocalDateTime data;
    private double peso;
    private double altura;
    private double cintura;
    private double quadril;
    
    public AvaliacaoAntropometrica() {
        this.data = LocalDateTime.now();
    }
    
    public double calcularIMC() {
        return peso / (altura * altura);
    }
    
    public String classificarIMC() {
        double imc = calcularIMC();
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25) return "Normal";
        if (imc < 30) return "Sobrepeso";
        return "Obesidade";
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long id) { this.pacienteId = id; }
    public LocalDateTime getData() { return data; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }
    public double getCintura() { return cintura; }
    public void setCintura(double cintura) { this.cintura = cintura; }
    public double getQuadril() { return quadril; }
    public void setQuadril(double quadril) { this.quadril = quadril; }
}
