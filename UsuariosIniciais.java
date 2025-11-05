import java.util.LinkedHashSet;
import java.util.Set;

public final class UsuariosIniciais {

    public static final String[] NOMES = {
            "Pedro Kuba Bloise",
            "Luigi Lauand Botto",
            "Robson Calvetti",
            "Gabriel Coutinho Cavalini",
            "Arthur Silva Correia",
            "Diogo Musso Coutinho",
            "Gustavo Alves Gomes",
            "Enzo Medeiros Grando",
            "Ilan Hameiry",
            "Leonardo Luiz Seixas Iorio",
            "Alan Martins Leandro",
            "Vinicius Takashi Nakatsui",
            "Enzo Pistori Fontenele de Oliveira",
            "Gustavo Henrique Rivero Pasqualin",
            "Gustavo Henrique Lamberti Widonsck"
    };

    /** Adiciona todos os nomes à rede (deduplicando e mantendo a ordem). */
    public static void popular(GrafoSocial rede) {
        Set<String> unicosEmOrdem = new LinkedHashSet<>();
        for (String nome : NOMES) {
            if (nome != null) {
                String s = nome.trim();
                if (!s.isEmpty()) unicosEmOrdem.add(s);
            }
        }
        for (String s : unicosEmOrdem) {
            rede.adicionarUsuario(s);
        }
    }

    /**
     * Popula os usuários E cria algumas amizades pré-definidas.
     * Chame este método no start do app.
     */
    public static void popularComConexoes(GrafoSocial rede) {
        popular(rede);

        // --- Comunidade A ---
        link(rede, "Enzo Medeiros Grando", "Alan Martins Leandro");
        link(rede, "Enzo Medeiros Grando", "Vinicius Takashi Nakatsui");
        link(rede, "Enzo Medeiros Grando", "Gustavo Henrique Lamberti Widonsck");
        link(rede, "Enzo Medeiros Grando", "Gustavo Alves Gomes");
        link(rede, "Ilan Hameiry", "Enzo Medeiros Grando");
        // --- Comunidade B ---
        link(rede, "Diogo Musso Coutinho", "Leonardo Luiz Seixas Iorio");
        link(rede, "Diogo Musso Coutinho", "Gabriel Coutinho Cavalini");
        link(rede, "Diogo Musso Coutinho", "Gustavo Henrique Rivero Pasqualin");
        // --- Comunidade C ---
        link(rede, "Luigi Lauand Botto", "Enzo Pistori Fontenele de Oliveira");
        link(rede, "Luigi Lauand Botto", "Pedro Kuba Bloise");
        link(rede, "Luigi Lauand Botto", "Arthur Silva Correia");
        // --- Pontes entre comunidades (para o BFS brilhar) ---
        link(rede, "Gustavo Alves Gomes", "Diogo Musso Coutinho");                 // A ↔ B
        link(rede, "Enzo Medeiros Grando", "Luigi Lauand Botto");                      // B ↔ C
        link(rede, "Robson Calvetti", "Enzo Medeiros Grando");  // B ↔ D
        link(rede, "Robson Calvetti", "Luigi Lauand Botto");           // A ↔ D
    }

    /** Adiciona amizade se ambos existirem (evita NPE/casos de nome removido). */
    private static void link(GrafoSocial rede, String a, String b) {
        if (existe(rede, a) && existe(rede, b) && !a.equals(b)) {
            rede.adicionarAmizade(a, b);
        }
    }

    /** Verifica existência do usuário na rede. */
    private static boolean existe(GrafoSocial rede, String nome) {
        return rede.getListaAdjacencia() != null
                && rede.getListaAdjacencia().containsKey(nome);
    }

    private UsuariosIniciais() {}
}
