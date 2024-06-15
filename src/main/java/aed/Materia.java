package aed;

import aed.SistemaSIU.Trie;

public class Materia {
     int [] CargoDocentes ;
        listaEnlazada<String> libretas;
        listaEnlazada<TrieStringPar> referencias;//con esto guardo la refencia de los tries que apuntan a esta materia

        public Materia(){
            CargoDocentes = new int[4];
            libretas = new listaEnlazada<String>();
            referencias = new listaEnlazada<TrieStringPar>();
        }
        
        public void agregarReferencia(Trie<String> trieDeLaMateria, String Materia){//agrego referencia a la pila
            TrieStringPar valor = new TrieStringPar(trieDeLaMateria, Materia);
            referencias.apilar(valor);
        }
        
        public void agregarAlumno(String libreta){//agrego alumno a la pila
            libretas.apilar(libreta);
        }
        public void CargarDocente(int indice){//agrego 
            CargoDocentes[indice] ++;
        }
        public int[] docentes(){//devuelo la lista de cantidad de
            return CargoDocentes;
        }

        public class TrieStringPar{//cree un tipo de dato que relaciona el trie donde esta uno de los nombres de la materia, con el nombre que deberia estar.
            String NombreDeLaMateria;
            Trie<String> TrieDondeEsta;
            public TrieStringPar(Trie<String> Materias, String nombreEspecifico){
                NombreDeLaMateria = nombreEspecifico;
                TrieDondeEsta = Materias;
            }
        }

        public class listaEnlazada<T>{//aca guardo las libretas universitarias, y guardo las referencias junto el nombre de la materia.
            private Nodo Primero;
            private int size;
            private class Nodo{
                T libretaUniversitaria;
                Nodo siguiente;
                public Nodo(T alumno){
                    libretaUniversitaria = alumno;
                    siguiente = null;
                }
            }
            public listaEnlazada(){
                Primero = null;
                size = 0;
            }

            public T desapilar(){
                Nodo actual = Primero;
                if (actual != null){
                    Primero = actual.siguiente;
                    return actual.libretaUniversitaria;
                }
                else{
                    return null;
                }
            }

            public int longitud (){
                return size;
            }
            public void apilar(T alumno){
                Nodo nuevo = new Nodo(alumno);
                nuevo.siguiente = Primero;
                Primero = nuevo;
                size ++;
            }
        }
}
