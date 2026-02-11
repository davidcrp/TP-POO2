package view;

import model.Estatisticas;
import util.ConstantesVisuais;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Painel responsável por exibir estatísticas do jogador
 * VERSÃO PREMIUM - Design moderno com gradientes e sombras
 */
public class PainelEstatisticas extends JPanel {

    private JLabel lblTotalJogos;
    private JLabel lblVitorias;
    private JLabel lblDerrotas;
    private JLabel lblTaxaVitoria;
    private JLabel lblSequenciaAtual;
    private JLabel lblTentativasRestantes;

    private JPanel cardJogos;
    private JPanel cardVitorias;
    private JPanel cardDerrotas;
    private JPanel cardTaxa;
    private JPanel cardSequencia;
    private JPanel cardTentativas;

    public PainelEstatisticas() {
        configurarPainel();
        criarComponentes();
    }

    private void configurarPainel() {
        setLayout(new GridLayout(2, 3, 8, 5));
        setBackground(ConstantesVisuais.COR_FUNDO);
        setBorder(new EmptyBorder(4, 8, 4, 8));
        setPreferredSize(new Dimension(480, 80));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
    }

    private void criarComponentes() {
        lblTotalJogos = criarLabel("0");
        lblVitorias = criarLabel("0");
        lblDerrotas = criarLabel("0");
        lblTaxaVitoria = criarLabel("0%");
        lblSequenciaAtual = criarLabel("0");
        lblTentativasRestantes = criarLabel("6");

        cardJogos = criarCardStat("Jogos", lblTotalJogos, new Color(59, 130, 246));
        cardVitorias = criarCardStat("Vitorias", lblVitorias, new Color(16, 185, 129));
        cardDerrotas = criarCardStat("Derrotas", lblDerrotas, new Color(239, 68, 68));
        cardTaxa = criarCardStat("Taxa", lblTaxaVitoria, new Color(147, 51, 234));
        cardSequencia = criarCardStat("Sequencia", lblSequenciaAtual, new Color(245, 158, 11));
        cardTentativas = criarCardStat("Tentativas", lblTentativasRestantes, new Color(107, 114, 128));

        add(cardJogos);
        add(cardVitorias);
        add(cardDerrotas);
        add(cardTaxa);
        add(cardSequencia);
        add(cardTentativas);
    }

    /**
     * Cria um label estilizado para os valores
     */
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Cria um card moderno com gradiente e sombra
     */
    private JPanel criarCardStat(String titulo, JLabel lblValor, Color corPrincipal) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                
                // Anti-aliasing premium
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);

                // Sombra suave
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fillRoundRect(2, 3, getWidth() - 2, getHeight() - 2, 16, 16);

                // Gradiente diagonal
                Color corClara = corPrincipal;
                Color corEscura = corPrincipal.darker();
                GradientPaint gradient = new GradientPaint(
                    0, 0, corClara,
                    getWidth(), getHeight(), corEscura
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 3, 16, 16);

                // Brilho no topo
                GradientPaint brilho = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 40),
                    0, getHeight() / 2, new Color(255, 255, 255, 0)
                );
                g2.setPaint(brilho);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() / 2, 16, 16);

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setLayout(new BorderLayout(0, 1));
        card.setBorder(new EmptyBorder(5, 6, 5, 6));

        // Título
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 10));
        lblTitulo.setForeground(new Color(255, 255, 255, 200));

        lblValor.setForeground(Color.WHITE);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        // Efeito hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lblValor.setFont(new Font("Arial", Font.BOLD, 26));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                lblValor.setFont(new Font("Arial", Font.BOLD, 24));
            }
        });

        return card;
    }

    /**
     * Atualiza estatísticas
     */
    public void atualizar(Estatisticas estatisticas) {
        if (estatisticas == null) return;

        lblTotalJogos.setText(String.valueOf(estatisticas.getTotalPartidas()));
        lblVitorias.setText(String.valueOf(estatisticas.getPartidasGanhas()));
        lblDerrotas.setText(String.valueOf(estatisticas.getPartidasPerdidas()));
        lblTaxaVitoria.setText(
            String.format("%.0f%%", estatisticas.getPorcentagemVitorias())
        );
        lblSequenciaAtual.setText(
            String.valueOf(estatisticas.getSequenciaAtual())
        );

        repaint();
    }

    /**
     * Atualiza tentativas com cores dinâmicas
     */
    public void atualizarTentativas(int tentativas) {
        lblTentativasRestantes.setText(String.valueOf(tentativas));

        // Cor baseada em tentativas
        Color novaCor;
        if (tentativas <= 1) {
            novaCor = new Color(239, 68, 68); // Vermelho
        } else if (tentativas <= 2) {
            novaCor = new Color(251, 146, 60); // Laranja
        } else if (tentativas <= 3) {
            novaCor = new Color(245, 158, 11); // Amarelo
        } else {
            novaCor = new Color(107, 114, 128); // Cinza
        }

        // Recria card
        int index = getComponentZOrder(cardTentativas);
        remove(cardTentativas);
        cardTentativas = criarCardStat("Tentativas", lblTentativasRestantes, novaCor);
        add(cardTentativas, index);
        
        revalidate();
        repaint();
    }

    /**
     * Reseta o painel
     */
    public void resetar() {
        atualizarTentativas(6);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Fundo com gradiente sutil
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(250, 250, 250),
            0, getHeight(), new Color(245, 245, 245)
        );
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}