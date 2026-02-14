package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

// dialogo "sobre" do jogo
// mostra informacoes da aplicacao
public class DialogoSobre extends JDialog {

    // construtor
    public DialogoSobre(JFrame parent) {

        // chama construtor da superclasse
        // titulo = "Sobre"
        // true = modal
        super(parent, "Sobre", true);

        setSize(420, 380);
        setLocationRelativeTo(parent);
        setResizable(false);

        criarConteudo();
    }

    // cria todo o layout do dialogo
    private void criarConteudo() {

        JPanel painel = new JPanel();

        // layout vertical
        painel.setLayout(
                new BoxLayout(painel, BoxLayout.Y_AXIS)
        );

        // margem interna
        painel.setBorder(
                new EmptyBorder(30, 40, 30, 40)
        );

        // fundo escuro
        painel.setBackground(
                new Color(18, 18, 19)
        );

        
        // titulo principal

        JLabel titulo = new JLabel("TERMO");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 34)
        );

        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(8));

      
        // versao

        JLabel versao =
                new JLabel("Versão 2.0");

        versao.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        versao.setForeground(
                new Color(150, 150, 150)
        );

        versao.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painel.add(versao);
        painel.add(Box.createVerticalStrut(25));

      
        // descricao

        JLabel descricao1 =
                criarTexto("Jogo de adivinhação de palavras");

        JLabel descricao2 =
                criarTexto("inspirado no Wordle e no Termo.");

        JLabel descricao3 =
                criarTexto("Desenvolvido em Java com Swing");

        JLabel descricao4 =
                criarTexto("utilizando arquitetura MVC.");

        painel.add(descricao1);
        painel.add(Box.createVerticalStrut(4));
        painel.add(descricao2);
        painel.add(Box.createVerticalStrut(10));
        painel.add(descricao3);
        painel.add(Box.createVerticalStrut(4));
        painel.add(descricao4);

        painel.add(Box.createVerticalStrut(25));

      
        // separador visual

        JSeparator separador =
                new JSeparator();

        separador.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 1)
        );

        separador.setBackground(
                new Color(58, 58, 60)
        );

        separador.setForeground(
                new Color(58, 58, 60)
        );

        painel.add(separador);
        painel.add(Box.createVerticalStrut(20));

       
        // creditos


        JLabel creditos =
                new JLabel("© 2026 - UFOP");

        creditos.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        creditos.setForeground(
                new Color(120, 120, 120)
        );

        creditos.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        painel.add(creditos);
        painel.add(Box.createVerticalStrut(30));

        // ======================
        // botao fechar
        // ======================

        JButton btnFechar =
                new JButton("Fechar") {

            // sobrescreve pintura para estilo moderno
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 =
                        (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                // fundo verde arredondado
                g2.setColor(
                        new Color(106, 170, 100)
                );

                g2.fillRoundRect(
                        0, 0,
                        getWidth(),
                        getHeight(),
                        8, 8
                );

                g2.dispose();

                super.paintComponent(g);
            }
        };

        btnFechar.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setContentAreaFilled(false);

        btnFechar.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        btnFechar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        btnFechar.setPreferredSize(
                new Dimension(140, 38)
        );

        btnFechar.setMaximumSize(
                new Dimension(140, 38)
        );

        // fecha dialogo ao clicar
        btnFechar.addActionListener(
                e -> dispose()
        );

        painel.add(btnFechar);

        add(painel);
    }

    // cria label padrao de texto
    private JLabel criarTexto(String texto) {

        JLabel label = new JLabel(texto);

        label.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        label.setForeground(
                new Color(180, 180, 180)
        );

        label.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        return label;
    }
}
