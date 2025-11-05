import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultadosGUI extends JFrame {

    private Grafo grafo;
    private JTabbedPane tabbedPane;
    
    // Componentes de abas (JComponents para facilitar a atualização)
    private JComponent abaGraus;
    private JComponent abaCaminhoCurto;
    private JComponent abaLigacoes;
    
    // Elementos da Aba de Construção
    private JTextField txtVertice;
    private JTextField txtArestaV1;
    private JTextField txtArestaV2;
    private JTextArea statusArea;
    
    // Elementos da Aba de Caminho Mais Curto
    private JTextField txtCaminhoInicio;
    private JTextField txtCaminhoFim;
    private JTextArea txtCaminhoResultado;


    // Construtor
    public ResultadosGUI(Grafo grafo) {
        this.grafo = grafo;
        
        setTitle("Análise de Rede PPI com Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null); 

        tabbedPane = new JTabbedPane();
        
        // As abas são criadas e adicionadas imediatamente, sem dados de análise.
        inicializarAbas();

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }
    
    /**
     * Cria e adiciona todas as abas no JTabbedPane, sem números no nome.
     */
    private void inicializarAbas() {
        // Aba 0: Construção
        tabbedPane.addTab("Construir Rede PPI", criarAbaConstrucao()); 

        // Abas de Análise (Criadas sem dados de análise)
        abaGraus = criarAbaGraus(null, null);
        abaCaminhoCurto = criarAbaCaminhoMaisCurto();
        abaLigacoes = criarAbaLigacoes();
        
        // Adiciona as abas
        tabbedPane.addTab("Análise das proteínas essenciais", abaGraus);
        tabbedPane.addTab("Descobrir a via mais eficiente", abaCaminhoCurto);
        tabbedPane.addTab("Interações das proteínas", abaLigacoes);
    }
    
    /**
     * Ação disparada pelo botão 'Executar Análise'.
     * Executa a análise e adiciona/atualiza as abas de resultado.
     */
    private void executarAnaliseGeral(ActionEvent event) {
        if (grafo.getNumeroVertices() == 0) {
             JOptionPane.showMessageDialog(this, "O grafo está vazio. Adicione proteínas antes de executar a análise.", "Erro de Análise", JOptionPane.WARNING_MESSAGE);
             return;
        }

        // 1. Executa Análises
        Map<String, Integer> graus = AnaliseRedeUtil.calcularGraus(grafo);
        List<String> hubs = AnaliseRedeUtil.encontrarHubs(grafo); 

        // 2. Remove Abas Antigas (para garantir que as novas entrem)
        tabbedPane.remove(abaGraus);
        tabbedPane.remove(abaLigacoes);
        
        // 3. Cria/Atualiza Abas com Resultados
        abaGraus = criarAbaGraus(graus, hubs);
        abaLigacoes = criarAbaLigacoes();

        // 4. Adiciona as Abas Atualizadas (com os nomes sem números)
        tabbedPane.addTab("Análise dos Graus", abaGraus);
        tabbedPane.addTab("Ligações (Adjacências)", abaLigacoes);
        
        // Mover a aba 1 e 3 para a posição correta, pois a 2 é fixa
        // O índice da aba de Caminho Mais Curto (BFS) é 2.
        tabbedPane.setTabComponentAt(1, tabbedPane.getTabComponentAt(tabbedPane.getTabCount() - 2));
        tabbedPane.setTabComponentAt(3, tabbedPane.getTabComponentAt(tabbedPane.getTabCount() - 1));

        // Vai para a primeira aba de análise
        tabbedPane.setSelectedIndex(1); 
        statusArea.append("SUCESSO: Análise Geral de " + grafo.getNumeroVertices() + " proteínas concluída e abas de análise atualizadas.\n");
    }
    
    // --- MÉTODOS DE CRIAÇÃO DAS ABAS ---

    private JComponent criarAbaConstrucao() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de Controles (Norte)
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Sub-painel 1: Adicionar Vértice
        JPanel verticePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtVertice = new JTextField(15);
        JButton btnAddVertice = new JButton("Adicionar Proteína");
        btnAddVertice.addActionListener(this::adicionarVerticeAcao);
        verticePanel.add(new JLabel("Nome da Proteína:"));
        verticePanel.add(txtVertice);
        verticePanel.add(btnAddVertice);
        
        // Sub-painel 2: Adicionar Aresta
        JPanel arestaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtArestaV1 = new JTextField(5);
        txtArestaV2 = new JTextField(5);
        JButton btnAddAresta = new JButton("Adicionar Interação");
        btnAddAresta.addActionListener(this::adicionarArestaAcao);
        arestaPanel.add(new JLabel("Proteína 1:"));
        arestaPanel.add(txtArestaV1);
        arestaPanel.add(new JLabel("Proteína 2:"));
        arestaPanel.add(txtArestaV2);
        arestaPanel.add(btnAddAresta);

        // Sub-painel 3: Botão de Análise (Ação principal para o novo requisito)
        JPanel acaoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnExecutarAnalise = new JButton("EXECUTAR ANÁLISE COMPLETA");
        btnExecutarAnalise.setFont(new Font("Arial", Font.BOLD, 14));
        btnExecutarAnalise.addActionListener(this::executarAnaliseGeral);
        acaoPanel.add(btnExecutarAnalise);


        controlPanel.add(verticePanel);
        controlPanel.add(arestaPanel);
        controlPanel.add(acaoPanel);
        
        panel.add(controlPanel, BorderLayout.NORTH);

        // Área de Status (Centro)
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statusArea.setText("STATUS: Grafo inicializado vazio. Construa a rede.\n"); 
        panel.add(new JScrollPane(statusArea), BorderLayout.CENTER);

        return panel;
    }
    
    private JComponent criarAbaGraus(Map<String, Integer> graus, List<String> hubs) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("      GRAU DE CADA PROTEÍNA E HUBS\n");
        sb.append("=========================================\n\n");
        
        if (graus == null || graus.isEmpty()) {
            sb.append(">> Execute a Análise Geral (Aba 'Construir Grafo') para ver os resultados.\n");
        } else {
            // Formata os graus
            sb.append("GRAU POR PROTEÍNA (CONECTIVIDADE):\n");
            graus.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> sb.append(String.format("  %-10s: %d\n", entry.getKey(), entry.getValue())));
            
            sb.append("\n-----------------------------------------\n");
            
            // Formata os Hubs
            sb.append("PROTEÍNA(S) HUB (MAIOR GRAU):\n");
            if (hubs.isEmpty()) { 
                sb.append("  Nenhum Hub encontrado. (Grafo vazio ou problema de cálculo).\n");
            } else {
                sb.append("  Grau Máximo: ").append(graus.get(hubs.get(0))).append("\n"); 
                sb.append("  Hubs (Proteínas Essenciais): ").append(hubs.stream().collect(Collectors.joining(", "))).append("\n");
            }
        }
        
        textArea.setText(sb.toString());
        return new JScrollPane(textArea);
    }

    private JComponent criarAbaCaminhoMaisCurto() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de Controles (Norte)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtCaminhoInicio = new JTextField(10);
        txtCaminhoFim = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar Caminho Mais Curto");
        btnBuscar.addActionListener(this::executarAnaliseCaminhoMaisCurto);
        
        controlPanel.add(new JLabel("Início (Proteína A):"));
        controlPanel.add(txtCaminhoInicio);
        controlPanel.add(new JLabel("Fim (Proteína B):"));
        controlPanel.add(txtCaminhoFim);
        controlPanel.add(btnBuscar);
        
        panel.add(controlPanel, BorderLayout.NORTH);

        // Área de Resultados (Centro)
        txtCaminhoResultado = new JTextArea();
        txtCaminhoResultado.setEditable(false);
        txtCaminhoResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtCaminhoResultado.setText("=========================================\n" +
                                    "    ANÁLISE DE CAMINHO MAIS CURTO (BFS)\n" +
                                    "=========================================\n\n" +
                                    ">> Insira as proteínas de Início e Fim acima e clique em 'Buscar'.");
        
        panel.add(new JScrollPane(txtCaminhoResultado), BorderLayout.CENTER);
        
        return panel;
    }
    
    private void executarAnaliseCaminhoMaisCurto(ActionEvent event) {
        String inicio = txtCaminhoInicio.getText().trim();
        String fim = txtCaminhoFim.getText().trim();

        if (grafo.getNumeroVertices() < 2) {
            JOptionPane.showMessageDialog(this, "O grafo deve ter pelo menos 2 vértices para buscar um caminho.", "Erro de Parâmetros", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (inicio.isEmpty() || fim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Os campos de Início e Fim devem ser preenchidos.", "Erro de Parâmetros", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (grafo.buscarVertice(inicio) == null || grafo.buscarVertice(fim) == null) {
            JOptionPane.showMessageDialog(this, "A(s) proteína(s) de Início ou Fim não existe(m) no grafo.", "Erro de Parâmetros", JOptionPane.WARNING_MESSAGE);
            return;
        }


        // 1. Executa a análise
        List<String> caminho = AnaliseRedeUtil.encontrarCaminhoMaisCurto(grafo, inicio, fim);
        
        // 2. Formata o resultado
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("RESULTADO DA BUSCA (%s -> %s):\n", inicio, fim));
        sb.append("-----------------------------------------\n");
        
        if (caminho != null && !caminho.isEmpty()) {
            sb.append(String.format("  - Interações (Tamanho do Caminho): %d\n", caminho.size() - 1));
            sb.append("  - Caminho:  ").append(String.join(" -> ", caminho)).append("\n");
            statusArea.append(String.format("SUCESSO: Caminho de %s para %s encontrado com %d passos.\n", inicio, fim, caminho.size() - 1));
        } else {
            sb.append(String.format("  - Caminho NÃO encontrado. Verifique se as proteínas '%s' e '%s' estão conectadas.\n", inicio, fim));
            statusArea.append(String.format("AVISO: Caminho de %s para %s NÃO encontrado.\n", inicio, fim));
        }

        txtCaminhoResultado.setText(sb.toString());
    }


    private JComponent criarAbaLigacoes() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        textArea.setText(grafo.toString());
        
        return new JScrollPane(textArea);
    }
    
    // --- MÉTODOS DE AÇÃO DE CONSTRUÇÃO ---

    private void adicionarVerticeAcao(ActionEvent e) {
        String nome = txtVertice.getText().trim();
        if (nome.isEmpty()) {
            statusArea.append("ERRO: Nome da proteína não pode ser vazio.\n");
            return;
        }

        if (grafo.buscarVertice(nome) == null) {
            grafo.adicionarVertice(nome);
            statusArea.append("SUCESSO: Proteína '" + nome + "' adicionada.\n");
            txtVertice.setText("");
            // Atualiza a aba de Ligações se a análise geral já foi executada
            if (tabbedPane.getTabCount() > 3) {
                 tabbedPane.remove(abaLigacoes);
                 abaLigacoes = criarAbaLigacoes();
                 tabbedPane.addTab("Ligações (Adjacências)", abaLigacoes);
            }
        } else {
            statusArea.append("AVISO: Proteína '" + nome + "' já existe.\n");
        }
    }

    private void adicionarArestaAcao(ActionEvent e) {
        String v1Nome = txtArestaV1.getText().trim();
        String v2Nome = txtArestaV2.getText().trim();
        
        if (v1Nome.isEmpty() || v2Nome.isEmpty()) {
            statusArea.append("ERRO: Nomes dos vértices não podem ser vazios.\n");
            return;
        }
        
        Vertice v1 = grafo.buscarVertice(v1Nome);
        Vertice v2 = grafo.buscarVertice(v2Nome);
        
        if (v1 == null || v2 == null) {
            String ausente = (v1 == null ? v1Nome : "") + (v2 == null ? (v1 == null ? " e " : "") + v2Nome : "");
            statusArea.append("ERRO: Vértice(s) não encontrado(s): " + ausente + ". Adicione-os primeiro.\n");
            return;
        }

        Aresta novaAresta = grafo.adicionarAresta(v1Nome, v2Nome);
        
        if (novaAresta != null) {
            statusArea.append("SUCESSO: Interação (" + v1Nome + " <-> " + v2Nome + ") adicionada.\n");
            txtArestaV1.setText("");
            txtArestaV2.setText("");
            // Atualiza a aba de Ligações se a análise geral já foi executada
            if (tabbedPane.getTabCount() > 3) {
                 tabbedPane.remove(abaLigacoes);
                 abaLigacoes = criarAbaLigacoes();
                 tabbedPane.addTab("Ligações (Adjacências)", abaLigacoes);
            }
        } else {
            statusArea.append("AVISO: Interação (" + v1Nome + " <-> " + v2Nome + ") já existe ou falha.\n");
        }
    }
}