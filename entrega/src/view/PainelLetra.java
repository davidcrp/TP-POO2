package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

public class PainelLetra extends JPanel {

    // Estados possíveis
    public static final String INDEFINIDO = "INDEFINIDO";
    public static final String CORRETA = "CORRETA";
    public static final String POSICAO_ERRADA = "POSICAO_ERRADA";
    public static final String AUSENTE = "AUSENTE";

    private char caractere;
    private String estado;
    private Object letraObject;
    private JLabel labelLetra;
    private boolean ativo;

    // Raio dos cantos arredondados
    private static final int RAIO_CANTOS = 8;

    public PainelLetra() {
        this.caractere = ' ';
        this.estado = INDEFINIDO;
        this.letraObject = null;
        this.ativo = false;
        configurarPainel();
        atualizarVisual();
    }

    private void configurarPainel() {
        Dimension tamanho = new Dimension(62, 62);
        setPreferredSize(tamanho);
        setMinimumSize(tamanho);
        setMaximumSize(tamanho);

        setLayout(new BorderLayout());
        setOpaque(false); // Importante para cantos arredondados
        setBorder(null); // IMPORTANTE: Sem borda padrão

        labelLetra = new JLabel("", SwingConstants.CENTER);
        labelLetra.setFont(new Font("Arial", Font.BOLD, 32));
        labelLetra.setOpaque(false);
        labelLetra.setBorder(null); // IMPORTANTE: Label sem borda
        
        add(labelLetra, BorderLayout.CENTER);
    }

    public void atualizarVisual() {
        labelLetra.setText(caractere == ' ' ? "" : String.valueOf(caractere));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Anti-aliasing para bordas suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int width = getWidth();
        int height = getHeight();
        
        Color fundo;
        Color texto;
        Color borda;
        int espessuraBorda = 2;
        boolean temLetra = caractere != ' ';

        // Define cores baseado no estado
        switch (estado) {
            case CORRETA:
                fundo = new Color(106, 170, 100);    // Verde IDÊNTICO ao teclado
                texto = Color.WHITE;
                borda = new Color(106, 170, 100);
                espessuraBorda = 0;
                break;

            case POSICAO_ERRADA:
                fundo = new Color(201, 180, 88);   // Amarelo IDÊNTICO ao teclado
                texto = Color.WHITE;
                borda = new Color(201, 180, 88);
                espessuraBorda = 0;
                break;

            case AUSENTE:
                fundo = new Color(58, 58, 60);     // Cinza escuro
                texto = Color.WHITE;
                borda = new Color(58, 58, 60);
                espessuraBorda = 0;
                break;

            default:
                // Fundo MAIS ESCURO quando vazio
                if (temLetra) {
                    fundo = new Color(18, 18, 19);
                    texto = Color.WHITE;
                    borda = new Color(86, 87, 88);  // Borda cinza clara quando tem letra
                } else {
                    fundo = new Color(18, 18, 19);  // Mesmo fundo escuro
                    texto = Color.WHITE;
                    
                    // Borda especial quando ativo (cursor)
                    if (ativo) {
                        borda = new Color(135, 138, 140);
                        espessuraBorda = 2;
                    } else {
                        borda = new Color(58, 58, 60);  // Borda mais escura quando vazio
                    }
                }
                break;
        }

        // Desenha fundo com cantos arredondados
        g2.setColor(fundo);
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, RAIO_CANTOS, RAIO_CANTOS));

        // Desenha borda com cantos arredondados
        if (espessuraBorda > 0) {
            g2.setColor(borda);
            g2.setStroke(new BasicStroke(espessuraBorda));
            g2.draw(new RoundRectangle2D.Double(
                espessuraBorda / 2.0, 
                espessuraBorda / 2.0, 
                width - espessuraBorda, 
                height - espessuraBorda, 
                RAIO_CANTOS, 
                RAIO_CANTOS
            ));
        }

        g2.dispose();
        
        // Atualiza cor do texto
        labelLetra.setForeground(texto);
        
        super.paintComponent(g);
    }

    // ========== MÉTODOS PARA USO DIRETO ==========

    public char getCaractere() {
        return caractere;
    }

    public void setCaractere(char c) {
        this.caractere = c;
        atualizarVisual();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        atualizarVisual();
    }

    // ========== COMPATIBILIDADE COM model.Letra ==========

    public Object getLetra() {
        return letraObject;
    }

public void setLetra(Object letra) {
        this.letraObject = letra;
        
        if (letra == null) {
            limpar();
            return;
        }

        try {
            // 1. Extrai o caractere (ex: 'A', 'G')
            java.lang.reflect.Method getChar = letra.getClass().getMethod("getCaractere");
            Object caracterObj = getChar.invoke(letra);
            if (caracterObj instanceof Character) {
                this.caractere = (Character) caracterObj;
            }

            // 2. Extrai o estado (ex: Estado.POSICAO_ERRADA)
            java.lang.reflect.Method getEst = letra.getClass().getMethod("getEstado");
            Object estObj = getEst.invoke(letra);
            
            if (estObj != null) {
                String estadoStr = estObj.toString().toUpperCase();
                
                // Limpa o nome caso venha com o prefixo da classe (Ex: "Estado.CORRETA" -> "CORRETA")
                if (estadoStr.contains(".")) {
                    estadoStr = estadoStr.substring(estadoStr.lastIndexOf(".") + 1);
                }
                
                // Mapeamento flexível para garantir que a cor amarela/verde funcione
                if (estadoStr.contains("CORRETA")) {
                    this.estado = CORRETA;
                } else if (estadoStr.contains("ERRADA") || estadoStr.contains("INCORRETA")) {
                    this.estado = POSICAO_ERRADA;
                } else if (estadoStr.contains("AUSENTE")) {
                    this.estado = AUSENTE;
                } else {
                    this.estado = INDEFINIDO;
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao processar objeto Letra: " + e.getMessage());
            // Fallback: tenta usar o toString do objeto como estado
            this.estado = letra.toString().toUpperCase();
        }

        // Força a atualização do texto no JLabel e a repintura do fundo (cores)
        atualizarVisual();
    }

    public void setLetraObject(Object letra) {
        setLetra(letra);
    }

    // ========== MÉTODOS COMUNS ==========

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
        atualizarVisual();
    }

    public void limpar() {
        caractere = ' ';
        estado = INDEFINIDO;
        letraObject = null;
        ativo = false;
        setBorder(null); // IMPORTANTE: Remove qualquer borda
        atualizarVisual();
    }

    public boolean isVazio() {
        return caractere == ' ';
    }
}