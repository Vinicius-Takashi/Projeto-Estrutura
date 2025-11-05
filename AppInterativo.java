import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AppInterativo extends JFrame {

    private GrafoSocial rede;
    private JTextArea areaTextoGrafo;
    private JTextArea areaTextoResultados;

    public AppInterativo() {
        super("Sistema de Recomendação - Grafo Social (ECM306)");

        this.rede = new GrafoSocial();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        // --- Painel de Visualização (esquerda: grafo | direita: resultados) ---
        JPanel painelVisualizacao = criarPainelVisualizacao();
        add(painelVisualizacao, BorderLayout.CENTER);

        atualizarExibicaoGrafo();

        setLocationRelativeTo(null);
        // IMPORTANTE: não tornamos visível aqui.
        // A janela só aparece DEPOIS do login/menu optar por abrir o painel.
    }

    private JPanel criarPainelVisualizacao() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Esquerda: Exibição do Grafo (Lista de Adjacências)
        areaTextoGrafo = new JTextArea("Grafo Social:\n[Vazio]");
        areaTextoGrafo.setEditable(false);
        areaTextoGrafo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        painel.add(new JScrollPane(areaTextoGrafo));

        // Direita: Exibição dos Resultados (Buscas/Recomendações)
        areaTextoResultados = new JTextArea("Resultados das Buscas:\n[Nenhum]");
        areaTextoResultados.setEditable(false);
        areaTextoResultados.setFont(new Font("Monospaced", Font.BOLD, 14));
        areaTextoResultados.setForeground(new Color(0, 100, 0));
        painel.add(new JScrollPane(areaTextoResultados));

        return painel;
    }

    /** Ponto de partida do app. */
    public void iniciar() {
        // Se quiser popular com usuários fixos, descomente:
        UsuariosIniciais.popular(rede);

        exibirTelaLogin(); // não abre a janela ainda; só abre se o menu mandar
    }

    // --- Fluxo de navegação: Login -> (Menu) -> talvez abrir painel ---
    private void exibirTelaLogin() {
        LoginDialog dialog = new LoginDialog(this, rede);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true); // bloqueia até o login+menu fechar

        if (dialog.isAbrirPainelPrincipal()) {
            atualizarExibicaoGrafo();
            String resultado = dialog.getResultadoAcao();
            if (resultado != null && !resultado.isEmpty()) {
                areaTextoResultados.setText(resultado);
            }
            setLocationRelativeTo(null);
            setVisible(true); // só agora mostramos a janela principal
        } else {
            // Sessão encerrada sem abrir painel
            dispose();
            System.exit(0);
        }
    }

    // Método para diálogos atualizarem o painel principal, se necessário
    public void setResultado(String resultado) {
        areaTextoResultados.setText(resultado);
        atualizarExibicaoGrafo();
    }

    private void atualizarExibicaoGrafo() {
        StringBuilder sb = new StringBuilder();
        sb.append("GRAFO SOCIAL ATUAL (LISTA DE ADJACÊNCIAS):\n\n");
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
