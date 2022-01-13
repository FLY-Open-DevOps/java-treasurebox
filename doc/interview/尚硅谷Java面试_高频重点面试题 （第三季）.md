## 尚硅谷Java面试_高频重点面试题 （第三季）

转载自[Java开发常见面试题详解（LockSupport，AQS，Spring循环依赖，Redis）_KISS-CSDN博客](https://blog.csdn.net/u011863024/article/details/115270840)

#### 00 前言闲聊和课程说明

暖身面试题

- Redis默认端口是多少？- 6379 [link](https://blog.csdn.net/u011863024/article/details/107476187#09_420)
- Spring官网地址 - https://spring.io
- 经典计算机图书看过吗？

#### 01 字符串常量Java内部加载-上

> Returns a canonical representation for the string object.
>
> A pool of strings, initially empty, is maintained privately by the class String.
>
> When the intern method is invoked, if the pool already contains a string equal to this String object as determined by the equals(Object) method, then the string from the pool is returned. Otherwise, this String object is added to the pool and a reference to this String object is returned.
>
> It follows that for any two strings s and t, s.intern() == t.intern() is true if and only if s.equals(t) is true.
>
> All literal strings and string-valued constant expressions are interned. String literals are defined in section 3.10.5 of the The Java™ Language Specification.
>
> ```java
> public native String intern();
> ```

由于运行时常量池是方法区的一部分，所以这两个区域的溢出测试可以放到一起进行。HotSpot从JDK 7开始逐步“去永久代”的计划，并在JDK 8中完全使用元空间来代替永久代的背景故事，在此我们就以测试代码来观察一下，使用"永久代"还是“元空间"来实现方法区，对程序有什么实际的影响。

String:intern()是一个本地方法，它的作用是如果字符串常量池中已经包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象的引用；否则，会将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用。在JDK 6或更早之前的HotSpot虚拟机中，常量池都是分配在永久代中，我们可以通过-XX:PermSize和-XX:MaxPermSize限制永久代的大小，即可间接限制其中常量池的容量。

```java
public class StringInternDemo {

	public static void main(String[] args) {
		
		String str1 = new StringBuilder("58").append("tongcheng").toString();
		System.out.println(str1);
		System.out.println(str1.intern());
		System.out.println(str1 == str1.intern());

		System.out.println();
		
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2);
		System.out.println(str2.intern());
		System.out.println(str2 == str2.intern());
		
	}

}
/*输出结果：

58tongcheng
58tongcheng
true

java
java
false
*/
```

#### 02 字符串常量Java内部加载-下

按照代码结果，Java字符串答案为false必然是两个不同的java，那另外一个java字符串如何加载进来的?

有一个初始化的Java字符串（JDK出娘胎自带的），在加载sun.misc.Version这个类的时候进入常量池。

递推步骤

System代码解析 System -> initializeSystemClass() -> Version

```java
package java.lang;

public final class System {

    /* register the natives via the static initializer.
     *
     * VM will invoke the initializeSystemClass method to complete
     * the initialization for this class separated from clinit.
     * Note that to use properties set by the VM, see the constraints
     * described in the initializeSystemClass method.
     */
    private static native void registerNatives();
    static {
        registerNatives();
    }
    
    //本地方法registerNatives()将会调用initializeSystemClass()
    private static void initializeSystemClass() {

		...
        
        sun.misc.Version.init();

		...
    }
    ...
}
```

```java
package sun.misc;

//反编译后的代码
public class Version {
	private static final String launcher_name = "java";
	...
}
```

- 类加载器和rt.jar - 根加载器提前部署加载rt.jar
- OpenJDK8源码
  - http://openjdk.java.net/
  - openjdk8\jdk\src\share\classes\sun\misc
- 考查点 - intern()方法，判断true/false？- 《深入理解java虚拟机》书原题是否读过经典JVM书籍

> 这段代码在JDK 6中运行，会得到两个false，而在JDK 7中运行，会得到一个true和一个false。产生差异的原因是，在JDK 6中，intern()方法会把首次遇到的字符串实例复制到永久代的字符串常量池中存储，返回的也是永久代里面这个字符串实例的引用，而由StringBuilder创建的字符串对象实例在Java堆上，所以必然不可能是同一个引用，结果将返回false。
>
> 而JDK 7(以及部分其他虚拟机，例如JRockit）的intern()方法实现就不需要再拷贝字符串的实例到永久代了，既然字符串常量池已经移到Java堆中，那只需要在常量池里记录一下首次出现的实例引用即可，因此intern()返回的引用和由StringBuilder创建的那个字符串实例就是同一个。而对str2比较返回false，这是因为“java”这个字符串在执行StringBuilder.toString()之前就已经出现过了，字符串常量池中已经有它的引用，不符合intern()方法要求“首次遇到"”的原则，“计算机软件"这个字符串则是首次出现的，因此结果返回true。

sun.misc.Version类会在JDK类库的初始化过程中被加载并初始化，而在初始化时它需要对静态常量字段根据指定的常量值(ConstantValue〉做默认初始化，此时被sun.misc.Version.launcher静态常量字段所引用的"java"字符串字面量就被intern到HotSpot VM的字符串常量池——StringTable里了。

#### 03 闲聊力扣算法第一题

给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

你可以按任意顺序返回答案。

示例 1：

```
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
123
```

示例 2：

```
输入：nums = [3,2,4], target = 6
输出：[1,2]
```

示例 3：

```
输入：nums = [3,3], target = 6
输出：[0,1]
12
```

提示：

- 2 <= nums.length <= 103
- -109 <= nums[i] <= 109
- -109 <= target <= 109
- **只会存在一个有效答案**

#### 04 TwoSum暴力解法

```java
public static int[] twoSum1(int[] nums,int target){
	for (int i = 0; i < nums.length; i++) {
		for (int j = i + 1; j < nums.length; j++) {
			if(target - nums[i] == nums[j])
				return new int[]{i,j};
	return null;
}
```

通过双重循环遍历数组中所有元素的两两组合。

#### 05 TwoSum优化解法

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
        int diff = target - nums[i];
        if (map.containsKey(diff)) {
            return new int[] { map.get(diff), i };
        }
        map.put(nums[i], i);
    }
    return new int[] { -1, -1 };
}
```

#### 06 闲聊AQS面试

略

#### 07 可重入锁理论

可重入锁又名递归锁，是**指在同一个线程在外层方法获取锁的时候，再进入该线程的的内层方法会自动获取锁（前提是锁对象得是同一个对象），不会因为之前已经获取过还没释放而阻塞。**

Java中ReentrantLock和synchronized都是可重入锁，可重入锁的一个优点是可一定程度避免死锁。

将字分开解释：

- 可：可以
- 重：再次
- 入：进入
- 锁：同步锁
- 进入什么？ - 进入同步域（即同步代码块/方法或显示锁锁定的代码）

一个线程中的多个流程可以获取同一把锁，持有这把同步锁可以再次进入。

自己可以获取自己的内部锁。

可重入锁的种类：

- 隐式锁（即synchronized关键字使用的锁）默认是可重入锁。
  - 同步块
  - 同步方法
- Synchronized的重入的实现机理。
- 显式锁（即Lock）也有ReentrantLock这样的可重入锁。

#### 08 可重入锁的代码验证-上

可重入锁的种类：

- 隐式锁（即synchronized关键字使用的锁）默认是可重入锁。
  - 同步块
  - 同步方法

```java
public class ReentrantLockDemo2 {
    Object object = new Object();

    public void sychronizedMethod(){
       new Thread(()->{
           synchronized (object){
               System.out.println(Thread.currentThread().getName()+"\t"+"外层....");
               synchronized (object){
                   System.out.println(Thread.currentThread().getName()+"\t"+"中层....");
                   synchronized (object){
                       System.out.println(Thread.currentThread().getName()+"\t"+"内层....");
                   }
               }
           }
       },"Thread A").start();
    }

    public static void main(String[] args) {
        new ReentrantLockDemo2().sychronizedMethod();
    }
    
}
/*
输出结果：

Thread A	外层....
Thread A	中层....
Thread A	内层....
*/
```

```java
public class ReentrantLockDemo2 {

    public static void main(String[] args) {
        new ReentrantLockDemo2().m1();
        
    }
    
    public synchronized void m1() {
    	System.out.println("===外");
    	m2();
    }
    
    public synchronized void m2() {
    	System.out.println("===中");
    	m3();
    }
    
    public synchronized void m3() {
    	System.out.println("===内");
    	
    }
}
/*
输出结果：

===外
===中
===内
*/
```

#### 09 可重入锁的代码验证-下

Synchronized的重入的实现机理
每个锁对象拥有一个锁计数器和一个指向持有该锁的线程的指针。

当执行monitorenter时，如果目标锁对象的计数器为零，那么说明它没有被其他线程所持有，Java虚拟机会将该锁对象的持有线程设置为当前线程，并且将其计数器加1。

在目标锁对象的计数器不为零的情况下，如果锁对象的持有线程是当前线程，那么Java虚拟机可以将其计数器加1，否则需要等待，直至持有线程释放该锁。

当执行monitorexit时，Java虚拟机则需将锁对象的计数器减1。计数器为零代表锁已被释放。

显式锁（即Lock）也有ReentrantLock这样的可重入锁

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone2 implements Runnable{

    Lock lock = new ReentrantLock();

    /**
     * set进去的时候，就加锁，调用set方法的时候，能否访问另外一个加锁的set方法
     */
    public void getLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get Lock");
            setLock();
        } finally {
            lock.unlock();
        }
    }

    public void setLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t set Lock");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        getLock();
    }
}

public class ReentrantLockDemo {


    public static void main(String[] args) {
        Phone2 phone = new Phone2();

        /**
         * 因为Phone实现了Runnable接口
         */
        Thread t3 = new Thread(phone, "t3");
        Thread t4 = new Thread(phone, "t4");
        t3.start();
        t4.start();
    }
}
/*
输出结果：

t3	 get Lock
t3	 set Lock
t4	 get Lock
t4	 set Lock
*/
```

#### 10 LockSupport是什么

[LockSupport Java doc](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/LockSupport.html)

LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。

LockSupport中的park()和 unpark()的作用分别是阻塞线程和解除阻塞线程。

总之，比wait/notify，await/signal更强。

3种让线程等待和唤醒的方法

1. 使用Object中的wait()方法让线程等待，使用object中的notify()方法唤醒线程
2. 使用JUC包中Condition的await()方法让线程等待，使用signal()方法唤醒线程
3. LockSupport类可以阻塞当前线程以及唤醒指定被阻塞的线程

#### 11 waitNotify限制

Object类中的wait和notify方法实现线程等待和唤醒

```java
public class WaitNotifyDemo {

	static Object lock = new Object();
	
	public static void main(String[] args) {
		new Thread(()->{
			synchronized (lock) {
				System.out.println(Thread.currentThread().getName()+" come in.");
				try {
					lock.wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName()+" 换醒.");
		}, "Thread A").start();
		
		new Thread(()->{
			synchronized (lock) {
				lock.notify();
				System.out.println(Thread.currentThread().getName()+" 通知.");
			}
		}, "Thread B").start();
	}
}
```

wait和notify方法必须要在同步块或者方法里面且成对出现使用，否则会抛出java.lang.IllegalMonitorStateException。

调用顺序要先wait后notify才OK。

#### 12 awaitSignal限制

Condition接口中的await后signal方法实现线程的等待和唤醒，与Object类中的wait和notify方法实现线程等待和唤醒类似。

```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionAwaitSignalDemo {
		
	public static void main(String[] args) {
		
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		
		new Thread(()->{
			
			try {
				System.out.println(Thread.currentThread().getName()+" come in.");
				lock.lock();
				condition.await();				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			
			System.out.println(Thread.currentThread().getName()+" 换醒.");
		},"Thread A").start();
		
		new Thread(()->{
			try {
				lock.lock();
				condition.signal();
				System.out.println(Thread.currentThread().getName()+" 通知.");
			}finally {
				lock.unlock();
			}
		},"Thread B").start();
	}
}
/*
输出结果：

Thread A come in.
Thread B 通知.
Thread A 换醒.
*/
```

await和signal方法必须要在同步块或者方法里面且成对出现使用，否则会抛出java.lang.IllegalMonitorStateException。

调用顺序要先await后signal才OK。

#### 13 LockSupport方法介绍

传统的synchronized和Lock实现等待唤醒通知的约束

- 线程先要获得并持有锁，必须在锁块(synchronized或lock)中
- 必须要先等待后唤醒，线程才能够被唤醒

LockSupport类中的park等待和unpark唤醒

> Basic thread blocking primitives for creating locks and other synchronization classes.
>
> This class associates, with each thread that uses it, a permit (in the sense of the Semaphore class). A call to park will return immediately if the permit is available, consuming it in the process; otherwise it may block. A call to unpark makes the permit available, if it was not already available. (Unlike with Semaphores though, permits do not accumulate. There is at most one.) 
>
> [link](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/LockSupport.html)

LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。

LockSupport类使用了一种名为Permit（许可）的概念来做到阻塞和唤醒线程的功能，每个线程都有一个许可（permit），permit只有两个值1和零，默认是零。

可以把许可看成是一种(0.1)信号量（Semaphore），但与Semaphore不同的是，许可的累加上限是1。

通过park()和unpark(thread)方法来实现阻塞和唤醒线程的操作

park()/park(Object blocker) - 阻塞当前线程阻塞传入的具体线程

```java
public class LockSupport {

    ...
    
    public static void park() {
        UNSAFE.park(false, 0L);
    }

    public static void park(Object blocker) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        UNSAFE.park(false, 0L);
        setBlocker(t, null);
    }
    
    ...
    
}
```

permit默认是0，所以一开始调用park()方法，当前线程就会阻塞，直到别的线程将当前线程的permit设置为1时，park方法会被唤醒，然后会将permit再次设置为0并返回。

unpark(Thread thread) - 唤醒处于阻塞状态的指定线程

```java
public class LockSupport {
 
    ...
    
    public static void unpark(Thread thread) {
        if (thread != null)
            UNSAFE.unpark(thread);
    }
    
    ...

}
```

调用unpark(thread)方法后，就会将thread线程的许可permit设置成1（注意多次调用unpark方法，不会累加，pemit值还是1）会自动唤醒thead线程，即之前阻塞中的LockSupport.park()方法会立即返回。

#### 14 LockSupport案例解析

```java
public class LockSupportDemo {

	public static void main(String[] args) {
		Thread a = new Thread(()->{
//			try {
//				TimeUnit.SECONDS.sleep(2);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			System.out.println(Thread.currentThread().getName() + " come in. " + System.currentTimeMillis());
			LockSupport.park();
			System.out.println(Thread.currentThread().getName() + " 换醒. " + System.currentTimeMillis());
		}, "Thread A");
		a.start();
		
		Thread b = new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LockSupport.unpark(a);
			System.out.println(Thread.currentThread().getName()+" 通知.");
		}, "Thread B");
		b.start();
	}
	
}
/*
输出结果：

Thread A come in.
Thread B 通知.
Thread A 换醒.
*/
```

正常 + 无锁块要求。

先前错误的先唤醒后等待顺序，LockSupport可无视这顺序。

重点说明

LockSupport是用来创建锁和共他同步类的基本线程阻塞原语。

LockSuport是一个线程阻塞工具类，所有的方法都是静态方法，可以让线程在任意位置阻塞，阻寨之后也有对应的唤醒方法。归根结底，LockSupport调用的Unsafe中的native代码。

LockSupport提供park()和unpark()方法实现阻塞线程和解除线程阻塞的过程

LockSupport和每个使用它的线程都有一个许可(permit)关联。permit相当于1，0的开关，默认是0，

调用一次unpark就加1变成1，

调用一次park会消费permit，也就是将1变成0，同时park立即返回。

如再次调用park会变成阻塞(因为permit为零了会阻塞在这里，一直到permit变为1)，这时调用unpark会把permit置为1。每个线程都有一个相关的permit, permit最多只有一个，重复调用unpark也不会积累凭证。

**形象的理解**

线程阻塞需要消耗凭证(permit)，这个凭证最多只有1个。

当调用park方法时

- 如果有凭证，则会直接消耗掉这个凭证然后正常退出。
- 如果无凭证，就必须阻塞等待凭证可用。

而unpark则相反，它会增加一个凭证，但凭证最多只能有1个，累加无放。

面试题

为什么可以先唤醒线程后阻塞线程？

因为unpark获得了一个凭证，之后再调用park方法，就可以名正言顺的凭证消费，故不会阻塞。

为什么唤醒两次后阻塞两次，但最终结果还会阻塞线程？

因为凭证的数量最多为1（不能累加），连续调用两次 unpark和调用一次 unpark效果一样，只会增加一个凭证；而调用两次park却需要消费两个凭证，证不够，不能放行。

#### 15 AQS理论初步

是什么？AbstractQueuedSynchronizer 抽象队列同步器。

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {
    
    ...
    
}
```

是用来构建锁或者其它同步器组件的重量级基础框架及整个JUC体系的基石，通过内置的FIFO队列来完成资源获取线程的排队工作，并通过一个int类型变量表示持有锁的状态。

![img](https://img-blog.csdnimg.cn/img_convert/47fad3563d427ffe5058343de85e3e05.png)

CLH：Craig、Landin and Hagersten队列，是一个单向链表，AQS中的队列是CLH变体的虚拟双向队列FIFO。

#### 16 AQS能干嘛

AQS为什么是JUC内容中最重要的基石？

和AQS有关的

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210705093505.png)

![img](https://img-blog.csdnimg.cn/img_convert/7ecbe7fbeecd5d5e20b2d8de59e8a033.png)

进一步理解锁和同步器的关系

- 锁，面向锁的**使用者** - 定义了程序员和锁交互的使用层APl，隐藏了实现细节，你调用即可
- 同步器，面向锁的**实现者** - 比如Java并发大神DougLee，提出统一规范并简化了锁的实现，屏蔽了同步状态管理、阻塞线程排队和通知、唤醒机制等。

能干嘛？

加锁会导致阻塞 - 有阻塞就需要排队，实现排队必然需要有某种形式的队列来进行管理

解释说明

抢到资源的线程直接使用处理业务逻辑，抢不到资源的必然涉及一种排队等候机制。抢占资源失败的线程继续去等待(类似银行业务办理窗口都满了，暂时没有受理窗口的顾客只能去候客区排队等候)，但等候线程仍然保留获取锁的可能且获取锁流程仍在继续(候客区的顾客也在等着叫号，轮到了再去受理窗口办理业务)。

既然说到了排队等候机制，那么就一定会有某种队列形成，这样的队列是什么数据结构呢?

如果共享资源被占用，就需要一定的阻塞等待唤醒机制来保证锁分配。这个机制主要用的是CLH队列的变体实现的，将暂时获取不到锁的线程加入到队列中，这个队列就是AQS的抽象表现。它将请求共享资源的线程封装成队列的结点(Node)，通过CAS、自旋以及LockSupportpark)的方式，维护state变量的状态，使并发达到同步的控制效果。

![img](https://img-blog.csdnimg.cn/img_convert/47fad3563d427ffe5058343de85e3e05.png)

#### 17 AQS源码体系-上

> Provides a framework for implementing blocking locks and related synchronizers (semaphores, events, etc) that rely on first-in-first-out (FIFO) wait queues. This class is designed to be a useful basis for most kinds of synchronizers that rely on a single atomic int value to represent state. Subclasses must define the protected methods that change this state, and which define what that state means in terms of this object being acquired or released. Given these, the other methods in this class carry out all queuing and blocking mechanics. Subclasses can maintain other state fields, but only the atomically updated int value manipulated using methods getState(), setState(int) and compareAndSetState(int, int) is tracked with respect to synchronization.
>
> [AbstractQueuedSynchronizer (Java Platform SE 8 )](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/AbstractQueuedSynchronizer.html)
>
> 提供一个框架来实现阻塞锁和依赖先进先出（FIFO）等待队列的相关同步器（信号量、事件等）。此类被设计为大多数类型的同步器的有用基础，这些同步器依赖于单个原子“int”值来表示状态。子类必须定义更改此状态的受保护方法，以及定义此状态在获取或释放此对象方面的含义。给定这些，这个类中的其他方法执行所有排队和阻塞机制。子类可以维护其他状态字段，但是只有使用方法getState（）、setState（int）和compareAndSetState（int，int）操作的原子更新的’int’值在同步方面被跟踪。

有阻塞就需要排队，实现排队必然需要队列

AQS使用一个volatile的int类型的成员变量来表示同步状态，通过内置的FIFO队列来完成资源获取的排队工作将每条要去抢占资源的线程封装成一个Node，节点来实现锁的分配，通过CAS完成对State值的修改。

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

    private static final long serialVersionUID = 7373984972572414691L;

     * Creates a new {@code AbstractQueuedSynchronizer} instance
    protected AbstractQueuedSynchronizer() { }

     * Wait queue node class.
    static final class Node {

     * Head of the wait queue, lazily initialized.  Except for
    private transient volatile Node head;

     * Tail of the wait queue, lazily initialized.  Modified only via
    private transient volatile Node tail;

     * The synchronization state.
    private volatile int state;

     * Returns the current value of synchronization state.
    protected final int getState() {

     * Sets the value of synchronization state.
    protected final void setState(int newState) {

     * Atomically sets synchronization state to the given updated
    protected final boolean compareAndSetState(int expect, int update) {
         
    ...
}         
```

#### 18 AQS源码体系-下

**AQS自身**

AQS的int变量 - AQS的同步状态state成员变量

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

    ...

     * The synchronization state.
    private volatile int state;
    
    ...
}
```

state成员变量相当于银行办理业务的受理窗口状态。

- 零就是没人，自由状态可以办理
- 大于等于1，有人占用窗口，等着去

AQS的CLH队列

- CLH队列(三个大牛的名字组成)，为一个双向队列
- 银行候客区的等待顾客

> The wait queue is a variant of a “CLH” (Craig, Landin, and Hagersten) lock queue. CLH locks are normally used forspinlocks. We instead use them for blocking synchronizers, butuse the same basic tactic of holding some of the controlinformation about a thread in the predecessor of its node. A"status" field in each node keeps track of whether a threadshould block. A node is signalled when its predecessorreleases. Each node of the queue otherwise serves as aspecific-notification-style monitor holding a single waiting thread. The status field does NOT control whether threads aregranted locks etc though. A thread may try to acquire if it isfirst in the queue. But being first does not guarantee success;it only gives the right to contend. So the currently releasedcontender thread may need to rewait.
>
> To enqueue into a CLH lock, you atomically splice it in as new tail. To dequeue, you just set the head field. 本段文字出自AbstractQueuedSynchronizer内部类Node源码注释
>
> 等待队列是“CLH”（Craig、Landin和Hagersten）锁队列的变体。CLH锁通常用于旋转锁。相反，我们使用它们来阻止同步器，但是使用相同的基本策略，即在其节点的前一个线程中保存一些关于该线程的控制信息。每个节点中的“status”字段跟踪线程是否应该阻塞。当一个节点的前一个节点释放时，它会发出信号。否则，队列的每个节点都充当一个特定的通知样式监视器，其中包含一个等待线程。状态字段并不控制线程是否被授予锁等。如果线程是队列中的第一个线程，它可能会尝试获取。但是，第一并不能保证成功，它只会给人争取的权利。因此，当前发布的内容线程可能需要重新等待。
>
> 要排队进入CLH锁，您可以将其作为新的尾部进行原子拼接。要出列，只需设置head字段。

**小总结**

- 有阻塞就需要排队，实现排队必然需要队列
- state变量+CLH变种的双端队列

AbstractQueuedSynchronizer内部类Node源码

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

    ...

     * Creates a new {@code AbstractQueuedSynchronizer} instance
    protected AbstractQueuedSynchronizer() { }

     * Wait queue node class.
    static final class Node {
        //表示线程以共享的模式等待锁
        /** Marker to indicate a node is waiting in shared mode */
        static final Node SHARED = new Node();
        
        //表示线程正在以独占的方式等待锁
        /** Marker to indicate a node is waiting in exclusive mode */
        static final Node EXCLUSIVE = null;

        //线程被取消了
        /** waitStatus value to indicate thread has cancelled */
        static final int CANCELLED =  1;

        //后继线程需要唤醒
        /** waitStatus value to indicate successor's thread needs unparking */
        static final int SIGNAL    = -1;
        
        //等待condition唤醒
        /** waitStatus value to indicate thread is waiting on condition */
        static final int CONDITION = -2;
        
        //共享式同步状态获取将会无条件地传播下去
        * waitStatus value to indicate the next acquireShared should     
        static final int PROPAGATE = -3;

        //当前节点在队列中的状态（重点）
        //说人话：
        //等候区其它顾客(其它线程)的等待状态
        //队列中每个排队的个体就是一个Node
        //初始为0，状态上面的几种
         * Status field, taking on only the values:
        volatile int waitStatus;

        //前驱节点（重点）
         * Link to predecessor node that current node/thread relies on
        volatile Node prev;

        //后继节点（重点）
         * Link to the successor node that the current node/thread
        volatile Node next;

        //表示处于该节点的线程
         * The thread that enqueued this node.  Initialized on
        volatile Thread thread;

        //指向下一个处于CONDITION状态的节点
         * Link to next node waiting on condition, or the special
        Node nextWaiter;

         * Returns true if node is waiting in shared mode.
        final boolean isShared() {

        //返回前驱节点，没有的话抛出npe
         * Returns previous node, or throws NullPointerException if null.
        final Node predecessor() throws NullPointerException {

        Node() {    // Used to establish initial head or SHARED marker

        Node(Thread thread, Node mode) {     // Used by addWaiter

        Node(Thread thread, int waitStatus) { // Used by Condition
    }
	...
}
```

AQS同步队列的基本结构

![img](https://img-blog.csdnimg.cn/img_convert/0efad5e335d52c8487af4e80680d251e.png)

#### 19 AQS源码深度解读01

从ReentrantLock开始解读AQS

Lock接口的实现类，基本都是通过**聚合**了一个**队列同步器**的子类完成线程访问控制的。

![img](https://img-blog.csdnimg.cn/img_convert/f991a259579532ca2528a66aa5c8ac60.png)

```java
 * A reentrant mutual exclusion {@link Lock} with the same basic
public class ReentrantLock implements Lock, java.io.Serializable {
    private static final long serialVersionUID = 7373984872572414699L;
    /** Synchronizer providing all implementation mechanics */
    private final Sync sync;

     * Base of synchronization control for this lock. Subclassed
    abstract static class Sync extends AbstractQueuedSynchronizer {

     * Sync object for non-fair locks
    static final class NonfairSync extends Sync {

     * Sync object for fair locks
    static final class FairSync extends Sync {

     * Creates an instance of {@code ReentrantLock}.
    public ReentrantLock() {
        sync = new NonfairSync();
    }

     * Creates an instance of {@code ReentrantLock} with the
    public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

     * Acquires the lock.
    public void lock() {
        sync.lock();//<------------------------注意，我们从这里入手
    }
        
    * Attempts to release this lock.
    public void unlock() {
        sync.release(1);
    }
    ...
}
```

从最简单的lock方法开始看看公平和非公平，先浏览下AbstractQueuedSynchronizer，FairSync，NonfairSync类的源码。

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

	...

     * Acquires in exclusive mode, ignoring interrupts.  Implemented
    public final void acquire(int arg) {//公平锁或非公平锁都会调用这方法
        if (!tryAcquire(arg) &&//0.
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))//1. 2.
            selfInterrupt();//3.
    }
    
    //0.
    * Attempts to acquire in exclusive mode. This method should query
    protected boolean tryAcquire(int arg) {//取决于公平锁或非公平锁的实现
        throw new UnsupportedOperationException();
    }
	
    
    //1.
    * Acquires in exclusive uninterruptible mode for thread already in
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
    
    //2.
    * Creates and enqueues node for current thread and given mode.
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }
    
    //3.
    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }
    
    //这个方法将会被公平锁的tryAcquire()调用
    * Queries whether any threads have been waiting to acquire longer
    public final boolean hasQueuedPredecessors() {
        // The correctness of this depends on head being initialized
        // before tail and on head.next being accurate if the current
        // thread is first in queue.
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        Node s;
        return h != t &&
            ((s = h.next) == null || s.thread != Thread.currentThread());
    }
    
    
	...         
}
```

```java
public class ReentrantLock implements Lock, java.io.Serializable {
    
