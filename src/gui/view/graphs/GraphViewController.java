package gui.view.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gui.view.Controller;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import model.Alumno;
import model.Areafuncional;
import model.Categorizacion;
import model.Puntuacion;

public class GraphViewController extends Controller {

	@FXML
	BorderPane root;
	private Map<String, Integer> datos = new HashMap<String, Integer>();
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private LineChart<String, Number> chart;
	
	public void faChart(List<Alumno> students, List<Areafuncional> faList) {
		
		for(Areafuncional fa: faList) {
			System.out.println("Samba");
			for(Categorizacion ca : fa.getCategorizacions()) {
				System.out.println("Mary tenia un ovejita");
				datos.put(ca.getDescripcion(), 0);
				for(Alumno stu: students) {
					for(Puntuacion pun : stu.getEvaluacions().get(0).getPuntuacions()) {
						datos.replace(ca.getDescripcion(), datos.get(ca.getDescripcion() + pun.getValoracion()));
					}
				}
			}
		}
		
		for(String caName : datos.keySet()) {
			for(Alumno stu: students) {
				for(Puntuacion pun : stu.getEvaluacions().get(0).getPuntuacions()) {
					if(caName.equals(pun.getItem().getCategorizacion().getDescripcion())) {
						datos.replace(caName, datos.get(caName) + pun.getValoracion() +2);
					}
				}
			}
		}
		
		
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		LineChart<String, Number> chart = new LineChart<String, Number>(xAxis, yAxis);
		
		Series<String, Number> series = new Series<String, Number>();
		for(String caName: datos.keySet()) {
			series.getData().add(new XYChart.Data<>(caName, datos.get(caName)));
			System.out.println(caName);
		}
		
		chart.getData().add(series);
		chart.setTitle("Grafico generado");
		
		root.setCenter(chart);
	}
	
	public void caChart() {
		
	}
	
	public void itChart() {
		
	}
	
}
