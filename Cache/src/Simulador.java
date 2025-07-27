import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

public class Simulador {

    public static void main(String[] args) {
        // Leitura dos Argumentos
        if (args.length < 2 || args.length > 4) {
            System.out.println("Uso incorreto. Formatos esperados:");
            System.out.println("java Simulador <nsets_L1>:<bsize_L1>:<assoc_L1> arquivo_de_entrada");
            System.out.println("java Simulador <nsets_L1>... <nsets_L2>:<bsize_L2>:<assoc_L2> arquivo_de_entrada");
            System.out.println("java Simulador <nsets_L1>... <nsets_L2>... <nsets_L3>... arquivo_de_entrada");
            return;
        }

        Cache l1 = null, l2 = null, l3 = null; // Inicio com null pra facilitar no construtor
        String arquivoDeEntrada = "";

        try {
            // Construção das caches
            int indiceArquivo = args.length - 1;
            arquivoDeEntrada = args[indiceArquivo];

            if (args.length > 2) { // Temos L2 ou L3
                if (args.length > 3) { // Temos L3
                    String[] paramsL3 = args[2].split(":");
                    l3 = new Cache("L3", Integer.parseInt(paramsL3[0]), Integer.parseInt(paramsL3[1]), Integer.parseInt(paramsL3[2]), null);
                }
                String[] paramsL2 = args[1].split(":");
                // L2 aponta para L3
                l2 = new Cache("L2", Integer.parseInt(paramsL2[0]), Integer.parseInt(paramsL2[1]), Integer.parseInt(paramsL2[2]), l3);
            }
            String[] paramsL1 = args[0].split(":");
            // L1 aponta para L2
            l1 = new Cache("L1", Integer.parseInt(paramsL1[0]), Integer.parseInt(paramsL1[1]), Integer.parseInt(paramsL1[2]), l2);

        } catch (Exception e) {
            System.err.println("MOIOU!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + e.getMessage());
            return;
        }

        // Leitura do arquivo
        System.out.println("Iniciando simulação com o arquivo: " + arquivoDeEntrada);
        System.out.println("Configuração de cache montada.\n");

        try (DataInputStream leitor = new DataInputStream(new FileInputStream(arquivoDeEntrada))) {
            while (true) {
                // O arquivo contém endereços de 32 bits em formato binário 
                int enderecoInt = leitor.readInt();
                // long pq deu BO
                long endereco = Integer.toUnsignedLong(enderecoInt);
                
                // Manda o endereço pra cache
                if (l1 != null) {
                    l1.acessarEndereco(endereco);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de entrada não encontrado: " + arquivoDeEntrada);
        } catch (EOFException e) {
            // Fim do arquivo
            System.out.println("Simulação concluída. Todos os endereços foram processados.");
        } catch (IOException e) {
            System.err.println("Erro de I/O ao ler o arquivo: " + e.getMessage());
        }

        // Estatistica final
        if (l1 != null) {
            l1.imprimirEstatisticas();
        }
        if (l2 != null) {
            l2.imprimirEstatisticas();
        }
        if (l3 != null) {
            l3.imprimirEstatisticas();
        }
    }
}