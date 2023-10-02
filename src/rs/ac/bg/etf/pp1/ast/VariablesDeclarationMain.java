// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class VariablesDeclarationMain extends Decl {

    private VariablesDeclaration VariablesDeclaration;

    public VariablesDeclarationMain (VariablesDeclaration VariablesDeclaration) {
        this.VariablesDeclaration=VariablesDeclaration;
        if(VariablesDeclaration!=null) VariablesDeclaration.setParent(this);
    }

    public VariablesDeclaration getVariablesDeclaration() {
        return VariablesDeclaration;
    }

    public void setVariablesDeclaration(VariablesDeclaration VariablesDeclaration) {
        this.VariablesDeclaration=VariablesDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariablesDeclaration!=null) VariablesDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariablesDeclaration!=null) VariablesDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariablesDeclaration!=null) VariablesDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariablesDeclarationMain(\n");

        if(VariablesDeclaration!=null)
            buffer.append(VariablesDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariablesDeclarationMain]");
        return buffer.toString();
    }
}
