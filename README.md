# TERMO — jogo de adivinhação de palavras em Java

> Trabalho prático em grupo da disciplina **Programação Orientada a Objetos** (Ciência da Computação, UFOP).
> Repositório original de Pedro Souza ([@pedrosouzag](https://github.com/pedrosouzag)); o histórico de commits preserva a autoria.

Implementação do jogo **TERMO** (estilo Wordle) com interface gráfica em **Java Swing**, organizada no padrão **MVC**: descubra a palavra secreta de 5 letras em até 6 tentativas, com feedback colorido por letra.

**Stack:** Java · Swing · MVC · serialização em arquivo

**Vídeo de demonstração:** https://youtu.be/rabTKB1mqK0

## Funcionalidades

- Grade de tentativas e teclado virtual com feedback por cor
- Cadastro e login de jogadores, com senha armazenada como hash
- Estatísticas por jogador: vitórias, sequência atual, melhor sequência e distribuição de tentativas
- Banco de palavras carregado de arquivo (`resources/palavras.txt`)

## Estrutura

| Caminho | O que é |
|---|---|
| `entrega/src/model` | Regras do jogo: palavra, partida, jogador, estatísticas |
| `entrega/src/view` | Janelas, grade, teclado e diálogos (Swing) |
| `entrega/src/controller` | Controle do jogo e dos usuários |
| `entrega/src/util` | Persistência em arquivo e constantes visuais |
| `entrega/TermoGame.jar` | Versão executável |
| `entrega/RELATORIO TP2 POO.pdf` | Relatório do trabalho |

## Executar

```bash
cd entrega
java -jar TermoGame.jar
```

Ou compilando:

```bash
cd entrega
javac -d bin $(find src -name "*.java")
java -cp bin Main
```

Detalhes das regras e da arquitetura em [`entrega/README.md`](entrega/README.md).
