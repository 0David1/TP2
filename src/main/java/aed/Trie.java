package aed;

import java.util.ArrayList;

public class Trie<T> {
        //--------ATRIBUTOS--------------
        private NodoTrie<T> raiz;
        private int cantidadDePalabras; //numero de palabras en el arbol
        //--------CONSTRUCTORES--------------
        public Trie(){//constructor del trie
            raiz = new NodoTrie<T>();
        }
        public Trie(NodoTrie<T> nuevaRaiz){//constructor para cuando quiero hacer recursion.
            raiz = nuevaRaiz;
        }
        //--------METODOS--------------
        public void definir(String palabra, T valor){//funcion define palabra en el trie
            int largoDePalabra = palabra.length();
            NodoTrie<T> actual = raiz;
            for (int i = 0; i < largoDePalabra; i ++){
                if (actual.caracteres[(int)palabra.charAt(i)] == null){
                    actual.caracteres[(int)palabra.charAt(i)] = new NodoTrie<T>();
                    actual = actual.caracteres[(int)palabra.charAt(i)];
                }
                else {
                    actual = actual.caracteres[(int)palabra.charAt(i)];
                }
            }
            actual.definicion = valor;
            cantidadDePalabras++;
        }   
        public NodoTrie<T> definirSiVacio(String palabra){//si el camino a la definicion no esta hecho lo hace, si no devuelve el ultimo nodo donde deberia estar la definicion.
            int largoDePalabra = palabra.length();
            NodoTrie<T> actual = raiz;
            for (int i = 0; i < largoDePalabra; i++){
                if (actual.caracteres[(int)palabra.charAt(i)] == null){
                    actual.caracteres[(int)palabra.charAt(i)] = new NodoTrie<T>();
                    actual = actual.caracteres[(int)palabra.charAt(i)];
                }
                else {
                    actual = actual.caracteres[(int)palabra.charAt(i)];
                }
            }
            if (actual.definicion == null){
                cantidadDePalabras ++;
            }
            return actual;
        }

        public T definicion (String palabra){
            int largoDePalabra = palabra.length();
            NodoTrie<T> actual = raiz;
            for (int i = 0; i < largoDePalabra; i ++){
                actual = actual.caracteres[(int)palabra.charAt(i)];
                if (actual == null){//si la palabra no esta definida devuelve null.
                    return null;
                }
            }
            return actual.definicion;//actual.definicion puede ser null si el nodo no tiene definido nada, chequear en las funciones.
        }


        public int tamaño(){
            return cantidadDePalabras;
        }

        public void borrar(String palabra){
            raiz = borrar(raiz, palabra, 0);
        }
        public NodoTrie<T> raiz(){
            return raiz;
        }

        private NodoTrie<T> borrar(NodoTrie<T> x, String palabra, int contador){
            if(x == null) return null;
            if(contador == palabra.length()){
                if(x.definicion != null) cantidadDePalabras--;
                x.definicion = null;

            }
            else {
                int letraActual = palabra.charAt(contador);
                x.caracteres[letraActual] = borrar(x.caracteres[letraActual], palabra, contador+1);
            }

            if(x.definicion != null) return x;
            
            for (int c = 0; c < 128; c++){
                if(x.caracteres[c] != null) return x;
            }
            return null;
        }

        public void listaDeStrings(ArrayList<String> agregarAca, String raizPalabra, int longitud){
            NodoTrie<T> actual = raiz;
            String nuevaRaiz;

            for (int i = 0; i < longitud; i++){
                if (actual.caracteres[i] != null){
                    nuevaRaiz = raizPalabra + String.valueOf((char)i);
                    if (actual.caracteres[i].definicion != null){
                        agregarAca.add(nuevaRaiz);
                    }
                    Trie<T> buscarAca =new Trie<T>(actual.caracteres[i]);
                    buscarAca.listaDeStrings(agregarAca, nuevaRaiz, longitud);
                }
            }
        }


        //--------NODO--------------
        @SuppressWarnings("hiding")//evita un warning
        public class NodoTrie<T>{
            NodoTrie<T>[] caracteres;
            T definicion;
            @SuppressWarnings("unchecked")//evita un warning
            public NodoTrie(){  
                caracteres = new NodoTrie[256];
                definicion = null;
            }
            public T definicion(){
                return definicion;
            }
            public void definir(T nuevaDef){
                definicion = nuevaDef;
            }
        }
}
