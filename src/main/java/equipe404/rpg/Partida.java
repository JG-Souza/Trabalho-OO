package equipe404.rpg;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;            // Para tratar erros de leitura (Input/Output)
import java.net.URISyntaxException;  // Para tratar erros na conversão da URL
import java.net.URL;                 // Para representar a localização (URL) do recurso
import java.nio.file.Files;            // Para ler o arquivo (`Files.lines`)
import java.nio.file.Path;             // Para representar o "caminho" do arquivo
import java.nio.file.Paths;            // Para converter a URL em um "caminho" (`Paths.get`)
import java.util.List;               // Para usar o tipo de retorno `List`
import java.util.stream.Collectors;    // Para usar o `.collect(Collectors.toList())`
import java.util.stream.Stream;          // Para usar o `Stream<String>`

import equipe404.rpg.model.*;

public class Partida {
    // Atributos
    private Hacker hacker1;
    private Hacker hacker2;
    public static List<Carta> catalogoDeCartas = new ArrayList<>(); // Para não perder as instancias de objetos e para renderizar para o jogador

    // Métodos
    public Partida() {

    }

    // Le os dados do arquivo e retorna uma lista de arrays com esses dados, sendo cada array uma linha do arquivo
    public static List<String[]> lerCSV(String nomeArquivo) throws IOException, URISyntaxException {

        // Se a gente for criar uma classe utilitária depois, tem que mexer aqui
        URL recursoURL = Partida.class.getResource(nomeArquivo);

        if (recursoURL == null) {
            throw new IOException("Arquivo não encontrado em resources: " + nomeArquivo);
        }

        Path caminho = Paths.get(recursoURL.toURI());

        try (Stream<String> linhas = Files.lines(caminho)) { // linhas armazena o fluxo de strings, onde cada string é uma linha do arquivo
            return linhas
                .skip(1) // Pula o cabeçalho
                .map(linha -> linha.split(",")) // Quebra a linha do registro em um array de strings, separados por virgulas
                .collect(Collectors.toList()); // Pega todos os arrays e coloca dentro de uma lista
        }
    }


    // Posso tirar o dinamismo (parametragem) daqui e simplesmente ler todos os arquivos de uma vez
    public static void carregarCartas(String tipo) {
        if (tipo.equals("Ataque")) {
            try {
                List<String[]> arrayAtaque = Partida.lerCSV("/ataque.csv");

                for (String[] dadosDaLinha : arrayAtaque) { // Sintaxe do forEach em Java
                    try {
                        String nome = dadosDaLinha[0];
                        String tipoCarta = dadosDaLinha[1];
                        int poder = Integer.parseInt(dadosDaLinha[2].trim());
                        int custo = Integer.parseInt(dadosDaLinha[3].trim());
                        String descricao = dadosDaLinha[4];

                        Carta novaCarta = new Carta(nome, tipoCarta, poder, custo, descricao);

                        catalogoDeCartas.add(novaCarta);
                    } catch (NumberFormatException e) {
                        // Se "Poder" ou "Custo" não for um número válido no CSV
                        System.err.println("Erro ao converter dados numéricos da linha: " + String.join(",", dadosDaLinha));
                    }
                }
            } catch (IOException | URISyntaxException e) {
                // Se o "lerCSV" falhar (ex: arquivo não encontrado)
                System.err.println("ERRO CRÍTICO: Não foi possível ler o arquivo /ataque.csv");
            }

            Partida.exibirCatalogo();
        }
    }


    // Metodo para renderizar o cátalogo de cartas
    public static void exibirCatalogo() {
        catalogoDeCartas.forEach(carta -> {
            System.out.println("Nome: " + carta.getNome());
        });
    }


    public static void prepararDeck(Hacker hacker, Scanner sc) {
        //preparar os decks pro HUMANO
    }

    public static void iniciarPartida() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    A partida será entre:
                    1. Humano x Humano
                    2. Humano x Bot
                    3. Cancelar
                    """);
            String escolha = scanner.nextLine();

            if (escolha.equals("1")) {
                // Informações básicas do Hacker 1
                System.out.println("Digite o nome do Hacker 1");
                String nome1 = scanner.nextLine();
                System.out.println("Digite a matricula do Hacker 1");
                String matricula1 = scanner.nextLine();

                // Informações básicas do Hacker 2
                System.out.println("Digite o nome do Hacker 2");
                String nome2 = scanner.nextLine();
                System.out.println("Digite a matricula do Hacker 2");
                String matricula2 = scanner.nextLine();

                // Instanciação dos Hackers
                Hacker hacker1 = new Hacker(nome1, matricula1);
                Hacker hacker2 = new Hacker(nome2, matricula2);

                Partida.carregarCartas("Ataque");

            } else if (escolha.equals("2")) {
                // Informações básicas do Hacker 1
                System.out.println("Digite o nome do Hacker 1");
                String nomeHacker = scanner.nextLine();
                System.out.println("Digite a matricula do Hacker 1");
                String matriculaHacker = scanner.nextLine();

                // Informações do Bot são fixas
                String nomeBot = "BOT";
                String matriculaBot = "202565001";

                // Instanciação dos Hackers
                Hacker hacker1 = new Hacker(nomeHacker, matriculaHacker);
                Hacker bot = new Hacker(nomeBot, matriculaBot);

            } else if (escolha.equals("3")) {
                break;
            }
        }
    }

    public static void iniciarTurnos() {

    }
}