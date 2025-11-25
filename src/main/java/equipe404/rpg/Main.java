package equipe404.rpg;

import equipe404.rpg.model.Deck;
import equipe404.rpg.model.Hacker;

 import java.util.Locale;
import java.util.Random;
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

                System.out.println("\n" + h1.getNome() + " escolha sua opção de montagem de deck:");
                System.out.println("1. Montar deck manualmente");
                System.out.println("2. Usar deck aleatório");
                System.out.print("Escolha uma opção: ");
                String opcaoDeck1 = scanner.nextLine();

                // Eles escolhem as cartas
                if (opcaoDeck1.equals("1")){
                    escolherCartas(h1);
                } else if (opcaoDeck1.equals("2")){
                    escolherCartasAleatorio(h1);
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }

                System.out.println("\n" + h2.getNome() + " escolha sua opção de montagem de deck:");
                System.out.println("1. Montar deck manualmente");
                System.out.println("2. Usar deck aleatório");
                System.out.print("Escolha uma opção: ");
                String opcaoDeck2 = scanner.nextLine();
                if(opcaoDeck2.equals("1")) {
                    escolherCartas(h2);
                } else if (opcaoDeck2.equals("2")){
                    escolherCartasAleatorio(h2);
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }

                // Cria e inicia a partida
                Partida novaPartida = new Partida(h1, h2);
                novaPartida.iniciar();
                verificarFimDeJogo(h1,h2);
                break;

            } else if (escolha.equals("2")) {
                Hacker h1 = criarJogadorHumano(scanner, 1);
                Hacker h2 = criarJogadorBot(2);

                System.out.println("\n" + h1.getNome() + " escolha sua opção de montagem de deck:");
                System.out.println("1. Montar deck manualmente");
                System.out.println("2. Usar deck aleatório");
                System.out.print("Escolha uma opção: ");
                String opcaoDeck1 = scanner.nextLine();

                // Eles escolhem as cartas
                if (opcaoDeck1.equals("1")){
                    escolherCartas(h1);
                } else if (opcaoDeck1.equals("2")){
                    escolherCartasAleatorio(h1);
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
                escolherCartasAleatorio(h2);

                Partida novaPartida = new Partida(h1, h2);
                novaPartida.iniciar();
                verificarFimDeJogo(h1,h2);
                break;

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
                if (scanner.hasNextInt()) {
                    indiceCarta = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("ENTRADA INVÁLIDA. Por favor, digite o número do índice. Tente novamente.");
                    //limpa a entrada se não da loop infinito
                    scanner.nextLine();
                    continue;
                }

                if (indiceCarta < 0 || indiceCarta > GerenciadorCartas.catalogoDeAtaque.size()) {
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
                if (scanner.hasNextInt()) {
                    indiceCarta = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("ENTRADA INVÁLIDA. Por favor, digite o número do índice. Tente novamente.");
                    //limpa a entrada se não da loop infinito
                    scanner.nextLine();
                    continue;
                }

                if (indiceCarta < 0 || indiceCarta > GerenciadorCartas.catalogoDeDefesa.size()) {
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
                if (scanner.hasNextInt()) {
                    indiceCarta = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("ENTRADA INVÁLIDA. Por favor, digite o número do índice. Tente novamente.");
                    //limpa a entrada se não da loop infinito
                    scanner.nextLine();
                    continue;
                }

                if (indiceCarta < 0 || indiceCarta > GerenciadorCartas.catalogoDeSuporte.size()) {
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

    private static Hacker criarJogadorBot (int numeroJogador) {
        return new Hacker ("BOT", "202565001");
    }

    public static void escolherCartasAleatorio(Hacker hacker) {

        Deck deck = hacker.getDeck();

        Random r = new Random();

        for(int i = 0; i < 4; i++) {
            int indiceAtaque;
            while(true) {
                indiceAtaque = r.nextInt(GerenciadorCartas.catalogoDeAtaque.size());
                String cartaAtual = GerenciadorCartas.catalogoDeAtaque.get(indiceAtaque).getNome();

                boolean repetido = false;
                for(int j = 0; j < hacker.getDeck().getCartasAtaque().size(); j++) {
                    if (hacker.getDeck().getCartasAtaque().get(j).getNome().equals(cartaAtual)) {
                        repetido = true;
                        break;
                    }
                }
                if(!repetido) break; //se nao for repetido ==> carta valida, sai do while
            }
            deck.addAtaque(GerenciadorCartas.catalogoDeAtaque.get(indiceAtaque));
        }

        for(int i = 0; i < 4; i++) {
            int indiceDefesa;
            while(true) {
                indiceDefesa = r.nextInt(GerenciadorCartas.catalogoDeDefesa.size());
                String cartaAtual = GerenciadorCartas.catalogoDeDefesa.get(indiceDefesa).getNome();

                boolean repetido = false;
                for(int j = 0; j < hacker.getDeck().getCartasDefesa().size(); j++) {
                    if (hacker.getDeck().getCartasDefesa().get(j).getNome().equals(cartaAtual)) {
                        repetido = true;
                        break;
                    }
                }
                if(!repetido) break; //se nao for repetido ==> carta valida, sai do while
            }
            deck.addDefesa(GerenciadorCartas.catalogoDeDefesa.get(indiceDefesa));
        }

        for(int i = 0; i < 2; i++) {
            int indiceSuporte;
            while(true) {
                indiceSuporte = r.nextInt(GerenciadorCartas.catalogoDeSuporte.size());
                String cartaAtual = GerenciadorCartas.catalogoDeSuporte.get(indiceSuporte).getNome();

                boolean repetido = false;
                for(int j = 0; j < hacker.getDeck().getCartasSuporte().size(); j++) {
                    if (hacker.getDeck().getCartasSuporte().get(j).getNome().equals(cartaAtual)) {
                        repetido = true;
                        break;
                    }
                }
                if(!repetido) break; //se nao for repetido ==> carta valida, sai do while
            }
            deck.addSuporte(GerenciadorCartas.catalogoDeSuporte.get(indiceSuporte));
        }
        Deck copia = hacker.getDeck().copiaDeck();
        hacker.setDeckCopia(copia);
    }

    public static boolean verificarFimDeJogo(Hacker h1, Hacker h2) {
        if (h1.getHp() <= 0) {
            System.out.println("\n====================");
            System.out.println("☠ O SISTEMA DE " + h1.getNome() + "FOI INVADIDO! ☠");
            System.out.println("🏅 VENCEDOR " + h2.getNome());
            System.out.println("====================");
            return true;
        }
        if (h2.getHp() <= 0) {
            System.out.println("\n====================");
            System.out.println("☠ O SISTEMA DE " + h2.getNome().toUpperCase(Locale.ROOT) + " FOI INVADIDO! ☠");
            System.out.println("🏅 VENCEDOR " + h1.getNome());
            System.out.println("====================");
            return true;
        }
        return false;
    }

}
