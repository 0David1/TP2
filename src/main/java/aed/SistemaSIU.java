package aed;
import java.util.*;

public class SistemaSIU {
    enum CargoDocente {
        PROF,
        JTP,
        AY1,
        AY2
    }


    //-------ATRIBUTOS
    private Trie<Integer> estudiantes;
    private Trie<Trie<Materia>> carreras;


    //---------CONSTRUCTOR
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        estudiantes = new Trie<Integer>();
        carreras = new Trie<Trie<Materia>>();

        //--------TRIE ESTUDIANTE---------
        int alumnos = libretasUniversitarias.length;
        for (int i = 0 ; i < alumnos ;i ++){ //O(|alumnos|)
            estudiantes.definir(libretasUniversitarias[i], 0); //O(1) por tamaño acotado
        }


        // Trie carreras --> Trie materias -->Materia
        int cantidadDeMaterias = infoMaterias.length, cantidadDeNombres;
        ParCarreraMateria[] nombresYCarreras;
        String nombreMateria, nombreCarrera;
        for (int materia = 0 ; materia <cantidadDeMaterias; materia++){ //O(|materias|)
            Materia objetoMateria = new Materia();//para objetoMateria, es intencional el uso de aliasing ya que queremos cambiar la direccion de materia para cualquier nombre con la que busques a esa materia.
            nombresYCarreras = infoMaterias[materia].getParesCarreraMateria(); //O(1)
            cantidadDeNombres = nombresYCarreras.length;

            
            for (int knombre = 0; knombre < cantidadDeNombres ; knombre ++){ //O(cantidad de nombres de materia)
                nombreMateria = nombresYCarreras[knombre].getNombreMateria(); //O(1)
                nombreCarrera = nombresYCarreras[knombre].getCarrera(); //O(1)
                Trie<Materia> dondeEs;
                dondeEs = this.carreras.definicion(nombreCarrera);
                if (dondeEs == null) {
                    dondeEs = new Trie<Materia>();
                    this.carreras.definir(nombreCarrera, dondeEs);// define la materia en un nodo del trie de carreras
                }
                else{
                    dondeEs = (Trie<Materia>)this.carreras.definicion(nombreCarrera);
                }
                dondeEs.definir(nombreMateria,objetoMateria); //O(1) al ser el tamaño acotado
                objetoMateria.agregarReferencia(dondeEs, nombreMateria); // lo mismo
                
            }
        }
    }
    /*
    Complejidad: la complejidad de esta funcion se cumple. Para empezar, dividamos la complejidad en 3 componentes:
    La sumatoria de carreras multiplicado por la cantidad de materias de cada una se cumple, ya que se recorre el Trie de carreras la cantidad de veces que hay materias en esa carrera.
    Luego, hay una sumatoria de materias con una sumatoria de la cantidad de nombres de esa materia de la longitud de nombre de la materia, que se cumple ya que se recorre cuando inscribo el par carreraMateria.
    Por ultimo, se suma la cantidad de estudiantes, igual a la creacion de cada uno en el Trie de estudiantes, que es O(1) * E ya que el limite de cantidad de caracteres esta acotado.
    */


    //-----------METODOS
    public void inscribir(String estudiante, String carrera, String materia) {
        int valor= this.estudiantes.definicion(estudiante);
        if (this.estudiantes.definicion(estudiante) == null){
            this.estudiantes.definir(estudiante,1);
        }
        else{
            this.estudiantes.definir(estudiante,valor ++);
        }
        Trie<Materia> materiasDeCarrera = carreras.definicion(carrera);
        Materia objetoMateria = materiasDeCarrera.definicion(materia);
        objetoMateria.agregarAlumno(estudiante);
    }
    /*
    Complejidad: se recorre el Trie carreras (|c|) y luego su Trie de materias asociado (|m|), y se agrega un estudiante al objetoMateria, que como se suma un elemento 
    a la cola (O(1)).Por aliasing el valor cambia no importa con que nombre busquemos la materia.
    Para sumar una inscripcion al estudiante, al ser acotado el Trie de estudiantes y hacer una operacion sobre la definicion, la complejidad es O(1)
    */

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        Materia mate = carreras.definicion(carrera).definicion(materia);
        switch (cargo) {
            case PROF:
                mate.CargarDocente(0);
                break;
            case JTP:
                mate.CargarDocente(1);
                break;
            case AY1:
                mate.CargarDocente(2);
                break;
            case AY2:
                mate.CargarDocente(3);;
                break;
        }
    }

    /*
    Complejidad: se recorre el Trie carreras (|c|) y luego su Trie de materias asociado (|m|) y se suma el tipo de docente deseado que es una operacion (O(1)) al objetoMateria.
    Como las materias equivalente de distintas materias llevan al mismo lugar, con agregar un docente aqui se cumple el requerimiento de sumar uno a sus equivalentes.
    */


    public int[] plantelDocente(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).Docentes();
    }

    /*
    Complejidad: se recorre el Trie carreras (|c|) y luego su Trie de materias asociado (|m|), y devuelve los docentes (O(1)).
    */

    public void cerrarMateria(String materia, String carrera) {
        Materia borrada = carreras.definicion(carrera).definicion(materia);
        borrada.eliminarMateria(estudiantes); //toda la complejidad viene de esta accion, que cumple lo pedido por la consigna como se explica abajo
    }

    /*
    Complejidad: para empezar, la complejidad de |c| se cumple al recorrer el Trie de carreras, y tambien la de |m| al buscar la materia a borrar.
    Luego, como se ve en la clase de Materia, se elimina una por una las referencias de la materia, lo cual es la sumatoria de la longitud de cada nombre.
    Por ultimo, se reduce la cantidad de materias inscriptas a cada alumno de dicha materia por uno, por lo que se recorre su Trie O(1) * Em veces.
    */

    public int inscriptos(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).numeroDeLibretas();
    }
    
    /*
    Complejidad: se recorre el Trie carreras (|c|) y luego su Trie de materias asociado (|m|), y se devuelve la longitud de inscriptos (O(1)).
    */

    public boolean excedeCupo(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).excedeCupo();
    }
    
    /*
    Complejidad: se recorre el Trie carreras (|c|) y luego su Trie de materias asociado (|m|), y se devuelve el resultado de la funcion excedeCupo (O(1), ya que son cuentas matematicas y comparaciones entre booleanos).
    */

    public String[] carreras() {
        ArrayList<String> resultado = new ArrayList<>(carreras.tamaño());
        carreras.listaDeStrings(resultado,"");
        return resultado.toArray(new String[0]);
    }

    /*
    Complejidad: Se recorre el Trie de carreras la cantidad de veces que hay carreras, cumpliendo la complejidad de sumatoria de longitud de la clave carrera para todas las carreras.
    */

    public String[] materias(String carrera) {
        ArrayList<String> resultado = new ArrayList<>(carreras.definicion(carrera).tamaño());
        carreras.definicion(carrera).listaDeStrings(resultado, "");
        return resultado.toArray(new String[0]);
    }

    /*
    Complejidad: lo mismo que el ejercicio anterior, solo que primero se busca la carrera en el Trie de carreras y luego se devuelve las materias del Trie asociado a esta carrera, cumpliendo la complejidad propuesta.
    */

    public int materiasInscriptas(String estudiante) {
        return estudiantes.definicion(estudiante);
    }

    /*
    Complejidad: se recorre el Trie de estudiantes y se devuelve la cantidad de materias inscriptas. Como la longitud de estudiante esta acotado, termina siendo O(1).
    */
}

/*
Invariante de representacion: esta clase, al ser la que incorpora todas las demas, es la mas extensiva de todas, asi que iremos por partes.
Para empezar, todo estudiante, materia y carrera del sistema existe en su Trie correspondiente. Ademas, el Trie de carreras lleva a un Trie de materias, y el de estudiantes a su cantidad de materias inscriptas.
-Todo valor que existe en libretaUniversitarias para cualquier materia, existe tambien en el Trie de estudiantes.
El largo de materia.referencias, para cualquier materia, es menor o igual a la cantidad de carreras, es decir, a la cantidad de definiciones del Trie carreras.
Para todo elemento en InfoMaterias, la carrera pertenece al Trie carreras y el nombre de la materia existe en el Trie materias asociado a esa carrera.
La suma total de la cantidad de inscripciones de todos los estudiantes es igual a la suma total de de la longitud de libretasUniversitarias de las materias.
*/
