package aed;

import java.util.ArrayList;

public class Trie<T> {
        NodoTrie<T> raiz;

        public Trie(){//constructor del trie
            raiz = new NodoTrie<T>();
        }
        // public Trie(NodoTrie nuevaRaiz){//constructor para cuando quiero hacer recursion.
        //     raiz = nuevaRaiz;
        // }

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

        public String[] listaDeStrings(){
            ArrayList<String> resultado = new ArrayList<>();
            NodoTrie<T> actual = raiz;

            // int largoDeCaracteres = actual.caracteres.length;
            // for (int i = 0 ; i < largoDeCaracteres ; i ++){
            //     if (actual.caracteres[i] != null){
            //         StringBuffer palabra = new StringBuffer();
            //         palabra.append((char) i);
            //         if (actual.)
            //     }
            //     else{

            //     }
            // }
            return resultado.toArray(new String[0]);
        }

        public class NodoTrie<T>{
            NodoTrie<T>[] caracteres;
            T definicion;
            public NodoTrie(){  
                caracteres = new NodoTrie[128];
                definicion = null;
            }
        }
}
