package controller;

import model.*;
import util.GerenciadorArquivos;
import controller.GerenciadorUsuarios;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

// controlador principal do jogo termo
// faz a ligação entre a interface grafica (view) e o modelo
// tambem integra o sistema de multiplos usuarios
public class ControladorJogo {

    // banco com todas as palavras validas do jogo
    private BancoPalavras bancoPalavras;

    // gerenciador responsavel pelos usuarios
    private GerenciadorUsuarios gerenciadorUsuarios;

    // partida atual em andamento
    private Partida partidaAtual;

    // responsavel por salvar e carregar dados em arquivo
    private GerenciadorArquivos gerenciadorArquivos;

    // indica se o jogo foi inicializado corretamente
    private boolean inicializado;

    // listener usado para comunicar eventos para a view
    private JogoListener listener;

    // interface usada para enviar eventos do controller para a interface grafica
    public interface JogoListener {

        // chamada depois que uma tentativa valida eh processada
        void onTentativaProcessada(Palavra tentativa, int tentativasRestantes);

        // chamada quando o jogador vence
        void onVitoria(int tentativasUsadas);

        // chamada quando o jogador perde
        void onDerrota(String palavraSecreta);

        // chamada quando ocorre algum erro
        void onErro(String mensagem);

        // chamada ao iniciar nova partida
        void onNovaPartida();

        // chamada quando estatisticas sao atualizadas
        void onEstatisticasAtualizadas(Estatisticas estatisticas);
    }

    // construtor padrao
    // cria os gerenciadores mas nao carrega o banco de palavras
    public ControladorJogo() {
        gerenciadorArquivos = new GerenciadorArquivos();
        gerenciadorUsuarios = new GerenciadorUsuarios();
        inicializado = false;
    }

    // construtor que ja inicializa o jogo com o arquivo de palavras
    public ControladorJogo(String caminhoArquivoPalavras) throws IOException {
        this();
        inicializar(caminhoArquivoPalavras);
    }

    // carrega o banco de palavras
    public void inicializar(String caminhoArquivoPalavras) throws IOException {

        bancoPalavras = new BancoPalavras(caminhoArquivoPalavras);

        // verifica se o banco esta vazio
        if (bancoPalavras.isEmpty()) {
            throw new IOException("banco de palavras vazio");
        }

        inicializado = true;

        System.out.println("jogo inicializado com " 
                + bancoPalavras.getTotalPalavras() + " palavras");
    }

    // define o listener da interface grafica
    public void setListener(JogoListener listener) {
        this.listener = listener;
    }

    // retorna o gerenciador de usuarios
    public GerenciadorUsuarios getGerenciadorUsuarios() {
        return gerenciadorUsuarios;
    }

    // inicia uma nova partida
    public boolean iniciarNovaPartida() {

        // verifica se o jogo foi inicializado
        if (!inicializado) {
            notificarErro("jogo não inicializado");
            return false;
        }

        // verifica se ha usuario logado
        if (!gerenciadorUsuarios.isUsuarioLogado()) {
            notificarErro("nenhum usuário logado");
            return false;
        }

        // se existe partida em andamento, abandona
        if (partidaAtual != null && partidaAtual.isEmAndamento()) {
            partidaAtual.abandonar();
            bancoPalavras.marcarComoUsada(
                    partidaAtual.getPalavraSecretaString());
        }

        // pega nova palavra aleatoria
        String novaPalavra = bancoPalavras.getPalavraAleatoria();

        // verifica se ainda ha palavras disponiveis
        if (novaPalavra == null) {
            notificarErro("não há mais palavras disponíveis");
            return false;
        }

        // cria nova partida
        partidaAtual = new Partida(novaPalavra);

        // notifica a view
        if (listener != null) {
            listener.onNovaPartida();
        }

        System.out.println(partidaAtual.getPalavraSecretaString());

        return true;
    }

