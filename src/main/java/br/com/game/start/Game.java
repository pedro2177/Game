package br.com.game.start;

import br.com.game.entidades.*;
import br.com.game.sistema.Localizacao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends JFrame {

    // CRIA UM LANÇADOR INICIAL.
    private Lancador lancador = new Lancador();
    // DECLARA UM ALVO MOVEL.
    private AlvoMovel alvoMovel;
    // AQUI É ONDE SERÃO PRODUZIDAS AS IMAGENS
    public static BufferedImage backBuffer;
    // TEMPO DE LOCOMOÇÃO DOS OBJETOS
    static double locomocao = 2.0;
    // TAMANHO DA JANELA
    int janelaW = 500;
    int janelaH = 500;
    //
    static double pontoY = 440;
    static double pontoX = 215;
    static double contaX = 0;
    static double contaY = 0;

    // MOVE O ALVO PARA QUE VÁ EM DIREÇÃO DA POSIÇÃO 500 NO EIXO Y
    public void moveAlvoMovel() {
        if (alvoMovel != null) {
            alvoMovel.setLocalizacaoAtual(new Localizacao(alvoMovel.getPontoOrigem().getVarX(), alvoMovel.getLocalizacaoAtual().getVarY() + 2));
            System.out.println(alvoMovel.getLocalizacaoAtual().getVarY());
            if (alvoMovel.getLocalizacaoAtual().getVarY() > 500) {
                alvoMovel.setLocalizacaoAtual(new Localizacao(80, 0));
            }
        }
    }

    // INFORMA SE O ALVO CHEGOU AO DESTINO E O SISTEMA PERDEU
    public void exibeTexto() {
        Graphics bbg = backBuffer.getGraphics();
        if (alvoMovel != null) {
            if (alvoMovel.getChegadaNoDestino()) {
                bbg.setColor(Color.RED);
                bbg.drawString("Você perdeu!", 200, 100);
            }
        }
    }

    //Verifica se o alvo chegou no destino (ponto inferior do mapa Y = 420)
    public boolean chegou(AlvoMovel alvoMovel) {
        if (alvoMovel != null) {
            Localizacao loc = alvoMovel.getLocalizacaoAtual();
            if (loc.getVarY() >= 420) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // ATUALIZA A POSIÇÃO DO ALVO E CHAMA O MÉTODO CHEGOU PARA VER SE CHEGOU.
    public void atualizar() {
        if (alvoMovel != null)
            alvoMovel.setChegadaNoDestino(chegou(alvoMovel));
        moveAlvoMovel();
    }

    //DESENHA TODOS OS GRÁFICOS
    public void desenharGraficos() {
        Graphics g = getGraphics(); // ISSO JÃ� ESTAVA AQUI
        Graphics bbg = backBuffer.getGraphics();// ISSO TAMBÃ‰M JÃ� ESTAVA AQUI...
        // ==================================================================================
        bbg.setColor(Color.WHITE);
        bbg.fillRect(0, 0, janelaW, janelaH);// DESENHA UM FUNDO BRANCO NA TELA!

        // EXIBE UM TEXTO CASO O OBJETO COLIDA!
        exibeTexto();

        //DESENHANDO AS ENTIDADES
        desenharLancador(bbg);
        desenharAlvoMovel(bbg);
        desenharTiro(bbg);
        // -----------------------

        colidiram(alvoMovel, lancador.getTiro());
        if (teste && lancador.getTiro().getLocalizacaoAtual().getVarX() > 0) {
            bbg.setColor(Color.RED);
            alvoMovel.setAtingido(true);
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

    public void desenharTiro(Graphics bbg){
        ImageIcon image = lancador.getTiro().getImageTiro();
        bbg.drawImage(image.getImage(), lancador.getTiro().getLocalizacaoAtual().getVarX(),
                lancador.getTiro().getLocalizacaoAtual().getVarY(), this);
    }

    public void desenharLancador(Graphics bbg) {
        ImageIcon image = lancador.getImageLancador();
        bbg.drawImage(image.getImage(), 215, 440, this);
    }

    public void desenharAlvoMovel(Graphics bbg) {
        if (alvoMovel != null && !alvoMovel.getAtingido()) {
            if (alvoMovel.caminho == 1) {
                ImageIcon image = alvoMovel.getImageAlvo();
                bbg.drawImage(image.getImage(), alvoMovel.getLocalizacaoAtual().getVarX(),
                        alvoMovel.getLocalizacaoAtual().getVarY(), this);
            } else if (alvoMovel.caminho == 2) {
                ImageIcon image = alvoMovel.getImageAlvo();
                bbg.drawImage(image.getImage(), alvoMovel.getLocalizacaoAtual().getVarX(),
                        alvoMovel.getLocalizacaoAtual().getVarY(), this);
            }
        }
    }

    public static Localizacao traj;
    static boolean teste = false;

    public void colidiram(AlvoMovel alvoMovel, Tiro tiro) {
        Graphics bbg = backBuffer.getGraphics();
        if (colisao(alvoMovel, tiro)) {
            this.alvoMovel = null;
            bbg.setColor(Color.RED);
            bbg.drawString("Tiro Acertado.", 200, 100);
        }

    }

    // VERIFICA SE HOUVE A COLISÃO COM BASE NA LOCALIZAÇÃO DO ALVO E DO TIRO
    public static boolean colisao(AlvoMovel alvoMovel,
                                  Tiro tiro) {
        if (alvoMovel != null) {
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

    // ELE CALCULA CADA POSIÇÃO QUE O ALVO TERÁ QUE SE MOVER, DADO A VELOCIDAE DE LOMOÇÃO
    // EO PONTO DE ORIGEM DO TIRO E O PONTO FINAL (ONDE ELE IRÁ COLIDIR COM O ALVO)
    public static Localizacao geraTrajetoria(Localizacao pontoOrigem, Localizacao pontoFinal) {
        contaY = pontoFinal.getVarY() - pontoOrigem.getVarY();
        contaX = pontoFinal.getVarX() - pontoOrigem.getVarX();
        if (contaY == 0 && pontoX < pontoFinal.getVarY()) {
            contaX = locomocao;
            pontoY += contaX;
            pontoY -= contaY;
        } else if (contaY < 0) {
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

    public void lancador() {
    }

    public void run() {
        alvoMovel = new AlvoMovel(1);
        inicializar();
        while (true) {
            if (alvoMovel == null) {
                Random random = new Random();
                alvoMovel = new AlvoMovel(random.nextInt(1,2));
            }
            lancador.verificaAlvo(alvoMovel, backBuffer.getGraphics());
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
