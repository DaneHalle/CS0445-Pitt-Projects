import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.*;
import java.util.function.*;

class Expression {
	private String _type;
	private String _value;
	private Expression _left, _right;

	private Expression(String type, String value) {
		this(type, value, null, null);
	}

	private Expression(String type, String value, Expression left, Expression right) {
		_type = type;
		_value = value;
		_left = left;
		_right = right;
	}

	/**
	* Creates an operator expression.
	*/
	public static Expression Operator(Expression left, String operator, Expression right) {
		return new Expression("Operator", operator, left, right);
	}

	/**
	* Creates a number expression.
	*/
	public static Expression Number(double value) {
		return new Expression("Number", Double.toString(value));
	}

	/**
	* Creates a variable expression.
	*/
	public static Expression Variable(String name) {
		return new Expression("Variable", name);
	}

	/**
	* Very quick-and-dirty expression parser; doesn't really do any error checking.
	* But it's enough to build an Expression from a (known-to-be-correct) String.
	*/
	public static Expression quickParse(String input) {
		StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
		tokenizer.ordinaryChar('-');
		tokenizer.ordinaryChar('/');
		Stack<Character> operators = new Stack<>();
		Stack<Expression> operands = new Stack<>();
		try { tokenizer.nextToken(); }
		catch (IOException e) { throw new RuntimeException(e); }
		while(tokenizer.ttype != StreamTokenizer.TT_EOF) {
			int prec = 2;
			switch(tokenizer.ttype) {
				case StreamTokenizer.TT_NUMBER: operands.push(Number(tokenizer.nval));   break;
				case StreamTokenizer.TT_WORD:   operands.push(Variable(tokenizer.sval)); break;
				case '^': case '(': operators.push((char)tokenizer.ttype);  break;
				case ')':
					while(operators.peek() != '(')
						poperator(operators, operands);
					operators.pop();
					break;
				case '+': case '-': prec = 1; // fall thru
				case '*': case '/':
					while(!operators.empty()) {
						char top = operators.peek();
						int topPrec = (top == '^') ? 3 : (top == '*' || top == '/') ? 2 : 1;
						if(top == '(' || topPrec < prec) break;
						poperator(operators, operands);
					}
					operators.push((char)tokenizer.ttype);
					break;
				default: throw new RuntimeException("wat");
			}
			try { tokenizer.nextToken(); }
			catch (IOException e) { throw new RuntimeException(e); }
		}
		while(!operators.empty()){ poperator(operators, operands); }
		return operands.pop();
	}

	private static void poperator(Stack<Character> operators, Stack<Expression> operands) {
		Expression r = operands.pop();
		Expression l = operands.pop();
		operands.push(Operator(l, operators.pop() + "", r));
	}

	// These can be used to quickly check if an Expression is an Operator, Number, or Variable.
	public boolean isOperator() { return _type.equals("Operator"); }
	public boolean isNumber()   { return _type.equals("Number");   }
	public boolean isVariable() { return _type.equals("Variable"); }

	/**
	* For Numbers, converts the _value to a double and returns it.
	* Will crash for non-Numbers.
	*/
	private double getNumberValue() { return Double.parseDouble(_value); }

