###### tags: `JAVA`
# Java Quick Review

## Modifier 
```java
/**
  * declaration
  */
access-specifier storage returnType function(parameters)
```
### access-specifier
1. public
2. protected
3. private
4. default (No modifier)

access-specifier for class 
- CLASS可见表示其它类可以用这个类创建实例对象

access-specifier for member
- MEMBER可见表示其它类可以用这个类的实例对象访问到该成员；

### storage
1. static 
2. final
3. abstract
- a `static` member can be shared by all objects of classA and objects which inherit from classA

## Access Control for the Class
1. public    : Accessed By Any classes
2. default   : Accessed By the Classes Within the same package 
3. protected : **Accessed By the classes and the sub-classes IN THE SAME PACAKAGE** (e.g. `clone()`)
4. private   : Accessed only By members(fields and method) in same class

### Visibility Within pakacge

| Access Specifier              |  Accessible to a SUBCLASS inside A same package | Accessible to all OTHER CLASSES in the same package |
| ---                           |     ---                                         |     ---                                             |
|  default(no modifier)         |     Yes                                         |     Yes                                             |
|  public                       |     Yes                                         |     Yes                                             |
|  protected                    |     Yes                                         |     Yes                                             |
|  private                      |     No                                          |     No                                              | 

- Use `default` when classes and `public void main()` inside the same package  


### Visibility Outside the class's package

| Access Specifier      |  Accessible to a SUBCLASS outside the same package | Accessible to all OTHER CLASSES outside the same package |
| ----                  |     ---                                            |     ----                                                 |
|  default(no modifier) |     No                                             |     No                                                   |
|  public               |     Yes                                            |     Yes                                                  |
|  protected            |     Yes                                            |     No                                                   |
|  private              |     No                                             |     No                                                   | 

- **The derived can not access base's private member but it can be accessed via public method or protected method of base class**
- **`protected`通常都用來修飾MEMBERS(e.g. methods)，表示`protected members`在繼承時對於其Subclass是可見的，但是這個訪問修飾符對於Class沒什麼意義。**

### Access Specifier for a Class 
1. Do not declare more than one `public` class in same file   
2. `pubic` class name should be the same as file name. e.g. filename : `X.java` => class name : `public class X`  
3. **non-public class only can be accessed by same package's classes**.  
   > `public` class A: Any Classes can inherit from it and uses it(and its methods)   
   > `private` class A: Members in class A can only be accessed by method **in the same class**, cant not be the base class   
   > `protected` class A: Members in class A only can be accessed by same package's classes, and other classes from other packages can inherit from it, but can't access the protected members in class A    
4. No access-specifier : Only classes in same package can inherit from it

### Protected Member in the Base Class

Protected members in a class may ony be accessed by methods in a subclass and the methods in the same package.
```java
public class GradedActivity2
{
    /**
      * <p> protected member {@code score}  
      *     can be accessed by subclass  </p>
      */
    protected double score; 
    
    public void setScore(double s)
    {
       score = s;
    }
}

public class Exam extends GradeActivity2
{
    //...
    public Exam(int question, int miss)
    {
        //...
        int s = (question - miss)*2;
        setScore(s);
        // The derived class Exam access the portect member of base 
    }
}
```

## [Inheritance](https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%9F%BA%E7%A1%80.md#%E5%85%AD%E7%BB%A7%E6%89%BF) 

一個良好的OOP/OOD會隱藏所有實現(implementations)細節,把它的 API 與它的implementation清晰地隔離開  
- 模組之间只透過它们的 API 溝通，一個模組不需要知道其他模組的内部工作情況，我們把概念稱作訊息隱藏或封装
  > 因此Access-Control應盡可能地使每個Class或者Member不被外界訪問


