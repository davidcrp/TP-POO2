package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dialogo "Sobre" - versão moderna e clean
 */
public class DialogoSobre extends JDialog {

    public DialogoSobre(JFrame parent) {
        super(parent, "Sobre", true);

        setSize(420, 380);
        setLocationRelativeTo(parent);
        setResizable(false);

        criarConteudo();
    }

    private void criarConteudo() {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));
        painel.setBackground(new Color(248, 249, 250));

        // ===== TITULO =====
        JLabel titulo = new JLabel("TERMO");
        titulo.setFont(new Font("Arial", Font.BOLD, 34));
        titulo.setForeground(new Color(30, 30, 30));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(8));

        // ===== SUBTITULO =====
        JLabel versao = new JLabel("Versão 1.0");
        versao.setFont(new Font("Arial", Font.PLAIN, 14));
        versao.setForeground(new Color(120, 120, 120));
        versao.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(versao);
        painel.add(Box.createVerticalStrut(25));

        // ===== DESCRICAO =====
        JLabel descricao1 = criarTexto("Jogo de adivinhação de palavras");
        JLabel descricao2 = criarTexto("inspirado no Wordle e no Termo.");
        JLabel descricao3 = criarTexto("Desenvolvido em Java com Swing");
        JLabel descricao4 = criarTexto("utilizando arquitetura MVC.");

        painel.add(descricao1);
        painel.add(Box.createVerticalStrut(4));
        painel.add(descricao2);
        painel.add(Box.createVerticalStrut(10));
        painel.add(descricao3);
        painel.add(Box.createVerticalStrut(4));
        painel.add(descricao4);

        painel.add(Box.createVerticalStrut(25));

        // ===== SEPARADOR =====
        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        painel.add(separador);

        painel.add(Box.createVerticalStrut(20));

        // ===== CREDITOS =====
        JLabel creditos = new JLabel("© 2026 - UFOP");
        creditos.setFont(new Font("Arial", Font.PLAIN, 12));
        creditos.setForeground(new Color(150, 150, 150));
        creditos.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(creditos);

        painel.add(Box.createVerticalStrut(30));

        // ===== BOTAO =====
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setBackground(new Color(46, 125, 50));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.setPreferredSize(new Dimension(140, 38));
        btnFechar.setMaximumSize(new Dimension(140, 38));

        btnFechar.addActionListener(e -> dispose());

        painel.add(btnFechar);

        add(painel);
    }

    private JLabel criarTexto(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
