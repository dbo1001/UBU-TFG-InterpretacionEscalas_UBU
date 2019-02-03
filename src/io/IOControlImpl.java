package io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import io.csv.CSVControl;
import io.oneDrive.util.OneDriveAPI;
import model.Alumno;
import model.Aula;
import model.Evaluacion;
import model.Profesor;
import model.Puntuacion;

public class IOControlImpl implements IOControl {

	private final String PATH_SOURCE = "/InterpretacionEscalas_2019_2020/";
	private final String PATH_LOCAL = "ioData/";
	private final String PATH_EVALUATIONS = "ioData/Evaluaciones/";
	private final String STUDENTS_FN = "alumnos.csv";
	private final String TEACHERS_FN = "profesores.csv";
	private final String CLASSROOMS_FN = "aulas.csv";
	private final String EVALUATIONS_FN = "evaluaciones.csv";
	private final String PUNTUATIONS_FN = "puntuaciones.csv";
	private List<Aula> allClassrooms;
	private List<Profesor> allTeachers;
	private List<Alumno> allStudents;
	private List<Aula> currentTeacherClassrooms;
	
	public IOControlImpl (List<Alumno> allStudents, List<Profesor> allTeachers, List<Aula> allClassrooms,
			List<Aula> currentTeacherClassrooms) {
		this.allClassrooms = allClassrooms;
		this.allTeachers = allTeachers;
		this.allStudents = allStudents;
		this.currentTeacherClassrooms = currentTeacherClassrooms;
	}

	@Override
	public boolean exportData() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		boolean exito = false;
		
		this.generateCSV();
		
		try {
			while (!exito) {
				boolean conectado = OneDriveAPI.testCurrentAccessToken(httpClient);
				if (!conectado) {
					System.out.println("Validación incorrecta, reintentado obtención de token...");
					OneDriveAPI.renewAccessToken(httpClient);
				} else {

					System.out.println("Comprobando que existe el directorio raiz.");
					while (!OneDriveAPI.checkDirectory(httpClient, PATH_SOURCE)) {
						System.out.println("No existe el directorio, se procede a crearlo.");
						OneDriveAPI.createFolder(httpClient, "InterpretacionEscalas_2019_2020");
						System.out.println(OneDriveAPI.checkDirectory(httpClient, PATH_SOURCE));
					}
					
					System.out.println("Fichero raiz listo.");
					
					do{
						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + STUDENTS_FN);
						OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + STUDENTS_FN);
					}while(!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + STUDENTS_FN));
					
					System.out.println("Alumnos exportados correctamente.");
					