    ...
    
    //非公平锁与公平锁的公共父类
     * Base of synchronization control for this lock. Subclassed
    abstract static class Sync extends AbstractQueuedSynchronizer {
    
    	...
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        } 
        ...
    
    }
        
    //非公平锁
	static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        /**
         * Performs lock.  Try immediate barge, backing up to normal
         * acquire on failure.23637w
         */
        final void lock() {//<---ReentrantLock初始化为非公平锁时，ReentrantLock.lock()将会调用这
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                acquire(1);//调用父类AbstractQueuedSynchronizer的acquire()
        }

        //acquire()将会间接调用该方法
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);//调用父类Sync的nonfairTryAcquire()
        }
    }
    
    * Sync object for fair locks
    static final class FairSync extends Sync {
        private static final long serialVersionUID = -3000897897090466540L;

        final void lock() {//<---ReentrantLock初始化为非公平锁时，ReentrantLock.lock()将会调用这
            acquire(1);调用父类AbstractQueuedSynchronizer的acquire()
        }

        //acquire()将会间接调用该方法
         * Fair version of tryAcquire.  Don't grant access unless
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&//<---公平锁与非公平锁的唯一区别，公平锁调用hasQueuedPredecessors()，而非公平锁没有调用
                    							//hasQueuedPredecessors()在父类AbstractQueuedSynchronizer定义
                    compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }
    
	...

}
```

可以明显看出公平锁与非公平锁的lock()方法唯一的区别就在于公平锁在获取同步状态时多了一个限制条件：hasQueuedPredecessors()

hasQueuedPredecessors是公平锁加锁时判断等待队列中是否存在有效节点的方法

对比公平锁和非公平锁的tyAcquire()方法的实现代码，其实差别就在于非公平锁获取锁时比公平锁中少了一个判断!hasQueuedPredecessors()

hasQueuedPredecessors()中判断了是否需要排队，导致公平锁和非公平锁的差异如下：

公平锁：公平锁讲究先来先到，线程在获取锁时，如果这个锁的等待队列中已经有线程在等待，那么当前线程就会进入等待队列中;

非公平锁：不管是否有等待队列，如果可以获取锁，则立刻占有锁对象。也就是说队列的第一个排队线程在unpark()，之后还是需要竞争锁（存在线程竞争的情况下)

![img](https://img-blog.csdnimg.cn/img_convert/15641ff20adcaf0e8d72b5dcec7d2da1.png)

**接下来讲述非公平锁的lock()**

整个ReentrantLock 的加锁过程，可以分为三个阶段：

1. 尝试加锁；
2. 加锁失败，线程入队列；
3. 线程入队列后，进入阻赛状态。

#### 20 AQS源码深度解读02

ReentrantLock的示例程序

带入一个银行办理业务的案例来模拟我们的AQS 如何进行线程的管理和通知唤醒机制，3个线程模拟3个来银行网点，受理窗口办理业务的顾客。

```java
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		
		//带入一个银行办理业务的案例来模拟我们的AQS 如何进行线程的管理和通知唤醒机制
		//3个线程模拟3个来银行网点，受理窗口办理业务的顾客

		//A顾客就是第一个顾客，此时受理窗口没有任何人，A可以直接去办理
		new Thread(()->{
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " come in.");
				
				try {
					TimeUnit.SECONDS.sleep(5);//模拟办理业务时间
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.unlock();
			}
		}, "Thread A").start();
		
		//第2个顾客，第2个线程---->，由于受理业务的窗口只有一个(只能一个线程持有锁)，此代B只能等待，
		//进入候客区
		new Thread(()->{
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " come in.");
				
			} finally {
				lock.unlock();
			}
		}, "Thread B").start();
		
		
		//第3个顾客，第3个线程---->，由于受理业务的窗口只有一个(只能一个线程持有锁)，此代C只能等待，
		//进入候客区
		new Thread(()->{
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " come in.");
				
			} finally {
				lock.unlock();
			}
		}, "Thread C").start();
	}
}
```

程序初始状态方便理解图

![img](https://img-blog.csdnimg.cn/img_convert/c43af638d95a7c6d45219b9da17fad64.png)

------

启动程序，首先是运行线程A，ReentrantLock默认是选用非公平锁。

```java
public class ReentrantLock implements Lock, java.io.Serializable {
    
