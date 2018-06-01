package cvorstabla;
/*
 * Autor @mutavdzicmilos
 * Radjeno dana 01.06.2018.
 * CvorBSTStabla sa integer kljucem po uzoru na projekat
 * radjen na casu iz programiranja 2 vezan za Kolekcije
 */
public class CvorBSTStabla<T> {

	public Integer kljuc;
	public T vrednost;
	public CvorBSTStabla<T> levo, desno;
	public CvorBSTStabla(Integer kljuc, T value,CvorBSTStabla<T> desno ,CvorBSTStabla<T> levo) {
		this.kljuc = kljuc;
		this.vrednost = value;
		this.levo = levo;
		this.desno = desno;
	}
	
	
	public CvorBSTStabla(Integer kljuc, T vrednost) {
		this.kljuc = kljuc;
		this.vrednost = vrednost;
		this.levo = null;
		this.desno = null;
	}
	
	


	
}