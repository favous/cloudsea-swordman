package threadlocal;

public class Start2 {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		
		final Content content = new Content();
		final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
		
		for (int i = 0; i < 30; i++) {
			final int j = i;
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					int m = j;
					threadLocal.set(m);
					content.threadLocal.set(m);
					System.out.println("m = " + m);
					
					try {
						Thread.currentThread().sleep(Math.round(Math.random()*3000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int n= threadLocal.get();
					int n2= content.threadLocal.get();
					System.out.println("n = " + n);
					System.out.println("n2 = " + n2);
					boolean eq = m == n ? true : false;
					boolean eq2 = m == n2 ? true : false;
					System.out.println(eq);
					System.out.println(eq2);
				}
				
			};
			Thread t = new Thread(runnable);
			t.start();
		}
		
		Object Object = new Object();
		Object Object2 = new Object();
		System.out.println(Object.toString());
		System.out.println(Object.toString());
		System.out.println(Object2.toString());
		System.out.println(Object2.toString());
	}


}
