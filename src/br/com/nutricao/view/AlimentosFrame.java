package br.com.nutricao.view;

import br.com.nutricao.model.Alimento;
import br.com.nutricao.service.AlimentoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AlimentosFrame extends JFrame {
    
    private AlimentoService alimentoService;
    
    // Componentes
    private JPanel painelPrincipal;
    private JTextField txtBusca;
    private JTable tabelaAlimentos;
    private DefaultTableModel modeloTabela;
    private JButton btnNovoAlimento;
    private JButton btnVoltar;
    
    public AlimentosFrame() {
        alimentoService = AlimentoService.getInstance();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        carregarAlimentos();
    }
    
    private void inicializarComponentes() {
        setTitle("Banco de Alimentos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBackground(new Color(245, 255, 249));
        
        // Título
        JLabel lblTitulo = new JLabel(" BANCO DE ALIMENTOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(30, 20, 400, 30);
        painelPrincipal.add(lblTitulo);
        
        // Campo de busca
        JLabel lblBusca = new JLabel(" Buscar alimento:");
        lblBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBusca.setBounds(30, 70, 150, 30);
        painelPrincipal.add(lblBusca);
        
        txtBusca = new JTextField();
        txtBusca.setBounds(180, 70, 400, 35);
        txtBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBusca.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtBusca.setBackground(new Color(232, 255, 249));
        painelPrincipal.add(txtBusca);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(600, 70, 100, 35);
        configurarBotao(btnBuscar, new Color(0, 217, 163), new Color(0, 180, 140));
        btnBuscar.addActionListener(e -> buscarAlimentos());
        painelPrincipal.add(btnBuscar);
        
        // Botão Novo Alimento
        btnNovoAlimento = new JButton(" ADICIONAR NOVO ALIMENTO");
        btnNovoAlimento.setBounds(30, 120, 940, 40);
        btnNovoAlimento.setFont(new Font("Arial", Font.BOLD, 14));
        configurarBotao(btnNovoAlimento, new Color(0, 217, 163), new Color(0, 180, 140));
        painelPrincipal.add(btnNovoAlimento);
        
        // Tabela de alimentos
        String[] colunas = {
            "ID", 
            "Nome", 
            "Categoria", 
            "Calorias (kcal)", 
            "Proteínas (g)", 
            "Carboidratos (g)", 
            "Gorduras (g)", 
            "Medida Caseira"
        };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaAlimentos = new JTable(modeloTabela);
        tabelaAlimentos.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaAlimentos.setRowHeight(30);
        tabelaAlimentos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaAlimentos.getTableHeader().setBackground(new Color(0, 217, 163));
        tabelaAlimentos.getTableHeader().setForeground(Color.WHITE);
        tabelaAlimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ajustar largura das colunas
        tabelaAlimentos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaAlimentos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabelaAlimentos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaAlimentos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabelaAlimentos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaAlimentos.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelaAlimentos.getColumnModel().getColumn(6).setPreferredWidth(80);
        tabelaAlimentos.getColumnModel().getColumn(7).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(tabelaAlimentos);
        scrollPane.setBounds(30, 180, 940, 320);
        painelPrincipal.add(scrollPane);
        
        // Botão Voltar
        btnVoltar = new JButton("← VOLTAR AO DASHBOARD");
        btnVoltar.setBounds(30, 520, 940, 40);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 13));
        configurarBotao(btnVoltar, new Color(150, 150, 150), new Color(120, 120, 120));
        painelPrincipal.add(btnVoltar);
    }
    
    private void configurarBotao(JButton btn, Color normal, Color hover) {
        btn.setBackground(normal);
        btn.setForeground(Color.BLACK); // ← LETRA PRETA
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
        });
    }
    
    private void configurarLayout() {
        add(painelPrincipal);
    }
    
    private void configurarEventos() {
        btnNovoAlimento.addActionListener(e -> abrirCadastroAlimento());
        btnVoltar.addActionListener(e -> voltarDashboard());
        txtBusca.addActionListener(e -> buscarAlimentos());
    }
    
    private void carregarAlimentos() {
        try {
            List<Alimento> alimentos = alimentoService.listar();
            atualizarTabela(alimentos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar alimentos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarAlimentos() {
        String termoBusca = txtBusca.getText().trim();
        
        if (termoBusca.isEmpty()) {
            carregarAlimentos();
            return;
        }
        
        try {
            List<Alimento> resultados = alimentoService.buscarPorNome(termoBusca);
            atualizarTabela(resultados);
            
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum alimento encontrado com o termo: " + termoBusca, 
                    "Resultado da busca", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao buscar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarTabela(List<Alimento> alimentos) {
        modeloTabela.setRowCount(0);
        
        for (Alimento a : alimentos) {
            Object[] linha = {
                a.getId(),
                a.getNome(),
                a.getCategoria() != null ? a.getCategoria() : "-",
                String.format("%.1f", a.getCalorias()),
                String.format("%.1f", a.getProteinas()),
                String.format("%.1f", a.getCarboidratos()),
                String.format("%.1f", a.getGorduras()),
                a.getMedidaCaseira()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void abrirCadastroAlimento() {
        // Dialog para cadastrar novo alimento
        JDialog dialog = new JDialog(this, "Cadastrar Novo Alimento", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);
        
        int y = 20;
        
        // Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, y, 100, 25);
        dialog.add(lblNome);
        
        JTextField txtNome = new JTextField();
        txtNome.setBounds(20, y + 25, 450, 30);
        dialog.add(txtNome);
        y += 70;
        
        // Categoria
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(20, y, 100, 25);
        dialog.add(lblCategoria);
        
        JTextField txtCategoria = new JTextField();
        txtCategoria.setBounds(20, y + 25, 450, 30);
        dialog.add(txtCategoria);
        y += 70;
        
        // Calorias
        JLabel lblCalorias = new JLabel("Calorias (kcal):");
        lblCalorias.setBounds(20, y, 150, 25);
        dialog.add(lblCalorias);
        
        JTextField txtCalorias = new JTextField();
        txtCalorias.setBounds(20, y + 25, 450, 30);
        dialog.add(txtCalorias);
        y += 70;
        
        // Proteínas
        JLabel lblProteinas = new JLabel("Proteínas (g):");
        lblProteinas.setBounds(20, y, 150, 25);
        dialog.add(lblProteinas);
        
        JTextField txtProteinas = new JTextField();
        txtProteinas.setBounds(20, y + 25, 450, 30);
        dialog.add(txtProteinas);
        y += 70;
        
        // Medida Caseira
        JLabel lblMedida = new JLabel("Medida Caseira:");
        lblMedida.setBounds(20, y, 150, 25);
        dialog.add(lblMedida);
        
        JTextField txtMedida = new JTextField();
        txtMedida.setBounds(20, y + 25, 450, 30);
        dialog.add(txtMedida);
        y += 70;
        
        // Botões
        JButton btnSalvar = new JButton("SALVAR");
        btnSalvar.setBounds(20, y, 210, 40);
        configurarBotao(btnSalvar, new Color(0, 217, 163), new Color(0, 180, 140));
        
        btnSalvar.addActionListener(e -> {
            try {
                Alimento alimento = new Alimento();
                alimento.setNome(txtNome.getText());
                alimento.setCategoria(txtCategoria.getText());
                alimento.setCalorias(Double.parseDouble(txtCalorias.getText()));
                alimento.setProteinas(Double.parseDouble(txtProteinas.getText()));
                alimento.setMedidaCaseira(txtMedida.getText());
                
                alimentoService.cadastrar(alimento);
                
                JOptionPane.showMessageDialog(dialog, 
                    "Alimento cadastrado com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                dialog.dispose();
                carregarAlimentos();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erro ao cadastrar: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(260, y, 210, 40);
        configurarBotao(btnCancelar, new Color(150, 150, 150), new Color(120, 120, 120));
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        dialog.add(btnSalvar);
        dialog.add(btnCancelar);
        
        dialog.setVisible(true);
    }
    
    private void voltarDashboard() {
        DashboardFrame dashboard = new DashboardFrame();
        dashboard.setVisible(true);
        this.dispose();
    }
}