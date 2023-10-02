// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDeclaration extends ConstDecl {

    private SingleConstDecl SingleConstDecl;

    public SingleConstDeclaration (SingleConstDecl SingleConstDecl) {
        this.SingleConstDecl=SingleConstDecl;
        if(SingleConstDecl!=null) SingleConstDecl.setParent(this);
    }

    public SingleConstDecl getSingleConstDecl() {
        return SingleConstDecl;
    }

    public void setSingleConstDecl(SingleConstDecl SingleConstDecl) {
        this.SingleConstDecl=SingleConstDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleConstDecl!=null) SingleConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleConstDecl!=null) SingleConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleConstDecl!=null) SingleConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDeclaration(\n");

        if(SingleConstDecl!=null)
            buffer.append(SingleConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDeclaration]");
        return buffer.toString();
    }
}