    ...
        ...
        
    * Acquires the lock.
    public void lock() {
        sync.lock();//<------------------------注意，我们从这里入手,一开始将线程A的
    }
    
    abstract static class Sync extends AbstractQueuedSynchronizer {
        
        ...

        //被NonfairSync的tryAcquire()调用
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
        ...

    }
    
    
	//非公平锁
	static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        /**
         * Performs lock.  Try immediate barge, backing up to normal
         * acquire on failure.
         */
        final void lock() {//<----线程A的lock.lock()调用该方法
            if (compareAndSetState(0, 1))//AbstractQueuedSynchronizer的方法,刚开始这方法返回true
                setExclusiveOwnerThread(Thread.currentThread());//设置独占的所有者线程，显然一开始是线程A
            else
                acquire(1);//稍后紧接着的线程B将会调用该方法。
        }

        //acquire()将会间接调用该方法
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);//调用父类Sync的nonfairTryAcquire()
        }
        

        
    }
    
    ...
}
```

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

    /**
     * The synchronization state.
     */
    private volatile int state;

    //线程A将state设为1，下图红色椭圆区
    /*Atomically sets synchronization state to the given updated value 
    if the current state value equals the expected value.
    This operation has memory semantics of a volatile read and write.*/
    protected final boolean compareAndSetState(int expect, int update) {
        // See below for intrinsics setup to support this
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

}
```

线程A开始办业务了。

![img](https://img-blog.csdnimg.cn/img_convert/096f574353f3965eed5996e8a6962f94.png)

#### 21 AQS源码深度解读03

轮到线程B运行

```java
public class ReentrantLock implements Lock, java.io.Serializable {
    
    ...
        
    * Acquires the lock.
    public void lock() {
        sync.lock();//<------------------------注意，我们从这里入手,线程B的执行这
    }
    
	//非公平锁
	static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        /**
         * Performs lock.  Try immediate barge, backing up to normal
         * acquire on failure.
         */
        final void lock() {//<-------------------------线程B的lock.lock()调用该方法
            if (compareAndSetState(0, 1))//这是预定线程A还在工作，这里返回false
                setExclusiveOwnerThread(Thread.currentThread());//
            else
                acquire(1);//线程B将会调用该方法，该方法在AbstractQueuedSynchronizer，
            			   //它会调用本类的tryAcquire()方法
        }

        //acquire()将会间接调用该方法
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);//调用父类Sync的nonfairTryAcquire()
        }
    }

    //非公平锁与公平锁的公共父类
     * Base of synchronization control for this lock. Subclassed
    abstract static class Sync extends AbstractQueuedSynchronizer {
    
        //acquire()将会间接调用该方法
    	...
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();//这里是线程B
            int c = getState();//线程A还在工作，c=>1
            if (c == 0) {//false
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {//(线程B == 线程A) => false
                int nextc = c + acquires;//+1
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;//最终返回false
        } 
        ...
    
    }
    
    ...
}
```

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

	...

     * Acquires in exclusive mode, ignoring interrupts.  Implemented
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&//线程B调用非公平锁的tryAcquire(), 最终返回false，加上!,也就是true,也就是还要执行下面两行语句
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))//下一节论述
            selfInterrupt();
    }
    
    ...
}
```

另外

假设线程B，C还没启动，正在工作线程A重新尝试获得锁，也就是调用lock.lock()多一次

```java
 //非公平锁与公平锁的公共父类fa
     * Base of synchronization control for this lock. Subclassed
    abstract static class Sync extends AbstractQueuedSynchronizer {
    
    	...
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();//这里是线程A
            int c = getState();//线程A还在工作，c=>1；如果线程A恰好运行到在这工作完了，c=>0，这时它又要申请锁的话
            if (c == 0) {//线程A正在工作为false;如果线程A恰好工作完，c=>0，这时它又要申请锁的话,则为true
                if (compareAndSetState(0, acquires)) {//线程A重新获得锁
                    setExclusiveOwnerThread(current);//这里相当于NonfairSync.lock()另一重设置吧！
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {//(线程A == 线程A) => true
                int nextc = c + acquires;//1+1=>nextc=2
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);//state=2,说明要unlock多两次吧（现在盲猜）
                return true;//返回true
            }
            return false;
        } 
        ...
    
    }
```

#### 22 AQS源码深度解读04

继续上一节，

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

	...

     * Acquires in exclusive mode, ignoring interrupts.  Implemented
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&//线程B调用非公平锁的tryAcquire(), 最终返回false，加上!,也就是true,也就是还要执行下面两行语句
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))//线程B加入等待队列
            selfInterrupt();//下一节论述
    }
    
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {//根据上面一句注释，本语句块的意义是将新节点快速添加至队尾
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node; 
            }
        }
        enq(node);//快速添加至队尾失败，则用这方法调用（可能链表为空，才调用该方法）
        return node;
    }
    
    //Inserts node into queue, initializing if necessary.
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                if (compareAndSetHead(new Node()))//插入一个哨兵节点（或称傀儡节点）
                    tail = head;
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {//真正插入我们需要的节点，也就是包含线程B引用的节点
                    t.next = node;
                    return t;
                }
            }
        }
    }
    
    //CAS head field. Used only by enq.
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    //CAS tail field. Used only by enq.
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    
    ...
}
```

![img](https://img-blog.csdnimg.cn/img_convert/5b6e78a76dbef77aa015d3f2b5baf927.png)

线程B加入等待队列。

#### 23 AQS源码深度解读05

线程A依然工作，线程C如线程B那样炮制加入等待队列。

![img](https://img-blog.csdnimg.cn/img_convert/e1bf33ccbf81dacd7601677bda136f02.png)

双向链表中，第一个节点为虚节点(也叫哨兵节点)，其实并不存储任何信息，只是占位。真正的第一个有数据的节点，是从第二个节点开始的。

#### 24 AQS源码深度解读06

继续上一节

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

	...

     * Acquires in exclusive mode, ignoring interrupts.  Implemented
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&//线程B调用非公平锁的tryAcquire(), 最终返回false，加上!,也就是true,也就是还要执行下面两行语句
            //线程B加入等待队列，acquireQueued本节论述<--------------------------
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();//
    }
    
    //Acquires in exclusive uninterruptible mode for thread already inqueue. 
    //Used by condition wait methods as well as acquire.
    //
    //return true if interrupted while waiting
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();//1.返回前一节点，对与线程B来说，p也就是傀儡节点
				//p==head为true，tryAcquire()方法说明请转至 #21_AQS源码深度解读03
                //假设线程A正在工作,现在线程B只能等待，所以tryAcquire(arg)返回false，下面的if语块不执行
                //
                //第二次循环，假设线程A继续正在工作，下面的if语块还是不执行
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                //请移步到2.处的shouldParkAfterFailedAcquire()解说。第一次返回false, 下一次（第二次）循环
                //第二次循环，shouldParkAfterFailedAcquire()返回true，执行parkAndCheckInterrupt()
                if (shouldParkAfterFailedAcquire(p, node) && 
                    //4. 
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
    
    
    static final class Node {

        ...
        //1.返回前一节点
        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }
        
        ...

    }
    
    //2. 
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;//此时pred指向傀儡节点，它的waitStatus为0
        //Node.SIGNAL为-1，跳过
        //第二次调用，ws为-1，条件成立，返回true
        if (ws == Node.SIGNAL)//-1
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             */
            return true;
        if (ws > 0) {//跳过
            /*
             * Predecessor was cancelled. Skip over predecessors and
             * indicate retry.
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
             */
            //3. 傀儡节点的WaitStatus设置为-1//下图红圈
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;//第一次返回
    }
    
    /**
     * CAS waitStatus field of a node.
     */
    //3.
    private static final boolean compareAndSetWaitStatus(Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                                        expect, update);
    }
    
    /**
     * Convenience method to park and then check if interrupted
     *
     * @return {@code true} if interrupted
     */
    //4.
    private final boolean parkAndCheckInterrupt() {
        //前段章节讲述的LockSupport，this指的是NonfairSync对象，
        //这意味着真正阻塞线程B，同样地阻塞了线程C
        LockSupport.park(this);//线程B,C在此处暂停了运行<-------------------------
        return Thread.interrupted();
    }
    
}
```

![img](https://img-blog.csdnimg.cn/img_convert/741c9ead0810b5b6d9c48915e5355aaa.png)

图中的傀儡节点的waitStatus由0变为-1（Node.SIGNAL）。

#### 25 AQS源码深度解读07

接下来讨论ReentrantLock.unLock()方法。假设线程A工作结束，调用unLock()，释放锁占用。

```java
public class ReentrantLock implements Lock, java.io.Serializable {
    
    private final Sync sync;

    abstract static class Sync extends AbstractQueuedSynchronizer {
        
        ...
        //2.unlock()间接调用本方法，releases传入1
        protected final boolean tryRelease(int releases) {
            //3.
            int c = getState() - releases;//c为0
            //4.
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {//c为0，条件为ture，执行if语句块
                free = true;
                //5.
                setExclusiveOwnerThread(null);
            }
            //6.
            setState(c);
            return free;//最后返回true
        }
    	...
    
    }
    
    static final class NonfairSync extends Sync {...}
    
    public ReentrantLock() {
        sync = new NonfairSync();//我们使用的非公平锁
    }
    					//注意！注意！注意！
    public void unlock() {//<----------从这开始，假设线程A工作结束，调用unLock()，释放锁占用
        //1.
        sync.release(1);//在AbstractQueuedSynchronizer类定义
    }
    
    ...
 
}
```

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

    ...
    //1.
    public final boolean release(int arg) {
        //2.
        if (tryRelease(arg)) {//该方法看子类NonfairSync实现，最后返回true
            Node h = head;//返回傀儡节点
            if (h != null && h.waitStatus != 0)//傀儡节点非空，且状态为-1，条件为true，执行if语句
                //7.
                unparkSuccessor(h);
            return true;
        }
        return false;//返回true,false都无所谓了，unlock方法只是简单调用release方法，对返回结果没要求
    }
    
    /**
     * The synchronization state.
     */
    private volatile int state;

    //3.
    protected final int getState() {
        return state;
    }

    //6.
    protected final void setState(int newState) {
        state = newState;
    }
    
    //7. Wakes up node's successor, if one exists.
    //传入傀儡节点
    private void unparkSuccessor(Node node) {
        /*
         * If status is negative (i.e., possibly needing signal) try
         * to clear in anticipation of signalling.  It is OK if this
         * fails or if status is changed by waiting thread.
         */
        int ws = node.waitStatus;//傀儡节点waitStatus为-1
        if (ws < 0)//ws为-1，条件成立，执行if语块
            compareAndSetWaitStatus(node, ws, 0);//8.将傀儡节点waitStatus由-1变为0

        /*
         * Thread to unpark is held in successor, which is normally
         * just the next node.  But if cancelled or apparently null,
         * traverse backwards from tail to find the actual
         * non-cancelled successor.
         */
        Node s = node.next;//傀儡节点的下一节点,也就是带有线程B的节点
        if (s == null || s.waitStatus > 0) {//s非空，s.waitStatus非0，条件为false，不执行if语块
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)//s非空，条件为true，不执行if语块
            LockSupport.unpark(s.thread);//唤醒线程B。运行到这里，线程A的工作基本告一段落了。
    }
    
    //8.
    private static final boolean compareAndSetWaitStatus(Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                                        expect, update);
    }
    
    
}
```

```java
public abstract class AbstractOwnableSynchronizer
    implements java.io.Serializable {

    ...

    protected AbstractOwnableSynchronizer() { }

    private transient Thread exclusiveOwnerThread;

    //5.
    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }
    
    //4.
    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
}
```

线程A结束工作，调用unlock()的tryRelease()后的状态，state由1变为0，exclusiveOwnerThread由线程A变为null。

