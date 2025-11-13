package equipe404.rpg.model;

public class Carta {
    private String nome;
    private String tipo;
    private int poder;
    private int custo;
    private String descricao;

    public Carta(String nome, String tipo, int poder, int custo, String descricao) {
        this.nome = nome;
        this.tipo = tipo;
        this.poder = poder;
        this.custo = custo;
        this.descricao = descricao;
    }

    public String getNome() {
        return this.nome;
    }
}
