package view;

import model.Estatisticas;
import util.ConstantesVisuais;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Painel Estatísticas - VERSÃO ULTRA PREMIUM
 * Design inspirado em dashboards modernos com Glassmorphism sutil e feedbacks táteis.
 */
public class PainelEstatisticas extends JPanel {

    private JLabel lblTotalJogos, lblVitorias, lblDerrotas, lblTaxaVitoria, lblSequenciaAtual, lblTentativasRestantes;
    private CardStat cardTentativasComp; // Referência direta para mudar cor dinamicamente

    public PainelEstatisticas() {
        configurarPainel();
        criarComponentes();
    }

    private void configurarPainel() {
        // Grid com espaçamento elegante
        setLayout(new GridLayout(2, 3, 12, 12));
        setBackground(new Color(248, 250, 252)); // Slate 50 (Fundo ultra limpo)
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setPreferredSize(new Dimension(500, 150));
    }

    private void criarComponentes() {
        lblTotalJogos = new JLabel("0");
        lblVitorias = new JLabel("0");
        lblDerrotas = new JLabel("0");
        lblTaxaVitoria = new JLabel("0%");
        lblSequenciaAtual = new JLabel("0");
        lblTentativasRestantes = new JLabel("6");

        // Adicionando os cards com paleta moderna
        add(new CardStat("TOTAL JOGOS", lblTotalJogos, new Color(37, 99, 235)));      // Blue 600
        add(new CardStat("VITÓRIAS", lblVitorias, new Color(22, 163, 74)));        // Green 600
        add(new CardStat("DERROTAS", lblDerrotas, new Color(220, 38, 38)));        // Red 600
        add(new CardStat("WIN RATE", lblTaxaVitoria, new Color(124, 58, 237)));      // Violet 600
        add(new CardStat("STREAK", lblSequenciaAtual, new Color(217, 119, 6)));      // Amber 600
        
        cardTentativasComp = new CardStat("TENTATIVAS", lblTentativasRestantes, new Color(71, 85, 105)); // Slate 600
        add(cardTentativasComp);
    }

    public void atualizar(Estatisticas estatisticas) {
        if (estatisticas == null) return;
        lblTotalJogos.setText(String.valueOf(estatisticas.getTotalPartidas()));
        lblVitorias.setText(String.valueOf(estatisticas.getPartidasGanhas()));
        lblDerrotas.setText(String.valueOf(estatisticas.getPartidasPerdidas()));
        lblTaxaVitoria.setText(String.format("%.0f%%", estatisticas.getPorcentagemVitorias()));
        lblSequenciaAtual.setText(String.valueOf(estatisticas.getSequenciaAtual()));
        repaint();
    }

    public void atualizarTentativas(int tentativas) {
        lblTentativasRestantes.setText(String.valueOf(tentativas));
        
        // Mudança de cor lógica sem recriar o objeto
        Color novaCor = switch (tentativas) {
            case 0, 1 -> new Color(220, 38, 38);
            case 2, 3 -> new Color(245, 158, 11);
            default -> new Color(71, 85, 105);
        };
        
        cardTentativasComp.setCorBase(novaCor);
        cardTentativasComp.repaint();
    }

    public void resetar() {
        atualizarTentativas(6);
    }

    // --- CLASSE INTERNA PARA O CARD CUSTOMIZADO ---
    
    private class CardStat extends JPanel {
        private final String titulo;
        private final JLabel lblValor;
        private Color corBase;
        private boolean isHovered = false;

        public CardStat(String titulo, JLabel lblValor, Color corBase) {
            this.titulo = titulo;
            this.lblValor = lblValor;
            this.corBase = corBase;
            
            setOpaque(false);
            setLayout(new BorderLayout());
            
            // Estilização do Label de Valor
            lblValor.setFont(new Font("Inter", Font.BOLD, 22));
            lblValor.setForeground(Color.WHITE);
            lblValor.setHorizontalAlignment(SwingConstants.CENTER);

            // Estilização do Título
            JLabel lblTit = new JLabel(titulo, SwingConstants.CENTER);
            lblTit.setFont(new Font("SansSerif", Font.BOLD, 10));
            lblTit.setForeground(new Color(255, 255, 255, 180));
            lblTit.setBorder(new EmptyBorder(8, 0, 0, 0));

            add(lblTit, BorderLayout.NORTH);
            add(lblValor, BorderLayout.CENTER);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }
            });
        }

        public void setCorBase(Color cor) { this.corBase = cor; }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int arc = 20; // Cantos arredondados modernos

            // Efeito de elevação (Sombra ou Brilho no Hover)
            if (isHovered) {
                g2.setColor(new Color(corBase.getRed(), corBase.getGreen(), corBase.getBlue(), 40));
                g2.fillRoundRect(0, 0, w, h, arc, arc);
                g2.translate(0, -2); // Sobe o card levemente
            }

            // Gradiente Principal
            Color corClara = isHovered ? corBase.brighter() : corBase;
            GradientPaint gp = new GradientPaint(0, 0, corClara, 0, h, corBase.darker());
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, w - 1, h - 1, arc, arc);

            // Borda interna sutil para dar profundidade
            g2.setColor(new Color(255, 255, 255, 60));
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);

            g2.dispose();
        }
    }
}