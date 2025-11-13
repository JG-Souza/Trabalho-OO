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
    }

    public void imprime() {
        System.out.println("Este é o Hacker " + this.nome);
    }
}
