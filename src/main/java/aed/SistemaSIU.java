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
        int i = 0;
        while(i < libretasUniversitarias.length){//esto se ejecuta E veces (E * dentro del ciclo)
            estudiantes.definir(libretasUniversitarias[i],0); // O (1) ya que las la longitud de las libretas universitarias es acotada.
            i++;
        }
        int j = 0;
        while(j < infoMaterias.length){
            int k = 0;
            while(k < infoMaterias[j].getParesCarreraMateria().length){
                String nombre_carrera = infoMaterias[j].getParesCarreraMateria()[k].getCarrera(); 
                String nombre_materia = infoMaterias[j].getParesCarreraMateria()[k].getNombreMateria(); //creamos dos variables distintas para guardar los nombres de carrera y materia
                if(carreras.definicion(nombre_carrera) == null){ //si no existe la carrera, la creamos con su Trie asociado a materias
                    carreras.definir(nombre_carrera, new Trie<Materia>());
                }
                k ++;
                carreras.definicion(nombre_carrera).definir(nombre_materia, new Materia()); //definimos la materia nueva
            }
            j++;
        }
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
