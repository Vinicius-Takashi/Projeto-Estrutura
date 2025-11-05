import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Collections;

public class TelaListarAmigosDialog extends JDialog {
    
    private String usuarioAtivo;
    private String resultadoAcao;

    public TelaListarAmigosDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Tela 4: Amigos Diretos de " + usuarioAtivo, true);
        this.usuarioAtivo = usuarioAtivo;
        
        setSize(300, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(owner);
        
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
        add(new JScrollPane(areaLista), BorderLayout.CENTER);
        
        JButton btnOK = new JButton("Voltar");
        btnOK.addActionListener(e -> dispose());
        add(btnOK, BorderLayout.SOUTH);
        
        resultadoAcao = sb.toString();
    }

    public String getResultadoAcao() {
        return resultadoAcao;
    }
}