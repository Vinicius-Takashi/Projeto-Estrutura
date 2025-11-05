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

        // === REMOVIDO o painel com botão "INICIAR/LOGIN" ===
        // Agora não existe tela inicial. Vamos direto para o Login.

        // --- Painel de Visualização ---
        JPanel painelVisualizacao = criarPainelVisualizacao();
        add(painelVisualizacao, BorderLayout.CENTER);

        atualizarExibicaoGrafo();

        setLocationRelativeTo(null);
        // IMPORTANTE: não chamamos setVisible(true) aqui.
        // A janela só aparece DEPOIS do login (ver método iniciar()).
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

    /**
     * Chame este método para iniciar o app:
     * 1) Abre o Login (modal)
     * 2) Atualiza textos
     * 3) Mostra a janela principal
     */
    public void iniciar() {
        UsuariosIniciais.popular(rede);
        exibirTelaLogin();          // abre o Login primeiro (sem tela inicial)
        setVisible(true);           // só depois mostra a janela principal
    }

    // --- Lógica de Navegação Principal ---
    private void exibirTelaLogin() {
        // LoginDialog deve ser um JDialog modal: new LoginDialog(Frame owner, GrafoSocial rede)
        LoginDialog dialog = new LoginDialog(this, rede);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true); // bloqueia até fechar (se modal)

        // Após o diálogo fechar, o resultado é capturado e as telas são atualizadas
        atualizarExibicaoGrafo();
        String resultado = dialog.getResultadoAcao();
        if (resultado != null && !resultado.isEmpty()) {
            areaTextoResultados.setText(resultado);
        }
    }

    // Método para ser chamado publicamente pelos diálogos para atualizar o resultado
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
            app.iniciar(); // -> abre Login primeiro e só depois exibe a janela
        });
    }
}
