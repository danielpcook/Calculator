package calculator;

import java.util.Scanner;
import java.util.ArrayList;


public class Calculator {
	public static ArrayList<Character> operandsInEquation = new ArrayList<Character>();
	public static ArrayList<Double> numsInEquation = new ArrayList<Double>();

	public static void main(String[] args) throws InvalidOperationException {
		Scanner scanner1 = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);
		int num1;
		int num2;
		
		System.out.println("Welcome to the calculator");
		System.out.println("Enter operation or type 'i' for instructions");
		System.out.println("Separate numbers and operands with space");
		System.out.println();
		
		String input = scanner1.nextLine();
		String equation = "";
		
		if(input == "i") {
			printInstructions();
		}
		else {
			equation = input;
		}
		
		if(!(checkIfValid(equation))) {
			throw new InvalidOperationException("Invalid Equation");
		}
		
		printEquation(equation);
		

	}
	
	public static void printInstructions() {
		System.out.println("Calculator Operations:");
		System.out.println("For addition(+), subtraction(-), multiplication(*), or division(%) operations, use (+, -, *, %)");
		System.out.println("For operations with fractions, use 'f'");
		
	}
	
	public static boolean checkIfValid(String equation) throws InvalidOperationException {
		Scanner numScanner = new Scanner(equation);
		double curr;
		int numCount = 0;
		int opCount = 0;
		boolean scanningNum = false;
		
		//invalid if equation contains characters other than numbers or allowed operands
		//invalid if final character is not a number
		for(int i = 0; i < equation.length() - 1; i++) {
			if(!(equation.charAt(i) == '1' || equation.charAt(i) == '2' || equation.charAt(i) == '3' || equation.charAt(i) == '4' || equation.charAt(i) == '5' || equation.charAt(i) == '6' || equation.charAt(i) == '7' || equation.charAt(i) == '8' || equation.charAt(i) == '9' || equation.charAt(i) == '0' || equation.charAt(i) == '+' || equation.charAt(i) == '-' || equation.charAt(i) == '*' || equation.charAt(i) == '/' || equation.charAt(i) == '^' || equation.charAt(i) == '.' || equation.charAt(i) == ' ')) {
				throw new InvalidOperationException("Error, Invalid Character Detected");
			}
		}
		if(!(equation.charAt(equation.length() -1) == '1' || equation.charAt(equation.length() -1) == '2' || equation.charAt(equation.length() -1) == '3' || equation.charAt(equation.length() -1) == '4' || equation.charAt(equation.length() -1) == '5' || equation.charAt(equation.length() -1) == '6' || equation.charAt(equation.length() -1) == '7' || equation.charAt(equation.length() -1) == '8' || equation.charAt(equation.length() -1) == '9' || equation.charAt(equation.length() -1) == '0')) {
			throw new InvalidOperationException("Error, Invalid Character Detected");
		}
		
		//scan through equation string
		Scanner dscnr = new Scanner(equation);
		
		//detect instances of doubles in equation, then add to arraylist of doubles
		while(dscnr.hasNext()) {
			if(dscnr.hasNextDouble()) {
				numCount++;
				numsInEquation.add(dscnr.nextDouble());
				
			}
			else {
				dscnr.next();
			}
		}
		
		//count number of operands
		for(int i = 0; i < equation.length(); i++) {
			if(equation.charAt(i) == '+' || equation.charAt(i) == '-' || equation.charAt(i) == '*' || equation.charAt(i) == '/' || equation.charAt(i) == '/' || equation.charAt(i) == '^') {
				opCount++;
				operandsInEquation.add(equation.charAt(i));
			}
		}
		
		if(opCount == (numCount - 1) && opCount > 0){
			return true;
		}
		
		return false;
	}
	
	public static void printEquation(String equation) {
		System.out.print(equation + " = " + solveEquation(equation));
	}
	
	public static double solveEquation(String equation) {
		int highestPriorityIndex;
		int currOpVal;
		char currentOperand;
		double currNum1;
		double currNum2;
		
		while(!(operandsInEquation.isEmpty())) {
			highestPriorityIndex = 0;
			currentOperand = '-';
			currOpVal = 0;
			for(int i = 0; i < operandsInEquation.size(); i++) {
				//PEMDAS
				if(assignValue(operandsInEquation.get(i)) > currOpVal) {
					currOpVal = assignValue(operandsInEquation.get(i));
					highestPriorityIndex = i;
					currentOperand = operandsInEquation.get(i);
				}
				
			}
			currNum1 = numsInEquation.get(highestPriorityIndex);
			currNum2 = numsInEquation.get(highestPriorityIndex + 1);
			
			numsInEquation.set(highestPriorityIndex, equate(currNum1, currNum2, currentOperand));
			
			numsInEquation.remove(highestPriorityIndex + 1);
			operandsInEquation.remove(highestPriorityIndex);
		}
		
		return numsInEquation.get(0);
	}
	
	public static int assignValue(char c) {
		if(c == '-') {
			return 0;
		}
		else if(c == '+') {
			return 1;
		}
		else if(c == '*') {
			return 2;
		}
		else if(c == '/') {
			return 3;
		}
		else{
			return 4;
		}
	}
	
	public static double equate(double num1, double num2, char c) {
		if(c == '-') {
			return num1 - num2;
		}
		else if(c == '+') {
			return num1 + num2;
		}
		else if(c == '*') {
			return num1 * num2;
		}
		else if(c == '/') {
			return num1 / num2;
		}
		else{
			return Math.pow(num1, num2);
		}
	}
	

}
