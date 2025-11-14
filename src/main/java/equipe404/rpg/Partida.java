package equipe404.rpg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;            // Para tratar erros de leitura (Input/Output)
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

    // Para não perder as instancias e para renderizar para o jogador
    public static List<CartaAtaque> catalogoDeAtaque = new ArrayList<>();
    public static List<CartaDefesa> catalogoDeDefesa = new ArrayList<>();
    public static List<CartaSuporte> catalogoDeSuporte = new ArrayList<>();

    // Métodos
    public Partida() {

    }

    // Le os dados do arquivo e retorna uma lista de arrays com esses dados, sendo cada array uma linha do arquivo
    public static List<String[]> lerCSV(String nomeArquivo) {
        Path caminho = Paths.get(nomeArquivo);

        try (Stream<String> linhas = Files.lines(caminho)) { // linhas armazena o fluxo de strings, onde cada string é uma linha do arquivo
            return linhas
                    .skip(1) // Pula o cabeçalho
                    .map(linha -> linha.split(",")) // Quebra a linha do registro em um array de strings, separados por virgulas
                    .collect(Collectors.toList()); // Pega todos os arrays e coloca dentro de uma lista
        } catch (IOException e) {
            // Se o arquivo não for encontrado na raiz
            System.err.println("ERRO: Não foi possível ler o arquivo: " + nomeArquivo);
            return Collections.emptyList();
        }
    }


    public static void carregarCartas() {

        // Ataque
        List<String[]> arrayAtaque = Partida.lerCSV("ataque.csv");
        for (String[] dados : arrayAtaque) { // Sintaxe do forEach em Java
                String nome = dados[0];
                int poder = Integer.parseInt(dados[2].trim());
                int custo = Integer.parseInt(dados[3].trim());
                String descricao = dados[4];

                CartaAtaque novaCarta = new CartaAtaque(nome, poder, custo, descricao);
                catalogoDeAtaque.add(novaCarta);
        }

        // Defesa
        List<String[]> arrayDefesa = Partida.lerCSV("defesa.csv");
        for (String[] dados : arrayDefesa) { // Sintaxe do forEach em Java
            String nome = dados[0];
            int poder = Integer.parseInt(dados[2].trim());
            int custo = Integer.parseInt(dados[3].trim());
            String descricao = dados[4];

            CartaDefesa novaCarta = new CartaDefesa(nome, poder, custo, descricao);
            catalogoDeDefesa.add(novaCarta);
        }

        // Suporte
        List<String[]> arraySuporte = Partida.lerCSV("suporte.csv");
        for (String[] dados : arraySuporte) { // Sintaxe do forEach em Java
            String nome = dados[0];
            double poder = Double.parseDouble(dados[2].trim());
            int custo = Integer.parseInt(dados[3].trim());
            String efeito = dados[4];
            String descricao = dados[5];

            CartaSuporte novaCarta = new CartaSuporte(nome, poder, custo, efeito, descricao);
            catalogoDeSuporte.add(novaCarta);
        }
    }


    // Metodo para renderizar o cátalogo de cartas. Ao invés de renderizar um só catálogo, da pra separar por tipo.
    public static void exibirCatalogo(String tipo) {
        // Criar verificações
        if (tipo.equals("ataque")) {
            catalogoDeAtaque.forEach(carta -> {
                System.out.println("Nome: " + carta.getNome());
            });
        } else if (tipo.equals("defesa")) {
            catalogoDeDefesa.forEach(carta -> {
                System.out.println("Nome: " + carta.getNome());
            });
        } else if (tipo.equals("suporte")) {
            catalogoDeSuporte.forEach(carta -> {
                System.out.println("Nome: " + carta.getNome());
            });
        }

    }


    //    public static void escolherCartas() {
//
//        Scanner scanner = new Scanner(System.in);
//
//        // Instancia o Deck do Jogador
//        Deck deck1 = new Deck();
//
//        // Exibir todas as cartas de ataque como opções
//        Partida.exibirCatalogo(); // Fazer com que retorne só as de ataque
//
//        // Fazer o loop para a escolha das cartas. Precisa se certificar de que os inputs estão corretos, provavelmente por loops
//        if (deck1.getCartasAtaque().size() < 5) {
//            System.out.println("Digite o índice das 4 cartas escolhidas");
//
//            System.out.print("Primeira carta escolhida: ");
//            int indiceCarta1 = scanner.nextInt();
//
//            System.out.print("Segunda carta escolhida: ");
//            int indiceCarta2 = scanner.nextInt();
//
//            System.out.print("Terceira carta escolhida: ");
//            int indiceCarta3 = scanner.nextInt();
//
//            System.out.print("Quarta carta escolhida: ");
//            int indiceCarta4 = scanner.nextInt();
//
//            // Tenho que buscar no catálogo quais são as cartas referentes aos indices
//
//            deck1.addAtaque(catalogoDeCartas.get(indiceCarta1));
//            deck1.addAtaque(catalogoDeCartas.get(indiceCarta2));
//            deck1.addAtaque(catalogoDeCartas.get(indiceCarta3));
//            deck1.addAtaque(catalogoDeCartas.get(indiceCarta4));
//        }
//
//        deck1.imprimirCartas();
//    }


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

                Partida.carregarCartas();

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