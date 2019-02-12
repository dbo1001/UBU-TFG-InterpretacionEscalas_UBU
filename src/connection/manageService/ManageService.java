package connection.manageService;

import java.util.List;

import connection.ConnectionException;
/**
 * 
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 * @param <T> tipo del objeto que se usará en la transacción
 * @param <O> tipo del objeto necesario para obtener un T concreto de la base de datos, debe ser unique. 
 * Ej: T=Alumno O=String(para buscar alumnos por código)
 */
public interface ManageService<T, O> {
	/**
	 * Devuelve todos los objetos T que hay en la base de datos
	 * @return todos los objetos T
	 */
	public List<T> getAll();
	
	/**
	 * Busca un objeto T concreto filtrado por Id
	 * @param l id
	 * @return objeto T encontrado, null si no se encuentra
	 */
	public T getOneById(long l);
	
	/**
	 * Busca un objeto T concreto filtrado por O
	 * @param object campor por el que filtrar T
	 * @return objeto T encontrado, null si no se encuentra
	 */
	public T getOne(O object);
	
	/**
	 * Inserta un objeto T en la base de datos, comprueba sus campos primero
	 * @param object objeto a insertar
	 * @return true si exito, false si error
	 * @throws ConnectionException alguno de los campos es erroneo
	 */
	public boolean add(T object) throws ConnectionException;
	
	/**
	 * Edita un objeto T en la base de datos, comprueba sus campos primero
	 * @param object objeto a editar
	 * @return true si exito, false si error
	 * @throws ConnectionException alguno de los campos es erroneo
	 */
	public boolean edit(T object) throws ConnectionException;
	
	/**
	 * Borra un objeto T en la base de datos, comprueba sus campos primero
	 * @param object objeto a borrar
	 * @return true si exito, false si error
	 * @throws ConnectionException alguna resticción evita que el objeto se pueda borrar
	 */
	public boolean delete(T object) throws ConnectionException;

}