    // processa uma tentativa digitada pelo jogador
    public Palavra processarTentativa(String palavraTentativa) {

        // verifica se existe partida valida
        if (!inicializado || partidaAtual == null) {
            notificarErro("nenhuma partida em andamento");
            return null;
        }

        // verifica se ha usuario logado
        if (!gerenciadorUsuarios.isUsuarioLogado()) {
            notificarErro("nenhum usuário logado");
            return null;
        }

        // verifica se a partida ja terminou
        if (!partidaAtual.isEmAndamento()) {
            notificarErro("a partida já terminou");
            return null;
        }

        // normaliza a palavra digitada
        String palavra = Palavra.normalizarPalavra(palavraTentativa);

        // verifica tamanho
        if (palavra.length() != Palavra.TAMANHO_PALAVRA) {
            notificarErro("a palavra deve ter 5 letras");
            return null;
        }

        // verifica se a palavra existe no banco
        if (!bancoPalavras.palavraExiste(palavra)) {
            notificarErro("palavra não encontrada no dicionário");
            return null;
        }

        // faz a tentativa na partida
        Palavra tentativa = partidaAtual.fazerTentativa(palavra);

        // notifica a view
        if (listener != null) {
            listener.onTentativaProcessada(
                    tentativa,
                    partidaAtual.getTentativasRestantes()
            );
        }

        // verifica se houve vitoria ou derrota
        verificarResultadoPartida();

        return tentativa;
    }

    // verifica se a partida terminou
    private void verificarResultadoPartida() {

        if (partidaAtual == null) return;

        Jogador jogador = gerenciadorUsuarios.getJogadorLogado();
        if (jogador == null) return;

        // se venceu
        if (partidaAtual.isVitoria()) {

            int tentativasUsadas = partidaAtual.getNumeroTentativas();

            jogador.registrarVitoria(tentativasUsadas);

            // salva no arquivo
            gerenciadorUsuarios.salvarJogadorLogado();

            if (listener != null) {
                listener.onVitoria(tentativasUsadas);
                listener.onEstatisticasAtualizadas(
                        jogador.getEstatisticas()
                );
            }

        // se perdeu
        } else if (partidaAtual.isDerrota()) {

            jogador.registrarDerrota();

            // salva no arquivo
            gerenciadorUsuarios.salvarJogadorLogado();

            if (listener != null) {
                listener.onDerrota(
                        partidaAtual.getPalavraSecretaString()
                );
                listener.onEstatisticasAtualizadas(
                        jogador.getEstatisticas()
                );
            }
        }
    }

    // zera estatisticas do jogador atual
    public void zerarEstatisticas() {

        if (!gerenciadorUsuarios.isUsuarioLogado()) {
            return;
        }

        gerenciadorUsuarios.resetarEstatisticas();

        if (listener != null) {
            listener.onEstatisticasAtualizadas(
                    getEstatisticas()
            );
        }
    }

    // envia mensagem de erro para a view
    private void notificarErro(String mensagem) {

        System.err.println("erro: " + mensagem);

        if (listener != null) {
            listener.onErro(mensagem);
        }
    }

    // GETTERS 

    // retorna se o jogo esta inicializado
    public boolean isInicializado() {
        return inicializado;
    }

    // verifica se existe partida em andamento
    public boolean temPartidaEmAndamento() {
        return partidaAtual != null && partidaAtual.isEmAndamento();
    }

    // retorna partida atual
    public Partida getPartidaAtual() {
        return partidaAtual;
    }

    // retorna jogador logado
    public Jogador getJogadorAtual() {
        return gerenciadorUsuarios.getJogadorLogado();
    }

    // retorna estatisticas do jogador atual
    public Estatisticas getEstatisticas() {
        Jogador jogador = gerenciadorUsuarios.getJogadorLogado();
        return jogador != null
                ? jogador.getEstatisticas()
                : new Estatisticas();
    }

    // retorna numero de tentativas restantes
    public int getTentativasRestantes() {
        return partidaAtual != null
                ? partidaAtual.getTentativasRestantes()
                : 0;
    }

    // retorna letras ja usadas na partida
    public Set<Character> getLetrasUsadas() {
        return partidaAtual != null
                ? partidaAtual.getLetrasUsadas()
                : new HashSet<>();
    }

    // retorna banco de palavras
    public BancoPalavras getBancoPalavras() {
        return bancoPalavras;
    }

    // retorna gerenciador de arquivos
    public GerenciadorArquivos getGerenciadorArquivos() {
        return gerenciadorArquivos;
    }

    // finaliza o jogo salvando dados pendentes
    public void finalizarJogo() {

        // salva jogador se estiver logado
        if (gerenciadorUsuarios.isUsuarioLogado()) {
            gerenciadorUsuarios.salvarJogadorLogado();
            gerenciadorUsuarios.logout();
        }

        // abandona partida se ainda estiver em andamento
        if (partidaAtual != null && partidaAtual.isEmAndamento()) {
            partidaAtual.abandonar();
        }

        System.out.println("jogo finalizado");
    }
}
