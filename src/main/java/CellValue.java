
import java.util.Arrays;

/**
 *
 * @author nuboat
 */
public class CellValue implements Rounder {

	private Double value;

	public final String[] original;

	public CellValue(final String[] original) {
		this.original = original;
	}

	public boolean hasValue() {
		return value != null;
	}
	
	public boolean noValue() {
		return value == null;
	}
	
	public void setValue(final Double value) {
		this.value = value;
	}
	
	public Double getValue() {
		return value;
	}

	public String toText() {
		return (value != null) ? toDisplay(value) : "";
	}

	@Override
	public String toString() {
		return Arrays.toString(original);
	}

}
