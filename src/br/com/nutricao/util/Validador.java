package br.com.nutricao.util;

public class Validador {
    
    public static boolean validarEmail(String email) {
        return email != null && email.contains("@");
    }
    
    public static boolean validarCPF(String cpf) {
        return cpf != null && cpf.replaceAll("[^0-9]", "").length() == 11;
    }
    
    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 6;
    }
}