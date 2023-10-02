// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class NumDeclaration extends SingleConstDecl {

    private String numName;
    private Integer numValue;

    public NumDeclaration (String numName, Integer numValue) {
        this.numName=numName;
        this.numValue=numValue;
    }

    public String getNumName() {
        return numName;
    }

    public void setNumName(String numName) {
        this.numName=numName;
    }

    public Integer getNumValue() {
        return numValue;
    }

    public void setNumValue(Integer numValue) {
        this.numValue=numValue;
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
        buffer.append("NumDeclaration(\n");

        buffer.append(" "+tab+numName);
        buffer.append("\n");

        buffer.append(" "+tab+numValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumDeclaration]");
        return buffer.toString();
    }
}
