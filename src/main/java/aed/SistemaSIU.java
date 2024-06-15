package aed;
import java.util.*;

public class SistemaSIU {


    public class Trie<T>{
        NodoTrie raiz;

        public Trie(){//constructor del trie
            raiz = new NodoTrie();
        }
        public Trie(NodoTrie nuevaRaiz){//constructor para cuando quiero hacer recursion.
            raiz = nuevaRaiz;
        }

        public void definir(String palabra, T valor){//funcion define palabra en el trie
            int largoDePalabra = palabra.length();
            NodoTrie actual = raiz;
            for (int i = 0; i < largoDePalabra; i ++){
                if (actual.caracteres[(int)palabra.charAt(i)] == null){
                    actual.caracteres[(int)palabra.charAt(i)] = new NodoTrie();
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
            NodoTrie actual = raiz;
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
            NodoTrie actual = raiz;

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

        public class NodoTrie{
            NodoTrie[] caracteres;
            T definicion;
            public NodoTrie(){  
                caracteres = new NodoTrie[256];
                definicion = null;
            }
        }
    }
    enum CargoDocente{
        PROF,
        JTP,
        AY1,
        AY2
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void inscribir(String estudiante, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int[] plantelDocente(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public boolean excedeCupo(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }
}
