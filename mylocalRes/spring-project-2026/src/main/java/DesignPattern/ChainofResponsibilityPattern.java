package DesignPattern;

public class ChainofResponsibilityPattern {

	public abstract class Handle {
		Handle sucessor;

		public void setSucessor(Handle successor) {
			this.sucessor = successor;
		}

		public abstract void HandleRequest(Trouble trouble);
	}

	public class Trouble {
		private String name;
		private int weight;

		public Trouble(String name, int weight) {
			this.name = name;
			this.weight = weight;
		}

		public String getName() {
			return this.name;
		}

		public int getWeight() {
			return this.weight;
		}
	}

	public class CEO extends Handle {
		public void HandleRequest(Trouble trouble) {
			System.out.println("CEO" + trouble.getName() + "是誰搞的！叫他過來。");
		}

		

	}
	public class Manager extends Handle {
		public Manager() {
			setSucessor(new CEO());
		}

		@Override
		public void HandleRequest(Trouble trouble) {
			// TODO Auto-generated method stub
			if (trouble.getWeight() > 1000) {
				sucessor.HandleRequest(trouble);
			} else {
				System.out.println("Manager:" + trouble.getName() + "是小問題，不用擔心。");
			}
		}

	}

	public void Call() {
		Handle Manager = new Manager();
		Trouble smallTrouble = new Trouble("想加薪500", 500);
		Manager.HandleRequest(smallTrouble);
		Trouble bigTrouble = new Trouble("搞壞五十萬的機器", 500000);
		Manager.HandleRequest(bigTrouble);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChainofResponsibilityPattern Ch=new ChainofResponsibilityPattern();
		Ch.Call();
	}

}
