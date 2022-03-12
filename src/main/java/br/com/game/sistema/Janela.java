package br.com.game.sistema;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Janela extends JFrame {

    private Integer janelaW;
    private Integer janelaH;

    public void inicializar(BufferedImage backBuffer) {
        setTitle("Teste Caminho");
        setSize(janelaW, janelaH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        backBuffer = new BufferedImage(janelaW, janelaH,
                BufferedImage.TYPE_INT_RGB);
    }
}
