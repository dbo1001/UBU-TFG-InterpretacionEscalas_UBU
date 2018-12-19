package gui.view.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import model.Item;
import model.Puntuacion;

public class GraphViewController extends Controller {

	@FXML
	BorderPane root;
	private Map<String, Integer> datos = new LinkedHashMap<String, Integer>();
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private LineChart<String, Number> chart;
	Series<String, Number> series;

	public void faChart(List<Alumno> students, List<Areafuncional> faList) {
		for (Areafuncional fa : faList) {
			datos.put(fa.getDescripcion(), 0);
			for (Alumno stu : students) {
				datos.replace(fa.getDescripcion(), datos.get(fa.getDescripcion()) + 10);
				if (stu.getEvaluacions().size() > 0) {
					for (Puntuacion pun : stu.getEvaluacions().get(0).getPuntuacions()) {
						// datos.replace(fa.getDescripcion(), datos.get(fa.getDescripcion()) +
						// pun.getValoracion());
						System.out.print("evaluacion");
					}
				}
			}
		}

		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.getStyleClass().add("xAxis");
		//yAxis.setUpperBound(0.0);
		chart = new LineChart<String, Number>(xAxis, yAxis);

		series = new Series<String, Number>();
		for (Areafuncional fa : faList) {
			series.getData().add(new XYChart.Data<String, Number>(fa.getDescripcion(), fa.getPuntuacionMaxima()));
		}
		series.setName("Puntuación máxima");
		chart.getData().add(series);
		
		for (Alumno stu : students) {
			series = new Series<String, Number>();
			for (String faName : datos.keySet()) {
				series.getData().add(new XYChart.Data<String, Number>(faName, datos.get(faName)));
				series.setName(stu.getNombre() + " " + stu.getApellido1() + " " + stu.getApellido2());
			}
			chart.getData().add(series);
		}

		xAxis.setTickLabelRotation(30);
		chart.setTitle("Grafico generado");

		root.setCenter(chart);
	}

	public void caChart(List<Alumno> students, List<Categorizacion> caList) {
		for (Categorizacion ca : caList) {
			datos.put(ca.getDescripcion(), 0);
			for (Alumno stu : students) {
				datos.replace(ca.getDescripcion(), datos.get(ca.getDescripcion()) + 10);
				if (stu.getEvaluacions().size() > 0) {
					for (Puntuacion pun : stu.getEvaluacions().get(0).getPuntuacions()) {
						// datos.replace(fa.getDescripcion(), datos.get(fa.getDescripcion()) +
						// pun.getValoracion());
						System.out.print("evaluacion");
					}
				}
			}
		}

		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.getStyleClass().add("xAxis");
		//yAxis.setUpperBound(0.0);
		chart = new LineChart<String, Number>(xAxis, yAxis);

		series = new Series<String, Number>();
		for (Categorizacion ca : caList) {
			series.getData().add(new XYChart.Data<String, Number>(ca.getDescripcion(), ca.getPuntuacionMaxima()));
		}
		series.setName("Puntuación máxima");
		chart.getData().add(series);
		
		for (Alumno stu : students) {
			series = new Series<String, Number>();
			for (String faName : datos.keySet()) {
				series.getData().add(new XYChart.Data<String, Number>(faName, datos.get(faName)));
				series.setName(stu.getNombre() + " " + stu.getApellido1() + " " + stu.getApellido2());
			}
			chart.getData().add(series);
		}

		chart.setTitle("Grafico generado");

		root.setCenter(chart);
	}

	public void itChart(List<Alumno> students, List<Item> itList) {
		for (Item it : itList) {
			datos.put("Item " + it.getNumero(), 0);
			for (Alumno stu : students) {
				datos.replace("Item " + it.getNumero(), datos.get("Item " + it.getNumero()) + 10);
				if (stu.getEvaluacions().size() > 0) {
					for (Puntuacion pun : stu.getEvaluacions().get(0).getPuntuacions()) {
						// datos.replace(fa.getDescripcion(), datos.get(fa.getDescripcion()) +
						// pun.getValoracion());
						System.out.print("evaluacion");
					}
				}
			}
		}

		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.getStyleClass().add("xAxis");
		//yAxis.setUpperBound(0.0);
		chart = new LineChart<String, Number>(xAxis, yAxis);

		/*
		series = new Series<String, Number>();
		for (Item it : itList) {
			series.getData().add(new XYChart.Data<String, Number>(it.getDescripcion(), 5));
		}
		series.setName("Puntuación máxima");
		chart.getData().add(series);*/
		
		for (Alumno stu : students) {
			series = new Series<String, Number>();
			for (String itName : datos.keySet()) {
				series.getData().add(new XYChart.Data<String, Number>(itName, datos.get(itName)));
				series.setName(stu.getNombre() + " " + stu.getApellido1() + " " + stu.getApellido2());
			}
			chart.getData().add(series);
		}

		chart.setTitle("Grafico generado");

		root.setCenter(chart);
	}

}
