package model;

import java.io.Serializable;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// armazena e gerencia as estatisticas do jogador
// implementa serializable para permitir salvar em arquivo
public class Estatisticas implements Serializable {

    private static final long serialVersionUID = 1L;

    // total de partidas jogadas
    private int totalPartidas;

    // total de partidas ganhas
    private int partidasGanhas;

    // total de partidas perdidas
    private int partidasPerdidas;

    // sequencia atual de vitorias
    private int sequenciaAtual;

    // melhor sequencia de vitorias
    private int melhorSequencia;

    // distribuicao de vitorias por numero de tentativas
    // indice 0 representa vitoria em 1 tentativa
    private int[] distribuicaoVitorias;

    // inicializa todas as estatisticas com zero
    public Estatisticas() {
        totalPartidas = 0;
        partidasGanhas = 0;
        partidasPerdidas = 0;
        sequenciaAtual = 0;
        melhorSequencia = 0;
        distribuicaoVitorias = new int[6];
    }

    // cria uma copia das estatisticas
    public Estatisticas(Estatisticas outra) {
        totalPartidas = outra.totalPartidas;
        partidasGanhas = outra.partidasGanhas;
        partidasPerdidas = outra.partidasPerdidas;
        sequenciaAtual = outra.sequenciaAtual;
        melhorSequencia = outra.melhorSequencia;
        distribuicaoVitorias = outra.distribuicaoVitorias.clone();
    }

    // getters simples

    public int getTotalPartidas() {
        return totalPartidas;
    }

    public int getPartidasGanhas() {
        return partidasGanhas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public int getSequenciaAtual() {
        return sequenciaAtual;
    }

    public int getMelhorSequencia() {
        return melhorSequencia;
    }

    public int[] getDistribuicaoVitorias() {
        return distribuicaoVitorias.clone();
    }

    // calcula a porcentagem de vitorias
    public double getPorcentagemVitorias() {
        if (totalPartidas == 0) {
            return 0.0;
        }
        return (double) partidasGanhas / totalPartidas * 100;
    }

    // registra uma vitoria e atualiza estatisticas
    // tentativasUsadas deve estar entre 1 e 6
    public void registrarVitoria(int tentativasUsadas) {

        if (tentativasUsadas < 1 || tentativasUsadas > 6) {
            throw new IllegalArgumentException("tentativas devem estar entre 1 e 6");
        }

        totalPartidas++;
        partidasGanhas++;
        sequenciaAtual++;

        if (sequenciaAtual > melhorSequencia) {
            melhorSequencia = sequenciaAtual;
        }

        distribuicaoVitorias[tentativasUsadas - 1]++;
    }

    // registra uma derrota
    public void registrarDerrota() {
        totalPartidas++;
        partidasPerdidas++;
        sequenciaAtual = 0;
    }

    // reseta todas as estatisticas
    public void resetar() {
        totalPartidas = 0;
        partidasGanhas = 0;
        partidasPerdidas = 0;
        sequenciaAtual = 0;
        melhorSequencia = 0;
        distribuicaoVitorias = new int[6];
    }

    // representacao textual simples das estatisticas
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== ESTATÍSTICAS ==========\n");
        sb.append(String.format("Total de Partidas: %d\n", totalPartidas));
        sb.append(String.format("Vitórias: %d\n", partidasGanhas));
        sb.append(String.format("Derrotas: %d\n", partidasPerdidas));
        sb.append(String.format("Taxa de Vitória: %.1f%%\n", getPorcentagemVitorias()));
        sb.append(String.format("Sequência Atual: %d\n", sequenciaAtual));
        sb.append(String.format("Melhor Sequência: %d\n", melhorSequencia));
        sb.append("==================================");
        return sb.toString();
    }


    // retorna um resumo curto das estatisticas
    public String getResumo() {
        return "jogos=" + totalPartidas +
                " | vitorias=" + partidasGanhas +
                " | derrotas=" + partidasPerdidas;
    }
}
