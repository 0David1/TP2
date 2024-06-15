package aed;

public class SistemaSIU {


    public class Trie<T>{
        //funcion para devolver la definicion de una palabra
        //funcion que devuelve el contenido del trie en una secuencia de strings.
        NodoTrie raiz;

        public Trie(){//constructor del trie
            raiz = new NodoTrie();
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
                if (actual == null){
                    return null;
                }
                // if (actual.caracteres[(int)palabra.charAt(i)] == null){
                //     actual.caracteres[(int)palabra.charAt(i)] = new NodoTrie();
                //     actual = actual.caracteres[(int)palabra.charAt(i)];
                // }
                // else {
                //     actual = actual.caracteres[(int)palabra.charAt(i)];
                // }
            }
            return actual.definicion;
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




    public class Materia {
        int [] CargoDocentes ;
        // otro atributo para la lista enlazada de libretas universitarias
        //otro atributo para los tries de donde se llega a esta materia
        public Materia(){

        }
    }


    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
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
