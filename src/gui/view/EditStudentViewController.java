package gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Alumno;

public class EditStudentViewController {

	@FXML private Label nombre;
	@FXML private Label primerApellido;
	@FXML private Label segundoApellido;
	@FXML private Label NIF;
	@FXML private Label direccion;
	@FXML private Label fechaNacimiento;
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
		this.fechaNacimiento.setText(stu.getFechaNacimiento().toString());
	}
	
}
