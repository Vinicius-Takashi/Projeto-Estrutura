import javax.swing.*;
import java.awt.*;

public class MenuOpcoesDialog extends JDialog {

    private GrafoSocial rede;
    private String usuarioAtivo;
    private String resultadoAcao = "[Nenhuma ação realizada]";
    private boolean abrirPainelPrincipal = false; // >>> NOVO: flag para o App decidir abrir o painel

    public MenuOpcoesDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Tela 2: Opções do Usuário - " + usuarioAtivo, true);

        this.rede = rede;
        this.usuarioAtivo = usuarioAtivo;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(480, 280);
        setLayout(new GridLayout(6, 1, 10, 10)); // +1 linha por causa do novo botão
        setLocationRelativeTo(owner);

        add(new JLabel("Usuário Logado: " + usuarioAtivo, SwingConstants.CENTER));

        JButton btnAmizade = new JButton("1. Adicionar Amigo (Tela 3)");
        JButton btnListar = new JButton("2. Listar Amigos (Tela 4)");
        JButton btnRecomendar = new JButton("3. Ver Recomendações (Tela 5)");
        JButton btnAbrirPainel = new JButton("Abrir Painel do Grafo");      // <<< NOVO
        JButton btnFechar = new JButton("Sair (sem abrir painel)");         // renomeado p/ clareza

        btnAmizade.addActionListener(e -> chamarTelaAdicionar());
        btnListar.addActionListener(e -> chamarTelaListar());
        btnRecomendar.addActionListener(e -> chamarTelaRecomendacoes());

        // Quando quiser ver a tela principal:
        btnAbrirPainel.addActionListener(e -> {
            abrirPainelPrincipal = true;
            dispose(); // fecha o menu; LoginDialog captura e abre o painel principal
        });

        // Sair sem abrir o painel:
        btnFechar.addActionListener(e -> {
            abrirPainelPrincipal = false;
            resultadoAcao = "[Sessão encerrada sem abrir painel]";
            dispose();
        });

        add(btnAmizade);
        add(btnListar);
        add(btnRecomendar);
        add(btnAbrirPainel); // <<< NOVO
        add(btnFechar);
    }

    // --- Chamadas para as Telas Finais (3, 4 e 5) ---
    // Importante: não damos dispose() aqui; o menu permanece aberto.

    private void chamarTelaAdicionar() {
        TelaAdicionarAmigoDialog dialog = new TelaAdicionarAmigoDialog((JFrame) getOwner(), rede, usuarioAtivo);
        dialog.setLocationRelativeTo(getOwner());
        dialog.setVisible(true);
        resultadoAcao = dialog.getResultadoAcao();
    }

    private void chamarTelaListar() {
        TelaListarAmigosDialog dialog = new TelaListarAmigosDialog((JFrame) getOwner(), rede, usuarioAtivo);
        dialog.setLocationRelativeTo(getOwner());
        dialog.setVisible(true);
        resultadoAcao = dialog.getResultadoAcao();
    }

    private void chamarTelaRecomendacoes() {
        TelaRecomendacoesDialog dialog = new TelaRecomendacoesDialog((JFrame) getOwner(), rede, usuarioAtivo);
        dialog.setLocationRelativeTo(getOwner());
        dialog.setVisible(true);
        resultadoAcao = dialog.getResultadoAcao();
    }

    public String getResultadoAcao() {
        return resultadoAcao;
    }

    // >>> NOVO: usado pelo LoginDialog para decidir se abre a janela principal
    public boolean isAbrirPainelPrincipal() {
        return abrirPainelPrincipal;
    }
}
