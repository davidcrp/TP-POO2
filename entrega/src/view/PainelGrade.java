package view;

import model.Palavra;
import model.Partida;
import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;

/**
 * Grade 6x5 onde as tentativas são exibidas
 * VERSÃO MELHORADA - Layout otimizado e visual profissional
 */
public class PainelGrade extends JPanel {

    private static final int NUM_LINHAS = Partida.MAX_TENTATIVAS;
    private static final int NUM_COLUNAS = Palavra.TAMANHO_PALAVRA;

    // matriz de paineis de letra
    private PainelLetra[][] paineis;

    // posicao atual de edicao
    private int linhaAtual;
    private int colunaAtual;

    // cria a grade vazia
    public PainelGrade() {
        paineis = new PainelLetra[NUM_LINHAS][NUM_COLUNAS];
        linhaAtual = 0;
        colunaAtual = 0;

        configurarPainel();
        criarGrade();
    }

    // configuracao visual da grade - MELHORADO
    private void configurarPainel() {
        // GridLayout com espaçamento entre células
        setLayout(new GridLayout(
                NUM_LINHAS,
                NUM_COLUNAS,
                ConstantesVisuais.ESPACAMENTO_CELULA,
                ConstantesVisuais.ESPACAMENTO_CELULA
        ));

        setBackground(ConstantesVisuais.COR_FUNDO);
        
        // Margem ao redor da grade
        setBorder(BorderFactory.createEmptyBorder(
                ConstantesVisuais.MARGEM * 2,  // Margem superior maior
                ConstantesVisuais.MARGEM,
                ConstantesVisuais.MARGEM,
                ConstantesVisuais.MARGEM
        ));

        // Define tamanho preferencial para centralização
        int larguraTotal = (NUM_COLUNAS * ConstantesVisuais.TAMANHO_CELULA) + 
                          ((NUM_COLUNAS - 1) * ConstantesVisuais.ESPACAMENTO_CELULA) +
                          (ConstantesVisuais.MARGEM * 2);
        
        int alturaTotal = (NUM_LINHAS * ConstantesVisuais.TAMANHO_CELULA) + 
                         ((NUM_LINHAS - 1) * ConstantesVisuais.ESPACAMENTO_CELULA) +
                         (ConstantesVisuais.MARGEM * 3);

        setPreferredSize(new Dimension(larguraTotal, alturaTotal));
        setMaximumSize(new Dimension(larguraTotal, alturaTotal));
    }

