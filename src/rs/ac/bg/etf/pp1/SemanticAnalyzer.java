package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	private static int currentLevel = -1;
	public static final Struct boolType = new Struct(Struct.Bool);
	Struct currentTypeStruct = null;
	int varDeclCount = 0;
	Obj currentMethode = null;
	int nVars;
	boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	//====================================================
	
	public void visit(ProgName progName) {
    	Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
    	progName.obj =  Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	nVars=0;//?
    	Tab.openScope();
    	currentLevel++;
    }
	
	//Proverava da li postoji trazeni tip u tabeli simbola
	//dodeljuje vrednost currentTypeStruct= procitan tip
		public void visit(Type type) {
			Obj typeNode = Tab.find(type.getTypeName());
			if ( typeNode == Tab.noObj) {
				report_error("Greska: Tip nije pronadjen " + type.getTypeName() + " u tabeli simbola ", type);
				type.struct =Tab.noType;
			} else {
				if (Obj.Type == typeNode.getKind()) {
					type.struct = typeNode.getType();
					currentTypeStruct = typeNode.getType();
				}else {
					report_error("Greska: Ime" + type.getTypeName() + " ne predstavlja tip ", type);
					type.struct =Tab.noType;
				}
			}
		}
		
		//Deklarisanje int konstante
		//Provereno da li je dodeljena vrednost koja je int tipa
		//Proverava da li postoji dupla deklaracija
		public void visit(NumDeclaration numDeclaration) {
	    	Obj obj = Tab.find(numDeclaration.getNumName());
	    	if ( !obj.equals( Tab.noObj) && currentLevel == obj.getLevel()) {
				report_error("Greska: NumDeclaration: Visestruka deklaracija " + numDeclaration.getNumName() , numDeclaration);
				return;
	    	}
	    	Obj node = Tab.insert(Obj.Con, numDeclaration.getNumName(), Tab.intType);
			node.setAdr(numDeclaration.getNumValue());
			numDeclaration.obj = node;
		}
		
		//Deklarisanje char konstante
		//Provereno da li je dodeljena vrednost koja je char tipa
		//Proverava da li postoji dupla deklaracija
		public void visit(CharDeclaration charDeclaration) {
			Obj obj = Tab.find(charDeclaration.getCharName());
	    	if ( !obj.equals( Tab.noObj) && currentLevel == obj.getLevel()) {
	    		report_error("Greska: charDeclaration: Visestruka deklaracija " + charDeclaration.getCharName() , charDeclaration);
				return;
	    	}
	    	
			Obj node = Tab.insert(Obj.Con, charDeclaration.getCharName(),Tab.charType);
			node.setAdr(charDeclaration.getCharValue());
			charDeclaration.obj = node;
			
		}

		//Deklarisanje bool konstante
		//Provereno da li je dodeljena vrednost koja je bool tipa
		//Proverava da li postoji dupla deklaracija
		public void visit(BoolDeclaration boolDeclaration) {
			Obj obj = Tab.find(boolDeclaration.getBoolName());
	    	if ( !obj.equals( Tab.noObj) && currentLevel == obj.getLevel()) {
	    		report_error("Greska: BoolDeclaration: Visestruka deklaracija " + boolDeclaration.getBoolName() , boolDeclaration);
				return;
	    	}
			
			Obj node = Tab.insert(Obj.Con, boolDeclaration.getBoolName(), boolType);
			node.setAdr(boolDeclaration.getBoolValue());
			boolDeclaration.obj = node;
		}
		
		public void visit(SingleConstDeclaration singleConstDeclaration) {
			singleConstDeclaration.obj = singleConstDeclaration.getSingleConstDecl().obj;
		}
	
		public void visit(ConstDeclaration constDeclaration) {
			if ( constDeclaration.getConstDecl().obj.getType().getKind() != constDeclaration.getSingleConstDecl().obj.getType().getKind()) {
				report_error("Greska: ConstDeclaration: Sve konstante moraju biti istog tipa", constDeclaration);
			}
			constDeclaration.obj = constDeclaration.getSingleConstDecl().obj;
		}	
	
		public void visit(ConstantsDeclaration constantsDeclaration) {
			if ( constantsDeclaration.getType().struct.getKind() != constantsDeclaration.getConstDecl().obj.getType().getKind() ) {
				report_error("Greska: ConstDeclaration: Nedozvoljen tip konstante", constantsDeclaration);
			} else {
				report_info("Deklarisana konstanta " + constantsDeclaration.getConstDecl().obj.getName() + " = " + constantsDeclaration.getConstDecl().obj.getAdr(), constantsDeclaration);
			}
		}
		
		public void visit(VariableDeclaration variableDeclaration) {
			Obj obj = Tab.find(variableDeclaration.getVarName());
			if ( !obj.equals( Tab.noObj) && currentLevel == obj.getLevel()) {
				report_error("Greska: VariableDeclaration: Visestruka deklaracija " + variableDeclaration.getVarName() , variableDeclaration);
				return;
	    	}
			
			Obj varNode = Tab.insert(Obj.Var, variableDeclaration.getVarName(), currentTypeStruct);
			
			report_info("Deklarisana promenljiva " + variableDeclaration.getVarName(), variableDeclaration);
		}
		
		public void visit(ArrayVariableDeclaration arrayVariableDeclaration) {
			Obj obj = Tab.find(arrayVariableDeclaration.getArrayName());
			if ( !obj.equals( Tab.noObj) && currentLevel == obj.getLevel()) {
				report_error("Greska: ArrayVariableDeclaration: Visestruka deklaracija " + arrayVariableDeclaration.getArrayName() , arrayVariableDeclaration);
				return;
	    	}
			
			Struct variableType = new Struct(Struct.Array, currentTypeStruct);
			Obj varNode = Tab.insert(Obj.Var, arrayVariableDeclaration.getArrayName(), variableType);
			
			report_info("Deklarisana niz " + arrayVariableDeclaration.getArrayName(), arrayVariableDeclaration);
		}
		
	    public void visit(TypeName methodeTypeName) {
	    	currentMethode = Tab.insert(Obj.Meth, methodeTypeName.getMethName(), methodeTypeName.getType().struct);
	    	currentMethode.setLevel(0);
	    	methodeTypeName.obj = currentMethode;
	    	Tab.openScope();
	    	currentLevel++;
	    	report_info("Funkcija" + methodeTypeName.getMethName(), methodeTypeName);
	    }
	    
	    public void visit(VoidName methodeTypeName) {
	    	currentMethode = Tab.insert(Obj.Meth, methodeTypeName.getVoidmethName(), Tab.noType);
	    	currentMethode.setLevel(0);
	    	methodeTypeName.obj = currentMethode;
	    	Tab.openScope();
	    	currentLevel++;
	    	report_info("Funkcija" + methodeTypeName.getVoidmethName(), methodeTypeName);
	    }

	    public void visit(VariableParam variableParam) {
	    	currentMethode.setLevel(currentMethode.getLevel() + 1);
	    	Obj obj = Tab.find(variableParam.getVarName());
			if ( !obj.equals( Tab.noObj) && currentLevel == obj.getLevel()) {
				report_error("Greska: Dupla deklaracija " + variableParam.getVarName() , variableParam);
				return;
	    	}
			
			Obj varNode = Tab.insert(Obj.Var, variableParam.getVarName(), currentTypeStruct);
			
			report_info("Deklarisan parametar " + variableParam.getVarName(), variableParam);
	    }
	    
	    public void visit(ArrayParam arrayParam) {
	    	currentMethode.setLevel(currentMethode.getLevel() + 1);
	    	Obj obj = Tab.find(arrayParam.getArrayName());
			if ( !obj.equals( Tab.noObj) && currentLevel == obj.getLevel()) {
				report_error("Greska: Dupla deklaracija " + arrayParam.getArrayName() , arrayParam);
				return;
	    	}
			
			Struct variableType = new Struct(Struct.Array, currentTypeStruct);
			Obj varNode = Tab.insert(Obj.Var, arrayParam.getArrayName(), variableType);
			
			report_info("Deklarisan parametar " + arrayParam.getArrayName(), arrayParam);
	    }
	    
	    public void visit(NumConst cnst){
	    	cnst.struct = Tab.intType;
	    }
	    
	    public void visit(CharConst chr){
	    	chr.struct = Tab.charType;
	    }
	    
	    public void visit(BoolConst bl) {
	    	bl.struct = boolType;
	    }
	    
	    public void visit(ParenFactor parenFactor) {
	    	parenFactor.struct = parenFactor.getExpr().struct;
	    }
	    
	    public void visit(ArrayAlocation arrayAlocation) {
	    	if ( arrayAlocation.getExpr().struct.getKind() != Struct.Int) {
	    		report_error("Greska: ArrayAlocation: Redni broj elementa niza mora biti int" , arrayAlocation);
	    	}
	    	
	    	
	    	if( arrayAlocation.getType().getTypeName().equals("int")) {
		    	arrayAlocation.struct = new Struct(Struct.Array, Tab.intType);
	    	} else if (arrayAlocation.getType().getTypeName().equals("char")) {
		    	arrayAlocation.struct = new Struct(Struct.Array, Tab.charType);
	    	} else {
		    	arrayAlocation.struct = new Struct(Struct.Array, boolType);
	    	}
	    }
	    
	    
	    public void visit( DesignatorType designatorType) {
	    	Obj obj = Tab.find(designatorType.getName());
	    	if(obj == Tab.noObj) {
	    		report_error("Greska: designatorType: Designator " +  designatorType.getName() + " nije deklarisan ", designatorType);
	    	}
	    	designatorType.obj = obj;
	    	designatorType.obj.setFpPos(0);//procitan element je direknto taj, nije element niza
	    }
	    
	    public void visit(ArrayName arrayName) {
	    	arrayName.obj = Tab.find(arrayName.getI());
	    	if(arrayName.obj == Tab.noObj) {
	    		report_error("Greska: DesignatorArray: Niz " +  arrayName.getI() + " nije deklarisan ", arrayName);
	    	}
	    }
	    
	    
	    public void visit( DesignatorArray designatorArray) {
	    	Obj obj = (designatorArray.getArrName().obj);
	    	
	    	if(designatorArray.getExpr().struct.getKind() != Struct.Int) {
	    		report_error("Greska: DesignatorArray: Redni broj elementa niza mora biti int ", designatorArray);
	    	}
	    	designatorArray.obj = obj;
	    	designatorArray.obj.setFpPos(1);//procitan elementt je element procitanog niza
	    }
	    
	    public void visit(Designator designator) {
	    	designator.obj=designator.getSingleDesignator().obj;
	    }
	    
	    public void visit(DesignatorFactor designatorFactor) {
	    	if( designatorFactor.getDesignator().obj.getType().getKind() == Struct.Array && designatorFactor.getDesignator().obj.getFpPos()==1) {
	    		designatorFactor.struct =  designatorFactor.getDesignator().obj.getType().getElemType(); //dodeljuje tip elementa niza
	    	} else {
	    		designatorFactor.struct =  designatorFactor.getDesignator().obj.getType();
	    	}
	    }
	    
		public void visit(SingleFactor singleFactor) {
			singleFactor.struct = singleFactor.getFactor().struct;
		}
		
		public void visit(MulOpFactorList mulOpFactorList) {
			if( mulOpFactorList.getFactorList().struct.getKind() != Struct.Int 
					||  mulOpFactorList.getFactor().struct.getKind()!= Struct.Int ) {
				report_error("Greska: MulOpFactorList: Nedozvoljen tip ", mulOpFactorList);
			}
			mulOpFactorList.struct = mulOpFactorList.getFactorList().struct;
		}
		
		public void visit(TermFactorList termFactorList) {
			termFactorList.struct = termFactorList.getFactorList().struct;
		}
		
		public void visit(PositiveSingleTerm termExpr) {
			termExpr.struct = termExpr.getTerm().struct;
		}
		
		public void visit(MinusTermExpr minusTermExpr) {
			minusTermExpr.struct = minusTermExpr.getTerm().struct;
			if (minusTermExpr.struct.getKind() != Struct.Int) {
				report_error("Greska: MinusTermExpr: Nedozvoljen tip. Term mora biti tipa int", minusTermExpr);
			}
		}

		public void visit(SingleTerm singleTerm) {
			singleTerm.struct = singleTerm.getSngTerm().struct;
		}
		
	    public void visit(AddExpr addExpr){
	    	Struct te = addExpr.getExpr().struct;
	    	Struct t = addExpr.getSngTerm().struct;
	    	if(te.getKind()!=Struct.Int ||  t.getKind()!=Struct.Int ){
	    		report_error("Greska: AddExpr: Nedozvoljen tip ", addExpr);
	    	}
	    	addExpr.struct = te; //struktura izraza nasledjuje strukturu od prethodna dva izraza
	    }
		
	    public void visit(ReadStmt readStmt) {
	    	int isVar = readStmt.getDesignator().obj.getKind();
	    	Struct type = readStmt.getDesignator().obj.getType();
	    	
	    	if(isVar != 1) {//Ako nije promenljiva nije dozvoljeno citanje
				report_error("Greska: ReadStmt: Vrednost se ne moze dodeliti. Designator nije promenljiva", readStmt);
	    	} else if( type.getKind() == 1 || type.getKind() == 2 || type.getKind() == 5 
	    			|| (type.getKind() == 3 && readStmt.getDesignator().obj.getFpPos()==1 && ( type.getElemType().getKind() == 1 || type.getElemType().getKind() == 2 || type.getElemType().getKind() == 5 ))  ){
	    		report_info("Uspesno izvrseno citanje", readStmt);
	    	} else {
	    		report_error("Greska: ReadStmt: Vrednost se ne moze dodeliti. Designator more biti tipa int, char ili bool", readStmt);
	    	}
	    }
		
	    public void visit(PrintStmt print) {
	    	Struct type = print.getExpr().struct;
	    	if( type.getKind() == 1 || type.getKind() == 2 || type.getKind() == 5 ) {
	    			report_info("Print ", print);
	    	} else {
	    		report_error("Greska: PrintStmt: Vrednost se ne moze dodeliti. Designator more biti tipa int, char ili bool", print);
	    	}	
		}
	    
	    public void visit(PrintStmtNumConst print) {
	    	Struct type = print.getExpr().struct;
	    	if( type.getKind() == 1 || type.getKind() == 2 || type.getKind() == 5 ) {
	    			report_info("Print ", print);
	    	} else {
	    		report_error("Greska: PrintStmt: Vrednost se ne moze dodeliti. Designator more biti tipa int, char ili bool", print);
	    	}
		}
	    
	    public void visit(Assign assign) {
	    	currentTypeStruct = assign.getDesignator().obj.getType();
	    }
	    
	    public void visit(FindAnyStatement findAnyStatment) {
	    	if ( findAnyStatment.getAssign().getDesignator().obj.getType().getKind() != Struct.Bool) {
	    		report_error("Greska: FindAnyStatement: Vrednost se ne moze dodeliti. Vrednost mora biti dodeljena promenljivoj tipa bool", findAnyStatment);
	    		return;
	    	}
	    	
	    	if ( findAnyStatment.getDesignator().obj.getType().getKind() != Struct.Array) {
	    		report_error("Greska: FindAnyStatement: Vrednost se ne moze dodeliti. Pretrazivanje se mora vrsiti nad nizom", findAnyStatment);
	    		return;
	    	}
	    	
	    	Struct TypeOfArrElements = findAnyStatment.getDesignator().obj.getType().getElemType();
	    	Struct TypeOfValueForFinding = findAnyStatment.getExpr().struct;
	    	//System.out.println(TypeOfArrElements.getKind() + " " + TypeOfArrElements.getKind());
	    	if ( TypeOfArrElements.getKind() != TypeOfValueForFinding.getKind()) {
	    		report_error("Greska: FindAnyStatement: Vrednost se ne moze dodeliti. Element koji se pretrazuje more biti istog tipa kao elementi niza", findAnyStatment);
	    		return;   		
	    	}
	    	
	    	report_info("FindAny", findAnyStatment);
	    }
	    
	    public void visit(Assignment assignment){
	    	int isVar = assignment.getAssign().getDesignator().obj.getKind();
	    	if(isVar != 1) {
				report_error("Greska: Assignment: Vrednost se moze dodeliti iskljucivo promenljivoj." , assignment);
	    	}
	    	Struct te = assignment.getAssign().getDesignator().obj.getType();
	    	Struct e = assignment.getExpr().struct;
	    	if ((te.getKind() == e.getKind()) && ( te.getKind() != Struct.Array)) {
	    		report_info("Izvrsena dodela vrednost1" , assignment);
	    	} else if( (te.getKind() == e.getKind()) && ( te.getKind() == Struct.Array) && (te.getElemType().getKind() == e.getElemType().getKind()) ) { //niz1=niz2 || niz1 = new type[int]
	    		report_info("Izvrsena dodela vrednost2" , assignment);
	    	} else if ( te.getKind() == Struct.Array && te.getElemType().getKind() == e.getKind()) { //niz[i]=x
	    		report_info("Izvrsena dodela vrednost3" , assignment);
	    	} else if ( e.getKind() == Struct.Array && e.getElemType().getKind() == te.getKind()) { //niz[i]=x
	    		report_info("Izvrsena dodela vrednost4" , assignment);
	    	} else {
	    		report_error("Greska: Assignment: Nekompatibilni tipovi" , assignment);
	    	}
	    }
	    
	    public void visit(Increment increment) {
	    	if ( increment.getDesignator().obj.getType().getKind() == Struct.Int 
	    			||  (increment.getDesignator().obj.getType().getKind() == Struct.Array 
	    					&& increment.getDesignator().obj.getType().getElemType().getKind()==Struct.Int 
	    					&&  increment.getDesignator().obj.getFpPos()==1) ) {
	    		report_info("Izvrseno inkrementiranje", increment);
	    	} else {
	    		report_error("Greska: Increment:  Nedozvoljen tip. Tip mora biti int", increment);
	    	}
	    }
	    
	    public void visit(Decrement decrement) {
	    	if ( decrement.getDesignator().obj.getType().getKind() == Struct.Int 
	    			||  (decrement.getDesignator().obj.getType().getKind() == Struct.Array 
	    					&& decrement.getDesignator().obj.getType().getElemType().getKind()==Struct.Int 
	    					&&  decrement.getDesignator().obj.getFpPos()==1) ) {
	    		report_info("Izvrseno inkrementiranje", decrement);
	    	} else {
	    		report_error("Greska: Increment:  Nedozvoljen tip. Tip mora biti int", decrement);
	    	}
	    }
	    
	    public void visit(MethodDecl methodDecl) {
	    	Tab.chainLocalSymbols(currentMethode);
	    	Tab.closeScope(); 
	    	currentLevel--;
	    	currentMethode = null;
	    }
	    
	    public void visit(Program program) {
	    	nVars = Tab.currentScope.getnVars();
	    	Tab.chainLocalSymbols(program.getProgName().obj);
	    	Tab.closeScope();
	    	currentLevel--;
	    }
	    
	//neupotrebljeno
	public void visit(VarDecl vardecl){
		varDeclCount++;
	}
	
    
    
    
   
    
    
    
    
    

    
   
    
   
    

    
    public boolean passed(){
    	return !errorDetected;
    }

}
