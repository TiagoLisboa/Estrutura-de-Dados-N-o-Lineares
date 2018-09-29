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
    
    @Override
    public NoRB remover(No no) {
        return remover((NoRB) no, no.getElemento());
    }
    
    public NoRB remover(int valor) {
        NoRB deletar = (NoRB) buscar(valor);
        if(deletar == null) return this.nil;
        return remover(deletar, deletar.getElemento());
    }

    private NoRB remover(NoRB n, Object o) {
        if (n != null) {
            /*folha*/
            if (isExternal(n)) {
                if ((int) c.compare(n.getElemento(), n.getPai().getElemento()) <= 0) {
                    n.getPai().setFilhoEsquerda(null);
                } else {
                    n.getPai().setFilhoDireita(null);
                }
                decrementarTamanho();
                return n;
            }
            /*um nó*/
            if (n.getFilhoEsquerda() != null && n.getFilhoDireita() == null) {
                if ((int) c.compare(n.getElemento(), n.getPai().getElemento()) <= 0) {
                    n.getPai().setFilhoEsquerda(n.getFilhoEsquerda());
                    n.getFilhoEsquerda().setPai(n.getPai());
                } else {
                    n.getPai().setFilhoDireita(n.getFilhoEsquerda());
                    n.getFilhoEsquerda().setPai(n.getPai());
                }
                decrementarTamanho();
                if(n.isBlack()){
                    corrigirRemocao(n.getFilhoEsquerda());
                }
                return n;
            }
            if (n.getFilhoEsquerda() == null && n.getFilhoDireita() != null) {
                if ((int) c.compare(n.getElemento(), n.getPai().getElemento()) <= 0) {
                    n.getPai().setFilhoEsquerda(n.getFilhoDireita());
                    n.getFilhoDireita().setPai(n.getPai());
                } else {
                    n.getPai().setFilhoDireita(n.getFilhoDireita());
                    n.getFilhoDireita().setPai(n.getPai());
                }
                decrementarTamanho();
                if(n.isBlack()){
                    corrigirRemocao(n.getFilhoDireita());
                }
                return n;
            }
            /*dois nós*/
            NoRB andaEsq = n.getFilhoDireita();
            while (andaEsq.getFilhoEsquerda() != null) {
                andaEsq = andaEsq.getFilhoEsquerda();
            }
            Object valorBackup = andaEsq.getElemento();
            remover(andaEsq, valorBackup);
            n.setElemento((int) valorBackup);
            return n;
        }
        return null;
    }
    
    public void corrigirRemocao(NoRB n) {
        
        if (n.getPai() != null) {// CASO 1: verificar se o pai não é nulo
        // Se for vai pro caso 2
            boolean isLeft = n.getPai().isLeftChild(n);
            NoRB irmao = isLeft ? n.getPai().getFilhoDireita() : n.getPai().getFilhoEsquerda();
            if (n.isBlack() && n.getPai().isBlack() && irmao != null && !irmao.isBlack()) { 
                // CASO 2: verifica se o nó e seu pai forem pretos e seu irmão for vermelho
                // Se for o pai deve ser pintado de vermelho e o irmão de preto e 
                n.getPai().setRed();
                irmao.setBlack();
                
                if (isLeft) {   // então se o nó for filho esquerdo, faz a rotação à esquerda de 
                                // seu pai e vai pro caso 3,
                    this.rotacaoSimplesEsq(n.getPai());
                } else {    // se for filho direito, rotaciona 
                            // o pai à direita e vai pro caso 3
                    this.rotacaoSimplesDir(n.getPai());
                }
            }
            
            // CASO 3: se o pai do nó, o irmão, o filho esquerdo e direito do irmão forem todos pretos,
            // obs: no null equivale a um no preto
            if (n.getPai().isBlack() &&  // pai é preto
                    (irmao != null && irmao.isBlack() && // irmão é preto
                        (irmao.getFilhoEsquerda() == null || irmao.getFilhoEsquerda().isBlack()) && // os filhos do irmão são pretos
                        (irmao.getFilhoDireita() == null || irmao.getFilhoDireita().isBlack())
                    )
                ) {
                // pinta o irmão de vermelho e volte para o primeiro caso com o
                // pai do nó, se não forem vai pro próximo caso
                irmao.setRed();
                corrigirRemocao(n.getPai());
                return;   
            }
            
            // CASO 4: se o irmão e o filho esquerdo e direito do irmão forem pretos e o pai do nó for vermelho,
            if (!n.getPai().isBlack() &&  // pai é vermelho
                    (irmao != null && irmao.isBlack() && // irmão é preto
                        (irmao.getFilhoEsquerda() == null || irmao.getFilhoEsquerda().isBlack()) && // os filhos do irmão são pretos
                        (irmao.getFilhoDireita() == null || irmao.getFilhoDireita().isBlack())
                    )
                ) {
                // deve pintar o irmão de vermelho e o pai do nó de preto, 
                irmao.setRed();
                n.getPai().setBlack();
                corrigirRemocao(n.getPai());
                return;   
            } // se não deve prosseguir para o próximo caso
            
            // CASO 5: se o nó for filho esquerdo e o filho direito do irmão for preto
            if (isLeft && (irmao != null && (irmao.getFilhoDireita() == null || irmao.getFilhoDireita().isBlack()))) {
                // deverá pintar o irmão de vermelho e o filho esquerdo do irmão de preto e aí sim rotacionar à direita o irmão,
                irmao.setRed();
                this.rotacaoSimplesDir(irmao);
            }
            // mas se o nó for filho direito
            if (!isLeft) {
                // deverá pintar o irmão de vermelho e o filho direito do
                if (irmao != null) {
                    irmao.setRed();
                    // irmão de preto e então rotacionar para esquerda o irmão, indo para o último caso.
                    if (irmao.getFilhoDireita() != null) {
                        irmao.getFilhoDireita().setBlack();
                        this.rotacaoSimplesEsq(irmao);
                    }
                }
            }
            
            // CASO 6: deverá pintar o pai do nó de preto,
            n.getPai().setBlack();
            // caso o nó seja filho esquerdo, 
            if (isLeft) {
                // pinta o filho direito do irmão do nó de preto e 
                if (irmao != null && irmao.getFilhoDireita() != null) {
                    irmao.getFilhoDireita().setBlack();
                    // rotaciona o pai do nó para a esquerda
                    this.rotacaoSimplesEsq(n.getPai());
                }
            } else { // se o nó for filho direito, 
                // pinta o filho esquerdo do irmão de preto e rotaciona o pai para direita
                if (irmao != null && irmao.getFilhoEsquerda() != null) {
                    irmao.getFilhoEsquerda().setBlack();
                    this.rotacaoSimplesDir(n.getPai());
                }
            }
                
            
            
        }
        
        
        
        
        
//        if(n != this.raiz && n.isBlack()){
//            if(n.getPai().isLeftChild(n)){
//                NoRB aux = n.getPai().getFilhoDireita();
//                if(!aux.isBlack()){
//                    aux.setBlack();
//                    n.getPai().setRed();
//                    rotacaoSimplesEsq(n.getPai());
//                    aux = n.getPai().getFilhoDireita();
//                }
//                if(aux.getFilhoEsquerda().isBlack() && aux.getFilhoDireita().isBlack()){
//                     aux.setRed();
//                     n = n.getPai();
//                }else if(aux.getFilhoDireita().isBlack()){
//                    aux.getFilhoEsquerda().setBlack();
//                    aux.setRed();
//                    rotacaoSimplesDir(aux);
//                    aux = n.getPai().getFilhoDireita();
//                } else {
//                    if(n.getPai().isBlack()) aux.setBlack();
//                    else aux.setRed();
//                    n.getPai().setBlack();
//                    aux.getFilhoDireita().setBlack();
//                    rotacaoSimplesEsq(n.getPai());
//                    n = this.raiz;
//                }
//            }
//            else{
//                NoRB aux = n.getPai().getFilhoEsquerda();
//                if(!aux.isBlack()){
//                    aux.setBlack();
//                    n.getPai().setRed();
//                    rotacaoSimplesDir(n.getPai());
//                    aux = n.getPai().getFilhoEsquerda();
//                }
//                if(aux.getFilhoDireita().isBlack() && aux.getFilhoEsquerda().isBlack()){
//                     aux.setRed();
//                     n = n.getPai();
//                }else if(aux.getFilhoEsquerda().isBlack()){
//                    aux.getFilhoDireita().setBlack();
//                    aux.setRed();
//                    rotacaoSimplesEsq(aux);
//                    aux = n.getPai().getFilhoEsquerda();
//                } else {
//                    if(n.getPai().isBlack()) aux.setBlack();
//                    else aux.setRed();
//                    n.getPai().setBlack();
//                    aux.getFilhoEsquerda().setBlack();
//                    rotacaoSimplesDir(n.getPai());
//                    n = this.raiz;
//                }
//            }
//            corrigirRemocao(n);
//        }
//        else {
//           n.setBlack();
//        }
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
