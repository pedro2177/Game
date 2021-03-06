package br.com.game.entidades;

import br.com.game.sistema.Localizacao;
import br.com.game.start.Game;

import javax.swing.*;
import java.awt.*;

public class Lancador {

    private Tiro tiro;
    private Carregador carregador = new Carregador();
    public ImageIcon imageLancador =  new ImageIcon("src/main/resources/sprites/Lançador.png");
    int disparos;
    public ImageIcon getImageLancador() {
        return imageLancador;
    }

    public void setImageLancador(ImageIcon imageLancador) {
        this.imageLancador = imageLancador;
    }

    public void verificaAlvo(AlvoMovel alvoMovel, Graphics bbg){
        if(alvoMovel != null){
            carregar();
            preparar(alvoMovel);
            atirar(bbg);
        }
    }

    public void carregar(){
        if(carregador.getMunicao() == null){
            tiro = new Tiro();
            tiro.setMunicao(new Municao(disparos+1));
            tiro.setIdentificacao(disparos+1);
            disparos += 1;
        }
    }
    public void preparar(AlvoMovel alvoMovel){
        if (alvoMovel != null) {
            this.tiro.setPontoDestino(calcularTrajetoria(alvoMovel, this.tiro));
        }
    }

    //Dispara o tiro
    public void atirar(Graphics bbg){
        if(tiro.getLocalizacaoAtual() == null){
            tiro.setLocalizacaoAtual(tiro.getPontoOrigem());
        }
        if(tiro.getLocalizacaoAtual().getVarX()
                <= 500 && tiro.getLocalizacaoAtual().getVarX()
                >= 0 && tiro.getLocalizacaoAtual().getVarY()
                <= 500 && tiro.getLocalizacaoAtual().getVarY() >= 0){
            try {
                tiro.setLocalizacaoAtual(
                        Game.geraTrajetoria(
                                tiro.getPontoOrigem(),tiro.getPontoDestino()));
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Não converteu");
            }
            bbg.drawImage(tiro.getImageTiro().getImage(), tiro.getLocalizacaoAtual().getVarX(),
                    tiro.getLocalizacaoAtual().getVarY(), null);
            bbg.drawImage(Game.backBuffer, 0, 0, null);
        }
    }
    //Calcula a localização final onde o alvo e o tiro irão se encontrar.

    public Localizacao calcularTrajetoria(AlvoMovel alvoMovel, Tiro tiro){
        // CALCULA O TRIANGULO RETANGULO TOTAL
        // distancia do pontoOrigem do tiro até o eixo x 80
        int catetoOposto = tiro.getPontoOrigem().getVarX() - alvoMovel.getPontoOrigem().getVarX();
        // distancia do final da visão do lancador até o y 0
        int catetoAdjascente = tiro.getPontoOrigem().getVarY();
        // distancia reta entre a saída do alvo e a saída do tiro
        double hipotenusa = Math.sqrt(Math.pow(catetoAdjascente,2) + Math.pow(catetoOposto,2));
        // tangente do grau
        double tan = ((double) catetoOposto/ (double) catetoAdjascente);
        double tangente = Math.tanh(tan);

        // CALCULA O TRIANGULO EQUILÁTERO PARA PEGAR A DISTANCIA A PERCORRER
        double hipotenusa2 = (hipotenusa/2);
        double cttOposto = hipotenusa2 * tangente;

        double hip = Math.sqrt((Math.pow(cttOposto,2)+Math.pow(hipotenusa2,2)));

        // CALCULA COM BASE NAS DIMENSÕES DAS ENTIDADES.
        int iconH = tiro.getImageTiro().getIconHeight()/2;
        hip -= iconH;
        Localizacao loca = new Localizacao(alvoMovel.getPontoOrigem().getVarX(), (int) Math.round(hip));
        return loca;
    }
    public Tiro getTiro(){
        return this.tiro;
    }
}
