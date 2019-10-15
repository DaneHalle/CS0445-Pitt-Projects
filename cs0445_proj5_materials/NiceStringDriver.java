import java.util.*;

public class NiceStringDriver {
	public static void main(String[] args) {
		Map<String, Double> vars = new HashMap<>();
		// you can change or add more variables here.
		vars.put("x", 10.0);
		vars.put("y", 27.0);

		Expression expr = Expression.quickParse("4*x + y/9 + 12");

		System.out.println("toString:        " + expr.toNiceString());
		System.out.println("toPostfix:       " + expr.toPostfix());
		System.out.println("evaluate:        " + expr.evaluate(vars));
		System.out.println("reciprocal:      " + expr.reciprocal().toNiceString());
		System.out.println("reciprocal(num): " + Expression.Number(7).reciprocal());
		System.out.println("reciprocal(div): " + Expression.quickParse("x / 10").reciprocal().toNiceString());
		System.out.println("getVariables:    " + expr.getVariables());

		Expression mean = Expression.geometricMean(new double[]{4, 9, 3, 7, 6});
		System.out.println("geometricMean:   " + mean.toNiceString());
		System.out.println("it evalutes to:  " + mean.evaluate(vars));

		System.out.println("===================================================");

		Map<String, Double> testVars1=new HashMap<>();
		testVars1.put("x",42.0);
		testVars1.put("y",9.0);
		testVars1.put("z",10.0);
		testVars1.put("j",0.0);
		testVars1.put("pi",3.14);

		Expression testExpr1=Expression.quickParse("x*y/z+j-pi*3");

		System.out.println("toString:        " + testExpr1.toNiceString());
		System.out.println("toPostfix:       " + testExpr1.toPostfix());
		System.out.println("evaluate:        " + testExpr1.evaluate(testVars1));
		System.out.println("reciprocal:      " + testExpr1.reciprocal().toNiceString());
		System.out.println("reciprocal(num): " + Expression.Number(5).reciprocal());
		System.out.println("reciprocal(div): " + Expression.quickParse("10 / x").reciprocal().toNiceString());
		System.out.println("getVariables:    " + testExpr1.getVariables());

		Expression testMean1 = Expression.geometricMean(new double[]{42.0,9.0,10.0,3.14});
		System.out.println("geometricMean:   " + testMean1.toNiceString());
		System.out.println("it evalutes to:  " + testMean1.evaluate(testVars1));

		System.out.println("===================================================");

		Map<String, Double> testVars2=new HashMap<>();
		testVars2.put("x",1.5376669114795833);

		Expression testExpr2=Expression.quickParse("69*x^3+420");

		System.out.println("toString:        " + testExpr2.toNiceString());
		System.out.println("toPostfix:       " + testExpr2.toPostfix());
		System.out.println("evaluate:        " + testExpr2.evaluate(testVars2));
		System.out.println("reciprocal:      " + testExpr2.reciprocal().toNiceString());
		System.out.println("reciprocal(num): " + Expression.Number(10).reciprocal());
		System.out.println("reciprocal(div): " + Expression.quickParse("y / 42").reciprocal().toNiceString());
		System.out.println("getVariables:    " + testExpr2.getVariables());

		Expression testMean2 = Expression.geometricMean(new double[]{69, 1,188, 420, 666});
		System.out.println("geometricMean:   " + testMean2.toNiceString());
		System.out.println("it evalutes to:  " + testMean2.evaluate(testVars2));

		System.out.println("===================================================");

		Map<String, Double> testVars3=new HashMap<>();
		testVars3.put("x",69.0);
		testVars3.put("y",7.0);

		Expression testExpr3=Expression.quickParse("12*x-49*y^2/(3*x+2*y)");

		System.out.println("toString:        " + testExpr3.toNiceString());
		System.out.println("toPostfix:       " + testExpr3.toPostfix());
		System.out.println("evaluate:        " + testExpr3.evaluate(testVars3));
		System.out.println("reciprocal:      " + testExpr3.reciprocal().toNiceString());
		System.out.println("reciprocal(num): " + Expression.Number(1).reciprocal());
		System.out.println("reciprocal(div): " + Expression.quickParse("x / 10").reciprocal().toNiceString());
		System.out.println("getVariables:    " + testExpr3.getVariables());

		Expression testMean3 = Expression.geometricMean(new double[]{12, 69, 49, 7, 2, 3, 2});
		System.out.println("geometricMean:   " + testMean3.toNiceString());
		System.out.println("it evalutes to:  " + testMean3.evaluate(testVars3));

		System.out.println("===================================================");

		Map<String, Double> testVars4=new HashMap<>();
		testVars3.put("x",69.0);
		testVars3.put("y",7.0);

		Expression testExpr4=Expression.quickParse("10+9-8+7-6*5/4*3^2");

		System.out.println("toString:        " + testExpr4.toNiceString());
		System.out.println("toPostfix:       " + testExpr4.toPostfix());
		System.out.println("evaluate:        " + testExpr4.evaluate(testVars4));
		System.out.println("reciprocal:      " + testExpr4.reciprocal().toNiceString());
		System.out.println("reciprocal(num): " + Expression.Number(42).reciprocal());
		System.out.println("reciprocal(div): " + Expression.quickParse("x / 10").reciprocal().toNiceString());
		System.out.println("getVariables:    " + testExpr4.getVariables());

		Expression testMean4 = Expression.geometricMean(new double[]{10,9,8,7,6,5,4,3,2});
		System.out.println("geometricMean:   " + testMean4.toNiceString());
		System.out.println("it evalutes to:  " + testMean4.evaluate(testVars4));

		System.out.println("===================================================");
	}
}