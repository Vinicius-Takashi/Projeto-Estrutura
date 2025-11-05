import java.util.Objects;

public class Aresta {
    final Vertice v1;
    final Vertice v2;


    public Aresta(Vertice v1, Vertice v2) {
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Vértices da aresta não podem ser nulos.");
        }
       
        if (v1.getNome().compareTo(v2.getNome()) > 0) {
            this.v1 = v2;
            this.v2 = v1;
        } else {
            this.v1 = v1;
            this.v2 = v2;
        }
      
    }

    public Vertice getV1() {
        return v1;
    }

    public Vertice getV2() {
        return v2;
    }

    public Vertice getVizinho(Vertice atual) {
        if (atual.equals(v1)) {
            return v2;
        } else if (atual.equals(v2)) {
            return v1;
        }
        return null; 
    }

    public boolean conecta(Vertice vert1, Vertice vert2) {
        return (v1.equals(vert1) && v2.equals(vert2)) || (v1.equals(vert2) && v2.equals(vert1));
    }

    public String toString() {
        return "(" + v1.getNome() + " <-> " + v2.getNome() + ")";
    }

   
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Aresta aresta = (Aresta) o;
        return Objects.equals(v1, aresta.v1) && Objects.equals(v2, aresta.v2);
    }

    public int hashCode() {
        return Objects.hash(v1, v2);
    }
}