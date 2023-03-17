package gui;

public class HitDetection {
	
	private static int hit[] = new int[2];
	private static boolean empty = true;

	public HitDetection() {
		hit = new int[2];
		empty = true;
	}

	public synchronized void setHit(int x, int y) {
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		empty = false;

		hit[0] = x;
		hit[1] = y;

		notifyAll();
	}

	public synchronized int[] getHit() {
		while (empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		empty = true;

		notifyAll();

		return hit;
	}
}
