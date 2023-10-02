// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class VariablesDecarationlList extends VarDeclList {

    private VarDeclList VarDeclList;
    private VariablesDeclaration VariablesDeclaration;

    public VariablesDecarationlList (VarDeclList VarDeclList, VariablesDeclaration VariablesDeclaration) {
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.VariablesDeclaration=VariablesDeclaration;
        if(VariablesDeclaration!=null) VariablesDeclaration.setParent(this);
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
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
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(VariablesDeclaration!=null) VariablesDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(VariablesDeclaration!=null) VariablesDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(VariablesDeclaration!=null) VariablesDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariablesDecarationlList(\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariablesDeclaration!=null)
            buffer.append(VariablesDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariablesDecarationlList]");
        return buffer.toString();
    }
}
