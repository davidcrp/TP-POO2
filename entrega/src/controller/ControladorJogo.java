package controller;

import model.*;
import util.GerenciadorArquivos;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// controlador principal do jogo termo
// faz a ligacao entre a interface grafica e as classes de modelo
public class ControladorJogo {

    // banco com todas as palavras validas do jogo
    private BancoPalavras bancoPalavras;

    // jogador atual
    private Jogador jogadorAtual;

    // partida que esta em andamento
    private Partida partidaAtual;

    // responsavel por salvar e carregar dados em arquivo
    private GerenciadorArquivos gerenciadorArquivos;

    // indica se o jogo foi inicializado corretamente
    private boolean inicializado;

    // listener usado pela view para receber eventos do jogo
    private JogoListener listener;

    // interface usada para comunicar eventos do controller para a view
    public interface JogoListener {

        // chamada apos uma tentativa valida
        void onTentativaProcessada(Palavra tentativa, int tentativasRestantes);

        // chamada quando o jogador vence
        void onVitoria(int tentativasUsadas);

        // chamada quando o jogador perde
        void onDerrota(String palavraSecreta);

        // chamada quando ocorre algum erro
        void onErro(String mensagem);

        // chamada ao iniciar uma nova partida
        void onNovaPartida();

        // chamada quando estatisticas sao atualizadas
        void onEstatisticasAtualizadas(Estatisticas estatisticas);
    }

    // cria o controlador sem carregar o banco de palavras
    public ControladorJogo() {
        gerenciadorArquivos = new GerenciadorArquivos();
        jogadorAtual = new Jogador();
        inicializado = false;
    }

    // cria o controlador e ja inicializa o jogo
    public ControladorJogo(String caminhoArquivoPalavras) throws IOException {
        this();
        inicializar(caminhoArquivoPalavras);
    }

    // carrega o banco de palavras e estatisticas salvas
    public void inicializar(String caminhoArquivoPalavras) throws IOException {

        bancoPalavras = new BancoPalavras(caminhoArquivoPalavras);

        if (bancoPalavras.isEmpty()) {
            throw new IOException("banco de palavras vazio");
        }

        Estatisticas estatisticas = gerenciadorArquivos.carregarEstatisticas();
        if (estatisticas != null) {
            jogadorAtual.setEstatisticas(estatisticas);
        }

        inicializado = true;
        System.out.println("jogo inicializado com " + bancoPalavras.getTotalPalavras() + " palavras");
    }

    // registra o listener da interface grafica
    public void setListener(JogoListener listener) {
        this.listener = listener;
    }

    // inicia uma nova partida
    public boolean iniciarNovaPartida() {

        if (!inicializado) {
            notificarErro("jogo nao inicializado");
            return false;
        }

        // abandona a partida atual se ainda estiver em andamento
        if (partidaAtual != null && partidaAtual.isEmAndamento()) {
            partidaAtual.abandonar();
            bancoPalavras.marcarComoUsada(partidaAtual.getPalavraSecretaString());
        }

        String novaPalavra = bancoPalavras.getPalavraAleatoria();

        if (novaPalavra == null) {
            notificarErro("nao ha mais palavras disponiveis");
            return false;
        }

        partidaAtual = new Partida(novaPalavra);

        if (listener != null) {
            listener.onNovaPartida();
        }

        return true;
    }

    // processa uma tentativa digitada pelo jogador
    public Palavra processarTentativa(String palavraTentativa) {

        if (!inicializado || partidaAtual == null) {
            notificarErro("nenhuma partida em andamento");
            return null;
        }

        if (!partidaAtual.isEmAndamento()) {
            notificarErro("a partida ja terminou");
            return null;
        }

        String palavra = Palavra.normalizarPalavra(palavraTentativa);

        if (palavra.length() != Palavra.TAMANHO_PALAVRA) {
            notificarErro("a palavra deve ter 5 letras");
            return null;
        }

        if (!bancoPalavras.palavraExiste(palavra)) {
            notificarErro("palavra nao encontrada no dicionario");
            return null;
        }

        Palavra tentativa = partidaAtual.fazerTentativa(palavra);

        if (listener != null) {
            listener.onTentativaProcessada(tentativa, partidaAtual.getTentativasRestantes());
        }

        verificarResultadoPartida();
        return tentativa;
    }

    // verifica se a partida terminou em vitoria ou derrota
    private void verificarResultadoPartida() {

        if (partidaAtual == null) return;

        if (partidaAtual.isVitoria()) {

            int tentativasUsadas = partidaAtual.getNumeroTentativas();
            jogadorAtual.registrarVitoria(tentativasUsadas);
            salvarEstatisticas();

            if (listener != null) {
                listener.onVitoria(tentativasUsadas);
                listener.onEstatisticasAtualizadas(jogadorAtual.getEstatisticas());
            }

        } else if (partidaAtual.isDerrota()) {

            jogadorAtual.registrarDerrota();
            salvarEstatisticas();

            if (listener != null) {
                listener.onDerrota(partidaAtual.getPalavraSecretaString());
                listener.onEstatisticasAtualizadas(jogadorAtual.getEstatisticas());
            }
        }
    }

    // salva as estatisticas do jogador
    private void salvarEstatisticas() {
        gerenciadorArquivos.salvarEstatisticas(jogadorAtual.getEstatisticas());
    }

    // zera as estatisticas do jogador e salva em arquivo
    public void zerarEstatisticas() {

        jogadorAtual.getEstatisticas().resetar();

        salvarEstatisticas();

        if (listener != null) {
            listener.onEstatisticasAtualizadas(jogadorAtual.getEstatisticas());
        }
    }

    // envia uma mensagem de erro para a view
    private void notificarErro(String mensagem) {
        System.err.println("erro: " + mensagem);
        if (listener != null) {
            listener.onErro(mensagem);
        }
    }

    // getters usados pela interface grafica

    public boolean isInicializado() {
        return inicializado;
    }

    public boolean temPartidaEmAndamento() {
        return partidaAtual != null && partidaAtual.isEmAndamento();
    }

    public Partida getPartidaAtual() {
        return partidaAtual;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public Estatisticas getEstatisticas() {
        return jogadorAtual.getEstatisticas();
    }

    public int getTentativasRestantes() {
        return partidaAtual != null ? partidaAtual.getTentativasRestantes() : 0;
    }

    public Set<Character> getLetrasUsadas() {
        return partidaAtual != null ? partidaAtual.getLetrasUsadas() : new HashSet<>();
    }

    public BancoPalavras getBancoPalavras() {
        return bancoPalavras;
    }

    public GerenciadorArquivos getGerenciadorArquivos() {
        return gerenciadorArquivos;
    }

    // finaliza o jogo salvando dados pendentes
    public void finalizarJogo() {

        if (jogadorAtual != null) {
            salvarEstatisticas();
        }

        if (partidaAtual != null && partidaAtual.isEmAndamento()) {
            partidaAtual.abandonar();
        }

        System.out.println("jogo finalizado");
        System.out.println(jogadorAtual.getEstatisticas());
    }
}
