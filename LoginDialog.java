import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    private GrafoSocial rede;
    private String resultadoAcao = "[Nenhuma ação realizada]";
    private boolean abrirPainelPrincipal = false; // <- MENU decide se abre o painel
    private String ultimoUsuarioLogado = null;     // <- NOVO: quem está logado

    public LoginDialog(JFrame owner, GrafoSocial rede) {
        super(owner, "Seleção de Usuário", true);
        this.rede = rede;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLayout(new GridLayout(4, 1, 10, 10));
        setLocationRelativeTo(owner);

        add(new JLabel("Bem-vindo ao MAUÁ CONECTA", SwingConstants.CENTER));

        JButton btnAdicionar = new JButton("Adicionar novo usuário");
        JButton btnSelecionar = new JButton("Selecionar usuário existente");

        btnAdicionar.addActionListener(e -> acaoAdicionarUsuario());
        btnSelecionar.addActionListener(e -> acaoSelecionarUsuario());

        add(btnAdicionar);
        add(btnSelecionar);
        add(new JLabel("Escolha uma opção para prosseguir.", SwingConstants.CENTER));
    }

    // --- Ações da Tela de Login ---

    private void acaoAdicionarUsuario() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do novo Usuário:");
        if (nome != null && !nome.trim().isEmpty()) {
            String n = nome.trim();
            rede.adicionarUsuario(n);
            resultadoAcao = "[Ação]: Usuário '" + n + "' adicionado ao grafo.";
            ultimoUsuarioLogado = n;              // <<< NOVO: guarda logado
            chamarTelaOpcoes(n);
        } else {
            resultadoAcao = "[Ação Cancelada]";
            dispose();
        }
    }

    private void acaoSelecionarUsuario() {
        if (rede.getListaAdjacencia().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O grafo está vazio. Adicione um usuário primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            resultadoAcao = "[Erro]: Tentativa de seleção em grafo vazio.";
            dispose();
            return;
        }

        String[] usuarios = rede.getListaAdjacencia().keySet().toArray(new String[0]);
        String usuarioAtivo = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o usuário para logar:",
                "Selecionar usuário",
                JOptionPane.QUESTION_MESSAGE,
                null,
                usuarios,
                usuarios[0]
        );

        if (usuarioAtivo != null) {
            ultimoUsuarioLogado = usuarioAtivo;   // <<< NOVO: guarda logado
            chamarTelaOpcoes(usuarioAtivo);
        } else {
            resultadoAcao = "[Ação Cancelada]";
            dispose();
        }
    }

    /** Abre o Menu (fica no loop lá dentro) e só então decide se abre o painel principal. */
    private void chamarTelaOpcoes(String usuario) {
        // Abre o menu e decide o próximo passo
        MenuOpcoesDialog dialog = new MenuOpcoesDialog((JFrame) getOwner(), rede, usuario);
        dialog.setLocationRelativeTo(getOwner());
        dialog.setVisible(true);

        resultadoAcao = dialog.getResultadoAcao();

        if (dialog.isTrocarUsuario()) {
            // Volta para a tela de login sem fechar o LoginDialog:
            // o usuário poderá clicar em "Selecionar Usuário" ou "Adicionar".
            // Apenas retornamos deste método.
            return;
        }

        // Caso contrário, respeitamos a decisão de abrir (ou não) o painel principal
        abrirPainelPrincipal = dialog.isAbrirPainelPrincipal();

        // Encerramos o LoginDialog; o AppInterativo decide abrir a janela principal ou sair
        dispose();
    }

    public String getResultadoAcao() {
        return resultadoAcao;
    }

    public boolean isAbrirPainelPrincipal() {
        return abrirPainelPrincipal;
    }

    // <<< NOVO: usado pelo AppInterativo para voltar ao menu com o mesmo usuário
    public String getUltimoUsuarioLogado() {
        return ultimoUsuarioLogado;
    }
}
