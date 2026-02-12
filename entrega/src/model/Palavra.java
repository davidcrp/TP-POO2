package model;

import java.util.ArrayList;
import java.util.List;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// representa uma palavra de 5 letras no jogo termo
// pode ser tanto a palavra secreta quanto uma tentativa do jogador
public class Palavra {

    // tamanho fixo das palavras no jogo
    public static final int TAMANHO_PALAVRA = 5;

    // lista que armazena as letras da palavra
    private List<Letra> letras;

    // guarda a palavra original digitada
    private String palavraOriginal;

    // construtor que cria uma palavra vazia
    public Palavra() {

        // cria a lista com capacidade 5
        letras = new ArrayList<>(TAMANHO_PALAVRA);

        // palavra original começa vazia
        palavraOriginal = "";

        // adiciona 5 letras vazias na lista
        for (int i = 0; i < TAMANHO_PALAVRA; i++) {
            letras.add(new Letra());
        }
    }

    // construtor que cria a palavra a partir de uma string
    public Palavra(String palavra) {

        // verifica se a palavra é nula
        if (palavra == null) {
            throw new IllegalArgumentException("palavra nao pode ser nula");
        }

        // normaliza a palavra (remove acentos e deixa maiúscula)
        String palavraNormalizada = normalizarPalavra(palavra);

        // verifica se tem exatamente 5 letras
        if (palavraNormalizada.length() != TAMANHO_PALAVRA) {
            throw new IllegalArgumentException(
                "palavra deve ter exatamente " + TAMANHO_PALAVRA + " letras"
            );
        }

        // guarda a palavra original em maiúscula
        palavraOriginal = palavra.toUpperCase();

        // cria a lista de letras
        letras = new ArrayList<>(TAMANHO_PALAVRA);

        // transforma cada caractere em um objeto Letra
        for (int i = 0; i < TAMANHO_PALAVRA; i++) {
            letras.add(new Letra(palavraNormalizada.charAt(i)));
        }
    }

    // retorna uma cópia da lista de letras
    // evita que o código externo modifique a lista original
    public List<Letra> getLetras() {
        return new ArrayList<>(letras);
    }

    // retorna a letra de uma posição específica
    public Letra getLetra(int indice) {

        // verifica se o índice está dentro do limite
        if (indice < 0 || indice >= TAMANHO_PALAVRA) {
            throw new IndexOutOfBoundsException(
                "indice deve estar entre 0 e " + (TAMANHO_PALAVRA - 1)
            );
        }

        return letras.get(indice);
    }

    // retorna a palavra original
    public String getPalavraOriginal() {
        return palavraOriginal;
    }

    // compara essa palavra com a palavra secreta
    // define o estado de cada letra (correta, posição errada ou ausente)
    public void compararCom(Palavra palavraSecreta) {

        // verifica se a palavra secreta é válida
        if (palavraSecreta == null) {
            throw new IllegalArgumentException("palavra secreta nao pode ser nula");
        }

        // vetor para marcar quais letras da palavra secreta já foram usadas
        boolean[] letraUsada = new boolean[TAMANHO_PALAVRA];

        // primeira passada: verifica letras na posição correta
        for (int i = 0; i < TAMANHO_PALAVRA; i++) {

            Letra tentativa = letras.get(i);
            Letra secreta = palavraSecreta.getLetra(i);

            // se o caractere for igual na mesma posição
            if (tentativa.getCaractere() == secreta.getCaractere()) {

                // marca como correta
                tentativa.setEstado(Letra.Estado.CORRETA);

                // marca essa posição como usada
                letraUsada[i] = true;
            }
        }

        // segunda passada: verifica letras na posição errada ou ausentes
        for (int i = 0; i < TAMANHO_PALAVRA; i++) {

            Letra tentativa = letras.get(i);

            // se já foi marcada como correta, pula
            if (tentativa.getEstado() == Letra.Estado.CORRETA) {
                continue;
            }

            boolean encontrada = false;

            // percorre a palavra secreta
            for (int j = 0; j < TAMANHO_PALAVRA; j++) {

                // verifica se a letra ainda não foi usada
                // e se o caractere é igual
                if (!letraUsada[j]
                        && tentativa.getCaractere() == palavraSecreta.getLetra(j).getCaractere()) {

                    // marca como posição errada
                    tentativa.setEstado(Letra.Estado.POSICAO_ERRADA);

                    // marca a posição como usada
                    letraUsada[j] = true;

                    encontrada = true;
                    break;
                }
            }

            // se não encontrou em nenhuma posição
            if (!encontrada) {
                tentativa.setEstado(Letra.Estado.AUSENTE);
            }
        }
    }

    // verifica se todas as letras estão corretas
    // ou seja, se o jogador acertou a palavra inteira
    public boolean isCorreta() {

        for (Letra letra : letras) {

            if (letra.getEstado() != Letra.Estado.CORRETA) {
                return false;
            }
        }

        return true;
    }

    // normaliza uma palavra
    // remove acentos e converte para maiúscula
    public static String normalizarPalavra(String palavra) {

        if (palavra == null) return "";

        // deixa tudo maiúsculo e remove espaços extras
        String resultado = palavra.toUpperCase().trim();

        // substitui letras acentuadas por letras simples
        resultado = resultado.replace("Á", "A")
                             .replace("À", "A")
                             .replace("Ã", "A")
                             .replace("Â", "A")
                             .replace("É", "E")
                             .replace("Ê", "E")
                             .replace("Í", "I")
                             .replace("Ó", "O")
                             .replace("Ô", "O")
                             .replace("Õ", "O")
                             .replace("Ú", "U")
                             .replace("Ü", "U");

        return resultado;
    }

    // retorna apenas a palavra formada pelas letras
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (Letra letra : letras) {
            sb.append(letra.getCaractere());
        }

        return sb.toString();
    }

    // retorna uma versão detalhada mostrando o estado das letras
    public String toStringDetalhado() {

        StringBuilder sb = new StringBuilder();

        for (Letra letra : letras) {
            sb.append(letra.toString());
        }

        return sb.toString();
    }

    // compara duas palavras pelo conteúdo
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Palavra palavra = (Palavra) obj;

        return this.toString().equals(palavra.toString());
    }

    // gera hash baseado na palavra
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
