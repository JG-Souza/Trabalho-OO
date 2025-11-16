package equipe404.rpg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hacker {
    private String nome;
    private String matricula;
    private int hp;
    private int mana;
    private Deck deck;

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

    public int getMana() {return this.mana;}

    public void setMana(int mana) {
        if (mana > 10) {
            this.mana = 10;
        } else {
            this.mana = mana;
        }
    }

    public Deck getDeck() {return this.deck;}



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

        for(int i = 0; i < this.deck.getTamanhoDeck(); i++){
            System.out.println((i + 1) + ". " + cartasDisponiveis.get(i).getNome() + " - Custo: " +
                    cartasDisponiveis.get(i).getCusto() + " - Tipo: " + cartasDisponiveis.get(i).getTipo()) ;
        }

        boolean passarVez = false;
        int energiaGasta = 0;
        List<Carta> cartasEscolhidas = new ArrayList<>();

        while(!passarVez){
            System.out.println("\nEscolha sua carta. Caso queira passar a vez digite 0");

            //pede o usuario para escolher a carta
            int indiceEscolhido;
            indiceEscolhido = scanner.nextInt();

            //se escolher 0 passa a vez e vai sair do while
            if (indiceEscolhido == 0){
                passarVez = true;
                continue;
            }

            int energiaRestante = this.getMana() - energiaGasta;

            //se escolheu indice inválido
            if (indiceEscolhido < 1 || indiceEscolhido > cartasDisponiveis.size()){
                System.out.println("Opção inválida. Digite um valor válido!");
                continue;
            }

            Carta cartaSelecionada = cartasDisponiveis.get(indiceEscolhido - 1);

            //se escolher a carta certa vai custar a energia e adicionar as cartas escolhidas
            if (cartaSelecionada.getCusto() <= energiaRestante){
                cartasEscolhidas.add(cartaSelecionada);
                energiaGasta += cartaSelecionada.getCusto();
                deck.removeCarta(cartaSelecionada);
                System.out.println("Carta '" + indiceEscolhido + "' Selecionada. Energia restante: " + (this.getMana() - energiaGasta));
                this.setMana(this.getMana() - energiaGasta);
            } else {
                System.out.println("Mana insuficiente!");
            }
        }
        this.setMana(this.getMana()+1);
        //ainda falta implementar o metodo de entregar o sistema
        return cartasEscolhidas;
    }


}
