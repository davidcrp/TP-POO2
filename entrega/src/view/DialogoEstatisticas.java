package view;

import model.Estatisticas;
import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;

/**
 * Dialogo profissional de estatisticas - estilo moderno similar ao Wordle
 */
public class DialogoEstatisticas extends JDialog {

    private Estatisticas estatisticas;

    public DialogoEstatisticas(JFrame parent, Estatisticas estatisticas) {
        super(parent, "Estatísticas", true);

        this.estatisticas = estatisticas;

        setSize(500, 560);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(ConstantesVisuais.COR_FUNDO);

        criarConteudo();
    }

    private void criarConteudo() {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        painel.setBackground(ConstantesVisuais.COR_FUNDO);

        // ===== TITULO =====
        JLabel titulo = new JLabel("ESTATÍSTICAS");
        titulo.setFont(ConstantesVisuais.FONTE_SUBTITULO);
        titulo.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));

        // ===== GRID PRINCIPAL =====
        JPanel gridStats = new JPanel(new GridLayout(1, 4, 15, 0));
        gridStats.setBackground(ConstantesVisuais.COR_FUNDO);
        gridStats.setMaximumSize(new Dimension(440, 100));

        gridStats.add(criarCardStat("Jogos", String.valueOf(estatisticas.getTotalPartidas())));
        gridStats.add(criarCardStat("Vitórias", String.valueOf(estatisticas.getPartidasGanhas())));
        gridStats.add(criarCardStat("%", String.format("%.0f", estatisticas.getPorcentagemVitorias())));
        gridStats.add(criarCardStat("Sequência", String.valueOf(estatisticas.getSequenciaAtual())));

        painel.add(gridStats);
        painel.add(Box.createVerticalStrut(40));

        // ===== DISTRIBUICAO =====
        JLabel lblDistribuicao = new JLabel("Distribuição de Vitórias");
        lblDistribuicao.setFont(ConstantesVisuais.FONTE_NORMAL);
        lblDistribuicao.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        lblDistribuicao.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(lblDistribuicao);
        painel.add(Box.createVerticalStrut(15));

        painel.add(criarGraficoDistribuicao());
        painel.add(Box.createVerticalStrut(40));

        // ===== BOTAO =====
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(ConstantesVisuais.FONTE_NORMAL);
        btnFechar.setBackground(ConstantesVisuais.COR_SUCESSO);
        btnFechar.setForeground(ConstantesVisuais.COR_TEXTO_CLARO);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setPreferredSize(new Dimension(160, 38));
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.addActionListener(e -> dispose());

        painel.add(btnFechar);

        add(painel);
    }

    // ===== CARD ESTILO WORDLE =====
    private JPanel criarCardStat(String titulo, String valor) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(ConstantesVisuais.COR_FUNDO);

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(ConstantesVisuais.FONTE_ESTATISTICAS);
        lblValor.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(ConstantesVisuais.FONTE_PEQUENA);
        lblTitulo.setForeground(Color.GRAY);

        card.add(lblValor, BorderLayout.CENTER);
        card.add(lblTitulo, BorderLayout.SOUTH);

        return card;
    }

    // ===== GRAFICO HORIZONTAL =====
    private JPanel criarGraficoDistribuicao() {

        JPanel painelGrafico = new JPanel();
        painelGrafico.setLayout(new BoxLayout(painelGrafico, BoxLayout.Y_AXIS));
        painelGrafico.setBackground(ConstantesVisuais.COR_FUNDO);
        painelGrafico.setAlignmentX(Component.LEFT_ALIGNMENT);

        int maxTentativas = ConstantesVisuais.MAX_TENTATIVAS;

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
        linha.setBackground(ConstantesVisuais.COR_FUNDO);
        linha.setMaximumSize(new Dimension(420, 25));

        JLabel lblNumero = new JLabel(String.valueOf(tentativa));
        lblNumero.setFont(ConstantesVisuais.FONTE_NORMAL);
        lblNumero.setPreferredSize(new Dimension(20, 20));

        JPanel barra = new JPanel();
        barra.setLayout(new BorderLayout());
        barra.setBackground(ConstantesVisuais.COR_CORRETA);
        barra.setPreferredSize(new Dimension(Math.max(20, valor * 25), 20));

        JLabel lblValor = new JLabel(String.valueOf(valor), SwingConstants.RIGHT);
        lblValor.setForeground(ConstantesVisuais.COR_TEXTO_CLARO);
        lblValor.setFont(ConstantesVisuais.FONTE_PEQUENA);
        lblValor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        barra.add(lblValor, BorderLayout.EAST);

        linha.add(lblNumero, BorderLayout.WEST);
        linha.add(barra, BorderLayout.CENTER);

        return linha;
    }
}


    
