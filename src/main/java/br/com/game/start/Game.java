package br.com.game.start;

import br.com.game.entidades.*;
import br.com.game.caminho.Trajeto;
import br.com.game.sistema.Disparo;
import br.com.game.sistema.Localizacao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Game extends JFrame {

    private Lancador lancador = new Lancador();

    private Trajeto trajeto = new Trajeto();
    private AlvoMovel alvoMovel = new AlvoMovel(1);
    private AlvoMovel alvoMovel2;
    static BufferedImage backBuffer;
    static double locomocao = 2.0;
    // Taxa de atualização
    int FPS = 30;
    // Tamanho da janela criada
    int janelaW = 500;
    int janelaH = 500;
    //
    static double pontoY= 440;
    static double pontoX= 215;
    static double contaX= 0;
    static double contaY= 0;
    Disparo disparo = new Disparo();
    static boolean objeto1Colediu = false;

    public void dispararTiro(Tiro tiro, Graphics bbg){
        tiro.setPontoOrigem(new Localizacao(215,440));
        if(tiro.getLocalizacaoAtual() == null){
            tiro.setLocalizacaoAtual(tiro.getPontoOrigem());
        }
        if(tiro.getLocalizacaoAtual().getVarX()
                <= 500 && tiro.getLocalizacaoAtual().getVarX()
                >= 0 && tiro.getLocalizacaoAtual().getVarY()
                <= 500 && tiro.getLocalizacaoAtual().getVarY() >= 0){
            try {
                tiro.setLocalizacaoAtual(Game.geraTrajetoria(tiro.getPontoOrigem(),Game.traj));
            } catch (Exception e){
                System.out.println("Não converteu");
            }
            bbg.drawImage(tiro.getImageTiro().getImage(), tiro.getLocalizacaoAtual().getVarX(),
                    tiro.getLocalizacaoAtual().getVarY(), this);
        }
    }

    public void criarAlvo(){
        alvoMovel.setPontoOrigem(trajeto.getOrigem());
        alvoMovel.setPontoDestino(trajeto.getDestino());
        alvoMovel.setLocalizacaoAtual(trajeto.getOrigem());
        alvoMovel.setTamanho(new Tamanho(50,50));
    }

    //Move o Alvo aumentando seu Y
    public void moveAlvoMovel() {
        if(alvoMovel!= null){
            alvoMovel.setLocalizacaoAtual(new Localizacao(alvoMovel.getPontoOrigem().getVarX(),alvoMovel.getLocalizacaoAtual().getVarY()+2));
            System.out.println(alvoMovel.getLocalizacaoAtual().getVarY());
            if (alvoMovel.getLocalizacaoAtual().getVarY() > 500) {
                alvoMovel.setLocalizacaoAtual(new Localizacao(80, 0));
            }
        }
    }

    // --------------------------------------------------------------------
    // SE O OBJETO COLEDIR APARECERÃ� UM TEXTO INFORMANDO!!!
    // ESSE MÃ‰TODO VAI SER CHAMANDO LÃ� NO DENTRO DO MÃ‰TODO desenharGraficos()
    // --------------------------------------------------------------------
    public void exibeTexto() {
        Graphics bbg = backBuffer.getGraphics();
        if(alvoMovel != null) {
            if (alvoMovel.getChegadaNoDestino()) {
                bbg.setColor(Color.RED);
                bbg.drawString("Você perdeu!", 200, 100);
            }
        }
    }

    public boolean chegou(AlvoMovel alvoMovel){
        if(alvoMovel != null){


        Localizacao loc = alvoMovel.getLocalizacaoAtual();
        if(loc.getVarY() >= 420){
            return true;
        } else {
            return false;
        }
        } else {
            return false;
        }
    }

    // --------------------------------------------------------------------
    public void atualizar() {
        if(alvoMovel != null)
        alvoMovel.setChegadaNoDestino(chegou(alvoMovel));
        moveAlvoMovel();
    }
    public void desenharGraficos() {
            Graphics g = getGraphics(); // ISSO JÃ� ESTAVA AQUI
            Graphics bbg = backBuffer.getGraphics();// ISSO TAMBÃ‰M JÃ� ESTAVA AQUI...
            // ==================================================================================
            bbg.setColor(Color.WHITE);
            bbg.fillRect(0, 0, janelaW, janelaH);// DESENHA UM FUNDO BRANCO NA TELA!

            // EXIBE UM TEXTO CASO O OBJETO COLIDA!
            exibeTexto();
            desenharLancador(bbg);
            desenharAlvoMovel(bbg);
            lancador.disparar(lancador.getTiro(), backBuffer.getGraphics());
            if (alvoMovel != null) {
                traj = lancador.calcularTrajetoria(alvoMovel, lancador.getTiro());
            }
            colidiram(alvoMovel, lancador.getTiro());
            if (teste && lancador.getTiro().getLocalizacaoAtual().getVarX() > 0) {
                bbg.setColor(Color.RED);
                bbg.drawString("Tiro Acertado.", 200, 100);
            }
            // ==================================================================================
            g.drawImage(backBuffer, 0, 0, this);// OBS: ISSO DEVE FICAR SEMPRE NO
            // FINAL!

    }
    public void inicializar() {
        setTitle("Teste Caminho");
        setSize(janelaW, janelaH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        backBuffer = new BufferedImage(janelaW, janelaH,
                BufferedImage.TYPE_INT_RGB);
    }
    public void desenharLancador(Graphics bbg){
        ImageIcon image = lancador.getImageLancador();
        bbg.drawImage(image.getImage(), 215,440,this);
    }
    public void desenharAlvoMovel(Graphics bbg){
        if(alvoMovel != null){
            if(alvoMovel.caminho == 1){
                ImageIcon image = alvoMovel.getImageAlvo();
                bbg.drawImage(image.getImage(), alvoMovel.getLocalizacaoAtual().getVarX(),
                        alvoMovel.getLocalizacaoAtual().getVarY(), this);
            } else if (alvoMovel.caminho == 2){
                ImageIcon image = alvoMovel.getImageAlvo();
                bbg.drawImage(image.getImage(), alvoMovel.getLocalizacaoAtual().getVarX()+300,
                        alvoMovel.getLocalizacaoAtual().getVarY(), this);
            }
        }
    }
    public static Localizacao traj;
    static boolean teste = false;
    public void colidiram(AlvoMovel alvoMovel, Tiro tiro) {
        Graphics bbg = backBuffer.getGraphics();
        objeto1Colediu = colisao(alvoMovel, tiro);
        if (objeto1Colediu) {
            teste = true;
            this.alvoMovel = null;
            bbg.setColor(Color.RED);
            bbg.drawString("Tiro Acertado.", 200, 100);
        }

    }
    public static boolean colisao(AlvoMovel alvoMovel,
                                  Tiro tiro) {
        if(alvoMovel != null) {
            Localizacao loc1 = alvoMovel.getLocalizacaoAtual();
            Localizacao loc2 = tiro.getLocalizacaoAtual();
            ImageIcon image1 = alvoMovel.getImageAlvo();
            ImageIcon image2 = tiro.getImageTiro();
            if ((loc1.getVarX() >= loc2.getVarX() && loc1.getVarX() <= loc2.getVarX() + image2.getIconWidth())
                    && (loc1.getVarY() >= loc2.getVarY() && loc1.getVarY() <= loc2.getVarY() + image2.getIconHeight())) {
                return true;
            } else if ((loc1.getVarX() + alvoMovel.getImageAlvo().getIconWidth() >= loc2.getVarX()
                    && loc1.getVarX() + image1.getIconWidth() <= loc2.getVarX() + image2.getIconWidth())
                    && (loc1.getVarY() >= loc2.getVarY() && loc1.getVarY() <= loc2.getVarY() + image2.getIconHeight())) {
                return true;
            } else if ((loc1.getVarX() >= loc2.getVarX() && loc1.getVarX() <= loc2.getVarX() + image2.getIconWidth())
                    && (loc1.getVarY() + image1.getIconHeight() >= loc2.getVarY()
                    && loc1.getVarY() + image1.getIconHeight() <= loc2.getVarY() + image2.getIconHeight())) {
                return true;
            } else if ((loc1.getVarX() + alvoMovel.getImageAlvo().getIconWidth() >= loc2.getVarX()
                    && loc1.getVarX() + image1.getIconWidth() <= loc2.getVarX() + image2.getIconWidth())
                    && (loc1.getVarY() + image1.getIconHeight() >= loc2.getVarY()
                    && loc1.getVarY() + image1.getIconHeight() <= loc2.getVarY() + image2.getIconHeight())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    public static Localizacao geraTrajetoria(Localizacao pontoOrigem, Localizacao pontoFinal){
        contaY = pontoFinal.getVarY() - pontoOrigem.getVarY();
        contaX = pontoFinal.getVarX() - pontoOrigem.getVarX();
        if(contaY == 0 && pontoX < pontoFinal.getVarY()){
            contaX = locomocao;
            pontoY += contaX;
            pontoY -= contaY;
        } else if (contaY < 0){
            contaX = (contaX / contaY) * locomocao;
            contaY = locomocao;
            pontoX -= contaX;
            pontoY -= locomocao;
        } else {
            contaY = (contaY / contaX) * locomocao;
            contaX = locomocao;
            pontoY += contaY;
            pontoX += contaX;
        }
        System.out.println("PontoX = " + pontoX +
        "\nPontoY = " + pontoY);
        return new Localizacao((int) Math.round(pontoX), (int) Math.round(pontoY));
    }
    public void lancador(){
    }
    public void run() {
        criarAlvo();
        inicializar();
        while (true) {
            atualizar();
            desenharGraficos();
            try {
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Thread interrompida!");
            }
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
