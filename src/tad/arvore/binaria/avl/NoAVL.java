/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.arvore.binaria.avl;

import tad.arvore.binaria.No;

/**
 *
 * @author tiago
 */
public class NoAVL extends No {
    private int FB;
    private int elemento;
    private NoAVL pai;
    private NoAVL filhoEsquerda;
    private NoAVL filhoDireita;

    public NoAVL() {
        this.FB = 0;
    }
    
    public NoAVL(int elemento){
        this.elemento = elemento;
        this.FB = 0;
    }

    public int getFB() {
        return FB;
    }

    public void setFB(int FB) {
        this.FB = FB;
    }

    public int getElemento() {
        return elemento;
    }

    public void setElemento(int elemento) {
        this.elemento = elemento;
    }

    public NoAVL getPai() {
        return pai;
    }

    public void setPai(NoAVL pai) {
        this.pai = pai;
    }

    public NoAVL getFilhoEsquerda() {
        return filhoEsquerda;
    }

    public void setFilhoEsquerda(NoAVL filhoEsquerda) {
        this.filhoEsquerda = filhoEsquerda;
    }

    public NoAVL getFilhoDireita() {
        return filhoDireita;
    }

    public void setFilhoDireita(NoAVL filhoDireita) {
        this.filhoDireita = filhoDireita;
    }
    
    
    
}
