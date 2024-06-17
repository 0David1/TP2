package aed;


public class Materia {
    //atributos de Materia
    int [] CargoDocentes ;
    listaEnlazada<String> libretas;
    listaEnlazada<TrieStringPar> referencias;//con esto guardo la refencia de los tries que apuntan a esta materia
   
    public Materia(){//generador de materia
        CargoDocentes = new int[4];
        libretas = new listaEnlazada<String>();
        referencias = new listaEnlazada<TrieStringPar>();
    }
    
    public void agregarReferencia(Trie<Materia> trieDeLaMateria, String Materia){//agrego referencia a la pila
        TrieStringPar valor = new TrieStringPar(trieDeLaMateria, Materia);
        referencias.apilar(valor);
    }
    
    public void agregarAlumno(String libreta){//agrego alumno a la pila
        libretas.apilar(libreta);
    }

    public void CargarDocente(int indice){//agrego docente de tipo segun el indice.
        CargoDocentes[indice] ++;
    }

    public int[] docentes(){//devuelo la lista de cantidad de
        return CargoDocentes;
    }
    public String eliminarLibreta(){//lo voy a usar para borrar esta materia
        return libretas.desapilar();
    }
    public TrieStringPar eliminarReferencia(){
        return referencias.desapilar();//esto es un triestring par con eso tengo que acceder al trie y eliminar esa palabra del "diccionario"
    }

    public class TrieStringPar{//cree un tipo de dato que relaciona el trie donde esta uno de los nombres de la materia, con el nombre que deberia estar.
        String NombreDeLaMateria;
        Trie<Materia> TrieDondeEsta;
        public TrieStringPar(Trie<Materia> Materias, String nombreEspecifico){
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

        public int longitud (){//importante cuando necesitamos saber la longitud de la lista(saber la cantidad de alumnos inscriptos)
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


/*Resumen de Materia : 
tiene tres atributos
   1- lista enlazada de libretas universitarias
   2- lista enlazada de una clase llamada TrieStringPar que guarda un nombre de la materia, y el trie de donde tengo que borrarlo
   3- una int[] donde guardo la cantidad de docentes por cargo.
tengo metodos:
    1-generador: inicia todos los atributos vacios, o con ceros
    2-agregarReferencia : suma a la pila de TrieStringPar, dandole el string del nombre de la materia especifico, y el trie donde buscarlo como parametro.
    3- agregarAlumno : idem anterior pero apila Strings, y necesita a String libreta como parametro.
    4- cargar Docente : suma uno al indice del cargo del profesor que voy a agregar (0 = Profesor; 1 = JTP ; 2 = AY1; 3 = AY2);(se rompe si salgo de esos indices)
    5- docentes : devuelve int[] que es atributo, arreglar aliasing debe primero generar una copia y despues devolverla.
    6-eliminarReferencia : desapila 1 la lista de referencias y devuelve el elemento TrieStringPar "tope".
    7-eliminarLibreta : desapila el tope de libretas Universitarias inscriptas a esta materia y devuelve el string de esa libreta.
*/