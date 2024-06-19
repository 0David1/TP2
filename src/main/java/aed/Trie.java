package aed;

import java.util.ArrayList;

public class Trie<T> {
        
    
        //--------ATRIBUTOS--------------
        private NodoTrie<T> raiz;
        private int cantidadDePalabras; 
        
        
        //--------CONSTRUCTORES--------------
        public Trie(){//constructor del trie
            raiz = new NodoTrie<T>();
        }
        public Trie(NodoTrie<T> nuevaRaiz){
            raiz = nuevaRaiz;
        }


        //--------METODOS--------------
        public void definir(String palabra, T valor){
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
        
        public NodoTrie<T> definirSiVacio(String palabra){
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
                if (actual == null){
                    return null;
                }
            }
            return actual.definicion;
        }


        public int tamaño(){
            return cantidadDePalabras;
        }


        //--------RELACIONADO CON EJERCICIO CERRAR MATERIA
        public void borrar(String palabra){
            raiz = borrar(raiz, palabra, 0);
        }

        private NodoTrie<T> borrar(NodoTrie<T> nodo, String palabra, int contador){
            if(nodo == null) return null;

            if(contador == palabra.length() ){
                if(nodo.definicion != null) cantidadDePalabras--;
                nodo.definicion = null;
            }
            else {
                int letraActual = palabra.charAt(contador);
                nodo.caracteres[letraActual] = borrar(nodo.caracteres[letraActual], palabra, contador+1);
            }

            if(nodo.definicion != null) return nodo;
            
            for (int c = 0; c < 256; c++){
                if(nodo.caracteres[c] != null) return nodo;
            }
            return new NodoTrie<T>();
        }

        //--------RELACIONADO CON EJERCICIO CARRERAS Y EJERCICIO MATERIAS
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

        //----------SUBCLASES DE TRIE NECESARIOS------

        
        //--------NODO--------------
        @SuppressWarnings("hiding")
        public class NodoTrie<T>{
            NodoTrie<T>[] caracteres;
            T definicion;
            @SuppressWarnings("unchecked")
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
