// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class CharDeclaration extends SingleConstDecl {

    private String charName;
    private Character charValue;

    public CharDeclaration (String charName, Character charValue) {
        this.charName=charName;
        this.charValue=charValue;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName=charName;
    }

    public Character getCharValue() {
        return charValue;
    }

    public void setCharValue(Character charValue) {
        this.charValue=charValue;
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
        buffer.append("CharDeclaration(\n");

        buffer.append(" "+tab+charName);
        buffer.append("\n");

        buffer.append(" "+tab+charValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharDeclaration]");
        return buffer.toString();
    }
}
