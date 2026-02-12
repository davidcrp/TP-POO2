package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */


public class TecladoVirtual extends JPanel {
    
    private Map<Character, BotaoLetra> teclas;
    private TecladoListener listener;
    
    // CORES IDÊNTICAS À GRADE
    private static final Color COR_VERDE = new Color(106, 170, 100);    // Verde da grade
    private static final Color COR_AMARELO = new Color(201, 180, 88);   // Amarelo da grade
    private static final Color COR_CINZA_ESCURO = new Color(58, 58, 60); // Cinza escuro
    private static final Color COR_PADRAO = new Color(129, 131, 132);   // Cinza padrão
    
    public interface TecladoListener {
        void onLetraPressionada(char letra);
        void onEnterPressionado();
        void onBackspacePressionado();
    }
    
    public TecladoVirtual() {
        this.teclas = new HashMap<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(10, 8, 20, 8));
        criarTeclado();
    }
    
    private void criarTeclado() {
        add(criarLinha1());
        add(Box.createVerticalStrut(6));
        add(criarLinha2());
        add(Box.createVerticalStrut(6));
        add(criarLinha3());
    }
    
    private JPanel criarLinha1() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        p.setBackground(Color.BLACK);
        
        adicionarTecla(p, 'Q');
        adicionarTecla(p, 'W');
        adicionarTecla(p, 'E');
        adicionarTecla(p, 'R');
        adicionarTecla(p, 'T');
        adicionarTecla(p, 'Y');
        adicionarTecla(p, 'U');
        adicionarTecla(p, 'I');
        adicionarTecla(p, 'O');
        adicionarTecla(p, 'P');
        
        return p;
    }
    
    private JPanel criarLinha2() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        p.setBackground(Color.BLACK);
        
        adicionarTecla(p, 'A');
        adicionarTecla(p, 'S');
        adicionarTecla(p, 'D');
        adicionarTecla(p, 'F');
        adicionarTecla(p, 'G');
        adicionarTecla(p, 'H');
        adicionarTecla(p, 'J');
        adicionarTecla(p, 'K');
        adicionarTecla(p, 'L');
        
        BotaoEspecial back = new BotaoEspecial("DEL", 65);
        back.addActionListener(e -> {
            if (listener != null) listener.onBackspacePressionado();
        });
        back.setPreferredSize(new Dimension(65, 55));
        p.add(back);
        
        return p;
    }
    
    private JPanel criarLinha3() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        p.setBackground(Color.BLACK);
        
        adicionarTecla(p, 'Z');
        adicionarTecla(p, 'X');
        adicionarTecla(p, 'C');
        adicionarTecla(p, 'V');
        adicionarTecla(p, 'B');
        adicionarTecla(p, 'N');
        adicionarTecla(p, 'M');
        
        BotaoEspecial enter = new BotaoEspecial("ENTER", 90);
        enter.addActionListener(e -> {
            if (listener != null) listener.onEnterPressionado();
        });
        p.add(enter);
        
        return p;
    }
    
    private void adicionarTecla(JPanel painel, char letra) {
        BotaoLetra btn = new BotaoLetra(letra);
        btn.addActionListener(e -> {
            if (listener != null) listener.onLetraPressionada(letra);
        });
        teclas.put(letra, btn);
        painel.add(btn);
    }
    
    public void atualizarCorTecla(char letra, Object estado) {
        BotaoLetra btn = teclas.get(Character.toUpperCase(letra));
        if (btn == null) return;
        
        String s = estado.toString();
        if (s.contains(".")) s = s.substring(s.lastIndexOf(".") + 1);
        
        Color corAtual = btn.getCor();
        Color novaCor = null;
        
        if ("CORRETA".equals(s)) {
            novaCor = COR_VERDE;
        } else if ("POSICAO_ERRADA".equals(s)) {
            // Verde tem prioridade sobre amarelo
            if (corAtual.equals(COR_VERDE)) {
                return;
            }
            novaCor = COR_AMARELO;
        } else if ("AUSENTE".equals(s)) {
            // Verde e amarelo têm prioridade sobre cinza
            if (corAtual.equals(COR_VERDE) || corAtual.equals(COR_AMARELO)) {
                return;
            }
            novaCor = COR_CINZA_ESCURO;
        }
        
        if (novaCor != null) {
            btn.setCor(novaCor);
        }
    }
    
    public void resetar() {
        for (BotaoLetra btn : teclas.values()) {
            btn.setCor(COR_PADRAO);
        }
    }
    
    public void setListener(TecladoListener listener) {
        this.listener = listener;
    }
    
    // Classe interna para botão com letra
    class BotaoLetra extends JComponent {
        private char letra;
        private Color cor;
        
        public BotaoLetra(char letra) {
            this.letra = letra;
            this.cor = COR_PADRAO;
            setPreferredSize(new Dimension(42, 52));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    for (java.awt.event.ActionListener al : getActionListeners()) {
                        al.actionPerformed(new java.awt.event.ActionEvent(BotaoLetra.this, 0, ""));
                    }
                }
            });
        }
        
        public Color getCor() {
            return cor;
        }
        
        public void setCor(Color cor) {
            this.cor = cor;
            repaint();
        }
        
        private java.util.List<java.awt.event.ActionListener> listeners = new java.util.ArrayList<>();
        
        public void addActionListener(java.awt.event.ActionListener l) {
            listeners.add(l);
        }
        
        private java.awt.event.ActionListener[] getActionListeners() {
            return listeners.toArray(new java.awt.event.ActionListener[0]);
        }
        
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Desenha fundo
            g2.setColor(cor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 4, 4));
            
            // Desenha letra
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fm = g2.getFontMetrics();
            String s = String.valueOf(letra);
            int x = (getWidth() - fm.stringWidth(s)) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(s, x, y);
            
            g2.dispose();
        }
    }
    
    // Classe interna para botões especiais
    class BotaoEspecial extends JComponent {
        private String texto;
        private Color cor;
        
        public BotaoEspecial(String texto, int largura) {
            this.texto = texto;
            this.cor = COR_PADRAO;
            
            Dimension tamanho = new Dimension(largura, 58);
            setPreferredSize(tamanho);
            setMinimumSize(tamanho);
            setMaximumSize(tamanho);
            
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setOpaque(false);
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    for (java.awt.event.ActionListener al : getActionListeners()) {
                        al.actionPerformed(new java.awt.event.ActionEvent(BotaoEspecial.this, 0, ""));
                    }
                }
            });
        }
        
        private java.util.List<java.awt.event.ActionListener> listeners = new java.util.ArrayList<>();
        
        public void addActionListener(java.awt.event.ActionListener l) {
            listeners.add(l);
        }
        
        private java.awt.event.ActionListener[] getActionListeners() {
            return listeners.toArray(new java.awt.event.ActionListener[0]);
        }
        
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            g2.setColor(cor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 4, 4));
            
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 18));  // Aumentado de 14 para 18
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(texto, x, y);
            
            g2.dispose();
        }
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}