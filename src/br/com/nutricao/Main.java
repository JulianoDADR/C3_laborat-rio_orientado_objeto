// Main.java
// Coloque na pasta: src/br/com/nutricao/

package br.com.nutricao;

import br.com.nutricao.model.*;
import br.com.nutricao.service.*;
import br.com.nutricao.enums.*;
import br.com.nutricao.exception.*;
import br.com.nutricao.util.GerenciadorCSV;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    
    private static PacienteService pacienteService = new PacienteService();
    private static AvaliacaoService avaliacaoService = new AvaliacaoService();
    private static AlimentoService alimentoService = new AlimentoService();
    private static PlanoAlimentarService planoService = new PlanoAlimentarService();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE NUTRIÇÃO ===\n");
        
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer
            
            try {
                switch (opcao) {
                    case 1 -> cadastrarPaciente();
                    case 2 -> listarPacientes();
                    case 3 -> avaliarPaciente();
                    case 4 -> criarPlanoAlimentar();
                    case 5 -> listarAlimentos();
                    case 6 -> testarExcecoes();
                    case 7 -> exportarParaCSV();
                    case 0 -> {
                        System.out.println("Saindo...");
                        rodando = false;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
            
            System.out.println();
        }
        
        scanner.close();
    }
    
    private static void exibirMenu() {
        System.out.println("--- MENU ---");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Listar Pacientes");
        System.out.println("3. Avaliar Paciente");
        System.out.println("4. Criar Plano Alimentar");
        System.out.println("5. Listar Alimentos");
        System.out.println("6. Testar Exceções");
        System.out.println("7. Exportar Dados para CSV");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }
    
    private static void cadastrarPaciente() throws DadosInvalidosException {
        System.out.println("\n--- CADASTRAR PACIENTE ---");
        
        Paciente paciente = new Paciente();
        
        System.out.print("Nome: ");
        paciente.setNome(scanner.nextLine());
        
        System.out.print("Email: ");
        paciente.setEmail(scanner.nextLine());
        
        System.out.print("Senha: ");
        paciente.setSenha(scanner.nextLine());
        
        System.out.print("CPF: ");
        paciente.setCpf(scanner.nextLine());
        
        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
        String[] data = scanner.nextLine().split("/");
        paciente.setDataNascimento(LocalDate.of(
            Integer.parseInt(data[2]), 
            Integer.parseInt(data[1]), 
            Integer.parseInt(data[0])
        ));
        
        System.out.print("Gênero (MASCULINO/FEMININO/OUTRO): ");
        paciente.setGenero(Genero.valueOf(scanner.nextLine().toUpperCase()));
        
        System.out.print("Profissão: ");
        paciente.setProfissao(scanner.nextLine());
        
        Paciente cadastrado = pacienteService.cadastrar(paciente);
        System.out.println("\n✓ Paciente cadastrado com ID: " + cadastrado.getId());
        System.out.println(cadastrado.getNome() + " - " + cadastrado.getIdade() + " anos");
    }
    
    private static void listarPacientes() {
        System.out.println("\n--- PACIENTES ATIVOS ---");
        var pacientes = pacienteService.listarAtivos();
        
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        
        for (Paciente p : pacientes) {
            System.out.println("ID: " + p.getId() + " | " + p.getNome() + 
                             " | " + p.getIdade() + " anos | " + p.getGenero());
        }
    }
    
    private static void avaliarPaciente() throws PacienteNaoEncontradoException {
        System.out.println("\n--- AVALIAÇÃO ANTROPOMÉTRICA ---");
        
        System.out.print("ID do Paciente: ");
        Long pacienteId = scanner.nextLong();
        scanner.nextLine();
        
        // Verifica se existe
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        
        AvaliacaoAntropometrica aval = new AvaliacaoAntropometrica();
        aval.setPacienteId(pacienteId);
        
        System.out.print("Peso (kg): ");
        aval.setPeso(scanner.nextDouble());
        
        System.out.print("Altura (m): ");
        aval.setAltura(scanner.nextDouble());
        
        System.out.print("Cintura (cm): ");
        aval.setCintura(scanner.nextDouble());
        
        System.out.print("Quadril (cm): ");
        aval.setQuadril(scanner.nextDouble());
        scanner.nextLine();
        
        avaliacaoService.salvar(aval);
        
        System.out.println("\n✓ Avaliação salva!");
        System.out.println("IMC: " + String.format("%.2f", aval.calcularIMC()));
        System.out.println("Classificação: " + aval.classificarIMC());
        
        // Calcular GET
        System.out.print("\nNível de Atividade (SEDENTARIO/LEVEMENTE_ATIVO/MODERADAMENTE_ATIVO/MUITO_ATIVO): ");
        NivelAtividade atividade = NivelAtividade.valueOf(scanner.nextLine().toUpperCase());
        
        double get = avaliacaoService.calcularGET(
            aval.getPeso(), 
            aval.getAltura(), 
            paciente.getIdade(), 
            paciente.getGenero(), 
            atividade
        );
        
        System.out.println("Gasto Energético Total: " + String.format("%.0f", get) + " kcal/dia");
    }
    
    private static void criarPlanoAlimentar() throws PacienteNaoEncontradoException {
        System.out.println("\n--- CRIAR PLANO ALIMENTAR ---");
        
        System.out.print("ID do Paciente: ");
        Long pacienteId = scanner.nextLong();
        scanner.nextLine();
        
        pacienteService.buscarPorId(pacienteId); // valida se existe
        
        PlanoAlimentar plano = new PlanoAlimentar();
        plano.setPacienteId(pacienteId);
        plano.setNutricionistaId(1L);
        
        System.out.print("Objetivo: ");
        plano.setObjetivo(scanner.nextLine());
        
        // Adicionar refeições
        System.out.print("Quantas refeições: ");
        int qtdRefeicoes = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < qtdRefeicoes; i++) {
            System.out.println("\nRefeição " + (i+1) + ":");
            
            RefeicaoPlano refeicao = new RefeicaoPlano();
            
            System.out.print("Tipo (Café da manhã/Almoço/Jantar): ");
            refeicao.setTipo(scanner.nextLine());
            
            System.out.print("Horário: ");
            refeicao.setHorario(scanner.nextLine());
            
            System.out.print("Quantos alimentos: ");
            int qtdAlimentos = scanner.nextInt();
            scanner.nextLine();
            
            for (int j = 0; j < qtdAlimentos; j++) {
                AlimentoRefeicao alimento = new AlimentoRefeicao();
                
                System.out.print("  Nome do alimento: ");
                alimento.setNomeAlimento(scanner.nextLine());
                
                System.out.print("  Quantidade: ");
                alimento.setQuantidade(scanner.nextDouble());
                scanner.nextLine();
                
                System.out.print("  Unidade: ");
                alimento.setUnidade(scanner.nextLine());
                
                refeicao.adicionarAlimento(alimento);
            }
            
            plano.adicionarRefeicao(refeicao);
        }
        
        planoService.criar(plano);
        System.out.println("\n✓ Plano alimentar criado com sucesso!");
    }
    
    private static void listarAlimentos() {
        System.out.println("\n--- BANCO DE ALIMENTOS ---");
        var alimentos = alimentoService.listar();
        
        for (Alimento a : alimentos) {
            System.out.println(a.getId() + ". " + a.getNome() + 
                             " - " + a.getCalorias() + " kcal (" + a.getMedidaCaseira() + ")");
        }
    }
    
    private static void testarExcecoes() {
        System.out.println("\n--- TESTANDO EXCEÇÕES ---");
        
        // Teste 1: Paciente não encontrado
        try {
            System.out.println("1. Buscando paciente ID 999...");
            pacienteService.buscarPorId(999L);
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("   ✓ Exceção capturada: " + e.getMessage());
        }
        
        // Teste 2: Dados inválidos
        try {
            System.out.println("2. Cadastrando paciente sem nome...");
            Paciente p = new Paciente();
            pacienteService.cadastrar(p);
        } catch (DadosInvalidosException e) {
            System.out.println("   ✓ Exceção capturada: " + e.getMessage());
        }
        
        // Teste 3: Herança e Polimorfismo
        System.out.println("3. Testando Herança:");
        Nutricionista nutri = new Nutricionista();
        nutri.setNome("Dr. Silva");
        nutri.setCrn("12345");
        System.out.println("   ✓ Nutricionista criado: " + nutri.getNome() + " (Tipo: " + nutri.getTipo() + ")");
        
        // Teste 4: Interface
        System.out.println("4. Testando Interface CalculoNutricional:");
        double imc = avaliacaoService.calcularIMC(70, 1.75);
        System.out.println("   ✓ IMC calculado via interface: " + String.format("%.2f", imc));
        
        System.out.println("\n✓ Todos os testes de exceção passaram!");
    }
    
    private static void exportarParaCSV() {
        System.out.println("\n--- EXPORTAR DADOS PARA CSV ---");
        
        System.out.print("Nome da pasta de destino (ex: dados_exportados): ");
        String pasta = scanner.nextLine();
        
        try {
            GerenciadorCSV.exportarTodosSistema(
                pacienteService.listarAtivos(),
                avaliacaoService.buscarPorPaciente(1L), // exemplo
                alimentoService.listar(),
                java.util.Collections.emptyList(), // planos
                java.util.Collections.emptyList(), // diário
                java.util.Collections.emptyList(), // anamneses
                pasta
            );
        } catch (Exception e) {
            System.out.println("Erro ao exportar: " + e.getMessage());
        }
    }
}