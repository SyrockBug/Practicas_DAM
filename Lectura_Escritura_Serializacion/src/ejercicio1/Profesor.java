package ejercicio1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Clase que representa un <strong>Profesor</strong> para la gestion de profesores de un
 * instituto.
 * <p>
 * Los objetos de esta clase permiten almacenar y gestionar informacion sobre el
 * propio profesor: nombre, especialidad, módulos, departamento y fecha de incorporación.</p> 
 *
 * @author Nicolás Medina Fernández
 */
public final class Profesor implements Serializable{
    
    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------------------
    // ATRIBUTOS DE OBJETO
    // ------------------------------------------------------------------------
    // Atributos de objeto variables
    private String nombre; // Nombre del profesor
    private String especialidad; // Especialidad del profesor
    private List<String> modulos; // Lista de módulos que imparte el profesor
    private String departamento; // Departamento del profesor.
    private LocalDate fechaIncorporacion; // Fecha de incorporación del profesor al instituto.

    // ------------------------------------------------------------------------
    //                        CONSTRUCTOR
    // ------------------------------------------------------------------------
    /**
     * Crea un objeto Profesor con un nombre, especialidad, fecha de incorporación, departamento
       y lista de módulos que imparte.
     *
     * @param nombre nombre del profesor
     * @param especialidad especialidad del profesor
     * @param modulos Lista de modulos del profesor
     * @param departamento Departamento del profesor
     * @param fechaIncorporacion Fecha de incorporación del profesor al instituto.
     */
    public Profesor(String nombre, String especialidad, LocalDate fechaIncorporacion, List<String> modulos, String departamento) throws NullPointerException{
        this.setNombre(nombre);
        this.setEspecialidad(especialidad);
        this.setModulos(modulos);
        this.setDepartamento(departamento);
        this.setFechaIncorporacion(fechaIncorporacion);
    }

    // ------------------------------------------------------------------------
    //                       getter y setter
    // ------------------------------------------------------------------------
    /**
     * Devuelve el nombre del profesor.
     *
     * @return nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el especialidad del profesor
     *
     * @return especialidad
     */
    public String getEspecialidad() {
        return this.especialidad;
    }

    /**
     * Devuelve la lista de modulos del profesor
     *
     * @return lista de modulos
     */
    public List<String> getModulos() {
        // Devolvemos una copia de la lista para evitar modificaciones externas
        return new ArrayList<>(this.modulos);
    }

    /**
     * Devuelve el departamento del profesor
     *
     * @return departamento
     */
    public String getDepartamento() {
        return this.departamento;
    }

    /**
     * Devuelve la fecha de incorporación del profesor al instituto
     *
     * @return fecha de incorporacion
     */
    public LocalDate getFechaIncorporacion() {
        return this.fechaIncorporacion;
    }

    /**
     * Asigna un nombre al profesor.
     *
     * @param nombre Nombre que se va a asignar al profesor
     * @throws NullPointerException si el parametro nombre es nulo
     */
    public void setNombre(String nombre) throws NullPointerException {
        if (nombre == null) {
            throw new NullPointerException("El nombre del profesor no puede ser nulo.");
        }
        this.nombre = nombre;
    }

    /**
     * Asigna un especialidad al profesor.
     *
     * @param especialidad Especialidad que se va a asignar al profesor
     * @throws NullPointerException si el parametro especialidad es nulo
     */
    public void setEspecialidad(String especialidad) throws NullPointerException {
        if (especialidad == null) {
            throw new NullPointerException("La especialidad del profesor no puede ser nula.");
        }
        this.especialidad = especialidad;
    }

    /**
     * Asigna una lista de modulos al profesor.
     *
     * @param modulos Lista de modulos que se van a asignar al profesor
     * @throws NullPointerException si el parametro modulos es nulo
     */
    public void setModulos(List<String> modulos) throws NullPointerException {
        if (modulos == null) {
            throw new NullPointerException("La lista de módulos del profesor no puede ser nula.");
        }
        this.modulos = new ArrayList<>(modulos);
    }

    /**
     * Asigna el departamento del profesor.
     *
     * @param departamento Departamento que se va a asignar al profesor
     * @throws NullPointerException si el parametro departamento es nulo
     */
    public void setDepartamento(String departamento) throws NullPointerException {
        if (departamento == null) {
            throw new NullPointerException("El departamento del profesor no puede ser nulo.");
        }
        this.departamento = departamento;
    }

    /**
     * Asigna una fecha de incorporación del profesor al instituto.
     *
     * @param fechaIncorporacion Fecha de incorporación que se va a asignar al profesor en el instituto
     * @throws NullPointerException si el parametro fechaIncorporacion es nulo
     */
    public void setFechaIncorporacion(LocalDate fechaIncorporacion) throws NullPointerException {
        if (fechaIncorporacion == null) {
            throw new NullPointerException("La fecha de incorporación del profesor no puede ser nula.");
        }
        this.fechaIncorporacion = fechaIncorporacion;
    }

    //-------------------------------------------------------------------------
    //                         Método toString
    //-------------------------------------------------------------------------
    /**
     * Devuelve una cadena que representa los datos del profesor de forma textual.
     *
     * @return cadena que representa los datos del profesor de forma textual
     */
    @Override
    public String toString() {
        return String.format("#%s;%s;%s;%s;%s;",
                this.getNombre(), this.getEspecialidad(), this.getFechaIncorporacion(),this.getDepartamento(), this.getModulos());
    }
}

