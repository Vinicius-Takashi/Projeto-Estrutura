import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class TelaRecomendacoesDialog extends JDialog {
    
    private String usuarioAtivo;
    private String resultadoAcao;

    public TelaRecomendacoesDialog(JFrame owner, GrafoSocial rede, String usuarioAtivo) {
        super(owner, "Tela 5: Recomendações para " + usuarioAtivo, true);
        this.usuarioAtivo = usuarioAtivo;
        
        setSize(400, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(owner);
        
        // --- Executa o Algoritmo BFS ---
        Set<String> recomendacoes = rede.recomendarAmigos(usuarioAtivo);
        
        StringBuilder sb = new StringBuilder();
        sb.append("--- RECOMENDAÇÕES BFS (Distância 2) ---\n");
        sb.append("Usuário Base: ").append(usuarioAtivo).append("\n\n");
        
        if (recomendacoes.isEmpty()) {
            sb.append("Nenhuma recomendação (Amigo de Amigo) encontrada.");
        } else {
            for (String recomendado : recomendacoes) {
                sb.append("** RECOMENDAR ** -> ").append(recomendado).append("\n");
            }
        }
        
        JTextArea areaLista = new JTextArea(sb.toString());
        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.BOLD, 14));
        areaLista.setForeground(new Color(150, 0, 150)); // Cor de destaque para o BFS
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