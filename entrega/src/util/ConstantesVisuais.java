package util;

import java.awt.Color;
import java.awt.Font;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// classe utilitaria que guarda todas as constantes visuais do jogo
// nao pode ser instanciada
public final class ConstantesVisuais {

    // impede criacao de objetos dessa classe
    private ConstantesVisuais() {
        throw new UnsupportedOperationException(
                "Classe utilitária não pode ser instanciada");
    }

    // ==================== CORES PRINCIPAIS ====================

    // cor verde quando a letra esta correta
    public static final Color COR_CORRETA = new Color(106, 170, 100);

    // cor amarela quando letra existe mas esta na posicao errada
    public static final Color COR_POSICAO_ERRADA = new Color(201, 180, 88);

    // cor cinza escuro quando letra nao existe na palavra
    public static final Color COR_AUSENTE = new Color(120, 124, 126);

    // cor cinza claro para celulas vazias
    public static final Color COR_INDEFINIDO = new Color(211, 214, 218);

    // fundo principal da interface
    public static final Color COR_FUNDO = new Color(255, 255, 255);

    // fundo do teclado virtual
    public static final Color COR_FUNDO_TECLADO = new Color(209, 213, 219);

    // texto branco (usado em celulas coloridas)
    public static final Color COR_TEXTO_CLARO = Color.WHITE;

    // texto preto (usado em fundos claros)
    public static final Color COR_TEXTO_ESCURO = Color.BLACK;

    // borda padrao das celulas
    public static final Color COR_BORDA = new Color(211, 214, 218);

    // borda quando celula esta ativa
    public static final Color COR_BORDA_ATIVA = new Color(135, 138, 140);

    // ==================== CORES AUXILIARES ====================

    // cor verde para mensagens de sucesso
    public static final Color COR_SUCESSO = new Color(46, 125, 50);

    // cor vermelha para mensagens de erro
    public static final Color COR_ERRO = new Color(211, 47, 47);

    // cor laranja para avisos
    public static final Color COR_AVISO = new Color(237, 108, 2);

    // cor azul para informacoes
    public static final Color COR_INFO = new Color(33, 150, 243);

    // ==================== FONTES ====================

    // fonte principal das letras do jogo
    public static final Font FONTE_LETRA =
            new Font("Arial", Font.BOLD, 32);

    // fonte usada no teclado virtual
    public static final Font FONTE_TECLADO =
            new Font("Arial", Font.BOLD, 14);

    // fonte usada em titulos grandes
    public static final Font FONTE_TITULO =
            new Font("Arial", Font.BOLD, 32);

    // fonte usada em subtitulos
    public static final Font FONTE_SUBTITULO =
            new Font("Arial", Font.BOLD, 18);

    // fonte padrao de texto normal
    public static final Font FONTE_NORMAL =
            new Font("Arial", Font.PLAIN, 14);

    // fonte usada nas estatisticas
    public static final Font FONTE_ESTATISTICAS =
            new Font("Arial", Font.BOLD, 20);

    // fonte pequena para labels menores
    public static final Font FONTE_PEQUENA =
            new Font("Arial", Font.PLAIN, 11);

    // ==================== DIMENSOES ====================

    // tamanho do quadrado de cada letra
    public static final int TAMANHO_CELULA = 56;

    // espaco entre celulas da grade
    public static final int ESPACAMENTO_CELULA = 4;

    // largura das teclas do teclado virtual
    public static final int LARGURA_TECLA = 40;

    // altura das teclas
    public static final int ALTURA_TECLA = 52;

    // espaco entre teclas
    public static final int ESPACAMENTO_TECLA = 5;

    // largura da janela principal
    public static final int LARGURA_JANELA = 540;

    // altura da janela principal
    public static final int ALTURA_JANELA = 820;

    // margem padrao da interface
    public static final int MARGEM = 10;

    // espessura da borda das celulas
    public static final int ESPESSURA_BORDA = 2;

    // ==================== ANIMACOES ====================

    // duracao da animacao de flip
    public static final int DURACAO_FLIP = 250;

    // duracao da animacao de shake
    public static final int DURACAO_SHAKE = 400;

    // duracao da animacao de bounce
    public static final int DURACAO_BOUNCE = 100;

    // delay entre animacao de cada letra
    public static final int DELAY_LETRA = 100;

    // ==================== TECLADO ====================

    // primeira linha do teclado qwerty
    public static final String TECLADO_LINHA1 = "QWERTYUIOP";

    // segunda linha
    public static final String TECLADO_LINHA2 = "ASDFGHJKL";

    // terceira linha
    public static final String TECLADO_LINHA3 = "ZXCVBNM";

    // texto do botao enter
    public static final String TEXTO_ENTER = "ENTER";

    // texto do botao apagar
    public static final String TEXTO_BACKSPACE = "DEL";

    // ==================== TEXTOS PADRAO ====================

    // titulo principal do jogo
    public static final String TITULO_JOGO = "TERMO";

    // mensagem exibida quando vence
    public static final String MSG_VITORIA =
            "Parabéns! Você acertou!";

    // mensagem exibida quando perde
    public static final String MSG_DERROTA =
            "Que pena! A palavra era: ";

    // mensagem quando palavra nao existe
    public static final String MSG_PALAVRA_INVALIDA =
            "Palavra não encontrada no dicionário";

    // mensagem quando palavra esta incompleta
    public static final String MSG_PALAVRA_INCOMPLETA =
            "Complete a palavra com 5 letras";

    // ==================== LIMITES DO JOGO ====================

    // quantidade de letras por palavra
    public static final int TAMANHO_PALAVRA = 5;

    // numero maximo de tentativas
    public static final int MAX_TENTATIVAS = 6;
}
