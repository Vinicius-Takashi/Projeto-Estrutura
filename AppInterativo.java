import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AppInterativo extends JFrame {

    private GrafoSocial rede;
    private JTextArea areaTextoGrafo;
    private String ultimoUsuarioLogado; // <<< guarda o último usuário

    public AppInterativo() {
        super("MAUÁ CONECTA");

        this.rede = new GrafoSocial();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setMinimumSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));

        // --- Área central (somente grafo)
        JPanel painelVisualizacao = criarPainelVisualizacao();
        add(painelVisualizacao, BorderLayout.CENTER);

        // --- Botão inferior: voltar ao menu
        JButton btnVoltar = new JButton("Voltar ao Menu de Opções");
        btnVoltar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnVoltar.addActionListener(e -> voltarAoMenu());
        add(btnVoltar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JPanel criarPainelVisualizacao() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        areaTextoGrafo = new JTextArea("MAUÁ CONECTA:");
        areaTextoGrafo.setEditable(false);
        areaTextoGrafo.setLineWrap(true);
        areaTextoGrafo.setWrapStyleWord(false);
        areaTextoGrafo.setFont(new Font("Monospaced", Font.PLAIN, 12));

        painel.add(new JScrollPane(areaTextoGrafo), BorderLayout.CENTER);
        return painel;
    }

    public void iniciar() {
        // Popula a rede antes do login
        UsuariosIniciais.popularComConexoes(rede);
        exibirTelaLogin();
    }

    private void exibirTelaLogin() {
        LoginDialog dialog = new LoginDialog(this, rede);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (dialog.isAbrirPainelPrincipal()) {
            // guarda o último usuário logado para o botão "Voltar"
            ultimoUsuarioLogado = dialog.getUltimoUsuarioLogado();

            atualizarExibicaoGrafo();
            setVisible(true);
        } else {
            dispose();
            System.exit(0);
        }
    }

    private void voltarAoMenu() {
        if (ultimoUsuarioLogado != null) {
            setVisible(false); // esconde a janela do grafo
            MenuOpcoesDialog menu = new MenuOpcoesDialog(this, rede, ultimoUsuarioLogado);
            menu.setLocationRelativeTo(this);
            menu.setVisible(true);

            // Se no menu o usuário decidir abrir o painel novamente:
            if (menu.isAbrirPainelPrincipal()) {
                atualizarExibicaoGrafo();
                setVisible(true);
            } else if (menu.isTrocarUsuario()) {
                // volta ao login
                exibirTelaLogin();
            } else {
                dispose();
            }
        } else {
            // fallback caso não haja usuário salvo
            JOptionPane.showMessageDialog(this,
                    "Nenhum usuário ativo encontrado. Voltando ao login.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            setVisible(false);
            exibirTelaLogin();
        }
    }

    private void atualizarExibicaoGrafo() {
        StringBuilder sb = new StringBuilder();
        sb.append("MAUÁ CONECTA:\n\n");
        if (rede.getListaAdjacencia().isEmpty()) {
            sb.append("[Grafo vazio]");
        } else {
            for (Map.Entry<String, List<String>> entry : rede.getListaAdjacencia().entrySet()) {
                sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
            }
        }
        areaTextoGrafo.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppInterativo app = new AppInterativo();
            app.iniciar();
        });
    }
}
