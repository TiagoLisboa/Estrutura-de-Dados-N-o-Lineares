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
public class VerticeEstrela extends Vertices {
    private int x;
    private int y;
    private char c;
    private int h; // pontuação heuristica
    private int g; // custo para chegar nesse nó
    private int f; // pontuação total (g + h)

    public VerticeEstrela(int chave, double valor, int x, int y, int h, int g, int f) {
        super(chave, valor);
        this.x = x;
        this.y = y;
        this.h = h;
        this.g = g;
        this.f = f;
        this.c = '0';
    }

    public VerticeEstrela(int chave, double valor, int x, int y, int h, int g, int f, char c) {
        super(chave, valor);
        this.x = x;
        this.y = y;
        this.c = c;
        this.h = h;
        this.g = g;
        this.f = f;
    }
    
    

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }
    
    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
    
    
    
}
