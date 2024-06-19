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


    //-----------METODOS
    public void inscribir(String estudiante, String carrera, String materia) {
        NodoTrie nodoEstudiante =estudiantes.definirSiVacio(estudiante);//hago esto para no tener que buscar dos veces el mismo nodo para hacer cosas
        nodoEstudiante.definicion = (int)nodoEstudiante.definicion + 1;
        Trie<Materia> materiasDeCarrera = carreras.definicion(carrera);
        Materia objetoMateria = materiasDeCarrera.definicion(materia);
        objetoMateria.agregarAlumno(estudiante);
    }

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


    public int[] plantelDocente(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).Docentes();
    }

    public void cerrarMateria(String materia, String carrera) {
        Materia borrada = carreras.definicion(carrera).definicion(materia);
        borrada.eliminarMateria(estudiantes);//solo elimine los alumnos hasta aca

    }

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
