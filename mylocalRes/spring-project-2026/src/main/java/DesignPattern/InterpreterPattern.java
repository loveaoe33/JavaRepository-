package DesignPattern;

public class InterpreterPattern {
	public abstract class AbstractExpression {
		public abstract void interpret(BrowserContext context);
	}
	
	public class BrowserContext{
    private String text="Bro";
    public String browsweText;
    public String getText() {
    	return text;
    }
    public void setText(String text) {
    	this.text=text;
    }
}
	
	public class TerinalExpression extends  AbstractExpression{

		@Override
		public void interpret(BrowserContext context) {
			// TODO Auto-generated method stub
			 System.out.println("終端的解釋器");
			 System.out.println("終端的解釋器"+context.getText());
		}
		
	}
	
	public class NonterminalExpression extends AbstractExpression{

		@Override
		public void interpret(BrowserContext context) {
			// TODO Auto-generated method stub
			System.out.println("非終端的解釋器");
			 System.out.println("非終端的解釋器"+context.getText());

		}
		
	}
	
	
    public void Call() {
    	BrowserContext con=new BrowserContext ();
    	AbstractExpression noterminal=new NonterminalExpression();
    	AbstractExpression terminal=new TerinalExpression();
    	noterminal.interpret(con);
    	terminal.interpret(con);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterpreterPattern In=new InterpreterPattern();
		In.Call();
	}

}
