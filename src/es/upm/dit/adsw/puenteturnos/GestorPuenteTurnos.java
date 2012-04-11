package es.upm.dit.adsw.puenteturnos;


/**
 * @author Alejandro Alonso
 * Monitor que gestiona la entrada a un puente de coches 
 * por sus dos extremos (norte y sur). Dentro el puente s—lo puede
 * haber un coche. Si hay coches esperando en sus dos extremos, se 
 * alterna el orden de entrada.
 */
public class GestorPuenteTurnos {

	/** Indica si hay un coche dentro del puente
	 */
	private boolean hayCocheEnPuente = false;

	/** Indica en n'umero de coches que est'an esperando
	 * para entrar en el puente por el norte
	 */
	private int nCochesNorte = 0;

	/** Indica en n'umero de coches que est'an esperando
	 * para entrar en el puente por el sur
	 */
	private int nCochesSur   = 0;

	/** Indica si el turno es de los coches que vienen
	 * por el norte o por el sur. 
	 */
	private boolean turnoNorte = true;

	/** M'etodo que ejecutan los coches que quiere entrar por el norte.
	 * El coche entrar‡ en el puente cuando estŽ vacio y sea el turno de
	 * los coches del norte o no haya coches esperando en el sur.
	 * @throws InterruptedException Esta excepci'on se eleva
	 * cuando se interrumpe a la hebra mientra est'a esperando
	 */
	public synchronized void entrarNorte() 
			throws InterruptedException {

		nCochesNorte ++;		
		while (hayCocheEnPuente ||
				(!turnoNorte && nCochesSur > 0)) wait();
		hayCocheEnPuente = true;
		nCochesNorte --;
		turnoNorte = false;
	}

	
	/** M'etodo que ejecutan los coches que quiere entrar por el sur.
	 * El coche entrar‡ en el puente cuando estŽ vacio y sea el turno de
	 * los coches del sur o no haya coches esperando en el norte.
	 * @throws InterruptedException Esta excepci'on se eleva
	 * cuando se interrumpe a la hebra mientra est'a esperando
	 */
	public synchronized void  entrarSur() 
			throws InterruptedException {
		nCochesSur ++;
		while (hayCocheEnPuente ||
				(turnoNorte && nCochesNorte > 0)) wait();
		hayCocheEnPuente = true;
		nCochesSur --;
		turnoNorte = true;
	}

	/** M'etodo que invoca el coche que est'a en el puente al salir
	 * del mismo
	 */
	public synchronized void salirPuente() {
		hayCocheEnPuente = false;
		notifyAll();
	}
}
