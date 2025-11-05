import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections; // Necessário para Collections.emptyList()

public class AppRecomendacao extends JFrame {
    
    private GrafoSocial rede;
    private JTextArea areaTexto;
    private final String USUARIO_BUSCA = "Alice";

    public AppRecomendacao() {
        super("Sistema de Recomendação - Tema 7 (ECM306)");
        
        // Configuração da Janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Inicialização
        this.rede = new GrafoSocial();
        configurarDadosIniciais();
        
        // Área de Texto
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);
        
        executarAnalise(USUARIO_BUSCA);

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void configurarDadosIniciais() {
        // Vértices
        rede.adicionarUsuario("Alice");
        rede.adicionarUsuario("Bob");
        rede.adicionarUsuario("Carol");
        rede.adicionarUsuario("David");
        rede.adicionarUsuario("Eva");
        rede.adicionarUsuario("Frank");
        rede.adicionarUsuario("Grace");

        // Arestas: Amizades
        
        // Distância 1 de Alice
        rede.adicionarAmizade("Alice", "Bob");
        rede.adicionarAmizade("Alice", "Carol");

        // Distância 2 de Alice (RECOMENDAÇÕES)
        rede.adicionarAmizade("Bob", "David");   
        rede.adicionarAmizade("Carol", "Eva");   
        
        // Distância 3 de Alice
        rede.adicionarAmizade("David", "Frank"); 
        
        // Conexão interna
        rede.adicionarAmizade("Grace", "Frank"); // Grace é D=3 de Alice
    }
    
    private void executarAnalise(String usuarioBusca) {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("      SISTEMA DE RECOMENDAÇÃO EM REDE SOCIAL      \n");
        sb.append("    Problema: Amigos de Amigos (Distância 2)    \n");
        sb.append("==================================================\n\n");
        
        // Exibe a estrutura de dados (Grafo)
        sb.append("ESTRUTURA DA REDE (LISTA DE ADJACÊNCIAS):\n");
        for (Map.Entry<String, List<String>> entry : rede.getListaAdjacencia().entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        sb.append("\n--------------------------------------------------\n");
        
        // Executa o Algoritmo BFS
        sb.append("ANÁLISE BFS: Usuário de Origem [").append(usuarioBusca).append("]\n\n");
        
        Set<String> recomendacoes = rede.recomendarAmigos(usuarioBusca);
        List<String> amigosDiretos = rede.getListaAdjacencia().getOrDefault(usuarioBusca, Collections.emptyList());
        
        sb.append("1. Amigos Diretos (Distância 1):\n");
        sb.append("  ").append(amigosDiretos).append("\n\n");
        
        sb.append("2. Usuários Recomendados (Distância 2):\n");
        if (recomendacoes.isEmpty()) {
            sb.append("  Nenhuma recomendação de amigos de amigos encontrada.\n");
        } else {
            for (String recomendado : recomendacoes) {
                sb.append("  ** RECOMENDAR ** -> ").append(recomendado).append("\n");
            }
        }
        sb.append("\n==================================================\n");

        areaTexto.setText(sb.toString());
    }

    public static void main(String[] args) {
        // Inicializa a interface gráfica no Thread de Eventos do Swing (Prática padrão)
        SwingUtilities.invokeLater(() -> new AppRecomendacao());
    }
}