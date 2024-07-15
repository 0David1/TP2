package aed;

import java.util.ArrayList;

public class Trie<T> {

    // --------ATRIBUTOS--------------
    private NodoTrie<T> raiz;
    private int cantidadDePalabras;
    private NodoTrie<T> nodoADefinir;

    // --------CONSTRUCTORES--------------
    public Trie() {// constructor del trie
        raiz = new NodoTrie<T>(); //O(1)
    }

    public Trie(NodoTrie<T> nuevaRaiz) {
        raiz = nuevaRaiz; //O(1)
    }
 
    // --------METODOS--------------
    public void definir(String palabra, T valor) {
        NodoTrie<T> actual = raiz;
        for (int i = 0; i < palabra.length(); i++) { //O(|palabra|), y si el tamaño es acotado es O(1)
            char caracterActual = palabra.charAt(i);
            if (actual.caracter(caracterActual) == null) {
                actual.caracteres[(int) caracterActual] = new NodoTrie<>(); //O(1)
            }
            actual = actual.caracter(caracterActual);
        }
        actual.definir(valor);
        cantidadDePalabras++;
    }
    // complejidad: O(|palabra|)

    public T definicion(String palabra) {
        int largoDePalabra = palabra.length();
        NodoTrie<T> actual = raiz;
        for (int i = 0; i < largoDePalabra; i++) { //O(|palabra|)
            char letraActual = palabra.charAt(i);
            actual = actual.caracter(letraActual);
            if (actual == null) {
                return null;
            }
        }
        return actual.definicion();
    }// complejidad O(|palabra|)

    public int tamaño() {
        return cantidadDePalabras;
    }// complejidad O(1)

    // --------RELACIONADO CON EJERCICIO CERRAR MATERIA
    public void borrar(String palabra) {
        raiz = borrar(raiz, palabra, 0);
    }

    private NodoTrie<T> borrar(NodoTrie<T> nodo, String palabra, int contador) {
        if (nodo == null)
            return null; //O(1)

        if (contador == palabra.length()) { //O(|palabra|)
            if (nodo.definicion() != null)
                cantidadDePalabras--; //O(1)
            nodo.definicion = null;
        } else {
            int letraActual = palabra.charAt(contador);
            nodo.caracteres[letraActual] = borrar(nodo.caracteres[letraActual], palabra, contador + 1);
        }

        if (nodo.definicion != null)
            return nodo;

        for (int c = 0; c < 256; c++) { //O(1) al ser acotado
            if (nodo.caracteres[c] != null)
                return nodo;
        }
        return null;
    }
    //O(palabra)

    // --------RELACIONADO CON EJERCICIO CARRERAS Y EJERCICIO MATERIAS
    public void listaDeStrings(ArrayList<String> agregarAca, String raizPalabra) {
        listaDeStrings(agregarAca, raizPalabra, raiz);
    }
    
    private void listaDeStrings(ArrayList<String> agregarAca, String raizPalabra, NodoTrie<T> nodo) {
        if (nodo == null) {
            return;
        }
    
        if (nodo.definicion() != null) {
            agregarAca.add(raizPalabra);
        }
    
        for (int i = 0; i < 256; i++) { //O(1) al ser acotado
            if (nodo.caracteres[i] != null) {
                String nuevaRaiz = raizPalabra + (char) i;
                listaDeStrings(agregarAca, nuevaRaiz, nodo.caracteres[i]);
            }
        }
    }// complejidad: O(sumatoria de longitud de claves en el trie), ya que la funcion
     // recorre todos los nodos de las claves para armar la palabra, en el peor de
     // los casos no tienen ningun caracter en comun.

    // ----------SUBCLASES DE TRIE NECESARIOS------

    // --------NODO--------------
    @SuppressWarnings("hiding")
    class NodoTrie<T> {
        private NodoTrie<T>[] caracteres;
        private T definicion;

        @SuppressWarnings("unchecked")
        public NodoTrie() {
            caracteres = new NodoTrie[256];
            definicion = null;
        }

        public T definicion() {
            return definicion;
        }

        public void definir(T nuevaDef) {
            definicion = nuevaDef;
        }

        public NodoTrie<T> caracter(char p) {
            return this.caracteres[(int) p];
        }

        public NodoTrie<T>[] caracteres(){
            return this.caracteres;
        }
    }
}

/*
 * Invariante de representacion: la cantidad de palabras, o definiciones, es
 * menor o igual a la cantidad de nodos.
 * Ademas, todo nodo o lleva a una unica definicion o a otro nodo o nodos, a
 * menos que el trie no tenga ninguna definicion.
 */
