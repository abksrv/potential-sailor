package executors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		Runnable helloWorldSleepTask = helloWorldSleepTask();
		Callable<Integer> helloWorldSleepCallableTask = helloWorldSleepCallableTask();
		newSingleThreadExecutor.submit(helloWorldSleepTask);
		Future<Integer> future = newSingleThreadExecutor.submit(helloWorldSleepCallableTask);
		newSingleThreadExecutor.shutdown(); // stops executor from accepting new tasks -- any task submitted after this
											// are rejected and a RejectedExecutionException is thrown.
		newSingleThreadExecutor.shutdownNow(); // Interrupts executing tasks and
												// doesn't allow pending tasks to be
												// executed. Its important to note that the tasks must respond to
												// interrupts, else the currently executing task will finish if the
												// interrupt has been consumed.
		boolean termination = newSingleThreadExecutor.awaitTermination(1, TimeUnit.SECONDS);// current thread waits
																							// until executor finishes
																							// or tasks are interrupted
																							// or timeout happens, true
																							// in case of
																							// termination/interruption;
																							// false if timed out.
		System.out.println(termination);
		System.out.println("Before i die");
		if (future.isDone())
			System.out.println(future.get());// throws a TimeoutException if result times out and a timeout is
												// specified. If
												// shutdownNow() was invoked and the task was not even started
												// future.get() w/o arguments waits forever.
		System.out.println(newSingleThreadExecutor.isTerminated());

		// -----------------Invoke All -------------------//
		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(() -> "task1", () -> "task2", () -> "task3");

		executor.invokeAll(callables, 10, TimeUnit.SECONDS) // invokes all of them. the returned future collection is in
															// order, each of them is completed. If it did time out,
															// some of these tasks will not have completed. Contrasts
															// with invokeAny which returns a future representing one of
															// the tasks which completed first, if any, and cancels
															// other tasks.
				.stream().map(f -> {
					try {
						return f.get();
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				}).forEach(System.out::println);

	}

	private static Runnable helloWorldSleepTask() {
		Runnable helloWorldSleepTask = () -> {
			System.out.println("Hello there.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		return helloWorldSleepTask;
	}

	private static Callable<Integer> helloWorldSleepCallableTask() {
		Callable<Integer> helloWorldSleepTask = () -> {
			System.out.println("Hello callable.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace(); // Supports interrupt by throwing execution. Interrupts can be consumed also
										// without responding. Long processes should periodically invoke
										// Thread.currentThread().isInterrupted() to check of interrupted, and respond
										// properly.
			}
			return 123;
		};
		return helloWorldSleepTask;
	}
}