![img](https://img-blog.csdnimg.cn/img_convert/4834c2b2372914e35d9e6a40d8618b25.png)

------

线程B被唤醒，即从原先park()的方法继续运行

```java
public abstract class AbstractQueuedSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

     private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);//线程B从阻塞到非阻塞，继续执行
        return Thread.interrupted();//线程B没有被中断，返回false
    }
    
	...
 
    //Acquires in exclusive uninterruptible mode for thread already inqueue. 
    //Used by condition wait methods as well as acquire.
    //
    //return true if interrupted while waiting
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();//线程B所在的节点的前一节点是傀儡节点
                //傀儡节点是头节点，tryAcquire()的说明请移步至#21_AQS源码深度解读03
                //tryAcquire()返回true,线程B成功上位
                if (p == head && tryAcquire(arg)) {
                    setHead(node);//1.将附带线程B的节点的变成新的傀儡节点
                    p.next = null; // help GC//置空原傀儡指针与新的傀儡节点之间的前后驱指针，方便GC回收
                    failed = false;
                    return interrupted;//返回false，跳到2.acquire()
                }
               
                if (shouldParkAfterFailedAcquire(p, node) && 
                    //唤醒线程B继续工作，parkAndCheckInterrupt()返回false
                    //if语块不执行，跳到下一循环
                    parkAndCheckInterrupt())//<---------------------------------唤醒线程在这里继续运行
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
    
    //1. 
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }
    
    //2.
    * Acquires in exclusive mode, ignoring interrupts.  Implemented
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
            //acquireQueued()返回fasle,条件为false，if语块不执行，acquire()返回
            //也就是说，线程B成功获得锁，可以展开线程B自己的工作了。
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();//
    }
    
}
```

最后，线程B上位成功。

![img](https://img-blog.csdnimg.cn/img_convert/aa60bf0eb603162facee9e8503f59c9a.png)

#### 26 AQS小总结

略

#### 27 Aop的题目说明要求

Spring的AOP顺序

Spring的循环依赖

AOP常用注解：

@Before 前置通知：目标方法之前执行

@After 后置通知：目标方法之后执行（始终执行)

@AfterReturning 返回后通知：执行方法结束前执行(异常不执行)

@AfterThrowing 异常通知：出现异常时候执行

@Around 环绕通知：环绕目标方法执行

面试题

你肯定知道spring，那说说aop的全部通知顺序springboot或springboot2对aop的执行顺序影响？

说说你使用AOP中碰到的坑?

#### 28 spring4下的aop测试案例

新建Maven工程，pom.xml如下：

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-parent</artifactId>
	    <version>1.5.9.RELEASE</version>
	    <relativePath/> <!-- lookup parent from repository -->
	</parent>

	<groupId>com.lun</groupId>
	<artifactId>HelloSpringBoot</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>HelloSpringBoot</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
		<!-- <version>1.5.9.RELEASE</version〉 ch/qos/Logback/core/joran/spi/JoranException解决方案-->
		<dependency>
		    <groupId>ch.qos.logback</groupId>
		    <artifactId>logback-core</artifactId>
		    <version>1.1.3</version>
		</dependency>
		
		<dependency>
		    <groupId>ch.qos.logback</groupId>
		    <artifactId>logback-access</artifactId>
		    <version>1.1.3</version>
		</dependency>
		
		<dependency>
		    <groupId>ch.qos.logback</groupId>
		    <artifactId>logback-classic</artifactId>
		    <version>1.1.3</version>
		</dependency>
		
		<!-- web+actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		<!-- SpringBoot与Redis整合依赖 -->
		<!-- 
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		 -->
		
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-pool2</artifactId>
		</dependency>
		
		<!-- jedis -->
		<dependency>
			<groupId>redis.clients</groupId>
			<artifactId>jedis</artifactId>
			<version>3.1.0</version>
		</dependency>

		<!-- Spring Boot AOP技术-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
		
		<!-- redisson -->
		<dependency>
			<groupId>org.redisson</groupId>
			<artifactId>redisson</artifactId>
			<version>3.13.4</version>
		</dependency>

		<!-- 一般通用基础配置 -->
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-devtools</artifactId>
		    <scope>runtime</scope>
		    <optional>true</optional>
		</dependency>
		
		<dependency>
		    <groupId>org.projectlombok</groupId>
		    <artifactId>lombok</artifactId>
		    <optional>true</optional>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-test</artifactId><scope>test</scope>
		    <exclusions>
		        <exclusion>
		            <groupId>org.junit.vintage</groupId>
		            <artifactId>junit-vintage-engine</artifactId>
		        </exclusion>
		    </exclusions>
		</dependency>

	</dependencies>
	
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

启动类

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
```

接口CalcService

```java
public interface CalcService {	
	public int div(int x, int y);
}
```

接口实现类CalcServiceImpl新加@Service

```java
import org.springframework.stereotype.Service;

@Service
public class CalcServiceImpl implements CalcService {

	@Override
	public int div(int x, int y) {
		int result = x / y;
		System.out.println("===>CalcServiceImpl被调用，计算结果为：" + result);
		return result;
	}
}
```

新建一个切面类MyAspect并为切面类新增两个注解：

- @Aspect 指定一个类为切面类
- @Component 纳入Spring容器管理

```java
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    @Before("execution(public int com.lun.interview.service.CalcServiceImpl.*(..))")
    public void beforeNotify() {
        System.out.println("********@Before我是前置通知");
    }

    @After("execution(public int com.lun.interview.service.CalcServiceImpl.*(..))")
    public void afterNotify() {
        System.out.println("********@After我是后置通知");
    }

    @AfterReturning("execution(public int com.lun.interview.service.CalcServiceImpl.*(..))")
    public void afterReturningNotify() {
        System.out.println("********@AfterReturning我是返回后通知");
    }

    @AfterThrowing(" execution(public int com.lun.interview.service.CalcServiceImpl.*(..))")
    public void afterThrowingNotify() {
        System.out.println("********@AfterThrowing我是异常通知");
    }

    @Around(" execution(public int com.lun.interview.service.CalcServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object retvalue = null;
        System.out.println("我是环绕通知之前AAA");
        retvalue = proceedingJoinPoint.proceed();
        System.out.println("我是环绕通知之后BBB");
        return retvalue ;
    }
}
```

测试类

```java
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.context.junit4.SpringRunner;

import com.lun.interview.service.CalcService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AopTest {

	@Resource
	private CalcService calcService;
	
	@Test
	public void testAop4() {
		System.out.println(String.format("Spring Verision : %s, Sring Boot Version : %s.", //
				SpringVersion.getVersion(), SpringBootVersion.getVersion()));
		
		calcService.div(10, 2);
	}
}
```

#### 29 spring4下的aop测试结果

继续上节

输出结果：

```java
Spring Verision : 4.3.13.RELEASE, Sring Boot Version : 1.5.9.RELEASE.

我是环绕通知之前AAA
********@Before我是前置通知
===>CalcServiceImpl被调用，计算结果为：5
我是环绕通知之后BBB
********@After我是后置通知
********@AfterReturning我是返回后通知
```

修改测试类，让其抛出算术异常类：

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class AopTest {

	@Resource
	private CalcService calcService;
	
	@Test
	public void testAop4() {
		System.out.println(String.format("Spring Verision : %s, Sring Boot Version : %s.", //
				SpringVersion.getVersion(), SpringBootVersion.getVersion()));
		
		//calcService.div(10, 2);
        calcService.div(10, 0);//将会抛异常
	}
}
```

输出结果：

```java
Spring Verision : 4.3.13.RELEASE, Sring Boot Version : 1.5.9.RELEASE.

我是环绕通知之前AAA
********@Before我是前置通知
********@After我是后置通知
********@AfterThrowing我是异常通知

java.lang.ArithmeticException: / by zero
	at com.lun.interview.service.CalcServiceImpl.div(CalcServiceImpl.java:10)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	...
```

**小结**

AOP执行顺序：

- 正常情况下：@Before前置通知----->@After后置通知----->@AfterRunning正常返回
- 异常情况下：@Before前置通知----->@After后置通知----->@AfterThrowing方法异常

#### 30 spring5下的aop测试

修改POM

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-parent</artifactId>
	    <version>2.3.3.RELEASE</version>
	    <!-- 1.5.9.RELEASE -->
	    <relativePath/> <!-- lookup parent from repository -->
	</parent>

	<groupId>com.lun</groupId>
	<artifactId>HelloSpringBoot</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>HelloSpringBoot</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>
		<!-- web+actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		<!-- SpringBoot与Redis整合依赖 -->
		<!-- 
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		 -->
		
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-pool2</artifactId>
		</dependency>
		
		<!-- jedis -->
		<dependency>
			<groupId>redis.clients</groupId>
			<artifactId>jedis</artifactId>
			<version>3.1.0</version>
		</dependency>

		<!-- Spring Boot AOP技术-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
		
		<!-- redisson -->
		<dependency>
			<groupId>org.redisson</groupId>
			<artifactId>redisson</artifactId>
			<version>3.13.4</version>
		</dependency>

		<!-- 一般通用基础配置 -->
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-devtools</artifactId>
		    <scope>runtime</scope>
		    <optional>true</optional>
		</dependency>
		
		<dependency>
		    <groupId>org.projectlombok</groupId>
		    <artifactId>lombok</artifactId>
		    <optional>true</optional>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-test</artifactId><scope>test</scope>
		    <exclusions>
		        <exclusion>
		            <groupId>org.junit.vintage</groupId>
		            <artifactId>junit-vintage-engine</artifactId>
		        </exclusion>
		    </exclusions>
		</dependency>

	</dependencies>
	
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

修改测试类

```java
import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
//import org.springframework.test.context.junit4.SpringRunner;

import com.lun.interview.service.CalcService;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class AopTest {

	@Resource
	private CalcService calcService;
	
	@Test
	public void testAop5() {
		System.out.println(String.format("Spring Verision : %s, Sring Boot Version : %s.", //
				SpringVersion.getVersion(), SpringBootVersion.getVersion()));
		System.out.println();
		calcService.div(10, 2);
		//calcService.div(10, 0);
	}
	
}
```

输出结果

```shell
Spring Verision : 5.2.8.RELEASE, Sring Boot Version : 2.3.3.RELEASE.

我是环绕通知之前AAA
********@Before我是前置通知
===>CalcServiceImpl被调用，计算结果为：5
********@AfterReturning我是返回后通知
********@After我是后置通知
我是环绕通知之后BBB
```

修改测试类，让其抛出算术异常类：

```java
@SpringBootTest
public class AopTest {

	@Resource
	private CalcService calcService;

    ...
	
	@Test
	public void testAop5() {
		System.out.println(String.format("Spring Verision : %s, Sring Boot Version : %s.", //
				SpringVersion.getVersion(), SpringBootVersion.getVersion()));
		System.out.println();
		calcService.div(10, 2);
		//calcService.div(10, 0);
	}
```

输出结果

```java
Spring Verision : 5.2.8.RELEASE, Sring Boot Version : 2.3.3.RELEASE.

我是环绕通知之前AAA
********@Before我是前置通知
********@AfterThrowing我是异常通知
********@After我是后置通知

java.lang.ArithmeticException: / by zero
	at com.lun.interview.service.CalcServiceImpl.div(CalcServiceImpl.java:10)
	at com.lun.interview.service.CalcServiceImpl$$FastClassBySpringCGLIB$$355acbc4.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)
```

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210706093949.png)

#### 31 spring循环依赖题目说明

恶心的大厂面试题。

- 你解释下spring中的三级缓存？
- 三级缓存分别是什么？三个Map有什么异同？
- 什么是循环依赖？请你谈谈？看过spring源码吗？
- 如何检测是否存在循环依赖？实际开发中见过循环依赖的异常吗？
- 多例的情况下，循环依赖问题为什么无法解决？

什么是循环依赖？

多个bean之间相互依赖，形成了一个闭环。比如：A依赖于B、B依赖于C、C依赖于A。

通常来说，如果问Spring容器内部如何解决循环依赖，一定是指默认的单例Bean中，属性互相引用的场景。

![img](https://img-blog.csdnimg.cn/img_convert/cbc160e2abda182bc696ff47e3fe5ec5.png)

两种注入方式对循环依赖的影响

循环依赖官网说明

> Circular dependencies
>
> If you use predominantly constructor injection, it is possible to create an unresolvable circular dependency scenario.
>
> For example: Class A requires an instance of class B through constructor injection, and class B requires an instance of class A through constructor injection. If you configure beans for classes A and B to be injected into each other, the Spring IoC container detects this circular reference at runtime, and throws a BeanCurrentlyInCreationException.
>
> One possible solution is to edit the source code of some classes to be configured by setters rather than constructors. Alternatively, avoid constructor injection and use setter injection only. In other words, although it is not recommended, you can configure circular dependencies with setter injection.
>
> Unlike the typical case (with no circular dependencies), a circular dependency between bean A and bean B forces one of the beans to be injected into the other prior to being fully initialized itself (a classic chicken-and-egg scenario).
>
> [link](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-dependency-resolution)

结论

我们AB循环依赖问题只要A的注入方式是setter且singleton ，就不会有循环依赖问题。

#### 32 spring循环依赖纯java代码验证案例

Spring容器循环依赖报错演示BeanCurrentlylnCreationException

循环依赖现象在spring容器中注入依赖的对象，有2种情况

- 构造器方式注入依赖（不可行）
- 以set方式注入依赖（可行）

构造器方式注入依赖（不可行）

```java
@Component
public class ServiceB{
    private ServiceA serviceA;
    
    public ServiceB(ServiceA serviceA){
        this.serviceA = serviceA;
    }
}
```

```java
@Component
public class ServiceA{
    private ServiceB serviceB;
    
    public ServiceA(ServiceB serviceB){
        this.serviceB = serviceB;
    }
}
```

```java
public class ClientConstructor{
    public static void main(String[] args){
        new ServiceA(new ServiceB(new ServiceA()));//这会抛出编译异常
    }
}
```

以set方式注入依赖（可行）

```java
@Component
public class ServiceBB{
    private ServiceAA serviceAA;
    
    public void setServiceAA(ServiceAA serviceAA){
        this.serviceAA = serviceAA;
        System.out.println("B里面设置了A");
    }
}
```

```java
@Component
public class ServiceAA{
    private ServiceBB serviceBB;
    
    public void setServiceBB(ServiceBB serviceBB){
        this.serviceBB = serviceBB;
        System.out.println("A里面设置了B");
    }
}
```

```java
public class ClientSet{
    public static void main(String[] args){
        //创建serviceAA
        ServiceAA a = new ServiceAA();
        //创建serviceBB
        ServiceBB b = new ServiceBB();
        //将serviceA入到serviceB中
        b.setServiceAA(a);
        //将serviceB法入到serviceA中
        a.setServiceBB(b);
    }
}
/*
输出结果：

B里面设置了A
A里面设置了B
*/
```

#### 33 spring循环依赖bug演示

beans：A，B

```java
public class A {

	private B b;

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
        System.out.println("A call setB.");
	}
}
```

```java
public class B {

	private A a;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
        System.out.println("B call setA.");
	}	
}
```

运行类

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringContainer {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		A a = context.getBean("a", A.class);
		B b = context.getBean("b", B.class);
	}
}
```

默认的单例(Singleton)的场景是**支持**循环依赖的，不报错

beans.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
       http://www.springframework.org/schema/context 
       http://www.springframework.org/schema/context/spring-context-4.0.xsd
       http://www.springframework.org/schema/tx 
       http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-4.0.xsd">
    
    <bean id="a" class="com.lun.interview.circular.A">
    	<property name="b" ref="b"></property>
    </bean>
    <bean id="b" class="com.lun.interview.circular.B">
    	<property name="a" ref="a"></property>
    </bean>
    
</beans>
```

