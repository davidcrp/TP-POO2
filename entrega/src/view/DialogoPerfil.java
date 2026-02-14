package view;

import controller.GerenciadorUsuarios;
import model.Estatisticas;
import model.Jogador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
*/

// dialogo que mostra informacoes do perfil do usuario
public class DialogoPerfil extends JDialog {

    // gerenciador de usuarios
    private GerenciadorUsuarios gerenciadorUsuarios;

    // jogador logado
    private Jogador jogador;

    // indica se usuario escolheu relogar
    private boolean deveRelogar;

    // construtor
    public DialogoPerfil(JFrame parent, GerenciadorUsuarios gerenciadorUsuarios) {

        super(parent, "Perfil do Usuário", true);

        this.gerenciadorUsuarios = gerenciadorUsuarios;
        this.jogador = gerenciadorUsuarios.getJogadorLogado();
        this.deveRelogar = false;

        // se nao houver jogador logado fecha dialogo
        if (jogador == null) {
            dispose();
            return;
        }

        configurarJanela();
        criarComponentes();

        setLocationRelativeTo(parent);
    }

    // configura tamanho e fundo da janela
    private void configurarJanela() {
        setSize(500, 600);
        setResizable(false);
        getContentPane().setBackground(new Color(18, 18, 19));
    }

    // cria todos os componentes visuais
    private void criarComponentes() {

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(new Color(18, 18, 19));
        painelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40));

        // titulo
        JLabel lblTitulo = new JLabel("MEU PERFIL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createVerticalStrut(30));

        // nome do usuario
        JPanel painelNome = criarCampoInfo("Usuário", jogador.getNome());
        painelPrincipal.add(painelNome);
        painelPrincipal.add(Box.createVerticalStrut(15));

        // data de cadastro
        String dataCadastro = formatarData(jogador.getDataCriacao());
        JPanel painelData = criarCampoInfo("Membro desde", dataCadastro);
        painelPrincipal.add(painelData);
        painelPrincipal.add(Box.createVerticalStrut(15));

        // ultimo acesso
        String ultimoAcesso = formatarData(jogador.getUltimoAcesso());
        JPanel painelAcesso = criarCampoInfo("Último acesso", ultimoAcesso);
        painelPrincipal.add(painelAcesso);
        painelPrincipal.add(Box.createVerticalStrut(30));

        // separador
        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador.setBackground(new Color(58, 58, 60));
        separador.setForeground(new Color(58, 58, 60));
        painelPrincipal.add(separador);
        painelPrincipal.add(Box.createVerticalStrut(25));

        // estatisticas
        JLabel lblEstatisticas = new JLabel("ESTATÍSTICAS");
        lblEstatisticas.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstatisticas.setForeground(Color.WHITE);
        lblEstatisticas.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelPrincipal.add(lblEstatisticas);
        painelPrincipal.add(Box.createVerticalStrut(20));

        Estatisticas stats = jogador.getEstatisticas();

        JPanel gridStats = new JPanel(new GridLayout(2, 3, 12, 12));
        gridStats.setBackground(new Color(18, 18, 19));
        gridStats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        gridStats.add(criarCardStat("Jogos", String.valueOf(stats.getTotalPartidas())));
        gridStats.add(criarCardStat("Vitórias", String.valueOf(stats.getPartidasGanhas())));
        gridStats.add(criarCardStat("Derrotas", String.valueOf(stats.getPartidasPerdidas())));
        gridStats.add(criarCardStat("Taxa", String.format("%.0f%%", stats.getPorcentagemVitorias())));
        gridStats.add(criarCardStat("Sequência", String.valueOf(stats.getSequenciaAtual())));
        gridStats.add(criarCardStat("Melhor", String.valueOf(stats.getMelhorSequencia())));

        painelPrincipal.add(gridStats);
        painelPrincipal.add(Box.createVerticalStrut(40));

        // botao trocar conta
        JButton btnTrocarConta = criarBotao("TROCAR DE CONTA", new Color(59, 130, 246));
        btnTrocarConta.addActionListener(e -> trocarConta());
        painelPrincipal.add(btnTrocarConta);
        painelPrincipal.add(Box.createVerticalStrut(12));

        // botao fechar
        JButton btnFechar = criarBotao("FECHAR", new Color(106, 170, 100));
        btnFechar.addActionListener(e -> dispose());
        painelPrincipal.add(btnFechar);

        add(painelPrincipal);
    }

    // cria campo simples com label e valor
    private JPanel criarCampoInfo(String label, String valor) {

        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(new Color(18, 18, 19));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        lblLabel.setForeground(new Color(150, 150, 150));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 14));
        lblValor.setForeground(Color.WHITE);

        painel.add(lblLabel, BorderLayout.WEST);
        painel.add(lblValor, BorderLayout.EAST);

        return painel;
    }

    // cria card de estatistica
    private JPanel criarCardStat(String titulo, String valor) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(39, 39, 42));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(58, 58, 60), 1),
                BorderFactory.createEmptyBorder(10, 8, 10, 8)
        ));

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        lblValor.setForeground(Color.WHITE);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTitulo.setForeground(new Color(150, 150, 150));

        card.add(lblValor, BorderLayout.CENTER);
        card.add(lblTitulo, BorderLayout.SOUTH);

        return card;
    }

    // cria botao estilizado
    private JButton criarBotao(String texto, Color cor) {

        JButton btn = new JButton(texto) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(cor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        return btn;
    }

    // confirma troca de conta
    private void trocarConta() {

        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja sair da conta atual e fazer login com outro usuário?\n" +
                        "Seu progresso será salvo automaticamente.",
                "Trocar de Conta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {
            deveRelogar = true;
            dispose();
        }
    }

    // formata data no padrao brasileiro
    private String formatarData(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date(timestamp));
    }

    // retorna se usuario escolheu relogar
    public boolean deveRelogar() {
        return deveRelogar;
    }
}
