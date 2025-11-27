// Nutricionista.java
// Coloque na pasta: src/br/com/nutricao/model/

package br.com.nutricao.model;

import br.com.nutricao.enums.TipoUsuario;

public class Nutricionista extends Usuario {
    private String crn;
    
    public Nutricionista() {
        super();
        this.tipo = TipoUsuario.NUTRICIONISTA;
    }
    
    public String getCrn() { return crn; }
    public void setCrn(String crn) { this.crn = crn; }
}