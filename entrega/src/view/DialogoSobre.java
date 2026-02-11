package view;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo "Sobre" com informações do jogo
 */
public class DialogoSobre extends JDialog {

    public DialogoSobre(JFrame parent) {
        super(parent, "Sobre TERMO", true);
        
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        criarConteudo();
    }
    
    private void criarConteudo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        painel.setBackground(Color.WHITE);
        
        // Ícone/Logo
        JLabel icone = new JLabel("🎯");
        icone.setFont(new Font("Arial", Font.PLAIN, 72));
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(icone);
        painel.add(Box.createVerticalStrut(15));
        
        // Título
        JLabel titulo = new JLabel("TERMO");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(10));
        
        // Versão
        JLabel versao = new JLabel("Versão 1.0");
        versao.setFont(new Font("Arial", Font.PLAIN, 14));
        versao.setForeground(Color.GRAY);
        versao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(versao);
        painel.add(Box.createVerticalStrut(20));
        
        // Descrição
        String[] descricao = {
            "Jogo de adivinhação de palavras",
            "inspirado no Wordle e Termo.",
            "",
            "Desenvolvido em Java usando Swing",
            "com arquitetura MVC."
        };
        
        for (String linha : descricao) {
            JLabel label = new JLabel(linha);
            label.setFont(new Font("Arial", Font.PLAIN, 13));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(label);
            painel.add(Box.createVerticalStrut(3));
        }
        
        painel.add(Box.createVerticalStrut(15));
        
        // Créditos
        JLabel creditos = new JLabel("© 2024 - Projeto Acadêmico");
        creditos.setFont(new Font("Arial", Font.ITALIC, 12));
        creditos.setForeground(Color.GRAY);
        creditos.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(creditos);
        
        painel.add(Box.createVerticalStrut(20));
        
        // Botão fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFechar.setBackground(new Color(46, 125, 50));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.setPreferredSize(new Dimension(120, 35));
        btnFechar.addActionListener(e -> dispose());
        painel.add(btnFechar);
        
        add(painel);
    }
}