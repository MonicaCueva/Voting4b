package es.uniovi.asw.Calculate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.asw.Calculate.voters.ReferendumOption;
import es.uniovi.asw.DBManagement.modelo.Voto;

import org.junit.Before;
import org.junit.Test;

public class ReferendumTest {
	
	private ReferendumOption ref;
	private List<Voto> votos;
	
	@Before
	public void before(){
		votos = new ArrayList<Voto>();
		votos.add(new Voto(null, "SI", false, false, false));
		votos.add(new Voto(null, "NO", false, false, false));
		votos.add(new Voto(null, null, false, true, false));
		votos.add(new Voto(null, null, false, false, true));
	}

	@Test
	public void testIntialitation() {
		ref = new ReferendumOption();
		assertEquals(0,ref.getResult().get("NO").intValue());
		assertEquals(0,ref.getResult().get("SI").intValue());
		assertEquals(0,ref.getResult().get("NULO").intValue());
		assertEquals(0,ref.getResult().get("BLANCO").intValue());
		assertEquals(0, ref.nVoto());
	}
	
	@Test
	public void testBasicCounter() {
		ref = new ReferendumOption();
		int i=0;
		for(Voto v: votos){
			i++;
			ref.contarVoto(v);
			assertEquals(i, ref.nVoto());
		}
	}
	
	@Test
	public void testBasicRecount() {
		ref = new ReferendumOption();
		for(Voto v: votos){
			ref.contarVoto(v);
			if(v.isBlanco())
				assertEquals(1,ref.getResult().get("BLANCO").intValue());
			else if(v.isNulo())
				assertEquals(1,ref.getResult().get("NULO").intValue());
			else
				assertEquals(1,ref.getResult().get(v.getOpcion()).intValue());
		}
	}
	
	@Test
	public void testPercents() {
		ref = new ReferendumOption();
		for(Voto v: votos){
			ref.contarVoto(v);
		}//Total
		assertEquals(((double)ref.getResult().get("SI")/ref.nVoto())*100
				,ref.getPercents().get("SI"),0);
		
		ref.contarVoto(new Voto(null, "NO", false, false, false));
		ref.contarVoto(new Voto(null, "NO", false, false, false));
		ref.contarVoto(new Voto(null, "NO", false, false, false));
		assertEquals(((double)ref.getResult().get("NO")/ref.nVoto())*100
				,ref.getPercents().get("NO"),0);
		assertEquals(((double)ref.getResult().get("SI")/ref.nVoto())*100
				,ref.getPercents().get("SI"),0);
		
	}
	
	@Test
	public void testNull() {
		ref = new ReferendumOption();
		ref.contarVoto(new Voto(null, "sin valor", false, false,false ));
		assertEquals(1,ref.nVoto());
		assertEquals(1,ref.getResult().get("NULO").intValue());
	}

}
