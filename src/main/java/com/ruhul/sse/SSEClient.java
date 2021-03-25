package com.ruhul.sse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SSEClient {
	private static final int totalTimeRunTime = 1; // minutes
	private static final int concurrentUser = 100;
	private static final int totalUser = 200;
	private static final int pauseBetweenUsers = 10;

	public static void main(String[] args) throws InterruptedException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(concurrentUser, 200, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
		for (int i = 0; i < totalUser; i++) {
			int x = i;
			executor.execute(() -> openConnection(x));
			Thread.sleep(pauseBetweenUsers);
		}
		executor.awaitTermination(10, TimeUnit.SECONDS);
		executor.shutdown();
	}

	public static void openConnection(int i) {
		String url = "http://localhost:8080/public/ping/subscribe";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		long l = System.currentTimeMillis();
		long stopTime = l + 60 * 1000 * totalTimeRunTime;
		try (SseEventSource source = SseEventSource.target(target).build()) {
			System.out.println("Opening connection" + i);
			int kk = i;
			source.register((inboundSseEvent) -> {
				String data = inboundSseEvent.readData();
				System.out.println("###### data -> " + kk + " " + data);
//				System.out.println("###### data -> " + data);
			});
			source.open();
			while (System.currentTimeMillis() < stopTime) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("exiting client : " + kk);
		}
	}
}
