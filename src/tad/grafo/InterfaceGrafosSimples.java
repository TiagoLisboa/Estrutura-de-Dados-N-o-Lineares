package tad.grafo;


import java.util.Vector;
import tad.grafo.Arestas;
import tad.grafo.Vertices;

/*
 * Created on 17/04/2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * @author Robinson
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface InterfaceGrafosSimples {

    //private int qtdVertices;
    //private Vector vertices;
    //private Arestas matrizAdj[][];
    //public GrafoSimples()
    public abstract void inserirVertice(Vertices Vertice);

    public abstract void removerVertice(Vertices Vertice);

    public abstract Arestas insereAresta(Vertices VerticeUm, Vertices VerticeDois,
            double valor);

    public abstract Arestas insereAresta(Vertices VerticeUm, Vertices VerticeDois);

    public abstract void removeAresta(Arestas Aresta);

    public abstract Arestas insereArco(Vertices VerticeUm, Vertices VerticeDois,
            double valor);

    public abstract Arestas insereArco(Vertices VerticeUm, Vertices VerticeDois);

    public abstract void removeArco(Arestas Aresta);

    public abstract int grau(Vertices Vertice);

    public abstract int ordem();

    public abstract Vector vertices();

    public abstract Vector arestas();

    public abstract Vector arestasIncidentes(Vertices vertice);

    public abstract Vector finalVertices(Arestas a);

    public abstract Vertices oposto(Vertices v, Arestas a) throws OpostoError;

    public abstract boolean éAdjacente(Vertices v, Vertices w);
}
