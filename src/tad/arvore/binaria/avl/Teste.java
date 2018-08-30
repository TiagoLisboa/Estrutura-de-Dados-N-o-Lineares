package tad.arvore.binaria.avl;

import java.util.Iterator;

/**
 *
 * @author rute
 */
public class Teste {

    public static void main(String[] args) {
        ArvoreAVL arvore = new ArvoreAVL();
        arvore.inserir(10);
        arvore.inserir(20);
        arvore.inserir(30);
        arvore.inserir(40);
        arvore.inserir(50);
        arvore.inserir(25);
        arvore.inserir(60);
        arvore.inserir(70);
        arvore.inserir(80);
        arvore.inserir(90);
        arvore.mostrar();
        
        arvore.remover((NoAVL) arvore.buscar(10));
        arvore.mostrar();
        arvore.remover(arvore.buscar(20));
        arvore.mostrar();
        arvore.remover(arvore.buscar(30));
        arvore.mostrar();
        arvore.remover(arvore.buscar(40));
        arvore.mostrar();
        arvore.remover(arvore.buscar(50));
        arvore.mostrar();
        arvore.remover(arvore.buscar(25));
        arvore.mostrar();
        arvore.remover(arvore.buscar(60));
        arvore.mostrar();
        arvore.remover(arvore.buscar(70));
        arvore.mostrar();
        arvore.remover(arvore.buscar(80));
        arvore.mostrar();
        arvore.remover(arvore.buscar(90));
        arvore.mostrar();
    }
    
}
