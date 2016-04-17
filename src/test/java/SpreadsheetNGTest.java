
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.Test;

/**
 *
 * @author Peerapat A, created on 2016-04-17
 */
public class SpreadsheetNGTest implements Postfix, Rounder {

	final Spreadsheet sheet = new Spreadsheet();

	@Test
	public void testReadRelativePath() {
		final String filename = "example";

		final List<String[]> lines = sheet.read(filename);

		assert lines.size() == 7 : "Size should be 7";
		assert "[39, B1, B2, *, /]".equals(Arrays.toString(lines.get(6))) : "Last line should be '39 B1 B2 * /'";
	}

	@Test
	public void testReadFullPath() {
		final String filename = "/Users/nuboat/Projects/Quatcast/example";

		final List<String[]> lines = sheet.read(filename);

		assert lines.size() == 7 : "Size should be 7";
		assert "[39, B1, B2, *, /]".equals(Arrays.toString(lines.get(6))) : "Last line should be '39 B1 B2 * /'";
	}

	@Test
	public void testEvaluate() {
		final String[] postfix = new String[]{"20", "3", "/", "2", "+"};

		final double total = sheet.evaluate(postfix);

		assert "8.7".equals(toDisplay(total));
	}

}
