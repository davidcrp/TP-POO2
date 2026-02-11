package model;

import java.io.Serializable;

// representa um jogador do jogo termo
// armazena nome, estatisticas e informacoes de acesso
public class Jogador implements Serializable {

    // uid para serializacao
    private static final long serialVersionUID = 1L;

    // nome padrao para jogador anonimo
    private static final String NOME_PADRAO = "Jogador";

    // nome do jogador
    private String nome;

    // estatisticas associadas ao jogador
    private Estatisticas estatisticas;

    // data de criacao do perfil
    private long dataCriacao;

    // ultimo acesso do jogador
    private long ultimoAcesso;

    // cria um jogador anonimo
    public Jogador() {
        this(NOME_PADRAO);
    }

    // cria um jogador com nome definido
    public Jogador(String nome) {
        this.nome = (nome != null && !nome.trim().isEmpty()) ? nome.trim() : NOME_PADRAO;
        estatisticas = new Estatisticas();
        dataCriacao = System.currentTimeMillis();
        ultimoAcesso = dataCriacao;
    }

    // retorna o nome do jogador
    public String getNome() {
        return nome;
    }

    // altera o nome do jogador
    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome.trim();
        }
    }

    // retorna as estatisticas do jogador
    public Estatisticas getEstatisticas() {
        return estatisticas;
    }

    // define novas estatisticas para o jogador
    public void setEstatisticas(Estatisticas estatisticas) {
        if (estatisticas != null) {
            this.estatisticas = estatisticas;
        }
    }

    // retorna a data de criacao do perfil
    public long getDataCriacao() {
        return dataCriacao;
    }

    // retorna a data do ultimo acesso
    public long getUltimoAcesso() {
        return ultimoAcesso;
    }

    // atualiza o ultimo acesso para o momento atual
    public void atualizarUltimoAcesso() {
        ultimoAcesso = System.currentTimeMillis();
    }

    // registra uma vitoria delegando para estatisticas
    public void registrarVitoria(int tentativasUsadas) {
        estatisticas.registrarVitoria(tentativasUsadas);
        atualizarUltimoAcesso();
    }

    // registra uma derrota delegando para estatisticas
    public void registrarDerrota() {
        estatisticas.registrarDerrota();
        atualizarUltimoAcesso();
    }

    // retorna o total de partidas jogadas
    public int getTotalPartidas() {
        return estatisticas.getTotalPartidas();
    }

    // retorna o total de vitorias
    public int getVitorias() {
        return estatisticas.getPartidasGanhas();
    }

    // retorna o total de derrotas
    public int getDerrotas() {
        return estatisticas.getPartidasPerdidas();
    }

    // representacao textual do jogador
    @Override
    public String toString() {
        return String.format("Jogador: %s\n%s", nome, estatisticas.getResumo());
    }

    // compara jogadores pelo nome ignorando maiusculas e minusculas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Jogador jogador = (Jogador) obj;
        return nome.equalsIgnoreCase(jogador.nome);
    }

    // gera hash baseado no nome
    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }
}
