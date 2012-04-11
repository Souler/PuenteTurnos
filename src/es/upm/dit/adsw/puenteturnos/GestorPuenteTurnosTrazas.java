package es.upm.dit.adsw.puenteturnos;

/* ESTE GESTOR ES IGUAL A GestorPuenteTurnos, PERO CON TRAZAS PARA
 * SEGUIR SU FUNCIONAMIENTO.
 */


/**
 * @author Alejandro Alonso
 * Monitor que gestiona la entrada a un puente de coches 
 * por sus dos extremos (norte y sur). Dentro el puente s—lo puede
 * haber un coche. Si hay coches esperando en sus dos extremos, se 
 * alterna el orden de entrada.
 */
public class GestorPuenteTurnosTrazas {

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
	public synchronized void entrarNorte(int idCoche) 
			throws InterruptedException {

		nCochesNorte ++;
		
		if (hayCocheEnPuente ||	(!turnoNorte && nCochesSur > 0))
			System.out.println("NNNNN El coche " + idCoche + 
					" se bloquea en la entrada");
			
		while (hayCocheEnPuente || (!turnoNorte && nCochesSur > 0)) wait();
		hayCocheEnPuente = true;
		nCochesNorte --;
		turnoNorte = false;
		System.out.println("N>>>>> El coche " + idCoche + 
				" entra por el norte");
		
	}

	
	/** M'etodo que ejecutan los coches que quiere entrar por el sur.
	 * El coche entrar‡ en el puente cuando estŽ vacio y sea el turno de
	 * los coches del sur o no haya coches esperando en el norte.
	 * @throws InterruptedException Esta excepci'on se eleva
	 * cuando se interrumpe a la hebra mientra est'a esperando
	 */
	public synchronized void  entrarSur(int idCoche) 
			throws InterruptedException {
		nCochesSur ++;
		
		if (hayCocheEnPuente || (turnoNorte && nCochesNorte > 0))
			System.out.println("SSSSS El coche " + idCoche + 
				" se bloquea en la entrada");
		
		while (hayCocheEnPuente || (turnoNorte && nCochesNorte > 0)) wait();
		hayCocheEnPuente = true;
		nCochesSur --;
		turnoNorte = true;
		System.out.println("S>>>>> El coche " + idCoche + 
				" entra por el sur");
	}

	/** M'etodo que invoca el coche que est'a en el puente al salir
	 * del mismo
	 */
	public synchronized void salirPuente(int idCoche) {
		hayCocheEnPuente = false;
		notifyAll();
	}
}
