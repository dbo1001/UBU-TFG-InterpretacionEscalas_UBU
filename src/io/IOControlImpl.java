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

import gui.Main;
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
	private final int TRY_LIMIT = 5;
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
	public boolean exportData() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		boolean exito = false;
		int retrys = 0;

		try {

			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle("Exportando archivos...");
			info.getDialogPane().setPrefWidth(Region.USE_COMPUTED_SIZE);
			info.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

			info.setContentText("Generando ficheros CSV en el directorio /ioData...");
			info.show();

			this.generateCSV();

			info.close();
			info.setContentText(info.getContentText() + "OK...\n");
			info.show();

			info.close();
			info.setContentText(info.getContentText() + "Conectando con la cuenta de la nube...");
			info.show();

			while (!exito) {
				boolean conectado = OneDriveAPI.testCurrentAccessToken(httpClient);
				if (!conectado) {
					System.out.println("Validación incorrecta, reintentado obtención de token...");
					OneDriveAPI.renewAccessToken(httpClient);
				} else {

					info.close();
					info.setContentText(info.getContentText() + "OK\nComprobando que existe directorio raíz...");
					info.show();

					System.out.println("Comprobando que existe el directorio raíz.");
					while (!OneDriveAPI.checkDirectory(httpClient, PATH_SOURCE) && retrys < TRY_LIMIT) {
						if (retrys >= TRY_LIMIT) {
							this.showRetryError();
							return exito;
						}
						if (retrys > 0) {
							Thread.sleep(5000);
						}

						info.close();
						info.setContentText(
								info.getContentText() + "\nDirectorio raíz no existe, se procede a crearlo...\n");
						info.show();

						System.out.println("No existe el directorio, se procede a crearlo.");
						OneDriveAPI.createFolder(httpClient, "InterpretacionEscalas_2019_2020");
						System.out.println(OneDriveAPI.checkDirectory(httpClient, PATH_SOURCE));
						retrys++;
					}

					retrys = 0;

					if (Main.getCurrentTeacher().getPermisos()) {

						info.close();
						info.setContentText(info.getContentText() + "OK\nSubiendo el archivo: " + PATH_SOURCE
								+ PATH_LOCAL + STUDENTS_FN + "...");
						info.show();

						System.out.println("Fichero raiz listo.");

						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + STUDENTS_FN);

						do {
							if (retrys >= TRY_LIMIT) {
								this.showRetryError();
								return exito;
							}
							if (retrys > 0) {
								Thread.sleep(5000);
							}

							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + STUDENTS_FN);
							retrys++;
						} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + STUDENTS_FN)
								&& retrys < TRY_LIMIT);

						retrys = 0;

						System.out.println("Alumnos exportados correctamente.");

						info.close();
						info.setContentText(info.getContentText() + "OK\nSubiendo el archivo: " + PATH_SOURCE
								+ PATH_LOCAL + TEACHERS_FN + "...");
						info.show();

						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + TEACHERS_FN);

						do {
							if (retrys >= TRY_LIMIT) {
								this.showRetryError();
								return exito;
							}
							if (retrys > 0) {
								Thread.sleep(5000);
							}

							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + TEACHERS_FN);
							retrys++;
						} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + TEACHERS_FN)
								&& retrys < TRY_LIMIT);

						retrys = 0;

						info.close();
						info.setContentText(info.getContentText() + "OK\nSubiendo el archivo: " + PATH_SOURCE
								+ PATH_LOCAL + CLASSROOMS_FN + "...");
						info.show();

						System.out.println("Profesores exportados correctamente.");

						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n", PATH_SOURCE,
								PATH_LOCAL + CLASSROOMS_FN);

						do {

							if (retrys >= TRY_LIMIT) {
								this.showRetryError();
								return exito;
							}
							if (retrys > 0) {
								Thread.sleep(5000);
							}

							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE, PATH_LOCAL + CLASSROOMS_FN);
							retrys++;
						} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE, PATH_LOCAL + CLASSROOMS_FN)
								&& retrys < TRY_LIMIT);

						retrys = 0;

						info.close();
						info.setContentText(info.getContentText() + "OK\nSubiendo el archivo: " + PATH_SOURCE
								+ PATH_LOCAL + CLASSROOMS_FN + "...");
						info.show();

						System.out.println("Aulas exportados correctamente.");
					}

					for (Aula cla : this.currentTeacherClassrooms) {
						String PATH_CLASSROOM = cla.getNombre() + "/";

						info.close();
						info.setContentText(info.getContentText() + "OK\nSubiendo archivos a la carpeta: " + PATH_SOURCE
								+ PATH_LOCAL + PATH_EVALUATIONS + PATH_CLASSROOM + "...");
						info.show();

						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n",
								PATH_SOURCE + PATH_LOCAL, PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN);

						do {
							if (retrys >= TRY_LIMIT) {
								this.showRetryError();
								return exito;
							}
							if (retrys > 0) {
								Thread.sleep(5000);
							}

							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE,
									PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN);
							retrys++;
						} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + PATH_CLASSROOM + EVALUATIONS_FN) && retrys < TRY_LIMIT);

						retrys = 0;

						System.out.printf("Subiendo fichero de texto a nuevo directorio creado: %s%s%n",
								PATH_SOURCE + PATH_LOCAL, PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN);

						do {
							if (retrys >= TRY_LIMIT) {
								this.showRetryError();
								return exito;
							}
							if (retrys > 0) {
								Thread.sleep(5000);
							}

							OneDriveAPI.uploadTextFile(httpClient, PATH_SOURCE,
									PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN);
							retrys++;
						} while (!OneDriveAPI.checkFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + PATH_CLASSROOM + PUNTUATIONS_FN) && retrys < TRY_LIMIT);

						retrys = 0;
					}

					info.close();
					info.setContentText(info.getContentText() + "OK\nArchivos exportados con EXITO.");
					info.show();

					System.out.println("Evaluaciones exportadas correctamente.");

					exito = true;

				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error desconocido al exportar los archivos.");
			error.setHeaderText("Ha ocurrido un error desconocido al exportar los archivos.");
			error.setContentText(
					"Algunas de las posibles causas son las siguientes:\n\t1. Hay un problema con la conexión de red.\n\t2. Tienes abierto un archivo \".csv\" hubicado dentro de la carpeta raíz de la aplicación y no es posible sobreescribirlo.");
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

	@Override
	public boolean importData() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		boolean exito = false;
		int retrys = 0;

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
					while (!OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "alumnos.csv")) {
						if (retrys >= TRY_LIMIT) {
							this.showRetryError();
							return exito;
						}

						Thread.sleep(5000);
						retrys++;
					}

					retrys = 0;

					info.close();
					info.setContentText(
							info.getContentText() + "Descargado fichero: " + PATH_SOURCE + PATH_LOCAL + "aulas.csv\n");
					info.show();

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "aulas.csv");
					while (!OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "aulas.csv")) {
						if (retrys >= TRY_LIMIT) {
							this.showRetryError();
							return exito;
						}

						Thread.sleep(5000);
						retrys++;
					}

					retrys = 0;

					info.close();
					info.setContentText(info.getContentText() + "Descargado fichero: " + PATH_SOURCE + PATH_LOCAL
							+ "profesores.csv\n");
					info.show();

					System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE, PATH_LOCAL + "profesores.csv");
					while (!OneDriveAPI.downloadFile(httpClient, PATH_SOURCE, PATH_LOCAL + "profesores.csv")) {
						if (retrys >= TRY_LIMIT) {
							this.showRetryError();
							return exito;
						}

						Thread.sleep(5000);
						retrys++;
					}

					retrys = 0;

					for (Aula cla : this.currentTeacherClassrooms) {

						info.close();
						info.setContentText(info.getContentText() + "Descargado ficheros dentro de: " + PATH_SOURCE
								+ PATH_EVALUATIONS + cla.getNombre() + "/\n");
						info.show();

						System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/evaluaciones.csv");
						while (!OneDriveAPI.downloadFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/evaluaciones.csv")) {
							if (retrys >= TRY_LIMIT) {
								this.showRetryError();
								return exito;
							}

							Thread.sleep(5000);
							retrys++;
						}

						retrys = 0;

						System.out.printf("Descargado fichero: %s%s %n", PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/puntuaciones.csv");
						while (!OneDriveAPI.downloadFile(httpClient, PATH_SOURCE,
								PATH_EVALUATIONS + cla.getNombre() + "/puntuaciones.csv")) {
							if (retrys >= TRY_LIMIT) {
								this.showRetryError();
								return exito;
							}

							Thread.sleep(5000);
							retrys++;
						}

						retrys = 0;
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
			error.setHeaderText("Ha ocurrido un error desconocido al descargar los archivos.");
			error.setContentText(
					"Algunas de las posibles causas son las siguientes:\n\t1. Hay un problema con la conexión de red.\n\t2. Tienes abierto un archivo \".csv\" hubicado dentro de la carpeta raíz de la aplicación y no es posible sobreescribirlo.");
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

	private void showRetryError() {
		Alert error = new Alert(AlertType.ERROR);
		error.setTitle("Error de conxión.");
		error.setHeaderText("Se ha superado el número de reintentos al intentar conectar con la nube.");
		error.setContentText("Es posible que no tengas conexión de red o haya un problema con ella.");
		error.showAndWait();
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
