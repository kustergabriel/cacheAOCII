import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Processador {
    //ATRIBUTOS
    List<String> enderecos = new ArrayList<>();


    //CONSTRUTOR
    public Processador () {

    }


    //LEITURA ARQUIVO

    public List <String> ler_arquivo () {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("arquivos/enderecos.txt");
            BufferedReader leitor = new BufferedReader(new InputStreamReader(is))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                enderecos.add(linha); // Adicionando os enderecos em uma lista pra ser mais facil manipular
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return enderecos;
    }

    //METODOS





}