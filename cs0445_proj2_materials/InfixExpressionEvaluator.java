import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.lang.Math;
import java.util.EmptyStackException;
import java.lang.String;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 * 
 * Student: dmh148
 */
public class InfixExpressionEvaluator 
{
	// Tokenizer to break up our input into tokens
	StreamTokenizer tokenizer;

	// Stacks for operators (for converting to postfix) and operands (for
	// evaluating)
	StackInterface<Character> operatorStack;
	StackInterface<Double> operandStack;

	// Stack for bracket error checking
	StackInterface<Character> bracketStack;
	boolean open=false;

	// Used to keep track of defined names
	static String[] names;
	static Double[] vals;

	/**
	 * Initializes the evaluator to read an infix expression from an input
	 * stream.
	 * @param input the input stream from which to read the expression
	 */
	public InfixExpressionEvaluator(InputStream input) 
	{
		// Initialize the tokenizer to read from the given InputStream
		tokenizer = new StreamTokenizer(new BufferedReader(
						new InputStreamReader(input)));

		// StreamTokenizer likes to consider - and / to have special meaning.
		// Tell it that these are regular characters, so that they can be parsed
		// as operators
		tokenizer.ordinaryChar('-');
		tokenizer.ordinaryChar('/');

		// Allow the tokenizer to recognize end-of-line, which marks the end of
		// the expression
		tokenizer.eolIsSignificant(true);

		// Initialize the stacks
		operatorStack = new ArrayStack<Character>();
		operandStack = new ArrayStack<Double>();
			bracketStack = new ArrayStack<Character>();
	}

