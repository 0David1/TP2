package aed;

import java.util.ArrayList;

public class Trie<T> {

    // --------ATRIBUTOS--------------
    private NodoTrie<T> raiz;
    private int cantidadDePalabras;
    private NodoTrie<T> nodoADefinir;

    // --------CONSTRUCTORES--------------
    public Trie() {// constructor del trie
        raiz = new NodoTrie<T>();
    }

    public Trie(NodoTrie<T> nuevaRaiz) {
        raiz = nuevaRaiz;
    }

    public NodoTrie<T> getNodoADefinir() {
        return this.nodoADefinir;
    }

    public void setNodoADefinir(NodoTrie<T> n) {
        this.nodoADefinir = n;
    }

    // --------METODOS--------------
    public void definir(String palabra, T valor) {
        NodoTrie<T> actual = raiz;
        for (int i = 0; i < palabra.length(); i++) {
            char caracterActual = palabra.charAt(i);
            if (actual.caracter(caracterActual) == null) {
                actual.caracteres[(int) caracterActual] = new NodoTrie<>();
            }
            actual = actual.caracter(caracterActual);
        }
        actual.definir(valor);
        cantidadDePalabras++;
    }
    // complejidad: O(|palabra|)

    public NodoTrie<T> definirSiVacio(String palabra) {
        int largoDePalabra = palabra.length();
        NodoTrie<T> actual = raiz;
        for (int i = 0; i < largoDePalabra; i++) {
            if (actual.caracteres[(int) palabra.charAt(i)] == null) {
                actual.caracteres[(int) palabra.charAt(i)] = new NodoTrie<T>();
                actual = actual.caracteres[(int) palabra.charAt(i)];
            } else {
                actual = actual.caracteres[(int) palabra.charAt(i)];
            }
        }
        if (actual.definicion() == null) {
            cantidadDePalabras++;
        }
        setNodoADefinir(actual);
        return actual;
    }
    // complejidad: O(|palabra|)

    public T definicion(String palabra) {
        int largoDePalabra = palabra.length();
        NodoTrie<T> actual = raiz;
        for (int i = 0; i < largoDePalabra; i++) {
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
            return null;

        if (contador == palabra.length()) {
            if (nodo.definicion() != null)
                cantidadDePalabras--;
            nodo.definicion = null;
        } else {
            int letraActual = palabra.charAt(contador);
            nodo.caracteres[letraActual] = borrar(nodo.caracteres[letraActual], palabra, contador + 1);
        }

        if (nodo.definicion != null)
            return nodo;

        for (int c = 0; c < 256; c++) {
            if (nodo.caracteres[c] != null)
                return nodo;
        }
        return null;
    }

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
    
        for (int i = 0; i < 256; i++) {
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
