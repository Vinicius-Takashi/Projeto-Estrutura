import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializa o Grafo VAZIO
        Grafo redePPI = new Grafo(); 
        // 2. Define o Grafo e lança a Interface Gráfica
        SwingUtilities.invokeLater(() -> {
            // A GUI agora é responsável por adicionar vértices e arestas
            // e chamar a análise a partir de um grafo (inicialmente) vazio.
            new ResultadosGUI(redePPI); 
        });
    }
}
