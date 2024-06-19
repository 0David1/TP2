package aed;

import aed.Trie.NodoTrie;

public class SistemaSIU {
    enum CargoDocente {
        PROF,
        JTP,
        AY1,
        AY2
    }

    Trie<Integer> estudiantes;
    Trie<Trie<Materia>> carreras;








    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        estudiantes = new Trie<Integer>();
        carreras = new Trie<Trie<Materia>>();

        // Trie Alumnos
        int i = 0;
        while (i < libretasUniversitarias.length) {// esto se ejecuta E veces (E * dentro del ciclo)
            estudiantes.definir(libretasUniversitarias[i], 0); // O (1) ya que las la longitud de las libretas
                                                               // universitarias es acotada.
            i++;
        } // Total = O (E)









        // Trie carreras --> Trie materias -->Materia
        int j = 0; // j itera sobre materias, y k itera sobre los nombres
        int cantidadDeMaterias = infoMaterias.length, cantidadDeNombres;// los limites de los iteradores anteriores
        ParCarreraMateria[] nombresYCarreras;
        String nombreMateria, nombreCarrera;

        while (j < cantidadDeMaterias) {
            int k = 0;
            Materia objetoMateria = new Materia();// tengo que unir todos los nombres a este objeto
            nombresYCarreras = infoMaterias[j].getParesCarreraMateria();
            cantidadDeNombres = nombresYCarreras.length;
            NodoTrie nodoADefinir;
            while (k < cantidadDeNombres) {
                nombreMateria = nombresYCarreras[k].getNombreMateria();
                nombreCarrera = nombresYCarreras[k].getCarrera();
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
                //En la ultima letra del trie carrera y la palabra carrera armo un trie de ese ultimo nodo del mismo y uso la funcion definicion("con la ultima letra de la palabra")
                k++;
            }
            j++;
        }






        // int j = 0; //itera sobre materias
        // int k = 0;//itera sobre nombres de materia
        // int cantidadDeMaterias = infoMaterias.length;
        // String nombre_carrera;
        // String nombre_materia;
        // Materia objetoMateria;
        // int cantidadDeNombres;//de nombres para una materia
        // NodoTrie materiaTrie;
        // while(j < cantidadDeMaterias){//O(M) , cantidad de materias
        // objetoMateria = new Materia();
        // cantidadDeNombres = infoMaterias[j].getParesCarreraMateria().length;
        // while(k < cantidadDeNombres){//O(N_m) , k iterar por la cantidad de nombres
        // de la materia
        // nombre_carrera = infoMaterias[j].getParesCarreraMateria()[k].getCarrera();
        // nombre_materia =
        // infoMaterias[j].getParesCarreraMateria()[k].getNombreMateria(); //creamos dos
        // variables distintas para guardar los nombres de carrera y materia
        // materiaTrie = carreras.definirSiVacio(nombre_carrera);
        // if(materiaTrie.definicion == null){ //si no existe la carrera, la creamos con
        // su Trie asociado a materias
        // materiaTrie.definicion = new Trie<Materia>();
        // }
        // k ++;
        // materiaTrie.definicion.definir(nombre_materia, objetoMateria);
        // carreras.definicion(nombre_carrera).definir(nombre_materia, objetoMateria);
        // //definimos la materia nueva
        // objetoMateria.agregarReferencia(carreras.definicion(nombre_carrera),
        // nombre_materia);
        // }
        // j++;
    }// O(M*N_m)

    public void inscribir(String estudiante, String carrera, String materia) {
        estudiantes.definir(estudiante ,estudiantes.definicion(estudiante) + 1);
        Trie<Materia> materiasDeCarrera = carreras.definicion(carrera);
        Materia objetoMateria = materiasDeCarrera.definicion(materia);
        objetoMateria.agregarAlumno(estudiante);
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        //carreras.definicion(carrera).definicion(materia).agregarDocente(CargoDocente.ordinal(cargo));
        //buscar bien la funcion del tipo Enum
    }

    public int[] plantelDocente(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).docentes();
    }

    public void cerrarMateria(String materia, String carrera) {
        throw new UnsupportedOperationException("Método no implementado aún");
    }

    public int inscriptos(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).libretas.longitud();
    }

    public boolean excedeCupo(String materia, String carrera) {
        return carreras.definicion(carrera).definicion(materia).excedeCupo();
    }

    public String[] carreras() {
        return carreras.listaDeStrings();
    }

    public String[] materias(String carrera) {
        return carreras.definicion(carrera).listaDeStrings();
    }

    public int materiasInscriptas(String estudiante) {
        return estudiantes.definicion(estudiante);
    } // la complejidad de esta funcion claramente es O(1) ya que estamos buscando la
      // definicion de un Trie con tamaño acotado.
}
