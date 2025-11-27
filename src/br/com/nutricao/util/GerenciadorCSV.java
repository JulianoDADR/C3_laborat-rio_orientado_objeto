// GerenciadorCSV.java
// Coloque na pasta: src/br/com/nutricao/util/

package br.com.nutricao.util;

import br.com.nutricao.model.*;
import br.com.nutricao.exception.ArquivoException;
import java.io.*;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class GerenciadorCSV {
    
    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    // ========== PACIENTES ==========
    
    public static void salvarPacientes(List<Paciente> pacientes, String caminho) throws ArquivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Cabeçalho
            writer.write("ID;Nome;Email;CPF;DataNascimento;Genero;Profissao;Ativo");
            writer.newLine();
            
            // Dados
            for (Paciente p : pacientes) {
                writer.write(p.getId() + SEPARADOR);
                writer.write(p.getNome() + SEPARADOR);
                writer.write(p.getEmail() + SEPARADOR);
                writer.write(p.getCpf() + SEPARADOR);
                writer.write(p.getDataNascimento() + SEPARADOR);
                writer.write(p.getGenero() + SEPARADOR);
                writer.write((p.getProfissao() != null ? p.getProfissao() : "") + SEPARADOR);
                writer.write(String.valueOf(p.isAtivo()));
                writer.newLine();
            }
            
            System.out.println("✓ Pacientes salvos em: " + caminho);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar pacientes: " + e.getMessage());
        }
    }
    
    // ========== AVALIAÇÕES ==========
    
    public static void salvarAvaliacoes(List<AvaliacaoAntropometrica> avaliacoes, String caminho) throws ArquivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Cabeçalho
            writer.write("ID;PacienteID;Data;Peso;Altura;Cintura;Quadril;IMC;Classificacao");
            writer.newLine();
            
            // Dados
            for (AvaliacaoAntropometrica a : avaliacoes) {
                writer.write(a.getId() + SEPARADOR);
                writer.write(a.getPacienteId() + SEPARADOR);
                writer.write(a.getData().format(formatoData) + SEPARADOR);
                writer.write(a.getPeso() + SEPARADOR);
                writer.write(a.getAltura() + SEPARADOR);
                writer.write(a.getCintura() + SEPARADOR);
                writer.write(a.getQuadril() + SEPARADOR);
                writer.write(String.format("%.2f", a.calcularIMC()) + SEPARADOR);
                writer.write(a.classificarIMC());
                writer.newLine();
            }
            
            System.out.println("✓ Avaliações salvas em: " + caminho);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar avaliações: " + e.getMessage());
        }
    }
    
    // ========== ALIMENTOS ==========
    
    public static void salvarAlimentos(List<Alimento> alimentos, String caminho) throws ArquivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Cabeçalho
            writer.write("ID;Nome;Categoria;Calorias;Proteinas;Carboidratos;Gorduras;MedidaCaseira");
            writer.newLine();
            
            // Dados
            for (Alimento a : alimentos) {
                writer.write(a.getId() + SEPARADOR);
                writer.write(a.getNome() + SEPARADOR);
                writer.write((a.getCategoria() != null ? a.getCategoria() : "") + SEPARADOR);
                writer.write(a.getCalorias() + SEPARADOR);
                writer.write(a.getProteinas() + SEPARADOR);
                writer.write(a.getCarboidratos() + SEPARADOR);
                writer.write(a.getGorduras() + SEPARADOR);
                writer.write(a.getMedidaCaseira());
                writer.newLine();
            }
            
            System.out.println("✓ Alimentos salvos em: " + caminho);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar alimentos: " + e.getMessage());
        }
    }
    
    // ========== PLANOS ALIMENTARES ==========
    
    public static void salvarPlanos(List<PlanoAlimentar> planos, String caminho) throws ArquivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Cabeçalho
            writer.write("ID;PacienteID;NutricionistaID;DataCriacao;Ativo;Objetivo;QtdRefeicoes");
            writer.newLine();
            
            // Dados
            for (PlanoAlimentar p : planos) {
                writer.write(p.getId() + SEPARADOR);
                writer.write(p.getPacienteId() + SEPARADOR);
                writer.write(p.getNutricionistaId() + SEPARADOR);
                writer.write(p.getDataCriacao().format(formatoData) + SEPARADOR);
                writer.write(p.isAtivo() + SEPARADOR);
                writer.write((p.getObjetivo() != null ? p.getObjetivo() : "") + SEPARADOR);
                writer.write(String.valueOf(p.getRefeicoes().size()));
                writer.newLine();
            }
            
            System.out.println("✓ Planos salvos em: " + caminho);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar planos: " + e.getMessage());
        }
    }
    
    // ========== DIÁRIO ALIMENTAR ==========
    
    public static void salvarDiario(List<DiarioAlimentar> registros, String caminho) throws ArquivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Cabeçalho
            writer.write("ID;PacienteID;DataHora;Refeicao;Descricao;Foto");
            writer.newLine();
            
            // Dados
            for (DiarioAlimentar d : registros) {
                writer.write(d.getId() + SEPARADOR);
                writer.write(d.getPacienteId() + SEPARADOR);
                writer.write(d.getDataHora().format(formatoData) + SEPARADOR);
                writer.write((d.getRefeicao() != null ? d.getRefeicao() : "") + SEPARADOR);
                writer.write((d.getDescricao() != null ? d.getDescricao() : "") + SEPARADOR);
                writer.write((d.getCaminhoFoto() != null ? d.getCaminhoFoto() : ""));
                writer.newLine();
            }
            
            System.out.println("✓ Diário salvo em: " + caminho);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar diário: " + e.getMessage());
        }
    }
    
    // ========== ANAMNESES ==========
    
    public static void salvarAnamneses(List<Anamnese> anamneses, String caminho) throws ArquivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Cabeçalho
            writer.write("ID;PacienteID;Data;Doencas;Medicamentos;Alergias;Restricoes;QueixaPrincipal");
            writer.newLine();
            
            // Dados
            for (Anamnese a : anamneses) {
                writer.write(a.getId() + SEPARADOR);
                writer.write(a.getPacienteId() + SEPARADOR);
                writer.write(a.getData().format(formatoData) + SEPARADOR);
                writer.write((a.getDoencas() != null ? a.getDoencas().replace(";", ",") : "") + SEPARADOR);
                writer.write((a.getMedicamentos() != null ? a.getMedicamentos().replace(";", ",") : "") + SEPARADOR);
                writer.write((a.getAlergias() != null ? a.getAlergias().replace(";", ",") : "") + SEPARADOR);
                writer.write((a.getRestricoes() != null ? a.getRestricoes().replace(";", ",") : "") + SEPARADOR);
                writer.write((a.getQueixaPrincipal() != null ? a.getQueixaPrincipal().replace(";", ",") : ""));
                writer.newLine();
            }
            
            System.out.println("✓ Anamneses salvas em: " + caminho);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar anamneses: " + e.getMessage());
        }
    }
    
    // ========== SALVAR TUDO ==========
    
    public static void exportarTodosSistema(
            List<Paciente> pacientes,
            List<AvaliacaoAntropometrica> avaliacoes,
            List<Alimento> alimentos,
            List<PlanoAlimentar> planos,
            List<DiarioAlimentar> diario,
            List<Anamnese> anamneses,
            String pastaDestino
    ) throws ArquivoException {
        
        // Cria a pasta se não existir
        File pasta = new File(pastaDestino);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        
        // Salva cada arquivo
        salvarPacientes(pacientes, pastaDestino + "/pacientes.csv");
        salvarAvaliacoes(avaliacoes, pastaDestino + "/avaliacoes.csv");
        salvarAlimentos(alimentos, pastaDestino + "/alimentos.csv");
        salvarPlanos(planos, pastaDestino + "/planos.csv");
        salvarDiario(diario, pastaDestino + "/diario.csv");
        salvarAnamneses(anamneses, pastaDestino + "/anamneses.csv");
        
        System.out.println("\n✓✓✓ TODOS OS DADOS EXPORTADOS COM SUCESSO! ✓✓✓");
        System.out.println("Pasta: " + pastaDestino);
    }
}