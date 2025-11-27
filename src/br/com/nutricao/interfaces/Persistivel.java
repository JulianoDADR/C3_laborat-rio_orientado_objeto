package br.com.nutricao.interfaces;

public interface Persistivel {
    void salvarEmArquivo(String caminho) throws Exception;
    void carregarDeArquivo(String caminho) throws Exception;
}