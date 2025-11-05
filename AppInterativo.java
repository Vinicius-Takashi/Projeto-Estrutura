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

        // --- Painel de Controle (Apenas um botão para chamar a tela de Login) ---
        JPanel painelControle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnLogin = new JButton("INICIAR/LOGIN >> (Tela 1)");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.addActionListener(e -> exibirTelaLogin());
        painelControle.add(btnLogin);
        add(painelControle, BorderLayout.NORTH);

        // --- Painel de Visualização ---
        JPanel painelVisualizacao = criarPainelVisualizacao();
        add(painelVisualizacao, BorderLayout.CENTER);

        atualizarExibicaoGrafo();

        setLocationRelativeTo(null);
        setVisible(true);
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
    
    // --- Lógica de Navegação Principal ---
    private void exibirTelaLogin() {
        LoginDialog dialog = new LoginDialog(this, rede);
        dialog.setVisible(true); 
        
        // Após o diálogo fechar, o resultado é capturado e as telas são atualizadas
        atualizarExibicaoGrafo();
        areaTextoResultados.setText(dialog.getResultadoAcao());
    }
    
    // Método para ser chamado publicamente pelos diálogos para atualizar o resultado
    public void setResultado(String resultado) {
        areaTextoResultados.setText(resultado);
        atualizarExibicaoGrafo();
    }

    private void atualizarExibicaoGrafo() {
        // ... (método de atualização do grafo mantido)
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
        SwingUtilities.invokeLater(() -> new AppInterativo());
    }
}