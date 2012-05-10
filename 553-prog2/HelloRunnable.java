public class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
	for(int i = 1; i <= 30; i++){        
		(new Thread(new HelloRunnable())).start();
	}
    }

}
/*
public class HelloThread extends Thread {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new HelloThead).start();
    }

}
*/
