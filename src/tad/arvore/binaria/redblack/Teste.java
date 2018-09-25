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
        
        rb.inserir(2);
        System.out.println(rb);
        
        rb.inserir(1);
        System.out.println(rb);
        
        rb.inserir(4);
        System.out.println(rb);
        rb.inserir(5);
        System.out.println(rb);
        rb.inserir(9);
        System.out.println(rb);
        rb.inserir(3);
        System.out.println(rb);
        rb.inserir(6);
        System.out.println(rb);
        
        rb.inserir(7);
        
        System.out.println(rb);

    }
}
