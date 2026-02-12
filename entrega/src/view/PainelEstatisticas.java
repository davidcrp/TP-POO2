package view;

import model.Estatisticas;
import util.ConstantesVisuais;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// painel responsável por mostrar as estatísticas do jogador
public class PainelEstatisticas extends JPanel {

    // labels que mostram os valores
    private JLabel lblTotalJogos;
    private JLabel lblVitorias;
    private JLabel lblDerrotas;
    private JLabel lblTaxaVitoria;
    private JLabel lblSequenciaAtual;
    private JLabel lblTentativasRestantes;

    // cards visuais (caixinhas estilizadas)
    private JPanel cardJogos;
    private JPanel cardVitorias;
    private JPanel cardDerrotas;
    private JPanel cardTaxa;
    private JPanel cardSequencia;
    private JPanel cardTentativas;

    // construtor do painel
    public PainelEstatisticas() {
        configurarPainel();   // configura layout e aparência
        criarComponentes();   // cria os componentes visuais
    }

    // configura propriedades gerais do painel
    private void configurarPainel() {

        // layout 2 linhas x 3 colunas
        setLayout(new GridLayout(2, 3, 10, 8));

        // cor de fundo definida nas constantes visuais
        setBackground(ConstantesVisuais.COR_FUNDO);

        // espaçamento interno
        setBorder(new EmptyBorder(10, 20, 10, 20));

        // tamanho preferido
        setPreferredSize(new Dimension(500, 110));

        // tamanho máximo
        setMaximumSize(new Dimension(550, 110));

        //mantem centralizado
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // cria os labels e os cards
    private void criarComponentes() {

        // cria labels com valores iniciais
        lblTotalJogos = criarLabel("0");
        lblVitorias = criarLabel("0");
        lblDerrotas = criarLabel("0");
        lblTaxaVitoria = criarLabel("0%");
        lblSequenciaAtual = criarLabel("0");
        lblTentativasRestantes = criarLabel("6");

        // cria os cards com cores diferentes
        cardJogos = criarCardStat("Jogos", lblTotalJogos, new Color(59, 130, 246));
        cardVitorias = criarCardStat("Vitorias", lblVitorias, new Color(16, 185, 129));
        cardDerrotas = criarCardStat("Derrotas", lblDerrotas, new Color(239, 68, 68));
        cardTaxa = criarCardStat("Taxa", lblTaxaVitoria, new Color(147, 51, 234));
        cardSequencia = criarCardStat("Sequencia", lblSequenciaAtual, new Color(245, 158, 11));
        cardTentativas = criarCardStat("Tentativas", lblTentativasRestantes, new Color(107, 114, 128));

        // adiciona os cards no painel
        add(cardJogos);
        add(cardVitorias);
        add(cardDerrotas);
        add(cardTaxa);
        add(cardSequencia);
        add(cardTentativas);
    }

    // cria um label estilizado para mostrar os valores
    private JLabel criarLabel(String texto) {

        JLabel label = new JLabel(texto);

        // centraliza o texto
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // define fonte
        label.setFont(new Font("Arial", Font.BOLD, 18));

        // define cor branca
        label.setForeground(Color.WHITE);

        return label;
    }

    // cria um card visual com gradiente, sombra e efeito hover
    private JPanel criarCardStat(String titulo, JLabel lblValor, Color corPrincipal) {

        // cria um painel customizado sobrescrevendo paintComponent
        JPanel card = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                // ativa anti-aliasing (melhora qualidade visual)
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);

                // desenha sombra suave
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fillRoundRect(2, 3, getWidth() - 2, getHeight() - 2, 16, 16);

                // cria gradiente da cor principal
                Color corClara = corPrincipal;
                Color corEscura = corPrincipal.darker();

                GradientPaint gradient = new GradientPaint(
                    0, 0, corClara,
                    getWidth(), getHeight(), corEscura
                );

                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 3, 16, 16);

                // adiciona brilho na parte superior
                GradientPaint brilho = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 40),
                    0, getHeight() / 2, new Color(255, 255, 255, 0)
                );

                g2.setPaint(brilho);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() / 2, 16, 16);

                g2.dispose();
            }
        };

        // permite fundo transparente
        card.setOpaque(false);

        // layout interno do card
        card.setLayout(new BorderLayout(0, 1));

        // margem interna
        card.setBorder(new EmptyBorder(5, 6, 5, 6));

        // cria título do card
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 10));
        lblTitulo.setForeground(new Color(255, 255, 255, 200));

        // garante que o valor fique branco
        lblValor.setForeground(Color.WHITE);

        // adiciona título e valor
        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        // adiciona efeito hover (quando passa o mouse)
        card.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                // muda cursor
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // aumenta fonte
                lblValor.setFont(new Font("Arial", Font.BOLD, 26));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                // volta cursor normal
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                // volta fonte ao tamanho padrão
                lblValor.setFont(new Font("Arial", Font.BOLD, 24));
            }
        });

        return card;
    }

    // atualiza os valores das estatísticas
    public void atualizar(Estatisticas estatisticas) {

        if (estatisticas == null) return;

        // atualiza textos dos labels
        lblTotalJogos.setText(String.valueOf(estatisticas.getTotalPartidas()));
        lblVitorias.setText(String.valueOf(estatisticas.getPartidasGanhas()));
        lblDerrotas.setText(String.valueOf(estatisticas.getPartidasPerdidas()));

        lblTaxaVitoria.setText(
            String.format("%.0f%%", estatisticas.getPorcentagemVitorias())
        );

        lblSequenciaAtual.setText(
            String.valueOf(estatisticas.getSequenciaAtual())
        );

        repaint(); // redesenha painel
    }

    // atualiza o número de tentativas restantes
    public void atualizarTentativas(int tentativas) {

        lblTentativasRestantes.setText(String.valueOf(tentativas));

        // define nova cor baseada na quantidade de tentativas
        Color novaCor;

        if (tentativas <= 1) {
            novaCor = new Color(239, 68, 68); // vermelho
        } else if (tentativas <= 2) {
            novaCor = new Color(251, 146, 60); // laranja
        } else if (tentativas <= 3) {
            novaCor = new Color(245, 158, 11); // amarelo
        } else {
            novaCor = new Color(107, 114, 128); // cinza
        }

        // pega posição atual do card
        int index = getComponentZOrder(cardTentativas);

        // remove o card antigo
        remove(cardTentativas);

        // recria com nova cor
        cardTentativas = criarCardStat("Tentativas", lblTentativasRestantes, novaCor);

        // adiciona na mesma posição
        add(cardTentativas, index);

        revalidate(); // atualiza layout
        repaint();    // redesenha
    }

    // reseta painel para estado inicial
    public void resetar() {
        atualizarTentativas(6);
    }

    // sobrescreve pintura do fundo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // melhora qualidade de renderização
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        // cria gradiente suave de fundo
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(250, 250, 250),
            0, getHeight(), new Color(245, 245, 245)
        );

        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
