package view;

import controller.ControladorJogo;
import model.Estatisticas;
import util.ConstantesVisuais;
import view.DialogoPerfil;
import view.DialogoLogin;

import javax.swing.*;
import java.awt.*;

/*
Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094
 */

public class MenuPrincipal extends JMenuBar {

    private ControladorJogo controlador;
    private JFrame janelaPrincipal;

    // Callbacks para ações do menu
    private MenuListener listener;

    /**
     * Interface para notificar eventos do menu
     */
    public interface MenuListener {
        void onNovoJogo();
        void onReiniciar();
        void onSair();
        void onMostrarEstatisticas();
        void onMostrarAjuda();
        void onMostrarSobre();
    }

    /**
     * Construtor do menu
     */
    public MenuPrincipal(ControladorJogo controlador, JFrame janelaPrincipal) {
        this.controlador = controlador;
        this.janelaPrincipal = janelaPrincipal;

        criarMenus();
    }

    /**
     * Cria todos os menus da barra
     */
    private void criarMenus() {

        // ==========================
        // Menu Jogo
        // ==========================

        JMenu menuJogo = new JMenu("Jogo");
        menuJogo.setMnemonic('J');

        JMenuItem itemNovoJogo = criarMenuItem("Novo Jogo", "Ctrl+N",
                e -> {
                    if (listener != null) listener.onNovoJogo();
                });

        JMenuItem itemReiniciar = criarMenuItem("Reiniciar Partida", "Ctrl+R",
                e -> {
                    if (listener != null) listener.onReiniciar();
                });

        JMenuItem itemSair = criarMenuItem("Sair", "Ctrl+Q",
                e -> {
                    if (listener != null) listener.onSair();
                });

        menuJogo.add(itemNovoJogo);
        menuJogo.add(itemReiniciar);
        menuJogo.addSeparator();
        menuJogo.add(itemSair);

        // ==========================
        // Menu Estatísticas
        // ==========================

        JMenu menuEstatisticas = new JMenu("Estatísticas");
        menuEstatisticas.setMnemonic('E');

        JMenuItem itemVerEstatisticas = criarMenuItem("Ver Estatísticas", "Ctrl+E",
                e -> {
                    if (listener != null) listener.onMostrarEstatisticas();
                });

        JMenuItem itemZerarEstatisticas = criarMenuItem("Zerar Estatísticas", null,
                e -> zerarEstatisticas());

        menuEstatisticas.add(itemVerEstatisticas);
        menuEstatisticas.addSeparator();
        menuEstatisticas.add(itemZerarEstatisticas);

        // ==========================
        // Menu Usuário
        // ==========================

        JMenu menuUsuario = new JMenu("Usuário");
        menuUsuario.setMnemonic('U');

        JMenuItem itemPerfil = criarMenuItem("Meu Perfil", "Ctrl+U",
                e -> mostrarPerfil());

        JMenuItem itemTrocarConta = criarMenuItem("Sair da Conta", null,
                e -> trocarConta());

        menuUsuario.add(itemPerfil);
        menuUsuario.addSeparator();
        menuUsuario.add(itemTrocarConta);

        // ==========================
        // Menu Ajuda
        // ==========================

        JMenu menuAjuda = new JMenu("Ajuda");
        menuAjuda.setMnemonic('A');

        JMenuItem itemComoJogar = criarMenuItem("Como Jogar", "F1",
                e -> {
                    if (listener != null) listener.onMostrarAjuda();
                });

        JMenuItem itemSobre = criarMenuItem("Sobre", null,
                e -> {
                    if (listener != null) listener.onMostrarSobre();
                });

        menuAjuda.add(itemComoJogar);
        menuAjuda.addSeparator();
        menuAjuda.add(itemSobre);

        // ==========================
        // Adiciona menus à barra
        // ==========================

        add(menuJogo);
        add(menuEstatisticas);
        add(menuUsuario);
        add(menuAjuda);
    }

    /**
     * Cria item de menu com atalho
     */
    private JMenuItem criarMenuItem(String texto, String atalho,
                                     java.awt.event.ActionListener action) {

        JMenuItem item = new JMenuItem(texto);

        if (atalho != null) {

            String[] partes = atalho.split("\\+");
            int modificador = 0;
            int tecla = 0;

            for (String parte : partes) {

                switch (parte) {
                    case "Ctrl":
                        modificador = java.awt.event.InputEvent.CTRL_DOWN_MASK;
                        break;
                    case "Alt":
                        modificador = java.awt.event.InputEvent.ALT_DOWN_MASK;
                        break;
                    case "Shift":
                        modificador = java.awt.event.InputEvent.SHIFT_DOWN_MASK;
                        break;
                    case "F1":
                        tecla = java.awt.event.KeyEvent.VK_F1;
                        break;
                    default:
                        if (parte.length() == 1) {
                            tecla = java.awt.event.KeyEvent
                                    .getExtendedKeyCodeForChar(parte.charAt(0));
                        }
                }
            }

            if (tecla != 0) {
                item.setAccelerator(KeyStroke.getKeyStroke(tecla, modificador));
            }
        }

        item.addActionListener(action);
        return item;
    }

    /**
     * Mostra diálogo de perfil
     */
    private void mostrarPerfil() {

        DialogoPerfil dialogo = new DialogoPerfil(
                janelaPrincipal,
                controlador.getGerenciadorUsuarios()
        );

        dialogo.setVisible(true);

        if (dialogo.deveRelogar()) {
            trocarConta();
        }
    }

    /**
     * Troca de conta
     */
    private void trocarConta() {

        int opcao = JOptionPane.showConfirmDialog(
                janelaPrincipal,
                "Tem certeza que deseja trocar de conta?\n" +
                        "Seu progresso será salvo automaticamente.",
                "Trocar de Conta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {

            controlador.getGerenciadorUsuarios().logout();

            DialogoLogin dialogoLogin = new DialogoLogin(
                    janelaPrincipal,
                    controlador.getGerenciadorUsuarios()
            );

            dialogoLogin.setVisible(true);

            if (dialogoLogin.isLoginBemSucedido()) {

                //Estatisticas stats = controlador.getEstatisticas();

                controlador.iniciarNovaPartida();

                JOptionPane.showMessageDialog(
                        janelaPrincipal,
                        "Bem-vindo, " +
                                controlador.getJogadorAtual().getNome() + "!",
                        "Login",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Zera estatísticas com confirmação
     */
    private void zerarEstatisticas() {

        int opcao = JOptionPane.showConfirmDialog(
                janelaPrincipal,
                "Tem certeza que deseja zerar todas as estatísticas?\n" +
                        "Esta ação não pode ser desfeita!",
                "Zerar Estatísticas",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opcao == JOptionPane.YES_OPTION) {

            controlador.zerarEstatisticas();

            JOptionPane.showMessageDialog(
                    janelaPrincipal,
                    "Estatísticas zeradas com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Define listener
     */
    public void setListener(MenuListener listener) {
        this.listener = listener;
    }
}
