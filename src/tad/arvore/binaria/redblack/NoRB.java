/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.arvore.binaria.redblack;

import tad.arvore.binaria.No;

/**
 *
 * @author tiago
 */
public class NoRB extends No {
    public static final boolean VERMELHO = false;
    public static final boolean PRETO = true;
    
    private boolean cor;
    private int elemento;
    private NoRB pai, filhoEsquerda, filhoDireita;
    
    public NoRB(int elemento) {
        this.elemento = elemento;
        this.cor = VERMELHO;
    }
    
    public boolean mudarCor() {
        cor = !cor;
        return cor;
    }

    public boolean getCor() {
        return cor;
    }
    
    public boolean isBlack() {
        return cor;
    }

    public void setCor(boolean cor) {
        this.cor = cor;
    }
    
    public void setBlack() {
        this.cor = PRETO;
    }
    
    public void setRed() {
        this.cor = VERMELHO;
    }

    public int getElemento() {
        return elemento;
    }

    public void setElemento(int elemento) {
        this.elemento = elemento;
    }

    public NoRB getPai() {
        return pai;
    }

    public void setPai(NoRB pai) {
        this.pai = pai;
    }

    public NoRB getFilhoEsquerda() {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(NoRB filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public NoRB getFilhoDireita() {
        return filhoDireita;
    }

    public void setFilhoDireita(NoRB filhoDireita) {
        this.filhoDireita = filhoDireita;
    }
    
    public boolean isLeftChild(NoRB no) {
        return no == filhoEsquerda;
    }
    
    
    
}
