
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <P>
 * A -> Z, 1 -> N A1 A2 A3 .... ZN
 *
 * @author Peerapat A, created on 2016-04-17
 */
public class Spreadsheet implements Postfix {

	private static final Logger LOG = Logger.getLogger(Spreadsheet.class.getSimpleName());

	public static void main(final String[] args) {
		Logger
				.getLogger(Spreadsheet.class.getSimpleName())
				.setLevel(Level.SEVERE);
		
		if (args.length > 0) {
			new Spreadsheet().run(args[0]);	
		} else {
			final List<String[]> lines = new ArrayList<>();
			try (final BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
				while(true ) {
					final String line = br.readLine();
					if (line.isEmpty()) {
						break;
					} else {
						lines.add(line.split(" "));
					}
				}
			} catch (final IOException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
			}
			
			new Spreadsheet().run(lines);	
		}
		
		
	}

	private final Map<String, CellValue> cells = new HashMap<>();

	void run(final String filename) {
		final List<String[]> lines = read(filename);
		LOG.log(Level.INFO, "Lines : {0}", lines.size());
		
		run(lines);
	}
	
	void run(final List<String[]> lines) {
		final String[] meta = lines.remove(0);
		final int sizeX = Integer.valueOf(meta[0]);
		final int sizeY = Integer.valueOf(meta[1]);
		System.out.println(sizeX + " " + sizeY);

		final List<String[]> values = Collections.unmodifiableList(lines);
		LOG.log(Level.INFO, "Values : {0}", values.size());

		int count = 0;
		for (int y = 0; y < sizeY; y++) {
			for (int x = 1; x <= sizeX; x++) {
				cells.put("" + (char) ('A' + y) + x, new CellValue(values.get(count++)));
			}
		}
		LOG.log(Level.INFO, "Cells : {0}", cells);

		int runcount = 1;
		try {
			while (isSuccess(cells)) {
				LOG.log(Level.INFO, "Run {0}", runcount++);
				calculate();
			}
			
			display(sizeX, sizeY);
		} catch (final IllegalStateException e) {
			System.out.println(e.getMessage());
		} catch (final Exception e) {
			System.out.println("System Error.");
		}
	}

	void display(final int sizeX, final int sizeY) {
		for (int y = 0; y < sizeY; y++) {
			for (int x = 1; x <= sizeX; x++) {
				System.out.println(cells.get("" + (char) ('A' + y) + x).toText());
			}
		}
	}

	void calculate() {
		final List<String> processList = cells
				.keySet()
				.parallelStream()
				.filter(key -> isProcess(cells, cells.get(key)))
				.collect(Collectors.toList());
		
		if (processList.isEmpty()) {
			throw new IllegalStateException("Cannot Process: Not enough value");
		}
		
		processList
				.parallelStream()
				.forEach( key -> processCell(cells, cells.get(key)));
	}

	void processCell(final Map<String, CellValue> cells, final CellValue cell) {
		final String[] postfix = new String[cell.original.length];
		for (int i = 0; i < postfix.length; i++) {
			final char ch = cell.original[i].charAt(0);
			if ((ch >= 'A' && ch <= 'Z')) {
				postfix[i] = Double.toString(cells.get(cell.original[i]).getValue());
			} else {
				postfix[i] = cell.original[i];
			}
		}
		cell.setValue(evaluate(postfix));
	}

	boolean isProcess(final Map<String, CellValue> cells, final CellValue cell) {
		if (cell.hasValue()) {
			return false;
		}

		for (final String s : cell.original) {
			final char ch = s.charAt(0);
			if ((ch >= 'A' && ch <= 'Z') && (cells.get(s).noValue())) {
				return false;
			}
		}

		return true;
	}
	
	Optional<CellValue> shouldProcess(final Map<String, CellValue> cells, final CellValue cell) {
		if (cell.hasValue()) {
			return Optional.empty();
		}

		for (final String s : cell.original) {
			final char ch = s.charAt(0);
			if ((ch >= 'A' && ch <= 'Z') && (cells.get(s).noValue())) {
				return Optional.empty();
			}
		}

		return Optional.of(cell);
	}

	boolean isSuccess(final Map<String, CellValue> cells) {
		return !cells.keySet().parallelStream().noneMatch(key -> cells.get(key).noValue());
	}

	List<String[]> read(final String filename) {
		try (final Stream<String> stream = Files.lines(Paths.get(filename))) {
			return stream
					.map(line -> line.split(" "))
					.collect(Collectors.toList());
		} catch (final IOException e) {
			return (List<String[]>) Collections.EMPTY_LIST;
		}
	}

}
