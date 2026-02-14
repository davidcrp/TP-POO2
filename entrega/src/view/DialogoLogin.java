package view;

import controller.GerenciadorUsuarios;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
*/

// dialogo responsavel por login e cadastro de usuarios
public class DialogoLogin extends JDialog {

    // gerenciador de usuarios
    private GerenciadorUsuarios gerenciadorUsuarios;

    // campos de entrada
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    // botoes
    private JButton btnLogin;
    private JButton btnCadastrar;

    // label para mensagens
    private JLabel lblMensagem;

    // indica se login foi bem sucedido
    private boolean loginBemSucedido;

    // construtor
    public DialogoLogin(JFrame parent,
                        GerenciadorUsuarios gerenciadorUsuarios) {

        super(parent, "TERMO - Login", true);

        this.gerenciadorUsuarios = gerenciadorUsuarios;
        this.loginBemSucedido = false;

        configurarJanela();
        criarComponentes();

        setLocationRelativeTo(parent);
    }

    // configura tamanho e comportamento da janela
    private void configurarJanela() {

        setSize(450, 520);
        setResizable(false);

        // impede fechar no X sem decidir login
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        getContentPane().setBackground(
                new Color(18, 18, 19));
    }

    // cria todos os componentes da tela
    private void criarComponentes() {

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(
                new BoxLayout(painelPrincipal,
                        BoxLayout.Y_AXIS));

        painelPrincipal.setBackground(
                new Color(18, 18, 19));

        painelPrincipal.setBorder(
                new EmptyBorder(40, 50, 40, 50));

        // titulo
        JLabel lblTitulo =
                new JLabel("TERMO",
                        SwingConstants.CENTER);

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 42));

        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createVerticalStrut(8));

        // subtitulo
        JLabel lblSubtitulo =
                new JLabel("Jogo de Palavras",
                        SwingConstants.CENTER);

        lblSubtitulo.setFont(
                new Font("Arial", Font.PLAIN, 16));

        lblSubtitulo.setForeground(
                new Color(150, 150, 150));

        lblSubtitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        painelPrincipal.add(lblSubtitulo);
        painelPrincipal.add(Box.createVerticalStrut(40));

        // campo usuario
        JLabel lblUsuario =
                new JLabel("Usuário");

        lblUsuario.setFont(
                new Font("Arial", Font.BOLD, 13));

        lblUsuario.setForeground(
                new Color(200, 200, 200));

        lblUsuario.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        txtUsuario = new JTextField(20);

        txtUsuario.setFont(
                new Font("Arial", Font.PLAIN, 14));

        txtUsuario.setBackground(
                new Color(39, 39, 42));

        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);

        txtUsuario.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(58, 58, 60), 1),
                        BorderFactory.createEmptyBorder(
                                10, 12, 10, 12)
                )
        );

        txtUsuario.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42));

        txtUsuario.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        painelPrincipal.add(lblUsuario);
        painelPrincipal.add(Box.createVerticalStrut(8));
        painelPrincipal.add(txtUsuario);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // campo senha
        JLabel lblSenha =
                new JLabel("Senha");

        lblSenha.setFont(
                new Font("Arial", Font.BOLD, 13));

        lblSenha.setForeground(
                new Color(200, 200, 200));

        lblSenha.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        txtSenha = new JPasswordField(20);

        txtSenha.setFont(
                new Font("Arial", Font.PLAIN, 14));

        txtSenha.setBackground(
                new Color(39, 39, 42));

        txtSenha.setForeground(Color.WHITE);
        txtSenha.setCaretColor(Color.WHITE);

        txtSenha.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(58, 58, 60), 1),
                        BorderFactory.createEmptyBorder(
                                10, 12, 10, 12)
                )
        );

        txtSenha.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42));

        txtSenha.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        // permite apertar enter para logar
        txtSenha.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode()
                                == KeyEvent.VK_ENTER) {
                            fazerLogin();
                        }
                    }
                });

        painelPrincipal.add(lblSenha);
        painelPrincipal.add(Box.createVerticalStrut(8));
        painelPrincipal.add(txtSenha);
        painelPrincipal.add(Box.createVerticalStrut(12));

        // label de mensagem
        lblMensagem =
                new JLabel(" ",
                        SwingConstants.CENTER);

        lblMensagem.setFont(
                new Font("Arial", Font.PLAIN, 12));

        lblMensagem.setForeground(
                new Color(239, 68, 68));

        lblMensagem.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        lblMensagem.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 20));

        painelPrincipal.add(lblMensagem);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // botao login
        btnLogin =
                criarBotao("ENTRAR",
                        new Color(106, 170, 100));

        btnLogin.addActionListener(
                e -> fazerLogin());

        painelPrincipal.add(btnLogin);
        painelPrincipal.add(Box.createVerticalStrut(12));

        // botao cadastrar
        btnCadastrar =
                criarBotao("CADASTRAR",
                        new Color(59, 130, 246));

        btnCadastrar.addActionListener(
                e -> fazerCadastro());

        painelPrincipal.add(btnCadastrar);
        painelPrincipal.add(Box.createVerticalStrut(30));

        // informacao de usuarios cadastrados
        JLabel lblInfo =
                new JLabel(
                        String.format(
                                "Usuários cadastrados: %d",
                                gerenciadorUsuarios
                                        .getNumeroUsuarios()),
                        SwingConstants.CENTER);

        lblInfo.setFont(
                new Font("Arial", Font.PLAIN, 11));

        lblInfo.setForeground(
                new Color(120, 120, 120));

        lblInfo.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        painelPrincipal.add(lblInfo);

        add(painelPrincipal);
    }

    // cria botao estilizado
    private JButton criarBotao(String texto,
                                Color cor) {

        JButton btn = new JButton(texto) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 =
                        (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(cor);

                g2.fillRoundRect(
                        0, 0,
                        getWidth(),
                        getHeight(),
                        8, 8);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(
                new Font("Arial", Font.BOLD, 15));

        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);

        btn.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        btn.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        btn.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 45));

        return btn;
    }

    // realiza login
    private void fazerLogin() {

        String usuario =
                txtUsuario.getText().trim();

        String senha =
                new String(txtSenha.getPassword());

        if (usuario.isEmpty()) {
            mostrarMensagem(
                    "Digite o nome de usuário", true);
            return;
        }

        if (senha.isEmpty()) {
            mostrarMensagem(
                    "Digite a senha", true);
            return;
        }

        if (gerenciadorUsuarios
                .login(usuario, senha)) {

            loginBemSucedido = true;
            dispose();

        } else {

            mostrarMensagem(
                    "Usuário ou senha incorretos",
                    true);

            txtSenha.setText("");
            txtSenha.requestFocus();
        }
    }

    // realiza cadastro
    private void fazerCadastro() {

        String usuario =
                txtUsuario.getText().trim();

        String senha =
                new String(txtSenha.getPassword());

        if (usuario.isEmpty()) {
            mostrarMensagem(
                    "Digite o nome de usuário", true);
            return;
        }

        if (usuario.length() < 3) {
            mostrarMensagem(
                    "Usuário deve ter pelo menos 3 caracteres",
                    true);
            return;
        }

        if (senha.length()
                < GerenciadorUsuarios
                .getTamanhoMinimoSenha()) {

            mostrarMensagem(
                    String.format(
                            "Senha deve ter pelo menos %d caracteres",
                            GerenciadorUsuarios
                                    .getTamanhoMinimoSenha()),
                    true);
            return;
        }

        if (gerenciadorUsuarios
                .usuarioExiste(usuario)) {

            mostrarMensagem(
                    "Usuário já cadastrado! Faça login.",
                    true);
            return;
        }

        if (gerenciadorUsuarios
                .cadastrar(usuario, senha)) {

            mostrarMensagem(
                    "Cadastro realizado! Faça login.",
                    false);

            txtSenha.setText("");
            txtUsuario.requestFocus();

        } else {

            mostrarMensagem(
                    "Erro ao cadastrar usuário",
                    true);
        }
    }

    // mostra mensagem de erro ou sucesso
    private void mostrarMensagem(String mensagem,
                                  boolean erro) {

        lblMensagem.setText(mensagem);

        lblMensagem.setForeground(
                erro
                        ? new Color(239, 68, 68)
                        : new Color(106, 170, 100));
    }

    // retorna se login foi bem sucedido
    public boolean isLoginBemSucedido() {
        return loginBemSucedido;
    }
}
