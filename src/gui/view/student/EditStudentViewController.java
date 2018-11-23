package gui.view.student;

import java.io.IOException;

import gui.Main;
import gui.view.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Alumno;

public class EditStudentViewController extends Controller{

	@FXML private TextField nombre;
	@FXML private TextField primerApellido;
	@FXML private TextField segundoApellido;
	@FXML private TextField NIF;
	@FXML private TextField direccion;
	@FXML private TextField fechaNacimiento;
	private Alumno stu;
	
	public void setStudent(Alumno stu) {
		this.stu = stu;
		this.fillFields();
	}
	
	@FXML
	private void fillFields() {
		this.nombre.setText(stu.getNombre());
		this.primerApellido.setText(stu.getApellido1());
		this.segundoApellido.setText(stu.getApellido2());
		this.NIF.setText(stu.getNif());
		this.direccion.setText(stu.getDireccion());
		if(stu.getFechaNacimiento()!=null) {
			this.fechaNacimiento.setText(stu.getFechaNacimiento().toString());
		}
	}
	
	@FXML
	private void cancel() throws IOException {
		if(cancelAlert()) {
			Main.showManageView();
		}
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y sobreescibir los cambios del alumno.");
	}
	
}