输出结果

```
00:00:25.649 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@6d86b085
00:00:25.828 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 2 bean definitions from class path resource [beans.xml]
00:00:25.859 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'a'
00:00:25.875 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'b'
B call setA.
A call setB.
```

原型(Prototype)的场景是**不支持**循环依赖的，会报错

beans.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
       http://www.springframework.org/schema/context 
       http://www.springframework.org/schema/context/spring-context-4.0.xsd
       http://www.springframework.org/schema/tx 
       http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-4.0.xsd">
    
    <bean id="a" class="com.lun.interview.circular.A" scope="prototype">
    	<property name="b" ref="b"></property>
    </bean>
    <bean id="b" class="com.lun.interview.circular.B" scope="prototype">
    	<property name="a" ref="a"></property>
    </bean>
    
</beans>
```

输出结果

```
00:01:39.904 [main] DEBUG org.springframework.context.support.ClassPathXmlApplicationContext - Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@6d86b085
00:01:40.062 [main] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 2 bean definitions from class path resource [beans.xml]
Exception in thread "main" org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'a' defined in class path resource [beans.xml]: Cannot resolve reference to bean 'b' while setting bean property 'b'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'b' defined in class path resource [beans.xml]: Cannot resolve reference to bean 'a' while setting bean property 'a'; nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:342)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveValueIfNecessary(BeanDefinitionValueResolver.java:113)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyPropertyValues(AbstractAutowireCapableBeanFactory.java:1697)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1442)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:593)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:516)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:342)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:207)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1115)
	at com.lun.interview.circular.ClientSpringContainer.main(ClientSpringContainer.java:10)
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'b' defined in class path resource [beans.xml]: Cannot resolve reference to bean 'a' while setting bean property 'a'; nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:342)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveValueIfNecessary(BeanDefinitionValueResolver.java:113)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyPropertyValues(AbstractAutowireCapableBeanFactory.java:1697)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1442)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:593)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:516)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:342)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:330)
	... 9 more
Caused by: org.springframework.beans.factory.BeanCurrentlyInCreationException: Error creating bean with name 'a': Requested bean is currently in creation: Is there an unresolvable circular reference?
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:268)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:330)
	... 17 more
```

重要结论(spring内部通过3级缓存来解决循环依赖) - DefaultSingletonBeanRegistry

只有单例的bean会通过三级缓存提前暴露来解决循环依赖的问题，而非单例的bean，每次从容器中获取都是一个新的对象，都会重新创建，所以非单例的bean是没有缓存的，不会将其放到三级缓存中。

第一级缓存（也叫单例池）singletonObjects：存放已经经历了完整生命周期的Bean对象。

第二级缓存：earlySingletonObjects，存放早期暴露出来的Bean对象，Bean的生命周期未结束（属性还未填充完。

第三级缓存：Map<String, ObjectFactory<?>> singletonFactories，存放可以生成Bean的工厂。

```java
package org.springframework.beans.factory.support;

...

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

	...

	/** Cache of singleton objects: bean name to bean instance. */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	/** Cache of singleton factories: bean name to ObjectFactory. */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

	/** Cache of early singleton objects: bean name to bean instance. */
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
 
    ...
    
}
```

#### 34 spring循环依赖debug前置知识

实例化 - 内存中申请一块内存空间，如同租赁好房子，自己的家当还未搬来。

初始化属性填充 - 完成属性的各种赋值，如同装修，家具，家电进场。

3个Map和四大方法，总体相关对象

![img](https://img-blog.csdnimg.cn/img_convert/fe2c0b589930bbf2988a374c2644d941.png)

第一层singletonObjects存放的是已经初始化好了的Bean,

第二层earlySingletonObjects存放的是实例化了，但是未初始化的Bean,

第三层singletonFactories存放的是FactoryBean。假如A类实现了FactoryBean,那么依赖注入的时候不是A类，而是A类产生的Bean

```java
package org.springframework.beans.factory.support;

...

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

	...

	/** 
	单例对象的缓存:bean名称—bean实例，即:所谓的单例池。
	表示已经经历了完整生命周期的Bean对象
	第一级缓存
	*/
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	/**
	早期的单例对象的高速缓存: bean名称—bean实例。
	表示 Bean的生命周期还没走完（Bean的属性还未填充）就把这个 Bean存入该缓存中也就是实例化但未初始化的 bean放入该缓存里
	第二级缓存
	*/
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

	/**
	单例工厂的高速缓存:bean名称—ObjectFactory
	表示存放生成 bean的工厂
	第三级缓存
	*/
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
 
    ...
    
}
```

A / B两对象在三级缓存中的迁移说明

A创建过程中需要B，于是A将自己放到三级缓里面，去实例化B。
B实例化的时候发现需要A，于是B先查一级缓存，没有，再查二级缓存，还是没有，再查三级缓存，找到了A然后把三级缓存里面的这个A放到二级缓存里面，并删除三级缓存里面的A。
B顺利初始化完毕，将自己放到一级缓存里面（此时B里面的A依然是创建中状态)，然后回来接着创建A，此时B已经创建结束，直接从一级缓存里面拿到B，然后完成创建，并将A自己放到一级缓存里面。

```java
@FunctionalInterface
public interface ObjectFactory<T> {

	T getObject() throws BeansException;

}
```

#### 35 spring循环依赖debug源码01

DEBUG一步一步来，scope默认为singleton

从运行类启航

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringContainer {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		A a = context.getBean("a", A.class);
		B b = context.getBean("b", B.class);
	}

}
```

```java
package org.springframework.context.support;

...

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    
    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
		this(new String[] {configLocation}, true, null);
	}
    
	public ClassPathXmlApplicationContext(
			String[] configLocations, boolean refresh, @Nullable ApplicationContext parent)
			throws BeansException {

		super(parent);
		setConfigLocations(configLocations);
		if (refresh) {
            //源于AbstractXmlApplicationContext
            //->AbstractRefreshableConfigApplicationContext
            //->AbstractRefreshableApplicationContext
            //->AbstractApplicationContext的refresh()
			refresh();
		}
	}
    
}
```

```java
package org.springframework.context.support;

...

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {
    ...
}
```

```java
package org.springframework.context.support;

...

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext
		implements BeanNameAware, InitializingBean {
    ...
}
```

```java
package org.springframework.context.support;

...

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    ...
}
```

```java
package org.springframework.context.support;

...

public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {
    
    ...
        
    @Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				initMessageSource();

				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				onRefresh();

				// Check for listener beans and register them.
				registerListeners();

--------------->//<---------------------重点关注点是这里
				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}
    
   	/**
	 * Finish the initialization of this context's bean factory,
	 * initializing all remaining singleton beans.
	 */
	protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
		// Initialize conversion service for this context.
		if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) &&
				beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {
			beanFactory.setConversionService(
					beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
		}

		// Register a default embedded value resolver if no bean post-processor
		// (such as a PropertyPlaceholderConfigurer bean) registered any before:
		// at this point, primarily for resolution in annotation attribute values.
		if (!beanFactory.hasEmbeddedValueResolver()) {
			beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));
		}

		// Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
		String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
		for (String weaverAwareName : weaverAwareNames) {
			getBean(weaverAwareName);
		}

		// Stop using the temporary ClassLoader for type matching.
		beanFactory.setTempClassLoader(null);

		// Allow for caching all bean definition metadata, not expecting further changes.
		beanFactory.freezeConfiguration();

------->//<---------------------重点关注点是这里
		// Instantiate all remaining (non-lazy-init) singletons.
		beanFactory.preInstantiateSingletons();
	}
    
}
```

#### 36 spring循环依赖debug源码02

接着上文的beanFactory.preInstantiateSingletons()

beanFactory是ConfigurableListableBeanFactory

DefaultListableBeanFactory实现了ConfigurableListableBeanFactory接口

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
		implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
    ...
    
    @Override
	public void preInstantiateSingletons() throws BeansException {
		if (logger.isTraceEnabled()) {
			logger.trace("Pre-instantiating singletons in " + this);
		}

		// Iterate over a copy to allow for init methods which in turn register new bean definitions.
		// While this may not be part of the regular factory bootstrap, it does otherwise work fine.
		List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
		//根据上下文beanNames为[a, b]
        
		// Trigger initialization of all non-lazy singleton beans...
		for (String beanName : beanNames) {//遍历a,b
			RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
			if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
				if (isFactoryBean(beanName)) {
					Object bean = getBean(FACTORY_BEAN_PREFIX + beanName);
					if (bean instanceof FactoryBean) {
						FactoryBean<?> factory = (FactoryBean<?>) bean;
						boolean isEagerInit;
						if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
							isEagerInit = AccessController.doPrivileged(
									(PrivilegedAction<Boolean>) ((SmartFactoryBean<?>) factory)::isEagerInit,
									getAccessControlContext());
						}
						else {
							isEagerInit = (factory instanceof SmartFactoryBean &&
									((SmartFactoryBean<?>) factory).isEagerInit());
						}
						if (isEagerInit) {
							getBean(beanName);
						}
					}
				}
				else {
------------------->//<---------------------重点关注点是这里
    				//源于AbstractAutowireCapableBeanFactory
    				//->AbstractBeanFactory的getBean()
					getBean(beanName);
				}
			}
		}

------------------------------下面可略读---------------------------
		// Trigger post-initialization callback for all applicable beans...
		for (String beanName : beanNames) {
			Object singletonInstance = getSingleton(beanName);
			if (singletonInstance instanceof SmartInitializingSingleton) {
				SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
				if (System.getSecurityManager() != null) {
					AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
						smartSingleton.afterSingletonsInstantiated();
						return null;
					}, getAccessControlContext());
				}
				else {
					smartSingleton.afterSingletonsInstantiated();
				}
			}
		}
	}    
    
}
```

```java
package org.springframework.beans.factory.support;

...

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
		implements AutowireCapableBeanFactory {
    ...
}
```

```java
package org.springframework.beans.factory.support;

...

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
	...
    @Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null, null, false);
	}
    
    
	@SuppressWarnings("unchecked")
	protected <T> T doGetBean(
			String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
			throws BeansException {

        //name为a
		String beanName = transformedBeanName(name);
		Object bean;

		// Eagerly check singleton cache for manually registered singletons.
------->//<---------------------重点关注点是这里
------->//<---------------------重点关注点是这里
------->//<---------------------重点关注点是这里
    	//源于FactoryBeanRegistrySupport
    	//->DefaultSingletonBeanRegistry的getSingleton()
    	//DefaultSingletonBeanRegistry也就是上文谈论的三级缓存所在类
    	//本章节末有getSingleton()源码
    	//最后getSingleton返回null
		Object sharedInstance = getSingleton(beanName);
        //sharedInstance为null，下面if语块不执行
		if (sharedInstance != null && args == null) {
			if (logger.isTraceEnabled()) {
				if (isSingletonCurrentlyInCreation(beanName)) {
					logger.trace("Returning eagerly cached instance of singleton bean '" + beanName +
							"' that is not fully initialized yet - a consequence of a circular reference");
				}
				else {
					logger.trace("Returning cached instance of singleton bean '" + beanName + "'");
				}
			}
			bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
		}

		else {
			// Fail if we're already creating this bean instance:
			// We're assumably within a circular reference.
            //不执行下面if语块
			if (isPrototypeCurrentlyInCreation(beanName)) {
				throw new BeanCurrentlyInCreationException(beanName);
			}

			// Check if bean definition exists in this factory.
			BeanFactory parentBeanFactory = getParentBeanFactory();
            //parentBeanFactory为null，不执行下面if语块
			if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
				// Not found -> check parent.
				String nameToLookup = originalBeanName(name);
				if (parentBeanFactory instanceof AbstractBeanFactory) {
					return ((AbstractBeanFactory) parentBeanFactory).doGetBean(
							nameToLookup, requiredType, args, typeCheckOnly);
				}
				else if (args != null) {
					// Delegation to parent with explicit args.
					return (T) parentBeanFactory.getBean(nameToLookup, args);
				}
				else if (requiredType != null) {
					// No args -> delegate to standard getBean method.
					return parentBeanFactory.getBean(nameToLookup, requiredType);
				}
				else {
					return (T) parentBeanFactory.getBean(nameToLookup);
				}
			}

			if (!typeCheckOnly) {
				markBeanAsCreated(beanName);
			}

			try {
				//下面方法返回Root bean: class [com.lun.interview.circular.A]; 
                //scope=singleton; abstract=false; lazyInit=false; 
                //autowireMode=0; dependencyCheck=0; autowireCandidate=true; 
                //primary=false; factoryBeanName=null; factoryMethodName=null; 
                //initMethodName=null; destroyMethodName=null; 
                //defined in class path resource [beans.xml]
                //重点关注scope=singleton
                RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
                
				checkMergedBeanDefinition(mbd, beanName, args);

				// Guarantee initialization of beans that the current bean depends on.
				String[] dependsOn = mbd.getDependsOn();
                //dependsOn返回null，不执行下面if语块
				if (dependsOn != null) {
					for (String dep : dependsOn) {
						if (isDependent(beanName, dep)) {
							throw new BeanCreationException(mbd.getResourceDescription(), beanName,
									"Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
						}
						registerDependentBean(dep, beanName);
						try {
							getBean(dep);
						}
						catch (NoSuchBeanDefinitionException ex) {
							throw new BeanCreationException(mbd.getResourceDescription(), beanName,
									"'" + beanName + "' depends on missing bean '" + dep + "'", ex);
						}
					}
				}

				// Create bean instance.
                //mbd.isSingleton()返回true，执行下面if语块
				if (mbd.isSingleton()) {
					sharedInstance = getSingleton(beanName, () -> {
						try {
--------------------------->//<---------------------重点关注点是这里
							return createBean(beanName, mbd, args);
						}
						catch (BeansException ex) {
							// Explicitly remove instance from singleton cache: It might have been put there
							// eagerly by the creation process, to allow for circular reference resolution.
							// Also remove any beans that received a temporary reference to the bean.
							destroySingleton(beanName);
							throw ex;
						}
					});
					bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
				}

----------------------------下面代码可略读---------
    ------------------
                
				else if (mbd.isPrototype()) {
					// It's a prototype -> create a new instance.
					Object prototypeInstance = null;
					try {
						beforePrototypeCreation(beanName);
						prototypeInstance = createBean(beanName, mbd, args);
					}
					finally {
						afterPrototypeCreation(beanName);
					}
					bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
				}

				else {
					String scopeName = mbd.getScope();
					if (!StringUtils.hasLength(scopeName)) {
						throw new IllegalStateException("No scope name defined for bean ´" + beanName + "'");
					}
					Scope scope = this.scopes.get(scopeName);
					if (scope == null) {
						throw new IllegalStateException("No Scope registered for scope name '" + scopeName + "'");
					}
					try {
						Object scopedInstance = scope.get(beanName, () -> {
							beforePrototypeCreation(beanName);
							try {
								return createBean(beanName, mbd, args);
							}
							finally {
								afterPrototypeCreation(beanName);
							}
						});
						bean = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
					}
					catch (IllegalStateException ex) {
						throw new BeanCreationException(beanName,
								"Scope '" + scopeName + "' is not active for the current thread; consider " +
								"defining a scoped proxy for this bean if you intend to refer to it from a singleton",
								ex);
					}
				}
			}
			catch (BeansException ex) {
				cleanupAfterBeanCreationFailure(beanName);
				throw ex;
			}
		}

		// Check if required type matches the type of the actual bean instance.
		if (requiredType != null && !requiredType.isInstance(bean)) {
			try {
				T convertedBean = getTypeConverter().convertIfNecessary(bean, requiredType);
				if (convertedBean == null) {
					throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
				}
				return convertedBean;
			}
			catch (TypeMismatchException ex) {
				if (logger.isTraceEnabled()) {
					logger.trace("Failed to convert bean '" + name + "' to required type '" +
							ClassUtils.getQualifiedName(requiredType) + "'", ex);
				}
				throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
			}
		}
		return (T) bean;
	}
}
```

AbstractBeanFactory继承了FactoryBeanRegistrySupport

```java
package org.springframework.beans.factory.support;

