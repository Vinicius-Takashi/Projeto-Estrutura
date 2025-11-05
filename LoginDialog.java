import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginDialog extends JDialog {
    
    private GrafoSocial rede;
    private String resultadoAcao = "[Nenhuma ação realizada]";

    public LoginDialog(JFrame owner, GrafoSocial rede) {
        super(owner, "Tela 1: Login / Seleção de Usuário", true);
        this.rede = rede;
        
        setSize(400, 250);
        setLayout(new GridLayout(4, 1, 10, 10));
        setLocationRelativeTo(owner);
        
        add(new JLabel("Bem-vindo ao Simulador de Rede Social!", SwingConstants.CENTER));
        
        JButton btnAdicionar = new JButton("Adicionar Novo Usuário");
        JButton btnSelecionar = new JButton("Selecionar Usuário Existente");
        
        btnAdicionar.addActionListener(e -> acaoAdicionarUsuario());
        btnSelecionar.addActionListener(e -> acaoSelecionarUsuario());
        
        add(btnAdicionar);
        add(btnSelecionar);
        add(new JLabel("Escolha uma opção para prosseguir.", SwingConstants.CENTER));
    }
    
    // --- Ações da Tela de Login ---

    private void acaoAdicionarUsuario() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do novo Usuário:");
        if (nome != null && !nome.trim().isEmpty()) {
            rede.adicionarUsuario(nome.trim());
            resultadoAcao = "[Ação]: Usuário '" + nome.trim() + "' adicionado ao grafo.";
            // Chama a próxima tela (Tela de Opções) com o novo usuário
            chamarTelaOpcoes(nome.trim());
        } else {
            resultadoAcao = "[Ação Cancelada]";
            dispose();
        }
    }

    private void acaoSelecionarUsuario() {
        if (rede.getListaAdjacencia().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O grafo está vazio. Adicione um usuário primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            resultadoAcao = "[Erro]: Tentativa de seleção em grafo vazio.";
            dispose();
            return;
        }

        String[] usuarios = rede.getListaAdjacencia().keySet().toArray(new String[0]);
        String usuarioAtivo = (String) JOptionPane.showInputDialog(this, 
            "Selecione o Usuário para Logar:", "Selecionar Usuário", 
            JOptionPane.QUESTION_MESSAGE, null, usuarios, usuarios[0]);
        
        if (usuarioAtivo != null) {
            // Chama a próxima tela (Tela de Opções)
            chamarTelaOpcoes(usuarioAtivo);
        } else {
            resultadoAcao = "[Ação Cancelada]";
            dispose();
        }
    }
    
    private void chamarTelaOpcoes(String usuario) {
        // Encerra a tela de Login
        dispose(); 
        
        // Chama a Tela de Opções (Tela 2)
        MenuOpcoesDialog dialog = new MenuOpcoesDialog((JFrame) getOwner(), rede, usuario);
        dialog.setVisible(true);
        resultadoAcao = dialog.getResultadoAcao();
    }
    
    public String getResultadoAcao() {
        return resultadoAcao;
    }
}