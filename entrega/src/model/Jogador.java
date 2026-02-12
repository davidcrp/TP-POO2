package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
*/

// classe que representa um jogador do sistema
// implementa Serializable para permitir salvar o objeto em arquivo
public class Jogador implements Serializable {

    // uid para controle de serialização (evita conflitos de versão)
    private static final long serialVersionUID = 2L;

    // nome padrão usado quando nenhum nome é informado
    private static final String NOME_PADRAO = "Jogador";

    // nome do jogador
    private String nome;

    // senha armazenada como hash (não guarda a senha original)
    private String hashSenha;

    // objeto que guarda as estatísticas do jogador
    private Estatisticas estatisticas;

    // data de criação do perfil (em milissegundos)
    private long dataCriacao;

    // data do último acesso do jogador
    private long ultimoAcesso;

    // construtor que cria um jogador anônimo
    public Jogador() {
        this(NOME_PADRAO);
    }

    // construtor que cria um jogador com nome definido
    public Jogador(String nome) {
        // se o nome for válido, usa ele
        // se não, usa o nome padrão
        this.nome = (nome != null && !nome.trim().isEmpty()) ? nome.trim() : NOME_PADRAO;

        // inicialmente não tem senha
        this.hashSenha = null;

        // cria estatísticas zeradas
        estatisticas = new Estatisticas();

        // salva a data atual como data de criação
        dataCriacao = System.currentTimeMillis();

        // último acesso começa igual à criação
        ultimoAcesso = dataCriacao;
    }

    // retorna o nome do jogador
    public String getNome() {
        return nome;
    }

    // altera o nome do jogador se for válido
    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome.trim();
        }
    }

    // retorna o objeto de estatísticas
    public Estatisticas getEstatisticas() {
        return estatisticas;
    }

    // substitui as estatísticas se o objeto não for nulo
    public void setEstatisticas(Estatisticas estatisticas) {
        if (estatisticas != null) {
            this.estatisticas = estatisticas;
        }
    }

    // retorna a data de criação do perfil
    public long getDataCriacao() {
        return dataCriacao;
    }

    // retorna a data do último acesso
    public long getUltimoAcesso() {
        return ultimoAcesso;
    }

    // atualiza o último acesso para o momento atual
    public void atualizarUltimoAcesso() {
        ultimoAcesso = System.currentTimeMillis();
    }

    // define uma senha para o jogador
    // a senha não é salva diretamente, e sim o hash dela
    public void setSenha(String senha) {

        // se a senha for null, remove a senha
        if (senha == null) {
            this.hashSenha = null;
            return;
        }

        // gera e armazena o hash da senha
        this.hashSenha = gerarHashSenha(senha);
    }

    // verifica se a senha informada está correta
    public boolean verificarSenha(String senha) {

        // se a senha fornecida for null ou não existir senha cadastrada
        if (senha == null || hashSenha == null) {
            return false;
        }

        // gera o hash da senha digitada
        String hashFornecido = gerarHashSenha(senha);

        // compara com o hash armazenado
        return hashSenha.equals(hashFornecido);
    }

    // verifica se o jogador tem senha cadastrada
    public boolean temSenha() {
        return hashSenha != null && !hashSenha.isEmpty();
    }

    // gera o hash SHA-256 da senha
    // isso serve para segurança, não guardar a senha original
    private String gerarHashSenha(String senha) {
        try {
            // cria o gerador de hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // aplica o hash na senha
            byte[] hash = digest.digest(senha.getBytes());

            // converte o resultado para Base64 para poder armazenar como String
            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException e) {

            // caso raro: se SHA-256 não existir
            // usa um método simples como alternativa
            return String.valueOf(senha.hashCode());
        }
    }

    // registra uma vitória
    // delega a responsabilidade para a classe Estatisticas
    public void registrarVitoria(int tentativasUsadas) {
        estatisticas.registrarVitoria(tentativasUsadas);
        atualizarUltimoAcesso();
    }

    // registra uma derrota
    public void registrarDerrota() {
        estatisticas.registrarDerrota();
        atualizarUltimoAcesso();
    }

    // retorna o total de partidas jogadas
    public int getTotalPartidas() {
        return estatisticas.getTotalPartidas();
    }

    // retorna o total de vitórias
    public int getVitorias() {
        return estatisticas.getPartidasGanhas();
    }

    // retorna o total de derrotas
    public int getDerrotas() {
        return estatisticas.getPartidasPerdidas();
    }

    // retorna uma representação textual do jogador
    @Override
    public String toString() {
        return String.format("Jogador: %s\n%s", nome, estatisticas.getResumo());
    }

    // compara dois jogadores pelo nome (ignora maiúsculas e minúsculas)
    @Override
    public boolean equals(Object obj) {

        // se for o mesmo objeto
        if (this == obj) return true;

        // se for null ou classe diferente
        if (obj == null || getClass() != obj.getClass()) return false;

        // converte para Jogador
        Jogador jogador = (Jogador) obj;

        // compara os nomes ignorando maiúsculas/minúsculas
        return nome.equalsIgnoreCase(jogador.nome);
    }

    // gera hash baseado no nome em minúsculo
    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }
}
