package aed;

import aed.Trie.NodoTrie;

public class Materia {
    
    
    //-----------ATRIBUTOS--------------------------------
    int [] CargoDocentes ;
    listaEnlazada<String> libretas;
    listaEnlazada<TrieStringPar> referencias;//con esto guardo la refencia de los tries que apuntan a esta materia
    


    //-----------METODOS----------------------------------

        
    //-----------CONSTRUCTOR
    public Materia(){//generador de materia
        CargoDocentes = new int[4];
        libretas = new listaEnlazada<String>();
        referencias = new listaEnlazada<TrieStringPar>();
    }


    //-------ARMADO DE MATERIA
    public void agregarReferencia(Trie<Materia> trieDeLaMateria, String Materia){
        TrieStringPar valor = new TrieStringPar(trieDeLaMateria, Materia);
        referencias.apilar(valor);
    }
    
    public void agregarAlumno(String libreta){
        libretas.apilar(libreta);
    }
    
    //----------DOCENTES
    public boolean excedeCupo(){
        int Prof = CargoDocentes[0];
        int JefeTP = CargoDocentes[1];
        int Ay1 = CargoDocentes[2];
        int Ay2 = CargoDocentes[3];
        int cant = libretas.longitud();

        return(250*Prof < cant)  || (100*JefeTP < cant) || (20*Ay1 < cant) || (30*Ay2 < cant);
    }
    public void CargarDocente(int indice){
        CargoDocentes[indice] ++;
    }
    public int[] docentes(){
        return CargoDocentes;
    }



    //-------------ELIMINAR MATERIA
    public void eliminarMateria(Trie<Integer> alumnos){
        String alumno = eliminarLibreta();
        NodoTrie nodo;
        while (alumno != null){
            nodo = alumnos.definirSiVacio(alumno);
            nodo.definicion =(int)nodo.definicion - 1;
            alumno = eliminarLibreta();
        }

        TrieStringPar referencia = eliminarReferencia();
        while(referencia != null){
            Trie<Materia> trie = referencia.trie();
            trie.borrar(referencia.nombre());
            referencia = eliminarReferencia();
        }
    }
    public String eliminarLibreta(){
        return libretas.desapilar();
    }
    public TrieStringPar eliminarReferencia(){
        return referencias.desapilar();
    }


    //-----------SUBCLASES NECESARIOS DE MATERIA-----------------------


    //-----------TRIESTRINGPAR
    public class TrieStringPar{
        private String NombreDeLaMateria;
        private Trie<Materia> TrieDondeEsta;
        public TrieStringPar(Trie<Materia> Materias, String nombreEspecifico){
            NombreDeLaMateria = nombreEspecifico;
            TrieDondeEsta = Materias;
        }
        public String nombre(){
            return NombreDeLaMateria;
        }
        public Trie<Materia> trie(){
            return TrieDondeEsta;
        }
    }


    //-----------LISTAENLAZADA IMPLEMENTA COLA
    public class listaEnlazada<T>{
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
