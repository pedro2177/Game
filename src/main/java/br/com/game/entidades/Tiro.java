package br.com.game.entidades;

import br.com.game.sistema.Localizacao;

import javax.swing.*;
import java.sql.Timestamp;

public class Tiro {

    private Integer identificacao;
    private Municao municao;
    private ImageIcon imageTiro =  new ImageIcon("src/main/resources/sprites/Tiro.png");
    private Localizacao pontoOrigem = new Localizacao(215,440);
    private Localizacao pontoDestino;
    private Localizacao localizacaoAtual;
    private Timestamp timestamp;
    private long freqAttPosicao;
    private boolean contatoAlvo;


    public Tiro(){
    }


    public ImageIcon getImageTiro() {
        return imageTiro;
    }

    public void setImageTiro(ImageIcon imageTiro) {
        this.imageTiro = imageTiro;
    }

    public Integer getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(Integer identificacao) {
        this.identificacao = identificacao;
    }

    public Municao getMunicao() {
        return municao;
    }

    public void setMunicao(Municao municao) {
        this.municao = municao;
    }

    public Localizacao getPontoOrigem() {
        return pontoOrigem;
    }

    public void setPontoOrigem(Localizacao pontoOrigem) {
        this.pontoOrigem = pontoOrigem;
    }

    public Localizacao getPontoDestino() {
        return pontoDestino;
    }

    public void setPontoDestino(Localizacao pontoDestino) {
        this.pontoDestino = pontoDestino;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getFreqAttPosicao() {
        return freqAttPosicao;
    }

    public void setFreqAttPosicao(long freqAttPosicao) {
        this.freqAttPosicao = freqAttPosicao;
    }

    public boolean isContatoAlvo() {
        return contatoAlvo;
    }

    public void setContatoAlvo(boolean contatoAlvo) {
        this.contatoAlvo = contatoAlvo;
    }
}