    // cria os paineis de letra - OTIMIZADO
    private void criarGrade() {
        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[i][j] = new PainelLetra();
                add(paineis[i][j]);
            }
        }

        atualizarPainelAtivo();
    }

    // ==================== MÉTODOS DE INTERAÇÃO ====================

    /**
     * Adiciona uma letra na linha atual
     * @param c Caractere a ser adicionado
     * @return true se adicionou com sucesso
     */
    public boolean adicionarLetra(char c) {
        if (colunaAtual >= NUM_COLUNAS) {
            return false;
        }

        paineis[linhaAtual][colunaAtual].setCaractere(
                Character.toUpperCase(c)
        );

        colunaAtual++;
        atualizarPainelAtivo();
        return true;
    }

    /**
     * Remove a última letra digitada
     * @return true se removeu com sucesso
     */
    public boolean removerLetra() {
        if (colunaAtual <= 0) {
            return false;
        }

        colunaAtual--;
        paineis[linhaAtual][colunaAtual].limpar();

        atualizarPainelAtivo();
        return true;
    }

    /**
     * Retorna a palavra digitada na linha atual
     * @return String com a palavra atual
     */
    public String getPalavraAtual() {
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < NUM_COLUNAS; j++) {
            char c = paineis[linhaAtual][j]
                    .getLetra()
                    .getCaractere();

            if (c != ' ') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Verifica se a linha atual está completa
     * @return true se tem 5 letras
     */
    public boolean linhaAtualCompleta() {
        return colunaAtual == NUM_COLUNAS;
    }

    // ==================== ATUALIZAÇÃO VISUAL ====================

    /**
     * Atualiza qual painel está ativo (cursor)
     */
    private void atualizarPainelAtivo() {
        // Desativa todos
        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[i][j].setAtivo(false);
            }
        }

        // Ativa o atual
        if (linhaAtual < NUM_LINHAS && colunaAtual < NUM_COLUNAS) {
            paineis[linhaAtual][colunaAtual].setAtivo(true);
        }
    }

    /**
     * Aplica o resultado de uma tentativa na linha atual
     * @param palavra Palavra com estados das letras
     */
    public void atualizarLinhaAtual(Palavra palavra) {
        if (palavra == null) {
            return;
        }

        for (int j = 0; j < NUM_COLUNAS; j++) {
            paineis[linhaAtual][j].setLetra(palavra.getLetra(j));
        }

        // Força repaint para animação suave
        repaint();
    }

    /**
     * Avança para a próxima linha
     * @return true se avançou com sucesso
     */
    public boolean avancarLinha() {
        if (linhaAtual >= NUM_LINHAS - 1) {
            return false;
        }

        linhaAtual++;
        colunaAtual = 0;
        atualizarPainelAtivo();
        return true;
    }

    /**
     * Limpa toda a grade
     */
    public void limpar() {
        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[i][j].limpar();
            }
        }

        linhaAtual = 0;
        colunaAtual = 0;
        atualizarPainelAtivo();
    }

    // ==================== ANIMAÇÕES ====================

    /**
     * Efeito de erro - pisca a linha em vermelho
     * VERSÃO MELHORADA com animação mais suave
     */
    public void animarShake() {
        // Salva bordas originais
        final java.util.List<javax.swing.border.Border> bordasOriginais = 
            new java.util.ArrayList<>();

        // Aplica borda vermelha
        for (int j = 0; j < NUM_COLUNAS; j++) {
            PainelLetra painel = paineis[linhaAtual][j];
            bordasOriginais.add(painel.getBorder());
            
            painel.setBorder(BorderFactory.createLineBorder(
                    ConstantesVisuais.COR_ERRO, 3
            ));
        }

        // Restaura bordas após 400ms
        Timer timer = new Timer(400, e -> {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[linhaAtual][j].atualizarVisual();
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Animação de flip das letras (revelação das cores)
     * Aplica um delay progressivo para efeito cascata
     */
    public void animarFlip() {
        for (int j = 0; j < NUM_COLUNAS; j++) {
            final int coluna = j;
            
            Timer timer = new Timer(j * 150, e -> {
                paineis[linhaAtual][coluna].repaint();
            });
            
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Animação de vitória - bounce nas letras corretas
     */
    public void animarVitoria() {
        for (int j = 0; j < NUM_COLUNAS; j++) {
            final int coluna = j;
            final PainelLetra painel = paineis[linhaAtual][coluna];
            final Point posicaoOriginal = painel.getLocation();
            
            Timer timer = new Timer(j * 100, e -> {
                // Pequeno bounce vertical
                Timer bounceTimer = new Timer(10, null);
                final int[] offset = {0};
                final boolean[] descendo = {false};
                
                bounceTimer.addActionListener(evt -> {
                    if (!descendo[0]) {
                        offset[0] -= 2;
                        if (offset[0] <= -10) {
                            descendo[0] = true;
                        }
                    } else {
                        offset[0] += 2;
                        if (offset[0] >= 0) {
                            offset[0] = 0;
                            ((Timer) evt.getSource()).stop();
                        }
                    }
                    
                    painel.setLocation(
                        posicaoOriginal.x, 
                        posicaoOriginal.y + offset[0]
                    );
                });
                
                bounceTimer.start();
            });
            
            timer.setRepeats(false);
            timer.start();
        }
    }

    // ==================== GETTERS ====================

    public int getLinhaAtual() {
        return linhaAtual;
    }

    public int getColunaAtual() {
        return colunaAtual;
    }

    public PainelLetra getPainel(int linha, int coluna) {
        if (linha < 0 || linha >= NUM_LINHAS) {
            return null;
        }
        if (coluna < 0 || coluna >= NUM_COLUNAS) {
            return null;
        }

        return paineis[linha][coluna];
    }

    /**
     * Retorna o número total de tentativas usadas
     */
    public int getTentativasUsadas() {
        return linhaAtual;
    }

    /**
     * Verifica se ainda há tentativas disponíveis
     */
    public boolean temTentativasDisponiveis() {
        return linhaAtual < NUM_LINHAS;
    }

    // ==================== RENDERIZAÇÃO ====================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Ativa anti-aliasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
        );
    }
}