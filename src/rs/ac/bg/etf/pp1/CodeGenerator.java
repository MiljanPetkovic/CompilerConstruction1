package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	
	public int getMainPc() {	
		return mainPc;
	}
	
	public void visit(PrintStmt printStmt) {
		if (printStmt.getExpr().struct.getKind() == Struct.Char) {
			Code.loadConst(0);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(0);
			Code.put(Code.print);
		}
	}

	public void visit(PrintStmtNumConst printStmt) {
		int width = printStmt.getN1();
		if (printStmt.getExpr().struct.getKind() == Struct.Int) {
			Code.loadConst(width);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(width);
			Code.put(Code.print);
		}
	}
	
	public void visit(NumConst numConst) {
		Obj obj = Tab.insert(Obj.Con, "$", numConst.struct);
		obj.setLevel(0);
		obj.setAdr(numConst.getN1());
		
		Code.load(obj);
	}
	
	public void visit(CharConst charConst) {
		Obj obj = Tab.insert(Obj.Con, "$", charConst.struct);
		obj.setLevel(0);
		obj.setAdr(charConst.getC1());
		
		Code.load(obj);
	}
	
	public void visit(BoolConst boolConst) {
		Obj obj = Tab.insert(Obj.Con, "$", SemanticAnalyzer.boolType);
		obj.setLevel(0);
		obj.setAdr(boolConst.getB1());
		
		Code.load(obj);
	}
	
	
	public void visit(ParenFactor parenFactor) {
		//mislim da ne treba nista
	}
	
	public void visit(DesignatorFactor designatorFactor) {
		if ( designatorFactor.getDesignator().getSingleDesignator() instanceof DesignatorType) {
			Code.load(designatorFactor.getDesignator().obj);
		} else if ( designatorFactor.getDesignator().getSingleDesignator() instanceof DesignatorArray) {
			if ( designatorFactor.getDesignator().obj.getType().getElemType().getKind() == Struct.Char) {//adresa niza se upisuje odma prilikom citanja pod uslovom da je read i redni broj
				Code.put(Code.baload);
			} else {
				Code.put(Code.aload);
			}
		} else {
			System.out.println("Greska: DesignatorFactor");
		}
	}
	
		public void visit(ArrayAlocation arrayAlocation) {
			Code.put(Code.newarray);
			if(arrayAlocation.getType().struct.getKind() == Tab.charType.getKind()) {
				Code.put(0);
			} else {
				Code.put(1);
			}
		}
	
	public void visit(MulOpFactorList mulOpFactorList) {
		if ( mulOpFactorList.getMulop() instanceof Muloper) {
			Code.put(Code.mul);
		} else if( mulOpFactorList.getMulop() instanceof Divop) {
			Code.put(Code.div);
		} else if (mulOpFactorList.getMulop() instanceof  Modop) {
			Code.put(Code.rem);
		} else {
			System.err.println("Greska: MulOpFactorList");
		}
	}
	
	public void visit(ArrayName arrayName) {
		Code.load(arrayName.obj);
	}
	
	public void visit(MinusTermExpr minusTermExpr) {
		Code.put(Code.neg);
	}
	
	public void visit(AddExpr addExpr) {
		if (addExpr.getAddop() instanceof Addoperation ) {
			Code.put(Code.add);
		} else if(addExpr.getAddop() instanceof Subop ) {
			Code.put(Code.sub);
		}else {
			System.err.println("Greska: AddExpr");
		}
	}
	
	public void visit(Assignment assignment) {
		if ( assignment.getAssign().getDesignator().getSingleDesignator() instanceof DesignatorType) {
			Code.store(assignment.getAssign().getDesignator().obj);
		} else if ( assignment.getAssign().getDesignator().getSingleDesignator() instanceof DesignatorArray) {
			if ( assignment.getAssign().getDesignator().obj.getType().getElemType().getKind() == Struct.Char) {//adresa niza se upisuje odma prilikom citanja pod uslovom da je read i redni broj
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}
		} else {
			System.out.println("Greska: Assignment");
		}
		
	}
	 
	public void visit(ArrName arr) {
			Code.load(arr.obj);
	}
	
	public void visit(ReadStmt readStmt) {
		//citanje
		if ( readStmt.getDesignator().obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		
		//smestanje
		
		if ( readStmt.getDesignator().getSingleDesignator() instanceof DesignatorType) {
			Code.store(readStmt.getDesignator().obj);
		} else if ( readStmt.getDesignator().getSingleDesignator() instanceof DesignatorArray) {
			if ( readStmt.getDesignator().obj.getType().getElemType().getKind() == Struct.Char) {//adresa niza se upisuje odma prilikom citanja pod uslovom da je read i redni broj
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}
		} else {
			System.err.println("Greska: ReadStmt");
		}
	}
	
	public void visit(TypeName typeName) {
		if( "main".equals(typeName.getMethName())) {
			mainPc= Code.pc;
		}
		
		typeName.obj.setAdr(Code.pc); 
		
		SyntaxNode methodeNode = typeName.getParent();
		
		VarCounter varCounter =new VarCounter();
		methodeNode.traverseTopDown(varCounter);
		
		FormParamCounter formParamCounter = new FormParamCounter();
		methodeNode.traverseTopDown(formParamCounter);
		
		//Generate entry
		Code.put(Code.enter);
		Code.put(typeName.obj.getLevel());
		Code.put(typeName.obj.getLocalSymbols().size());
		
	}
	
	public void visit(VoidName typeName) {
		if( "main".equals(typeName.getVoidmethName())) {
			mainPc= Code.pc;
		}
		
		typeName.obj.setAdr(Code.pc); 
		
		SyntaxNode methodeNode = typeName.getParent();
		
		VarCounter varCounter =new VarCounter();
		methodeNode.traverseTopDown(varCounter);
		
		FormParamCounter formParamCounter = new FormParamCounter();
		methodeNode.traverseTopDown(formParamCounter);
		
		//Generate entry
		Code.put(Code.enter);
		Code.put(typeName.obj.getLevel());
		Code.put(typeName.obj.getLocalSymbols().size());	
	}
	
	
	public void visit(Increment increment) {
		if (increment.getDesignator().obj.getKind() == Obj.Var && increment.getDesignator().obj.getLevel() > 0 ) {//ako je lokalna promenljica
			Code.load(increment.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(increment.getDesignator().obj);
		} else {//ako je globalna ili clan niza
			if (increment.getDesignator().obj.getKind() == Obj.Var ) {
				Code.load(increment.getDesignator().obj);//stavice getstatic
				Code.loadConst(1);
				Code.put(Code.add);
				Code.store(increment.getDesignator().obj);
			} else { // ako je niz prvi na steku postoji niz, index
				Code.put(Code.dup2);
				Code.put(Code.aload);
				Code.loadConst(1);
				Code.put(Code.add);
				Code.put(Code.astore);
			}			
		}
	}

	public void visit(Decrement decrement) {
		if (decrement.getDesignator().obj.getKind() == Obj.Var && decrement.getDesignator().obj.getLevel() > 0 ) {//ako je lokalna promenljica
			Code.load(decrement.getDesignator().obj);
			Code.loadConst(-1);
			Code.put(Code.add);
			Code.store(decrement.getDesignator().obj);
		} else {//ako je globalna ili clan niza
			if (decrement.getDesignator().obj.getKind() == Obj.Var ) {
				Code.load(decrement.getDesignator().obj);//stavice getstatic
				Code.loadConst(-1);
				Code.put(Code.add);
				Code.store(decrement.getDesignator().obj);
			} else { // ako je niz prvi na steku postoji niz, index
				Code.put(Code.dup2);
				Code.put(Code.aload);
				Code.loadConst(-1);
				Code.put(Code.add);
				Code.put(Code.astore);
			}			
		}
	}
	
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	public void visit(FindAnyStatement findAnyStatement) {
		Code.loadConst(0);
		
		Code.put(Code.store);
		Code.put(99);
		
		int startLoopPc = Code.pc;
		
		Code.put(Code.dup);
		
		Code.load(findAnyStatement.getDesignator().obj);
		
		Code.put(Code.load);
		Code.put(99);
		
		Code.put(Code.aload);
		
		int address1 = Code.pc;
		Code.putFalseJump(Code.ne, 0);
		
		Code.put(Code.load);
		Code.put(99);
		
		Code.loadConst(1);
		
		Code.put(Code.add);
		
		Code.put(Code.dup);
		
		Code.put(Code.store);
		Code.put(99);
		
		Code.load(findAnyStatement.getDesignator().obj);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.eq, startLoopPc);
		
		
		Code.loadConst(0);
		
		int address2 = Code.pc;
		Code.putJump(0);
		
		Code.fixup(address1+1);
		
		Code.loadConst(1);
		
		
		Code.fixup(address2+1);
		Code.store(findAnyStatement.getAssign().getDesignator().obj);
		
		Code.put(Code.pop);
	}
	
	public void visit(ProgName progName) {
		Obj obj_ord = Tab.find("ord");
		obj_ord.setAdr(Code.pc);
		int allSym = obj_ord.getLocalSymbols().size();

		Code.put(Code.enter);
		Code.put(1);
		Code.put(allSym);
		Code.loadConst(0);
		
		Code.put(Code.exit);
		Code.put(Code.return_);

		
		Obj obj_chr = Tab.find("chr");
		obj_chr.setAdr(Code.pc);
		allSym = obj_chr.getLocalSymbols().size();

		Code.put(Code.enter);
		Code.put(1);
		Code.put(allSym);
		Code.loadConst(0);
		
		Code.put(Code.exit);
		Code.put(Code.return_);

		Obj obj_len = Tab.find("len");
		obj_len.setAdr(Code.pc);
		allSym = obj_len.getLocalSymbols().size();
		
		Code.put(Code.enter);
		Code.put(1);
		Code.put(allSym);
		Code.loadConst(0);
		Code.put(Code.arraylength);
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	
}
