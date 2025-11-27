package br.com.nutricao.view;

import br.com.nutricao.model.Paciente;
import br.com.nutricao.enums.Genero;
import br.com.nutricao.service.PacienteService;
import br.com.nutricao.exception.DadosInvalidosException;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class CadastroPacienteFrame extends JFrame {
    
    private PacienteService pacienteService;
    private JTextField txtNome, txtEmail, txtTelefone, txtCPF, txtDataNascimento, txtProfissao;
    private JComboBox<String> cmbGenero;
    private JPasswordField txtSenha, txtConfirmarSenha;
    private JButton btnSalvar, btnVoltar;
    
    public CadastroPacienteFrame() {
        // USAR SINGLETON - IMPORTANTE!
        pacienteService = PacienteService.getInstance();
        inicializar();
    }
    
    private void inicializar() {
        setTitle("Cadastrar Paciente");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel painel = new JPanel(null);
        painel.setBackground(new Color(245, 255, 249));
        
        JLabel lblTitulo = new JLabel("CADASTRAR NOVO PACIENTE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(0, 168, 120));
        lblTitulo.setBounds(120, 20, 400, 30);
        painel.add(lblTitulo);
        
        int y = 70;
        
        y = addCampo(painel, "Nome: *", txtNome = new JTextField(), y);
        y = addCampo(painel, "Email: *", txtEmail = new JTextField(), y);
        y = addCampo(painel, "Telefone:", txtTelefone = new JTextField(), y);
        y = addCampo(painel, "CPF:", txtCPF = new JTextField(), y);
        y = addCampo(painel, "Data Nascimento (dd/mm/aaaa):", txtDataNascimento = new JTextField(), y);
        
        JLabel lblGenero = new JLabel("Gênero: *");
        lblGenero.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGenero.setBounds(50, y, 150, 25);
        painel.add(lblGenero);
        
        cmbGenero = new JComboBox<>(new String[]{"MASCULINO", "FEMININO", "OUTRO"});
        cmbGenero.setBounds(50, y + 30, 500, 35);
        painel.add(cmbGenero);
        y += 75;
        
        y = addCampo(painel, "Profissão:", txtProfissao = new JTextField(), y);
        addCampo(painel, "Senha (opcional):", txtSenha = new JPasswordField(), y);
        y += 60;
        addCampo(painel, "Confirmar Senha:", txtConfirmarSenha = new JPasswordField(), y);
        y += 70;
        
        btnVoltar = new JButton("← VOLTAR");
        btnVoltar.setBounds(50, y, 240, 45);
        configurarBotao(btnVoltar, new Color(150, 150, 150), new Color(120, 120, 120));
        painel.add(btnVoltar);
        
        btnSalvar = new JButton("CADASTRAR →");
        btnSalvar.setBounds(310, y, 240, 45);
        configurarBotao(btnSalvar, new Color(0, 217, 163), new Color(0, 180, 140));
        painel.add(btnSalvar);
        
        btnSalvar.addActionListener(e -> salvar());
        btnVoltar.addActionListener(e -> voltar());
        
        JScrollPane scroll = new JScrollPane(painel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll);
    }
    
    private int addCampo(JPanel p, String label, JTextField campo, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        lbl.setBounds(50, y, 300, 25);
        p.add(lbl);
        
        campo.setBounds(50, y + 30, 500, 35);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.setBackground(new Color(232, 255, 249));
        p.add(campo);
        
        return y + 70;
    }
    
    private void configurarBotao(JButton btn, Color normal, Color hover) {
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(normal); }
        });
    }
    
    private void salvar() {
        try {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            
            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nome e Email são obrigatórios!", 
                    "Atenção", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String senha = new String(txtSenha.getPassword());
            String confirmar = new String(txtConfirmarSenha.getPassword());
            
            if (!senha.isEmpty() && !senha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, 
                    "Senhas não coincidem!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Paciente p = new Paciente();
            p.setNome(nome);
            p.setEmail(email);
            p.setTelefone(txtTelefone.getText().trim());
            p.setCpf(txtCPF.getText().trim());
            if (!senha.isEmpty()) p.setSenha(senha);
            p.setProfissao(txtProfissao.getText().trim());
            p.setGenero(Genero.valueOf((String) cmbGenero.getSelectedItem()));
            
            String dataTxt = txtDataNascimento.getText().trim();
            if (!dataTxt.isEmpty()) {
                try {
                    String[] partes = dataTxt.split("/");
                    if (partes.length == 3) {
                        p.setDataNascimento(LocalDate.of(
                            Integer.parseInt(partes[2]), 
                            Integer.parseInt(partes[1]), 
                            Integer.parseInt(partes[0])
                        ));
                    } else {
                        throw new Exception("Formato inválido");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Data inválida!\nUse: dd/mm/aaaa\nExemplo: 15/08/1990", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                p.setDataNascimento(LocalDate.of(1990, 1, 1));
            }
            
            Paciente cadastrado = pacienteService.cadastrar(p);
            
            JOptionPane.showMessageDialog(this, 
                "✓ Paciente cadastrado com sucesso!\n\n" +
                "ID: " + cadastrado.getId() + "\n" +
                "Nome: " + cadastrado.getNome() + "\n" +
                "Idade: " + cadastrado.getIdade() + " anos", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            voltar();
            
        } catch (DadosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro: " + ex.getMessage(), 
                "Validação", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar:\n" + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void voltar() {
        new DashboardFrame().setVisible(true);
        this.dispose();
    }
}