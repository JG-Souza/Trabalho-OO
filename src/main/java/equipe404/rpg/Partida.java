package equipe404.rpg;
import java.util.ArrayList;
import java.util.Scanner;

import equipe404.rpg.model.CartaAtaque;
import equipe404.rpg.model.CartaDefesa;
import equipe404.rpg.model.CartaSuporte;
import equipe404.rpg.model.Hacker;

public class Partida {
    // Atributos
    private Hacker hacker1;
    private Hacker hacker2;

    //lista com as cartas disponiveis
    //aqui é so um teste, depois tem que ver como que usa aqueles arquivos que ele passou

    private static ArrayList<CartaAtaque> ataquesDisponiveis = new ArrayList<>();
    private static ArrayList<CartaDefesa> defesasDisponiveis = new ArrayList<>();
    private static ArrayList<CartaSuporte> suportesDisponiveis = new ArrayList<>();

    // Métodos
    public Partida() {

    }

    //aqui é so um teste, depois tem que ver como que usa aqueles arquivos que ele passou
    public static void carregarCartas() {

        ataquesDisponiveis.add(new CartaAtaque("Exploit Zero-Day", 5, 50));
        ataquesDisponiveis.add(new CartaAtaque("Phishing Direcionado", 2, 20));
        ataquesDisponiveis.add(new CartaAtaque("Força Quântica", 5, 50));
        ataquesDisponiveis.add(new CartaAtaque("SQL Injection", 3, 30));

        defesasDisponiveis.add(new CartaDefesa("Firewall Hiperescalar", 5, 50));
        defesasDisponiveis.add(new CartaDefesa("Reinício Seguro", 1, 10));
        defesasDisponiveis.add(new CartaDefesa("Isolamento de Usuário", 2, 20));
        defesasDisponiveis.add(new CartaDefesa("Controle de Acesso Dinâmico", 4, 40));

        suportesDisponiveis.add(new CartaSuporte("Análise Heurística", "AUMENTA_ATAQUE", 2, 0.2));
        suportesDisponiveis.add(new CartaSuporte("Blindagem Digital", "DIMINUI_ATAQUE", 3, 0.3));
        suportesDisponiveis.add(new CartaSuporte("Compressão de Dados", "AUMENTA_VIDA", 10, 1));
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

                hacker1.imprime();

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