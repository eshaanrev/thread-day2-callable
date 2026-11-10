# Day 2: Callable, ExecutorService, Future

Count one billion numbers using 1,000 tasks, each counting one million numbers. Reuse a fixed pool of worker threads instead of creating one thread for every task.

## Run on this Mac

```sh
javac --release 19 src/Main.java
java -cp src Main
```

Expected count: `1000000000`. Elapsed time varies. Java 21 is installed; compilation targets Java 19. Counting loops may be optimized by the JVM, so tiny single-run times do not prove a generally faster design. Day 1 sums values while this assignment explicitly requests a count; their times are not an equivalent-work benchmark.

## Required history

- Start a separate repository with a baseline main method ending in `System.out.println("Project complete");`.
- On `executor-branch`, commit the task submission, then result collection, then explanatory comments.
- Merge that branch into `main`.
- Create `timer-branch` from the updated main; add timing and commit.
- On timer-branch change the baseline final print to `Timer branch complete` and commit.
- On main change that exact baseline line to `Main branch complete` and commit.
- Merge timer-branch into main, encounter the real conflict, resolve it, and commit. Keep the timer plus one final print statement.
- Push main and both feature branches. The directions specify meaningful commits but no exact Day 2 count.

## Conceptual quiz guide

### Thread pool
A reusable group of worker threads. Tasks wait when all workers are busy. A task is work; a thread is the execution path that runs it. One worker can run many tasks in succession. The pool size in this example equals the processor count reported by Java; 1,000 tasks do not mean 1,000 concurrent threads.

### Callable versus Runnable
Runnable defines `void run()`: no direct result and no declared checked exception. Callable<V> defines `V call() throws Exception`: it returns a result and may throw checked exceptions. `Callable<Long>` returns a Long; Java unboxes it when adding it to a primitive long. Neither interface starts a thread by itself. Calling call() directly is ordinary synchronous execution.

### ExecutorService
An interface for submitting and managing tasks and shutting execution down. `Executors.newFixedThreadPool(workers)` creates the implementation used here. `submit(task)` schedules a Callable and returns its Future. Shutdown rejects new tasks but allows accepted tasks to finish; shutdown alone does not wait. A finally block ensures shutdown is requested even if result retrieval fails.

### Future
A handle for a task's pending result. `get()` waits if necessary and returns the result. `isDone()` checks completion without waiting, including failure or cancellation. `get()` can throw InterruptedException if the caller is interrupted, or ExecutionException if the task failed. This beginner example declares exceptions in main rather than implementing custom recovery.

### Why submit first, get later?
All tasks must be available to workers before the main thread waits. Submitting one task and immediately waiting for it before submitting the next would largely serialize the work. Calling get() later in submission order does not stop other submitted tasks from running concurrently.

### Day 1 versus Day 2
Day 1: manually create Thread objects, start them, join them, and read their stored results. Day 2: submit Callable tasks, then retrieve their returned results through Futures. join() waits for a thread; get() waits for a task and returns its result. Separate local counts and main-thread aggregation avoid a shared-counter race condition.

### Merge conflict
Git cannot automatically combine competing edits, here two replacements of the same print statement. While main is checked out, merge timer-branch into it. Review both versions, write the intended combined result, remove conflict markers, stage the file, and complete the merge commit. This does not require discarding either branch's useful changes.

### Git vocabulary
- Repository: project files plus tracked history.
- Working tree: files currently being edited.
- Stage: choose changes for the next commit.
- Commit: save a local snapshot with a message.
- Branch: a named line of development; checkout switches to it.
- Merge: incorporate a branch into the currently checked-out branch.
- Remote/origin: the linked server repository.
- Push: upload local commits/branches. Fetch downloads remote history; pull fetches and integrates it.
- Git is version control; GitHub hosts repositories; GitKraken is a graphical Git client.

IntelliJ was waived by the user; these files were prepared directly on macOS. Callable, Future, and ExecutorService predate Java 19; no virtual-thread or preview feature is required.
