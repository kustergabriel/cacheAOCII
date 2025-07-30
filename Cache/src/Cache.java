import java.util.HashSet;
import java.util.Set;

/* Simula um nível completo de cache.
 *Receber um endereço de memória de 32 bits. 
 *Dividir esse endereço em Tag, Índice e Offset.
 *Usar o Índice para encontrar o ConjuntoCache correto.
 *A busca daí é feita pelo conjunto
 * -> Registrar se foi um hit ou um miss.
 *Se for um miss, classificá-lo (compulsório ou conflito/capacidade) e, em seguida, adicionar o novo bloco. 
 *Se houver um próximo nível de cache, buscar la tbm em caso de miss.
 *No final, exibir as estatísticas.
 */

public class Cache {
    //ATRIBUTOS
    // Atributos de configuração da cache
    private final String nome;
    private final int nsets, bsize, assoc; // To usando no construtor java ):
    private final int numBitsOffset;
    private final int numBitsIndice;
    
    // Estrutura da cache
    private final ConjuntoCache[] conjuntos;
    private final Cache proximoNivel;

    // Atributos para estatísticas
    private long numAcessos = 0;
    private long numHits = 0;
    private long numMisses = 0;
    private long missesCompulsorios = 0;
    private long missesConflitoCapacidade = 0;

    // Conjunto para rastrear blocos já vistos e identificar misses compulsórios
    private final Set<Long> blocosVistos;

    //CONSTRUTOR
    public Cache(String nome, int nsets, int bsize, int assoc, Cache proximoNivel) {
        this.nome = nome;
        this.nsets = nsets;
        this.bsize = bsize;
        this.assoc = assoc;
        this.proximoNivel = proximoNivel;
        
        // Calcula o número de bits para o offset e o índice
        this.numBitsOffset = log2(bsize);
        this.numBitsIndice = log2(nsets);

        // Inicializa a estrutura da cache
        this.conjuntos = new ConjuntoCache[nsets];
        for (int i = 0; i < nsets; i++) {
            conjuntos[i] = new ConjuntoCache(assoc);
        }
        
        // Inicializa o rastreador de misses compulsórios
        this.blocosVistos = new HashSet<>();
    }

    //MÉTODOS
    // Separa o endereço de 32 bits
    public void acessarEndereco(long endereco) {
    this.numAcessos++;

    int tag = calcularTag(endereco);
    int indice = calcularIndice(endereco);
    
    //Acessar o conjunto
    ConjuntoCache conjuntoAlvo = this.conjuntos[indice];
    // Procurar pelo bloco no conjunto
    LinhaCache linhaEncontrada = conjuntoAlvo.procurarHit(tag);

    if (linhaEncontrada != null) {
        // Hit
        this.numHits++;
    } else {
        // Miss
        this.numMisses++;

        long enderecoDoBloco = endereco / this.bsize;
        if (blocosVistos.contains(enderecoDoBloco)) {
            this.missesConflitoCapacidade++;
        } else {
            this.missesCompulsorios++;
            this.blocosVistos.add(enderecoDoBloco);
        }

        conjuntoAlvo.adicionarOuSubstituirBloco(tag);

        if (this.proximoNivel != null) {
            this.proximoNivel.acessarEndereco(endereco);
        }
    }
}
    
    private int calcularIndice(long endereco) {
        // Desloca os bits do offset para a direita
        long semOffset = endereco >> this.numBitsOffset; // Qtd q é pra deslocar (Tira o offset e coloca o indicie no final)
        // Cria uma máscara para isolar os bits do índice
        int mascaraIndice = (1 << this.numBitsIndice) - 1; // Manda todos os bits pra esquerda, e subtrai 1 ->
        //-> para ficar com tudo 1 nos bits menos significativos
        return (int) (semOffset & mascaraIndice); // Compara bit-a-bit e pega os valores que virou 1 em cima
    }
    
    private int calcularTag(long endereco) {
        // Desloca os bits do offset e do índice para a direita
        return (int) (endereco >> (this.numBitsOffset + this.numBitsIndice));
    }

    //Imprime o relatório final de estatísticas para este nível de cache.

    public void imprimirEstatisticas() {
        double hitRatio;
        double missRatio;

        // Calcula o nº de hitrate
        if (this.numAcessos == 0) {
            hitRatio = 0;
        } else {
            hitRatio = (double) this.numHits / this.numAcessos;
        }

        // Calcula o nº de missrate
        if (this.numAcessos == 0) {
            missRatio = 0;
        } else {
            missRatio = (double) this.numMisses / this.numAcessos;
        }

        System.out.println("--- Estatísticas da Cache " + this.nome + " ---");
        System.out.println("Número total de acessos: " + this.numAcessos);
        System.out.println("Número total de hits: " + this.numHits);
        System.out.println("Número total de misses: " + this.numMisses);
        System.out.println("\tMisses compulsórios: " + this.missesCompulsorios);
        System.out.println("\tMisses de capacidade/conflito: " + this.missesConflitoCapacidade);
        System.out.printf("Hit ratio: %.4f (%.2f%%)\n", hitRatio, hitRatio * 100);
        System.out.printf("Miss ratio: %.4f (%.2f%%)\n", missRatio, missRatio * 100);
        System.out.println("-------------------------------------\n");
    }

    // Calcula o log2 de um número.
    private static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }
}