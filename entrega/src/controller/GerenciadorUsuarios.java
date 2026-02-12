package controller;

import model.Jogador;
import util.GerenciadorArquivos;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// gerenciador de usuarios do jogo
// responsavel por login, cadastro e controle de multiplos jogadores
public class GerenciadorUsuarios {

    // mapa que armazena os usuarios
    // chave = nome em minusculo
    // valor = objeto Jogador
    private Map<String, Jogador> usuarios;
    
    // jogador atualmente logado
    private Jogador jogadorLogado;
    
    // responsavel por salvar e carregar dados do arquivo
    private GerenciadorArquivos gerenciadorArquivos;
    
    // tamanho minimo exigido para senha
    private static final int TAMANHO_MINIMO_SENHA = 4;
    
    // construtor
    // carrega usuarios salvos do arquivo
    public GerenciadorUsuarios() {

        gerenciadorArquivos = new GerenciadorArquivos();

        // carrega jogadores do arquivo
        usuarios = gerenciadorArquivos.carregarJogadores();

        jogadorLogado = null;
        
        System.out.println("Gerenciador de usuários inicializado.");
        System.out.println("Usuários cadastrados: " + usuarios.size());
    }
    
    // realiza login de um usuario
    public boolean login(String nomeUsuario, String senha) {

        // valida nome
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            return false;
        }
        
        // valida senha
        if (senha == null) {
            return false;
        }
        
        // gera chave padronizada
        String chave = nomeUsuario.toLowerCase().trim();

        // busca usuario no mapa
        Jogador jogador = usuarios.get(chave);
        
        // se nao existe
        if (jogador == null) {
            return false;
        }
        
        // verifica senha
        if (!jogador.verificarSenha(senha)) {
            return false;
        }
        
        // login bem sucedido
        jogadorLogado = jogador;

        // atualiza ultimo acesso
        jogadorLogado.atualizarUltimoAcesso();

        // salva alteracoes
        salvarJogadores();
        
        System.out.println("Login bem-sucedido: " 
                + jogadorLogado.getNome());

        return true;
    }
    
    // cadastra novo usuario
    public boolean cadastrar(String nomeUsuario, String senha) {

        // valida nome
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            return false;
        }
        
        // valida senha (tamanho minimo)
        if (senha == null || senha.length() < TAMANHO_MINIMO_SENHA) {
            return false;
        }
        
        String chave = nomeUsuario.toLowerCase().trim();
        
        // verifica se usuario ja existe
        if (usuarios.containsKey(chave)) {
            return false;
        }
        
        // cria novo jogador
        Jogador novoJogador = new Jogador(nomeUsuario.trim());

        // define senha (gera hash)
        novoJogador.setSenha(senha);
        
        // adiciona ao mapa
        usuarios.put(chave, novoJogador);

        // salva no arquivo
        salvarJogadores();
        
        System.out.println("Novo usuário cadastrado: " 
                + novoJogador.getNome());

        return true;
    }
    
    // faz logout do usuario atual
    public void logout() {

        if (jogadorLogado != null) {

            // salva antes de sair
            salvarJogadores();

            System.out.println("Logout: " 
                    + jogadorLogado.getNome());

            jogadorLogado = null;
        }
    }
    
    // verifica se existe usuario logado
    public boolean isUsuarioLogado() {
        return jogadorLogado != null;
    }
    
    // retorna jogador logado
    public Jogador getJogadorLogado() {
        return jogadorLogado;
    }
    
    // verifica se um nome ja esta cadastrado
    public boolean usuarioExiste(String nomeUsuario) {

        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            return false;
        }
        
        String chave = nomeUsuario.toLowerCase().trim();

        return usuarios.containsKey(chave);
    }
    
    // retorna lista de usuarios cadastrados
    public Set<String> listarUsuarios() {
        return usuarios.keySet();
    }
    
    // retorna numero total de usuarios
    public int getNumeroUsuarios() {
        return usuarios.size();
    }
    
    // salva todos os usuarios no arquivo
    private void salvarJogadores() {
        gerenciadorArquivos.salvarJogadores(usuarios);
    }
    
    // salva apenas o jogador logado
    // usado apos vitoria ou derrota
    public void salvarJogadorLogado() {

        if (jogadorLogado != null) {

            String chave = jogadorLogado
                    .getNome()
                    .toLowerCase();

            usuarios.put(chave, jogadorLogado);

            salvarJogadores();
        }
    }
    
    // altera senha do usuario logado
    public boolean alterarSenha(String senhaAtual, String novaSenha) {

        // verifica se existe usuario logado
        if (jogadorLogado == null) {
            return false;
        }
        
        // valida nova senha
        if (novaSenha == null 
                || novaSenha.length() < TAMANHO_MINIMO_SENHA) {
            return false;
        }
        
        // verifica senha atual
        if (!jogadorLogado.verificarSenha(senhaAtual)) {
            return false;
        }
        
        // define nova senha
        jogadorLogado.setSenha(novaSenha);

        // salva alteracoes
        salvarJogadores();

        return true;
    }
    
    // remove usuario logado (exclusao de conta)
    public boolean removerUsuario(String senha) {

        if (jogadorLogado == null) {
            return false;
        }
        
        // confirma senha
        if (!jogadorLogado.verificarSenha(senha)) {
            return false;
        }
        
        String chave = jogadorLogado
                .getNome()
                .toLowerCase();

        // remove do mapa
        usuarios.remove(chave);

        // salva arquivo
        salvarJogadores();
        
        System.out.println("Usuário removido: " 
                + jogadorLogado.getNome());

        jogadorLogado = null;
        
        return true;
    }
    
    // reseta estatisticas do jogador logado
    public boolean resetarEstatisticas() {

        if (jogadorLogado == null) {
            return false;
        }
        
        // chama metodo resetar da classe Estatisticas
        jogadorLogado.getEstatisticas().resetar();

        salvarJogadores();

        return true;
    }
    
    // retorna tamanho minimo exigido para senha
    public static int getTamanhoMinimoSenha() {
        return TAMANHO_MINIMO_SENHA;
    }
}
