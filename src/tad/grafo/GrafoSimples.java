/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grafo;

/**
 *
 * @author tiago
 */
import java.util.*;

public class GrafoSimples implements InterfaceGrafosSimples {

    private int qtdVertices;
    private Vector vertices;
    private Arestas matrizAdj[][];

    public GrafoSimples() {
        qtdVertices = 0;
        vertices = new Vector();
    }

    public void inserirVertice(Vertices Vertice) {
        qtdVertices++;
        vertices.add(Vertice);
        Arestas matrizAdjTemp[][] = new Arestas[qtdVertices][qtdVertices];
        
        // copiar matriz adj
        for(int f = 0; f < qtdVertices - 1; f++){
            for(int g = 0; g < qtdVertices - 1; g++){
                  matrizAdjTemp[f][g] = matrizAdj[f][g];              
            }
        }
        // null no novo vertice
        for(int g = 0;g <qtdVertices-1;g++){
            matrizAdjTemp[qtdVertices-1][g] = matrizAdjTemp[g][qtdVertices-1] = null;          
        }
        
        matrizAdj = matrizAdjTemp;
    }

    public void removerVertice(Vertices Vertice) {
        qtdVertices--;
        int índice = achaÍndice(Vertice.getChave());
        vertices.remove(índice);  // remove o vértice do vector    
        // remove linhas e colunas da matriz de adjacência
        Arestas tempMatrizAdj[][] = new Arestas[qtdVertices][qtdVertices];
        int ff = 0, gg;
        for (int f = 0; f < qtdVertices + 1; f++) {
            gg = 0;
            for (int g = 0; g < qtdVertices + 1; g++) {
                if (f != índice && g != índice) {
                    tempMatrizAdj[ff][gg] = matrizAdj[f][g];
                    if (g != índice) {
                        gg++;
                    }
                }
            }
            if (f != índice) {
                ff++;
            }
        }
        matrizAdj = tempMatrizAdj;
    }

    public Arestas insereAresta(Vertices VerticeUm, Vertices VerticeDois,
            double valor) {
        Arestas A = new Arestas(VerticeUm, VerticeDois, valor);
        int ind1 = achaÍndice(VerticeUm.getChave());
        int ind2 = achaÍndice(VerticeDois.getChave());
        matrizAdj[ind1][ind2] = matrizAdj[ind2][ind1] = A; // grafo nao orientado
        return A;
    }

    public Arestas insereAresta(Vertices VerticeUm, Vertices VerticeDois) {
        Arestas A = new Arestas(VerticeUm, VerticeDois);
        int ind1 = achaÍndice(VerticeUm.getChave());
        int ind2 = achaÍndice(VerticeDois.getChave());
        matrizAdj[ind1][ind2] = matrizAdj[ind2][ind1] = A; // grafo nao orientado
        return A;
    }

    public void removeAresta(Arestas Aresta) {
        int ind1 = achaÍndice(Aresta.getVerticeOrigem().getChave());
        int ind2 = achaÍndice(Aresta.getVerticeDestino().getChave());
        matrizAdj[ind1][ind2] = matrizAdj[ind2][ind1] = null; // grafo nao orientado
    }

    public Arestas insereArco(Vertices VerticeUm, Vertices VerticeDois, double valor) {
        Arestas A = new Arestas(VerticeUm, VerticeDois, valor, true);
        int ind1 = achaÍndice(VerticeUm.getChave());
        int ind2 = achaÍndice(VerticeDois.getChave());
        matrizAdj[ind1][ind2] = A; // grafo orientado
        return A;
    }

    public Arestas insereArco(Vertices VerticeUm, Vertices VerticeDois) {
        Arestas A = new Arestas(VerticeUm, VerticeDois, 0, true);
        int ind1 = achaÍndice(VerticeUm.getChave());
        int ind2 = achaÍndice(VerticeDois.getChave());
        matrizAdj[ind1][ind2] = A; // grafo orientado
        return A;
    }

    public void removeArco(Arestas Aresta) {   // grafo orientado     
        // método exercício, fique a vontade para implementa-lo coleguinha   
    }

    public void mostraVertices() {
        for (int f = 0; f < vertices.size(); f++) {
            System.out.print(vertices.elementAt(f) + ",");
        }
    }

    public void mostraMatriz() {
        for (int f = 0; f < qtdVertices; f++) {
            for (int g = 0; g < qtdVertices; g++) {
                System.out.print(matrizAdj[f][g] + " ");
            }
            System.out.println();
        }
    }

    public int grau(Vertices Vertice) {
        int grau = 0;
        int indice = this.achaÍndice(Vertice.getChave());
        for (int i = 0; i < this.qtdVertices; i++) {
            if (this.matrizAdj[i][indice] != null) {
                grau++;
            }
        }
        return grau;
    }

    public int ordem() {
        return qtdVertices;
    }

    private int achaÍndice(int chave) {
        Iterator I = vertices.iterator();
        int ind = 0;
        while (I.hasNext()) {
            Vertices v = (Vertices) (I.next());
            if (v.getChave() == chave)// achei
            {
                return ind;
            }
            ind++;
        }
        return -1; // nao achei
    }

    public Vector vertices() {
        return vertices;
    }

    public Vector arestas() {
        Vector v = new Vector();
        for (int l = 0; l < qtdVertices; l++) {
            for (int c = 0; c < qtdVertices; c++) {
                v.add(matrizAdj[l][c]);
            }
        }
        return v;
    }

    public Vector arestasIncidentes(Vertices vertice) {
        Vector<Arestas> arestas = new Vector();
        int indice = this.achaÍndice(vertice.getChave());
        for (int i = 0; i < this.qtdVertices; i++) {
            if (this.matrizAdj[i][indice] != null) {
                arestas.add(this.matrizAdj[i][indice]);
            }
        }
        return arestas;
    }

    public Vector finalVertices(Arestas a) {
        Vector v = new Vector();
        v.add(a.getVerticeOrigem());
        v.add(a.getVerticeDestino());
        return v;
    }

    public Vertices oposto(Vertices v, Arestas a) throws OpostoError {
        // método exercício, fique a vontade para implementa-lo coleguinha
        return null;
    }

    public boolean éAdjacente(Vertices v, Vertices w) {
        int ind1 = achaÍndice(v.getChave());
        int ind2 = achaÍndice(w.getChave());
        return (matrizAdj[ind1][ind2]) != null;

    }

    public Arestas getAresta(Vertices v, Vertices w) {
        int ind1 = achaÍndice(v.getChave());
        int ind2 = achaÍndice(w.getChave());
        return (matrizAdj[ind1][ind2]);
    }

}
