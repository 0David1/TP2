package aed;

public class SistemaSIU {
    enum CargoDocente{
        PROF,
        JTP,
        AY1,
        AY2
    }

    Trie<Integer> estudiantes;
    Trie<Trie<Materia>> carreras;

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        estudiantes = new Trie<Integer>();
        carreras = new Trie<Trie<Materia>>();
        
        //Trie Alumnos
        int i = 0;
        while(i < libretasUniversitarias.length){//esto se ejecuta E veces (E * dentro del ciclo)
            estudiantes.definir(libretasUniversitarias[i],0); // O (1) ya que las la longitud de las libretas universitarias es acotada.
            i++;
        }//Total = O (E)
        
        //Trie carreras --> Trie materias -->Materia
        int j = 0;
        int cantidadDeMaterias = infoMaterias.length;
        String nombre_carrera;
        String nombre_materia;
        Materia objetoMateria;
        while(j < cantidadDeMaterias){//O(M) , cantidad de materias
            int k = 0;
            int cantidadDeNombresDeLaMateria = infoMaterias[j].getParesCarreraMateria().length;
            while(k < cantidadDeNombresDeLaMateria){//O(N_m) , k iterar por la cantidad de nombres de la materia
                nombre_carrera = infoMaterias[j].getParesCarreraMateria()[k].getCarrera(); 
                nombre_materia = infoMaterias[j].getParesCarreraMateria()[k].getNombreMateria(); //creamos dos variables distintas para guardar los nombres de carrera y materia
                if(carreras.definicion(nombre_carrera) == null){ //si no existe la carrera, la creamos con su Trie asociado a materias
                    carreras.definir(nombre_carrera, new Trie<Materia>());
                }
                k ++;
                objetoMateria = new Materia();
                carreras.definicion(nombre_carrera).definir(nombre_materia, objetoMateria); //definimos la materia nueva
                objetoMateria.agregarReferencia(carreras.definicion(nombre_carrera), nombre_materia);
            }
            j++;
        }//O(M*N_m)
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
