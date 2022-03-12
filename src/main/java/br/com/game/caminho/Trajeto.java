package br.com.game.caminho;

import br.com.game.sistema.Localizacao;

public class Trajeto {

    Localizacao origem = new Localizacao(380,0);
    Localizacao destino = new Localizacao(380,500);

    public Localizacao getOrigem() {
        return origem;
    }

    public Localizacao getDestino() {
        return destino;
    }

    public void setOrigem(Localizacao origem) {
        this.origem = origem;
    }

    public void setDestino(Localizacao destino) {
        this.destino = destino;
    }
}