### `Is-A` Relationship Does Not Work In Reverse
**如果sub的方法Override了Base的方法，則sub中該方法的訪問級別不允许低于base的訪問級別**  
為了確保可以使用base實例的地方都可以使用sub實例去代替，也就是满足里氏替换原则  
```java
/**
  *  Big ------------------------- Small
  * GradeActivity--> extends --> FinalExam
  */
GradedActivity activity = new GradedActivity();

/** 
  * <p> CASE 1 </p>
  * SMALL <---- BIG 
  **/
FinalExam exam = activity;    // ERROR!

/**
  * <p> CASE 2 </p>
  * The Compiler Works BUT run-time fials
  */
GradedActivity activity = new GradedActivity();
FinalExam exam = (FinalExam) activity;    // ERROR!
```

## The `instanceof` Operator
To determine whether an object is an instance of a particular class.

```java
/**
  * <pre> refObj instanceof ClassName </pre>
  */
GradedActivity activity = new GradedActivity();
FinalExam exam = new FinalExam();
if (exam instanceof GradedActivity) // it will return true
   System.out.println("Yes, activity is a GradedActivity.");
else
   System.out.println("No, activity is not a GradedActivity.");

```

## `static`
A `static` variable is shared by everyone in this class or it's subclass  

A `static` method access static variable directly without allocating the object of that class  

```java
System.out.println();
/**
  * class name : System 
  * static member : out
  * static method : println
  */
```

Always Call static member with `.`
```java
ClassName.staticMember;
```

Static member As global variable
```java
/** 
 * {@code count} would never reset, and shared by account's objs
 * keep increasing when {@code Account} is called
 * and every different account objs have its sunique SID
 */
class Account{
  static long count = 0;
  private long SID;
  Account(){
    count++;
    SID=count
  }
}
```
1. fields and methods in a class can be used only after `new` a Account object
2. static methods can't access non-static member (fields/methods)

```java 
// What static method could do
Public Employee{
  static int count;
  //...
  public static int getCount(){
    return count; 
  }
  //...
}

/**
  * <p> We dont have to new a object 
  *     to use {@code static} methods/fields </P.
Employee.getCount(); 

//Else if obj of Employee exists say e1 then
e1.getCount();
```

## Garbage Collection 
call method `finalize` to release the memory while doing Termination housekeeping in case of **Memory Leaks**

1. method `finalize` returns `void`
2. method `finalize` may not be executed even program reachs it's end

## `final` keyword
```java
final dataType VAR = value;
/**
  *{@code VAR} usually represents as UPPERCASE
  *{@code final} as {@code const} in c/cpp 
  */
```

Same as in Cpp program
- `final` class : can't be a base class
- `final` method: can't be overriden

### Usage of `final`

```java
/**
  * priciple of least privilege ( ME is DA BOSS )
  * <p> `final` variable should always explictly declared </p>
  */
class increment
{
  private int total = 0;
  private final int INCREMENT;

  public Increment(int incrementValue){
    INCREMENT = incrementValue; 
  }
}
```

## Package 
Es ist wie `.h` in Cpp

Creat a package in bash
```console
javac -d . filename.java
```
Name of package
```java
//LARGE-----------SMALL
tw.network.nkust.csie;
```
Import the package
```java
import tw.network.nkust.csie;
iport java.util.*
```
- `*` all public classes in java.util can be used..

### namespace of JAVA
To avoid name conflit in different files

```java
package practice;
public class Increment 
{
  //.....
}
```
```java
/**
  * <p> import to test.java </p>
  */
import practice.Increment;
pubic class test{
  //...
}
```

Compiler uses **class loader** to find the package and it search the package first from
1. search from JDK's standard liberary  , if not
2. search optional package , if not
3. search from classspath (package list)


## Abstract

### Polymorphism
```java
/**
  * <p> There are numbers way `encrypt()` methods (polymorphism) </p>
  */
public abstract Encryption{

 abstract void Encrypt()
}

/**
  *Encrupt()
  *  '---------> Caser 
  *  '---------> ASE 
  *  '---------> RSA
  * so Caser , ASE and RSA have their own encryption, that is why we must override the method encrypt() from class Encryption
  */
```
- When an `abstract` method appears in the class, the method must be overridden in the subclass
- When a Class Contains an abstract method, you cannot create an instance of the class.
- An abstract class is not instantiated, but other classes extend it.
- An abstract class method has no body and must be overriden in a subclass