...

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    ...
}
```

FactoryBeanRegistrySupport继承了DefaultSingletonBeanRegistry，DefaultSingletonBeanRegistry也就是前文讨论三级缓存所在的类。

```java
package org.springframework.beans.factory.support;

...

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
	
    ...
    
	/** Cache of singleton objects: bean name to bean instance. */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	/** Cache of singleton factories: bean name to ObjectFactory. */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

	/** Cache of early singleton objects: bean name to bean instance. */
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
    
    @Override
	@Nullable
	public Object getSingleton(String beanName) {
		return getSingleton(beanName, true);
	}

	/**
	 * Return the (raw) singleton object registered under the given name.
	 * <p>Checks already instantiated singletons and also allows for an early
	 * reference to a currently created singleton (resolving a circular reference).
	 */
	@Nullable
	protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        //beanName为a,查找缓存，显然返回null
		Object singletonObject = this.singletonObjects.get(beanName);
		//singletonObject为null，下面if语块不执行
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
			synchronized (this.singletonObjects) {
				singletonObject = this.earlySingletonObjects.get(beanName);
				if (singletonObject == null && allowEarlyReference) {
					ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
					if (singletonFactory != null) {
						singletonObject = singletonFactory.getObject();
						this.earlySingletonObjects.put(beanName, singletonObject);
						this.singletonFactories.remove(beanName);
					}
				}
			}
		}
        //返回null
		return singletonObject;
	}
    
}
```

#### 37 spring循环依赖debug源码03

第一层缓存singletonObjects存放已经初始化好的Bean；第二层缓存earlySingletonObjects存放实例化但未初始化的Bean；第三层缓存singletonFactories存放的是FactoryBean，如果某个类实现了FactoryBean，那么依赖注入的不是该类，而是该类对应的Bean。

#### 38 spring循环依赖debug源码04

**再次A / B两对象在三级缓存中的迁移说明**

1. A创建过程中需要B，于是A将自己放到三级缓里面，去实例化B。
2. B实例化的时候发现需要A，于是B先查一级缓存，没有，再查二级缓存，还是没有，再查三级缓存，找到了A然后把三级缓存里面的这个A放到二级缓存里面，并删除三级缓存里面的A。
3. B顺利初始化完毕，将自己放到一级缓存里面（此时B里面的A依然是创建中状态)，然后回来接着创建A，此时B已经创建结束，直接从一级缓存里面拿到B，然后完成创建，并将A自己放到一级缓存里面。

#### 39 spring循环依赖小总结

Spring创建 bean主要分为两个步骤，创建原始bean对象，接着去填充对象属性和初始化

每次创建 bean之前，我们都会从缓存中查下有没有该bean，因为是单例，只能有一个

当我们创建 beanA的原始对象后，并把它放到三级缓存中，接下来就该填充对象属性了，这时候发现依赖了beanB，接着就又去创建beanB，同样的流程，创建完beanB填充属性时又发现它依赖了beanA又是同样的流程，
不同的是：这时候可以在三级缓存中查到刚放进去的原始对象beanA.所以不需要继续创建，用它注入 beanB，完成 beanB的创建

既然 beanB创建好了，所以 beanA就可以完成填充属性的步骤了，接着执行剩下的逻辑，闭环完成

![img](https://img-blog.csdnimg.cn/img_convert/b34a877c4363380c0243acbb69b4b834.png)

Spring解决循环依赖依靠的是Bean的"中间态"这个概念，而这个中间态指的是已经实例化但还没初始化的状态—>半成债。实例化的过程又是通过构造器创建的，如果A还没创建好出来怎么可能提前曝光，所以构造器的循环依赖无法解决。”对

Spring为了解决单例的循坏依赖问题，使用了三级缓存：

其中一级缓存为单例池(singletonObjects)。

二级缓存为提前曝光对象(earlySingletonObjects)。

三级级存为提前曝光对象工厂(singletonFactories) 。

假设A、B循环引用，实例化A的时候就将其放入三级缓存中，接着填充属性的时候，发现依赖了B，同样的流程也是实例化后放入三级缓存，接着去填充属性时又发现自己依赖A，这时候从缓存中查找到早期暴露的A，没有AOP代理的话，直接将A的原始对象注入B，完成B的初始化后，进行属性填充和初始化，这时候B完成后，就去完成剩下的A的步骤，如果有AOP代理，就进行AOP处理获取代理后的对象A，注入B，走剩下的流程。

![ms-007](https://img-blog.csdnimg.cn/img_convert/c816304c84497621b14ccd17d100a8e8.png)

Spring解决循环依赖过程：

1. 调用doGetBean()方法，想要获取beanA，于是调用getSingleton()方法从缓存中查找beanA
2. 在getSingleton()方法中，从一级缓存中查找，没有，返回null
3. doGetBean()方法中获取到的beanA为null，于是走对应的处理逻辑，调用getSingleton()的重载方法（参数为ObjectFactory的)
4. 在getSingleton()方法中，先将beanA_name添加到一个集合中，用于标记该bean正在创建中。然后回调匿名内部类的creatBean方法
5. 进入AbstractAutowireCapableBeanFactory#ndoCreateBean，先反射调用构造器创建出beanA的实例，然后判断:是否为单例、是否允许提前暴露引用(对于单例一般为true)、是否正在创建中（即是否在第四步的集合中）。判断为true则将beanA添加到【三级缓存】中
6. 对beanA进行属性填充，此时检测到beanA依赖于beanB，于是开始查找beanB
7. 调用doGetBean()方法，和上面beanA的过程一样，到缓存中查找beanB，没有则创建，然后给beanB填充属性
8. 此时 beanB依赖于beanA，调用getSingleton()获取beanA，依次从一级、二级、三级缓存中找，此时从三级缓存中获取到beanA的创建工厂，通过创建工厂获取到singletonObject，此时这个singletonObject指向的就是上面在doCreateBean()方法中实例化的beanA
9. 这样beanB就获取到了beanA的依赖，于是beanB顺利完成实例化，并将beanA从三级缓存移动到二级缓存中
10. 随后beanA继续他的属性填充工作，此时也获取到了beanB，beanA也随之完成了创建，回到getsingleton()方法中继续向下执行，将beanA从二级缓存移动到一级缓存中

#### 40 redis版本升级说明

接下来内容概述：

- 安装redis6.0.8
- redis传统五大数据类型的落地应用
- 知道分布式锁吗？有哪些实现方案？你谈谈对redis分布式锁的理解，删key的时候有什么问题?
- redis缓存过期淘汰策略
- redis的LRU算法简介

安装redis6.0.8：

- [Redis官网](https://redis.io/)
- [Redis中文网](http://www.redis.cn/)
- 安全Bug按照官网提示，升级成为6.0.8
- 进入Redis命令行，输入info，返回关于Redis服务器的各种信息（包括版本号）和统计数值。

#### 41 redis两个小细节说明

redis基本类型：

- string
- list
- set
- zset（sorted set）
- hash

其他redis的类型

- bitmap
- HyperLogLogs
- GEO
- Stream

备注

- **命令不区分大小写**，而key是区分大小写的
- help @类型名词



#### 42 string类型使用场景

最常用

- SET key value
- GET key

同时设置/获取多个键值

- MSET key value [key value…]
- MGET key [key…]

数值增减

- 递增数字 INCR key（可以不用预先设置key的数值。如果预先设置key但值不是数字，则会报错)
- 增加指定的整数 INCRBY key increment
- 递减数值 DECR key
- 减少指定的整数 DECRBY key decrement

获取字符串长度

- STRLEN key

分布式锁

- SETNX key value
- SET key value [EX seconds] [PX milliseconds] [NX|XX]
  - EX：key在多少秒之后过期
  - PX：key在多少毫秒之后过期
  - NX：当key不存在的时候，才创建key，效果等同于setnx
  - XX：当key存在的时候，覆盖key

应用场景

- 商品编号、订单号采用INCR命令生成
- 是否喜欢的文章

#### 43 hash类型使用场景

Redis的Hash类型相当于Java中Map<String, Map<Object, Object>>

一次设置一个字段值 HSET key field value

一次获取一个字段值 HGET key field

一次设置多个字段值 HMSET key field value [field value …]

一次获取多个字段值 HMGET key field [field …]

获取所有字段值 HGETALL key

获取某个key内的全部数量 HLEN

删除一个key HDEL

应用场景 - 购物车早期，当前小中厂可用

- 新增商品 hset shopcar:uid1024 334488 1
- 新增商品 hset shopcar:uid1024 334477 1
- 增加商品数量 hincrby shopcar:uid1024 334477 1
- 商品总数 hlen shopcar:uid1024
- 全部选择 hgetall shopcar:uid1024

#### 44 list类型使用场景

向列表左边添加元素 LPUSH key value [value …]

向列表右边添加元素 RPUSH key value [value …]

查看列表 LRANGE key start stop

获取列表中元素的个数 LLEN key

应用场景 - 微信文章订阅公众号

- 大V作者李永乐老师和ICSDN发布了文章分别是11和22
- 阳哥关注了他们两个，只要他们发布了新文章，就会安装进我的List
  - lpush likearticle:阳哥id11 22
- 查看阳哥自己的号订阅的全部文章，类似分页，下面0~10就是一次显示10条
  - lrange likearticle:阳哥id 0 10

#### 45 set类型使用场景

添加元素 SADD key member [member …]

删除元素 SREM key member [member …]

获取集合中的所有元素 SMEMBERS key

判断元素是否在集合中 SISMEMBER key member

获取集合中的元素个数 SCARD key

从集合中随机弹出一个元素，元素不删除 SRANDMEMBER key [数字]

从集合中随机弹出一个元素，出一个删一个 SPOP key[数字]

集合运算

- 集合的差集运算A - B
  - 属于A但不属于B的元素构成的集合
  - SDIFF key [key …]
- 集合的交集运算A ∩ B
  - 属于A同时也属于B的共同拥有的元素构成的集合
  - SINTER key [key …]
- 集合的并集运算A U B
  - 属于A或者属于B的元素合并后的集合
  - SUNION key [key …]

应用场景

- 微信抽奖小程序
  - 用户ID，立即参与按钮
    - SADD key 用户ID
  - 显示已经有多少人参与了、上图23208人参加
    - SCARD key
  - 抽奖(从set中任意选取N个中奖人)
    - SRANDMEMBER key 2（随机抽奖2个人，元素不删除）
    - SPOP key 3（随机抽奖3个人，元素会删除）

- 微信朋友圈点赞
  - 新增点赞
    - sadd pub:msglD 点赞用户ID1 点赞用户ID2
  - 取消点赞
    - srem pub:msglD 点赞用户ID
  - 展现所有点赞过的用户
    - SMEMBERS pub:msglD
  - 点赞用户数统计，就是常见的点赞红色数字
    - scard pub:msgID
  - 判断某个朋友是否对楼主点赞过
    - SISMEMBER pub:msglD用户ID

- 微博好友关注社交关系
  - 共同关注：我去到局座张召忠的微博，马上获得我和局座共同关注的人
    - sadd s1 1 2 3 4 5
    - sadd s2 3 4 5 6 7
    - SINTER s1 s2
  - 我关注的人也关注他(大家爱好相同)

- QQ内推可能认识的人
  - sadd s1 1 2 3 4 5
  - sadd s2 3 4 5 6 7
  - SINTER s1 s2
  - SDIFF s1 s2
  - SDIFF s2 s1

#### 46 zset类型使用场景

向有序集合中加入一个元素和该元素的分数

添加元素 ZADD key score member [score member …]

按照元素分数从小到大的顺序返回索引从start到stop之间的所有元素 ZRANGE key start stop [WITHSCORES]

获取元素的分数 ZSCORE key member

删除元素 ZREM key member [member …]

获取指定分数范围的元素 ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]

增加某个元素的分数 ZINCRBY key increment member

获取集合中元素的数量 ZCARD key

获得指定分数范围内的元素个数 ZCOUNT key min max

按照排名范围删除元素 ZREMRANGEBYRANK key start stop

获取元素的排名

- 从小到大 ZRANK key member


- 从大到小 ZREVRANK key member


应用场景

- 根据商品销售对商品进行排序显示
  - 定义商品销售排行榜（sorted set集合），key为goods:sellsort，分数为商品销售数量。
    - 商品编号1001的销量是9，商品编号1002的销量是15 - zadd goods:sellsort 9 1001 15 1002
    - 有一个客户又买了2件商品1001，商品编号1001销量加2 - zincrby goods:sellsort 2 1001
    - 求商品销量前10名 - ZRANGE goods:sellsort 0 10 withscores
- 抖音热搜
  - 点击视频
    - ZINCRBY hotvcr:20200919 1 八佰
    - ZINCRBY hotvcr:20200919 15 八佰 2 花木兰
  - 展示当日排行前10条
    - ZREVRANGE hotvcr:20200919 0 9 withscores



#### 47 redis分布式锁前情说明

常见的面试题：

- Redis除了拿来做缓存，你还见过基于Redis的什么用法？
- Redis做分布式锁的时候有需要注意的问题？
- 如果是Redis是单点部署的，会带来什么问题？那你准备怎么解决单点问题呢？
- 集群模式下，比如主从模式，有没有什么问题呢？
- 那你简单的介绍一下Redlock吧？你简历上写redisson，你谈谈。
- Redis分布式锁如何续期？看门狗知道吗？

#### 48 boot整合redis搭建超卖程序-上

使用场景：多个服务间 + 保证同一时刻内 + 同一用户只能有一个请求（防止关键业务出现数据冲突和并发错误）

建两个Module：boot_redis01，boot_redis02

POM

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-parent</artifactId>
	    <version>2.3.3.RELEASE</version>
	    <relativePath/> <!-- lookup parent from repository -->
	</parent>

	<groupId>com.lun</groupId>
	<artifactId>boot_redis01</artifactId> <!--boot_redis02-->
	<version>1.0.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>boot_redis01</name> <!--boot_redis02-->
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<dependencies>

		<!-- web+actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
		<!-- SpringBoot与Redis整合依赖 -->

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-pool2</artifactId>
		</dependency>
		
		<!-- jedis -->
		<dependency>
			<groupId>redis.clients</groupId>
			<artifactId>jedis</artifactId>
			<version>3.1.0</version>
		</dependency>

		<!-- Spring Boot AOP技术-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
		
		<!-- redisson -->
		<dependency>
			<groupId>org.redisson</groupId>
			<artifactId>redisson</artifactId>
			<version>3.13.4</version>
		</dependency>

		<!-- 一般通用基础配置 -->
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-devtools</artifactId>
		    <scope>runtime</scope>
		    <optional>true</optional>
		</dependency>
		
		<dependency>
		    <groupId>org.projectlombok</groupId>
		    <artifactId>lombok</artifactId>
		    <optional>true</optional>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-test</artifactId><scope>test</scope>
		    <exclusions>
		        <exclusion>
		            <groupId>org.junit.vintage</groupId>
		            <artifactId>junit-vintage-engine</artifactId>
		        </exclusion>
		    </exclusions>
		</dependency>

	</dependencies>
	
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

Properties

```properties
server.port=1111
#2222

