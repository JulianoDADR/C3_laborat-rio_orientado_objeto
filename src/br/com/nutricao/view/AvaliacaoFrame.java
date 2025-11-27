package br.com.nutricao.view;

import br.com.nutricao.model.*;
import br.com.nutricao.enums.*;
import br.com.nutricao.service.*;
import br.com.nutricao.exception.PacienteNaoEncontradoException;
import javax.swing.*;
import java.awt.*;

public class AvaliacaoFrame extends JFrame {
    
    private PacienteService pacienteService;
    private AvaliacaoService avaliacaoService;
    private Long pacienteId;
    private Paciente paciente;
    
    // Componentes
    private JPanel painelPrincipal;
    private JLabel lblNomePaciente;
    private JTextField txtPeso;
    private JTextField txtAltura;
    private JTextField txtCintura;
    private JTextField txtQuadril;
    private JTextField txtBraco;
    private JComboBox<String> cmbNivelAtividade;
    private JLabel lblIMC;
    private JLabel lblClassificacao;
    private JLabel lblGET;
    private JButton btnCalcular;
    private JButton btnSalvar;
    private JButton btnVoltar;
    
    public AvaliacaoFrame(Long pacienteId) {
        this.pacienteId = pacienteId;
        this.pacienteService = PacienteService.getInstance();
this.avaliacaoService = AvaliacaoService.getInstance();
        
        try {
            this.paciente = pacienteService.buscarPorId(pacienteId);
            inicializarComponentes();
            configurarLayout();
            configurarEventos();
        } catch (PacienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(null, 
                "Paciente não encontrado!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void inicializarComponentes() {
        setTitle("Avaliação Antropométrica");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(null);
        painelPrincipal.setBackground(new Color(245, 255, 249));
        
        // Título
        JLabel lblTitulo = new JLabel("AVALIAÇÃO ANTROPOMÉTRICA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(0, 168, 120));
        lblTitulo.setBounds(150, 20, 450, 30);
        painelPrincipal.add(lblTitulo);
        
        // Nome do paciente
        lblNomePaciente = new JLabel("Paciente: " + paciente.getNome() + " (" + paciente.getIdade() + " anos)");
        lblNomePaciente.setFont(new Font("Arial", Font.BOLD, 16));
        lblNomePaciente.setBounds(50, 60, 600, 25);
        painelPrincipal.add(lblNomePaciente);
        
        int y = 110;
        
        // Peso
        adicionarCampo("Peso (kg):", txtPeso = new JTextField(), y);
        y += 70;
        
        // Altura
        adicionarCampo("Altura (m):", txtAltura = new JTextField(), y);
        y += 70;
        
        // Cintura
        adicionarCampo("Cintura (cm):", txtCintura = new JTextField(), y);
        y += 70;
        
        // Quadril
        adicionarCampo("Quadril (cm):", txtQuadril = new JTextField(), y);
        y += 70;
        
        // Braço
        adicionarCampo("Braço (cm):", txtBraco = new JTextField(), y);
        y += 70;
        
        // Nível de Atividade
        JLabel lblAtividade = new JLabel("Nível de Atividade:");
        lblAtividade.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAtividade.setBounds(50, y, 200, 25);
        painelPrincipal.add(lblAtividade);
        
        cmbNivelAtividade = new JComboBox<>(new String[]{
            "SEDENTARIO", 
            "LEVEMENTE_ATIVO", 
            "MODERADAMENTE_ATIVO", 
            "MUITO_ATIVO"
        });
        cmbNivelAtividade.setBounds(50, y + 30, 600, 35);
        cmbNivelAtividade.setFont(new Font("Arial", Font.PLAIN, 14));
        painelPrincipal.add(cmbNivelAtividade);
        y += 90;
        
        // Botão Calcular
        btnCalcular = new JButton("🧮 CALCULAR");
        btnCalcular.setBounds(50, y, 600, 40);
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBackground(new Color(0, 217, 163));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelPrincipal.add(btnCalcular);
        y += 60;
        
        // Painel de Resultados
        JPanel painelResultados = new JPanel();
        painelResultados.setBounds(50, y, 600, 120);
        painelResultados.setLayout(null);
        painelResultados.setBackground(Color.WHITE);
        painelResultados.setBorder(BorderFactory.createLineBorder(new Color(0, 217, 163), 2));
        
        JLabel lblTituloResultados = new JLabel("RESULTADOS:");
        lblTituloResultados.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloResultados.setBounds(15, 10, 200, 25);
        painelResultados.add(lblTituloResultados);
        
        lblIMC = new JLabel("IMC: --");
        lblIMC.setFont(new Font("Arial", Font.PLAIN, 14));
        lblIMC.setBounds(15, 40, 300, 25);
        painelResultados.add(lblIMC);
        
        lblClassificacao = new JLabel("Classificação: --");
        lblClassificacao.setFont(new Font("Arial", Font.PLAIN, 14));
        lblClassificacao.setBounds(15, 65, 300, 25);
        painelResultados.add(lblClassificacao);
        
        lblGET = new JLabel("GET (Gasto Energético Total): --");
        lblGET.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGET.setBounds(15, 90, 400, 25);
        painelResultados.add(lblGET);
        
        painelPrincipal.add(painelResultados);
        y += 140;
        
        // Botões finais
        btnVoltar = new JButton("← VOLTAR");
        btnVoltar.setBounds(50, y, 280, 45);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setBackground(new Color(150, 150, 150));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelPrincipal.add(btnVoltar);
        
        btnSalvar = new JButton("💾 SALVAR AVALIAÇÃO");
        btnSalvar.setBounds(370, y, 280, 45);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(0, 217, 163));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setEnabled(false);
        painelPrincipal.add(btnSalvar);
    }
    
    private void adicionarCampo(String label, JTextField campo, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        lbl.setBounds(50, y, 150, 25);
        painelPrincipal.add(lbl);
        
        campo.setBounds(50, y + 30, 600, 35);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 224, 224), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.setBackground(new Color(232, 255, 249));
        painelPrincipal.add(campo);
    }
    
    private void configurarLayout() {
        JScrollPane scrollPane = new JScrollPane(painelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }
    
    private void configurarEventos() {
        btnCalcular.addActionListener(e -> calcularResultados());
        btnSalvar.addActionListener(e -> salvarAvaliacao());
        btnVoltar.addActionListener(e -> voltarLista());
    }
    
    private void calcularResultados() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());
            
            // Calcular IMC
            double imc = avaliacaoService.calcularIMC(peso, altura);
            lblIMC.setText(String.format("IMC: %.2f", imc));
            
            // Classificação
            String classificacao;
            if (imc < 18.5) classificacao = "Abaixo do peso";
            else if (imc < 25) classificacao = "Normal";
            else if (imc < 30) classificacao = "Sobrepeso";
            else classificacao = "Obesidade";
            lblClassificacao.setText("Classificação: " + classificacao);
            
            // Calcular GET
            NivelAtividade atividade = NivelAtividade.valueOf(
                (String) cmbNivelAtividade.getSelectedItem()
            );
            
            double get = avaliacaoService.calcularGET(
                peso, 
                altura, 
                paciente.getIdade(), 
                paciente.getGenero(), 
                atividade
            );
            
            lblGET.setText(String.format("GET (Gasto Energético Total): %.0f kcal/dia", get));
            
            btnSalvar.setEnabled(true);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha os campos com valores numéricos válidos!", 
                "Erro de formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarAvaliacao() {
        try {
            AvaliacaoAntropometrica avaliacao = new AvaliacaoAntropometrica();
            avaliacao.setPacienteId(pacienteId);
            avaliacao.setPeso(Double.parseDouble(txtPeso.getText()));
            avaliacao.setAltura(Double.parseDouble(txtAltura.getText()));
            avaliacao.setCintura(Double.parseDouble(txtCintura.getText()));
            avaliacao.setQuadril(Double.parseDouble(txtQuadril.getText()));
            
            avaliacaoService.salvar(avaliacao);
            
            JOptionPane.showMessageDialog(this, 
                "Avaliação salva com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            voltarLista();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao salvar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void voltarLista() {
        ListaPacientesFrame lista = new ListaPacientesFrame();
        lista.setVisible(true);
        this.dispose();
    }
}