```java
/**
  * A Abstract Class
  * <pre> AccessSpecifier abstract class ClassName </pre>
  */
/*public or protected*/ abstract class Name{
  
  // A Abstract Method
  /*public or protected*/ abstract returnType methods(parameters);
  
  // A Abstract VARIABLE
  /*final*/ dataType VAR = VALUE ;
```


# Interface 

- Interface是Abstract的延伸，在 Java 8 之前，它可以看成是一個完全抽象的類，也就是說它不能有任何的方法實現。 

- 從 Java 8 開始, Interface也可以擁有默認(Default)方法實現，這是因為不支持默認方法的Interface的維護成本太高了。
  > 在 Java 8 之前，如果一個Interface想要添加新的方法，那麼要修改所有實現了該接口的類，讓它們都實現新增的方法。

- Member of Interface : 
  > `public` by default，並且不允許定義為`private` 或者`protected`的Member
  >> **從 Java 9 開始，允許將方法定義為 private，這樣就能定義某些復用的代碼又不會把方法暴露出去。**
  > fields' keyword `static` and `final` By default

## Implementing Multiple Interface

```java
public class ACLASS implements INTERFACE1,
                               INTERFACE2,
                               INTERFACE3
```

For example
```java
/**
  * Deposit implements Exchange and BackAccount
  *                +-->(I)Exchange            
  *      Deposit---|     
  *                +-->(I)BackAccount
  */
interface BankAccount
{
      /**
        * Fields in interface 
        * are {@code final} type 
        */
      double saveRate = 2.2.5;
      abstract void getInterest();
}

interface Exchange
{
      abstract void setExchange(double e);
}

/**
  * Implementation
  */
class Deposit implements BankAccount, Exchange{
    private int savBalance ;
    private double exchange;
    
    public Deposit(int s){
        savBalance = s;
    }
    
    @Overide
    public void getInterest(){
        //..
    }
    
    @Overide
    public void setExchange(double e){
        //..
    }
}
```

