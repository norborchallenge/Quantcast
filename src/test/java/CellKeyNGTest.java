

import org.testng.annotations.Test;

/**
 *
 * @author nuboat
 */
public class CellKeyNGTest {
	
	final CellKey k1 = new CellKey(1, 'A');
	final CellKey k2 = new CellKey(1, 'A');
	final CellKey k3 = new CellKey(2, 'A');

	@Test
	public void testToString() {
		assert "A1".equals(k1.toString());
	}
	
	@Test
	public void testEquals0() {
		assert k1.equals(null) == false;
	}
	
	@Test
	public void testEquals1() {
		assert k1.equals(k2) == true;
	}
	
	@Test
	public void testEquals2() {
		assert k1.equals(k3) == false;
	}
	
}
