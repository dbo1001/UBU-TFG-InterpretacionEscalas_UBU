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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
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

	public IOControlImpl(List<Alumno> allStudents, List<Profesor> allTeachers, List<Aula> allClassrooms,
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
					// TODO poner un contador de reintentos
					System.out.println("Comprobando que existe el directorio raiz.");
					while (!OneDriveAPI.checkDirectory(httpClient, PATH_SOURCE)) {
						System.out.println("No existe el directorio, se procede a crearlo.");
						OneDriveAPI.createFolder(httpClient, "InterpretacionEscalas_2019_2020");
						System.out.println(OneDriveAPI.checkDirectory(httpClient, PATH_SOURCE));
					}

					System.out.println("Fichero raiz listo.");

					do {
						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + STUDENTS_FN);
						OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + STUDENTS_FN);
					} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + STUDENTS_FN));

					System.out.println("Alumnos exportados correctamente.");

					do {
						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + TEACHERS_FN);
						OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + TEACHERS_FN);
					} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + TEACHERS_FN));

					System.out.println("Profesores exportados correctamente.");

					do {
						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + CLASSROOMS_FN);
						OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + CLASSROOMS_FN);
					} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + CLASSROOMS_FN));

					System.out.println("Aulas exportados correctamente.");

					for (Aula cla : this.currentTeacherClassrooms) {
						String PATH_CLASSROOM = cla.getNombre() + "/";
						do {
							System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n",
									PATH_SOURCE + PATH_LOCAL, PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN);
							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE,
									PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN);
						} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN));

						do {
							System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n",
									PATH_SOURCE + PATH_LOCAL, PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN);
							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE,
									PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN);
						} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN));
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

		for (Aula cla : currentTeacherClassrooms) {
			CSVControl.createClassroomFile(cla);
		}

		try {
			while (!exito) {
				boolean conectado = OneDriveAPI.testCurrentAccessToken(httpClient);
				if (!conectado) {
					System.out.println("Validación incorrecta, reintentado obtención de token...");
					OneDriveAPI.renewAccessToken(httpClient);
				} else {
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle("Descargando archivos...");
					info.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

					info.setContentText("Descargado fichero: " + PATH_SOURCE + PATH_LOCAL + "alumnos.csv\n");
					info.show();

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "alumnos.csv");
					OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "alumnos.csv");

					info.close();
					info.setContentText(
							info.getContentText() + "Descargado fichero: " + PATH_SOURCE + PATH_LOCAL + "aulas.csv\n");
					info.show();

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "aulas.csv");
					OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "aulas.csv");

					info.close();
					info.setContentText(info.getContentText() + "Descargado fichero: " + PATH_SOURCE + PATH_LOCAL
							+ "profesores.csv\n");
					info.show();

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "profesores.csv");
					OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "profesores.csv");

					for (Aula cla : this.currentTeacherClassrooms) {

						info.close();
						info.setContentText(info.getContentText() + "Descargado ficheros dentro de: " + PATH_SOURCE
								+ PATH_EVALUATIONS + cla.getNombre() + "/\n");
						info.show();

						System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/evaluaciones.csv");
						OneDriveAPI.downloadFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/evaluaciones.csv");

						System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/puntuaciones.csv");
						OneDriveAPI.downloadFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/puntuaciones.csv");
					}

					info.close();
					info.setContentText(info.getContentText()
							+ "Los ficheros han sido descargados con EXITO\nSe procedera a importar los datos a la base de datos...");
					info.show();

					Thread.sleep(3000);

					info.close();

					exito = true;

				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error desconocido al descargar los archivos.");
			error.setContentText(
					"Ha ocurrido un error desconocido al descargar los archivos.\nAlgunas de las posibles causas son las siguientes\n\t1. Hay un problema con la conexión de red.\n\t2. Tienes abierto un archivo \".csv\" hubicado dentro de la carpeta raíz de la aplicación y no es posible sobreescribirlo.");
			error.showAndWait();
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

		for (Aula cla : currentTeacherClassrooms) {
			CSVControl.createClassroomFile(cla);
			String PATH_CLASSROOM = cla.getNombre() + "/";
			List<Evaluacion> evas = new ArrayList<Evaluacion>();
			for (Alumno stu : cla.getAlumnos()) {
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
