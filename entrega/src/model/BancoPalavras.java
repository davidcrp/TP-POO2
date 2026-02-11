package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

// gerencia o banco de palavras do jogo
// carrega palavras de um arquivo e fornece palavras aleatorias nao repetidas
public class BancoPalavras {

    // lista com todas as palavras validas carregadas do arquivo
    private List<String> todasPalavras;

    // conjunto de palavras ja utilizadas na partida
    private Set<String> palavrasUsadas;

    // gerador de numeros aleatorios
    private Random random;

    // caminho do arquivo de palavras
    private String caminhoArquivo;

    // cria um banco de palavras vazio
    public BancoPalavras() {
        todasPalavras = new ArrayList<>();
        palavrasUsadas = new HashSet<>();
        random = new Random();
        caminhoArquivo = "";
    }

    // cria o banco de palavras ja carregando do arquivo
    public BancoPalavras(String caminhoArquivo) throws IOException {
        this();
        carregarPalavras(caminhoArquivo);
    }

    // carrega palavras de um arquivo texto
    // cada linha deve conter uma palavra de 5 letras
    public void carregarPalavras(String caminhoArquivo) throws IOException {

        this.caminhoArquivo = caminhoArquivo;
        todasPalavras.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {

            String linha;
            int linhaNumero = 0;

            while ((linha = reader.readLine()) != null) {
                linhaNumero++;

                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }

                String palavra = Palavra.normalizarPalavra(linha);

                // valida tamanho da palavra
                if (palavra.length() != Palavra.TAMANHO_PALAVRA) {
                    System.err.println("aviso: palavra na linha " + linhaNumero + " nao tem 5 letras");
                    continue;
                }

                // valida se contem apenas letras
                if (!palavra.matches("[A-ZÇ]+")) {
                    System.err.println("aviso: palavra na linha " + linhaNumero + " contem caracteres invalidos");
                    continue;
                }

                todasPalavras.add(palavra);
            }
        }

        System.out.println("carregadas " + todasPalavras.size() + " palavras do arquivo");
    }

    // retorna uma palavra aleatoria ainda nao utilizada
    // retorna null se todas ja foram usadas
    public String getPalavraAleatoria() {

        List<String> disponiveis = new ArrayList<>();

        for (String palavra : todasPalavras) {
            if (!palavrasUsadas.contains(palavra)) {
                disponiveis.add(palavra);
            }
        }

        if (disponiveis.isEmpty()) {
            return null;
        }

        String palavraSelecionada = disponiveis.get(random.nextInt(disponiveis.size()));
        palavrasUsadas.add(palavraSelecionada);

        return palavraSelecionada;
    }

    // marca uma palavra como usada manualmente
    public void marcarComoUsada(String palavra) {
        if (palavra != null) {
            palavrasUsadas.add(Palavra.normalizarPalavra(palavra));
        }
    }

    // verifica se a palavra existe no banco
    public boolean palavraExiste(String palavra) {
        if (palavra == null) {
            return false;
        }
        return todasPalavras.contains(Palavra.normalizarPalavra(palavra));
    }

    // quantidade total de palavras carregadas
    public int getTotalPalavras() {
        return todasPalavras.size();
    }

    // quantidade de palavras ja usadas
    public int getPalavrasUsadas() {
        return palavrasUsadas.size();
    }

    // quantidade de palavras ainda disponiveis
    public int getPalavrasDisponiveis() {
        return todasPalavras.size() - palavrasUsadas.size();
    }

    // caminho do arquivo carregado
    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    // retorna uma copia da lista de palavras
    public List<String> getTodasPalavras() {
        return new ArrayList<>(todasPalavras);
    }

    // limpa o conjunto de palavras usadas
    public void resetarPalavrasUsadas() {
        palavrasUsadas.clear();
    }

    // verifica se nenhuma palavra foi carregada
    public boolean isEmpty() {
        return todasPalavras.isEmpty();
    }

    // verifica se ainda existem palavras nao usadas
    public boolean temPalavrasDisponiveis() {
        return getPalavrasDisponiveis() > 0;
    }

    // representacao textual do banco de palavras
    @Override
    public String toString() {
        return "BancoPalavras[total=" + getTotalPalavras()
                + ", usadas=" + getPalavrasUsadas()
                + ", disponiveis=" + getPalavrasDisponiveis()
                + ", arquivo=" + caminhoArquivo + "]";
    }
}
