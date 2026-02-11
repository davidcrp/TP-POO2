package model;

import java.util.ArrayList;
import java.util.List;

// representa uma palavra de 5 letras no jogo termo
// usada tanto para a palavra secreta quanto para tentativas
public class Palavra {

    // tamanho padrao das palavras do jogo
    public static final int TAMANHO_PALAVRA = 5;

    // letras que compoem a palavra
    private List<Letra> letras;

    // palavra original informada
    private String palavraOriginal;

    // cria uma palavra vazia
    public Palavra() {
        letras = new ArrayList<>(TAMANHO_PALAVRA);
        palavraOriginal = "";

        for (int i = 0; i < TAMANHO_PALAVRA; i++) {
            letras.add(new Letra());
        }
    }

    // cria uma palavra a partir de uma string
    public Palavra(String palavra) {

        if (palavra == null) {
            throw new IllegalArgumentException("palavra nao pode ser nula");
        }

        String palavraNormalizada = normalizarPalavra(palavra);

        if (palavraNormalizada.length() != TAMANHO_PALAVRA) {
            throw new IllegalArgumentException(
                "palavra deve ter exatamente " + TAMANHO_PALAVRA + " letras"
            );
        }

        palavraOriginal = palavra.toUpperCase();
        letras = new ArrayList<>(TAMANHO_PALAVRA);

        for (int i = 0; i < TAMANHO_PALAVRA; i++) {
            letras.add(new Letra(palavraNormalizada.charAt(i)));
        }
    }

    // retorna uma copia da lista de letras
    public List<Letra> getLetras() {
        return new ArrayList<>(letras);
    }

    // retorna a letra em uma posicao especifica
    public Letra getLetra(int indice) {

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

    // compara esta palavra com a palavra secreta
    // define o estado de cada letra conforme as regras do jogo
    public void compararCom(Palavra palavraSecreta) {

        if (palavraSecreta == null) {
            throw new IllegalArgumentException("palavra secreta nao pode ser nula");
        }

        boolean[] letraUsada = new boolean[TAMANHO_PALAVRA];

        // primeira passada: letras corretas
        for (int i = 0; i < TAMANHO_PALAVRA; i++) {
            Letra tentativa = letras.get(i);
            Letra secreta = palavraSecreta.getLetra(i);

            if (tentativa.getCaractere() == secreta.getCaractere()) {
                tentativa.setEstado(Letra.Estado.CORRETA);
                letraUsada[i] = true;
            }
        }

        // segunda passada: letras em posicao errada ou ausentes
        for (int i = 0; i < TAMANHO_PALAVRA; i++) {

            Letra tentativa = letras.get(i);

            if (tentativa.getEstado() == Letra.Estado.CORRETA) {
                continue;
            }

            boolean encontrada = false;

            for (int j = 0; j < TAMANHO_PALAVRA; j++) {
                if (!letraUsada[j]
                        && tentativa.getCaractere() == palavraSecreta.getLetra(j).getCaractere()) {

                    tentativa.setEstado(Letra.Estado.POSICAO_ERRADA);
                    letraUsada[j] = true;
                    encontrada = true;
                    break;
                }
            }

            if (!encontrada) {
                tentativa.setEstado(Letra.Estado.AUSENTE);
            }
        }
    }

    // verifica se a palavra foi completamente acertada
    public boolean isCorreta() {
        for (Letra letra : letras) {
            if (letra.getEstado() != Letra.Estado.CORRETA) {
                return false;
            }
        }
        return true;
    }

    // normaliza uma palavra removendo acentos e convertendo para maiuscula
    public static String normalizarPalavra(String palavra) {

        if (palavra == null) return "";

        String resultado = palavra.toUpperCase().trim();

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

    // retorna representacao detalhada com estados das letras
    public String toStringDetalhado() {
        StringBuilder sb = new StringBuilder();
        for (Letra letra : letras) {
            sb.append(letra.toString());
        }
        return sb.toString();
    }

    // compara palavras pelo conteudo das letras
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
