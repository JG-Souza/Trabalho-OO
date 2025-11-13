package equipe404.rpg.model;

public class CartaSuporte {
    private String nome;
    private String efeito;
    private int custoEnergia;
    private double poder;

    public CartaSuporte(String nome, String efeito, int custoEnergia, double poder) {
        this.nome = nome;
        this.efeito = efeito;
        this.custoEnergia = custoEnergia;
        this.poder = poder;
    }

    public String getNome() {return nome;}
    public String getEfeito() {return efeito;}
    public int getCustoEnergia() {return custoEnergia;}
    public double getPoder() {return poder;}

    @Override
    public String toString() {
        return "SUPORTE: " + nome + " | Custo: " + custoEnergia + " | Efeito: " + efeito + " | Valor: " + poder;
    }
}
