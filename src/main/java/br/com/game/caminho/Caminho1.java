package br.com.game.caminho;

import br.com.game.sistema.Localizacao;

import java.sql.Time;

public class Caminho1{

    private Localizacao pontoOrigem = null;
    private long timer;

    public Localizacao getPontoOrigem() {
        return pontoOrigem;
    }

    public void setPontoOrigem(Localizacao pontoOrigem) {
        this.pontoOrigem = pontoOrigem;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }
}
