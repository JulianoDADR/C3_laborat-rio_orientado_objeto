// CalculoNutricional.java
// Coloque na pasta: src/br/com/nutricao/interfaces/

package br.com.nutricao.interfaces;

import br.com.nutricao.enums.Genero;
import br.com.nutricao.enums.NivelAtividade;

public interface CalculoNutricional {
    double calcularIMC(double peso, double altura);
    double calcularGET(double peso, double altura, int idade, Genero genero, NivelAtividade atividade);
}