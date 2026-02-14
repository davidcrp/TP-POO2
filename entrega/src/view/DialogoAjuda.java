package view;

import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
*/

// dialogo que mostra as instrucoes do jogo
public class DialogoAjuda extends JDialog {

    // construtor
    public DialogoAjuda(JFrame parent) {
        super(parent, "Como Jogar", true);

        setSize(520, 650);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(new Color(18, 18, 19));

        criarConteudo();
    }

    // monta toda a interface da ajuda
    private void criarConteudo() {

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(18, 18, 19));
        container.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // titulo principal
        JLabel titulo = new JLabel("COMO JOGAR", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        container.add(titulo, BorderLayout.NORTH);

        // painel interno com as secoes
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(new Color(18, 18, 19));
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        adicionarSecao(conteudo,
                "OBJETIVO",
                "Descubra a palavra secreta em até 6 tentativas.");

        adicionarSecao(conteudo,
                "COMO FUNCIONA",
                "Digite uma palavra de 5 letras e pressione ENTER.\n" +
                "Após cada tentativa, as letras mudam de cor indicando o resultado.");

        adicionarExemploCores(conteudo);

        adicionarSecao(conteudo,
                "BARRA SUPERIOR",
                "?  - Abre esta tela de ajuda\n" +
                "R  - Reinicia a partida atual\n" +
                "X  - Fecha o jogo\n" +
                "i  - Exibe as estatísticas do jogador");

        adicionarSecao(conteudo,
                "ATALHOS",
                "ENTER - Enviar palavra\n" +
                "BACKSPACE - Apagar letra\n" +
                "Ctrl + N - Nova partida\n" +
                "Ctrl + R - Reiniciar partida");

        adicionarSecao(conteudo,
                "DICAS",
                "Use palavras com vogais variadas.\n" +
                "Aproveite as informações das tentativas anteriores.\n" +
                "Observe o teclado virtual para acompanhar letras usadas.");

        // scroll para permitir rolagem
        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.setBackground(new Color(18, 18, 19));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getViewport().setBackground(new Color(18, 18, 19));

        container.add(scroll, BorderLayout.CENTER);

        // botao fechar
        JButton btnFechar = new JButton("Entendi") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(106, 170, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btnFechar.setFont(new Font("Arial", Font.BOLD, 15));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setPreferredSize(new Dimension(160, 40));
        btnFechar.addActionListener(e -> dispose());

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(new Color(18, 18, 19));
        painelBotao.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        painelBotao.add(btnFechar);

        container.add(painelBotao, BorderLayout.SOUTH);
        add(container);
    }

    // adiciona uma secao com titulo e texto
    private void adicionarSecao(JPanel painel, String titulo, String texto) {

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(106, 170, 100));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JTextArea areaTexto = new JTextArea(texto);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        areaTexto.setForeground(new Color(200, 200, 200));
        areaTexto.setBackground(new Color(18, 18, 19));
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        areaTexto.setBorder(BorderFactory.createEmptyBorder(5, 0, 18, 0));

        painel.add(lblTitulo);
        painel.add(areaTexto);
    }

    // adiciona exemplos visuais das cores
    private void adicionarExemploCores(JPanel painel) {

        JLabel titulo = new JLabel("SIGNIFICADO DAS CORES");
        titulo.setFont(new Font("Arial", Font.BOLD, 15));
        titulo.setForeground(new Color(106, 170, 100));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        painel.add(titulo);

        painel.add(criarLinhaCor("A", new Color(106, 170, 100), "Letra correta na posição correta"));
        painel.add(criarLinhaCor("B", new Color(201, 180, 88), "Letra existe mas está na posição errada"));
        painel.add(criarLinhaCor("C", new Color(58, 58, 60), "Letra não existe na palavra"));

        painel.add(Box.createVerticalStrut(20));
    }

    // cria uma linha com exemplo de cor
    private JPanel criarLinhaCor(String letra, Color cor, String descricao) {

        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha.setBackground(new Color(18, 18, 19));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bloco = new JLabel(letra, SwingConstants.CENTER);
        bloco.setPreferredSize(new Dimension(40, 40));
        bloco.setOpaque(true);
        bloco.setBackground(cor);
        bloco.setForeground(Color.WHITE);
        bloco.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblDescricao = new JLabel(descricao);
        lblDescricao.setForeground(new Color(200, 200, 200));
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescricao.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        linha.add(bloco);
        linha.add(lblDescricao);

        return linha;
    }
}
