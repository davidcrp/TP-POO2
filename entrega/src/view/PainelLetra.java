package view;

import model.Letra;
import util.ConstantesVisuais;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Painel visual que representa uma letra na grade
 * VERSÃO MELHORADA - Visual moderno e profissional
 */
public class PainelLetra extends JPanel {

    // letra exibida no painel
    private Letra letra;

    // label que mostra o caractere
    private JLabel labelLetra;

    // indica se o painel esta ativo
    private boolean ativo;

    // cria painel vazio
    public PainelLetra() {
        this(new Letra());
    }

    // cria painel com letra definida
    public PainelLetra(Letra letra) {
        this.letra = letra;
        this.ativo = false;
        configurarPainel();
        atualizarVisual();
    }

    // configuracao inicial do painel - MELHORADO
    private void configurarPainel() {
        // Define tamanhos fixos
        Dimension tamanho = new Dimension(
                ConstantesVisuais.TAMANHO_CELULA,
                ConstantesVisuais.TAMANHO_CELULA
        );

        setPreferredSize(tamanho);
        setMinimumSize(tamanho);
        setMaximumSize(tamanho);

        // Layout centralizado
        setLayout(new BorderLayout());
        
        // Torna o painel opaco para cores de fundo
        setOpaque(true);

        // Cria label com configurações otimizadas
        labelLetra = new JLabel("", SwingConstants.CENTER);
        labelLetra.setFont(ConstantesVisuais.FONTE_LETRA);
        labelLetra.setOpaque(false); // Transparente para usar fundo do painel
        
        add(labelLetra, BorderLayout.CENTER);

        // Borda inicial
        atualizarBorda(false);
    }

    // atualiza cores, texto e borda conforme estado - MELHORADO
    public void atualizarVisual() {
        // Atualiza texto
        char c = letra.getCaractere();
        labelLetra.setText(c == ' ' ? "" : String.valueOf(c));

        Color fundo;
        Color texto;
        boolean temLetra = c != ' ';

        switch (letra.getEstado()) {
            case CORRETA:
                fundo = ConstantesVisuais.COR_CORRETA;
                texto = ConstantesVisuais.COR_TEXTO_CLARO;
                break;

            case POSICAO_ERRADA:
                fundo = ConstantesVisuais.COR_POSICAO_ERRADA;
                texto = ConstantesVisuais.COR_TEXTO_CLARO;
                break;

            case AUSENTE:
                fundo = ConstantesVisuais.COR_AUSENTE;
                texto = ConstantesVisuais.COR_TEXTO_CLARO;
                break;

            default:
                // Se tem letra mas ainda não foi avaliada
                if (temLetra) {
                    fundo = ConstantesVisuais.COR_FUNDO;
                    texto = ConstantesVisuais.COR_TEXTO_ESCURO;
                } else {
                    fundo = ConstantesVisuais.COR_FUNDO;
                    texto = ConstantesVisuais.COR_TEXTO_ESCURO;
                }
                break;
        }

        setBackground(fundo);
        labelLetra.setForeground(texto);

        // Atualiza borda
        atualizarBorda(temLetra);

        repaint();
    }

    /**
     * Atualiza a borda do painel baseado no estado
     * @param temLetra Se o painel contém uma letra
     */
    private void atualizarBorda(boolean temLetra) {
        Color corBorda;
        int espessura = ConstantesVisuais.ESPESSURA_BORDA;

        // Define cor da borda baseado no estado
        if (ativo && letra.getEstado() == Letra.Estado.INDEFINIDO) {
            // Painel ativo (cursor está aqui)
            corBorda = ConstantesVisuais.COR_BORDA_ATIVA;
            espessura = 3; // Borda mais grossa quando ativo
        } else if (temLetra && letra.getEstado() == Letra.Estado.INDEFINIDO) {
            // Tem letra mas ainda não foi submetida
            corBorda = new Color(135, 138, 140);
        } else {
            // Vazio ou já avaliado
            corBorda = ConstantesVisuais.COR_BORDA;
        }

        // Cria borda com arredondamento visual (usando padding interno)
        setBorder(new CompoundBorder(
            new LineBorder(corBorda, espessura),
            new EmptyBorder(2, 2, 2, 2) // Padding interno
        ));
    }

    // ==================== GETTERS E SETTERS ====================

    public Letra getLetra() {
        return letra;
    }

    public void setLetra(Letra letra) {
        this.letra = letra;
        atualizarVisual();
    }

    public void setCaractere(char c) {
        letra.setCaractere(c);
        atualizarVisual();
    }

    public void setEstado(Letra.Estado estado) {
        letra.setEstado(estado);
        atualizarVisual();
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
        atualizarVisual();
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Limpa o painel completamente
     */
    public void limpar() {
        letra = new Letra();
        ativo = false;
        atualizarVisual();
    }

    /**
     * Verifica se o painel está vazio
     */
    public boolean isVazio() {
        return letra.getCaractere() == ' ';
    }

    /**
     * Override para melhor renderização
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Ativa antialiasing para texto mais suave
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
        );
    }
}