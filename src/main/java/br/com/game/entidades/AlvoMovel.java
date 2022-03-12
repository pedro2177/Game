package br.com.game.entidades;

import br.com.game.sistema.Localizacao;

import javax.swing.*;
import java.sql.Timestamp;

public class AlvoMovel {

    private Integer identificacao;
    private Localizacao pontoOrigem;
    public Integer caminho;
    private Localizacao pontoDestino;
    private Localizacao localizacaoAtual;
    private Tamanho tamanho;
    public ImageIcon imageAlvo = new ImageIcon("src/main/resources/sprites/Alvo.png");
    private Timestamp timestamp = new Timestamp(30);
    private long frequenciaAttPosicao = 30;
    private boolean chegadaNoDestino = false;
    private boolean atingido = false;

    public AlvoMovel(Integer caminho) {
        this.caminho = caminho;
        if(caminho == 1){
            this.pontoOrigem = new Localizacao(80,0);
            this.pontoDestino = new Localizacao(80,500);
        } else if(caminho == 2){
            this.pontoOrigem = new Localizacao(380,0);
            this.pontoDestino = new Localizacao(380,500);
        }
        this.localizacaoAtual = this.pontoOrigem;
    }
    public ImageIcon getImageAlvo() {
        return imageAlvo;
    }

    public void setImageAlvo(ImageIcon imageAlvo) {
        this.imageAlvo = imageAlvo;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(Integer identificacao) {
        this.identificacao = identificacao;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Localizacao getPontoOrigem() {
        return pontoOrigem;
    }

    public long getFrequenciaAttPosicao() {
        return frequenciaAttPosicao;
    }

    public void setFrequenciaAttPosicao(long frequenciaAttPosicao) {
        this.frequenciaAttPosicao = frequenciaAttPosicao;
    }

    public boolean getChegadaNoDestino() {
        return chegadaNoDestino;
    }

    public void setChegadaNoDestino(boolean chegadaNoDestino) {
        this.chegadaNoDestino = chegadaNoDestino;
    }

    public boolean isAtingido() {
        return atingido;
    }

    public void setAtingido(boolean atingido) {
        this.atingido = atingido;
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
}
