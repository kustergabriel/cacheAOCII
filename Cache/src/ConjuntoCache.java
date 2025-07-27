import java.util.Random;

public class ConjuntoCache {
    //ATRIBUTOS
    // Vetor de linhas (associatividade)
    private final LinhaCache[] linhas;
    // Gerador de números aleatórios para a substituição.
    private final Random geradorAleatorio;

    //CONSTRUTOR
    public ConjuntoCache(int associatividade) {
        this.linhas = new LinhaCache[associatividade];
        // Iniciar as linhas filhas (LinhaCache)
        for (int i = 0; i < associatividade; i++) {
            this.linhas[i] = new LinhaCache();
        }
        // Cria a instãncia para o conjunto
        this.geradorAleatorio = new Random();
    }

    //MÉTODOS
    /*
     * Procura por uma tag específica dentro das linhas
     * Retorna o objeto LinhaCache se for um hit (encontrou a tag e a linha é válida).
     * Retorna null se for um miss.
     */
    public LinhaCache procurarHit(int tag) {
        for (LinhaCache linha : this.linhas) {
            if (linha.isValida() && linha.getTag() == tag) {
                return linha; // HIT
            }
        }
        return null; // MISS
    }

    /*
     * Adiciona um novo bloco ao conjunto.
     * Primeiro, procura por uma linha inválida (vazia).
     * Se não houver linhas vazias, seleciona uma linha aleatória para substituição.
     * Retorna a linha que foi utilizada para armazenar o novo bloco.
    */
    public LinhaCache adicionarOuSubstituirBloco(int tag) {
        for (LinhaCache linha : this.linhas) {
            if (!linha.isValida()) {
                linha.setTag(tag);
                linha.setValida(true);
                return linha;
            }
        }

        // Se não há linhas vazias, substitui.
        int indiceAleatorio = geradorAleatorio.nextInt(this.linhas.length);
        LinhaCache linhaParaSubstituir = this.linhas[indiceAleatorio];
        
        linhaParaSubstituir.setTag(tag);
        // A linha já era válida, então não precisa mudar o bit de validade.
        
        return linhaParaSubstituir;
    }
}