package view;

import model.Estatisticas;
import util.ConstantesVisuais;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Painel responsável por exibir estatísticas do jogador
 * VERSÃO MELHORADA - Visual moderno e profissional
 */
public class PainelEstatisticas extends JPanel {

    // labels das estatisticas
    private JLabel lblTotalJogos;
    private JLabel lblVitorias;
    private JLabel lblDerrotas;
    private JLabel lblTaxaVitoria;
    private JLabel lblSequenciaAtual;
    private JLabel lblTentativasRestantes;

    // paineis individuais para cada stat (visual card)
    private JPanel cardJogos;
    private JPanel cardVitorias;
    private JPanel cardDerrotas;
    private JPanel cardTaxa;
    private JPanel cardSequencia;
    private JPanel cardTentativas;

    // cria o painel de estatisticas
    public PainelEstatisticas() {
        configurarPainel();
        criarComponentes();
    }

    // configuracao visual do painel - MELHORADO
    private void configurarPainel() {
        setLayout(new GridLayout(2, 3, 12, 8)); // Espaçamento aumentado
        setBackground(ConstantesVisuais.COR_FUNDO);

        // Borda mais limpa e moderna
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true), // Borda arredondada
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Define altura preferencial
        setPreferredSize(new Dimension(480, 110));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
    }

    // cria os componentes com visual de cards - NOVO
    private void criarComponentes() {
        // Cria cards para cada estatística
        cardJogos = criarCardStat("Jogos", "0");
        cardVitorias = criarCardStat("Vitórias", "0");
        cardDerrotas = criarCardStat("Derrotas", "0");
        cardTaxa = criarCardStat("Taxa", "0%");
        cardSequencia = criarCardStat("Sequência", "0");
        cardTentativas = criarCardStat("Tentativas", "6");

        // Extrai labels dos cards
        lblTotalJogos = extrairLabelDoCard(cardJogos);
        lblVitorias = extrairLabelDoCard(cardVitorias);
        lblDerrotas = extrairLabelDoCard(cardDerrotas);
        lblTaxaVitoria = extrairLabelDoCard(cardTaxa);
        lblSequenciaAtual = extrairLabelDoCard(cardSequencia);
        lblTentativasRestantes = extrairLabelDoCard(cardTentativas);

        // Adiciona os cards
        add(cardJogos);
        add(cardVitorias);
        add(cardDerrotas);
        add(cardTaxa);
        add(cardSequencia);
        add(cardTentativas);
    }

    /**
     * Cria um card de estatística com título e valor
     * @param titulo Título da stat
     * @param valor Valor inicial
     * @return JPanel formatado como card
     */
    private JPanel criarCardStat(String titulo, String valor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 2));
        card.setBackground(new Color(248, 249, 250));
        
        // Borda sutil
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(230, 230, 230), 1, true),
            new EmptyBorder(6, 5, 6, 5)
        ));

        // Label do título (menor, acima)
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(ConstantesVisuais.FONTE_PEQUENA);
        lblTitulo.setForeground(new Color(100, 100, 100));

        // Label do valor (maior, abaixo)
        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(ConstantesVisuais.FONTE_ESTATISTICAS);
        lblValor.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        return card;
    }

    /**
     * Extrai o label de valor de um card
     * @param card Painel do card
     * @return JLabel com o valor
     */
    private JLabel extrairLabelDoCard(JPanel card) {
        Component[] componentes = card.getComponents();
        for (Component c : componentes) {
            if (c instanceof JLabel) {
                JLabel lbl = (JLabel) c;
                if (lbl.getFont().equals(ConstantesVisuais.FONTE_ESTATISTICAS)) {
                    return lbl;
                }
            }
        }
        return null;
    }

    // ==================== ATUALIZAÇÃO ====================

    /**
     * Atualiza todas as estatísticas exibidas
     * @param estatisticas Objeto com os dados
     */
    public void atualizar(Estatisticas estatisticas) {
        if (estatisticas == null) {
            return;
        }

        lblTotalJogos.setText(String.valueOf(estatisticas.getTotalPartidas()));
        lblVitorias.setText(String.valueOf(estatisticas.getPartidasGanhas()));
        lblDerrotas.setText(String.valueOf(estatisticas.getPartidasPerdidas()));
        
        lblTaxaVitoria.setText(
                String.format("%.0f%%", estatisticas.getPorcentagemVitorias())
        );
        
        lblSequenciaAtual.setText(
                String.valueOf(estatisticas.getSequenciaAtual())
        );

        // Destaca vitórias em verde
        if (estatisticas.getPartidasGanhas() > 0) {
            lblVitorias.setForeground(ConstantesVisuais.COR_SUCESSO);
        }

        // Destaca sequência se for boa
        if (estatisticas.getSequenciaAtual() >= 3) {
            lblSequenciaAtual.setForeground(ConstantesVisuais.COR_SUCESSO);
            cardSequencia.setBackground(new Color(232, 245, 233)); // Verde claro
        }

        repaint();
    }

    /**
     * Atualiza apenas as tentativas restantes
     * @param tentativas Número de tentativas
     */
    public void atualizarTentativas(int tentativas) {
        lblTentativasRestantes.setText(String.valueOf(tentativas));

        // Muda cor baseado no número de tentativas
        if (tentativas <= 1) {
            lblTentativasRestantes.setForeground(ConstantesVisuais.COR_ERRO);
            cardTentativas.setBackground(new Color(255, 235, 238)); // Vermelho claro
        } else if (tentativas <= 2) {
            lblTentativasRestantes.setForeground(ConstantesVisuais.COR_AVISO);
            cardTentativas.setBackground(new Color(255, 243, 224)); // Laranja claro
        } else {
            lblTentativasRestantes.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
            cardTentativas.setBackground(new Color(248, 249, 250)); // Cinza claro
        }
    }

    /**
     * Reseta o painel para o início da partida
     */
    public void resetar() {
        atualizarTentativas(6);
        
        // Reseta cores dos cards
        cardSequencia.setBackground(new Color(248, 249, 250));
        cardTentativas.setBackground(new Color(248, 249, 250));
        
        lblVitorias.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        lblSequenciaAtual.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
    }

    // ==================== RENDERIZAÇÃO ====================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Anti-aliasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
    }
}