					do{
						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + TEACHERS_FN);
						OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + TEACHERS_FN);
					}while(!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + TEACHERS_FN));
					
					System.out.println("Profesores exportados correctamente.");
					
					do{
						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + CLASSROOMS_FN);
						OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + CLASSROOMS_FN);
					}while(!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + CLASSROOMS_FN));
					
					System.out.println("Aulas exportados correctamente.");
					
					for(Aula cla : this.currentTeacherClassrooms) {
						String PATH_CLASSROOM = cla.getNombre() + "/";
						do{
							System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE + PATH_LOCAL,
									PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN);
							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN);
						}while(!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN));
						
						do{
							System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE + PATH_LOCAL,
									PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN);
							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN);
						}while(!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN));
					}
					
					System.out.println("Evaluaciones exportadas correctamente.");

					exito = true;
					
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return exito;
	}

	@Override
	public boolean importData() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		boolean exito = false;
		
		for(Aula cla: currentTeacherClassrooms) {
			CSVControl.createClassroomFile(cla);
		}
		
		try {
			while (!exito) {
				boolean conectado = OneDriveAPI.testCurrentAccessToken(httpClient);
				if (!conectado) {
					System.out.println("Validación incorrecta, reintentado obtención de token...");
					OneDriveAPI.renewAccessToken(httpClient);
				} else {

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "alumnos.csv");
					OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "alumnos.csv");

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "aulas.csv");
					OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "aulas.csv");
					
					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "profesores.csv");
					OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "profesores.csv");
					
					for(Aula cla: this.currentTeacherClassrooms) {
						System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE , PATH_EVALUATIONS + cla.getNombre() + "/evaluaciones.csv");
						OneDriveAPI.downloadFile(httpClient, PATH_SOURCE ,  PATH_EVALUATIONS + cla.getNombre() + "/evaluaciones.csv");
						
						System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE , PATH_EVALUATIONS + cla.getNombre() + "/puntuaciones.csv");
						OneDriveAPI.downloadFile(httpClient, PATH_SOURCE ,  PATH_EVALUATIONS + cla.getNombre() + "/puntuaciones.csv");
					}
					
					exito = true;
					
					/*
					System.out.printf("Borrando directorio previo: %s%n", PATH_TARGET);
					OneDriveAPI.deleteFolder(httpClient, PATH_TARGET);

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, FILENAME_TEXT);
					OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, FILENAME_TEXT);

					System.out.printf("Creando nuevo directorio: %s%n", "New_Folder");
					OneDriveAPI.createFolder(httpClient, "New_Folder");

					System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_TARGET,
							FILENAME_TEXT);
					OneDriveAPI.uploadTextFile(httpClient, PATH_TARGET, FILENAME_TEXT);

					System.out.printf("Comprobando que existe el fichero recien subido: %s%s%n", PATH_TARGET,
							FILENAME_TEXT);
					OneDriveAPI.checkFile(httpClient, PATH_TARGET, FILENAME_TEXT);

					System.out.printf("Borrarmos el fichero que acabamos de subir: %s%s%n", PATH_TARGET, FILENAME_TEXT);
					OneDriveAPI.deleteFile(httpClient, PATH_TARGET, FILENAME_TEXT);

					System.out.printf(
							"Debe generar error (OK) al comprobar que NO existe el fichero recien borrado: %s%s%n",
							PATH_TARGET, FILENAME_TEXT);
					OneDriveAPI.checkFile(httpClient, PATH_TARGET, FILENAME_TEXT); // "/New_Folder/prueba.txt");

					System.out.printf(
							"Debe generar error (OK) al comprobar que NO existe un directorio que nunca se ha creado: %s%n",
							"/New_Folder2/");
					OneDriveAPI.checkDirectory(httpClient, "/New_Folder2/");

					System.out.printf("Borrarmos el fichero binario que vamos a subir: %s%s%n", PATH_SOURCE,
							FILENAME_BINARY);
					OneDriveAPI.deleteFile(httpClient, PATH_SOURCE, FILENAME_BINARY);

					System.out.printf("Subiendo fichero binario imagen a directorio inicial: %s%s%n", PATH_SOURCE,
							FILENAME_BINARY);
					OneDriveAPI.uploadBinaryFile(httpClient, PATH_SOURCE, FILENAME_BINARY);

					System.out.printf("Comprobando que existe el fichero binario recien subido: %s%s%n", PATH_SOURCE,
							FILENAME_BINARY);
					OneDriveAPI.checkFile(httpClient, PATH_SOURCE, FILENAME_BINARY);

					System.out.println("Contenido del directorio ra�z al final de las operaciones...");
					OneDriveAPI.listDriveItem(httpClient, "");
					*/
					
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return exito;
	}

	private void generateCSV() throws IOException {
		
		CSVControl.exportClassroom(allClassrooms);
		CSVControl.exportStudents(allStudents);
		CSVControl.exportTeachers(allTeachers);
		
		for(Aula cla: currentTeacherClassrooms) {
			CSVControl.createClassroomFile(cla);
			String PATH_CLASSROOM = cla.getNombre() + "/";
			List<Evaluacion> evas = new ArrayList<Evaluacion>();
			for(Alumno stu: cla.getAlumnos()) {
				evas.addAll(stu.getEvaluacions());
			}
			CSVControl.exportEvaluation(evas, PATH_EVALUATIONS + PATH_CLASSROOM);
		}
	}

	@Override
	public List<Alumno> readStudentsCSV(String path) throws FileNotFoundException {
		return CSVControl.readStudentsCSV(path);
	}

	@Override
	public List<Profesor> readTeachersCSV(String path) throws FileNotFoundException {
		return CSVControl.readTeachersCSV(path);
	}

	@Override
	public List<Aula> readClassroomsCSV(String path) throws FileNotFoundException {
		return CSVControl.readClassroomsCSV(path);
	}

	@Override
	public List<Evaluacion> readEvaluationsCSV(String path) throws FileNotFoundException {
		return CSVControl.readEvaluationsCSV(path);
	}

	@Override
	public List<Puntuacion> readPuntuationsCSV(String path) throws FileNotFoundException {
		return CSVControl.readPuntuationsCSV(path);
	}

}
