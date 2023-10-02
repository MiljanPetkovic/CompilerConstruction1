// generated with ast extension for cup
// version 0.8
// 24/7/2023 21:28:15


package rs.ac.bg.etf.pp1.ast;

public class SingleTerm extends Expr {

    private SngTerm SngTerm;

    public SingleTerm (SngTerm SngTerm) {
        this.SngTerm=SngTerm;
        if(SngTerm!=null) SngTerm.setParent(this);
    }

    public SngTerm getSngTerm() {
        return SngTerm;
    }

    public void setSngTerm(SngTerm SngTerm) {
        this.SngTerm=SngTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SngTerm!=null) SngTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SngTerm!=null) SngTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SngTerm!=null) SngTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleTerm(\n");

        if(SngTerm!=null)
            buffer.append(SngTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleTerm]");
        return buffer.toString();
    }
}
