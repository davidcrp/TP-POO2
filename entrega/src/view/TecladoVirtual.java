package view;

import model.Letra;
import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Classe TecladoVirtual - Teclado virtual para entrada de letras
 * 
 * Esta classe cria um teclado QWERTY visual que permite ao jogador
 * inserir letras clicando nos botões. As teclas mudam de cor conforme
 * as letras são usadas nas tentativas.
 * 
 * @author [Seu Nome]
 * @version 1.0 - CORRIGIDO
 */
public class TecladoVirtual extends JPanel {
    
    // ==================== ATRIBUTOS ====================
    
    /** Mapa de caractere para botão */
    private Map<Character, JButton> teclas;
    
    /** Listener para eventos de tecla */
    private TecladoListener listener;
    
    // ==================== INTERFACE LISTENER ====================
    
    /**
     * Interface para notificar eventos do teclado
     */
    public interface TecladoListener {
        /** Chamado quando uma letra é pressionada */
        void onLetraPressionada(char letra);
        
        /** Chamado quando ENTER é pressionado */
        void onEnterPressionado();
        
        /** Chamado quando BACKSPACE é pressionado */
        void onBackspacePressionado();
    }
    
    // ==================== CONSTRUTORES ====================
    
    /**
     * Construtor padrão - cria o teclado
     */
    public TecladoVirtual() {
        this.teclas = new HashMap<>();
        configurarPainel();
        criarTeclado();
    }
    
    // ==================== CONFIGURAÇÃO ====================
    