	/**
	 * Parses and evaluates the expression read from the provided input stream,
	 * then returns the resulting value
	 * @return the value of the infix expression that was parsed
	 */
	public Double evaluate() throws ExpressionError 
	{
		// Get the first token. If an IO exception occurs, replace it with a
		// runtime exception, causing an immediate crash.
		try {
			tokenizer.nextToken();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Continue processing tokens until we find end-of-line
		while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
			// Consider possible token types
			switch (tokenizer.ttype) {
				case StreamTokenizer.TT_NUMBER:
					// If the token is a number, process it as a double-valued
					// operand
					handleOperand((double)tokenizer.nval);
					break;
				case '+':
				case '-':
				case '*':
				case '/':
				case '^':
					// If the token is any of the above characters, process it
					// is an operator
					handleOperator((char)tokenizer.ttype);
					break;
				case '(':
				case '{':
				case '[':
					// If the token is open bracket, process it as such. Forms
					// of bracket are interchangeable but must nest properly.
					handleOpenBracket((char)tokenizer.ttype);
					break;
				case ')':
				case '}':
				case ']':
					// If the token is close bracket, process it as such. Forms
					// of bracket are interchangeable but must nest properly.
					handleCloseBracket((char)tokenizer.ttype);
					break;
				case StreamTokenizer.TT_WORD:
					// If the token is a name, process it as such.
					handleName(tokenizer.sval.toLowerCase());
					break;
				default:
					// If the token is any other type or value, throw an
					// expression error
					throw new ExpressionError("Unrecognized token: " +
									String.valueOf((char)tokenizer.ttype));
			}

			// Read the next token, again converting any potential IO exception
			try {
				tokenizer.nextToken();
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Almost done now, but we may have to process remaining operators in
		// the operators stack
		handleRemainingOperators();

		// Return the result of the evaluation
		return operandStack.peek(); 
	}

	/**
	 * This method is called when the evaluator encounters an operand. It
	 * manipulates operatorStack and/or operandStack to process the operand
	 * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
	 * @param operand the operand token that was encountered
	 */
	void handleOperand(double operand) 
	{
		operandStack.push((Double)operand);
	}

	/**
	 * This method is called when the evaluator encounters a name. This is a
	 * special kind of operand, really. See the project description.
	 * @param name the lowercase name that was encountered
	 */
	void handleName(String name) 
	{
		boolean one=false; boolean two=false;
		if(name.equals("pi")){
			handleOperand(Math.PI);
		}else if(name.equals("e")){
			handleOperand(Math.E);
		}else{
			one=true;
		}
		for(int i=0; i<names.length; i++){
			if(name.equals(names[i])){
				handleOperand(vals[i]);
				two=false; break;
			}else{
				two=true;
			}
		}
		if(one && two){
			throw new ExpressionError("Unrecognized name");
		}
	}

	/**
	 * Checks the operator to see what the precidence is. 
	 * Higher the precidence, the later it will be used. 
	 * @param operator Character that is taken in
	 * @return 1, 2, or 3 depending on the operator taken in
	 */
	private int checkPrec(char operator)
	{
		int prec=0;
		if(operator=='+' || operator=='-'){
			prec=3;
		}else if(operator=='*' || operator=='/'){
			prec=2;
		}else if(operator=='^'){
			prec=1;
		}else if(operator=='(' || operator=='{' || operator=='['){
			prec=0;
		}else if(operator==')' || operator=='}' || operator==']'){
			prec=-1;
		}
		return prec;
	}

	/**
	 * This method is called when the evaluator encounters an operator. It
	 * manipulates operatorStack and/or operandStack to process the operator
	 * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
	 * @param operator the operator token that was encountered
	 */
	void handleOperator(char operator) 
	{
		if(operatorStack.isEmpty()){
			operatorStack.push(operator);
		}else if(checkPrec(operator)<=checkPrec(operatorStack.peek())&&checkPrec(operator)>0&&!open){
			operatorStack.push(operator);
		}else if(checkPrec(operator)>checkPrec(operatorStack.peek())&&checkPrec(operator)>0&&!open){
			try{
				Double result=null;
				Double operandTwo=operandStack.pop();
				Double operandOne=operandStack.pop();
				switch(operatorStack.peek()){
					case '+':
						result=operandOne+operandTwo;
						handleOperand(result);
						break;
					case '-':
						result=operandOne-operandTwo;
						handleOperand(result);
						break;
					case '*':
						result=operandOne*operandTwo;
						handleOperand(result);
						break;
					case '/':
						result=operandOne/operandTwo;
						handleOperand(result);
						break;
					case '^':
						result=Math.pow(operandOne,operandTwo);
						handleOperand(result);
						break;
					default:
						break;
					}
					operatorStack.pop();
					operatorStack.push(operator);
				}catch(ExpressionError e){
					throw new ExpressionError("Operand Stack does not have at least two operands to complete task");
				}
		}else if(checkPrec(operator)==-1){
			StackInterface<Double> temp=new ArrayStack<Double>();
			char test=operatorStack.pop();
			StackInterface<Character> hold=new ArrayStack<Character>(); 
			while(checkPrec(test)!=0){
				if(hold.isEmpty()){
					hold.push(test);
					test=operatorStack.pop();
				}else if(checkPrec(test)<=checkPrec(hold.peek())){
					hold.push(test);
					test=operatorStack.pop();
				}else if(checkPrec(test)>checkPrec(hold.peek())){
					try{
						Double result=null;
						Double operandOne=temp.pop();
						Double operandTwo=temp.pop();
						switch(hold.peek()){
							case '+':
								result=operandOne+operandTwo;
								temp.push(result);
								break;
							case '-':
								result=operandOne-operandTwo;
								temp.push(result);
								break;
							case '*':
								result=operandOne*operandTwo;
								temp.push(result);
								break;
							case '/':
								result=operandOne/operandTwo;
								temp.push(result);
								break;
							case '^':
								result=Math.pow(operandOne,operandTwo);
								temp.push(result);
								break;
							default:
								break;
						}
					}catch(ExpressionError e){
						throw new ExpressionError("Operand Stack does not ahve at least two operands to complete task");
					}
					hold.pop();
					hold.push(test);
					test=operatorStack.pop();
				}
				temp.push(operandStack.pop());
			}
			temp.push(operandStack.pop());
			Double result=null;
			while(!hold.isEmpty()){
				try{
					Double operandOne=temp.pop();
					Double operandTwo=temp.pop();
					switch(hold.peek()){
						case '+':
							result=operandOne+operandTwo;
							temp.push(result);
							break;
						case '-':
							result=operandOne-operandTwo;
							temp.push(result);
							break;
						case '*':
							result=operandOne*operandTwo;
							temp.push(result);
							break;
						case '/':
							result=operandOne/operandTwo;
							temp.push(result);
							break;
						case '^':
							result=Math.pow(operandOne,operandTwo);
							temp.push(result);
							break;
						default:
							break;
						}
					hold.pop();
				}catch(ExpressionError e){
						throw new ExpressionError("Operand Stack does not have at least two operands to complete task");
				}
			}
			handleOperand(temp.peek());
		}else if(checkPrec(operator)==0||open){
			operatorStack.push(operator);
		}	
	}
		
	



	/**
	 * This method is called when the evaluator encounters an open bracket. It
	 * manipulates operatorStack and/or operandStack to process the open bracket
	 * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
	 * @param openBracket the open bracket token that was encountered
	 */
	void handleOpenBracket(char openBracket) 
	{
		bracketStack.push(openBracket);	
		open=true;
		handleOperator(openBracket);
	}

	/**
	 * This method is called when the evaluator encounters a close bracket. It
	 * manipulates operatorStack and/or operandStack to process the close
	 * bracket according to the Infix-to-Postfix and Postfix-evaluation
	 * algorithms.
	 * @param closeBracket the close bracket token that was encountered
	 */
	void handleCloseBracket(char closeBracket){
		switch(closeBracket){
			case ')':
				if(bracketStack.isEmpty()){
					throw new ExpressionError("No open bracket");
				}else if(bracketStack.peek()!='('){
					throw new ExpressionError("Mismatch brackets");
				}else{
					bracketStack.pop();
					if(bracketStack.isEmpty()){
						open=false;
					}
					handleOperator(closeBracket);
				}
				break;
			case '}':
				if(bracketStack.isEmpty()){
					throw new ExpressionError("No open bracket");
				}else if(bracketStack.peek()!='{'){
					throw new ExpressionError("Mismatch brackets");
				}else{
					bracketStack.pop();
					if(bracketStack.isEmpty()){
						open=false;
					}
					handleOperator(closeBracket);
				}
				break;
			case ']':
				if(bracketStack.isEmpty()){
					throw new ExpressionError("No open bracket");
				}else if(bracketStack.peek()!='['){
					throw new ExpressionError("Mismatch brackets");
				}else{
					bracketStack.pop();
					if(bracketStack.isEmpty()){
						open=false;
					}
					handleOperator(closeBracket);
				}
				break;
			default:
				break;	
		}
	}

	/**
	 * This method is called when the evaluator encounters the end of an
	 * expression. It manipulates operatorStack and/or operandStack to process
	 * the operators that remain on the stack, according to the Infix-to-Postfix
	 * and Postfix-evaluation algorithms.
	 */
	void handleRemainingOperators()
	{
		Double result=null;
		while(!operatorStack.isEmpty()){
			try{
				Double operandTwo=operandStack.pop();
				Double operandOne=operandStack.pop();
				switch(operatorStack.peek()){
					case '+':
						result=operandOne+operandTwo;
						handleOperand(result);
						break;
					case '-':
						result=operandOne-operandTwo;
						handleOperand(result);
						break;
					case '*':
						result=operandOne*operandTwo;
						handleOperand(result);
						break;
					case '/':
						result=operandOne/operandTwo;
						handleOperand(result);
						break;
					case '^':
						result=Math.pow(operandOne,operandTwo);
						handleOperand(result);
						break;
					default:
						break;
					}
				operatorStack.pop();
			}catch(ExpressionError e){
					throw new ExpressionError("Operand Stack does not have at least two operands to complete task");
			}
			
		}
	

		if(!bracketStack.isEmpty()){
			throw new ExpressionError("No close bracket");
		}

	}


	/**
	 * Creates an InfixExpressionEvaluator object to read from System.in, then
	 * evaluates its input and prints the result.
	 * @param args not used
	 */
	public static void main(String[] args) 
	{
		System.out.println("Infix expression:");

		//The code for distinguishing between declared names
		names=new String[args.length];
		vals=new Double[args.length];
		String hold="";
		int temp=0;
		char[] token;
		if(args.length>0){
			for(int i=0; i<args.length; i++){
				token=args[i].toCharArray();
				for(int j=0; j<token.length; j++){
					if(token[j]=='='){
						j++; temp=j; break;
					}else{
						hold=hold+""+token[j];
					}
				}
				names[i]=hold;
				hold="";
				for(int z=temp; z<token.length; z++){
					hold=hold+""+token[z];
				}
				vals[i]=Double.parseDouble(hold);
				hold="";
			}
		}

		InfixExpressionEvaluator evaluator =
						new InfixExpressionEvaluator(System.in);
		Double value = null;
		try {
			value = evaluator.evaluate();
		} catch (ExpressionError e) {
			System.out.println("ExpressionError: " + e.getMessage());
		}
		if (value != null) {
			System.out.println(value);
		} else {
			System.out.println("Evaluator returned null");
		}
	}






	/**
	 * Helper method to help get an output for the evaluate method.
	 * 
	 * So here is the story with this method. I was working on trying to 
	 * get this class working as described but frankly didn't know what 
	 * I was doing so thinking I was, I wrote this during one of my classes.
	 * After doing some more research and reviewing the notes, I realized
	 * that this is very wrong but didn't want to just delete it but rather 
	 * keep it so that maybe whoever is grading this will get a laugh or something.
	 * @return the Double output of the input
	 */
	private Double getOut()
	{
		Double out=operandStack.pop();
		while(!operandStack.isEmpty()){
			switch(operatorStack.peek()){
				case '(':
				case '{':
				case '[':
					operatorStack.pop();
				case ')':
				case '}':
				case ']':
					operatorStack.pop();
					break;
				
				case '+':
					Double addHold=operandStack.pop();
					operatorStack.pop();
					if(!operatorStack.isEmpty()){
						switch(operatorStack.peek()){
							case '*':
								Double multHold=operandStack.pop();
								operatorStack.pop();
								if(!operatorStack.isEmpty()){
									switch(operatorStack.peek()){
										case '^':
											if(!operatorStack.isEmpty()){
												switch(operatorStack.peek()){
													case '(':
													case '{':
													case '[':
														Double brackHold=operandStack.pop();
														operatorStack.pop();
													case ')':
													case '}':
													case ']':
														operatorStack.pop();
														break;
												}
											}
											operatorStack.pop();
											multHold=Math.pow(operandStack.pop(),multHold);
											break;
										default:
											break;
									}
								}
								addHold=multHold*addHold;
								break;
							case '/':
								Double divdHold=operandStack.pop();
								operatorStack.pop();
								if(!operatorStack.isEmpty()){
									switch(operatorStack.peek()){
										case '^':
											operatorStack.pop();
											divdHold=Math.pow(operandStack.pop(),divdHold);
											break;
										default:
											break;
									}
								}
								addHold=divdHold/addHold;
								break;
							case '^':
								operatorStack.pop();
								addHold=Math.pow(operandStack.pop(),addHold);
								break;
							default:
								break;
						}
					}
					out=addHold+out;
					break;
				case '-':
					Double subHold=operandStack.pop();
					operatorStack.pop();
					if(!operatorStack.isEmpty()){
						switch(operatorStack.peek()){
							case '*':
								operatorStack.pop();
								Double multHold=operandStack.pop();
								if(!operatorStack.isEmpty()){
									switch(operatorStack.peek()){
										case '^':
											operatorStack.pop();
											multHold=Math.pow(operandStack.pop(),multHold);
											break;
										default:
											break;
									}
								}
								subHold=multHold*subHold;
								break;
							case '/':
								Double divdHold=operandStack.pop();
								operatorStack.pop();
								if(!operatorStack.isEmpty()){
									switch(operatorStack.peek()){
										case '^':
											operatorStack.pop();
											divdHold=Math.pow(operandStack.pop(),divdHold);
											break;
										default:
											break;
									}
								}
								subHold=divdHold/subHold;
								break;
							case '^':
								operatorStack.pop();
								subHold=Math.pow(operandStack.pop(),subHold);
								break;
							default:
								break;
						}
					}
					out=subHold-out;
					break;
				case '*':
					Double multHold=operandStack.pop();
					operatorStack.pop();
					if(!operatorStack.isEmpty()){
						switch(operatorStack.peek()){
							case '^':
								operatorStack.pop();
								multHold=Math.pow(operandStack.pop(),multHold);
								break;
							default:
								break;
						}
					}
					out=multHold*out;
					break;
				case '/':
					Double divdHold=operandStack.pop();
					operatorStack.pop();
					if(!operatorStack.isEmpty()){
						switch(operatorStack.peek()){
							case '^':
								operatorStack.pop();
								divdHold=Math.pow(operandStack.pop(),divdHold);
								break;
							default:
								break;
						}
					}
					out=divdHold/out;
					break;
				case '^':
					operatorStack.pop();
					out=Math.pow(operandStack.pop(),out);
					break;
				default:
					break;
				}
			}
		return out;
	}

}