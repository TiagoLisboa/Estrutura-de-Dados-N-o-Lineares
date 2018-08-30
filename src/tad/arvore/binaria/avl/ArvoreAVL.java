/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.arvore.binaria.avl;

import java.util.ArrayList;
import tad.arvore.binaria.ArvoreBinaria;
import tad.arvore.binaria.Comparator;
import tad.arvore.binaria.No;

/**
 *
 * @author tiago
 */
public class ArvoreAVL extends ArvoreBinaria {
    private NoAVL raiz;
//    private int tamanho;
    Comparator c = new Comparator();
    private ArrayList<NoAVL> inOrder = new ArrayList<NoAVL>();
    private ArrayList<NoAVL> preOrder = new ArrayList<NoAVL>();
    private ArrayList<NoAVL> posOrder = new ArrayList<NoAVL>();
    
    
    public ArvoreAVL(){
        super();
    }
    
    public ArvoreAVL(int o) {
        super(o);
        raiz = new NoAVL(o);
    }
    
    public void inserir (Object key) {
        int o = (int)key;
        NoAVL novoNo = new NoAVL(o);
        novoNo.setFB(0);
        int resultado;
        if(isEmpty()){
            setRaiz(novoNo);
            incrementarTamanho();
            System.out.println(novoNo+" adicionado como Raiz.");
        } else {
            NoAVL segura = (NoAVL) buscar(o, getRaiz());
            resultado = (int) c.compare(segura.getElemento(), novoNo.getElemento());
            if(resultado==0){
                System.out.println("Elemento ja existe.");
            } else if(resultado>0){
                segura.setFilhoEsquerda(novoNo);
                novoNo.setPai(segura);
                incrementarTamanho();
                System.out.println( novoNo + " adicionado a esquerda de " + segura.getElemento());
                atualizarFB((NoAVL) segura, 1, true);
            } else if(resultado<0){
                segura.setFilhoDireita(novoNo);
                novoNo.setPai(segura);
                incrementarTamanho();
                System.out.println( novoNo + " adicionado a direita de " + segura.getElemento());
                atualizarFB((NoAVL) segura, -1, true);
            }
        }
    }
    
    public void atualizarFB (NoAVL no, int mod, boolean op) {
        int fb_antigo = no.getFB();
        no.setFB(fb_antigo + mod);
        
        if (no.getFB() == 2) {
            if (hasLeft(no) && no.getFilhoEsquerda().getFB() < 0) {
                rotacaoDuplaDireita(no);
            } else {
                rotacaoSimplesDir(no);
            }
        } else if (no.getFB() == -2) {
            if (hasRight(no) && no.getFilhoDireita().getFB() > 0) {
                System.out.println(no.getElemento() + " " +  no.getFB());
                rotacaoDuplaEsquerda(no);
            } else {
                rotacaoSimplesEsq(no);
            }
        } else {
        
//        mostrar();
        
            if (!isRoot(no) && ((op && no.getFB() != 0) || (!op && no.getFB() == 0))) {
                NoAVL pai = no.getPai();
                if (no.getElemento() > pai.getElemento()) {
                    atualizarFB(pai, op ? -1 : 1, op);
                } else {
                    atualizarFB(pai, op ? 1 : -1, op);
                }
            }
        }
    }
    
    public NoAVL remover (No no) {
        return remover((NoAVL) no, no.getElemento());
    }
    
    public NoAVL remover (NoAVL no) {
        return remover(no, no.getElemento());
    }
    
