package stablo;
/*
 * Autor @mutavdzicmilos
 * Radjeno dana 01.06.2018.
 * BinarnoStablo sa dodatnim metodama po uzoru na projekat
 * radjen na casu iz programiranja 2 vezan za Kolekcije
 */
import java.util.HashSet;
import java.util.Set;

import cvorstabla.CvorBSTStabla;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class BinarnoStablo<T> implements Map<Integer, T> {

	private CvorBSTStabla<T> koren;
	//null konstruktor
	public BinarnoStablo() {
		this.koren = null;
	}

	public boolean containsKey(Object key) {
		if (praznoStablo())
			return false;
		CvorBSTStabla<T> cvor = nalazakPoKljucuP((Integer) key, koren);
		if (cvor == null)
			return false;
		else
			return true;
	}

	//praznjenje stabla(brisanje pokazivaca)
	public void clear() {
		koren = null;
	}
	private CvorBSTStabla<T> nalazakPoKljucuP(Integer kljuc, CvorBSTStabla<T> koren) {
		if (koren == null)
			return null;
		if (koren.kljuc.equals(kljuc))
			return koren;
		if (koren.kljuc < kljuc)
			return nalazakPoKljucuP(kljuc, koren.desno);
		else
			return nalazakPoKljucuP(kljuc, koren.levo);
	}

	@Override
	public boolean containsValue(Object value) {
		return daLiPostoji(koren, value);
	}

	private boolean daLiPostoji(CvorBSTStabla<T> koren, Object value) {
		if (koren == null)
			return false;
		if (koren.vrednost.equals(value))
			return true;
		return daLiPostoji(koren.levo, value) || daLiPostoji(koren.desno, value);
	}

	
	public boolean isEmpty() {
		return praznoStablo();
	}

	private void puniSet(CvorBSTStabla<T> k, Set<Entry<Integer, T>> elementi) {
		if (k == null)
			return;
		Map.Entry<Integer, T> entry = new AbstractMap.SimpleEntry<Integer, T>(k.kljuc, k.vrednost);
		elementi.add(entry);
		puniSet(k.levo, elementi);
		puniSet(k.desno, elementi);

	}

	public T get(Object key) {
		CvorBSTStabla<T> cvor = nalazakPoKljucuP((Integer) key, koren);
		if (cvor == null)
			return null;
		else
			return cvor.vrednost;
	}

	public boolean praznoStablo() {
		return koren == null;
	}

	public Set<Integer> keySet() {
		if (isEmpty())
			return null;
		return vratiKljuceveP();
	}

	private Set<Integer> vratiKljuceveP() {
		Set<Integer> kljucevi = new HashSet<>();
		dodajKljuceve(koren, kljucevi);
		return kljucevi;
	}

	private void dodajKljuceve(CvorBSTStabla<T> k, Set<Integer> kljucevi) {
		if (k == null)
			return;
		kljucevi.add(k.kljuc);
		dodajKljuceve(k.levo, kljucevi);
		dodajKljuceve(k.desno, kljucevi);

	}

	@Override
	public T put(Integer key, T value) {
		CvorBSTStabla<T> cvor = nalazakPoKljucuP(key, koren);
		if (cvor == null) {
			ubaci(key, value);
			return null;
		} else {
			T vrednost = cvor.vrednost;
			cvor.vrednost = value;
			return vrednost;
		}
	}

	private void ubaci(Integer key, T value) {
		if (isEmpty()) {
			this.koren = new CvorBSTStabla<T>(key, value);
			return;
		}
		ubaciPrivate(key, value, koren);
	}

	private void ubaciPrivate(Integer key, T value, CvorBSTStabla<T> k) {
		if (k.kljuc > key) {
			if (k.levo == null) {
				k.levo = new CvorBSTStabla<T>(key, value);
			} else
				ubaciPrivate(key, value, k.levo);
		} else if (k.kljuc < key) {
			if (k.desno == null) {
				k.desno = new CvorBSTStabla<T>(key, value);
			} else
				ubaciPrivate(key, value, k.desno);
		}
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends T> m) {
		m.forEach((k, v) -> put(k, v));
	}

	@Override
	public T remove(Object key) {
		if (isEmpty())
			return null;
		CvorBSTStabla<T> cvor = nalazakPoKljucuP((Integer) key, koren);
		if (cvor == null)
			return null;
		if ((cvor.levo == null && cvor.desno == null) || (cvor.levo == null ^ cvor.desno == null)) {
			return izbaciListPoluList(cvor);
		} else {
			CvorBSTStabla<T> maxL = max(cvor.levo);
			T vrednost = cvor.vrednost;
			cvor.vrednost = maxL.vrednost;
			maxL.vrednost = vrednost;
			return izbaciListPoluList(maxL);
		}
	}

	private CvorBSTStabla<T> max(CvorBSTStabla<T> k) {
		if (k == null)
			return null;
		if (k.desno == null)
			return k;
		return max(k.desno);
	}

	private T izbaciListPoluList(CvorBSTStabla<T> cvor) {
		CvorBSTStabla<T> roditelj = vratiRoditelja(koren, cvor);
		CvorBSTStabla<T> dete = cvor.levo != null ? cvor.levo : cvor.desno;
		if (roditelj == null) {
			koren = dete;
			return cvor.vrednost;
		}
		if (roditelj.levo == cvor) {
			roditelj.levo = dete;
			return cvor.vrednost;
		} else {
			roditelj.desno = dete;
			return cvor.vrednost;
		}
	}

	private CvorBSTStabla<T> vratiRoditelja(CvorBSTStabla<T> k, CvorBSTStabla<T> cvor) {
		if (k == null || k == cvor)
			return null;
		if (cvor.kljuc < k.kljuc) {
			if (k.levo == cvor) {
				return k;
			}
			return vratiRoditelja(k.levo, cvor);
		} else {
			if (k.desno == cvor) {
				return k;
			}
			return vratiRoditelja(k.desno, cvor);
		}
	}

	public int velicina() {
		return velicina(koren);
	}

	private int velicina(CvorBSTStabla<T> koren) {
		if (koren == null)
			return 0;
		return 1 + velicina(koren.levo) + velicina(koren.desno);
	}

	@Override
	public Collection<T> values() {
		if (koren == null)
			return null;
		return vratiVrednostiCvorova();
	}

	private Collection<T> vratiVrednostiCvorova() {
		Collection<T> vrednosti = new LinkedList<T>();
		vratiVrednostiCvorovaP(koren, vrednosti);
		return vrednosti;
	}

	private void vratiVrednostiCvorovaP(CvorBSTStabla<T> k, Collection<T> vrednosti) {
		if (k == null)
			return;
		vrednosti.add(k.vrednost);
		vratiVrednostiCvorovaP(k.levo, vrednosti);
		vratiVrednostiCvorovaP(k.desno, vrednosti);
	}

	@Override
	public Set<Entry<Integer, T>> entrySet() {
		if (isEmpty())
			return null;
		Set<Entry<Integer, T>> elementi = new HashSet<>();
		puniSet(koren, elementi);
		return elementi;
	}

	@Override
	public int size() {
		return velicina();
	}
	
}