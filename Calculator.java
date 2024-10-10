import java.util.Scanner;
import java.util.Stack;

public class AdvancedCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculating = true;

        System.out.println("Welcome to the Advanced Calculator!");

        while (continueCalculating) {
            System.out.print("Enter an expression (e.g., 3 + 4 * 2): ");
            String expression = scanner.nextLine();

            try {
                double result = evaluateExpression(expression);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("Do you want to perform another calculation? (yes/no): ");
            String response = scanner.nextLine();
            continueCalculating = response.equalsIgnoreCase("yes");
        }

        System.out.println("Thank you for using the Advanced Calculator!");
        scanner.close();
    }

    private static double evaluateExpression(String expression) throws Exception {
        // Remove whitespace
        expression = expression.replaceAll("\\s+", "");

        // Use stacks for the evaluation
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // If the character is a number, parse the full number
            if (Character.isDigit(ch) || (ch == '-' && (i == 0 || operators.size() > 0 && operators.peek() == '('))) ) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                i--; // decrement to counter the outer loop's increment
            } 
            // If the character is '(', push it to operators stack
            else if (ch == '(') {
                operators.push(ch);
            } 
            // If the character is ')', solve the entire brace
            else if (ch == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // pop '('
            } 
            // If the character is an operator
            else if (isOperator(ch)) {
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(ch);
            }
        }

        // Apply remaining operations
        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private static double applyOperation(char operator, double b, double a) throws Exception {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new Exception("Division by zero is not allowed.");
                return a / b;
            default:
                throw new Exception("Invalid operator: " + operator);
        }
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}




// Its very useful
