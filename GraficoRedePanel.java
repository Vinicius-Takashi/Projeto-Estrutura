import java.awt.*;
import javax.swing.*;
import java.util.Map;
import java.util.HashMap;

public class GraficoRedePanel extends JPanel {
    private final Grafo grafo;
    private final Map<Vertice, Point> positions;
    
    // Configurações visuais
    private static final int NODE_SIZE = 20;
    // Tamanho base para o painel de desenho. Deve ser ligeiramente menor que o JFrame (800x500).
    private static final int BASE_AREA_WIDTH = 750;
    private static final int BASE_AREA_HEIGHT = 400;

    public GraficoRedePanel(Grafo g) {
        this.grafo = g;
        this.positions = new HashMap<>();
        
        // 1. Cálculo da dimensão da grade para layout
        int numVertices = grafo.getNumeroVertices();
        int spacing = 120; // Espaçamento entre os nós (suficiente para o rótulo)
        
        // 2. Cálculo do tamanho do painel de desenho
        int maxPerLine = (int) Math.floor((double) BASE_AREA_WIDTH / spacing);
        if (maxPerLine == 0) maxPerLine = 1;

        int requiredWidth = Math.min(BASE_AREA_WIDTH, spacing + (maxPerLine - 1) * spacing + spacing / 2);
        int requiredHeight = (numVertices / maxPerLine + 1) * spacing;
        
        int preferredWidth = Math.max(BASE_AREA_WIDTH, requiredWidth);
        int preferredHeight = Math.max(BASE_AREA_HEIGHT, requiredHeight);
        
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
        // 3. Posicionamento dos nós em grade
        int count = 0;

        for (Vertice v : grafo.getVertices()) {
            // Posicionamento em grade
            int x = spacing / 2 + (count % maxPerLine) * spacing;
            int y = spacing / 2 + (count / maxPerLine) * spacing;
            positions.put(v, new Point(x, y));
            count++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- 1. DESENHAR ARESTAS (INTERAÇÕES) ---
        g2d.setColor(new Color(150, 150, 150)); 
        g2d.setStroke(new BasicStroke(1.5f));
        
        for (Aresta a : grafo.getArestas()) {
            Point p1 = positions.get(a.getV1());
            Point p2 = positions.get(a.getV2());

            if (p1 != null && p2 != null) {
                // Desenha a linha entre o centro dos nós
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // --- 2. DESENHAR VÉRTICES (PROTEÍNAS) E RÓTULOS ---
        for (Vertice v : grafo.getVertices()) {
            Point p = positions.get(v);
            if (p != null) {
                int x = p.x - NODE_SIZE / 2;
                int y = p.y - NODE_SIZE / 2;

                // Desenha o círculo do nó
                g2d.setColor(new Color(60, 140, 200)); // Azul
                g2d.fillOval(x, y, NODE_SIZE, NODE_SIZE);

                // Desenha a borda do nó
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawOval(x, y, NODE_SIZE, NODE_SIZE);
                
                // Desenha o nome da proteína
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 10));
                
                // Centraliza o texto abaixo do nó
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(v.getNome());
                g2d.drawString(v.getNome(), p.x - textWidth / 2, p.y + NODE_SIZE + 5);
            }
        }
    }
}