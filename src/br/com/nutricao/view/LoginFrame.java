package br.com.nutricao.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    
    private JPanel painelPrincipal;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;
    private JComboBox<String> cmbTipoUsuario;
    private JCheckBox chkLembrar;
    
    public LoginFrame() {
        inicializarComponentes();
        configurarEventos();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Nutrição - Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBackground(new Color(245, 255, 249));
        
        // Título
        JLabel lblTitulo = new JLabel("BEM VINDO!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(0, 217, 163));
        lblTitulo.setBounds(150, 30, 300, 40);
        painelPrincipal.add(lblTitulo);
        
        // Logo
        JLabel lblLogo = new JLabel("BALANÇA INTELIGENTE");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 16));
        lblLogo.setForeground(new Color(0, 217, 163));
        lblLogo.setBounds(130, 80, 250, 30);
        painelPrincipal.add(lblLogo);
        
        // Email
        JLabel lblEmail = new JLabel("Email / Telefone:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setBounds(50, 150, 150, 25);
        painelPrincipal.add(lblEmail);
        
        txtEmail = new JTextField();
        txtEmail.setBounds(50, 180, 400, 40);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtEmail.setBackground(new Color(232, 255, 249));
        painelPrincipal.add(txtEmail);
        
        // Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSenha.setBounds(50, 240, 150, 25);
        painelPrincipal.add(lblSenha);
        
        txtSenha = new JPasswordField();
        txtSenha.setBounds(50, 270, 400, 40);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtSenha.setBackground(new Color(232, 255, 249));
        painelPrincipal.add(txtSenha);
        
        // Tipo de Usuário
        cmbTipoUsuario = new JComboBox<>(new String[]{"Nutricionista", "Paciente"});
        cmbTipoUsuario.setBounds(150, 330, 200, 35);
        cmbTipoUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        painelPrincipal.add(cmbTipoUsuario);
        
        // Lembrar
        chkLembrar = new JCheckBox("Lembrar de mim");
        chkLembrar.setBounds(50, 380, 150, 25);
        chkLembrar.setBackground(new Color(245, 255, 249));
        painelPrincipal.add(chkLembrar);
        
        // Botão Entrar
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBounds(50, 420, 400, 45);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEntrar.setBackground(new Color(0, 217, 163));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorder(BorderFactory.createEmptyBorder());
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelPrincipal.add(btnEntrar);
        
        // Botão Cadastrar
        btnCadastrar = new JButton("CADASTRE-SE");
        btnCadastrar.setBounds(50, 490, 400, 45);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setBackground(new Color(0, 168, 120));
        btnCadastrar.setForeground(Color.BLACK);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder());
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelPrincipal.add(btnCadastrar);
        
        add(painelPrincipal);
    }
    
    private void configurarEventos() {
        btnEntrar.addActionListener(e -> realizarLogin());
        btnCadastrar.addActionListener(e -> abrirCadastro());
        
        txtSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarLogin();
                }
            }
        });
        
        addHover(btnEntrar, new Color(0, 217, 163), new Color(0, 180, 140));
        addHover(btnCadastrar, new Color(0, 168, 120), new Color(0, 140, 100));
    }
    
    private void addHover(JButton btn, Color normal, Color hover) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });
    }
    
    private void realizarLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String tipo = (String) cmbTipoUsuario.getSelectedItem();
        
        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Preencha todos os campos!", 
                "Atenção", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (tipo.equals("Nutricionista")) {
            new DashboardFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Área do paciente em desenvolvimento!", 
                "Em breve", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void abrirCadastro() {
        new CadastroPacienteFrame().setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}