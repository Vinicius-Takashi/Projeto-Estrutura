import java.util.*;


public class AnaliseRedeUtil {

    public static Map<String, Integer> calcularGraus(Grafo g) {
        Map<String, Integer> graus = new HashMap<>();
        for (Vertice v : g.getVertices()) {
            graus.put(v.getNome(), g.getGrau(v));
        }
        return graus;
    }

    public static List<String> encontrarHubs(Grafo g) {
        List<String> hubs = new ArrayList<>();
        int maxGrau = -1;

       
        for (Vertice v : g.getVertices()) {
            int grauAtual = g.getGrau(v);
            if (grauAtual > maxGrau) {
                maxGrau = grauAtual;
            }
        }

        if (maxGrau > 0) { 
            for (Vertice v : g.getVertices()) {
                if (g.getGrau(v) == maxGrau) {
                    hubs.add(v.getNome());
                }
            }
        }
        Collections.sort(hubs);
        return hubs;
    }

    public static List<String> encontrarCaminhoMaisCurto(Grafo g, String nomeInicio, String nomeFim) {
        Vertice inicio = g.buscarVertice(nomeInicio);
        Vertice fim = g.buscarVertice(nomeFim);

        if (inicio == null || fim == null) {
            System.err.println("Erro BFS: Vértice inicial ou final não encontrado.");
            return null;
        }

        if (inicio.equals(fim)) {
            return Collections.singletonList(inicio.getNome());
        }

        Queue<Vertice> fila = new LinkedList<>();
        Map<Vertice, Vertice> predecessores = new HashMap<>();
        Set<Vertice> visitados = new HashSet<>();

        fila.add(inicio);
        visitados.add(inicio);
        predecessores.put(inicio, null); 

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();

            if (atual.equals(fim)) {
                break; 
            }

            List<Vertice> vizinhos = g.getVizinhos(atual);
            vizinhos.sort(Comparator.comparing(Vertice::getNome));

            for (Vertice vizinho : vizinhos) {
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    predecessores.put(vizinho, atual); 
                    fila.add(vizinho);
                }
            }
        }

        if (!predecessores.containsKey(fim)) {
            return null;
        }

        LinkedList<String> caminho = new LinkedList<>();
        Vertice passo = fim;
        while (passo != null) {
            caminho.addFirst(passo.getNome());
            passo = predecessores.get(passo);
        }

        return caminho;
    }

    // --- Outros métodos de análise poderiam ser adicionados aqui ---
    // Ex: calcularCoeficienteAgrupamento, detectarComunidades, etc.
}