import javax.swing.*;
import java.awt.*;

public class MenuOpcoesDialog extends JDialog {

    private GrafoSocial rede;
    private String usuarioAtivo;
    private String resultadoAcao = "[Nenhuma ação realizada]";
    private boolean abrirPainelPrincipal = false; // abre a janela principal?
    private boolean trocarUsuario = false;        // voltar ao login?

    public MenuOpcoesDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "MAUÁ CONECTA ", true);

        this.rede = rede;
        this.usuarioAtivo = usuarioAtivo;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(520, 300);
        setLayout(new GridLayout(7, 1, 10, 10));
        setLocationRelativeTo(owner);

        add(new JLabel("Usuário logado: " + usuarioAtivo, SwingConstants.CENTER));

        JButton btnAmizade     = new JButton(" Adicionar amigo ");
        JButton btnListar      = new JButton(" Listar amigos ");
        JButton btnRecomendar  = new JButton(" Ver recomendações ");
        JButton btnAbrirPainel = new JButton(" Visualização do grafo ");
        JButton btnTrocar      = new JButton(" Trocar usuário "); // NOVO
        JButton btnFechar      = new JButton(" Sair ");

        btnAmizade.addActionListener(e -> chamarTelaAdicionar());
        btnListar.addActionListener(e -> chamarTelaListar());
        btnRecomendar.addActionListener(e -> chamarTelaRecomendacoes());

        btnAbrirPainel.addActionListener(e -> {
            abrirPainelPrincipal = true;
            dispose();
        });

        btnTrocar.addActionListener(e -> {
            trocarUsuario = true;
            resultadoAcao = "[Voltando ao login para escolher outro usuário]";
            dispose();
        });

        btnFechar.addActionListener(e -> {
            abrirPainelPrincipal = false;
            resultadoAcao = "[Sessão encerrada sem abrir painel]";
            dispose();
        });

        add(btnAmizade);
        add(btnListar);
        add(btnRecomendar);
        add(btnAbrirPainel);
        add(btnTrocar);   // NOVO
        add(btnFechar);
    }

    // Mantém o menu aberto: não damos dispose() ao abrir as telas 3/4/5
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

    public String getResultadoAcao() { return resultadoAcao; }
    public boolean isAbrirPainelPrincipal() { return abrirPainelPrincipal; }
    public boolean isTrocarUsuario() { return trocarUsuario; } // NOVO
}
