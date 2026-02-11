package view;

import util.ConstantesVisuais;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo que explica como jogar TERMO
 */
public class DialogoAjuda extends JDialog {

    public DialogoAjuda(JFrame parent) {
        super(parent, "Como Jogar TERMO", true);
        
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        criarConteudo();
    }
    
    private void criarConteudo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painel.setBackground(Color.WHITE);
        
        // Título
        JLabel titulo = new JLabel("🎯 COMO JOGAR TERMO");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));
        
        // Regras
        String[] regras = {
            "📝 OBJETIVO",
            "Adivinhe a palavra secreta em até 6 tentativas.",
            "",
            "🎮 COMO JOGAR",
            "1. Digite uma palavra de 5 letras",
            "2. Pressione ENTER para enviar",
            "3. As cores das letras mudarão para mostrar:",
            "",
            "🟩 VERDE = Letra correta na posição correta",
            "🟨 AMARELO = Letra existe, mas está na posição errada",
            "⬜ CINZA = Letra não existe na palavra",
            "",
            "⌨️ ATALHOS",
            "• ENTER = Enviar palavra",
            "• BACKSPACE = Apagar letra",
            "• Ctrl+N = Novo jogo",
            "• Ctrl+R = Reiniciar partida",
            "",
            "💡 DICAS",
            "• Comece com palavras que tenham vogais variadas",
            "• Use as informações das tentativas anteriores",
            "• O teclado virtual mostra quais letras já foram usadas",
            "",
            "🎯 BOA SORTE!"
        };
        
        for (String linha : regras) {
            JLabel label;
            if (linha.isEmpty()) {
                painel.add(Box.createVerticalStrut(10));
                continue;
            } else if (linha.contains("OBJETIVO") || linha.contains("COMO JOGAR") || 
                       linha.contains("ATALHOS") || linha.contains("DICAS")) {
                label = new JLabel(linha);
                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setForeground(new Color(46, 125, 50));
            } else if (linha.startsWith("🟩") || linha.startsWith("🟨") || linha.startsWith("⬜")) {
                label = new JLabel(linha);
                label.setFont(new Font("Arial", Font.PLAIN, 13));
            } else {
                label = new JLabel(linha);
                label.setFont(new Font("Arial", Font.PLAIN, 13));
            }
            
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            painel.add(label);
            painel.add(Box.createVerticalStrut(5));
        }
        
        // Botão fechar
        painel.add(Box.createVerticalStrut(15));
        JButton btnFechar = new JButton("Entendi!");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setBackground(new Color(46, 125, 50));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFechar.setPreferredSize(new Dimension(150, 40));
        btnFechar.addActionListener(e -> dispose());
        painel.add(btnFechar);
        
        // Scroll pane
        JScrollPane scroll = new JScrollPane(painel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scroll);
    }
}