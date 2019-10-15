public class postfix
{
	public static String convertToPostFix(infix)
	{
		StackInterface<Character> operatorStack=new ArrayStack<Character>();
		String postfix="";
		while(infix has characters left to parse){
			nextChar=next nonb lank character of infix;
			switch(nextChar){
				case variable:
					postfix=postfix+(char)nextChar;
					break;
				case '^':
					operatorStack.push(nextChar);
					break;
				case '+':
				case '-':
				case '*':
				case '/':
					while(!operatorStack.isEmpty() && precedence of nextCar <= precidence of operatorStack.peek()){
						postfix=postfix+operatorStack.peek();
						operatorStack.pop();
					}
					operatorStack.push(nextChar);
					break;
				case '(':
					oepratorStack.push(nextChar);
					break;
				case ')':
					char topOp=operatorStack.pop();
					while(topOp 	= '('){
						postfix=postfix+topOp;
						topOperator=operatorStack.pop();
					}
					break;
				default: 
					break;
			}
		}
		while(!operatorStack.isEmpty()){
			postfix=postfix+operatorStack.pop();
		}
		return postfix;
	}
	
	public static Double evalPostFix(String postfix)
	{
		StackInterface<Double> valueStack=new ArrayStack<Double>();
		while(postfix has characters  left to parse){
			nextChar=next nonblank character of postfix;
			switch(nextChar){
				case variable:
					valueStack.push(nextChar);
					break;
				case '+':
					Double operandTwo=valueStack.pop();
					Double operandOne=valueStack.pop();
					Double result=operandOne+oeprandTwo;
					valueStack.push(result);
					break;
				case '-':
					Double operandTwo=valueStack.pop();
					Double operandOne=valueStack.pop();
					Double result=operandOne-operandTwo;
					valueStack.push(result);
					break;
				case '*':
					Double operandTwo=valueStack.pop();
					Double operandOne=valueStack.pop();
					Double result=operandOne*operandTwo;
					valueStack.push(result);
					break;
				case '/':
					Double operandTwo=valueStack.pop();
					Double operandOne=valueStack.pop();
					Double result=operandOne/operandTwo;
					valueStack.push(result);
					break;
				case '^':
					Double operandTwo=valueStack.pop();
					Double operandOne=valueStack.pop();
					Double result=Math.pow(operandOne,operandTwo);
					valueStack.push(result);
					break;
				default: 
					break;
			}
		}
		return valueStack.peek();
	}
}