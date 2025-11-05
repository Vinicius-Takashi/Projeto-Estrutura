import java.util.LinkedHashSet;
import java.util.Set;

public final class UsuariosIniciais {

    public static final String[] NOMES = {
            "Joao Oliveira Lima de Queiroz Alves",
            "Joao Victor Pessoa de Lima Dos Anjos",
            "Guilherme Gouvea Said Antonio",
            "Vinicius Eloy Lopes de Oliveira Araujo",
            "Vinicius da Silva Azevedo",
            "Gabriel Barrochelo",
            "Pedro Wilian Palumbo Bevilacqua",
            "Pedro Kuba Bloise",
            "Luigi Lauand Botto",
            "Lucas Mammoccio Gomes Martins Calcada",
            "Matheus Antonio da Luz Cardoso",
            "Gabriel Coutinho Cavalini",
            "Luigi de Menezes Collesi",
            "Arthur Silva Correia",
            "Felipe Fazio da Costa",
            "Diogo Musso Coutinho",
            "Enzo Manzoni Cunha",
            "Joaquim Anderlini Alves da Cunha",
            "Enzo Oliveira D´Onofrio",
            "Rafaella Bataglia Dallovo",
            "Gustavo Schmid Dividino",
            "Mark Malpighi Downey",
            "Leandro Meneghetti Fabre",
            "Henrique de Souza Gandra",
            "Gustavo Alves Gomes",
            "Enzo Medeiros Grando",
            "Fernando Godoi Grinevicius",
            "Ilan Hameiry",
            "Rodrigo Yassuhide Higa",
            "Leonardo Luiz Seixas Iorio",
            "Alan Martins Leandro",
            "Jonas Fernando da Silva Eboli Machado",
            "Luca Lopes Martinho",
            "Eduardo Martelli Marzagao",
            "Thiago Espigado Miras",
            "Vinicius Takashi Nakatsui",
            "Bruno Ferreira Nishiya",
            "Diego Mourao Oliveira",
            "Enzo Pistori Fontenele de Oliveira",
            "Leonardo Souza Olivieri",
            "Gustavo Henrique Rivero Pasqualin",
            "Felipe Kolanian Pasquini",
            "Guilherme de Santana Pinto",
            "Thiago Benelli Pizzolato",
            "Maria Luiza Bogossian Remaili",
            "Ruth Ramos Romeu",
            "Arthur Gama Ruiz",
            "Guilherme Gonsales de sa",
            "Gabriel Fernandes Sabino",
            "Caio Silva Almeida Santos",
            "Eduardo Dislich Dos Santos",
            "Joao Vitor Morimoto Sesma",
            "Sarah Mascarese de Souza",
            "Gabriel Giardino Sprotte",
            "Livia Naomi Ueno",
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

    private UsuariosIniciais() {} // utilitário
}