#=========================redis相关配置========================
#Redis数据库索引（默认方0）
spring.redis.database=0
#Redis服务器地址
spring.redis.host=127.0.0.1
#Redis服务器连接端口
spring.redis.port=6379
#Redis服务器连接密码（默认为空）
spring.redis.password=
#连接池最大连接数（使用负值表示没有限制）默认8
spring.redis.lettuce.pool.max-active=8
#连接池最大阻塞等待时间（使用负值表示没有限制）默认-1
spring.redis.lettuce.pool.max-wait=-1
#连接池中的最大空闲连接默认8
spring.redis.lettuce.pool.max-idle=8
#连接池中的最小空闲连接默犬认0
spring.redis.lettuce.pool.min-idle=0
```

主启动类

```java
@SpringBootApplication
public class BootRedis01Application{
    
    public static void main(String[] args){
        SpringApplication.run(BootRedis01Application.class, args);
    }
    
}
```

配置类

```java
import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory){
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
    
}
```

#### 49 boot整合redis搭建超卖程序-下

```java
@RestController
public class GoodController{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/buy_goods")
    public String buy_Goods(){

        String result = stringRedisTemplate.opsForValue().get("goods:001");// get key ====看看库存的数量够不够
        int goodsNumber = result == null ? 0 : Integer.parseInt(result);
        if(goodsNumber > 0){
            int realNumber = goodsNumber - 1;
            stringRedisTemplate.opsForValue().set("goods:001", String.valueOf(realNumber));
            System.out.println("成功买到商品，库存还剩下: "+ realNumber + " 件" + "\t服务提供端口" + serverPort);
            return "成功买到商品，库存还剩下:" + realNumber + " 件" + "\t服务提供端口" + serverPort;
        }else{
            System.out.println("商品已经售完/活动结束/调用超时,欢迎下次光临" + "\t服务提供端口" + serverPort);
        }

        return "商品已经售完/活动结束/调用超时,欢迎下次光临" + "\t服务提供端口" + serverPort;
    }
    
}
```

测试

- redis：`set goods:001 100`
- 浏览器：http://localhost:1111/buy_goods

boot_redis02拷贝boot_redis01

#### 50 redis分布式锁01

JVM层面的加锁，单机版的锁

- synchronized
- ReentraLock

```java
class X {
    private final ReentrantLock lock = new ReentrantLock();
    // ...

    public void m() {
        lock.lock();  // block until condition holds//不见不散
        try {
            // ... method body
        } finally {
            lock.unlock()
        }
    }
     
     
    public void m2() {

       	if(lock.tryLock(timeout, unit)){//过时不候
            try {
            // ... method body
            } finally {
                lock.unlock()
            }   
        }else{
            // perform alternative actions
        }
   }
 }
```

#### 51 redis分布式锁02

分布式部署后，单机锁还是出现超卖现象，需要分布式锁

![img](https://img-blog.csdnimg.cn/img_convert/5717f85d1e82aa9a581e2404bd9cdb1e.png)

redis cluster

Nginx配置负载均衡

Nginx配置文件修改内容

```xml
upstream myserver{
    server 127.0.0.1:1111;
    server 127.0.0.1:2222;
}

server {
    listen       80;
    server_name  localhost;

    #charset koi8-r;

    #access_log  logs/host.access.log  main;

    location / {
        # 负责用到的配置
        proxy_pass  http://myserver;
        root   html;
        index  index.html index.htm;
    }

    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
    	root   html;
    }
}
```

启动两个微服务：1111，2222，多次访问http://localhost/buy_goods，服务提供端口在1111，2222两者之间横跳

上面手点，下面高并发模拟

redis：set goods:001 100，恢复到100

用到Apache JMeter，100个线程同时访问http://localhost/buy_goods。

![img](https://img-blog.csdnimg.cn/img_convert/0a9b749014864fb4f13c4c1b663d12c1.png)

![img](https://img-blog.csdnimg.cn/img_convert/1513a28520f864c4c1cb80b3c1bdbeaa.png)

启动测试，后台打印如下：

![img](https://img-blog.csdnimg.cn/img_convert/046d525f67ae40aebbd78c8401a8d35b.png)

![img](https://img-blog.csdnimg.cn/img_convert/170260fc5dbd96a92fba5014dc21ca8a.png)

这就是所谓分布式部署后出现超卖现象。

Redis具有极高的性能，且其命令对分布式锁支持友好，借助SET命令即可实现加锁处理。

[SET](https://redis.io/commands/set)

- EX seconds – Set the specified expire time, in seconds.
- PX milliseconds – Set the specified expire time, in milliseconds.
- NX – Only set the key if it does not already exist.
- XX – Only set the key if it already exist.

在Java层面

```java
public static final String REDIS_LOCK = "redis_lock";

@Autowired
private StringRedisTemplate stringRedisTemplate;

public void m(){
    String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

    Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value);


    if(!flag) {
        return "抢锁失败";
    }
 
    ...//业务逻辑
    
    stringRedisTemplate.delete(REDIS_LOCK);
}
```

#### 52 redis分布式锁03

**上面Java源码分布式锁问题**：出现异常的话，可能无法释放锁，必须要在代码层面finally释放锁。

解决方法：try…finally…

```java
public static final String REDIS_LOCK = "redis_lock";

@Autowired
private StringRedisTemplate stringRedisTemplate;

public void m(){
    String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

    try{
		Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value);

   		if(!flag) {
        	return "抢锁失败";
	    }
        
    	...//业务逻辑
            
    }finally{
	    stringRedisTemplate.delete(REDIS_LOCK);   
    }
}
```

**另一个问题**：部署了微服务jar包的机器挂了，代码层面根本没有走到finally这块，没办法保证解锁，这个key没有被删除，需要加入一个过期时间限定key。

```java
public static final String REDIS_LOCK = "redis_lock";

@Autowired
private StringRedisTemplate stringRedisTemplate;

public void m(){
    String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

    try{
		Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value);
		//设定时间
        stringRedisTemplate.expire(REDIS_LOCK, 10L, TimeUnit.SECONDS);
        
   		if(!flag) {
        	return "抢锁失败";
	    }
        
    	...//业务逻辑
            
    }finally{
	    stringRedisTemplate.delete(REDIS_LOCK);   
    }
}
```

#### 53 redis分布式锁04

**新问题**：设置key+过期时间分开了，必须要合并成一行具备原子性。

解决方法：

```java
public static final String REDIS_LOCK = "redis_lock";

@Autowired
private StringRedisTemplate stringRedisTemplate;

public void m(){
    String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

    try{
		Boolean flag = stringRedisTemplate.opsForValue()//使用另一个带有设置超时操作的方法
            .setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);
		//设定时间
        //stringRedisTemplate.expire(REDIS_LOCK, 10L, TimeUnit.SECONDS);
        
   		if(!flag) {
        	return "抢锁失败";
	    }
        
    	...//业务逻辑
            
    }finally{
	    stringRedisTemplate.delete(REDIS_LOCK);   
    }
}
```

**另一个新问题**：张冠李戴，删除了别人的锁

![img](https://img-blog.csdnimg.cn/img_convert/8491f7f7a87dcc888d60141f6d662e1b.png)

解决方法：只能自己删除自己的，不许动别人的。

```java
public static final String REDIS_LOCK = "redis_lock";

@Autowired
private StringRedisTemplate stringRedisTemplate;

public void m(){
    String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

    try{
		Boolean flag = stringRedisTemplate.opsForValue()//使用另一个带有设置超时操作的方法
            .setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);
		//设定时间
        //stringRedisTemplate.expire(REDIS_LOCK, 10L, TimeUnit.SECONDS);
        
   		if(!flag) {
        	return "抢锁失败";
	    }
        
    	...//业务逻辑
            
    }finally{
        if(stringRedisTemplate.opsForValue().get(REDIS_LOCK).equals(value)) {
            stringRedisTemplate.delete(REDIS_LOCK);
        }
    }
}
```

#### 54 redis分布式锁05

finally块的判断 + del删除操作不是原子性的

用lua脚本

用redis自身的事务

Redis事务复习

事务介绍

- Redis的事条是通过MULTI，EXEC，DISCARD和WATCH这四个命令来完成。
- Redis的单个命令都是原子性的，所以这里确保事务性的对象是命令集合。
- Redis将命令集合序列化并确保处于一事务的命令集合连续且不被打断的执行。
- Redis不支持回滚的操作。

| 命令              | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| DISCARD           | 取消事务，放弃执行事务块内的所有命令。                       |
| EXEC              | 执行所有事务块内的命令。                                     |
| MULTI             | 标记一个事务块的开始。                                       |
| UNWATCH           | 取消 WATCH 命令对所有 key 的监视。                           |
| WATCH key [key …] | 监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。 |

#### 55 redis分布式锁06

继续上一章节，解决之道

```java
public static final String REDIS_LOCK = "redis_lock";

@Autowired
private StringRedisTemplate stringRedisTemplate;

public void m(){
    String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

    try{
		Boolean flag = stringRedisTemplate.opsForValue()//使用另一个带有设置超时操作的方法
            .setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);
		//设定时间
        //stringRedisTemplate.expire(REDIS_LOCK, 10L, TimeUnit.SECONDS);
        
   		if(!flag) {
        	return "抢锁失败";
	    }
        
    	...//业务逻辑
            
    }finally{
        while(true){
            stringRedisTemplate.watch(REDIS_LOCK);
            if(stringRedisTemplate.opsForValue().get(REDIS_LOCK).equalsIgnoreCase(value)){
                stringRedisTemplate.setEnableTransactionSupport(true);
                stringRedisTemplate.multi();
                stringRedisTemplate.delete(REDIS_LOCK);
                List<Object> list = stringRedisTemplate.exec();
                if (list == null) {
                    continue;
                }
            }
            stringRedisTemplate.unwatch();
            break;
        } 
    }
}
```

#### 56 redis分布式锁07

Redis调用Lua脚本通过eval命令保证代码执行的原子性

RedisUtils：

```java
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {

	private static JedisPool jedisPool;
	
	static {
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxTotal(20);
		jpc.setMaxIdle(10);
		jedisPool = new JedisPool(jpc);
	}
	
	public static JedisPool getJedis() throws Exception{
		if(jedisPool == null)
			throw new NullPointerException("JedisPool is not OK.");
		return jedisPool;
	}
	
}
```

```java
public static final String REDIS_LOCK = "redis_lock";

@Autowired
private StringRedisTemplate stringRedisTemplate;

public void m(){
    String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

    try{
		Boolean flag = stringRedisTemplate.opsForValue()//使用另一个带有设置超时操作的方法
            .setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);
		//设定时间
        //stringRedisTemplate.expire(REDIS_LOCK, 10L, TimeUnit.SECONDS);
        
   		if(!flag) {
        	return "抢锁失败";
	    }
        
    	...//业务逻辑
            
    }finally{
    	Jedis jedis = RedisUtils.getJedis();
    	
    	String script = "if redis.call('get', KEYS[1]) == ARGV[1] "
    			+ "then "
    			+ "    return redis.call('del', KEYS[1]) "
    			+ "else "
    			+ "    return 0 "
    			+ "end";
    	
    	try {
    		
    		Object o = jedis.eval(script, Collections.singletonList(REDIS_LOCK),// 
    				Collections.singletonList(value));
    		
    		if("1".equals(o.toString())) {
    			System.out.println("---del redis lock ok.");
    		}else {
    			System.out.println("---del redis lock error.");
    		}
    		
    		
    	}finally {
    		if(jedis != null) 
    			jedis.close();
    	}
    }
}
```

#### 57 redis分布式锁08

确保RedisLock过期时间大于业务执行时间的问题

Redis分布式锁如何续期？

集群 + CAP对比ZooKeeper 对比ZooKeeper，重点，CAP

- Redis - AP -redis异步复制造成的锁丢失，比如：主节点没来的及把刚刚set进来这条数据给从节点，就挂了。
- ZooKeeper - CP

CAP

- C：Consistency（强一致性）
- A：Availability（可用性）
- P：Partition tolerance（分区容错性）

综上所述

Redis集群环境下，**我们自己写的也不OK**，直接上RedLock之Redisson落地实现。

#### 58 redis分布式锁09

[Redisson官方网站](https://redisson.org/)

Redisson配置类

```java
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedisConfig {

    @Bean
    public Redisson redisson() {
    	Config config = new Config();
    	config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
    	return (Redisson)Redisson.create(config);
    }
    
}
```

Redisson模板

```java
public static final String REDIS_LOCK = "REDIS_LOCK";

