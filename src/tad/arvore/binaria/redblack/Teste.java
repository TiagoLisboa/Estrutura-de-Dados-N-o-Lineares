/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.arvore.binaria.redblack;

/**
 *
 * @author tiago
 */
public class Teste {
    public static void main(String[] args) {
        ArvoreRB rb = new ArvoreRB();
        
//        rb.inserir(2);
//        System.out.println(rb);
//        
//        rb.inserir(1);
//        System.out.println(rb);
//        
//        rb.inserir(4);
//        System.out.println(rb);
//        rb.inserir(5);
//        System.out.println(rb);
//        rb.inserir(9);
//        System.out.println(rb);
//        rb.inserir(3);
//        System.out.println(rb);
//        rb.inserir(6);
//        System.out.println(rb);
//        
//        rb.inserir(7);
//        
//        System.out.println(rb);
//        
//        rb.remover(rb.buscar(7));
//        
//        System.out.println(rb);

        rb.inserir(33);
        rb.inserir(14);
        rb.inserir(47);
        rb.inserir(10);
        rb.inserir(20);
        rb.inserir(38);
        rb.inserir(51);
        rb.inserir(5);
        rb.inserir(18);
        rb.inserir(36);
        rb.inserir(39);
        rb.inserir(53);
        
        rb.inserir(17);
        rb.inserir(16);
        rb.inserir(15);
        
        rb.inserir(37);
        
        System.out.println(rb);
        
        rb.remover(47);
        
        System.out.println(rb);
        
        rb.remover(51);
        
        System.out.println(rb);
//        
        rb.remover(37);
        
        System.out.println(rb);
        
        rb.remover(36);
        
        System.out.println(rb);
        
        rb.remover(39);
        
        System.out.println(rb);
        
        rb.remover(38);
        
        System.out.println(rb);

//        rb.inserir(7);
//        rb.inserir(8);
//        rb.inserir(2);
//        rb.inserir(1);
//        rb.inserir(9);
//        rb.inserir(5);
//        rb.inserir(4);
//        
//        System.out.println(rb);
//        
//        NoRB t = rb.remover(2);
//        
//        System.out.println(t);
//        
//        System.out.println(rb);

    }
}
