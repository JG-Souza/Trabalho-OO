package equipe404.rpg;
import java.util.Scanner;
import equipe404.rpg.model.Hacker;

public class Partida {
    // Atributos

    // Métodos
    public Partida() {
        // Código do construtor
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