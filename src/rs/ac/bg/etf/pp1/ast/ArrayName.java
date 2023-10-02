// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class ArrayName extends ArrName {

    private String i;

    public ArrayName (String i) {
        this.i=i;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i=i;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayName(\n");

        buffer.append(" "+tab+i);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayName]");
        return buffer.toString();
    }
}
