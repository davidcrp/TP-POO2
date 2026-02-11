package util;

import java.awt.Color;
import java.awt.Font;

/**
 * Classe utilitária que centraliza cores, fontes e dimensões da interface
 * VERSÃO OTIMIZADA - Valores ajustados para layout perfeito
 */
public final class ConstantesVisuais {

    // Impede a instanciação da classe
    private ConstantesVisuais() {
        throw new UnsupportedOperationException("Classe utilitária não pode ser instanciada");
    }

    // ==================== CORES PRINCIPAIS ====================
    // Estilo Termo / Wordle oficial

    /** Letra na posição correta (verde) */
    public static final Color COR_CORRETA = new Color(106, 170, 100);

    /** Letra existente em posição errada (amarelo) */
    public static final Color COR_POSICAO_ERRADA = new Color(201, 180, 88);

    /** Letra ausente na palavra (cinza escuro) */
    public static final Color COR_AUSENTE = new Color(120, 124, 126);

    /** Célula vazia ou indefinida (cinza claro) */
    public static final Color COR_INDEFINIDO = new Color(211, 214, 218);

    /** Fundo padrão da interface (branco) */
    public static final Color COR_FUNDO = new Color(255, 255, 255);

    /** Fundo do teclado virtual (cinza médio) */
    public static final Color COR_FUNDO_TECLADO = new Color(209, 213, 219);

    /** Cor do texto em células coloridas (branco) */
    public static final Color COR_TEXTO_CLARO = Color.WHITE;

    /** Cor do texto em células claras (preto) */
    public static final Color COR_TEXTO_ESCURO = Color.BLACK;

    /** Borda padrão das células (cinza claro) */
    public static final Color COR_BORDA = new Color(211, 214, 218);

    /** Borda da célula ativa (cinza médio escuro) */
    public static final Color COR_BORDA_ATIVA = new Color(135, 138, 140);

    // ==================== CORES AUXILIARES ====================

    /** Cor usada para mensagens de sucesso (verde escuro) */
    public static final Color COR_SUCESSO = new Color(46, 125, 50);

    /** Cor usada para mensagens de erro (vermelho) */
    public static final Color COR_ERRO = new Color(211, 47, 47);

    /** Cor usada para avisos (laranja) */
    public static final Color COR_AVISO = new Color(237, 108, 2);

    /** Cor usada para informações (azul) */
    public static final Color COR_INFO = new Color(33, 150, 243);

    // ==================== FONTES ====================

    /** Fonte principal das letras do jogo (grande e bold) */
    public static final Font FONTE_LETRA = new Font("Arial", Font.BOLD, 32);

    /** Fonte do teclado virtual (média e bold) */
    public static final Font FONTE_TECLADO = new Font("Arial", Font.BOLD, 14);

    /** Fonte de títulos (grande e bold) */
    public static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 32);

    /** Fonte de subtítulos (média e bold) */
    public static final Font FONTE_SUBTITULO = new Font("Arial", Font.BOLD, 18);

    /** Fonte de texto comum (normal) */
    public static final Font FONTE_NORMAL = new Font("Arial", Font.PLAIN, 14);

    /** Fonte usada nas estatísticas (grande e bold) */
    public static final Font FONTE_ESTATISTICAS = new Font("Arial", Font.BOLD, 20);

    /** Fonte pequena para labels (pequena) */
    public static final Font FONTE_PEQUENA = new Font("Arial", Font.PLAIN, 11);

    // ==================== DIMENSÕES DA INTERFACE ====================

    /** Tamanho da célula de letra (quadrado) */
    public static final int TAMANHO_CELULA = 56;

    /** Espaço entre células da grade */
    public static final int ESPACAMENTO_CELULA = 4;

    /** Largura das teclas do teclado virtual */
    public static final int LARGURA_TECLA = 40;

    /** Altura das teclas do teclado virtual */
    public static final int ALTURA_TECLA = 52;

    /** Espaço entre teclas do teclado */
    public static final int ESPACAMENTO_TECLA = 5;

    /** Largura da janela principal */
    public static final int LARGURA_JANELA = 540;

    /** Altura da janela principal */
    public static final int ALTURA_JANELA = 820;

    /** Margem padrão da interface */
    public static final int MARGEM = 10;

    /** Espessura da borda das células */
    public static final int ESPESSURA_BORDA = 2;

    // ==================== CONFIGURAÇÕES DE ANIMAÇÃO ====================

    /** Duração da animação de flip (ms) */
    public static final int DURACAO_FLIP = 250;

    /** Duração da animação de shake (ms) */
    public static final int DURACAO_SHAKE = 400;

    /** Duração da animação de bounce (ms) */
    public static final int DURACAO_BOUNCE = 100;

    /** Delay entre animação de letras (ms) */
    public static final int DELAY_LETRA = 100;

    // ==================== LAYOUT DO TECLADO VIRTUAL ====================

    /** Primeira linha do teclado QWERTY */
    public static final String TECLADO_LINHA1 = "QWERTYUIOP";

    /** Segunda linha do teclado QWERTY */
    public static final String TECLADO_LINHA2 = "ASDFGHJKL";

    /** Terceira linha do teclado QWERTY */
    public static final String TECLADO_LINHA3 = "ZXCVBNM";

    /** Texto do botão ENTER */
    public static final String TEXTO_ENTER = "ENTER";

    /** Símbolo do botão BACKSPACE */
    public static final String TEXTO_BACKSPACE = "DEL";


    // ==================== TEXTOS PADRÃO DA INTERFACE ====================

    /** Título principal do jogo */
    public static final String TITULO_JOGO = "TERMO";

    /** Mensagem de vitória */
    public static final String MSG_VITORIA = "Parabéns! Você acertou!";

    /** Mensagem de derrota (palavra será concatenada) */
    public static final String MSG_DERROTA = "Que pena! A palavra era: ";

    /** Mensagem quando palavra não existe no dicionário */
    public static final String MSG_PALAVRA_INVALIDA = "Palavra não encontrada no dicionário";

    /** Mensagem quando palavra está incompleta */
    public static final String MSG_PALAVRA_INCOMPLETA = "Complete a palavra com 5 letras";

    // ==================== VALIDAÇÕES E LIMITES ====================

    /** Número de letras em cada palavra */
    public static final int TAMANHO_PALAVRA = 5;

    /** Número máximo de tentativas */
    public static final int MAX_TENTATIVAS = 6;
}