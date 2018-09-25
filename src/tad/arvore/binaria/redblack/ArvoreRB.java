/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.arvore.binaria.redblack;

import java.util.ArrayList;
import java.util.Iterator;
import tad.arvore.binaria.ArvoreBinaria;
import tad.arvore.binaria.Comparator;
import tad.arvore.binaria.No;

/**
 *
 * @author tiago
 */
public class ArvoreRB extends ArvoreBinaria{
    public static final boolean VERMELHO = false;
    public static final boolean PRETO = true;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    NoRB raiz, nil;
    Comparator c = new Comparator();
    private ArrayList<NoRB> inOrder = new ArrayList<NoRB>(),
            preOrder = new ArrayList<NoRB>(),
            posOrder = new ArrayList<NoRB>();
    
    public ArvoreRB(){
        super();
        nil = new NoRB(-1);
    }
    
    public ArvoreRB(int o) {
        super(o);
        raiz = new NoRB(o);
        nil = new NoRB(-1);
    }
    
    public NoRB tio(NoRB no) {
        NoRB pai = no.getPai();
        
        if (pai.getPai() == null) return null;
        
        if (pai.getPai().isLeftChild(pai)) {
            return pai.getPai().getFilhoDireita();
        }
        
        return pai.getPai().getFilhoEsquerda();
    }
    
    public void inserir(Object key) {
        int o = (int)key;
        NoRB novoNo = new NoRB(o);
        novoNo.setRed();
        int resultado;
        if(isEmpty()){
            setRaiz(novoNo);
            fixCase1(novoNo);
            incrementarTamanho();
            System.out.println(novoNo+" adicionado como Raiz.");
        } else {
            NoRB segura = (NoRB) buscar(o, getRaiz());
            resultado = (int) c.compare(segura.getElemento(), novoNo.getElemento());
            if(resultado==0){
                System.out.println("Elemento ja existe.");
            } else if(resultado>0){
                segura.setFilhoEsquerda(novoNo);
                novoNo.setPai(segura);
                incrementarTamanho();
                System.out.println( novoNo + " adicionado a esquerda de " + segura.getElemento());
                fixCase1(novoNo);
            } else if(resultado<0){
                segura.setFilhoDireita(novoNo);
                novoNo.setPai(segura);
                incrementarTamanho();
                System.out.println( novoNo + " adicionado a direita de " + segura.getElemento());
                fixCase1(novoNo);
            }
        }
    }
    
    public void fixCase1(NoRB no) {
        if (this.isRoot(no)) {
            no.setBlack();
        } else {
            fixCase2(no);
        }
    }
    
    public void fixCase2(NoRB no) {
        NoRB pai = no.getPai();
        if (!pai.isBlack()) { 
            fixCase3(no);
        }
    }
    
    public void fixCase3(NoRB no) {
        NoRB tio = tio(no);
        
        if (tio != null && tio != this.nil && tio.getCor() == VERMELHO) {
            NoRB pai = no.getPai();
            pai.setBlack();
            tio.setBlack();
            
            NoRB vo = pai.getPai();
            vo.setRed();
            fixCase1(vo);
        } else {
            fixCase4(no);
        }
        
    }
    
    public void fixCase4(NoRB no) {
        NoRB pai = no.getPai();
        NoRB avo = pai.getPai();
        
        if (c.compare(no.getElemento(), pai.getElemento()) > 0 && c.compare(pai.getElemento(), avo.getElemento()) < 0) {
            rotacaoSimplesEsq(pai);
            
            no = no.getFilhoEsquerda();
        } else if (c.compare(no.getElemento(), pai.getElemento()) < 0 && c.compare(pai.getElemento(), avo.getElemento()) > 0) {
            rotacaoSimplesDir(pai);
            
            no = no.getFilhoDireita();
        }
        
        fixCase5(no);
    }
    
    public void fixCase5(NoRB no) {
        NoRB pai = no.getPai();
        NoRB avo = pai.getPai();
        NoRB tipo = tio(no);
        
        avo.setRed();
        pai.setBlack();
        
        if (c.compare(no.getElemento(), pai.getElemento()) < 0 && c.compare(pai.getElemento(), avo.getElemento()) < 0) {
            rotacaoSimplesDir(avo);
        } else {
            rotacaoSimplesEsq(avo);
        }
    }
    
    public void rotacaoSimplesEsq(NoRB no) {
        // separar a subarvore da direita
        NoRB subarvoreDireita = no.getFilhoDireita();
        // separar a subarvore esquerda da subarvore a direita
        NoRB subarvoreEsquerdaDaSD = subarvoreDireita.getFilhoEsquerda();
        // separar o pai
        NoRB painho = no.getPai();
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
        
//        no.mudarCor();
//        subarvoreDireita.mudarCor();
    }
    
    public void rotacaoSimplesDir(NoRB no){
        // separar a subarvore da esquerda
        NoRB subarvoreEsquerda = no.getFilhoEsquerda();
        // separar a subarvore direita da subarvore a esquerda
        NoRB subarvoreDireitaDaSE = subarvoreEsquerda.getFilhoDireita();
        // separar o pai
        NoRB painho = no.getPai();
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
        
//        no.mudarCor();
//        subarvoreEsquerda.mudarCor();
        
    }
    
    public void rotacaoDuplaEsquerda(NoRB no){
        rotacaoSimplesDir(no.getFilhoDireita());
        rotacaoSimplesEsq(no);
    }
    
    public void rotacaoDuplaDireita(NoRB no){
        rotacaoSimplesEsq(no.getFilhoEsquerda());
        rotacaoSimplesDir(no);
    }
    
    public String toString () {
        Iterator itr = inOrder();
        if (itr == null) return "";
        int h = this.height() + 5;
        int l = this.size() + 5;
        
        Object matrix[][] = new Object[h][l];
        NoRB matrixRB[][] = new NoRB[h][l];
//        System.out.println("h: " + h + ", l:" + l);
        
        int i = 0;
        while (itr.hasNext()) {
            NoRB n = (NoRB) itr.next();
            int d = this.depth(n);
//            System.out.println("d: " + d + ", i:" + i);
//            System.out.println(n.getElemento());
            matrix[d][i] = n.getElemento();
            matrixRB[d][i] = n;
            i++;
        }
        
        String str = "";
        
        for (i = 0; i < h; i++){
            for (int j = 0; j < l; j++) {
                str += matrix[i][j] == null ? "  " : (matrixRB[i][j].isBlack() ? ANSI_BLACK : ANSI_RED) +
                        ((((int) matrix[i][j] >= 0) && ((int) matrix[i][j] < 10)  ? " " + matrix[i][j] : matrix[i][j]))
                        + ANSI_RESET;
            }
            str += "\n";
        }
        
        return str;
    }
}
