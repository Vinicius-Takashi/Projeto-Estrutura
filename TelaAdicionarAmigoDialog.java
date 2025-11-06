import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class TelaAdicionarAmigoDialog extends JDialog {

    private final GrafoSocial rede;
    private final String usuarioAtivo;
    private String resultadoAcao = "[Ação Cancelada]";

    public TelaAdicionarAmigoDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Adicionar amigo", true);
        this.rede = rede;
        this.usuarioAtivo = usuarioAtivo;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setResizable(false);

        // --- ESC para fechar (qualidade de vida) ---
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        // 1) Constrói lista de candidatos (não amigos + não o próprio usuário)
        List<String> todosUsuarios = rede.getListaAdjacencia()
                .keySet()
                .stream()
                .collect(Collectors.toList());

        List<String> amigosAtuais = rede.getListaAdjacencia()
                .getOrDefault(usuarioAtivo, List.of());

        todosUsuarios.remove(usuarioAtivo);
        todosUsuarios.removeAll(amigosAtuais);

        if (todosUsuarios.isEmpty()) {
            add(new JLabel("Não há outros usuários disponíveis para adicionar!"));
            JButton btnOK = new JButton("OK");
            btnOK.addActionListener(e -> dispose());
            add(btnOK);

            pack();
            setLocationRelativeTo(owner);
            return;
        }

        // 2) ComboBox com candidatos
        String[] candidatos = todosUsuarios.toArray(new String[0]);
        JComboBox<String> comboCandidatos = new JComboBox<>(candidatos);

        add(new JLabel("Candidatos a Amigo:"));
        comboCandidatos.setPreferredSize(new Dimension(220, 28));
        add(comboCandidatos);

        // 3) Botão de confirmação
        JButton btnAdicionar = new JButton("CONFIRMAR AMIZADE");
        btnAdicionar.addActionListener(e -> {
            Object sel = comboCandidatos.getSelectedItem();
            if (sel == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um usuário válido.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            acaoAdicionar(sel.toString());
        });
        add(btnAdicionar);

        pack();
        setLocationRelativeTo(owner);
    }

    private void acaoAdicionar(String novoAmigo) {
        rede.adicionarAmizade(usuarioAtivo, novoAmigo);
        resultadoAcao = "[Ação]: Amizade criada entre " + usuarioAtivo + " e " + novoAmigo + ".";
        dispose(); // Fecha e devolve o resultado
    }

    public String getResultadoAcao() {
        return resultadoAcao;
    }
}
