package view;

import model.Estatisticas;
import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo que mostra estatísticas detalhadas
 */
public class DialogoEstatisticas extends JDialog {

    private Estatisticas estatisticas;
    
    public DialogoEstatisticas(JFrame parent, Estatisticas estatisticas) {
        super(parent, "Estatísticas Detalhadas", true);
        
        this.estatisticas = estatisticas;
        
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        criarConteudo();
    }
    
    private void criarConteudo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painel.setBackground(Color.WHITE);
        
        // Título
        JLabel titulo = new JLabel("📊 SUAS ESTATÍSTICAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(25));
        
        // Estatísticas principais em cards
        JPanel gridStats = new JPanel(new GridLayout(2, 2, 15, 15));
        gridStats.setBackground(Color.WHITE);
        gridStats.setMaximumSize(new Dimension(400, 180));
        
        gridStats.add(criarCardStat("🎮 Jogos", 
            String.valueOf(estatisticas.getTotalPartidas())));
        gridStats.add(criarCardStat("🏆 Vitórias", 
            String.valueOf(estatisticas.getPartidasGanhas())));
        gridStats.add(criarCardStat("📈 Taxa de Vitória", 
            String.format("%.0f%%", estatisticas.getPorcentagemVitorias())));
        gridStats.add(criarCardStat("🔥 Sequência Atual", 
            String.valueOf(estatisticas.getSequenciaAtual())));
        
        painel.add(gridStats);
        painel.add(Box.createVerticalStrut(25));
        
        // Melhor sequência
        JPanel panelMelhor = criarInfoRow("⭐ Melhor Sequência:", 
            String.valueOf(estatisticas.getMelhorSequencia()));
        painel.add(panelMelhor);
        painel.add(Box.createVerticalStrut(10));
        
        // Total de derrotas
        JPanel panelDerrotas = criarInfoRow("❌ Derrotas:", 
            String.valueOf(estatisticas.getPartidasPerdidas()));
        painel.add(panelDerrotas);
        painel.add(Box.createVerticalStrut(25));
        
        // Distribuição de tentativas
        JLabel lblDistribuicao = new JLabel("📊 Distribuição de Vitórias:");
        lblDistribuicao.setFont(new Font("Arial", Font.BOLD, 14));
        lblDistribuicao.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(lblDistribuicao);
        painel.add(Box.createVerticalStrut(10));
        
        // Informação simples - sem gráfico detalhado
        JLabel lblInfo = new JLabel("Informação disponível após implementação completa");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(lblInfo);
        
        painel.add(Box.createVerticalStrut(20));
        
        // Botão fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setBackground(new Color(46, 125, 50));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.setPreferredSize(new Dimension(150, 40));
        btnFechar.addActionListener(e -> dispose());
        painel.add(btnFechar);
        
        // Scroll
        JScrollPane scroll = new JScrollPane(painel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scroll);
    }
    
    /**
     * Cria um card de estatística
     */
    private JPanel criarCardStat(String titulo, String valor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 5));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitulo.setForeground(new Color(100, 100, 100));
        
        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 28));
        lblValor.setForeground(new Color(46, 125, 50));
        
        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        
        return card;
    }
    
    /**
     * Cria uma linha de informação
     */
    private JPanel criarInfoRow(String label, String valor) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 30));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
        lblValor.setForeground(new Color(46, 125, 50));
        
        panel.add(lblLabel);
        panel.add(lblValor);
        
        return panel;
    }
}