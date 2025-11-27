package br.com.nutricao.util;
import br.com.nutricao.exception.ArquivoException;
import java.io.*;
import java.util.*;

public class GerenciadorArquivo {
    
    public static void salvarTexto(String caminho, List<String> linhas) throws ArquivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar: " + e.getMessage());
        }
    }
    
    public static List<String> lerTexto(String caminho) throws ArquivoException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            throw new ArquivoException("Erro ao ler: " + e.getMessage());
        }
        return linhas;
    }
}