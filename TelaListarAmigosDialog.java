import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Collections;

public class TelaListarAmigosDialog extends JDialog {

    private final String usuarioAtivo;
    private String resultadoAcao;

    public TelaListarAmigosDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Tela 4: Amigos Diretos de " + usuarioAtivo, true);
        this.usuarioAtivo = usuarioAtivo;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        // --- ESC fecha o diálogo ---
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        // --- Monta a lista de amigos diretos ---
        List<String> amigosDiretos = rede.getListaAdjacencia().getOrDefault(usuarioAtivo, Collections.emptyList());

        StringBuilder sb = new StringBuilder();
        sb.append("--- AMIGOS DIRETOS (DISTÂNCIA 1) ---\n");
        sb.append("Usuário Base: ").append(usuarioAtivo).append("\n\n");

        if (amigosDiretos.isEmpty()) {
            sb.append("Não há amigos diretos cadastrados.");
        } else {
            for (String amigo : amigosDiretos) {
                sb.append("-> ").append(amigo).append("\n");
            }
        }

        JTextArea areaLista = new JTextArea(sb.toString());
        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaLista.setMargin(new Insets(5, 10, 5, 10));

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
