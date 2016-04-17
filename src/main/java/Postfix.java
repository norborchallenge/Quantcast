
import java.util.Stack;



/**
 *
 * @author nuboat
 */
public interface Postfix {
	
	default double evaluate(final String[] postfix) {
		final Stack<Double> s = new Stack<>();

		double r;
		final int len = postfix.length;

		for (int i = 0; i < len; i++) {
			final String ch = postfix[i];

			if (isOperator(ch)) {
				final double x = s.pop();
				final double y = s.pop();

				switch (ch) {
					case "+":
						r = y + x;
						break;
					case "-":
						r = y - x;
						break;
					case "*":
						r = y * x;
						break;
					case "/":
						r = y / x;
						break;
					default:
						r = 0;
				}
				s.push(r);
			} else {
				s.push(Double.valueOf(postfix[i]));
			}
		}

		return s.pop();
	}

	static boolean isOperator(final String ch) {
		switch (ch) {
			case "*":
			case "/":
			case "+":
			case "-":
				return true;
			default:
				return false;
		}
	}
}