@Autowired
private Redisson redisson;

@GetMapping("/doSomething")
public String doSomething(){

    RLock redissonLock = redisson.getLock(REDIS_LOCK);
    redissonLock.lock();
    try {
        //doSomething
    }finally {
        redissonLock.unlock();
    }
}
```

回到实例

```java
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodController{

	public static final String REDIS_LOCK = "REDIS_LOCK";
	
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${server.port}")
    private String serverPort;
    
    @Autowired
    private Redisson redisson;
    
    @GetMapping("/buy_goods")
    public String buy_Goods(){
    	
    	//String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
    	
    	RLock redissonLock = redisson.getLock(REDIS_LOCK);
    	redissonLock.lock();
    	try {
	        String result = stringRedisTemplate.opsForValue().get("goods:001");// get key ====看看库存的数量够不够
	        int goodsNumber = result == null ? 0 : Integer.parseInt(result);
	        if(goodsNumber > 0){
	            int realNumber = goodsNumber - 1;
	            stringRedisTemplate.opsForValue().set("goods:001", String.valueOf(realNumber));
	            System.out.println("成功买到商品，库存还剩下: "+ realNumber + " 件" + "\t服务提供端口" + serverPort);
	            return "成功买到商品，库存还剩下:" + realNumber + " 件" + "\t服务提供端口" + serverPort;
	        }else{
	            System.out.println("商品已经售完/活动结束/调用超时,欢迎下次光临" + "\t服务提供端口" + serverPort);
	        }
	
	        return "商品已经售完/活动结束/调用超时,欢迎下次光临" + "\t服务提供端口" + serverPort;
    	}finally {
    		redissonLock.unlock();
    	}
    }
}
```

重启boot_redis01，boot_redis02，Nginx，重置Redis：`set goods:001 100`，启动JMeter，100个线程访问http://localhost/buy_goods。最后，后台输出：

![img](https://img-blog.csdnimg.cn/img_convert/777a0a97ebb6306b4ae3d9b698b58883.png)

#### 59 redis分布式锁10

让代码更加严谨

```java
public static final String REDIS_LOCK = "REDIS_LOCK";

@Autowired
private Redisson redisson;

@GetMapping("/doSomething")
public String doSomething(){

    RLock redissonLock = redisson.getLock(REDIS_LOCK);
    redissonLock.lock();
    try {
        //doSomething
    }finally {
    	//添加后，更保险
		if(redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()) {
    		redissonLock.unlock();
    	}
    }
}
```

可避免如下异常：

> *IllegalMonitorStateException: attempt to unlock lock，not loked by current thread by node id:da6385f-81a5-4e6c-b8c0*

#### 60 redis分布式锁总结回顾

synchronized单机版oK，上分布式

nginx分布式微服务单机锁不行

取消单机锁，上Redis分布式锁setnx

只加了锁，没有释放锁，出异常的话，可能无法释放锁,必须要在代码层面finally释放锁

宕机了，部署了微服务代码层面根本没有走到finally这块，没办法保证解锁，这个key没有被删除，
需要有lockKey的过期时间设定

为redis的分布式锁key，增加过期时间，此外，还必须要setnx+过期时间必须同一行

必须规定只能自己删除自己的锁,你不能把别人的锁删除了，防止张冠李戴，1删2，2删3

Redis集群环境下，我们自己写的也不oK直接上RedLock之Redisson落地实现

#### 61 redis内存调整默认查看

一些面试题：

- 生产上你们你们的redis内存设置多少？
- 如何配置、修改redis的内存大小
- 如果内存满了你怎么办？
- redis清理内存的方式？定期删除和惰性删除了解过吗
- redis缓存淘汰策略
- redis的LRU了解过吗？可否手写一个LRU算法
- Redis内存满了怎么办？Redis默认内存多少？在哪里查看？如何设置修改？

**查看Redis最大占用内存**

配置文件redis.conf的maxmemory参数，maxmemory是bytes字节类型，注意转换。

**redis默认内存多少可以用？**

如果不设置最大内存大小或者设置最大内存大小为0，在64位操作系统下不限制内存大小，在32位操作系统下最多使用3GB内存

**一般生产上你如何配置？**

一般推荐Redis设置内存为最大物理内存的四分之三。

**如何修改redis内存设置**

- 修改配置文件redis.conf的maxmemory参数，如：maxmemory 104857600
- 通过命令修改
  - config set maxmemory 1024
  - config get maxmemory

**什么命令查看redis内存使用情况?**

- info memory

#### 62 redis打满内存OOM

真要打满了会怎么样？如果Redis内存使用超出了设置的最大值会怎样?

我改改配置，故意把最大值设为1个

```java
127.0.0.1:6379> config get maxmemory
1) "maxmemory"
2) "0"
127.0.0.1:6379> config set maxmemory 1
OK
127.0.0.1:6379> set a 123
(error) OOM command not allowed when used memory > 'maxmemory'.
```

没有加上过期时间就会导致数据写满maxmemory为了避免类似情况，引出下一节内存淘汰策略

#### 63 redis内存淘汰策略

往redis里写的数据是怎么没了的？

**redis过期键的删除策略**

如果一个键是过期的，那它到了过期时间之后是不是马上就从内存中被被删除呢？

如果回答yes，你自己走还是面试官送你？

如果不是，那过期后到底什么时候被删除呢？？是个什么操作？

**三种不同的删除策略**

- 定时删除 - 总结：对CPU不友好，用处理器性能换取存储空间（拿时间换空间）
- 惰性删除 - 总结：对memory不友好，用存储空间换取处理器性能（拿空间换时间）
- 上面两种方案都走极端 - 定期删除 - 定期抽样key，判断是否过期（存在漏网之鱼）

**定时删除**

Redis不可能时时刻刻遍历所有被设置了生存时间的key，来检测数据是否已经到达过期时间，然后对它进行删除。

立即删除能保证内存中数据的最大新鲜度，因为它保证过期键值会在过期后马上被删除，其所占用的内存也会随之释放。但是立即删除对cpu是最不友好的。因为删除操作会占用cpu的时间，如果刚好碰上了cpu很忙的时候，比如正在做交集或排序等计算的时候，就会给cpu造成额外的压力，让CPU心累，时时需要删除，忙死。

这会产生大量的性能消耗，同时也会影响数据的读取操作。

**惰性删除**

数据到达过期时间，不做处理。等下次访问该数据时，

如果未过期，返回数据；

发现已过期，删除，返回不存在。

惰性删除策略的缺点是，它对内存是最不友好的。

如果一个键已经过期，而这个键又仍然保留在数据库中，那么只要这个过期键不被删除，它所占用的内存就不会释放。

在使用惰性删除策略时，如果数据库中有非常多的过期键，而这些过期键又恰好没有被访问到的话，那么它们也许永远也不会被删除（除非用户手动执行FLUSHDB），我们甚至可以将这种情况看作是一种内存泄漏 – 无用的垃圾数据占用了大量的内存，而服务器却不会自己去释放它们，这对于运行状态非常依赖于内存的Redis服务器来说，肯定不是一个好消息。

**定期删除**

定期删除策略是前两种策略的折中：

定期删除策略每隔一段时间执行一次删除过期键操作，并通过限制删除操作执行的时长和频率来减少删除操作对CPU时间的影响。

周期性轮询Redis库中的时效性数据，来用随机抽取的策略，利用过期数据占比的方式控制删除频度

特点1：CPU性能占用设置有峰值，检测频度可自定义设置

特点2：内存压力不是很大，长期占用内存的冷数据会被持续清理

总结：周期性抽查存储空间（随机抽查，重点抽查）

举例：

redis默认每个100ms检查，是否有过期的key，有过期key则删除。注意：redis不是每隔100ms将所有的key检查一次而是随机抽取进行检查(如果每隔100ms，全部key进行检查，redis直接进去ICU)。因此，如果只采用定期删除策略，会导致很多key到时间没有删除。

定期删除策略的难点是确定删除操作执行的时长和频率:如果删除操作执行得太频繁，或者执行的时间太长，定期删除策略就会退化成定时删除策略，以至于将CPU时间过多地消耗在删除过期键上面。如果删除操作执行得太少，或者执行的时间太短，定期删除策略又会和惰性删除束略一样，出现浪费内存的情况。因此，如果采用定期删除策略的话，服务器必须根据情况，合理地设置删除操作的执行时长和执行频率。

上述步骤都过堂了，还有漏洞吗？

1. 定期删除时，从来没有被抽查到
2. 惰性删除时，也从来没有被点中使用过

上述2步骤====>大量过期的key堆积在内存中，导致redis内存空间紧张或者很快耗尽

必须要有一个更好的兜底方案

**内存淘汰策略登场**（Redis 6.0.8版本）

> LRU，即：最近最少使用淘汰算法（Least Recently Used）。LRU是淘汰最长时间没有被使用的页面。
>
> LFU，即：最不经常使用淘汰算法（Least Frequently Used）。LFU是淘汰一段时间内，使用次数最少的页面。

- noeviction：不会驱逐任何key
- volatile-lfu：对所有设置了过期时间的key使用LFU算法进行删除
- volatile-Iru：对所有设置了过期时间的key使用LRU算法进行删除
- volatile-random：对所有设置了过期时间的key随机删除
- volatile-ttl：删除马上要过期的key
- allkeys-lfu：对所有key使用LFU算法进行删除
- allkeys-lru：对所有key使用LRU算法进行删除(工作使用)
- allkeys-random：对所有key随机删除

上面总结

- 2*4得8
- 2个维度
  - 过期键中筛选
  - 所有键中筛选
- 4个方面
  - LRU
  - LFU
  - random
  - ttl（Time To Live）
- 8个选项

如何配置

- 命令
  - config set maxmemory-policy noeviction
  - config get maxmemory
- 配置文件 - 配置文件redis.conf的maxmemory-policy参数

#### 64 lru算法简介

Redis的LRU了解过吗？可否手写一个LRU算法

是什么

LRU是Least Recently Used的缩写，即最近最少使用，是一种常用的页面置换算法，选择最近最久未使用的数据予以淘汰。

算法来源

[LeetCode - Medium - 146. LRU Cache](https://leetcode.com/problems/lru-cache/)

#### 65 lru的思想

设计思想

1. 所谓缓存，必须要有读+写两个操作，按照命中率的思路考虑，写操作+读操作时间复杂度都需要为O(1)

2. 特性要求

   1. 必须要有顺序之分，一区分最近使用的和很久没有使用的数据排序。
   2. 写和读操作一次搞定。
   3. 如果容量(坑位)满了要删除最不长用的数据，每次新访问还要把新的数据插入到队头(按照业务你自己设定左右那一边是队头)

   

查找快、插入快、删除快，且还需要先后排序---------->什么样的数据结构可以满足这个问题？

你是否可以在O(1)时间复杂度内完成这两种操作？

如果一次就可以找到，你觉得什么数据结构最合适？

答案：LRU的算法核心是哈希链表

编码手写如何实现LRU

本质就是HashMap + DoubleLinkedList

时间复杂度是O(1)，哈希表+双向链表的结合体

#### 66 巧用LinkedHashMap完成lru算法

```java
/**
 * 继承LinkedHashMap
 */
public class LRUCacheDemo<K, V> extends LinkedHashMap<K, V> {
    private int capcity;
    
    public LRUCacheDemo(int capcity) {
        // 参数说明：容量，负载因子，访问顺序：true--按照访问频率排序；false--按照插入顺序排序
        super(capcity, 0.75F, true);
        this.capcity = capcity;
    }
    
    // 重写父类 移除最少使用元素 的方法
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > capcity;
    }
    
    public static void main() {
        LRUCacheDemo lru = new LRUCacheDemo();
        lru.put(1, "a");
        lru.put(2, "b");
        lru.put(3, "c");
        System.out.println(lru.keySet()); // [1,2,3] [1,2,3]
        
        lru.put(4, "d");
        System.out.println(lru.keySet()); // [2,3,4] [2,3,4]
        
        lru.put(3, "c");
        System.out.println(lru.keySet()); // [2,4,3] [2,3,4]
        lru.put(3, "c");
        System.out.println(lru.keySet()); // [2,4,3] [2,3,4]
        lru.put(5, "x");
        System.out.println(lru.keySet()); // [4,3,5] [3,4,5]
    }
}
```

#### 67 手写LRU

哈希表 + 双向链表

```java
/**
 * 手写LRU
 */
public class LRUCache {
    
    // map负责查找，构建一个虚拟的双向链表，它里面安装的就是一个Node节点，作为数据载体
    // 1.构造一个Node节点作为数据载体
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;
        
       	public Node() {
            this.prev = this.next = null;
        }
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }
    }
    
    // 2.构造一个双向链表，用于存放Node节点
    class DoubleLinkedList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;
        
        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.prev = head;
        }
        
        // 添加节点
        public void addHead(Node<K, V> node) {
            // 当前节点后继指针指向头节点后继节点（尾节点）
            node.next = head.next;
            // 尾节点前驱指针指向当前节点
            head.next.prev = node;
            // 头节点后继指针指向当前节点
            head.next = node;
            // 当前节点前驱指针指向头节点
            node.prev = head;
        }
        
        // 删除节点
        public void removeNode(Node<K, V> node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            // 当前节点的指针全部指向空，相当于出队
            node.prev = null;
            node.next = null;
        }
        
        // 获得最后一个节点
        public Node getLast() {
            return tail.prev;
        }
    }
    
    // 容量大小
    private int cacheSize;
    Map<Integer, Node<Integer, Integer>> map;
    DoubleLinkedList<Integer, Integer> doubleLinkedList;
    
    public LRUCache(int cacheSize) {
        this.cacheSize= cacheSize；
        map = new HashMap<>();
        doubleLinkedList = new DoubleLinkedList<>();
    }
    
    
    public int get(int key) {
    	if (!map.containsKey(key)) {
            reutrn -1;
        }
        
        Node<Integer, Integer> node = map.get(key);
        // 频繁使用（读和写）的现从链表中删除，再加到队头
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);
    }
    
    // 更新和新增
    public void put(int key, int value) {
        // 更新节点
        if (map.contains(key)) {
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            map.put(key, node);
            
            doubleLinkedList.removeNode(node);
       		doubleLinkedList.addHead(node);
        } else {
            // 容量达到最大值
            if (map.size() == cacheSize) {
                Node<Integer, Integer> lastNode = doubleLinkedList.getLast();
                map.remove(lastNode.key);
                doubleLinkedList.removeNode(lastNode);
            }
                    
            // 新增节点
            Node<Integer, Integer> newNode = new Node<>(key, value)
            map.put(key, newNode);
            doubleLinkedList.addHead(newNode);
        }
    }
    
    public static void main(String[] args) {
        // 测试同上
    }
}
```

