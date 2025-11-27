package br.com.nutricao.view;

import br.com.nutricao.model.Paciente;
import br.com.nutricao.service.PacienteService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DashboardFrame extends JFrame {

    private PacienteService pacienteService;
    private JTextArea txtPacientesRecentes;
    private JButton btnNovoPaciente, btnListarPacientes;
    private JButton btnPacientes, btnAlimentos, btnSair;

    public DashboardFrame() {
        // USAR SINGLETON - IMPORTANTE!
        pacienteService = PacienteService.getInstance();
        inicializarComponentes();
        configurarEventos();
        carregarDados();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Nutrição - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // HEADER
        JPanel painelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelHeader.setBackground(new Color(0, 217, 163));
        painelHeader.setPreferredSize(new Dimension(1000, 60));

        JLabel lblLogo = new JLabel("BALANÇA INTELIGENTE");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
        lblLogo.setForeground(Color.WHITE);
        painelHeader.add(lblLogo);

        // MENU
        JPanel painelMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        painelMenu.setBackground(Color.WHITE);
        painelMenu.setPreferredSize(new Dimension(1000, 60));

        btnPacientes = criarBotaoMenu("Pacientes");
        btnAlimentos = criarBotaoMenu("Alimentos");
        btnSair = criarBotaoMenu("Sair");

        painelMenu.add(btnPacientes);
        painelMenu.add(btnAlimentos);
        painelMenu.add(btnSair);

        // CONTEÚDO
        JPanel painelConteudo = new JPanel(null);
        painelConteudo.setBackground(new Color(245, 255, 249));
        painelConteudo.setPreferredSize(new Dimension(1000, 600));

        JLabel lblTitulo = new JLabel("Dashboard - Nutricionista");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(30, 20, 400, 30);
        painelConteudo.add(lblTitulo);

        // Card Pacientes
        JPanel cardPacientes = new JPanel(null);
        cardPacientes.setBounds(30, 70, 450, 300);
        cardPacientes.setBackground(Color.WHITE);
        cardPacientes.setBorder(BorderFactory.createLineBorder(new Color(0, 217, 163), 2));

        JLabel lblCard = new JLabel("👤 Pacientes Ativos");
        lblCard.setFont(new Font("Arial", Font.BOLD, 18));
        lblCard.setBounds(15, 10, 300, 30);
        cardPacientes.add(lblCard);

        txtPacientesRecentes = new JTextArea();
        txtPacientesRecentes.setEditable(false);
        txtPacientesRecentes.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(txtPacientesRecentes);
        scroll.setBounds(15, 50, 420, 200);
        cardPacientes.add(scroll);

        btnListarPacientes = new JButton("Ver Lista Completa");
        btnListarPacientes.setBounds(15, 260, 420, 30);
        configurarBotao(btnListarPacientes, new Color(0, 217, 163), new Color(0, 180, 140));
        cardPacientes.add(btnListarPacientes);

        // Card Ações
        JPanel cardAcoes = new JPanel(null);
        cardAcoes.setBounds(510, 70, 450, 300);
        cardAcoes.setBackground(Color.WHITE);
        cardAcoes.setBorder(BorderFactory.createLineBorder(new Color(0, 217, 163), 2));

        JLabel lblAcoes = new JLabel("Ações Rápidas");
        lblAcoes.setFont(new Font("Arial", Font.BOLD, 18));
        lblAcoes.setBounds(15, 10, 300, 30);
        cardAcoes.add(lblAcoes);

        btnNovoPaciente = criarBotaoAcao("  Cadastrar Novo Paciente", 15, 60, 420);
        cardAcoes.add(btnNovoPaciente);

        painelConteudo.add(cardPacientes);
        painelConteudo.add(cardAcoes);

        painelPrincipal.add(painelHeader, BorderLayout.NORTH);
        painelPrincipal.add(painelMenu, BorderLayout.CENTER);
        painelPrincipal.add(painelConteudo, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(102, 102, 102));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder());
        addHover(btn, Color.WHITE, new Color(240, 240, 240));
        return btn;
    }

    private JButton criarBotaoAcao(String texto, int x, int y, int w) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, w, 40);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setBackground(new Color(232, 255, 249));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(0, 217, 163), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        addHover(btn, new Color(232, 255, 249), new Color(200, 245, 230));
        return btn;
    }

    private void configurarBotao(JButton btn, Color normal, Color hover) {
        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHover(btn, normal, hover);
    }

    private void addHover(JButton btn, Color normal, Color hover) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });
    }

    private void configurarEventos() {
        btnNovoPaciente.addActionListener(e -> abrirCadastro());
        btnListarPacientes.addActionListener(e -> abrirLista());
        btnPacientes.addActionListener(e -> abrirLista());
        btnAlimentos.addActionListener(e -> abrirAlimentos());
        btnSair.addActionListener(e -> voltarLogin());
    }

    private void carregarDados() {
        List<Paciente> pacientes = pacienteService.listarAtivos();

        StringBuilder sb = new StringBuilder();
        if (pacientes.isEmpty()) {
            sb.append("╔═══════════════════════════════╗\n");
            sb.append("║  Nenhum paciente cadastrado   ║\n");
            sb.append("║                               ║\n");
            sb.append("║  Clique em 'Cadastrar Novo'   ║\n");
            sb.append("╚═══════════════════════════════╝\n");
        } else {
            sb.append("╔═══════════════════════════════╗\n");
            sb.append("║   PACIENTES: ").append(String.format("%-16s", pacientes.size())).append("║\n");
            sb.append("╚═══════════════════════════════╝\n\n");

            for (int i = 0; i < Math.min(5, pacientes.size()); i++) {
                Paciente p = pacientes.get(i);
                sb.append((i + 1)).append(". ").append(p.getNome()).append("\n");
                sb.append("   ID: ").append(p.getId());
                sb.append(" | ").append(p.getIdade()).append(" anos");
                sb.append(" | ").append(p.getGenero()).append("\n");
                if (p.getEmail() != null) {
                    sb.append("   ").append(p.getEmail()).append("\n");
                }
                sb.append("\n");
            }

            if (pacientes.size() > 5) {
                sb.append("... e mais ").append(pacientes.size() - 5).append(" pacientes\n");
            }
        }

        txtPacientesRecentes.setText(sb.toString());
    }

    private void abrirCadastro() {
        new CadastroPacienteFrame().setVisible(true);
        this.dispose();
    }

    private void abrirLista() {
        new ListaPacientesFrame().setVisible(true);
        this.dispose();
    }

    private void abrirAlimentos() {
        new AlimentosFrame().setVisible(true);
        this.dispose();
    }

    private void voltarLogin() {
        int op = JOptionPane.showConfirmDialog(this, "Deseja sair?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}