	/**
	* Recursively clones an entire Expression tree.
	* Note how this method works: operators are the recursive case, and
	* numbers and variables are base cases.
	*/
	public Expression clone() {
		if(this.isOperator()) {
			return Expression.Operator(_left.clone(), _value, _right.clone());
		} else if(this.isVariable()) {
			return Expression.Variable(_value);
		} else {
			return Expression.Number(getNumberValue());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	/**
	* Converts this expression to an infix expression representation.
	*/
	public String toString() 
	{
		if(_left==null || _right==null){
			return _value;
		}
		return "("+_left+" "+_value+" "+_right+")";
	}

	/**
	* Converts this expression to a postfix expression representation.
	*/
	public String toPostfix() 
	{
		if(_left==null || _right==null){
			return _value;
		}
		return _left.toPostfix()+" "+_right.toPostfix()+" "+_value;
	}

	/**
	* Given the variables map (which tells what values each variable has),
	* evaluates the expression and returns the computed value.
	*/
	public double evaluate(Map<String, Double> variables) throws ExpressionError 
	{
		double left=0; 
		double right=0;
		double out=0; 

		if(isVariable()){
			if(variables.containsKey(_value)){
				return variables.get(_value);
			}else{
				String thrown="The variable "+_value+" is not defined within the Map. If you would ";
				thrown+="like for the variable to be defined, go to the Driver.java";
				thrown+="/NiceStringDriver.java file and define it with ";
				thrown+="\"name_of_map.put(variable, value);\"";
				throw new ExpressionError(thrown);
			}
		}else if(isNumber()){
			return getNumberValue();
		}else{
			left=_left.evaluate(variables);
			right=_right.evaluate(variables);

			if(_value.equals("+")){
				out=left+right;
			}else if(_value.equals("-")){
				out=left-right;
			}else if(_value.equals("*")){
				out=left*right;
			}else if(_value.equals("/")){
				out=left/right;
			}else if(_value.equals("^")){
				out=Math.pow(left,right);
			}
			return out;
		 }
	}

	/**
	* Creates a new Expression that is the reciprocal of this one.
	*/
	public Expression reciprocal() 
	{
		if(_value.equals("/")){
			return Operator(_right.clone(),"/",_left.clone());
		}else if(isNumber()){
			return Number(1/getNumberValue());
		}else{
			return Operator(Number(1),"/",clone());
		}
	}

	/**
	 * Privately recursive method to aid in finding all variables in the expression
	 */
	private Set<String> findVariables(Set<String> in) 
	{
		if(isNumber()){
			return in;
		}else if(isVariable()){
			in.add(_value);
			return in;
		}else if(isOperator() ){	
			_left.clone().findVariables(in);
			_right.clone().findVariables(in);
		}
		return in;
	}



	/**
	* Gets a set of all variables which appear in this expression.
	*/
	public Set<String> getVariables() 
	{
		Set<String> variables = new HashSet<>();
		return findVariables(variables);
	}

	private static Expression makeMean(double[] numbers)
	{
		if(numbers.length>2){
			double hold=numbers[0];
			double[] newNumbers=new double[numbers.length-1];
			for(int i=1; i<numbers.length; i++){
				newNumbers[i-1]=numbers[i];
			}
			return Expression.Operator(Number(hold),"*",makeMean(newNumbers));
		}
		return Expression.Operator(Number(numbers[0]),"*",Number(numbers[1]));
	}	

	/**
	* Constructs a new Expression of the form:
	* 	(numbers[0] * numbers[1] * ... numbers[n-1]) ^ (1 / n)
	* and returns it.
	*/
	public static Expression geometricMean(double[] numbers) 
	{
		return Expression.Operator(makeMean(numbers),"^",Number(1.0/numbers.length));
	}

	private int getPrecidence()
	{
		String[] prec={"^","*","/","+","-"};
		for(int i=0; i<5; i++){
			if(_value.equals(prec[i])){
				if(i==1 || i==2){
					return 2;
				}
				if(i==3 || i==4){
					return 3;
				}
				return 1;
			}
		}
		return 4; //Should never be returned
	}

	/**
	* EXTRA CREDIT: converts this expression to an infix expression representation,
	* but only places parentheses where needed to override the order of operations.
	*/
	public String toNiceString() 
	{
		if(_left==null || _right==null){
			return _value;
		}

		boolean left=false;
		if(_left.isOperator() && getPrecidence()<_left.getPrecidence()){
			left=true;
		}

		boolean right=false;
		if(_right.isOperator() && getPrecidence()<_right.getPrecidence()){
			right=true;
		}

		if(right && left){
			return "("+_left.toNiceString()+") "+_value+" ("+_right.toNiceString()+")";
		}else if(left){
			return "("+_left.toNiceString()+") "+_value+" "+_right.toNiceString();
		}else if(right){
			return _left.toNiceString()+" "+_value+" ("+_right.toNiceString()+")";
		}else{
			return _left.toNiceString()+" "+_value+" "+_right.toNiceString();
		}
	}
}