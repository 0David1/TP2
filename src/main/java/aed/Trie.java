package aed;

import java.util.ArrayList;

public class Trie<T> {
        private NodoTrie<T> raiz;
        private int cantidadDePalabras; //numero de palabras en el arbol

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
            cantidadDePalabras++;
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


        public int ultimoNodoAborrar(NodoTrie<T> nodo){
            int largo = nodo.caracteres.length;
            int cantidad = 0;
            for (int i = 0 ; i < largo ; i ++){
                if (nodo.caracteres[i] != null) cantidad ++;
            }
            return cantidad;
        }//devuelve true si el nodo tiene mas de un hijo


        public int tamaño(){
            return cantidadDePalabras;
        }

        public void borrar(String palabra){
            raiz = borrar(raiz, palabra, 0);
        }


        private NodoTrie<T> borrar(NodoTrie<T> x, String palabra, int contador){
            if (ultimoNodoAborrar(x) >1){
                NodoTrie<T> ultimoNodo = x;
            }
            if(x == null) return null;
            if(contador == palabra.length()){
                if(x.definicion != null) cantidadDePalabras--;
                x.definicion = null;//falta eliminar todos los nodos que lllevan a ese null , es info basura.

            }
            else {
                int letraActual = palabra.charAt(contador);//lo pase a int en un mismo paso.
                x.caracteres[letraActual] = borrar(x.caracteres[letraActual], palabra, contador+1);//todo el tiempo elimino la palabra completa, tengo que cortar la letra que ya recorri
            }

            if(x.definicion != null) return x;
            
            for (int c = 0; c < 128; c++){
                if(x.caracteres[c] != null) return x;
            }
            return null;
        }

        public void borrarInfoBasura(NodoTrie<T> desdeEsteNodo, String palabra){
            NodoTrie<T> aPartir = raiz;
            int limite = palabra.length();
            int i = 0;
            int caracter;
            while (i < limite && aPartir!= desdeEsteNodo ){
                caracter = palabra.charAt(i);
                aPartir = aPartir.caracteres[caracter];
                i++;
            }
            caracter = palabra.charAt(i+1);
            aPartir.caracteres[caracter] = null;
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

        @SuppressWarnings("hiding")//evita un warning
        public class NodoTrie<T>{
            NodoTrie<T>[] caracteres;
            T definicion;
            @SuppressWarnings("unchecked")//evita un warning
            public NodoTrie(){  
                caracteres = new NodoTrie[128];
                definicion = null;
            }
        }
}
