package model;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// representa uma letra individual do jogo termo
// cada letra possui um caractere e um estado visual
public class Letra {

    // estados possiveis de uma letra no jogo
    public enum Estado {
        INDEFINIDO,     // ainda nao foi verificada
        CORRETA,        // letra correta na posicao correta
        POSICAO_ERRADA, // letra existe mas esta em outra posicao
        AUSENTE         // letra nao existe na palavra
    }

    // caractere representado pela letra
    private char caractere;

    // estado atual da letra
    private Estado estado;

    // cria uma letra vazia com estado indefinido
    public Letra() {
        caractere = ' ';
        estado = Estado.INDEFINIDO;
    }

    // cria uma letra com o caractere informado
    public Letra(char caractere) {
        this.caractere = Character.toUpperCase(caractere);
        estado = Estado.INDEFINIDO;
    }

    // cria uma letra com caractere e estado definidos
    public Letra(char caractere, Estado estado) {
        this.caractere = Character.toUpperCase(caractere);
        this.estado = estado;
    }

    // retorna o caractere da letra
    public char getCaractere() {
        return caractere;
    }

    // define o caractere da letra
    public void setCaractere(char caractere) {
        this.caractere = Character.toUpperCase(caractere);
    }

    // retorna o estado atual da letra
    public Estado getEstado() {
        return estado;
    }

    // define o estado da letra
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    // verifica se a letra esta correta
    public boolean isCorreta() {
        return estado == Estado.CORRETA;
    }

    // verifica se a letra esta em posicao errada
    public boolean isPosicaoErrada() {
        return estado == Estado.POSICAO_ERRADA;
    }

    // verifica se a letra esta ausente
    public boolean isAusente() {
        return estado == Estado.AUSENTE;
    }

    // reseta o estado da letra
    public void resetar() {
        estado = Estado.INDEFINIDO;
    }

    // representacao textual da letra
    @Override
    public String toString() {
        return String.format("[%c:%s]", caractere, estado.name());
    }

    // compara letras apenas pelo caractere
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Letra letra = (Letra) obj;
        return Character.toUpperCase(caractere) == Character.toUpperCase(letra.caractere);
    }

    // gera hash baseado no caractere
    @Override
    public int hashCode() {
        return Character.hashCode(Character.toUpperCase(caractere));
    }
}
