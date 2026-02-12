package view;

import javax.swing.*;
import java.awt.*;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */


public class PainelGrade extends JPanel {

    private static final int NUM_LINHAS = 6;
    private static final int NUM_COLUNAS = 5;
    private static final int TAMANHO_CELULA = 62;
    private static final int ESPACAMENTO = 5;

    private PainelLetra[][] paineis;
    private int linhaAtual;
    private int colunaAtual;

    public PainelGrade() {
        paineis = new PainelLetra[NUM_LINHAS][NUM_COLUNAS];
        linhaAtual = 0;
        colunaAtual = 0;

        configurarPainel();
        criarGrade();
    }

    private void configurarPainel() {
        setLayout(new GridLayout(NUM_LINHAS, NUM_COLUNAS, ESPACAMENTO, ESPACAMENTO));
        
        // Fundo MAIS ESCURO para contraste com as células
        setBackground(new Color(18, 18, 19));
        
        int larguraTotal = (NUM_COLUNAS * TAMANHO_CELULA) + ((NUM_COLUNAS - 1) * ESPACAMENTO);
        int alturaTotal = (NUM_LINHAS * TAMANHO_CELULA) + ((NUM_LINHAS - 1) * ESPACAMENTO);

        setPreferredSize(new Dimension(larguraTotal, alturaTotal));
        setMaximumSize(new Dimension(larguraTotal, alturaTotal));
        
        // Borda invisível para padding
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    private void criarGrade() {
        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[i][j] = new PainelLetra();
                add(paineis[i][j]);
            }
        }
        atualizarPainelAtivo();
    }

    public boolean adicionarLetra(char c) {
        if (colunaAtual >= NUM_COLUNAS) {
            return false;
        }

        paineis[linhaAtual][colunaAtual].setCaractere(Character.toUpperCase(c));
        colunaAtual++;
        atualizarPainelAtivo();
        return true;
    }

    public boolean removerLetra() {
        if (colunaAtual <= 0) {
            return false;
        }

        colunaAtual--;
        paineis[linhaAtual][colunaAtual].limpar();
        atualizarPainelAtivo();
        return true;
    }

    public String getPalavraAtual() {
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < NUM_COLUNAS; j++) {
            char c = paineis[linhaAtual][j].getCaractere();
            if (c != ' ') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public boolean linhaAtualCompleta() {
        return colunaAtual == NUM_COLUNAS;
    }

    private void atualizarPainelAtivo() {
        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[i][j].setAtivo(false);
            }
        }

        if (linhaAtual < NUM_LINHAS && colunaAtual < NUM_COLUNAS) {
            paineis[linhaAtual][colunaAtual].setAtivo(true);
        }
    }

    public void atualizarLinhaAtual(Object palavra) {
        if (palavra == null) return;

        try {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                Object letra = palavra.getClass().getMethod("getLetra", int.class).invoke(palavra, j);
                paineis[linhaAtual][j].setLetraObject(letra);

                paineis[linhaAtual][j].atualizarVisual();
            }
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível processar palavra - " + e.getMessage());
        }

        animarFlip();
        revalidate();
        repaint();
    }

    public void atualizarLetra(int coluna, String estado) {
        if (coluna < 0 || coluna >= NUM_COLUNAS) return;
        paineis[linhaAtual][coluna].setEstado(estado);
    }

    public boolean avancarLinha() {
        if (linhaAtual >= NUM_LINHAS - 1) {
            return false;
        }

        linhaAtual++;
        colunaAtual = 0;
        atualizarPainelAtivo();
        return true;
    }

    public void limpar() {
        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[i][j].setBorder(null); // Remove bordas
                paineis[i][j].limpar();
            }
        }

        linhaAtual = 0;
        colunaAtual = 0;
        atualizarPainelAtivo();
        repaint();
    }

    public void animarShake() {
        for (int j = 0; j < NUM_COLUNAS; j++) {
            PainelLetra painel = paineis[linhaAtual][j];
            painel.setBorder(BorderFactory.createLineBorder(new Color(220, 38, 38), 2));
        }

        Timer timer = new Timer(400, e -> {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                // Remove a borda vermelha
                paineis[linhaAtual][j].setBorder(null);
                paineis[linhaAtual][j].atualizarVisual();
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

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

    public void animarVitoria() {
        for (int j = 0; j < NUM_COLUNAS; j++) {
            final int coluna = j;
            final PainelLetra painel = paineis[linhaAtual][coluna];
            
            Timer timer = new Timer(j * 80, e -> {
                Timer bounceTimer = new Timer(10, null);
                final int[] offset = {0};
                final boolean[] descendo = {false};
                
                bounceTimer.addActionListener(evt -> {
                    if (!descendo[0]) {
                        offset[0] -= 2;
                        if (offset[0] <= -15) {
                            descendo[0] = true;
                        }
                    } else {
                        offset[0] += 2;
                        if (offset[0] >= 0) {
                            offset[0] = 0;
                            ((Timer) evt.getSource()).stop();
                        }
                    }
                    
                    Point pos = painel.getLocation();
                    painel.setLocation(pos.x, pos.y + offset[0]);
                });
                
                bounceTimer.start();
            });
            
            timer.setRepeats(false);
            timer.start();
        }
    }

    public int getLinhaAtual() {
        return linhaAtual;
    }

    public int getColunaAtual() {
        return colunaAtual;
    }

    public PainelLetra getPainel(int linha, int coluna) {
        if (linha < 0 || linha >= NUM_LINHAS) return null;
        if (coluna < 0 || coluna >= NUM_COLUNAS) return null;
        return paineis[linha][coluna];
    }

    public int getTentativasUsadas() {
        return linhaAtual;
    }

    public boolean temTentativasDisponiveis() {
        return linhaAtual < NUM_LINHAS;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}