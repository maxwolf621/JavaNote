###### Tags : `JAVA`
# Thread Safe 

## Synchronized Collections
創建一個thread-safe的集合(collection)透過synchronization wrappers。

```java
Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
Thread thread1 = new Thread(() -> syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6)));
Thread thread2 = new Thread(() -> syncCollection.addAll(Arrays.asList(7, 8, 9, 10, 11, 12)));
thread1.start();
thread2.start();
```
- synchronized collections 利用intrinsic locking被lock在每一個method中.
  > intrinsic lock是一個與class中特定的Instance有關的Internal Instance  
- method在同一時間只可以被一個thread存取,假如其他threads試圖存取，就會被阻擋blocked，直到那個method被上一個thread 解鎖(unlocked)。

## Concurrent Collections
它是synchronized collections(`java.util.concurrent packag`)的替代品，同樣可以做到thread-safe的集合。
```java
Map<String,String> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put("1", "one");
concurrentMap.put("2", "two");
concurrentMap.put("3", "three");
```
- concurrent collections跟synchronized collections不同的地方是**concurrent collections會將數據分成不同segments以做到thread-safety的功能**
- **在ConcurrentHashMap，每一個thread可以鎖住一個map的segment(一個map由不同segments組成)。所以一個map可以同時被多個threads存取**
- ConcurrentHashMap : Thread safe without having to `synchronize` the whole map Very fast reads while write is done with a lock No locking at the object level Uses multitude of locks.
- SynchronizedHashMap : **Object level synchronization Both read and writes acquire a lock Locking the collection** has a performance drawback May cause contention
  - Vector
  - HashTable
  - CopyOnWriteArrayList
  - CopyOnWriteArraySet
  - Stack

## Atomic Objects
Java可以提供AtomicInteger, AtomicLong, AtomicBoolean, 和AtomicReference等Atomic Objects來完成Thread Safe

Atomic classes最大的優勢是可容許利用上述Atomic objects的操作以做到thread-safe，同時沒有用到任何高成本的synchronization  

例如在沒有Atomic的情況發生race condition
```java
/**
  * <p> 如果發生race condition </p>
  * <p> 例如兩個threads同時競爭存取{@code incrementCounter} </p>
  * <p> 因為{@code incrementCounter}不是atomic </p>
  * <p> 導致最counter的值會是2，</p>
  */
public class Counter {
    private int counter = 0;
     
    public void incrementCounter() {
        counter += 1;
    }
     
    public int getCounter() {
        return counter;
    }
}

/**
  *<p> with atomic object <p> 
  *<p> call {@code incrementAndGet}時會先加1再存取加1之後的值 </p>
  */
public class AtomicCounter {

    private final AtomicInteger counter = new AtomicInteger();
     
    public void incrementCounter() {
        /**
          * <p> call atomic object method </p>
          * <p> {@code incrementAndGet} to avoid race condition </p> 
          */
        counter.incrementAndGet();
    }
     會
    public int getCounter() {
        return counter.get();
    }
}
```

## Synchronized Methods
synchronized method use an **intrinsic locks** or **monitor locks** to lock a certain thread  

The Lock allows only one Thread to access synchronized method and block other treads untill the lock thread gets it job down or throws exception  
```java
public synchronized void incrementCounter() {
    counter += 1;
}
```
- 在Multiple threads的環境中，**monitor locks只是一個監視角色作為監測不包括的存取情況**    
- When the tread finish the job, it will release intrinsic locks allowing other threads to fetch the intrinsic locks to access the `synchronized` method

## Synchronized Statements
當我們只是對於一個segment來進行thread-safe如果是使用synchronized method則會造成很多不必要的cost, 可以使用Synchronized Statements對某一特定segement實行synchronize  
  
```java
public void incrementCounter() {
    /**
      * <p> 只針對一些需要用到synchronization部分
      *     放到synchronized block內 </p> 
      */
    synchronized(this) {
        counter += 1; 
    }
}
```
- 與synchronized methods不同，synchronized statements必須具體指出物件  

## Volatile Fields
在一般class中, Class變量的值會被儲存在CPU, 不過由於這個情況會對變量的可見性受到影響，有可能會出現不被其他threads見到的情況  

Volatile主要提供 
1. Visibility guarantee for data 
2. The value of the variable with `volatile` keyword will immediately flush to memory as long as it is modified  

For example
```java 
public class Counter {
    /**
      * <p> store attirbute {@code counter}
      *     directly in the memory
      * </p> 
      */
    private volatile int counter;     
}
``` 
- 透過volatile變數counter值會被儲存在主記憶體內, 所以當執行這個Program時，它會直接去Memory查找而不是在CPU的cache內  
  > 每一次JVM在寫入counter的值時都會直接寫入Memory內
- Via `volatile` 可以讓other threads都能夠在memory內見到所有變量的值(Visility of data)

```java
public class User {
 
    private String name;
    private volatile int age;
 
    // standard constructors / getters
     
}
```
- 每次JVM都會把變量age寫進主記憶體內，但同一時間也會將變量name寫進主記憶體


### EX1
```java
public class WorkerThread extends Thread {
    /**
      * <p> Make sure to get 
      *     latest status of isRunning
      * </p>
      */
    private volatile boolean isRunning = true;
    
    @Override
    public void run() {
        while (isRunning) {
            // execute a task
        }
    }
    public void stopWorker() {
        isRunning = false;
    }
}
```


## The happens-before relationship  
The happens-before relationship is a very important aspect of the Java Memory Model.   
- **When an happens-before relationship is established between two distinct events it means that all changes made in the first event and also its current state will be seen and reflected in the second event.**

> E,g. When a thread writes into a volatile variable and another thread later accesses that same variable, an happens before relationship will be established between the two threads. **All changes made by the first thread will be visible by the second thread.**

**In fact all the other shared and locally cached variables will be propagated from the writing thread through the thread that later accessed the volatile variable, even if the other variables are not declared as volatile.**

For example  
```java
/**
  * 當Instance of MyWorker初始化的時候則JVM會
  * <li> 1: Allocate 記憶體的空間 <li>
  * <li> 2: 將分配到的記憶體的空間(記憶體的空間的地址)給予該Instance <li>
  * <li> 3: 將值寫入Instance </li>
  */
public class MyWorker {
  /**
  * 保證初始化對象時的次序(happens-before relationship)
  */
  private int workerNumber;

  public MyWorker (int workerNumber) {
    this.workerNumber = workerNumber;
  }

  public int getWorkerNumber () {
    return workerNumber;
  }

}
```

**Since the `workerNumber` variable is not `volatile` (and the access to the variable is also not `synchronized`) there is no _happens-before relationship_ between write and read events.** 
```java
MyWorker worker1 = MyWorker(1) // thread 1
MyWorker worker2 = Myworker(2) // thread 2
System.out.println(worker1.getWorerNumber());
```
- Thread 1 may locally cache the variable and never read it again from main memory so it's not aware of changes made by Thread 2.

If we declare the variable as `volatile` then any read that follows a write will see the changes.  
```java
public class MyWorker {
  /**
    * <p> With {@cdoe volatile}
    * 可以保證JVM只能在完成所有**寫入**動作後(完成第step 3)
    * 才能讀取`worker1`的值
    * </p>
    */
  private volatile int workerNumber;
   // ...
}
```









