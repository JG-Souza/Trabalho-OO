package equipe404.rpg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hacker {
    private String nome;
    private String matricula;
    private int hp;
    private int mana;
    private Deck deck;
    private Deck deckCopia;
    private double ataqueTurno;
    private double defesaTurno;
    private double efeitoNegativoTurno;

    public Hacker(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.hp = 100;
        this.mana = 10;
        this.deck = new Deck();
    }

    public String getNome() {return this.nome;}

    public String getMatricula() {return this.matricula;}

    public int getHp() {return this.hp;}

    public void setHp (int novoHp) {
        this.hp = novoHp;
    }

    public double getAtaqueTurno() {return this.ataqueTurno;};
    public double getDefesaTurno() {return this.defesaTurno;};
    public double getEfeitoNegativoTurno() { return this.efeitoNegativoTurno; };

    public void setAtaqueTurno (double novoAtaqueTurno) {
        this.ataqueTurno = novoAtaqueTurno;
    }

    public void setDefesaTurno (double novaDefesaTurno) {
        this.defesaTurno = novaDefesaTurno;
    }

    public void setEfeitoNegativoTurno (double novoEfeitoNegativoTurno) {
        this.efeitoNegativoTurno = novoEfeitoNegativoTurno;
    }

    public int getMana() {return this.mana;}

    public void setMana(int newMana) {
        if (newMana > 10) {
            this.mana = 10;
        } else {
            this.mana = newMana;
        }
    }

    public Deck getDeck() {return this.deck;}

    public void setDeckCopia(Deck deck) {
        this.deckCopia = deck;
    }

    public void imprime() {
        System.out.println("Este é o Hacker " + this.nome);
    }

    //O usuario devera visualizar suas cartas e seus custos
    //Caso selecione uma carta que não tenha energia imprimir "Mana insuficiente"
    //Ter a opção de passar a vez e a opção de entregar o sistema
    //Descartar as cartas escolhidas do deck do jogador
    //Apos a escolha o metodo retornará uma lista de cartas selecionadass

    public List<Carta> jogarCarta(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Estado do hacker " + this.getNome() + " ===");
        System.out.println("HP: " + this.getHp() + " | " + "ENERGIA: " + this.getMana() + "\n");

        System.out.println("=== Suas cartas ===");
        List<Carta> cartasDisponiveis = deck.montaDeckCompleto();

        if (cartasDisponiveis.size() == 0){
            System.out.println("--- Deck vazio! Repondo deck... ---");

            this.deck = this.deckCopia.copiaDeck();
            //recarrega as cartas disponiveis
            cartasDisponiveis = deck.montaDeckCompleto();
        }

        for(int i = 0; i < this.deck.getTamanhoDeck(); i++) {
            String detalheCartas = "";
            Carta carta = cartasDisponiveis.get(i);

            if (carta instanceof CartaAtaque) {
                detalheCartas = " - Poder: " + ((CartaAtaque) carta).getPoderAtaque();
            } else if (carta instanceof CartaDefesa) {
                detalheCartas = " - Poder: " + ((CartaDefesa) carta).getPoderDefesa();
            } else if (carta instanceof CartaSuporte) {
                CartaSuporte c = (CartaSuporte) carta;

                if (c.getEfeito().equals("AUMENTA_ATAQUE")) {
                    detalheCartas = " - Efeito: +" + c.getPoderModificador() + " Ataque";
                } else if (c.getEfeito().equals("DIMINUI_ATAQUE")) {
                    detalheCartas = " - Efeito: -" + c.getPoderModificador() + " Ataque";
                } else if (c.getEfeito().equals("AUMENTA_VIDA")) {
                    detalheCartas = " - Efeito: +" + c.getPoderModificador() + " Vida";
                }
            }
            System.out.println((i + 1) + ". " + carta.getNome() + " - Custo: " +
                    carta.getCusto() + " - Tipo: " + carta.getTipo() + detalheCartas);
        }

        boolean passarVez = false;
        List<Carta> cartasEscolhidas = new ArrayList<>();

        while(!passarVez){
            System.out.println("\n               --- Escolha sua carta. ---\nPara passar a vez digite 0 | Para encerrar" +
                    " o sistema digite -1 ");
            int energiaRestante = this.getMana();
            //pede o usuario para escolher a carta
            int indiceEscolhido;
            if (scanner.hasNextInt()) {
                indiceEscolhido = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada inválida. Por favor, digite o número do índice. Tente novamente.");
                //limpa a entrada se não da loop infinito
                scanner.nextLine();
                continue;
            }
            //se escolher 0 passa a vez e vai sair do while
            if (indiceEscolhido == 0){
                passarVez = true;
                continue;
            }

            if(indiceEscolhido == -1){
                this.setHp(0);
                return null;
            }

            //se escolheu indice inválido
            if (indiceEscolhido < 1 || indiceEscolhido > cartasDisponiveis.size()){
                System.out.println("Opção inválida. Digite um valor válido!");
                continue;
            }

            Carta cartaSelecionada = cartasDisponiveis.get(indiceEscolhido - 1);

            boolean repetido = false;

            //verifica se a cara selecionada é igual a alguma ja escolhida antes
            for(int i = 0; i < cartasEscolhidas.size(); i++){
                if (cartaSelecionada == cartasEscolhidas.get(i)){
                    repetido = true;
                    break;
                }
            }
            if(repetido){
                System.out.println("CARTA JA SELECIONADA! Escolha novamente!");
                continue;
            }

            //se escolher a carta certa vai custar a energia e adicionar as cartas escolhidas
            if(energiaRestante == 0) {
                System.out.println("Mana insuficiente para uma nova jogada! Passando a vez para o outro jogador.");
                passarVez = true;
                continue;
            }
            else if (cartaSelecionada.getCusto() <= this.getMana()){
                cartasEscolhidas.add(cartaSelecionada);
                energiaRestante = this.getMana() - cartaSelecionada.getCusto();

                String detalhe = "";

                if (cartaSelecionada instanceof CartaAtaque) {
                    CartaAtaque c = (CartaAtaque) cartaSelecionada;
                    detalhe = "ATAQUE: " + c.getPoderAtaque();
                }

                else if (cartaSelecionada instanceof CartaDefesa) {
                    CartaDefesa c = (CartaDefesa) cartaSelecionada;
                    detalhe = "DEFESA: " + c.getPoderDefesa();
                }

                else if (cartaSelecionada instanceof CartaSuporte) {
                    CartaSuporte c = (CartaSuporte) cartaSelecionada;
                    if (c.getEfeito().equals("AUMENTA_ATAQUE")) {
                        detalhe = "AUMENTA " + c.getPoderModificador() + " NO ATAQUE DO JOGADOR";
                    }
                    else if (c.getEfeito().equals("DIMINUI_ATAQUE")) {
                        detalhe = "DIMINUI " + c.getPoderModificador() + " NO ATAQUE DO JOGADOR";
                    }
                    else if (c.getEfeito().equals("AUMENTA_VIDA")) {
                        detalhe = "AUMENTA " + c.getPoderModificador() + " NA VIDA DO JOGADOR";
                    }
                }

                System.out.println("=============================");
                System.out.println("✔ Carta selecionada: " + cartaSelecionada.getNome());
                System.out.println("  [" + detalhe + "]");
                System.out.println("  Energia restante: " + energiaRestante);
                System.out.println("=============================");



                this.setMana(energiaRestante);
            } else {
                System.out.println("Mana insuficiente!");
            }
        }
        deck.removeCarta(cartasEscolhidas);
        this.setMana(this.getMana()+1);
        return cartasEscolhidas;
    }

    public List<Carta> jogarCartaBot () {
        System.out.println("\n=== Estado do BOT " + " ===");
        System.out.println("HP: " + this.getHp() + " | " + "ENERGIA: " + this.getMana() + "\n");

        List<Carta> cartasDisponiveis = deck.montaDeckCompleto();

        if (cartasDisponiveis.size() == 0){
            System.out.println("--- Deck vazio! Repondo deck... ---");

            this.deck = this.deckCopia.copiaDeck();
            //recarrega as cartas disponiveis
            cartasDisponiveis = deck.montaDeckCompleto();
        }

        System.out.println("=== Cartas do BOT ===");
        for (int i = 0; i < cartasDisponiveis.size(); i++) {
            Carta c = cartasDisponiveis.get(i);
            System.out.println((i + 1) + ". " + c.getNome() + " - Custo: " + c.getCusto() + " - Tipo: " + c.getTipo());
        }

        List<Carta> cartasEscolhidas = new ArrayList<>();
        int energiaRestante = this.getMana();

        while (energiaRestante > 0 || cartasEscolhidas.size() < 2) {

            boolean passarVez = false;
            for(int j = 0; j < cartasDisponiveis.size(); j++){
                if(cartasDisponiveis.get(j).getCusto() < energiaRestante){
                    passarVez = true;
                    break;
                }
            }
            if (!passarVez){
                break;
            }
            Random r = new Random();

            int indice = r.nextInt(cartasDisponiveis.size());

            Carta cartaSelecionada = cartasDisponiveis.get(indice);

            boolean repetido = false;

            //verifica se a cara selecionada é igual a alguma ja escolhida antes
            for(int i = 0; i < cartasEscolhidas.size(); i++){
                if (cartaSelecionada == cartasEscolhidas.get(i)){
                    repetido = true;
                    break;
                }
            }
            if(repetido) continue;

            if (cartaSelecionada.getCusto() > energiaRestante) continue;

            cartasEscolhidas.add(cartaSelecionada);
            energiaRestante = this.getMana() - cartaSelecionada.getCusto();
            this.setMana(energiaRestante);

            String detalhe = "";

            if (cartaSelecionada instanceof CartaAtaque) {
                CartaAtaque c = (CartaAtaque) cartaSelecionada;
                detalhe = "ATAQUE: " + c.getPoderAtaque();
            }

            else if (cartaSelecionada instanceof CartaDefesa) {
                CartaDefesa c = (CartaDefesa) cartaSelecionada;
                detalhe = "DEFESA: " + c.getPoderDefesa();
            }

            else if (cartaSelecionada instanceof CartaSuporte) {
                CartaSuporte c = (CartaSuporte) cartaSelecionada;
                if (c.getEfeito().equals("AUMENTA_ATAQUE")) {
                    detalhe = "AUMENTA " + c.getPoderModificador() + " NO ATAQUE DO JOGADOR";
                }
                else if (c.getEfeito().equals("DIMINUI_ATAQUE")) {
                    detalhe = "DIMINUI " + c.getPoderModificador() + " NO ATAQUE DO JOGADOR";
                }
                else if (c.getEfeito().equals("AUMENTA_VIDA")) {
                    detalhe = "AUMENTA " + c.getPoderModificador() + " NA VIDA DO JOGADOR";
                }
            }

            System.out.println("=============================");
            System.out.println("✔ Carta selecionada: " + cartaSelecionada.getNome());
            System.out.println("  [" + detalhe + "]");
            System.out.println("  Energia restante: " + energiaRestante);
            System.out.println("=============================");
        }
        deck.removeCarta(cartasEscolhidas);
        this.setMana(this.getMana() + 1);
        return cartasEscolhidas;
    }

}
