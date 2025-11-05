import java.util.*;

public class Grafo {

    private final Map<Vertice, List<Aresta>> adjacencias;
    private final Set<Vertice> vertices;
    private final Set<Aresta> arestas; 

    public Grafo() {
        this.adjacencias = new HashMap<>();
        this.vertices = new HashSet<>();
        this.arestas = new HashSet<>();
    }

    public Vertice adicionarVertice(String nome) {
        Vertice v = new Vertice(nome);
        if (vertices.add(v)) { 
            adjacencias.put(v, new ArrayList<>()); 
        }
        return buscarVertice(nome); 
    }

 
    public Aresta adicionarAresta(String nomeV1, String nomeV2) {
        Vertice v1 = buscarVertice(nomeV1);
        Vertice v2 = buscarVertice(nomeV2);

        if (v1 == null) {
            System.err.println("Vértice '" + nomeV1 + "' não encontrado para criar aresta.");
            return null;
        }
        if (v2 == null) {
            System.err.println("Vértice '" + nomeV2 + "' não encontrado para criar aresta.");
            return null;
        }

        Aresta novaAresta = new Aresta(v1, v2);
        if (arestas.add(novaAresta)) {
            adjacencias.get(v1).add(novaAresta);
            if (!v1.equals(v2)) { 
                adjacencias.get(v2).add(novaAresta);
            }
            return novaAresta;
        } else {
            for (Aresta a : arestas) {
                if (a.equals(novaAresta))
                    return a;
            }
            return null;
        }
    }

    public Vertice buscarVertice(String nome) {
        for (Vertice v : vertices) {
            if (v.getNome().equals(nome)) {
                return v;
            }
        }
        return null;
    }

    public int getGrau(String nomeVertice) {
        Vertice v = buscarVertice(nomeVertice);
        if (v == null)
            return 0;

        int grau = adjacencias.getOrDefault(v, Collections.emptyList()).size();
        for (Aresta a : adjacencias.getOrDefault(v, Collections.emptyList())) {
            if (a.getV1().equals(a.getV2()) && a.getV1().equals(v)) {
                grau++;
            }
        }
        return grau;
    }

    public int getGrau(Vertice v) {
        return getGrau(v.getNome());
    }

    public List<Aresta> getArestasIncidentes(String nomeVertice) {
        Vertice v = buscarVertice(nomeVertice);
        if (v == null)
            return Collections.emptyList();
        return Collections.unmodifiableList(adjacencias.getOrDefault(v, Collections.emptyList()));
    }

    public List<Aresta> getArestasIncidentes(Vertice v) {
        return getArestasIncidentes(v.getNome());
    }

    public List<Vertice> getVizinhos(String nomeVertice) {
        Vertice v = buscarVertice(nomeVertice);
        if (v == null)
            return Collections.emptyList();

        List<Vertice> vizinhos = new ArrayList<>();
        for (Aresta a : adjacencias.getOrDefault(v, Collections.emptyList())) {
            Vertice vizinho = a.getVizinho(v);
            if (vizinho != null) {
                vizinhos.add(vizinho);
            }
        }
        return vizinhos;
    }

    public List<Vertice> getVizinhos(Vertice v) {
        return getVizinhos(v.getNome());
    }

    public Set<Vertice> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    public Set<Aresta> getArestas() {
        return Collections.unmodifiableSet(arestas);
    }

    public int getNumeroVertices() {
        return vertices.size();
    }

    public int getNumeroArestas() {
        return arestas.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo (Rede PPI):\n");
        sb.append("Proteínas (Vértices): ").append(vertices.size()).append("\n");
        sb.append("Interações (Arestas): ").append(arestas.size()).append("\n");
        sb.append("Conexões (Adjacências):\n");
        List<Vertice> sortedVertices = new ArrayList<>(vertices);
        sortedVertices.sort(Comparator.comparing(Vertice::getNome));

        for (Vertice v : sortedVertices) {
            sb.append("  ").append(v).append(" (Grau ").append(getGrau(v)).append("): -> ");
            List<String> vizinhosStr = new ArrayList<>();
            List<Vertice> vizinhos = getVizinhos(v);
            vizinhos.sort(Comparator.comparing(Vertice::getNome)); 
            for (Vertice vizinho : vizinhos) {
                vizinhosStr.add(vizinho.toString());
            }
            sb.append(vizinhosStr).append("\n");
        }
        return sb.toString();
    }
}