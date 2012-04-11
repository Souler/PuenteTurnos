package es.upm.dit.adsw.puenteturnos;

import java.util.ArrayList;

/**
 * @author Alejandro Alonso
 * Monitor que gestiona la entrada a un puente de coches 
 * por sus dos extremos (norte y sur). Dentro el puente sólo puede
 * haber un coche. Si hay coches esperando en sus dos extremos, se 
 * alterna el orden de entrada.
 */
public class GestorPuenteTurnos {

	/** Indica si hay un coche dentro del puente
	 */
	private boolean hayCocheEnPuente = false;

	/** Almacena los coches que est'an esperando
	 * para entrar en el puente por el norte
	 */
	private ArrayList<CocheNorte> lCochesNorte = new ArrayList<CocheNorte>();

	/** Almacena los coches que est'an esperando
	 * para entrar en el puente por el sur
	 */
	private ArrayList<CocheSur> lCochesSur = new ArrayList<CocheSur>();

	/** Indica si el turno es de los coches que vienen
	 * por el norte o por el sur. 
	 */
	private boolean turnoNorte = true;

	/** M'etodo que ejecutan los coches que quiere entrar por el norte.
	 * El coche entrará en el puente cuando esté vacio y sea el turno de
	 * los coches del norte o no haya coches esperando en el sur.
	 * @throws InterruptedException Esta excepci'on se eleva
	 * cuando se interrumpe a la hebra mientra est'a esperando
	 */
	public synchronized void entrarNorte(CocheNorte coche)
			throws InterruptedException {

		lCochesNorte.add(coche);
		while ((hayCocheEnPuente || (!turnoNorte && !lCochesSur.isEmpty()))
				&& coche != lCochesNorte.get(0)) wait();

		hayCocheEnPuente = true;
		lCochesNorte.remove(0);
		turnoNorte = false;
	}

	
	/** M'etodo que ejecutan los coches que quiere entrar por el sur.
	 * El coche entrará en el puente cuando esté vacio y sea el turno de
	 * los coches del sur o no haya coches esperando en el norte.
	 * @throws InterruptedException Esta excepci'on se eleva
	 * cuando se interrumpe a la hebra mientra est'a esperando
	 */
	public synchronized void  entrarSur(CocheSur coche)
			throws InterruptedException {

		lCochesSur.add(coche);
		while ((hayCocheEnPuente || (turnoNorte && !lCochesNorte.isEmpty()))
				&& coche != lCochesSur.get(0)) wait();

		hayCocheEnPuente = true;
		lCochesSur.remove(0);
		turnoNorte = true;
	}

	/** M'etodo que invoca el coche que est'a en el puente al salir
	 * del mismo
	 */
	public synchronized void salirPuente(int idCoche) {
		hayCocheEnPuente = false;
		notifyAll();
	}
}
