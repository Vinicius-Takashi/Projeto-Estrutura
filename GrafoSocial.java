import java.util.*;

public class GrafoSocial {
    // Lista de Adjacências: Mapeia o nome do Usuário para a lista de seus amigos.
    private Map<String, List<String>> listaAdjacencia = new HashMap<>();

    // --- Métodos de Construção do Grafo ---

    public void adicionarUsuario(String nomeUsuario) {
        if (!listaAdjacencia.containsKey(nomeUsuario)) {
            listaAdjacencia.put(nomeUsuario, new ArrayList<>());
        }
    }

    /**
     * Cria uma conexão de amizade bidirecional (aresta não-direcionada).
     */
    public void adicionarAmizade(String usuario1, String usuario2) {
        adicionarUsuario(usuario1);
        adicionarUsuario(usuario2);

        // Adiciona a conexão nos dois sentidos (grafo não-direcionado)
        if (!listaAdjacencia.get(usuario1).contains(usuario2)) {
            listaAdjacencia.get(usuario1).add(usuario2);
        }
        if (!listaAdjacencia.get(usuario2).contains(usuario1)) {
            listaAdjacencia.get(usuario2).add(usuario1);
        }
    }

    // --- Método do Algoritmo BFS para Recomendação ---

    /**
     * Algoritmo de Busca em Largura (BFS) para encontrar recomendações a distância 2.
     * @param usuarioOrigem O usuário para o qual se busca recomendações.
     * @return Um conjunto de nomes de usuários recomendados (amigos de amigos).
     */
    public Set<String> recomendarAmigos(String usuarioOrigem) {
        Queue<String> fila = new LinkedList<>();
        // Usa um Mapa para rastrear a distância da origem, essencial para o BFS e o problema
        Map<String, Integer> distancia = new HashMap<>(); 
        Set<String> recomendacoes = new HashSet<>();

        if (!listaAdjacencia.containsKey(usuarioOrigem)) {
            return recomendacoes;
        }
        
        fila.add(usuarioOrigem);
        distancia.put(usuarioOrigem, 0); // Distância da origem é 0

        while (!fila.isEmpty()) {
            String atual = fila.poll();
            int distAtual = distancia.get(atual);

            // Paramos a busca no nível 2
            if (distAtual >= 2) {
                // Se a distância é exatamente 2, ele é um Amigo de Amigo
                if (distAtual == 2) {
                     recomendacoes.add(atual);
                }
                continue; 
            }

            // Visitar vizinhos (amigos)
            for (String vizinho : listaAdjacencia.getOrDefault(atual, Collections.emptyList())) {
                
                // Se o vizinho ainda não foi visitado (não tem distância definida)
                if (!distancia.containsKey(vizinho)) {
                    // Define a nova distância e o adiciona à fila para visita futura
                    distancia.put(vizinho, distAtual + 1);
                    fila.add(vizinho);
                }
            }
        }
        
        // --- Filtragem Final ---
        // 1. Remove a origem da lista (Distância 0)
        recomendacoes.remove(usuarioOrigem);
        
        // 2. Remove todos os amigos diretos (Distância 1)
        for (String amigoDireto : listaAdjacencia.getOrDefault(usuarioOrigem, Collections.emptyList())) {
             recomendacoes.remove(amigoDireto);
        }

        return recomendacoes;
    }
    
    // --- Getter para uso na Interface Gráfica ---
    
    public Map<String, List<String>> getListaAdjacencia() {
        return listaAdjacencia;
    }
}