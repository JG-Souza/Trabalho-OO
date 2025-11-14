package equipe404.rpg.model;

public abstract class Carta {
    private String nome;
    private String tipo;
    private int custo;
    private String descricao;

    public Carta(String nome, String tipo, int custo, String descricao) {
        this.nome = nome;
        this.tipo = tipo;
        this.custo = custo;
        this.descricao = descricao;
    }

    public String getNome() { return this.nome; }
    public String getTipo() { return this.tipo; }
    public int getCusto() { return this.custo; }
    public String getDescricao() { return this.descricao; }
}
