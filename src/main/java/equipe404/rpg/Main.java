package equipe404.rpg;

import equipe404.rpg.model.Deck;
import equipe404.rpg.model.Hacker;

import java.util.List;
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
        GerenciadorReplay replay = new GerenciadorReplay();
        System.out.println("\n=== Inicio do Jogo Cyber Duel ===\n");
        System.out.println("Bem-vindo ao ciberespaço, o novo campo de batalha das megacorporações. O combate físico " +
                "acabou!\nAgora, o poder é disputado através de duelos estratégicos entre os hackers mais habilidosos " +
                "do planeta.\n");
        System.out.println("--- REGRAS ---\n1. Escolha o modo de jogo (Versus humano ou bot)\n" +
                "2. Escolha o Deck (Manual ou Aleatório)\n" +
                "3. Escolha as Cartas que vão ser utilizadas na rodada\n" +
                "4. O jogo termina se sua Vida chegar a zero\n");
        replay.registrar("=== Inicio do Jogo Cyber Duel ===");

        while (true) {
            System.out.println("A partida será entre:");
            System.out.println("1. Humano x Humano");
            System.out.println("2. Humano x Bot");
            System.out.println("3. Sair do Jogo");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            // Humano x Humano
            if (escolha.equals("1")) {
                replay.registrar("A partida será entre Humano x Humano");
                // Cria os jogadores
                Hacker h1 = criarJogadorHumano(scanner, 1);
                Hacker h2 = criarJogadorHumano(scanner, 2);

                replay.registrar("=== Jogador 1 ===\nNome: " + h1.getNome() + "\n\n=== Jogador 2 ===\nNome: " + h2.getNome());

                System.out.println("\n" + h1.getNome() + " escolha sua opção de montagem de deck:");
                System.out.println("1. Montar deck manualmente");
                System.out.println("2. Usar deck aleatório");
                System.out.print("Escolha uma opção: ");
                String opcaoDeck1 = scanner.nextLine();

                replay.registrar("\n--- Escolhas de decks ---");

                replay.registrar("\nEscolhas jogador 1");
                // Eles escolhem as cartas
                if (opcaoDeck1.equals("1")){
                    replay.registrar("\nO jogador "  + h1.getNome() + " optou montar o deck manualmente");
                    escolherCartas(h1, replay);
                } else if (opcaoDeck1.equals("2")){
                    replay.registrar("O jogador "  + h1.getNome() + " optou usar um deck aleatório");
                    escolherCartasAleatorio(h1,replay);
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }

                replay.registrar("\nEscolhas jogador 2");

                System.out.println("\n" + h2.getNome() + " escolha sua opção de montagem de deck:");
                System.out.println("1. Montar deck manualmente");
                System.out.println("2. Usar deck aleatório");
                System.out.print("Escolha uma opção: ");
                String opcaoDeck2 = scanner.nextLine();
                if(opcaoDeck2.equals("1")) {
                    replay.registrar("\nO jogador "  + h2.getNome() + " optou montar o deck manualmente");
                    escolherCartas(h2, replay);
                } else if (opcaoDeck2.equals("2")){
                    replay.registrar("\nO jogador "  + h2.getNome() + " optou usar um deck aleatório");
                    escolherCartasAleatorio(h2,replay);
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }

                // Cria e inicia a partida
                Partida novaPartida = new Partida(h1, h2, replay);
                novaPartida.iniciar();

                verificarFimDeJogo(h1,h2,replay);

                System.out.println("\n------------------------------------------------");
                System.out.println("Fim de jogo! Deseja salvar o arquivo de Replay? (S/N)");
                String opcaoReplay = scanner.nextLine();

                if (opcaoReplay.equalsIgnoreCase("S")) {
                    replay.registrar("Usuário optou por salvar o replay.");
                    replay.gerarArquivoReplay();
                } else {
                    System.out.println("Replay descartado.");
                }
                break;

            } else if (escolha.equals("2")) {
                replay.registrar("A partida será entre Humano x BOT");
                Hacker h1 = criarJogadorHumano(scanner, 1);
                Hacker h2 = criarJogadorBot(2);

                replay.registrar("=== Jogador 1 ===\nNome: " + h1.getNome() + "\n\n=== Jogador 2 ===\nNome: " + h2.getNome());

                replay.registrar("\n--- Escolhas de decks ---");

                replay.registrar("\nEscolhas jogador 1");

                System.out.println("\n" + h1.getNome() + " escolha sua opção de montagem de deck:");
                System.out.println("1. Montar deck manualmente");
                System.out.println("2. Usar deck aleatório");
                System.out.print("Escolha uma opção: ");
                String opcaoDeck1 = scanner.nextLine();

                // Eles escolhem as cartas
                if (opcaoDeck1.equals("1")){
                    replay.registrar("O jogador "  + h1.getNome() + " montou o deck manualmente");
                    escolherCartas(h1, replay);

                } else if (opcaoDeck1.equals("2")){
                    replay.registrar("O jogador "  + h1.getNome() + " usou um deck aleatório");
                    escolherCartasAleatorio(h1,replay);
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }

                replay.registrar("\nEscolhas do bot");
                replay.registrar("BOT gerou deck aleatório.");
                escolherCartasAleatorio(h2,replay);

                Partida novaPartida = new Partida(h1, h2, replay);
                novaPartida.iniciar();

                verificarFimDeJogo(h1,h2,replay);

                System.out.println("\n------------------------------------------------");
                System.out.println("Fim de jogo! Deseja salvar o arquivo de Replay? (S/N)");
                String opcaoReplay = scanner.nextLine();

                if (opcaoReplay.equalsIgnoreCase("S")) {
                    replay.registrar("Usuário optou por salvar o replay.");
                    replay.gerarArquivoReplay();
                } else {
                    System.out.println("Replay descartado.");
                }
                break;

            } else if (escolha.equals("3")) {
                replay.registrar("O jogador saiu do jogo");
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


    //tem que ter hacker como parametro
    public static void escolherCartas(Hacker hacker, GerenciadorReplay replay) {
        Scanner scanner = new Scanner(System.in);

        //cria o deck inicial vazio
        Deck deck = hacker.getDeck();

        //exibir as cartas pra serem selecionadas

        //seleção das cartas
        //fazer tambem a verificação pra ver se os indices estão corretos
        // se nao estiverem, pedir para escreverem de novo

        System.out.println("\n=== Escolha 4 Cartas de ATAQUE ===");
        replay.registrar("\n=== " + hacker.getNome() + " escolha de cartas de ATAQUE ===");
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
                replay.registrar("Carta escolhida: " + GerenciadorCartas.catalogoDeAtaque.get(indiceCarta -1).
                        getNome());
                break;
            }
            //adicionar as cartas no deck
            deck.addAtaque(GerenciadorCartas.catalogoDeAtaque.get(indiceCarta - 1));
        }

        System.out.println("\n=== Escolha 4 Cartas de DEFESA ===");
        replay.registrar("\n=== " + hacker.getNome() + " escolha de cartas de DEFESA ===");
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
                replay.registrar("Carta escolhida: " + GerenciadorCartas.catalogoDeDefesa.get(indiceCarta -1).
                        getNome());
                break;
            }
            //adicionar as cartas no deck
            deck.addDefesa(GerenciadorCartas.catalogoDeDefesa.get(indiceCarta - 1));
        }

        System.out.println("\n=== Escolha 2 Cartas de SUPORTE ===");
        replay.registrar("\n=== " + hacker.getNome() + " escolha de cartas de SUPORTE ===");
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
                replay.registrar("Carta escolhida: " + GerenciadorCartas.catalogoDeSuporte.get(indiceCarta -1).
                        getNome());
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

    public static void escolherCartasAleatorio(Hacker hacker, GerenciadorReplay replay) {

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
            replay.registrar("Carta aleatória escolhida: " + GerenciadorCartas.catalogoDeAtaque.get(indiceAtaque).
                    getNome());
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
            replay.registrar("Carta aleatória escolhida: " + GerenciadorCartas.catalogoDeDefesa.get(indiceDefesa).
                    getNome());
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
            replay.registrar("Carta aleatória escolhida: " + GerenciadorCartas.catalogoDeDefesa.get(indiceSuporte).
                    getNome());
            deck.addSuporte(GerenciadorCartas.catalogoDeSuporte.get(indiceSuporte));
        }
        Deck copia = hacker.getDeck().copiaDeck();
        hacker.setDeckCopia(copia);
    }

    public static boolean verificarFimDeJogo(Hacker h1, Hacker h2, GerenciadorReplay replay) {
        if (h1.getHp() <= 0) {
            System.out.println("\n============================");
            replay.registrar("\n============================");
            mensagemFinal(h1.getNome(),replay);
            System.out.println("🏅 VENCEDOR " + h2.getNome());
            System.out.println("============================");
            replay.registrar("🏅 VENCEDOR " + h2.getNome());
            replay.registrar("============================");
            return true;
        }
        if (h2.getHp() <= 0) {
            System.out.println("\n============================");
            replay.registrar("\n============================");
            mensagemFinal(h2.getNome(),replay);
            System.out.println("🏅 VENCEDOR " + h1.getNome());
            System.out.println("============================");
            replay.registrar("🏅 VENCEDOR " + h1.getNome());
            replay.registrar("============================");
            return true;
        }
        return false;
    }

    public static void mensagemFinal(String nomeHacker, GerenciadorReplay replay){
        Random r = new Random();
        int indice = r.nextInt(7);

        if(indice == 1){
            System.out.println("☠ O SISTEMA DE " + nomeHacker + " FOI INVADIDO! ☠");
            replay.registrar("☠ O SISTEMA DE " + nomeHacker + " FOI INVADIDO! ☠");
        } else if (indice == 2) {
            System.out.println("☠ A SENHA ERA 12345! " + nomeHacker + " FOI BANIDO DA REDE. ☠");
            replay.registrar("☠ A SENHA ERA 12345! " + nomeHacker + " FOI BANIDO DA REDE. ☠");
        } else if (indice == 3) {
            System.out.println("☠ VÍRUS INSTALADO COM SUCESSO! " + nomeHacker + " AGORA ESTÁ PRESO EM UM LOOP INFINITO!! ☠");
            replay.registrar("☠ VÍRUS INSTALADO COM SUCESSO! " + nomeHacker + " AGORA ESTÁ PRESO EM UM LOOP INFINITO!! ☠");
        } else if (indice == 4) {
            System.out.println("☠ ERRO 404: " + nomeHacker + " NÃO ENCONTRADO NA REDE! ☠");
            replay.registrar("☠ ERRO 404: " + nomeHacker + " NÃO ENCONTRADO NA REDE! ☠");
        } else if (indice == 5) {
            System.out.println("☠ PELO VISTO A INTERNET DE " + nomeHacker + " ERA DE ESCADA! ☠");
            replay.registrar("☠ PELO VISTO A INTERNET DE " + nomeHacker + " ERA DE ESCADA! ☠");
        } else if (indice == 6) {
            System.out.println("☠ A ESTRATÉGIA DE " + nomeHacker + " ERA FRACA. AGORA SÓ RODA JOGO DA COBRINHA! ☠");
            replay.registrar("☠ A ESTRATÉGIA DE " + nomeHacker + " ERA FRACA. AGORA SÓ RODA JOGO DA COBRINHA! ☠");
        } else {
            System.out.println("☠ ESSA FOI FÁCIL " + nomeHacker + ". MELHORE SEUS FIREWALLS DA PRÓXIMA VEZ! ☠");
            replay.registrar("☠ ESSA FOI FÁCIL " + nomeHacker + ". MELHORE SEUS FIREWALLS DA PRÓXIMA VEZ! ☠");
        }
    }

}
