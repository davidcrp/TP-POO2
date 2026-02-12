package view;

import model.Estatisticas;
import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

public class DialogoEstatisticas extends JDialog {

    private Estatisticas estatisticas;

    public DialogoEstatisticas(JFrame parent, Estatisticas estatisticas) {
        super(parent, "Estatísticas", true);

        this.estatisticas = estatisticas;

        setSize(500, 560);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(18, 18, 19));

        criarConteudo();
    }

    private void criarConteudo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        painel.setBackground(new Color(18, 18, 19));

        // TÍTULO
        JLabel titulo = new JLabel("ESTATÍSTICAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));

        // GRID DE ESTATÍSTICAS
        JPanel gridStats = new JPanel(new GridLayout(1, 4, 15, 0));
        gridStats.setBackground(new Color(18, 18, 19));
        gridStats.setMaximumSize(new Dimension(440, 100));

        gridStats.add(criarCardStat("Jogos", String.valueOf(estatisticas.getTotalPartidas())));
        gridStats.add(criarCardStat("Vitórias", String.valueOf(estatisticas.getPartidasGanhas())));
        gridStats.add(criarCardStat("Taxa", String.format("%.0f%%", estatisticas.getPorcentagemVitorias())));
        gridStats.add(criarCardStat("Sequência", String.valueOf(estatisticas.getSequenciaAtual())));

        painel.add(gridStats);
        painel.add(Box.createVerticalStrut(40));

        // DISTRIBUIÇÃO DE VITÓRIAS
        JLabel lblDistribuicao = new JLabel("Distribuição de Vitórias");
        lblDistribuicao.setFont(new Font("Arial", Font.BOLD, 16));
        lblDistribuicao.setForeground(Color.WHITE);
        lblDistribuicao.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        painel.add(lblDistribuicao);
        painel.add(Box.createVerticalStrut(15));

        painel.add(criarGraficoDistribuicao());
        painel.add(Box.createVerticalStrut(40));

        // BOTÃO FECHAR
        JButton btnFechar = new JButton("Fechar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(106, 170, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        btnFechar.setFont(new Font("Arial", Font.BOLD, 15));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setPreferredSize(new Dimension(160, 38));
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.addActionListener(e -> dispose());

        painel.add(btnFechar);
        add(painel);
    }

    private JPanel criarCardStat(String titulo, String valor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(18, 18, 19));

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 32));
        lblValor.setForeground(Color.WHITE);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitulo.setForeground(new Color(150, 150, 150));

        card.add(lblValor, BorderLayout.CENTER);
        card.add(lblTitulo, BorderLayout.SOUTH);

        return card;
    }

    private JPanel criarGraficoDistribuicao() {
        JPanel painelGrafico = new JPanel();
        painelGrafico.setLayout(new BoxLayout(painelGrafico, BoxLayout.Y_AXIS));
        painelGrafico.setBackground(new Color(18, 18, 19));
        painelGrafico.setAlignmentX(Component.LEFT_ALIGNMENT);

        int maxTentativas = 6;
        int[] distribuicao = estatisticas.getDistribuicaoVitorias();

        for (int i = 1; i <= maxTentativas; i++) {
            int valor = 0;
            if (distribuicao != null && distribuicao.length >= i) {
                valor = distribuicao[i - 1];
            }

            painelGrafico.add(criarBarraDistribuicao(i, valor));
            painelGrafico.add(Box.createVerticalStrut(6));
        }

        return painelGrafico;
    }

    private JPanel criarBarraDistribuicao(int tentativa, int valor) {
        JPanel linha = new JPanel(new BorderLayout(10, 0));
        linha.setBackground(new Color(18, 18, 19));
        linha.setMaximumSize(new Dimension(420, 25));

        JLabel lblNumero = new JLabel(String.valueOf(tentativa));
        lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
        lblNumero.setForeground(Color.WHITE);
        lblNumero.setPreferredSize(new Dimension(20, 20));

        JPanel barra = new JPanel();
        barra.setLayout(new BorderLayout());
        barra.setBackground(new Color(106, 170, 100));
        barra.setPreferredSize(new Dimension(Math.max(30, valor * 25), 25));

        JLabel lblValor = new JLabel(String.valueOf(valor), SwingConstants.RIGHT);
        lblValor.setForeground(Color.WHITE);
        lblValor.setFont(new Font("Arial", Font.BOLD, 13));
        lblValor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        barra.add(lblValor, BorderLayout.EAST);

        linha.add(lblNumero, BorderLayout.WEST);
        linha.add(barra, BorderLayout.CENTER);

        return linha;
    }
}