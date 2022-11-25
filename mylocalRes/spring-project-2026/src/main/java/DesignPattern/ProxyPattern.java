package DesignPattern;

public class ProxyPattern {

	
	public interface IBuyHouse{
		public void findHouse();
		public void priceTooHight();
		public void defendPrice();
		public void finish();
	}
	
	public class LittleEngineer  implements IBuyHouse{

		@Override
		public void findHouse() {
			// TODO Auto-generated method stub
			System.out.println("民生社區機能特好！當然找那邊的房子！");
		}

		@Override
		public void priceTooHight() {
			// TODO Auto-generated method stub
			System.out.println("現在台灣薪水那麼低，屋主開那麼高賣不掉啦！");
		}

		@Override
		public void defendPrice() {
			// TODO Auto-generated method stub
			  System.out.println("未來人口越來越少，我們也是自住而已！打我五折吧！");
		}

		@Override
		public void finish() {
			// TODO Auto-generated method stub
			System.out.println("辛苦還房貸幾十年");
		}
		
		
	}
	
	public class EstaeAgent implements IBuyHouse{
        private IBuyHouse iBuyHouse;
        
        public EstaeAgent(IBuyHouse iBuyHouse) {
        	this.iBuyHouse=iBuyHouse;
        }
        
        
		@Override
		public void findHouse() {
			// TODO Auto-generated method stub
			iBuyHouse.findHouse();
			System.out.println("#房仲幫忙找房子");
			
		}

		@Override
		public void priceTooHight() {
			// TODO Auto-generated method stub
     
			iBuyHouse.priceTooHight();
			System.out.println("#房仲幫忙協商價格");
		}

		@Override
		public void defendPrice() {
			// TODO Auto-generated method stub
			iBuyHouse.defendPrice();
			System.out.println("#房仲幫忙砍價錢");
		}

		@Override
		public void finish() {
			// TODO Auto-generated method stub
			iBuyHouse.finish();
			System.out.println("#成交房仲會幫忙簽約");
		}
		
		
	}
	public void Call() {
		LittleEngineer Li=new LittleEngineer();
		EstaeAgent Es=new EstaeAgent(Li);
		Es.findHouse();
		Es.priceTooHight();
		Es.defendPrice();
		Es.finish();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProxyPattern Pr=new ProxyPattern();
		Pr.Call();
 
	}

}
