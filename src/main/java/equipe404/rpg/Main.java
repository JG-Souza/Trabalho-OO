package equipe404.rpg;

import equipe404.rpg.model.Deck;
import equipe404.rpg.model.Hacker;

import java.util.Scanner;

public class Main {
    static void main() {
        GerenciadorCartas.carregarCartas();
        exibirMenu();
    }

    public static void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("A partida será entre:");
            System.out.println("1. Humano x Humano");
            System.out.println("2. Humano x Bot");
            System.out.println("3. Sair do Jogo");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            // Humano x Humano
            if (escolha.equals("1")) {
                // Cria os jogadores
                Hacker h1 = criarJogadorHumano(scanner, 1);
                Hacker h2 = criarJogadorHumano(scanner, 2);

                // Eles escolhem as cartass
                escolherCartas(h1);
                escolherCartas(h2);

                // Cria e inicia a partida
                Partida novaPartida = new Partida(h1, h2);
                novaPartida.iniciar();


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
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


    //tem que ter hacker como parametro
    public static void escolherCartas(Hacker hacker) {
        Scanner scanner = new Scanner(System.in);

        //cria o deck inicial vazio
        Deck deck = hacker.getDeck();

        //exibir as cartas pra serem selecionadas

        //seleção das cartas
        //fazer tambem a verificação pra ver se os indices estão corretos
        // se nao estiverem, pedir para escreverem de novo

        System.out.println("\n=== Escolha 4 Cartas de ATAQUE ===");
        GerenciadorCartas.exibirCatalogo("ataque");
        for(int i = 0; i < 4; i++) {
            int indiceCarta;
            while (true) {
                System.out.print("Escolha a " + (i + 1) + "° carta: ");
                indiceCarta = scanner.nextInt();
                if (indiceCarta < 0 || indiceCarta >= GerenciadorCartas.catalogoDeAtaque.size()) {
                    System.out.println("ERRO! Esse índice não está nas opções!");
                    continue;
                }

                String cartaAtual = GerenciadorCartas.catalogoDeAtaque.get(indiceCarta - 1).getNome();

                boolean repetido = false;
                for (int j = 0; j < hacker.getDeck().getCartasAtaque().size(); j++) {
                    if (cartaAtual == hacker.getDeck().getCartasAtaque().get(j).getNome()) {
                        repetido = true;
                        break;
                    }
                }
                if (repetido) {
                    System.out.println("CARTA REPETIDA! Escolha novamente!");
                    continue;
                }
                break;
            }
            //adicionar as cartas no deck
            deck.addAtaque(GerenciadorCartas.catalogoDeAtaque.get(indiceCarta - 1));
        }

        System.out.println("\n=== Escolha 4 Cartas de DEFESA ===");
        GerenciadorCartas.exibirCatalogo("defesa");

        for(int i = 0; i < 4; i++) {
            int indiceCarta;
            while (true) {
                System.out.print("Escolha a " + (i + 1) + "° carta: ");
                indiceCarta = scanner.nextInt();
                if (indiceCarta < 0 || indiceCarta >= GerenciadorCartas.catalogoDeDefesa.size()) {
                    System.out.println("ERRO! Esse índice não está nas opções!");
                    continue;
                }

                String cartaAtual = GerenciadorCartas.catalogoDeDefesa.get(indiceCarta - 1).getNome();

                boolean repetido = false;
                for (int j = 0; j < hacker.getDeck().getCartasDefesa().size(); j++) {
                    if (cartaAtual == hacker.getDeck().getCartasDefesa().get(j).getNome()) {
                        repetido = true;
                        break;
                    }
                }
                if (repetido) {
                    System.out.println("CARTA REPETIDA! Escolha novamente!");
                    continue;
                }
                break;
            }
            //adicionar as cartas no deck
            deck.addDefesa(GerenciadorCartas.catalogoDeDefesa.get(indiceCarta - 1));
        }

        System.out.println("\n=== Escolha 2 Cartas de SUPORTE ===");
        GerenciadorCartas.exibirCatalogo("suporte");

        for(int i = 0; i < 2; i++) {
            int indiceCarta;
            while (true) {
                System.out.print("Escolha a " + (i + 1) + "° carta: ");
                indiceCarta = scanner.nextInt();
                if (indiceCarta < 0 || indiceCarta >= GerenciadorCartas.catalogoDeSuporte.size()) {
                    System.out.println("ERRO! Esse índice não está nas opções!");
                    continue;
                }

                String cartaAtual = GerenciadorCartas.catalogoDeSuporte.get(indiceCarta - 1).getNome();

                boolean repetido = false;
                for (int j = 0; j < hacker.getDeck().getCartasSuporte().size(); j++) {
                    if (cartaAtual == hacker.getDeck().getCartasSuporte().get(j).getNome()) {
                        repetido = true;
                        break;
                    }
                }
                if (repetido) {
                    System.out.println("CARTA REPETIDA! Escolha novamente!");
                    continue;
                }
                break;
            }
            //adicionar as cartas no deck
            deck.addSuporte(GerenciadorCartas.catalogoDeSuporte.get(indiceCarta - 1));
        }
        //Cria o objeto que copia o deck atual e dps atribui a deckCopia
        Deck copia = hacker.getDeck().copiaDeck();
        hacker.setDeckCopia(copia);
    }


    private static Hacker criarJogadorHumano(Scanner scanner, int numeroJogador) {
        System.out.println("--- Dados do Jogador " + numeroJogador + " ---");
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a matricula: ");
        String matricula = scanner.nextLine();
        return new Hacker(nome, matricula);
    }
}
