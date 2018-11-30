package connection.service;

import java.util.ArrayList;
import java.util.List;

import model.Aula;

public class ClassroomServiceImpl implements Service<Aula> {
	
	private static Aula aulaPruebas = new Aula();
	
	public void setAulaPruebas() {
		ClassroomServiceImpl.aulaPruebas.setId(6);
		ClassroomServiceImpl.aulaPruebas.setNombre("Aula6");
		ClassroomServiceImpl.aulaPruebas.setCapacidad(6);
	}
	
	public Aula getAulaPruebas() {
		return ClassroomServiceImpl.aulaPruebas;
	}
	

	@Override
	public List<Aula> getAll() {
		//Codigo de ejemplo usado para testear
		
				List<Aula> resultado = new ArrayList<Aula>();
				Aula aula1 = new Aula();
				Aula aula2 = new Aula();
				Aula aula3 = new Aula();
				Aula aula4 = new Aula();
				Aula aula5 = new Aula();
				
				aula1.setId(1);
				aula1.setNombre("Aula1");
				aula1.setCapacidad(1);
				
				aula2.setId(2);
				aula2.setNombre("Aula2");
				aula2.setCapacidad(2);
				
				aula3.setId(3);
				aula3.setNombre("Aula3");
				aula3.setCapacidad(3);
				
				aula4.setId(4);
				aula4.setNombre("Aula4");
				aula4.setCapacidad(4);
				
				aula5.setId(5);
				aula5.setNombre("Aula5");
				aula5.setCapacidad(5);
				
				
				
				resultado.add(aula1);
				resultado.add(aula2);
				resultado.add(aula3);
				resultado.add(aula4);
				resultado.add(aula5);
				
				return resultado;
	}

	@Override
	public boolean edit(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
