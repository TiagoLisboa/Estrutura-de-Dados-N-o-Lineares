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
public class EstrelinhaDeBelem {
    public static void main(String[] args) {
        
        GrafoSimples g = new GrafoSimples();
        
        String mapa =   "1111111111\n" +
                        "1000000001\n" +
                        "1200010101\n" +
                        "1111010101\n" +
                        "1000000101\n" +
                        "1011111101\n" +
                        "1000000001\n" +
                        "1111111101\n" +
                        "3000100001\n" +
                        "1101101111\n" +
                        "1000001301\n" +
                        "1111111111";

        String[] labirinto = mapa.split("\n");
        
        int n = 0;
        int h = labirinto.length;
        int w = labirinto[0].length();
        
        VerticeEstrela vertices[][] = new VerticeEstrela[h][w];
        VerticeEstrela inicio = null;
        Vector<VerticeEstrela> saidas = new Vector();
        
        // transformar string em matriz de vertices
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                VerticeEstrela v = new VerticeEstrela(++n, (double) Character.digit(labirinto[i].charAt(j), 10), i, j, 0, 0, 0, labirinto[i].charAt(j));
                
                g.inserirVertice(v);
                vertices[i][j] = v;
                inicio = v.getValor() == 2 ? v : inicio;
                if (v.getValor() == 3) saidas.add(v);
            }
        }
        
        // ligar vertices vizinhos atraves de arestas
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
               if(i-1 > -1){
                   g.insereAresta(vertices[i][j], vertices[i-1][j]);
               }
               if(i+1 < h){
                   g.insereAresta(vertices[i][j], vertices[i+1][j]);
               }
               if(j-1 > -1){
                   g.insereAresta(vertices[i][j], vertices[i][j-1]);
               }
               if(j+1 < w){
                   g.insereAresta(vertices[i][j], vertices[i][j+1]);
               }
            }
        }
        
        int caminho[] = new int[n];
        Vector<VerticeEstrela> openSet = new Vector<>();
        Vector<VerticeEstrela> closedSet = new Vector<>();
        openSet.add(inicio);
        
        
        // A*
        while (openSet.size() > 0) {
            // Testa com o proximo vertice com menor pontuação
            VerticeEstrela atual = openSet.get(0);
            for (VerticeEstrela v : openSet) {
                if (v.getF() < atual.getF())
                    atual = v;
            }
            
            // se chegou em algum final
            // imprime caminho
            if (atual.getValor() == 3) {
                System.out.println("Cabaooooo0ooo!!!");
                int a = atual.getChave();
                Vector<VerticeEstrela> retaFinal = g.vertices();
                while (a != inicio.getChave()) {
                    a = caminho[a];
                    retaFinal.get(a-1).setC('*');
                    System.out.println(retaFinal.get(a).getX() + " " + retaFinal.get(a).getY());
                }
                retaFinal.get(a-1).setC('2');
                System.out.println("\n");
                
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        VerticeEstrela v = vertices[i][j];
                        System.out.print(v.getC());
                    }
                    System.out.print('\n');
                }
                break;
            }
            
            openSet.remove(atual);
            closedSet.add(atual);
            
            Vector<VerticeEstrela> vizinhanca = g.verticesConectados(atual);
            for (VerticeEstrela vizinho : vizinhanca) {
                // se o vizinho tiver no closedSet ele já foi testado
                // se ele for uma parade n precisa testar
                if (!closedSet.contains(vizinho) && vizinho.getValor() != 1) {
                    int tentativa_g = atual.getG() + 1;
                    
                    if (!openSet.contains(vizinho) || tentativa_g < vizinho.getG()) {
                        // atualiza valores do vizinho
                        vizinho.setG(tentativa_g);
                        vizinho.setH(heuristc(vizinho, saidas));
                        vizinho.setF(vizinho.getG()+vizinho.getH());
                        caminho[vizinho.getChave()] = atual.getChave();
                        
                        // nova melhor possibilidade possivel caminho
                        if (!openSet.contains(vizinho)) openSet.add(vizinho);
                        
                    }
                }
            }
       }
        
    }
    
    
    
    
    public static int heuristc(VerticeEstrela neighbor,Vector<VerticeEstrela> outs) {
        int minDist = Integer.MAX_VALUE;
        for (VerticeEstrela vertex : outs) {
            // Manhattan
            int dist = Math.abs(neighbor.getX() - vertex.getX()) + Math.abs(neighbor.getY() - vertex.getY());
            // Euclidian
            /*float xdif = Math.abs(neighbor.getX() - vertex.getX());
            float ydif = Math.abs(neighbor.getY() - vertex.getY());
            int dist = (int) Math.sqrt((xdif)*(xdif) +(ydif)*(ydif));*/
            if(dist < minDist){
                minDist = dist;
            }
        }
        return minDist;
    }
}
