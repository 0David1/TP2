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
        String alumno = eliminarLibreta();
        while (alumno != null){
            alumnos.definirSiVacio(alumno);
            alumnos.getNodoADefinir().definir((int)alumnos.getNodoADefinir().definicion() - 1);
            alumno = eliminarLibreta();
        }

        TrieStringPar referencia = eliminarReferencia();
        while(referencia != null){
            Trie<Materia> trie = referencia.trie();
            trie.borrar(referencia.nombre());
            referencia = eliminarReferencia();
        }
    }

    private String eliminarLibreta(){
        return libretas.desapilar();
    } //O(|libretas|)
    
    private TrieStringPar eliminarReferencia(){
        return referencias.desapilar();
    } //O(|referencias|)

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
        /*
        Invariante de representacion: todo nodo menos el ultimo apunta a otro nodo, y el ultimo apunta a null. Ademas, todo nodo es apuntado por otro, excepto el primero, que no es apuntado por ninguno.
        */

    }
}

/*
Invariante de representacion: en cargoDocente, todo elemento es mayor o igual a cero, cargoDocente es de longitud 4, tamaño de referencias es mayor o igual a 1.
*/
