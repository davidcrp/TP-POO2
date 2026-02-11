package view;

import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;

/**
 * Dialogo profissional de ajuda - estilo similar ao Termo
 */
public class DialogoAjuda extends JDialog {

    public DialogoAjuda(JFrame parent) {
        super(parent, "Como Jogar", true);

        setSize(520, 600);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(ConstantesVisuais.COR_FUNDO);

        criarConteudo();
    }

    private void criarConteudo() {

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(ConstantesVisuais.COR_FUNDO);
        container.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // ===== TITULO =====
        JLabel titulo = new JLabel("COMO JOGAR", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);

        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        container.add(titulo, BorderLayout.NORTH);

        // ===== CONTEUDO CENTRAL =====
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(ConstantesVisuais.COR_FUNDO);

        adicionarSecao(conteudo,
                "OBJETIVO",
                "Descubra a palavra secreta em até 6 tentativas.");

        adicionarSecao(conteudo,
                "COMO FUNCIONA",
                "Digite uma palavra de 5 letras e pressione ENTER.\n" +
                "Após cada tentativa, as letras mudam de cor indicando o resultado.");

        adicionarExemploCores(conteudo);

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

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.setBackground(ConstantesVisuais.COR_FUNDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        container.add(scroll, BorderLayout.CENTER);

        // ===== BOTAO =====
        JButton btnFechar = new JButton("Entendi");
        btnFechar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnFechar.setBackground(ConstantesVisuais.COR_SUCESSO);
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setPreferredSize(new Dimension(160, 40));
        btnFechar.addActionListener(e -> dispose());

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(ConstantesVisuais.COR_FUNDO);
        painelBotao.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        painelBotao.add(btnFechar);

        container.add(painelBotao, BorderLayout.SOUTH);

        add(container);
    }

    private void adicionarSecao(JPanel painel, String titulo, String texto) {
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTitulo.setForeground(ConstantesVisuais.COR_SUCESSO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea areaTexto = new JTextArea(texto);
        areaTexto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        areaTexto.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);

        areaTexto.setBackground(ConstantesVisuais.COR_FUNDO);
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        areaTexto.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        painel.add(lblTitulo);
        painel.add(areaTexto);
    }

    private void adicionarExemploCores(JPanel painel) {
        JLabel titulo = new JLabel("SIGNIFICADO DAS CORES");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        titulo.setForeground(ConstantesVisuais.COR_SUCESSO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        painel.add(titulo);

        painel.add(criarLinhaCor("A", new Color(46, 125, 50), "Letra correta na posição correta"));
        painel.add(criarLinhaCor("B", new Color(255, 193, 7), "Letra existe mas está na posição errada"));
        painel.add(criarLinhaCor("C", new Color(120, 124, 126), "Letra não existe na palavra"));

        painel.add(Box.createVerticalStrut(20));
    }

    private JPanel criarLinhaCor(String letra, Color cor, String descricao) {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha.setBackground(ConstantesVisuais.COR_FUNDO);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bloco = new JLabel(letra, SwingConstants.CENTER);
        bloco.setPreferredSize(new Dimension(40, 40));
        bloco.setOpaque(true);
        bloco.setBackground(cor);
        bloco.setForeground(Color.WHITE);
        bloco.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel lblDescricao = new JLabel(descricao);
        lblDescricao.setForeground(ConstantesVisuais.COR_TEXTO_ESCURO);

        lblDescricao.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescricao.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        linha.add(bloco);
        linha.add(lblDescricao);

        return linha;
    }
}
