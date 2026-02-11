# TERMO - Jogo de Palavras em Java

## Descrição
TERMO é uma implementação em Java do popular jogo de palavras Wordle, adaptado para o português brasileiro. O objetivo é adivinhar uma palavra secreta de 5 letras em até 6 tentativas, recebendo dicas visuais sobre a posição das letras.

## Requisitos
- Java JDK 8 ou superior
- Biblioteca Swing (incluída no JDK)

## Estrutura do Projeto

```
TermoGame/
├── src/
│   ├── Main.java                    # Ponto de entrada do programa
│   ├── model/                       # Classes de modelo (dados)
│   │   ├── Letra.java              # Representa uma letra com estado
│   │   ├── Palavra.java            # Representa uma palavra de 5 letras
│   │   ├── Partida.java            # Gerencia uma partida do jogo
│   │   ├── Jogador.java            # Dados do jogador
│   │   ├── Estatisticas.java       # Estatísticas do jogador
│   │   └── BancoPalavras.java      # Gerencia o banco de palavras
│   ├── view/                        # Classes de interface gráfica
│   │   ├── JanelaPrincipal.java    # Janela principal do jogo
│   │   ├── PainelGrade.java        # Grade 6x5 de letras
│   │   ├── PainelLetra.java        # Célula individual de letra
│   │   ├── PainelEstatisticas.java # Exibição de estatísticas
│   │   └── TecladoVirtual.java     # Teclado virtual interativo
│   ├── controller/                  # Classes de controle
│   │   └── ControladorJogo.java    # Lógica principal do jogo
│   └── util/                        # Classes utilitárias
│       ├── ConstantesVisuais.java  # Cores, fontes e dimensões
│       └── GerenciadorArquivos.java # Persistência de dados
├── resources/
│   └── palavras.txt                # Banco de palavras (a ser adicionado)
├── docs/
│   └── relatorio.pdf               # Relatório técnico (a ser criado)
└── README.md                       # Este arquivo
```

## Compilação

### Usando linha de comando (Terminal/CMD)

1. **Navegue até a pasta do projeto:**
   ```bash
   cd TermoGame
   ```

2. **Compile todos os arquivos Java:**
   ```bash
   # Linux/Mac
   javac -d bin -sourcepath src src/Main.java src/model/*.java src/view/*.java src/controller/*.java src/util/*.java

   # Windows
   javac -d bin -sourcepath src src\Main.java src\model\*.java src\view\*.java src\controller\*.java src\util\*.java
   ```

3. **Verifique se a pasta `bin` foi criada com os arquivos `.class`**

### Usando IDE (IntelliJ IDEA, Eclipse, NetBeans)

1. Importe o projeto como projeto Java existente
2. Configure a pasta `src` como pasta de fontes
3. Compile o projeto usando a opção Build/Compile da IDE

## Execução

### Pré-requisito
Você precisa do arquivo de palavras `palavras.txt` disponível em:
https://github.com/LauraCoura/BCC221/blob/main/palavras.txt

Baixe e salve na pasta `resources/` ou em qualquer local de sua preferência.

### Executando o jogo

```bash
# Navegue até a pasta bin (onde estão os .class)
cd bin

# Execute informando o caminho do arquivo de palavras
java Main ../resources/palavras.txt

# Ou, se o arquivo estiver em outro local
java Main /caminho/completo/para/palavras.txt
```

### Criando um JAR executável

```bash
# Na pasta do projeto
jar cvfe TermoGame.jar Main -C bin .

# Executando o JAR
java -jar TermoGame.jar resources/palavras.txt
```

## Como Jogar

1. **Inicie o jogo** clicando em "Nova Partida" ou confirmando no diálogo inicial
2. **Digite uma palavra** de 5 letras usando o teclado físico ou virtual
3. **Pressione ENTER** para confirmar sua tentativa
4. **Interprete as cores:**
   - 🟩 **Verde**: Letra na posição correta
   - 🟨 **Amarelo**: Letra existe na palavra, mas em outra posição
   - ⬜ **Cinza**: Letra não existe na palavra
5. **Continue tentando** até acertar ou esgotar as 6 tentativas

## Funcionalidades

### Implementadas
- [x] Interface gráfica com Swing
- [x] Grade de letras 6x5 com cores
- [x] Teclado virtual interativo
- [x] Suporte a teclado físico
- [x] Validação de palavras no dicionário
- [x] Estatísticas persistentes (jogos, vitórias, derrotas)
- [x] Tentativas restantes visíveis
- [x] Letras usadas destacadas no teclado
- [x] Botão Nova Partida
- [x] Botão Sair com confirmação e estatísticas

### Conceitos de POO Utilizados
- **Encapsulamento**: Atributos privados com getters/setters
- **Herança**: PainelLetra herda de JPanel
- **Polimorfismo**: Implementação de interfaces (JogoListener, TecladoListener, KeyListener)
- **Associação**: Jogador possui Estatisticas, Partida possui Palavra
- **Composição**: PainelGrade contém PainelLetra

## Estrutura de Dados

### Arquivo de Palavras
O arquivo `palavras.txt` deve conter uma palavra de 5 letras por linha:
```
ABADE
ABAFA
ABAIXO
...
```

### Dados Persistidos
Os dados são salvos automaticamente na pasta `dados/`:
- `estatisticas.dat`: Estatísticas do jogador
- `jogadores.dat`: Informações de múltiplos jogadores (se implementado)

## Tratamento de Erros

- Arquivo de palavras não encontrado
- Palavra digitada não está no dicionário
- Palavra incompleta (menos de 5 letras)
- Tentativa de jogar após partida terminar

## Autor
[Seu Nome]
[Sua Matrícula]

## Licença
Este projeto foi desenvolvido como trabalho acadêmico para a disciplina BCC 221 - Programação Orientada a Objetos.
