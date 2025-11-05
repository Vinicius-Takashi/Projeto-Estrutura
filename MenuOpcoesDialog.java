import javax.swing.*;
import java.awt.*;

public class MenuOpcoesDialog extends JDialog {

    private GrafoSocial rede;
    private String usuarioAtivo;
    private String resultadoAcao = "[Nenhuma ação realizada]";

    public MenuOpcoesDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Tela 2: Opções do Usuário - " + usuarioAtivo, true);

        this.rede = rede;
        this.usuarioAtivo = usuarioAtivo;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(450, 250);
        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(owner);

        add(new JLabel("Usuário Logado: " + usuarioAtivo, SwingConstants.CENTER));

        JButton btnAmizade = new JButton("1. Adicionar Amigo (Tela 3)");
        JButton btnListar = new JButton("2. Listar Amigos (Tela 4)");
        JButton btnRecomendar = new JButton("3. Ver Recomendações (Tela 5)");

        btnAmizade.addActionListener(e -> chamarTelaAdicionar());
        btnListar.addActionListener(e -> chamarTelaListar());
        btnRecomendar.addActionListener(e -> chamarTelaRecomendacoes());

        add(btnAmizade);
        add(btnListar);
        add(btnRecomendar);

        JButton btnFechar = new JButton("Concluir / Fechar");
        btnFechar.addActionListener(e -> dispose());
        add(btnFechar);
    }

    // --- Chamadas para as Telas Finais (3, 4 e 5) ---

    private void chamarTelaAdicionar() {
        dispose(); // fecha o menu antes da próxima tela
        TelaAdicionarAmigoDialog dialog = new TelaAdicionarAmigoDialog((JFrame) getOwner(), rede, usuarioAtivo);
        dialog.setLocationRelativeTo(getOwner());
        dialog.setVisible(true);
        resultadoAcao = dialog.getResultadoAcao();
    }

    private void chamarTelaListar() {
        dispose();
        TelaListarAmigosDialog dialog = new TelaListarAmigosDialog((JFrame) getOwner(), rede, usuarioAtivo);
        dialog.setLocationRelativeTo(getOwner());
        dialog.setVisible(true);
        resultadoAcao = dialog.getResultadoAcao();
    }

    private void chamarTelaRecomendacoes() {
        dispose();
        TelaRecomendacoesDialog dialog = new TelaRecomendacoesDialog((JFrame) getOwner(), rede, usuarioAtivo);
        dialog.setLocationRelativeTo(getOwner());
        dialog.setVisible(true);
        resultadoAcao = dialog.getResultadoAcao();
    }

    public String getResultadoAcao() {
        return resultadoAcao;
    }
}
