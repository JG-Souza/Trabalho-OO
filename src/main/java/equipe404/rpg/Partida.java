package equipe404.rpg;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import equipe404.rpg.model.*;

public class Partida {
    // Atributos
    private Hacker hacker1;
    private Hacker hacker2;
    private boolean h1Comeca;

    public Partida(Hacker hacker1, Hacker hacker2) {
        this.hacker1 = hacker1;
        this.hacker2 = hacker2;
        this.h1Comeca = true;
    }


    public void aplicarEfeitos (Hacker jogador, Hacker oponente, List <Carta> cartasJogadas){
        double ataqueTotal = 0;
        double defesaTotal = 0;
        double poderSuporte = 0;


        for (Carta c:  cartasJogadas) {
            if (c instanceof CartaAtaque) {
                CartaAtaque atk = (CartaAtaque) c;
                ataqueTotal += atk.getPoderAtaque();
            } else if (c instanceof CartaDefesa) {
                CartaDefesa def = (CartaDefesa) c;
                defesaTotal += def.getPoderDefesa();
            }
        }

        for(Carta c: cartasJogadas) {

             if (c instanceof CartaSuporte) {
                CartaSuporte sup = (CartaSuporte) c;
                poderSuporte = sup.getPoderModificador();

                if (sup.getEfeito().equals("AUMENTA_ATAQUE")) ataqueTotal += poderSuporte;
                if (sup.getEfeito().equals("DIMINUI_ATAQUE")) ataqueTotal -= poderSuporte;
                if (sup.getEfeito().equals("AUMENTA_VIDA")) {
                    //pode passar de 100 temporariamente no turno quando jogar essa carta de aumentar vida
                    int novoHp = jogador.getHp() + (int)poderSuporte;
                    jogador.setHp(novoHp);
                }
            }
        }

        double danoFinal = ataqueTotal - defesaTotal;

        oponente.setHp(oponente.getHp() - (int)danoFinal);

        System.out.println("\n>>> RESULTADOS DO TURNO");
        System.out.println("ATAQUE TOTAL: " + ataqueTotal);
        System.out.println("DEFESA TOTAL: " + defesaTotal);
        System.out.println("DANO TOTAL: " + danoFinal);
        System.out.println("HP RESTANTE DE " + oponente.getNome() + ": " + oponente.getHp());
    }

    public void consolidarVida (Hacker h) {
        int hp = h.getHp();

        if (hp > 100) hp = 100;
        if (hp < 0) hp = 0;

        int arredondarMaisProximo = hp % 10;

        if (arredondarMaisProximo < 5) hp -= arredondarMaisProximo;
        else hp = hp + (10 - arredondarMaisProximo);

        h.setHp(hp);
    }

    public void iniciar() {
        Scanner scannerDuelo = new Scanner(System.in);

        System.out.println("\n====== A PARTIDA VAI COMEÇAR! ======");
        System.out.println(hacker1.getNome() + " VS " + hacker2.getNome());
        System.out.println("=====================================");

        while (hacker1.getHp() > 0 && hacker2.getHp() > 0) {
            System.out.println("--- NOVO TURNO ---");

            Hacker primeiroAJogar = (h1Comeca) ? hacker1 : hacker2;
            Hacker segundoAJogar = (h1Comeca) ? hacker2 : hacker1;

            System.out.println("É a vez de: " + primeiroAJogar.getNome());
            List <Carta> jogadasDoPrimeiro = primeiroAJogar.jogarCarta();

            // Evita que o segundo jogador ataque depois de morto
            List <Carta> jogadasDoSegundo = new ArrayList<>();
            if (segundoAJogar.getHp() > 0) {
                System.out.println("É a vez de: " + segundoAJogar.getNome());
                jogadasDoSegundo = segundoAJogar.jogarCarta();
            }

            // Calcular dano, cura, etc.
            aplicarEfeitos(primeiroAJogar, segundoAJogar, jogadasDoPrimeiro);
            aplicarEfeitos(segundoAJogar, primeiroAJogar, jogadasDoSegundo);
            // recarregar energia, arredondar vida, etc.
            consolidarVida(primeiroAJogar);
            consolidarVida(segundoAJogar);

            h1Comeca = !h1Comeca;
        }
        // Implementar lógica de finalizar partida
    }
}