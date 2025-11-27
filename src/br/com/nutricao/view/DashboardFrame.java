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
    private JButton btnNovoPaciente, btnListarPacientes, btnAlimentos;
    private JButton btnSair;
    
    public DashboardFrame() {
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
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBackground(new Color(245, 255, 249));
        
        // HEADER
        JPanel painelHeader = new JPanel(null);
        painelHeader.setBounds(0, 0, 1000, 60);
        painelHeader.setBackground(new Color(0, 217, 163));
        
        JLabel lblLogo = new JLabel("BALANÇA INTELIGENTE");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(20, 15, 300, 30);
        painelHeader.add(lblLogo);
        
        // Botões do menu no header
        btnAlimentos = criarBotaoHeader(" Alimentos", 700);
        btnSair = criarBotaoHeader(" Sair", 840);
        
        painelHeader.add(btnAlimentos);
        painelHeader.add(btnSair);
        
        painelPrincipal.add(painelHeader);
        
        // TÍTULO
        JLabel lblTitulo = new JLabel("Dashboard - Nutricionista");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(30, 80, 400, 30);
        painelPrincipal.add(lblTitulo);
        
        // Card Pacientes
        JPanel cardPacientes = new JPanel(null);
        cardPacientes.setBounds(30, 130, 450, 320);
        cardPacientes.setBackground(Color.WHITE);
        cardPacientes.setBorder(BorderFactory.createLineBorder(new Color(0, 217, 163), 2));
        
        JLabel lblCard = new JLabel(" Pacientes Ativos");
        lblCard.setFont(new Font("Arial", Font.BOLD, 18));
        lblCard.setBounds(15, 10, 300, 30);
        cardPacientes.add(lblCard);
        
        txtPacientesRecentes = new JTextArea();
        txtPacientesRecentes.setEditable(false);
        txtPacientesRecentes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scroll = new JScrollPane(txtPacientesRecentes);
        scroll.setBounds(15, 50, 420, 220);
        cardPacientes.add(scroll);
        
        btnListarPacientes = new JButton("Ver Lista Completa");
        btnListarPacientes.setBounds(15, 280, 420, 30);
        configurarBotao(btnListarPacientes, new Color(0, 217, 163), new Color(0, 180, 140));
        cardPacientes.add(btnListarPacientes);
        
        painelPrincipal.add(cardPacientes);
        
        // Card Ações
        JPanel cardAcoes = new JPanel(null);
        cardAcoes.setBounds(510, 130, 450, 320);
        cardAcoes.setBackground(Color.WHITE);
        cardAcoes.setBorder(BorderFactory.createLineBorder(new Color(0, 217, 163), 2));
        
        JLabel lblAcoes = new JLabel(" Ações Rápidas");
        lblAcoes.setFont(new Font("Arial", Font.BOLD, 18));
        lblAcoes.setBounds(15, 10, 300, 30);
        cardAcoes.add(lblAcoes);
        
        btnNovoPaciente = criarBotaoAcao(" Cadastrar Novo Paciente", 20, 60, 420);
        cardAcoes.add(btnNovoPaciente);
        
        JButton btnVerAlimentos = criarBotaoAcao(" Ver Banco de Alimentos", 20, 120, 420);
        btnVerAlimentos.addActionListener(e -> abrirAlimentos());
        cardAcoes.add(btnVerAlimentos);
        
        painelPrincipal.add(cardAcoes);
        
        // Informações adicionais
        JLabel lblInfo = new JLabel("Sistema de Gestão Nutricional v1.0");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setBounds(30, 620, 400, 20);
        painelPrincipal.add(lblInfo);
        
        add(painelPrincipal);
    }
    
    private JButton criarBotaoHeader(String texto, int x) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, 12, 120, 36);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(new Color(0, 180, 140));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                btn.setBackground(new Color(0, 150, 120)); 
            }
            public void mouseExited(MouseEvent e) { 
                btn.setBackground(new Color(0, 180, 140)); 
            }
        });
        
        return btn;
    }
    
    private JButton criarBotaoAcao(String texto, int x, int y, int w) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, w, 45);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(232, 255, 249));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(0, 217, 163), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                btn.setBackground(new Color(200, 245, 230)); 
            }
            public void mouseExited(MouseEvent e) { 
                btn.setBackground(new Color(232, 255, 249)); 
            }
        });
        
        return btn;
    }
    
    private void configurarBotao(JButton btn, Color normal, Color hover) {
        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });
    }
    
    private void configurarEventos() {
        btnNovoPaciente.addActionListener(e -> abrirCadastro());
        btnListarPacientes.addActionListener(e -> abrirLista());
        btnAlimentos.addActionListener(e -> abrirAlimentos());
        btnSair.addActionListener(e -> voltarLogin());
    }
    
    private void carregarDados() {
        List<Paciente> pacientes = pacienteService.listarAtivos();
        
        StringBuilder sb = new StringBuilder();
        if (pacientes.isEmpty()) {
            sb.append("╔════════════════════════════════════╗\n");
            sb.append("║  Nenhum paciente cadastrado   ║\n");
            sb.append("║                               ║\n");
            sb.append("║  Clique em                    ║\n");
            sb.append("║   'Cadastrar Novo Paciente'   ║\n");
            sb.append("╚════════════════════════════════════╝\n");
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
        System.out.println("Abrindo tela de Alimentos..."); // Debug
        new AlimentosFrame().setVisible(true);
        this.dispose();
    }
    
    private void voltarLogin() {
        int op = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente sair do sistema?", 
            "Confirmar Saída", 
            JOptionPane.YES_NO_OPTION);
            
        if (op == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
}