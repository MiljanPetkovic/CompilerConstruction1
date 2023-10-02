// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class BoolDeclaration extends SingleConstDecl {

    private String boolName;
    private Integer boolValue;

    public BoolDeclaration (String boolName, Integer boolValue) {
        this.boolName=boolName;
        this.boolValue=boolValue;
    }

    public String getBoolName() {
        return boolName;
    }

    public void setBoolName(String boolName) {
        this.boolName=boolName;
    }

    public Integer getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Integer boolValue) {
        this.boolValue=boolValue;
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
        buffer.append("BoolDeclaration(\n");

        buffer.append(" "+tab+boolName);
        buffer.append("\n");

        buffer.append(" "+tab+boolValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolDeclaration]");
        return buffer.toString();
    }
}
