package equipe404.rpg.model;

import java.util.List;

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

        System.out.println("=== Suas cartas ===");
        for(int i = 0; i < this.deck.getTamanhoDeck(); i++){

        }
    }


}
