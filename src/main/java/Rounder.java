
import java.math.RoundingMode;
import java.text.DecimalFormat;



/**
 *
 * @author nuboat
 */
public interface Rounder {
	static final DecimalFormat DF = new DecimalFormat("#.00000"); 
	
	default String toDisplay(final double value) {
		DF.setRoundingMode(RoundingMode.HALF_UP);
		return DF.format(value);
	}

}
