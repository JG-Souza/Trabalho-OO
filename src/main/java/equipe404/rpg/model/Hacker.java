package equipe404.rpg.model;

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
}
