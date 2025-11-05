import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TelaAdicionarAmigoDialog extends JDialog {
    
    private GrafoSocial rede;
    private String usuarioAtivo;
    private String resultadoAcao = "[Ação Cancelada]";

    public TelaAdicionarAmigoDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Tela 3: Adicionar Amigo a " + usuarioAtivo, true);
        this.rede = rede;
        this.usuarioAtivo = usuarioAtivo;
        
        setSize(400, 200);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLocationRelativeTo(owner);
        
        // 1. Encontra usuários que NÃO SÃO amigos do usuário ativo
        List<String> todosUsuarios = rede.getListaAdjacencia().keySet().stream().collect(Collectors.toList());
        List<String> amigosAtuais = rede.getListaAdjacencia().getOrDefault(usuarioAtivo, List.of());
        
        // Remove o próprio usuário e os amigos atuais
        todosUsuarios.remove(usuarioAtivo);
        todosUsuarios.removeAll(amigosAtuais);

        if (todosUsuarios.isEmpty()) {
            add(new JLabel("Não há outros usuários disponíveis para adicionar!"));
            JButton btnOK = new JButton("OK");
            btnOK.addActionListener(e -> dispose());
            add(btnOK);
            return;
        }
        
        // 2. ComboBox com os candidatos a amigo
        String[] candidatos = todosUsuarios.toArray(new String[0]);
        JComboBox<String> comboCandidatos = new JComboBox<>(candidatos);
        
        add(new JLabel("Candidatos a Amigo:"));
        add(comboCandidatos);
        
        // 3. Botão de Confirmação
        JButton btnAdicionar = new JButton("CONFIRMAR AMIZADE");
        btnAdicionar.addActionListener(e -> acaoAdicionar(comboCandidatos.getSelectedItem().toString()));
        add(btnAdicionar);
    }
    
    private void acaoAdicionar(String novoAmigo) {
        rede.adicionarAmizade(usuarioAtivo, novoAmigo);
        resultadoAcao = "[Ação]: Amizade criada entre " + usuarioAtivo + " e " + novoAmigo + ".";
        dispose(); // Fecha e retorna o resultado
    }

    public String getResultadoAcao() {
        return resultadoAcao;
    }
}