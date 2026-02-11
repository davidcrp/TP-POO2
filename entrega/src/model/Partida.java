package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// representa uma partida individual do jogo termo
// controla palavra secreta, tentativas, letras usadas e estado da partida
public class Partida {

    // numero maximo de tentativas permitidas
    public static final int MAX_TENTATIVAS = 6;

    // estados possiveis de uma partida
    public enum EstadoPartida {
        EM_ANDAMENTO,
        VITORIA,
        DERROTA,
        ABANDONADA
    }

    // palavra secreta da partida
    private Palavra palavraSecreta;

    // lista de tentativas feitas pelo jogador
    private List<Palavra> tentativas;

    // conjunto de letras usadas
    private Set<Character> letrasUsadas;

    // letras corretas (verde)
    private Set<Character> letrasCorretas;

    // letras existentes em posicao errada (amarelo)
    private Set<Character> letrasPosicaoErrada;

    // letras ausentes (cinza)
    private Set<Character> letrasAusentes;

    // estado atual da partida
    private EstadoPartida estado;

    // tentativas restantes
    private int tentativasRestantes;

    // cria uma nova partida com a palavra secreta informada
    public Partida(String palavraSecreta) {

        if (palavraSecreta == null || palavraSecreta.trim().isEmpty()) {
            throw new IllegalArgumentException("palavra secreta nao pode ser vazia");
        }

        this.palavraSecreta = new Palavra(palavraSecreta);
        tentativas = new ArrayList<>();
        letrasUsadas = new HashSet<>();
        letrasCorretas = new HashSet<>();
        letrasPosicaoErrada = new HashSet<>();
        letrasAusentes = new HashSet<>();
        estado = EstadoPartida.EM_ANDAMENTO;
        tentativasRestantes = MAX_TENTATIVAS;
    }

    // retorna a palavra secreta
    public Palavra getPalavraSecreta() {
        return palavraSecreta;
    }

    // retorna a palavra secreta como string
    public String getPalavraSecretaString() {
        return palavraSecreta.toString();
    }

    // retorna as tentativas realizadas
    public List<Palavra> getTentativas() {
        return new ArrayList<>(tentativas);
    }

    // retorna uma tentativa especifica
    public Palavra getTentativa(int indice) {
        if (indice >= 0 && indice < tentativas.size()) {
            return tentativas.get(indice);
        }
        return null;
    }

    // retorna o numero de tentativas feitas
    public int getNumeroTentativas() {
        return tentativas.size();
    }

    // retorna o numero de tentativas restantes
    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    // retorna o estado atual da partida
    public EstadoPartida getEstado() {
        return estado;
    }

    // retorna as letras usadas
    public Set<Character> getLetrasUsadas() {
        return new HashSet<>(letrasUsadas);
    }

    // verifica se a partida esta em andamento
    public boolean isEmAndamento() {
        return estado == EstadoPartida.EM_ANDAMENTO;
    }

    // verifica se a partida terminou em vitoria
    public boolean isVitoria() {
        return estado == EstadoPartida.VITORIA;
    }

    // verifica se a partida terminou em derrota
    public boolean isDerrota() {
        return estado == EstadoPartida.DERROTA;
    }

    // verifica se a partida ja terminou
    public boolean isTerminada() {
        return estado != EstadoPartida.EM_ANDAMENTO;
    }

    // processa uma tentativa do jogador
    public Palavra fazerTentativa(String palavraTentativa) {

        if (!isEmAndamento()) {
            throw new IllegalStateException("partida ja terminou");
        }

        Palavra tentativa = new Palavra(palavraTentativa);
        tentativa.compararCom(palavraSecreta);

        tentativas.add(tentativa);
        tentativasRestantes--;

        atualizarLetrasUsadas(tentativa);

        if (tentativa.isCorreta()) {
            estado = EstadoPartida.VITORIA;
        } else if (tentativasRestantes <= 0) {
            estado = EstadoPartida.DERROTA;
        }

        return tentativa;
    }

    // atualiza os conjuntos de letras usadas com base na tentativa
    private void atualizarLetrasUsadas(Palavra tentativa) {

        for (Letra letra : tentativa.getLetras()) {

            char c = letra.getCaractere();
            letrasUsadas.add(c);

            switch (letra.getEstado()) {

                case CORRETA:
                    letrasCorretas.add(c);
                    letrasPosicaoErrada.remove(c);
                    break;

                case POSICAO_ERRADA:
                    if (!letrasCorretas.contains(c)) {
                        letrasPosicaoErrada.add(c);
                    }
                    break;

                case AUSENTE:
                    if (!letrasCorretas.contains(c)
                            && !letrasPosicaoErrada.contains(c)) {
                        letrasAusentes.add(c);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    // abandona a partida atual
    public void abandonar() {
        if (isEmAndamento()) {
            estado = EstadoPartida.ABANDONADA;
        }
    }

    // retorna letras usadas organizadas por estado
    public String getLetrasUsadasFormatado() {

        StringBuilder sb = new StringBuilder();

        if (!letrasCorretas.isEmpty()) {
            sb.append("corretas: ").append(letrasCorretas).append("\n");
        }
        if (!letrasPosicaoErrada.isEmpty()) {
            sb.append("posicao errada: ").append(letrasPosicaoErrada).append("\n");
        }
        if (!letrasAusentes.isEmpty()) {
            sb.append("ausentes: ").append(letrasAusentes);
        }

        return sb.toString();
    }

    // representacao textual da partida
    @Override
    public String toString() {
        return String.format(
            "Partida[estado=%s, tentativas=%d/%d, palavra=%s]",
            estado,
            getNumeroTentativas(),
            MAX_TENTATIVAS,
            isTerminada() ? palavraSecreta.toString() : "?????"
        );
    }
}