    /**
     * Configura as propriedades do painel
     */
    private void configurarPainel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ConstantesVisuais.COR_FUNDO);
        setBorder(BorderFactory.createEmptyBorder(
            ConstantesVisuais.MARGEM, 
            ConstantesVisuais.MARGEM, 
            ConstantesVisuais.MARGEM, 
            ConstantesVisuais.MARGEM));
        
        // Define altura mínima para garantir que todas as linhas apareçam
        int alturaTotal = (ConstantesVisuais.ALTURA_TECLA * 3) + 
                         (ConstantesVisuais.ESPACAMENTO_TECLA * 2) + 
                         (ConstantesVisuais.MARGEM * 2);
        setPreferredSize(new Dimension(500, alturaTotal));
        setMinimumSize(new Dimension(500, alturaTotal));
    }
    
    /**
     * Cria as três linhas do teclado
     */
    private void criarTeclado() {
        // Linha 1: QWERTYUIOP
        add(criarLinhaTeclado(ConstantesVisuais.TECLADO_LINHA1, false, false));
        add(Box.createVerticalStrut(ConstantesVisuais.ESPACAMENTO_TECLA));
        
        // Linha 2: ASDFGHJKL
        add(criarLinhaTeclado(ConstantesVisuais.TECLADO_LINHA2, false, false));
        add(Box.createVerticalStrut(ConstantesVisuais.ESPACAMENTO_TECLA));
        
        // Linha 3: ENTER + ZXCVBNM + BACKSPACE
        add(criarLinhaTeclado(ConstantesVisuais.TECLADO_LINHA3, true, true));
    }
    
    /**
     * Cria uma linha do teclado - VERSÃO CORRIGIDA
     * 
     * @param letras String com as letras da linha
     * @param comEnter Se deve incluir ENTER no início
     * @param comBackspace Se deve incluir BACKSPACE no final
     * @return JPanel com a linha de teclas
     */
    private JPanel criarLinhaTeclado(String letras, boolean comEnter, boolean comBackspace) {
        JPanel linha = new JPanel();
        linha.setLayout(new FlowLayout(FlowLayout.CENTER, 
            ConstantesVisuais.ESPACAMENTO_TECLA, 0));
        linha.setBackground(ConstantesVisuais.COR_FUNDO);
        
        // Define altura máxima para evitar expansão vertical
        linha.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, 
            ConstantesVisuais.ALTURA_TECLA + 10
        ));
        
        // Botão ENTER
        if (comEnter) {
            JButton btnEnter = criarBotaoEspecial(ConstantesVisuais.TEXTO_ENTER);
            btnEnter.addActionListener(e -> {
                if (listener != null) {
                    listener.onEnterPressionado();
                }
            });
            linha.add(btnEnter);
        }
        
        // Botões de letras
        for (char c : letras.toCharArray()) {
            JButton btn = criarBotaoLetra(c);
            teclas.put(c, btn);
            linha.add(btn);
        }
        
        // Botão BACKSPACE
        if (comBackspace) {
            JButton btnBackspace = criarBotaoEspecial(ConstantesVisuais.TEXTO_BACKSPACE);
            btnBackspace.addActionListener(e -> {
                if (listener != null) {
                    listener.onBackspacePressionado();
                }
            });
            linha.add(btnBackspace);
        }
        
        return linha;
    }
    
    /**
     * Cria um botão de letra - VERSÃO CORRIGIDA
     * 
     * @param letra Caractere do botão
     * @return JButton configurado
     */
    private JButton criarBotaoLetra(char letra) {
        JButton btn = new JButton(String.valueOf(letra));
        
        // Define tamanhos fixos
        Dimension tamanho = new Dimension(
            ConstantesVisuais.LARGURA_TECLA, 
            ConstantesVisuais.ALTURA_TECLA
        );
        
        btn.setPreferredSize(tamanho);
        btn.setMinimumSize(tamanho);
        btn.setMaximumSize(tamanho);
        
        // Configurações visuais
        btn.setFont(ConstantesVisuais.FONTE_TECLADO);
        btn.setBackground(ConstantesVisuais.COR_FUNDO_TECLADO);
        btn.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        
        // Adiciona borda arredondada sutil
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        
        // Adiciona ação
        btn.addActionListener(e -> {
            if (listener != null) {
                listener.onLetraPressionada(letra);
            }
        });
        
        return btn;
    }
    
    /**
     * Cria um botão especial (ENTER ou BACKSPACE) - VERSÃO CORRIGIDA
     * 
     * @param texto Texto do botão
     * @return JButton configurado
     */
    private JButton criarBotaoEspecial(String texto) {
        JButton btn = new JButton(texto);
        
        // Botões especiais são mais largos
        int largura = (int) (ConstantesVisuais.LARGURA_TECLA * 1.5);
        Dimension tamanho = new Dimension(largura, ConstantesVisuais.ALTURA_TECLA);
        
        btn.setPreferredSize(tamanho);
        btn.setMinimumSize(tamanho);
        btn.setMaximumSize(tamanho);
        
        btn.setFont(ConstantesVisuais.FONTE_PEQUENA);
        btn.setBackground(ConstantesVisuais.COR_FUNDO_TECLADO);
        btn.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        
        // Adiciona borda
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        
        return btn;
    }
    
    // ==================== MÉTODOS DE ATUALIZAÇÃO ====================
    
    /**
     * Atualiza as cores das teclas baseado nas letras usadas
     * 
     * @param corretas Letras corretas (verde)
     * @param posicaoErrada Letras em posição errada (amarelo)
     * @param ausentes Letras ausentes (cinza)
     */
    public void atualizarCoresTeclas(Set<Character> corretas, 
                                      Set<Character> posicaoErrada, 
                                      Set<Character> ausentes) {
        for (Map.Entry<Character, JButton> entry : teclas.entrySet()) {
            char c = entry.getKey();
            JButton btn = entry.getValue();
            
            // Prioridade: correta > posição errada > ausente
            if (corretas.contains(c)) {
                btn.setBackground(ConstantesVisuais.COR_CORRETA);
                btn.setForeground(ConstantesVisuais.COR_TEXTO_CLARO);
            } else if (posicaoErrada.contains(c)) {
                btn.setBackground(ConstantesVisuais.COR_POSICAO_ERRADA);
                btn.setForeground(ConstantesVisuais.COR_TEXTO_CLARO);
            } else if (ausentes.contains(c)) {
                btn.setBackground(ConstantesVisuais.COR_AUSENTE);
                btn.setForeground(ConstantesVisuais.COR_TEXTO_CLARO);
            }
        }
        
        repaint();
    }
    
    /**
     * Atualiza a cor de uma tecla específica
     * 
     * @param letra Caractere da tecla
     * @param estado Estado da letra
     */
    public void atualizarCorTecla(char letra, Letra.Estado estado) {
        JButton btn = teclas.get(Character.toUpperCase(letra));
        if (btn == null) return;
        
        // Pega a cor atual para comparação
        Color corAtual = btn.getBackground();
        
        // Define a nova cor baseado no estado
        Color novaCor;
        Color novaCorTexto;
        
        switch (estado) {
            case CORRETA:
                // Verde sempre sobrescreve qualquer cor
                novaCor = ConstantesVisuais.COR_CORRETA;
                novaCorTexto = ConstantesVisuais.COR_TEXTO_CLARO;
                break;
                
            case POSICAO_ERRADA:
                // Amarelo NÃO sobrescreve verde
                if (corAtual.equals(ConstantesVisuais.COR_CORRETA)) {
                    return; // Mantém verde
                }
                novaCor = ConstantesVisuais.COR_POSICAO_ERRADA;
                novaCorTexto = ConstantesVisuais.COR_TEXTO_CLARO;
                break;
                
            case AUSENTE:
                // Cinza NÃO sobrescreve verde nem amarelo
                if (corAtual.equals(ConstantesVisuais.COR_CORRETA) ||
                    corAtual.equals(ConstantesVisuais.COR_POSICAO_ERRADA)) {
                    return; // Mantém verde ou amarelo
                }
                novaCor = ConstantesVisuais.COR_AUSENTE;
                novaCorTexto = ConstantesVisuais.COR_TEXTO_CLARO;
                break;
                
            default:
                return; // Estado indefinido, não atualiza
        }
        
        // Aplica as cores
        btn.setBackground(novaCor);
        btn.setForeground(novaCorTexto);
        btn.repaint();
    }
    
    /**
     * Reseta todas as teclas para a cor padrão
     */
    public void resetar() {
        for (JButton btn : teclas.values()) {
            btn.setBackground(ConstantesVisuais.COR_FUNDO_TECLADO);
            btn.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);
        }
        repaint();
    }
    
    // ==================== SETTERS ====================
    
    /**
     * Define o listener para eventos do teclado
     * 
     * @param listener Implementação de TecladoListener
     */
    public void setListener(TecladoListener listener) {
        this.listener = listener;
    }
}