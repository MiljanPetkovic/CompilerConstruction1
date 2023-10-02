// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class ArrayParam extends FormalParamDecl {

    private Type Type;
    private String ArrayName;

    public ArrayParam (Type Type, String ArrayName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ArrayName=ArrayName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getArrayName() {
        return ArrayName;
    }

    public void setArrayName(String ArrayName) {
        this.ArrayName=ArrayName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayParam(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+ArrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayParam]");
        return buffer.toString();
    }
}
