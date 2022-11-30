package DesignPattern;

import java.io.ObjectStreamException;

public class Singleton {

	public static class SingleObject {
		private static SingleObject instance = new SingleObject();

		private SingleObject() {
		};

		public static SingleObject getInstance() {
			return instance;
		}
	}

	// 懶散模式(Lazy)
	public static class Singletons {
		private static Singletons instance;

		private Singletons() {
		}

		public static synchronized Singletons getInstance() {
			if (instance == null) {
				instance = new Singletons();
			}
			return instance;
		}
	}

	public static class Singletonss {
		public static Singletonss instance;

		private Singletonss() {
		}

		public static Singletonss getInstance() {
			if (instance == null) {
				synchronized (Singletonss.class) {
					if (instance == null) {
						instance = new Singletonss();
					}
				}
			}
			return instance;
		}
	}

	private static class StaticInnerClass {
		private StaticInnerClass() {
		}

		public static StaticInnerClass getInstance() {
			return StaticInnerClassHolder.instance;
		}

		private static class StaticInnerClassHolder {
			private static StaticInnerClass instance = new StaticInnerClass();
		}
	}

	public enum EnumSingleton {
		INSTANCE;

		public void doSomething() {
			System.out.println("do do !");
		}
	}

	// ---------------------------------------------

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
