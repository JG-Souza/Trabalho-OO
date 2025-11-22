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
        double poderSuporteTotal = 0;
        double efeitoNegativo = 0;


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

                if (sup.getEfeito().equals("AUMENTA_ATAQUE")) poderSuporteTotal += poderSuporte;
                if (sup.getEfeito().equals("DIMINUI_ATAQUE")) efeitoNegativo += poderSuporte;
                if (sup.getEfeito().equals("AUMENTA_VIDA")) {
                    //pode passar de 100 temporariamente no turno quando jogar essa carta de aumentar vida
                    int novoHp = jogador.getHp() + (int)poderSuporte;
                    jogador.setHp(novoHp);
                }
            }
        }

        if (poderSuporteTotal > 0) {
            ataqueTotal *= (1 + poderSuporteTotal);
        }

        double debuffSofrido = jogador.getEfeitoNegativoTurno();

        if (debuffSofrido > 0) {
            ataqueTotal *= (1 - debuffSofrido);
        }

        jogador.setAtaqueTurno(ataqueTotal);
        jogador.setDefesaTurno(defesaTotal);
        if (efeitoNegativo > 0) {
            oponente.setEfeitoNegativoTurno(efeitoNegativo);
        }

        //arredondar ataqueFinal
        ataqueTotal = Math.round(ataqueTotal * 100.0) / 100.0;

        System.out.println("\n>>> RESULTADOS DO TURNO DO " + jogador.getNome());
        System.out.println("ATAQUE TOTAL: " + ataqueTotal);
        System.out.println("DEFESA TOTAL: " + defesaTotal);
        //System.out.println("DANO TOTAL: " + danoFinal);
        //System.out.println("HP RESTANTE DE " + oponente.getNome() + ": " + oponente.getHp());
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

            System.out.println("\nÉ a vez de: " + primeiroAJogar.getNome());
            List <Carta> jogadasDoPrimeiro = (primeiroAJogar.getNome().equals("BOT")) ? primeiroAJogar.jogarCartaBot() : primeiroAJogar.jogarCarta();

            if(jogadasDoPrimeiro == null){
                System.out.println("\nO jogador " + this.hacker1.getNome() + " entregou o sistema!\n" +
                        "=== O JOGADOR " + this.hacker2.getNome() + " VENCEU A PARTIDA! ===\n");
                break;
            }

            // Evita que o segundo jogador ataque depois de morto
            List <Carta> jogadasDoSegundo = new ArrayList<>();
            if (segundoAJogar.getHp() > 0) {
                System.out.println("\nÉ a vez de: " + segundoAJogar.getNome());
                jogadasDoSegundo = (segundoAJogar.getNome().equals("BOT")) ? segundoAJogar.jogarCartaBot() : segundoAJogar.jogarCarta();

                if(jogadasDoSegundo == null){
                    System.out.println("\nO jogador " + this.hacker2.getNome() + " entregou o sistema!\n" +
                            "=== O JOGADOR " + this.hacker1.getNome() + " VENCEU A PARTIDA! ===\n");
                    break;
                }
            }

            // Calcular dano, cura, etc.
            aplicarEfeitos(primeiroAJogar, segundoAJogar, jogadasDoPrimeiro);
            aplicarEfeitos(segundoAJogar, primeiroAJogar, jogadasDoSegundo);


            //calcular dano
            int danoEm2 = (int)(primeiroAJogar.getAtaqueTurno() - segundoAJogar.getDefesaTurno());
            if(danoEm2 < 0) danoEm2 = 0;

            int danoEm1 = (int)(segundoAJogar.getAtaqueTurno() - primeiroAJogar.getDefesaTurno());
            if (danoEm1 < 0) danoEm1 = 0;

            primeiroAJogar.setHp(primeiroAJogar.getHp() - danoEm1);
            segundoAJogar.setHp((segundoAJogar.getHp()) - danoEm2);

            System.out.println("\n===== RESULTADOS DO TURNO =====");
            System.out.println(primeiroAJogar.getNome() + " causou " + danoEm2 + " de dano.");
            System.out.println(segundoAJogar.getNome() + " causou " + danoEm1 + " de dano.");
            System.out.println(primeiroAJogar.getNome() + " HP: " + primeiroAJogar.getHp());
            System.out.println(segundoAJogar.getNome() + " HP: " + segundoAJogar.getHp());

            // recarregar energia, arredondar vida, etc.
            consolidarVida(primeiroAJogar);
            consolidarVida(segundoAJogar);

            h1Comeca = !h1Comeca;
        }
        // Implementar lógica de finalizar partida
    }
}