    public NoAVL remover (NoAVL n, Object o) {
        if (n != null) {
            /*folha*/
            if (isExternal(n)) {
                if (isRoot(n)) {
                    setRaiz(null);
                } else if ((int) c.compare(n.getElemento(), n.getPai().getElemento()) <= 0) {
                    n.getPai().setFilhoEsquerda(null);
                    atualizarFB(n.getPai(), -1, false);
                } else {
                    n.getPai().setFilhoDireita(null);
                    atualizarFB(n.getPai(), 1, false);
                }
                decrementarTamanho();
                return n;
            }
            /*um nó*/
            if (n.getFilhoEsquerda() != null && n.getFilhoDireita() == null) {
                if (isRoot(n)) {
                    setRaiz(n.getFilhoEsquerda());
                    n.getFilhoEsquerda().setFB(0);
                    n.getFilhoEsquerda().setPai(null);
                    n.setFilhoEsquerda(null);
                } else if ((int) c.compare(n.getElemento(), n.getPai().getElemento()) <= 0) {
                    n.getPai().setFilhoEsquerda(n.getFilhoEsquerda());
                    atualizarFB(n.getPai(), -1, false);
                    n.getFilhoEsquerda().setPai(n.getPai());
                } else {
                    n.getPai().setFilhoDireita(n.getFilhoEsquerda());
                    atualizarFB(n.getPai(), 1, false);
                    n.getFilhoEsquerda().setPai(n.getPai());
                }
                decrementarTamanho();
                return n;
            }
            if (n.getFilhoEsquerda() == null && n.getFilhoDireita() != null) {
                if (isRoot(n)) {
                    setRaiz(n.getFilhoDireita());
                    n.getFilhoDireita().setFB(0);
                    n.getFilhoDireita().setPai(null);
                    n.setFilhoDireita(null);
                } else if ((int) c.compare(n.getElemento(), n.getPai().getElemento()) <= 0) {
                    n.getPai().setFilhoEsquerda(n.getFilhoDireita());
                    atualizarFB(n.getPai(), -1, false);
                    n.getFilhoDireita().setPai(n.getPai());
                    n.setFilhoDireita(null);
                } else {
                    n.getPai().setFilhoDireita(n.getFilhoDireita());
                    atualizarFB(n.getPai(), 1, false);
                    n.getFilhoDireita().setPai(n.getPai());
                    n.setFilhoDireita(null);
                }
                decrementarTamanho();
                return n;
            }
            /*dois nós*/
            NoAVL andaEsq = n.getFilhoDireita();
            while (andaEsq.getFilhoEsquerda() != null) {
                andaEsq = andaEsq.getFilhoEsquerda();
            }
            Object valorBackup = andaEsq.getElemento();
            remover(andaEsq, valorBackup);
            n.setElemento((int) valorBackup);
            decrementarTamanho();
            return n;
        }
        return null;
    }
    
    public void rotacaoSimplesEsq(NoAVL no){
        // separar a subarvore da direita
        NoAVL subarvoreDireita = no.getFilhoDireita();
        // separar a subarvore esquerda da subarvore a direita
        NoAVL subarvoreEsquerdaDaSD = subarvoreDireita.getFilhoEsquerda();
        // separar o pai
        NoAVL painho = no.getPai();
        // colocar a subarvore esquerda da subarvore direita como filho direito
        //      do no a ser rotacionado
        /*
            1. checar se existe uma subarvore esquerda na subarvore direita
            2. setar o pai da subarvore esquerda como sendo no 
            3. setar o filho direito de no como sendo a subarvore esquerda
        */
        if (subarvoreEsquerdaDaSD != null)
            subarvoreEsquerdaDaSD.setPai(no);
        no.setFilhoDireita(subarvoreEsquerdaDaSD);
        // colocar o no a ser rotacionado como filho esquerdo da subarvore direita
        //      separada
        /*
            1. setar o filho esquerdo da raiz da subarvore direita como sendo o no
            2. setar o pai do no como sendo a raiz da subarvore direita
        */
        subarvoreDireita.setFilhoEsquerda(no);
        no.setPai(subarvoreDireita);
        // colocar o no raiz da subarvore direita na posição anterior do no rotacionado
        /*
            1. checar se o no é raiz
                1.1. se no for raiz setar a raiz como sendo a raiz da subarvoreDireita
            2. checar se o no é filho direito de outro no
                2.1. setar a raiz da subarvoreDireita como sendo filho direito do pai de no
            3. senão
                3.1. setar a raiz da subarvoreDireita como sendo filho esquerdo do pai de no
        */
        if (painho == null) {
            setRaiz(subarvoreDireita);
        } else if (no.getElemento() > painho.getElemento()) {
            painho.setFilhoDireita(subarvoreDireita);
        } else {
            painho.setFilhoEsquerda(subarvoreDireita);
        }
        subarvoreDireita.setPai(painho);
        
        
        
        
        int fb_b = no.getFB(), 
                fb_a = subarvoreDireita.getFB(),
//                fb_a = hasRight(no) ?  no.getFilhoDireita().getFB() : 0,
                fb_b_novo, fb_a_novo;
        fb_b_novo = fb_b + 1 - Math.min(fb_a, 0);
        fb_a_novo = fb_a + 1 + Math.max(fb_b_novo, 0);
        no.setFB(fb_b_novo);
        subarvoreDireita.setFB(fb_a_novo);
    }
    
