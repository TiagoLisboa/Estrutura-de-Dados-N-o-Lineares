/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grafo;

import java.util.Vector;

/**
 *
 * @author tiago
 */
public class Teste {
    public static void main(String[] args) {
        GrafoSimples g = new GrafoSimples();
        Vertices v1 = new Vertices(1, 20);
        Vertices v2 = new Vertices(2, 20);
        Vertices v3 = new Vertices(3, 20);
        Vertices v4 = new Vertices(4, 20);
        g.inserirVertice(v1);
        g.inserirVertice(v2);
        g.inserirVertice(v3);
        g.inserirVertice(v4);
        g.insereAresta(v1, v2, 1);
        g.insereAresta(v1, v3, 2);
        g.mostraMatriz();
        System.out.println(g.grau(v4));
        Vector<Arestas> arestas = g.arestasIncidentes(v1);
        for(int i = 0; i < arestas.size(); i++) {
            System.out.println(arestas.get(i));
        }
    }
}