## Interface Vs Abstract Class 
[More Details](https://stackoverflow.com/questions/1913098/what-is-the-difference-between-an-interface-and-abstract-class)  

- abstract  : only inherit from one class
- interface : mutiple inheritations 

```java
abstract class Animal{
  //...
}

interface Pet{
  //..
}
public class Dog extends Animal implements Pet{
  //...
}
```

```java
//多重繼承
Interface derived extends InterfaceBase1, InterfaceBase2{
  // create new static variable , new abstract methods
}

// implementation 
class A implements interfacebase1, interfacebase2{
  // implements methods of intefacebase1 and interfacebase2 
}
```

- Abstract classes can have constants, members, method stubs (methods without a body) and defined methods, whereas interfaces can only have constants and methods stubs.
- Methods and members of an abstract class can be defined with any visibility, whereas** all methods of an interface must be defined as public (they are defined public by default)**.
- When inheriting an abstract class, a concrete child class must define the abstract methods, whereas **an abstract class can extend another abstract class and abstract methods from the parent class don't have to be defined.**
- Similarly, **an interface extending another interface is not responsible for implementing methods from the parent interface.** This is because interfaces cannot define any implementation.
- **A child class can only extend a single class (abstract or concrete)**, whereas **an interface can extend or a class can implement multiple other interfaces**.

- **A child class can define abstract methods with the same or less restrictive visibility**, whereas a class implementing an interface must define the methods with the exact same visibility (`public`).

- `Absract`提供了一種`IS-A`關係，需滿足里式替換原則，即Sub的object必须能夠替換掉所有base的對象。  
- `INTERFACE`更像是一種`LIKE-A(HAS-A)`關係，只提供方法的抽象實作，並不要求INTERFACE和IMPLEMENTATIONS具有`IS-A`關係。

從使用上来看，A Class可以implements multiple `Interface`s，但是不能extends多個`Abstract`s。
- `Interface`的Keyword只能是`static`和`final`类型的，而`Abstract`的Keyword無此限制。
- `Interface`的Member只能是`public`的，而`Abstract`的Member可以有不同的Access Controls。

### 判斷依據

Interface  
- 需要讓UNRELATED的Classes都實現一個某種(規範)方法
  > e.g. 不相關的Classes都可以實現Comparable界面中的`compareTo()`方法；
- 需要使用多重繼承
  > `Interface a extends b, c`

Abstract  
- RELATED : 需要被SHARED在幾個RELATED的CLASSES 
- ACCESS CONTROL : 需要能控制繼承來的成員的ACCESS CONTROL，而不是都為`public`
- NON-STATIC AND NON-FINAL : 需要繼承非STATIC和非FINAL FIELDS

IN MANY CASES，INTERFACE優先於ABSTRACT。因為INTERFACE沒有ABSTRACT嚴格的CLASS層次結構要求，可以靈活地為一個CLASS添加BEHAIOUR。
> Since Java 8，Interface也可以有DEFAULT的方法實作，使得修改INTERFACE的COST也變的很低    



## Interface and Inheritance Together 

Just like `pure virtual function` in cpp
- It contains only abstract methods. 
- **An interface cannot be instantiated**

`interface` is used instead of the keyword `class`
- The methods that are specified in an interface have no bodies
- Only headers that are terminated by semicolons 

All declarations in interface class are `public abstract` methods also variables are `public` , `static` , `final` by default  
so we dont have to write down these keywords or specifiers in interface class  

```java
// methods in interface class are
public   returnType method(/** @param **/)
abstract returnType method(/** @param **/)

// attributs in interface class are
public returnType attribut;
static returnType attribut;
final  returnType ATTRIBUTE;
```

```java
abstract class GraphicsObj{
  // A class Point
  protected Point Origin;
  
  public GraphicsObj{
    Origin = new Point(0,0);
  }
  
  public void moveTo(int newX, int newY){
    Origin.x = newX;
    Origin.y = newY;
  }
  
  // this should be implemented by the derived class
  public abstract void draw(); 
  
  // Garbage Collection
  protected void finalize() throw Throwable{
    Origin = null;
    super.finalize();
  }
}

/** 
  * <p> A interface </p>
  */
interface Paintable
{
  public void fillcolor(String color);
  public double getArea();
}
class Rectangle2 extends GrphicsObj implements Paintable
{
    public void draw(){
      //..
    }
    
    /* Implementation of INTERFACE */
    @OVerride
    public void fillColor(stirng Color){
      //...
    }
    @Override
    public double getArea(){
      //...
    }
}
```

## `default` Methods since Java 8

A default method is an interface method that has a body.
```java
public interface Displayable
{
    default void display()
    {
        System.out.println("This ...");
    }
}
```

## `SUPER`
Sub class一定會call Constructor from baseclass來完成初始化(一般是call default Constructor of baseclass)

BUT
如果子類需要調用父類其它Consturctors，就需要用到`super()`了   
當子類`@override`了父類的某個方法，可以通過使用`super`來reference該方法   

## `override` and `overload`

> 當Sub class實作了一個與base class在方法聲明上完全相同的一個方法。

為了滿足L式替換原則，Override有以下三個限制：
- `Access-specifier` : Sub方法的訪問權限必須大於等於父類方法；
- `return` : Sub方法的return type必須是父類方法返回類型或為其子類型
- `throws` : Sub方法拋出的異常類型必須是父類拋出異常類型或為其子類型
> 使用Annotation `@Override` ，可以讓Compiler幫忙檢查是否滿足上面的三個限制條件。

For example，SubClass 為 SuperClass 的子類，SubClass overrides SuperClass 的`func()`方法
```java
class SuperClass {
    protected List<Integer> func() throws Throwable {
        return new ArrayList<>();
    }
}

class SubClass extends SuperClass {
    @Override
    public ArrayList<Integer> func() throws Exception {
        return new ArrayList<>();
    }
}
```
- sub方法access control為`public`，大於父類的`protected`
- sub的`@return`為`ArrayList<Integer>`，是父類`@return` `List<Integer>`的子類。
- 子類拋出`@trhows`的異常類型為`Exception`，是父類拋出異常`Throwable`的子類。


## Dynamic Binding 

When a superclass variable references a subclass object, a potential problem exists. 
- If the subclass has overridden a method in the superclass, and an instance makes a call to that method?
  > Which Method it will call ? Method from superclass or subclass ?

Unlike Static Binding (it calls functions at the compiler time) dynamic Binding at comipler time only checks the type of object if it can be called or not

```java
class Employee{
  public static void main(String args[])
  {
    Employee EmployeeA = new Employee();
    Employee EngineerB = new Engineer();
    EmployeeA.getSalary(); 
    /**
      * getSalary() in Class Engineer or in Class Employee?
      * Dynamic Binding would check that for us 
      */
    EngineerB.getSalary();
  }
}
```

```java
class Employee{
  public static void main()
  {
    Employee e = new Manager();
    e.getSalary();
    e.doAccounting(); // Compiler Error (e doesn't have doAccounting methods)
  }
}
```

#### At Comiple-Time
- [x] 1. Check if `Employee` exists 
- [x] 2. Check if `Manager` is subclass of `Employee`
- [x] 3. Check if `Employee e` has `getSalary()` method
- [x] 4. Check if `Employee e` has `doAccounting()` method

#### At Run-Time (Check Overriden Function and References)
1. `Employee e` references to an `Manager` class;
2. (Check the Overriden Function) `Manager` class has its own `getSalary()` method
   > Invoke Manager's `getSalary()` method


`IS-A` RELATHIOSHIP中呼叫method時，最先從該類別中查找看是否有對應的方法，如果没有再到Base Class查看，CHECK是否從baseclass繼承,都沒有就要進行強至轉型了   
this.func(this)
super.func(this)
this.func(super)
super.func(super)
```java
/**
  * Hierarchie Hierarchy
  *  A
  *  '--> B
  *       '-->C
  *           '--> D
  */
class A{
    public void show(A obj) {
        System.out.println("A.show(A)");
    }

    public void show(C obj) {
        System.out.println("A.show(C)");
    }
}

class B extends A {

    @Override
    public void show(A obj) {
        System.out.println("B.show(A)");
    }
}

class C extends B {
}

class D extends C {
}
public static void main(String[] args) {

    A a = new A();
    B b = new B();
    C c = new C();
    D d = new D();

    /**
      * <p> 在 A 中存在該方法 
      */ 
    a.show(a); // A.show(A)
    
    /** 
      * <p> 在A中不存在 
      *     {@code show(B obj)}
      *     將 B convert to A </p>
      */
    a.show(b); // A.show(A)
    
    /**
      * <p> 在B中存在繼承A的{@code show(C obj)} </p>
      */
    b.show(c); // A.show(C)
    
    /** 
      *<p> 在 B 中不存在{@code show(D obj)}
      *    <strong> 但是存在繼承A的{@code show(C obj)}
      *             且 D extends C </strong>
      *    Convert D obj to C obj </p>
    b.show(d); // A.show(C)

    /**
      * {@code new B()} Reference to B object
      */
    A ba = new B();
    ba.show(c); // A.show(C)
    ba.show(d); // A.show(C)
}
```

## Overload

指在某Class內存在多個相同的Method名稱，但是在該Method的Parameter上數量,類型或順序At Least有一個不同  
- 返回類型不同，其它都相同不能稱作Overload
```java
class OverloadingExample {
    
    public void show(int x) {
        System.out.println(x);
    }

    public void show(int x, String y) {
        System.out.println(x + " " + y);
    }
}

public static void main(String[] args) {
    OverloadingExample example = new OverloadingExample();
    example.show(1);
    example.show(1, "2");
}
```
## [Java Reaction](https://github.com/CyC2018/CS-Notes/blob/456ff183d550baba9f1f5f54a3f736a5089f1cb2/notes/Java%20%E5%9F%BA%E7%A1%80.md#%E4%B8%83%E5%8F%8D%E5%B0%84)

## Throwable
[Question](https://www.journaldev.com/2167/java-exception-interview-questions-and-answers)

## [Generic](https://ethan-imagination.blogspot.com/2018/11/javase-gettingstarted-017.html)

```java
BaseType<Type> obj = New BaseType<Type>();
```
- Type不可為Primitive Type

### Generic Interface

```java
interface Info<T> { //在接口上定義泛型
    public T getVar(); //定義抽象方法，抽象方法的返回值就是泛型型態
    public void setVar(T x);
}

/** Info Impl 並非Generic **/
class InfoImpl implements Info<String> { //定義泛型介面的子類別
    private String var;
    
    public InfoImpl(String var){
        this.setVar(var);
    }
    //...    
}
```

### Generic Interface and Generic Implementation

```java
interface Info<T> { 
    public T getVar(); 
    public void setVar(T x);
}

class InfoImpl<T> implements Info<T> { //定義泛型介面的子類別
    private T var; 
    
    public InfoImpl(T var){
        this.setVar(var);
    }
    
    //...
}

class TestGeneric4 {
    public static void main(String[] args){
        InfoImpl<String> i = new InfoImpl<String>("Java generic interface 2.");
        System.out.println(i.getVar());
    }
}
```

### Generic Method

```java
class TestGeneric5 {
    
    static <T> void staticMethod(T a){
        System.out.println(a.getClass().getName() + " = " + a);
    }
    
    // 普通方法
    <T> void otherMethod(T a){
        System.out.println(a.getClass().getName() + " = " + a);
    }
    
    public static void main(String[] args){
        
        // 使用靜態方法
        TestGeneric5.staticMethod(13.5F);                   //使用方法一
        TestGeneric5.<String>staticMethod("Java is good!"); //使用方法二
        
        // 使用普通方法
        TestGeneric5 obj = new TestGeneric5();
        obj.otherMethod((byte)23);                  //使用方法一
        obj.<Integer>otherMethod(new Integer(123)); //使用方法二
    }
}
```
- Java中的泛型是什麽? 使用泛型的好處是什麽?
  > 那些擁有Java1.4或更早版本的開发背景的人都知道，在集合中存儲對象並在使用前進行類型轉換（強制轉換）是多麽的不方便。  
  > 泛型防止了那種情況的发生。它提供了編譯期的類型安全，確保你只能把正確類型的對象放入集合中，避免了在運行時出現`ClassCastException`  
- Java的泛型是如何工作的 ? 什麽是類型擦除 ?
  > 泛型是通過類型擦除來實現的，編譯器在編譯時擦除了所有類型相關的信息，所以在運行時不存在任何類型相關的信息。
  > For example, `List<String>`在運行時僅用一個`List`來表示。這樣做的目的，是確保能和Java 5之前的版本開发二進制類庫進行兼容。  
  > 你無法在run time時訪問到類型參數，因為Compiler已經把泛型類型轉換成了原始類型。
- 什麽是泛型中的限定通配符和非限定通配符 ?
  > 這是另一個非常流行的Java泛型面試題。限定通配符對類型進行了限制。
  > 有兩種限定通配符，一種是`<? extends T>`它通過確保類型必須是T的subclass來設定類型的上界，另一種是`<? super T>`它通過確保類型必須是T的baseclass來設定類型的下界  
  >> 泛型類型必須用限定內的類型來進行初始化，否則會導致Compiler Error。
  >> 另一方面`<?>`表示了非限定通配符，因為`<?>`可以用任意類型來替代
- `List<? extends T>`和`List <? super T>`之間有什麽區別 ?
   > 這兩個List的Declaration都是限定通配符的例子，`List<? extends T>`可以接受任何繼承自T的類型的List，而`List<? super T>`可以接受任何T的父類構成的List。
   > 例如`List<? extends Number>`可以接受`List<Integer>`或`List<Float>`
- 一個泛型方法，讓它能接受泛型參數並返回泛型類型?
  > 你需要用泛型類型來替代Primitive Type
  >> 比如使用`T`, `E` ,`K`,or `V`等被廣泛認可的類型占位符。
  > 最簡單的情況下，一個泛型方法可能會像這樣:
  ```java
  public V put(K key, V value) {
     return cache.put(key, value);
  }
  ```
-  使用泛型來實現LRU緩存?
   > `LinkedHashMap`可以用來實現固定大小的LRU Buffer，當LRU Buffer已經滿了的時候，它會把最老的鍵值對移出Buffer  
   > `LinkedHashMap`提供了一個稱為`removeEldestEntry()`的Method，該方法會被`put()`和`putAll()`呼叫並刪除最老的key-value pair  
- 你可以把`List<String>`傳遞給一個接受`List<Object>`參數的方法嗎？
  > 乍看之下`String`是一種`Object`，所以`List<String>`應當可以用在需要`List<Object>`的地方，但是事實並非如此。真這樣做的話會導致編譯錯誤。  
  > 因為`List<Object>`可以存儲任何類型的對象包括`String`, `Integer`等等，而`List<String>`卻只能用來存儲`String`s 
  ```java
  List<Object> objectList;
  List<String> stringList;
  objectList = stringList;  //compilation error incompatible types
  ```
- Array中可以用泛型嗎?
  > Array事實上並不支持泛型  
  >> 所以Joshua Bloch在Effective Java一書中建議使用`List`來代替`Array`，因為`List`可以提供Compiler Time的類型安全保證，而Array卻不能。

- 如何阻止Java中的類型未檢查的警告?
  > 如果你把泛型和原始類型混合起來使用，例如下
  > Java 5的javac編譯器會產生類型未檢查的警告 e.g. `List<String> rawList = new ArrayList()`


### Wildcard

`?` 可輸入任何類型的資料型態
```java
/**
  *<p> Say if we want p reference to all Number object
  *</p>
  */
Point<? extends Number> p = null;
p = new Point<Integer>;  // reference to Integer object
p = new Point<Float>;    // reference to Float object;
```

### WildCard And Bound

For A Case with out Bound 

```java
//Lev 1
class Food{}

//Lev 2
class Fruit extends Food{}
class Meat extends Food{}

//Lev 3
class Apple extends Fruit{}
class Banana extends Fruit{}
class Pork extends Meat{}
class Beef extends Meat{}

//Lev 4
class RedApple extends Apple{}
class GreenApple extends Apple{}

// Generic
class Plate<T> {
    private T item;
    public Plate(T t){item=t;}
    public void set(T t){item=t;}
    public T get(){return item;}
}

/** This will cause erro if we **/
Plate<Fruit> plate = New Plate<Apple>(New Apple());
/** Instead **/

// Upper Bounds Wildcard (suit for getter)
Plate< ? extends Fruit> plateL ;

// Down Bounds Wildcard (suit for setter)    
Plate< ? super Fruit> plateU  ;
```

## Anonymous Inner Classes

**An anonymous inner class is an inner class that has no name.**
- An inner class is a class that is defined inside another class. 

### When To USE?

Sometimes you need a class that is simple, and to be instantiated only once in your code. 

You use the new operator to simultaneously define an anonymous inner class and create an instance of it.
```java
new ClassOrInterfaceName(){
    //...
}
```
1. An anonymous inner class must either implement an interface, or extend another class.
2. If the anonymous inner class extends a superclass, the superclass’s no-arg constructor is called when the object is created.
3. An anonymous inner class must override all of the abstract methods specified by the interface it is implementing, or the superclass it is extending.
4. Because an anonymous inner class’s definition is written inside a method, it can access that method’s local variables, but only if they are declared `final`, or if they are effectively `final`. 
    > (An effectively final variable is a variable whose value is never changed.) A compiler error will result if an anonymous inner class tries to use a variable that is not final, or not effectively final.


```java
public class Example{
    public static void main(String[] args)
    {
        int num;
        
        Scanner keyboard = new Scanner(System.in);
        
        // INNER CLASS
        IntCalculator square = new IntCalculator(){
            public int calculate(int number){
            return number * number;}
        };
        
        System.oout.println("Enter an integer Number: ");
        num = keyboard.nextInt();
        
        // USER INNER CLASS OBJECT
        System.out.println("The square is " + square.calculate(num));
    }
}
```
