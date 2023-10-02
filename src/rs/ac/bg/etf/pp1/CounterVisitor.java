package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends  CounterVisitor{
		
		public void visit(VariableParam f) {
			count++;
		}
		
		public void visit(ArrayParam f) {
			count++;
		}
	}
	
public static class VarCounter extends  CounterVisitor{
		
		public void visit(VariableDeclaration f) {
			count++;
		}
		
		public void visit(ArrayVariableDeclaration	 f) {
			count++;
		}
	}
}
