package DesignPattern;

import java.util.HashMap;

public class FlyweightPattern {

	public static class Monitor {
		private final String monitor = "Retina Display";

		public String getMointor() {
			return monitor;
		}
	}

	public static class CustomSetting {
		public static CustomSetting Low = new CustomSetting("LOW");
		public static CustomSetting Hight = new CustomSetting("HIGHT");
		private String cpu;
		private int memory;
		private int storage;

		public CustomSetting() {
		};

		private CustomSetting(String set) {
			switch (set) {
			case "LOW":
				cpu = "i5";
				memory = 8;
				storage = 256;
				break;
			case "HIGHT":
				cpu = "i7";
				memory = 16;
				storage = 512;
				break;
			default:
				break;
			}
		}

		public String getCpu() {
			return cpu;
		}

		public void setCpu(String cpu) {
			this.cpu = cpu;
		}

		public int getMemory() {
			return memory;
		}

		public void setMemory(int memory) {
			this.memory = memory;
		}

		public int getStorage() {
			return storage;
		}

		public void setStorage(int storage) {
			this.storage = storage;
		}

		public String toString() {
			return "CustomSetting = {" + "\n" + "cpu=" + this.getCpu() + "\n" + "memory=" + this.getMemory() + "\n"
					+ "storage=" + this.getStorage() + "\n" + "}";
		}
	}

	public static class Macbook extends Monitor {

		enum Spec {
			LOW(CustomSetting.Low), HIGHT(CustomSetting.Hight);

			CustomSetting customSetting;

			Spec(CustomSetting customSetting) {
				this.customSetting = customSetting;
			}

			public CustomSetting getCustomSetting() {
				return customSetting;
			}

		}

		private CustomSetting customSetting;

		public Macbook(Spec spec) {
			this.customSetting = spec.customSetting;
		}

		public Macbook(CustomSetting customSetting) {
			this.customSetting = customSetting;
		}

		public String toString() {
			return "CustomSetting={" + "\n" + "cpu=" + customSetting.getCpu() + "\n" + "memory="
					+ customSetting.getMemory() + "\n" + "storage=" + customSetting.getStorage() + "\n" + "monitor="
					+ getMointor() + "\n" + "}";
		}
	}
  public class MacbookFactory{
		private HashMap<CustomSetting, Macbook> cache = new HashMap();

		public Macbook getCustomMacbook(CustomSetting key) {
			if (cache.containsKey(key))

			{
				return cache.get(key);
			}
			Macbook macbook = new Macbook(key);
			cache.put(key, macbook);
			return macbook;

		}

		public Macbook getMacbook(Macbook.Spec spec) {
			if (cache.containsKey(spec.getCustomSetting())) {
				return cache.get(spec.getCustomSetting());
			}
			Macbook macbook = new Macbook(spec);
			return macbook;
		}
		

	}
  
	public void Call() {
		MacbookFactory factory = new MacbookFactory();

        Macbook goodbook = factory.getMacbook(Macbook.Spec.HIGHT);

        System.out.println(goodbook.toString());

        CustomSetting superSetting = new CustomSetting();

        superSetting.setCpu("i9");
        superSetting.setMemory(32);
        superSetting.setStorage(1024);

        Macbook superMacbook = factory.getCustomMacbook(superSetting);

        System.out.println(superMacbook.toString());

    }

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FlyweightPattern Fl=new FlyweightPattern();
		Fl.Call();

	}

}
