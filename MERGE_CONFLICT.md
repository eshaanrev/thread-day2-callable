# Actual Day 2 merge conflict

While main was checked out, timer-branch was merged into it. Git reported a content conflict in src/Main.java. Both branches had replaced the same baseline print statement.

```java
<<<<<<< HEAD
        System.out.println("Main branch complete");
=======
        System.out.println("Timer branch complete");
>>>>>>> timer-branch
```

HEAD meant the checked-out main branch. The other side was timer-branch. The markers separated competing versions; they were removed during resolution.

Resolved statement:

```java
System.out.println("Project complete: counting and timer merged");
```

The timer was retained. Merge commit c5b1dcd has both parent histories. Both final projects were compiled with --release 19 and executed successfully on this Mac.
