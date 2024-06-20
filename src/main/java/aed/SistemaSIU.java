package aed;
import java.util.*;
import aed.Trie.NodoTrie;

public class SistemaSIU {
    enum CargoDocente {
        PROF,
        JTP,
        AY1,
        AY2
    }


    //-------ATRIBUTOS
    Trie<Integer> estudiantes;
    Trie<Trie<Materia>> carreras;


    //---------CONSTRUCTOR
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        estudiantes = new Trie<Integer>();
        carreras = new Trie<Trie<Materia>>();

        //--------TRIE ESTUDIANTE---------
        int alumnos = libretasUniversitarias.length;
        for (int i = 0 ; i < alumnos ;i ++){
            estudiantes.definir(libretasUniversitarias[i], 0);
        }


        // Trie carreras --> Trie materias -->Materia
        int cantidadDeMaterias = infoMaterias.length, cantidadDeNombres;// los limites de los iteradores anteriores
        ParCarreraMateria[] nombresYCarreras;
        String nombreMateria, nombreCarrera;
        for (int materia = 0 ; materia <cantidadDeMaterias; materia++){
            Materia objetoMateria = new Materia();// tengo que unir todos los nombres a este objeto
            nombresYCarreras = infoMaterias[materia].getParesCarreraMateria();
            cantidadDeNombres = nombresYCarreras.length;
            NodoTrie nodoADefinir;
            for (int knombre = 0; knombre < cantidadDeNombres ; knombre ++){
                nombreMateria = nombresYCarreras[knombre].getNombreMateria();
                nombreCarrera = nombresYCarreras[knombre].getCarrera();
                nodoADefinir = carreras.definirSiVacio(nombreCarrera);
                Trie<Materia> dondeEs;
                if (nodoADefinir.definicion() == null) {
                    dondeEs = new Trie<Materia>();
                    nodoADefinir.definir(dondeEs);
                }
                else{
                    dondeEs = (Trie<Materia>)nodoADefinir.definicion();
                }
                dondeEs.definir(nombreMateria,objetoMateria);
                objetoMateria.agregarReferencia(dondeEs, nombreMateria);
            }
        }
    }
    /*
    Complejidad: la complejidad de esta funcion se cumple. Para empezar, dividamos la complejidad en 3 componentes:
    La sumatoria de carreras multiplicado por la cantidad de materias de cada una se cumple, ya que se recorre el Trie de carreras la cantidad de veces que hay materias.
    Luego, hay una sumatoria de materias con una sumatoria de la cantidad de referencias, que se hace con objetoMateria.agregarReferencia, que se ejecuta en el for una cantidad de veces igual a la cantidad de nobmres.
    Por ultimo, se suma la cantidad de estudiantes, igual a la creacion de cada uno en el Trie de estudiantes, que es O(1) * E ya que el limite de caracteres esta acotado.
    */


    //-----------METODOS
    public void inscribir(String estudiante, String carrera, String materia) {
        NodoTrie nodoEstudiante =estudiantes.definirSiVacio(estudiante);//hago esto para no tener que buscar dos veces el mismo nodo para hacer cosas
        nodoEstudiante.definicion = (int)nodoEstudiante.definicion + 1;
        Trie<Materia> materiasDeCarrera = carreras.definicion(carrera);
        Materia objetoMateria = materiasDeCarrera.definicion(materia);
        objetoMateria.agregarAlumno(estudiante);
    }
    /*
    Complejidad: se cumple la complejidad ya que se recorre el Trie de carrera, y luego su Trie de materias asocidado, y las funciones dentro de estas son O(1).
    Al ser acotado el Trie de estudiantes y hacer una operacion de O(1), no hace falta mencionarlo. (O(|c| + |m|))
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
    Complejidad: se recorre el Trie de carreras, y luego su Trie de materias asociado y se suma el tipo de docente deseado. (O(|c| + |m|))
    Como las materias equivalente de distintas materias llevan al mismo lugar, con agregar un docente aqui se cumple el requerimiento de sumar uno a sus equivalentes.
    */


    public int[] plantelDocente(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).Docentes();
    }

    /*
    Complejidad: se recorre el Trie de carreras, y luego su Trie de materias asociado, y devuelve los docentes, esta ultima siendo O(1).
    (O(|c| + |m|))
    */

    public void cerrarMateria(String materia, String carrera) {
        Materia borrada = carreras.definicion(carrera).definicion(materia);
        borrada.eliminarMateria(estudiantes);//solo elimine los alumnos hasta aca
    }

    /*
    Complejidad: 
    */

    public int inscriptos(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).Libretas().longitud();
    }

    public boolean excedeCupo(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).excedeCupo();
    }

    public String[] carreras() {
        ArrayList<String> resultado = new ArrayList<>();
        carreras.listaDeStrings(resultado,"");
        return resultado.toArray(new String[0]);
    }

    public String[] materias(String carrera) {
        ArrayList<String> resultado = new ArrayList<>();
        carreras.definicion(carrera).listaDeStrings(resultado, "");
        return resultado.toArray(new String[0]);
    }

    public int materiasInscriptas(String estudiante) {
        return estudiantes.definicion(estudiante);
    } 
}

/*
Invariante de representacion: esta clase, al ser la que incorpora todas las demas, es la mas extensiva de todas, asi que iremos por partes.
Para empezar, todo estudiante, materia y carrera del sistema existe en su Trie correspondiente. Ademas, el Trie de carreras lleva a un Trie de materias, y el de estudiantes a su cantidad de materias inscriptas.
Todo valor que existe en libretaUniversitarias para cualquier materia, existe tambien en el Trie de estudiantes.
El largo de materia.referencias, para cualquier materia, es menor o igual a la cantidad de carreras, es decir, a la cantidad de definiciones del Trie carreras.
Para todo elemento en InfoMaterias, la carrera pertenece al Trie carreras y el nombre de la materia existe en el Trie materias asociado a esa carrera.
La suma total de la cantidad de inscripciones de todos los estudiantes es igual a la suma de la longitud de libretasUniversitarias.
*/