    public void rotacaoSimplesDir(NoAVL no){
        // separar a subarvore da esquerda
        NoAVL subarvoreEsquerda = no.getFilhoEsquerda();
        // separar a subarvore direita da subarvore a esquerda
        NoAVL subarvoreDireitaDaSE = subarvoreEsquerda.getFilhoDireita();
        // separar o pai
        NoAVL painho = no.getPai();
        // colocar a subarvore direita da subarvore esquerda como filho esquerdo
        //      do no a ser rotacionado
        /*
            1. checar se existe uma subarvore direita na subarvore esquerda
            2. setar o pai da subarvore direita como sendo no 
            3. setar o filho esquerdo de no como sendo a subarvore direita
        */
        if (subarvoreDireitaDaSE != null)
            subarvoreDireitaDaSE.setPai(no);
        no.setFilhoEsquerda(subarvoreDireitaDaSE);
        // colocar o no a ser rotacionado como filho direito da subarvore esquerda
        //      separada
        /*
            1. setar o filho direito da raiz da subarvore esquerda como sendo o no
            2. setar o pai do no como sendo a raiz da subarvore esquerda
        */
        subarvoreEsquerda.setFilhoDireita(no);
        no.setPai(subarvoreEsquerda);
        // colocar o no raiz da subarvore esquerda na posição anterior do no rotacionado
        /*
            1. checar se o no é raiz
                1.1. se no for raiz setar a raiz como sendo a raiz da subarvoreEsquerda
            2. checar se o no é filho direito de outro no
                2.1. setar a raiz da subarvoreEsquerda como sendo filho direito do pai de no
            3. senão
                3.1. setar a raiz da subarvoreEsquerda como sendo filho esquerdo do pai de no
        */
        if (painho == null) {
            setRaiz(subarvoreEsquerda);
        } else if (no.getElemento() > painho.getElemento()) {
            painho.setFilhoDireita(subarvoreEsquerda);
        } else {
            painho.setFilhoEsquerda(subarvoreEsquerda);
        }
        subarvoreEsquerda.setPai(painho);
        
        
        
        
        
        int fb_b = no.getFB(), 
                fb_a = hasLeft(no) ? no.getFilhoEsquerda().getFB() : 0,
                fb_b_novo, fb_a_novo;
        fb_b_novo = fb_b - 1 - Math.max(fb_a, 0);
        fb_a_novo = fb_a - 1 + Math.min(fb_b_novo, 0);
        no.setFB(fb_b_novo);
        subarvoreEsquerda.setFB(fb_a_novo);
    }
    
    public void rotacaoDuplaEsquerda(NoAVL no){
        rotacaoSimplesDir(no.getFilhoDireita());
        rotacaoSimplesEsq(no);
    }
    
    public void rotacaoDuplaDireita(NoAVL no){
        rotacaoSimplesEsq(no.getFilhoEsquerda());
        rotacaoSimplesDir(no);
    }

}
