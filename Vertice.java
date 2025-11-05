import java.util.Objects;

public class Vertice {
    final String nome; 

    public Vertice(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do vértice não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }


    public int hashCode() {
        return Objects.hash(nome);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vertice other = (Vertice) obj;
        return Objects.equals(nome, other.nome);
    }


    public String toString() {
        return nome;
    }
}