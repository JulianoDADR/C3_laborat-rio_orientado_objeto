package br.com.nutricao.exception;
import br.com.nutricao.enums.*;

public interface CalculoNutricional {
    double calcularIMC(double peso, double altura);
    double calcularGET(double peso, double altura, int idade, Genero genero, NivelAtividade atividade);
}