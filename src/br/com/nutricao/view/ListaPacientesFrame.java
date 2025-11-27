package br.com.nutricao.view;

import br.com.nutricao.model.Paciente;
import br.com.nutricao.service.PacienteService;
import br.com.nutricao.exception.PacienteNaoEncontradoException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListaPacientesFrame extends JFrame {

    private PacienteService pacienteService;

    // Componentes
    private JPanel painelPrincipal;
    private JTextField txtBusca;
    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;
    private JButton btnNovoPaciente;
    private JButton btnAvaliar;
    private JButton btnVoltar;

    public ListaPacientesFrame() {
        pacienteService = PacienteService.getInstance();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        carregarPacientes();
    }

    private void inicializarComponentes() {
        setTitle("Lista de Pacientes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBackground(new Color(245, 255, 249));

        // Título
        JLabel lblTitulo = new JLabel("TODOS OS PACIENTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.BLACK); // PRETO
        lblTitulo.setBounds(30, 20, 400, 30);
        painelPrincipal.add(lblTitulo);

        // Campo de busca
        JLabel lblBusca = new JLabel("Pesquisar:");
        lblBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBusca.setForeground(Color.BLACK); // PRETO
        lblBusca.setBounds(30, 70, 100, 30);
        painelPrincipal.add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(120, 70, 400, 35);
        txtBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBusca.setForeground(Color.BLACK); // PRETO - TEXTO DIGITADO
        txtBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtBusca.setBackground(new Color(232, 255, 249));
        painelPrincipal.add(txtBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(540, 70, 100, 35);
        btnBuscar.setBackground(new Color(0, 217, 163));
        btnBuscar.setForeground(Color.BLACK); // PRETO
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 13));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> buscarPacientes());
        painelPrincipal.add(btnBuscar);

        // Botão Novo Paciente
        btnNovoPaciente = new JButton("NOVO PACIENTE");
        btnNovoPaciente.setBounds(30, 120, 840, 40);
        btnNovoPaciente.setFont(new Font("Arial", Font.BOLD, 14));
        btnNovoPaciente.setBackground(new Color(0, 217, 163));
        btnNovoPaciente.setForeground(Color.BLACK); //  PRETO
        btnNovoPaciente.setFocusPainted(false);
        btnNovoPaciente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelPrincipal.add(btnNovoPaciente);

        // Tabela de pacientes
        String[] colunas = {"ID", "Nome", "Idade", "Gênero", "Email", "Telefone", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaPacientes = new JTable(modeloTabela);
        tabelaPacientes.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelaPacientes.setForeground(Color.BLACK); // PRETO - CONTEÚDO DA TABELA
        tabelaPacientes.setBackground(Color.WHITE);
        tabelaPacientes.setRowHeight(30);
        tabelaPacientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabelaPacientes.getTableHeader().setBackground(new Color(0, 217, 163));
        tabelaPacientes.getTableHeader().setForeground(Color.BLACK); // PRETO - CABEÇALHO
        tabelaPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabelaPacientes);
        scrollPane.setBounds(30, 180, 840, 300);
        painelPrincipal.add(scrollPane);

        // Botões de ação (AVALIAR PACIENTE e VOLTAR)
        btnAvaliar = new JButton("AVALIAR PACIENTE");
        btnAvaliar.setBounds(30, 500, 270, 40);
        btnAvaliar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAvaliar.setBackground(new Color(0, 168, 120));
        btnAvaliar.setForeground(Color.BLACK); // PRETO
        btnAvaliar.setFocusPainted(false);
        btnAvaliar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelPrincipal.add(btnAvaliar);

        btnVoltar = new JButton("<VOLTAR>");
        btnVoltar.setBounds(320, 500, 270, 40);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(150, 150, 150));
        btnVoltar.setForeground(Color.BLACK); // PRETO
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelPrincipal.add(btnVoltar);
    }

    private void configurarLayout() {
        add(painelPrincipal);
    }

    private void configurarEventos() {
        btnNovoPaciente.addActionListener(e -> abrirCadastro());
        btnAvaliar.addActionListener(e -> abrirAvaliacao());
        btnVoltar.addActionListener(e -> voltarDashboard());

        // Busca ao pressionar Enter
        txtBusca.addActionListener(e -> buscarPacientes());
    }

    private void carregarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.listarAtivos();
            atualizarTabela(pacientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar pacientes: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPacientes() {
        String termoBusca = txtBusca.getText().trim();

        if (termoBusca.isEmpty()) {
            carregarPacientes();
            return;
        }

        try {
            List<Paciente> todosPacientes = pacienteService.listarAtivos();
            List<Paciente> filtrados = todosPacientes.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(termoBusca.toLowerCase()) ||
                            p.getEmail().toLowerCase().contains(termoBusca.toLowerCase()))
                    .toList();

            atualizarTabela(filtrados);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao buscar: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela(List<Paciente> pacientes) {
        modeloTabela.setRowCount(0);

        for (Paciente p : pacientes) {
            Object[] linha = {
                    p.getId(),
                    p.getNome(),
                    p.getIdade() + " anos",
                    p.getGenero(),
                    p.getEmail(),
                    p.getTelefone() != null ? p.getTelefone() : "-",
                    p.isAtivo() ? "ATIVO" : "INATIVO"
            };
            modeloTabela.addRow(linha);
        }
    }

    private void abrirCadastro() {
        CadastroPacienteFrame cadastro = new CadastroPacienteFrame();
        cadastro.setVisible(true);
        this.dispose();
    }

    private void abrirAvaliacao() {
        int linhaSelecionada = tabelaPacientes.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um paciente na tabela!",
                    "Nenhum paciente selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long pacienteId = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);

        AvaliacaoFrame avaliacao = new AvaliacaoFrame(pacienteId);
        avaliacao.setVisible(true);
        this.dispose();
    }

    private void voltarDashboard() {
        DashboardFrame dashboard = new DashboardFrame();
        dashboard.setVisible(true);
        this.dispose();
    }
}