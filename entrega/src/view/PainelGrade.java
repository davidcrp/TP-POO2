package view;

import javax.swing.*;
import java.awt.*;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// painel que representa a grade do jogo (6 linhas x 5 colunas)
public class PainelGrade extends JPanel {

    // quantidade de linhas (tentativas)
    private static final int NUM_LINHAS = 6;

    // quantidade de colunas (letras da palavra)
    private static final int NUM_COLUNAS = 5;

    // tamanho de cada celula
    private static final int TAMANHO_CELULA = 62;

    // espaco entre celulas
    private static final int ESPACAMENTO = 5;

    // matriz de paineis de letra
    private PainelLetra[][] paineis;

    // controla linha atual
    private int linhaAtual;

    // controla coluna atual
    private int colunaAtual;

    // construtor
    public PainelGrade() {

        paineis = new PainelLetra[NUM_LINHAS][NUM_COLUNAS];

        linhaAtual = 0;
        colunaAtual = 0;

        configurarPainel();
        criarGrade();
    }

    // configura layout e tamanho do painel
    private void configurarPainel() {

        setLayout(new GridLayout(
                NUM_LINHAS,
                NUM_COLUNAS,
                ESPACAMENTO,
                ESPACAMENTO
        ));

        // fundo escuro para contraste
        setBackground(new Color(18, 18, 19));

        int larguraTotal =
                (NUM_COLUNAS * TAMANHO_CELULA)
                + ((NUM_COLUNAS - 1) * ESPACAMENTO);

        int alturaTotal =
                (NUM_LINHAS * TAMANHO_CELULA)
                + ((NUM_LINHAS - 1) * ESPACAMENTO);

        setPreferredSize(new Dimension(larguraTotal, alturaTotal));
        setMaximumSize(new Dimension(larguraTotal, alturaTotal));

        // padding invisivel
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    // cria todas as celulas da grade
    private void criarGrade() {

        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {

                paineis[i][j] = new PainelLetra();
                add(paineis[i][j]);
            }
        }

        atualizarPainelAtivo();
    }

    // adiciona letra na linha atual
    public boolean adicionarLetra(char c) {

        if (colunaAtual >= NUM_COLUNAS) {
            return false;
        }

        paineis[linhaAtual][colunaAtual]
                .setCaractere(Character.toUpperCase(c));

        colunaAtual++;

        atualizarPainelAtivo();
        return true;
    }

    // remove ultima letra digitada
    public boolean removerLetra() {

        if (colunaAtual <= 0) {
            return false;
        }

        colunaAtual--;

        paineis[linhaAtual][colunaAtual].limpar();

        atualizarPainelAtivo();
        return true;
    }

    // retorna palavra formada na linha atual
    public String getPalavraAtual() {

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < NUM_COLUNAS; j++) {

            char c = paineis[linhaAtual][j]
                    .getCaractere();

            if (c != ' ') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    // verifica se linha atual esta completa
    public boolean linhaAtualCompleta() {
        return colunaAtual == NUM_COLUNAS;
    }

    // atualiza qual celula esta ativa
    private void atualizarPainelAtivo() {

        // desativa todas
        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[i][j].setAtivo(false);
            }
        }

        // ativa proxima posicao
        if (linhaAtual < NUM_LINHAS
                && colunaAtual < NUM_COLUNAS) {

            paineis[linhaAtual][colunaAtual]
                    .setAtivo(true);
        }
    }

    // atualiza visual da linha apos tentativa
    public void atualizarLinhaAtual(Object palavra) {

        if (palavra == null) return;

        try {

            // usa reflexao para acessar metodo getLetra
            for (int j = 0; j < NUM_COLUNAS; j++) {

                Object letra =
                        palavra.getClass()
                                .getMethod("getLetra", int.class)
                                .invoke(palavra, j);

                paineis[linhaAtual][j]
                        .setLetraObject(letra);

                paineis[linhaAtual][j]
                        .atualizarVisual();
            }

        } catch (Exception e) {
            System.err.println(
                "Aviso: Não foi possível processar palavra - "
                        + e.getMessage());
        }

        animarFlip();
        revalidate();
        repaint();
    }

    // atualiza estado visual de uma letra
    public void atualizarLetra(int coluna, String estado) {

        if (coluna < 0 || coluna >= NUM_COLUNAS)
            return;

        paineis[linhaAtual][coluna]
                .setEstado(estado);
    }

    // avanca para proxima linha
    public boolean avancarLinha() {

        if (linhaAtual >= NUM_LINHAS - 1) {
            return false;
        }

        linhaAtual++;
        colunaAtual = 0;

        atualizarPainelAtivo();
        return true;
    }

    // limpa toda a grade
    public void limpar() {

        for (int i = 0; i < NUM_LINHAS; i++) {
            for (int j = 0; j < NUM_COLUNAS; j++) {

                paineis[i][j].setBorder(null);
                paineis[i][j].limpar();
            }
        }

        linhaAtual = 0;
        colunaAtual = 0;

        atualizarPainelAtivo();
        repaint();
    }

    // animacao de erro (shake)
    public void animarShake() {

        // adiciona borda vermelha
        for (int j = 0; j < NUM_COLUNAS; j++) {
            paineis[linhaAtual][j]
                    .setBorder(
                        BorderFactory.createLineBorder(
                            new Color(220, 38, 38), 2));
        }

        // remove borda apos 400ms
        Timer timer = new Timer(400, e -> {
            for (int j = 0; j < NUM_COLUNAS; j++) {
                paineis[linhaAtual][j].setBorder(null);
                paineis[linhaAtual][j].atualizarVisual();
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    // animacao flip ao revelar letras
    public void animarFlip() {

        for (int j = 0; j < NUM_COLUNAS; j++) {

            final int coluna = j;

            Timer timer = new Timer(j * 150, e ->
                paineis[linhaAtual][coluna].repaint()
            );

            timer.setRepeats(false);
            timer.start();
        }
    }

    // animacao de vitoria (bounce)
    public void animarVitoria() {

        for (int j = 0; j < NUM_COLUNAS; j++) {

            final int coluna = j;
            final PainelLetra painel =
                    paineis[linhaAtual][coluna];

            Timer timer = new Timer(j * 80, e -> {

                Timer bounceTimer = new Timer(10, null);

                final int[] offset = {0};
                final boolean[] descendo = {false};

                bounceTimer.addActionListener(evt -> {

                    if (!descendo[0]) {
                        offset[0] -= 2;
                        if (offset[0] <= -15)
                            descendo[0] = true;
                    } else {
                        offset[0] += 2;
                        if (offset[0] >= 0) {
                            offset[0] = 0;
                            ((Timer) evt.getSource()).stop();
                        }
                    }

                    Point pos = painel.getLocation();
                    painel.setLocation(
                            pos.x,
                            pos.y + offset[0]);
                });

                bounceTimer.start();
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    // getters simples

    public int getLinhaAtual() {
        return linhaAtual;
    }

    public int getColunaAtual() {
        return colunaAtual;
    }

    public PainelLetra getPainel(int linha, int coluna) {

        if (linha < 0 || linha >= NUM_LINHAS)
            return null;

        if (coluna < 0 || coluna >= NUM_COLUNAS)
            return null;

        return paineis[linha][coluna];
    }

    public int getTentativasUsadas() {
        return linhaAtual;
    }

    public boolean temTentativasDisponiveis() {
        return linhaAtual < NUM_LINHAS;
    }

    // melhora qualidade de renderizacao
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
    }
}
