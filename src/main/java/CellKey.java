

/**
 *
 * @author nuboat
 */
public class CellKey {
	
	public final int x;
	
	public final char y;
	
	public CellKey(final int x, final char y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return ""+y+x;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		} else if (!(obj instanceof CellKey)) {
			return false;
		}
		final CellKey k = (CellKey) obj;
		return this.x == k.x && this.y == k.y;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + this.x;
		hash = 29 * hash + this.y;
		return hash;
	}
	
}
