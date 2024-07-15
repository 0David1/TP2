package aed;

public class Materia {
    
    
    //-----------ATRIBUTOS--------------------------------
    private int [] CargoDocentes ;
    private listaEnlazada<String> libretas;
    private listaEnlazada<TrieStringPar> referencias;
    


    //-----------METODOS----------------------------------

        
    //-----------CONSTRUCTOR
    public Materia(){
        CargoDocentes = new int[4]; //O(4) = 4 * O(1)
        libretas = new listaEnlazada<String>();
        referencias = new listaEnlazada<TrieStringPar>();
    }//complejidad O(1)


    //-------ARMADO DE MATERIA
    public void agregarReferencia(Trie<Materia> trieDeLaMateria, String Materia){
        TrieStringPar valor = new TrieStringPar(trieDeLaMateria, Materia);
        referencias.apilar(valor);
    }//Complejidad : O(1)
    
    public void agregarAlumno(String libreta){
        libretas.apilar(libreta);
    }//Complejidad : O(1)
    
    //----------DOCENTES
    public boolean excedeCupo(){
        int Prof = CargoDocentes[0];
        int JefeTP = CargoDocentes[1];
        int Ay1 = CargoDocentes[2];
        int Ay2 = CargoDocentes[3];
        int cant = libretas.longitud();

        return(250*Prof < cant)  || (100*JefeTP < cant) || (20*Ay1 < cant) || (30*Ay2 < cant);
    }//Complejidad O(1)
    public void CargarDocente(int indice){
        CargoDocentes[indice] ++;
    }//Complejidad O(1)

    public int[] Docentes(){
        return CargoDocentes;
    }//Complejidad O(1)
    
    //---------INSCRIPTOS A MATERIA
    public int numeroDeLibretas(){
        return libretas.longitud();
    }//O(1)


    //-------------ELIMINAR MATERIA
    public void eliminarMateria(Trie<Integer> alumnos){
        String alumno = eliminarLibreta();//O(1)
        while (alumno != null){//O(alumnos inscriptos en la materia)
            alumnos.definir(alumno, alumnos.definicion(alumno) - 1);//O(|1|) alumno esta acotado
            alumno = eliminarLibreta();
        }

        TrieStringPar referencia = eliminarReferencia();//O(1)
        while(referencia != null){//O(cantidad de nombres de la materia)
            Trie<Materia> trie = referencia.trie();//O(1)
            trie.borrar(referencia.nombre());//(|nombreMateria|)
            referencia = eliminarReferencia();//O(1)
        }
    }

    private String eliminarLibreta(){
        return libretas.desapilar();
    } //O(1)
    
    private TrieStringPar eliminarReferencia(){
        return referencias.desapilar();
    } //O(1)

    //En total, tarda la cantidad de nombres de la materia y la cantidad de alumnos registrados, ademas de reducir en uno la cantidad de materias inscriptas para dichos alumnos.

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
    }//Todas las complejidades son O(1)


    //-----------LISTAENLAZADA IMPLEMENTA COLA
    public class listaEnlazada<T>{
        private Nodo Primero;
        private int size;
        private class Nodo{//O(1)
            T libretaUniversitaria;
            Nodo siguiente;
            public Nodo(T alumno){
                libretaUniversitaria = alumno;
                siguiente = null;
            }
        }
        public listaEnlazada(){//O(1)
            Primero = null;
            size = 0;
        }

        public T desapilar(){//O(1)
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
        }//O (1)
        public void apilar(T alumno){
            Nodo nuevo = new Nodo(alumno);
            nuevo.siguiente = Primero;
            Primero = nuevo;
            size ++;
        }//O (1)
        /*
        Invariante de representacion: todo nodo menos el ultimo apunta a otro nodo, y el ultimo apunta a null. Ademas, todo nodo es apuntado por otro, excepto el primero, que no es apuntado por ninguno.
        */

    }
}

/*
Invariante de representacion: en cargoDocente, todo elemento es mayor o igual a cero, cargoDocente es de longitud 4, tamaño de referencias es mayor o igual a 1.
*/
