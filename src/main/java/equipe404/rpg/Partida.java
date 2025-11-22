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
    private GerenciadorReplay replay;

    public Partida(Hacker hacker1, Hacker hacker2, GerenciadorReplay replay) {
        this.hacker1 = hacker1;
        this.hacker2 = hacker2;
        this.h1Comeca = true;
        this.replay = replay;
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

                if (sup.getEfeito().equals("AUMENTA_ATAQUE")){
                    poderSuporteTotal += poderSuporte;
                    replay.registrar(jogador.getNome() + " ativou suporte: Aumenta ataque em " + poderSuporte);
                }
                if (sup.getEfeito().equals("DIMINUI_ATAQUE")){
                    efeitoNegativo += poderSuporte;
                    replay.registrar(jogador.getNome() + " ativou suporte: Diminui ataque do oponente em " + poderSuporte);
                }
                if (sup.getEfeito().equals("AUMENTA_VIDA")) {
                    //pode passar de 100 temporariamente no turno quando jogar essa carta de aumentar vida
                    int novoHp = jogador.getHp() + (int)poderSuporte;
                    jogador.setHp(novoHp);
                    replay.registrar(jogador.getNome() + " recuperou " + (int)poderSuporte + " de vida.");
                }
            }
        }

        if (poderSuporteTotal > 0) {
            ataqueTotal *= (1 + poderSuporteTotal);
            replay.registrar("Bônus de ataque aplicado: +" + (poderSuporteTotal * 100) + "%");
        }

        double debuffSofrido = jogador.getEfeitoNegativoTurno();

        if (debuffSofrido > 0) {
            ataqueTotal *= (1 - debuffSofrido);
            replay.registrar("Debuff sofrido: Ataque reduzido em " + (debuffSofrido * 100) + "%");
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

        replay.registrar("--- Resultado do turno " + jogador.getNome() + " ---");
        replay.registrar("Ataque Final: " + ataqueTotal + " | Defesa Final: " + defesaTotal);
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

        replay.registrar("==========================================");
        replay.registrar(" INÍCIO DA PARTIDA: " + hacker1.getNome() + " vs " + hacker2.getNome());
        replay.registrar("==========================================");

        while (hacker1.getHp() > 0 && hacker2.getHp() > 0) {
            System.out.println("--- NOVO TURNO ---");
            replay.registrar("\n--- TURNO ---");
            Hacker primeiroAJogar = (h1Comeca) ? hacker1 : hacker2;
            Hacker segundoAJogar = (h1Comeca) ? hacker2 : hacker1;

            System.out.println("\nÉ a vez de: " + primeiroAJogar.getNome());
            replay.registrar("\nÉ a vez de: ");
            List <Carta> jogadasDoPrimeiro = (primeiroAJogar.getNome().equals("BOT")) ? primeiroAJogar.jogarCartaBot(replay) : primeiroAJogar.jogarCarta(replay);

            if(jogadasDoPrimeiro == null){
                System.out.println("\nO jogador " + this.hacker1.getNome() + " entregou o sistema!\n" +
                        "=== O JOGADOR " + this.hacker2.getNome() + " VENCEU A PARTIDA! ===\n");

                replay.registrar("\nO jogador " + this.hacker1.getNome() + " entregou o sistema!\n" +
                        "=== O JOGADOR " + this.hacker2.getNome() + " VENCEU A PARTIDA! ===\n");
                break;
            }

            // Evita que o segundo jogador ataque depois de morto
            List <Carta> jogadasDoSegundo = new ArrayList<>();
            if (segundoAJogar.getHp() > 0) {
                System.out.println("\nÉ a vez de: " + segundoAJogar.getNome());
                jogadasDoSegundo = (segundoAJogar.getNome().equals("BOT")) ? segundoAJogar.jogarCartaBot(replay) : segundoAJogar.jogarCarta(replay);

                if(jogadasDoSegundo == null){
                    System.out.println("\nO jogador " + this.hacker2.getNome() + " entregou o sistema!\n" +
                            "=== O JOGADOR " + this.hacker1.getNome() + " VENCEU A PARTIDA! ===\n");

                    replay.registrar("\nO jogador " + this.hacker2.getNome() + " entregou o sistema!\n" +
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

            replay.registrar("RESULTADO TURNO:");
            replay.registrar(primeiroAJogar.getNome() + " causou " + danoEm2 + " de dano.");
            replay.registrar(segundoAJogar.getNome() + " causou " + danoEm1 + " de dano.");
            replay.registrar("STATUS ATUAL -> " + hacker1.getNome() + ": " + hacker1.getHp() + " HP (" + hacker1.getMana() + " Mana) | " +
                    hacker2.getNome() + ": " + hacker2.getHp() + " HP (" + hacker2.getMana() + " Mana)");

            h1Comeca = !h1Comeca;
        }
        // Implementar lógica de finalizar partida
    }
}