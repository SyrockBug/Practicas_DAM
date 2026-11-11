package ejercicio1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que representa un <strong>instituto</strong> para la gestion de profesores. 
 * <p>
 * Los objetos de esta clase permiten almacenar y gestionar una lista de objetos de tipo
 * <strong>Profesor</strong>. 
 * @author Nicolás Medina Fernández
 */
public class Instituto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    // -------------------------------------------------------------------------
    // ATRIBUTOS DE OBJETO
    // ------------------------------------------------------------------------
    private List<Profesor> profesores; // Lista de objetos Profesor 
    
    // ------------------------------------------------------------------------
    //                        CONSTRUCTOR
    // ------------------------------------------------------------------------
    /**
     * Crea un objeto instituto vaci­o.
     */
    public Instituto(){
        profesores = new ArrayList<>();
    }

    /**
     * Asigna al instituto un listado de profesores que se pasa por parámetro.
     * @param profesores Lista de objetos de tipo Profesor.
     */
    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }
    
    /**
     * Añade un nuevo profesor al instituto.
     * @param nuevo Objeto de tipo Profesor que se añade
     * @throws NullPointerException si el parametro es nulo.
     */
    public void add(Profesor nuevo) throws NullPointerException {
        if (nuevo == null)
             throw new NullPointerException ("El objeto Profesor no puede ser nulo.");
        
        this.profesores.add(nuevo); 
    }
     
   /**
    * Busca un profesor en el instituto a partir de su nombre.
    * @param nombre Nombre del profesor que estamos buscando
    * @return si el profesor se encuentra o no en el Instituto
    * @throws NullPointerException si el parametro es nulo
    */
    public boolean buscarPorNombre(String nombre) throws NullPointerException {
        if (nombre == null)
            throw new NullPointerException ("El nombre del profesor no puede ser nulo.");
        
        boolean encontrado = false;
        Iterator<Profesor> iterator = this.profesores.iterator();
        while (iterator.hasNext() && !encontrado) {
            Profesor profesor = iterator.next();
            if (profesor.getNombre().equalsIgnoreCase(nombre)) {
               encontrado = true;
            }
        }
        return encontrado;
    }
    
    /**
     * Obtiene el numero de profesores en el instituto.
     * @return numero de profesores
     */
    public int numProfesores() {
        return this.profesores.size();
    }
    
    /**
     * Permite borrar un profesor del instituto.
     * @param borrado Objeto de tipo Profesor que queremos borrar
     * @return si el profesor ha sido eliminado
     * @throws NullPointerException si el parametro es nulo
     */
    public boolean borrarProfesor(Profesor borrado) throws NullPointerException {
        if (borrado == null)
            throw new NullPointerException ("El objeto Profesor no puede ser nulo.");
        
        return this.profesores.remove(borrado);
    }
    
    /**
     * Permite vaciar el instituto de profesores.
     */
    public void vaciarInstituto() {
        this.profesores.clear();
    }
    
    /**
     * Devuelve una cadena que representa los profesores del instituto de 
     * forma textual.
     * @return cadena que representa los profesores del instituto de forma textual
     */
    @Override
    public String toString() {
        String listado = "";

        if (this.profesores.isEmpty()) {
            listado = "No hay profesores registrados en el Instituto";
        } else {
            for (Profesor p : this.profesores) {
                listado = listado + p.toString()+"\n";
            }
        }
        return listado;
    }
}
