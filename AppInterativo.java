import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AppInterativo extends JFrame {

    private GrafoSocial rede;
    private JTextArea areaTextoGrafo;

    public AppInterativo() {
        super("MAUÁ CONECTA");

        this.rede = new GrafoSocial();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout(10, 10));

        // Painel central: só o grafo
        JPanel painelVisualizacao = criarPainelVisualizacao();
        add(painelVisualizacao, BorderLayout.CENTER);

        atualizarExibicaoGrafo();

        setLocationRelativeTo(null);
        // não chamamos setVisible aqui — só depois do login/menu
    }

    private JPanel criarPainelVisualizacao() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        // Apenas exibição do grafo (lista de adjacências)
        areaTextoGrafo = new JTextArea("Grafo Social:\n[Vazio]");
        areaTextoGrafo.setEditable(false);
        areaTextoGrafo.setFont(new Font("Monospaced", Font.PLAIN, 12));

        painel.add(new JScrollPane(areaTextoGrafo), BorderLayout.CENTER);
        return painel;
    }

    public void iniciar() {
        // Popula antes do login
        UsuariosIniciais.popularComConexoes(rede);

        exibirTelaLogin();
    }

    private void exibirTelaLogin() {
        LoginDialog dialog = new LoginDialog(this, rede);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (dialog.isAbrirPainelPrincipal()) {
            atualizarExibicaoGrafo();
            setLocationRelativeTo(null);
            setVisible(true);
        } else {
            dispose();
            System.exit(0);
        }
    }

    private void atualizarExibicaoGrafo() {
        StringBuilder sb = new StringBuilder();
        sb.append("MAUÁ CONECTA:\n\n");
        if (rede.getListaAdjacencia().isEmpty()) {
            sb.append("  [Grafo Vazio.]");
        } else {
            for (Map.Entry<String, List<String>> entry : rede.getListaAdjacencia().entrySet()) {
                sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
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
