import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


public class Processador {
    //ATRIBUTOS


    //CONSTRUTOR
    public Processador () {

    }


    //LEITURA ARQUIVO

    public void ler_arquivo () {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/enderecos.txt");
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String conteudo = reader.lines().collect(Collectors.joining("\n"));
                System.out.println(conteudo);
            } else {
                System.out.println("Arquivo não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }
    }

    //METODOS





}