import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;

public class TelaRecomendacoesDialog extends JDialog {

    private final String usuarioAtivo;
    private String resultadoAcao;

    public TelaRecomendacoesDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Tela 5: Recomendações para " + usuarioAtivo, true);
        this.usuarioAtivo = usuarioAtivo;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        // ESC fecha o diálogo (qualidade de vida)
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        // --- Executa o Algoritmo de Recomendação (BFS distância 2) ---
        Set<String> recomendacoes = rede.recomendarAmigos(usuarioAtivo);

        StringBuilder sb = new StringBuilder();
        sb.append("\t--- RECOMENDAÇÕES ---\n");
        sb.append("Usuário: ").append(usuarioAtivo).append("\n\n");

        if (recomendacoes.isEmpty()) {
            sb.append("Nenhuma recomendação encontrada.");
        } else {
            for (String recomendado : recomendacoes) {
                sb.append("-> ").append(recomendado).append("\n");
            }
        }

        JTextArea areaLista = new JTextArea(sb.toString());
        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaLista.setForeground(new Color(150, 0, 150)); // destaque para recomendações
        areaLista.setMargin(new Insets(6, 10, 6, 10));

        add(new JScrollPane(areaLista), BorderLayout.CENTER);

        JButton btnOK = new JButton("Fechar");
        btnOK.addActionListener(e -> dispose());
        add(btnOK, BorderLayout.SOUTH);

        resultadoAcao = sb.toString();

        pack();
        setLocationRelativeTo(owner);
    }

    public String getResultadoAcao() {
        return resultadoAcao;
    }
}
