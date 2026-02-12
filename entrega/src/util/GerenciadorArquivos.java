package util;

import model.Jogador;
import model.Estatisticas;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// gerencia a persistencia de dados do jogo
// responsavel por salvar e carregar estatisticas e jogadores
public class GerenciadorArquivos {

    // nomes padrao dos arquivos
    private static final String ARQUIVO_ESTATISTICAS = "estatisticas.dat";
    private static final String ARQUIVO_JOGADORES = "jogadores.dat";

    // diretorio onde os dados sao salvos
    private static final String DIRETORIO_DADOS = "dados";

    // caminho base para os arquivos
    private String caminhoBase;

    // cria o gerenciador usando o diretorio padrao
    public GerenciadorArquivos() {
        caminhoBase = DIRETORIO_DADOS;
        criarDiretorioSeNecessario();
    }

    // cria o gerenciador com caminho personalizado
    public GerenciadorArquivos(String caminhoBase) {
        this.caminhoBase = caminhoBase;
        criarDiretorioSeNecessario();
    }

    // cria o diretorio de dados caso nao exista
    private void criarDiretorioSeNecessario() {
        File diretorio = new File(caminhoBase);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    // monta o caminho completo de um arquivo
    private String getCaminhoCompleto(String nomeArquivo) {
        return caminhoBase + File.separator + nomeArquivo;
    }

    // salva as estatisticas no arquivo padrao
    public boolean salvarEstatisticas(Estatisticas estatisticas) {
        return salvarEstatisticas(estatisticas, ARQUIVO_ESTATISTICAS);
    }

    // salva as estatisticas em um arquivo especifico
    public boolean salvarEstatisticas(Estatisticas estatisticas, String nomeArquivo) {

        String caminho = getCaminhoCompleto(nomeArquivo);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(caminho))) {

            oos.writeObject(estatisticas);
            return true;

        } catch (IOException e) {
            System.err.println("erro ao salvar estatisticas: " + e.getMessage());
            return false;
        }
    }

    // carrega as estatisticas do arquivo padrao
    public Estatisticas carregarEstatisticas() {
        return carregarEstatisticas(ARQUIVO_ESTATISTICAS);
    }

    // carrega as estatisticas de um arquivo especifico
    public Estatisticas carregarEstatisticas(String nomeArquivo) {

        String caminho = getCaminhoCompleto(nomeArquivo);
        File arquivo = new File(caminho);

        if (!arquivo.exists()) {
            return new Estatisticas();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(caminho))) {

            return (Estatisticas) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("erro ao carregar estatisticas: " + e.getMessage());
            return new Estatisticas();
        }
    }

    // salva o mapa de jogadores
    public boolean salvarJogadores(Map<String, Jogador> jogadores) {

        String caminho = getCaminhoCompleto(ARQUIVO_JOGADORES);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(caminho))) {

            oos.writeObject(jogadores);
            return true;

        } catch (IOException e) {
            System.err.println("erro ao salvar jogadores: " + e.getMessage());
            return false;
        }
    }

    // carrega o mapa de jogadores salvo
    @SuppressWarnings("unchecked")
    public Map<String, Jogador> carregarJogadores() {

        String caminho = getCaminhoCompleto(ARQUIVO_JOGADORES);
        File arquivo = new File(caminho);

        if (!arquivo.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(caminho))) {

            return (Map<String, Jogador>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("erro ao carregar jogadores: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // salva ou atualiza um jogador especifico
    public boolean salvarJogador(Jogador jogador) {

        Map<String, Jogador> jogadores = carregarJogadores();
        jogadores.put(jogador.getNome().toLowerCase(), jogador);

        return salvarJogadores(jogadores);
    }

    // carrega um jogador pelo nome
    public Jogador carregarJogador(String nome) {

        Map<String, Jogador> jogadores = carregarJogadores();
        return jogadores.get(nome.toLowerCase());
    }

    // verifica se um jogador existe
    public boolean jogadorExiste(String nome) {

        Map<String, Jogador> jogadores = carregarJogadores();
        return jogadores.containsKey(nome.toLowerCase());
    }

    // retorna os nomes dos jogadores salvos
    public Set<String> listarJogadores() {
        return carregarJogadores().keySet();
    }

    // salva o conjunto de palavras usadas
    public boolean salvarPalavrasUsadas(Set<String> palavrasUsadas, String nomeArquivo) {

        String caminho = getCaminhoCompleto(nomeArquivo);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(caminho))) {

            oos.writeObject(palavrasUsadas);
            return true;

        } catch (IOException e) {
            System.err.println("erro ao salvar palavras usadas: " + e.getMessage());
            return false;
        }
    }

    // carrega o conjunto de palavras usadas
    @SuppressWarnings("unchecked")
    public Set<String> carregarPalavrasUsadas(String nomeArquivo) {

        String caminho = getCaminhoCompleto(nomeArquivo);
        File arquivo = new File(caminho);

        if (!arquivo.exists()) {
            return new java.util.HashSet<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(caminho))) {

            return (Set<String>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("erro ao carregar palavras usadas: " + e.getMessage());
            return new java.util.HashSet<>();
        }
    }

    // remove todos os arquivos de dados salvos
    public boolean limparDados() {

        File diretorio = new File(caminhoBase);

        if (diretorio.exists() && diretorio.isDirectory()) {

            File[] arquivos = diretorio.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    arquivo.delete();
                }
            }
            return true;
        }

        return false;
    }

    // verifica se existe algum dado salvo
    public boolean existeDadosSalvos() {

        File estatisticas = new File(getCaminhoCompleto(ARQUIVO_ESTATISTICAS));
        File jogadores = new File(getCaminhoCompleto(ARQUIVO_JOGADORES));

        return estatisticas.exists() || jogadores.exists();
    }
}
