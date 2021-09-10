###### tags: `JAVA`
# Java Basic

[Java 基本問題](https://medium.com/%E7%A7%91%E6%8A%80%E7%A0%94%E7%A9%B6%E9%99%A2/java%E9%9D%A2%E8%A9%A6%E9%A1%8C%E6%95%B4%E7%90%86-ed44d4b10437)  
[OOD Principle](https://github.com/maxwolf621/DesignPattern/blob/main/_Principle.md)


## [String](https://github.com/CyC2018/CS-Notes/blob/456ff183d550baba9f1f5f54a3f736a5089f1cb2/notes/Java%20%E5%9F%BA%E7%A1%80.md#%E4%BA%8Cstring)  

* Why String is `final`
* String, StringBuffer and StringBuilder and `synchronized` and thread safe
* String Pool, `new String("abc")` and `String s = "abc"`

## Modifier For Java 
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

#### access-specifier for Class' Member
- 表示某Class's member的可見度
  > 例如: Other Classes可以用這個Class的Instance access到該member

#### Access Control for the Class

Visibility Within pakacge   
| Access Specifier              |  Accessible to a SUBCLASS inside A same package | Accessible to all OTHER CLASSES in the same package |
| ---                           |     ---                                         |     ---                                             |
|  default(no modifier)         |     Yes                                         |     Yes                                             |
|  public                       |     Yes                                         |     Yes                                             |
|  protected                    |     Yes                                         |     Yes                                             |
|  private                      |     No                                          |     No                                              | 
- Use `default` when classes and `public void main()` inside the same package  


**Visibility Outside the class's package**   
| Access Specifier      |  Accessible to a SUBCLASS outside the same package | Accessible to all OTHER CLASSES outside the same package |
| ----                  |     ---                                            |     ----                                                 |
|  default(no modifier) |     No                                             |     No                                                   |
|  public               |     Yes                                            |     Yes                                                  |
|  protected            |     Yes                                            |     No                                                   |
|  private              |     No                                             |     No                                                   | 
- **The derived can not access base's private member but it can be accessed via public method/protected method from base class**
- **`protected`通常都用來修飾MEMBERS(e.g. methods)，表示`protected members`在繼承時對於其Subclass是可見的，但是這個訪問修飾符對於Class沒什麼意義。**

1. Do not declare more than one `public` class in same file   
2. `pubic` class name should be the same as file name. e.g. filename : `X.java` => class name : `public class X`  
3. **non-public class only can be accessed by same package's classes**.  
   > `public` class A: Any Classes can inherit from it and uses it(and its methods) => Accessed By Any classes
   > `private` class A: Members in class A can only be accessed by method **in the same class**, cant not be the base class   
   > `protected` class A: Members in class A only can be accessed by same package's classes or its subclasses, and other classes from other packages can inherit from it    
   >  `default` class A : Only classes in same package can inherit from it => Accessed By the Classes Within the same package

# OOP
[class and object](https://medium.com/@nwyyy/design-pattern%E5%88%9D%E5%BF%83%E8%80%85%E7%AD%86%E8%A8%98-1-95774a905010)   

Object-oriented programming (OOP) is a programming paradigm based on the concept of objects, which can contain data and code: data in the form of fields (often known as attributes or properties), and code, in the form of procedures (often known as methods).

## 物件導向的四個根基

#### Encapsulation(將資料以及行為隱藏起來)  

物件將其本身的資料以及行為 (behaviors) 包裝在物件內部，外界除了透過物件所開放的成員 (ex: 屬性，方法，事件等) 使用物件外，**不需知道物件內部的各種實作細節**
Group all relevant things together. I.e. encapsulation is wrapping/binding up of data and member functions in single unit. 
- In simple, abstraction is hiding the implementation and encapsulation is to hide data.

#### Polymorphism

Polymorphism describes a pattern in object oriented programming in which classes have different functionality while sharing a common interface.

相同性質的Class相同名稱Method的行為，會依物件特性不同而有所不同，這個性質經常出現在介面實作以及抽象類別的覆寫(override)上(an object exhibits different behavior in different situation. In simple way)   
> 例如一個 Car 物件，定義有一個`getEngineProperties()`方法來取得引擎資訊，但 2000 年的車子和 2011 年的車子引擎資訊可能略有不同，而不同車子的引擎也未必相同，所以這些 Car 的子類別都會覆寫`Start()`方法，並且在用戶端存取不同的Car物件的`getEngineProperties()`方法時，會得到不同的結果  

```java
/**
  * <p> There are numbers way `encrypt()` methods 
  *     (polymorphism) </p>
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

#### Abstraction (你只需要知道它可以的功能,不必知道功能怎麼實現的)

- Show only necessary thing to user that he required, not extra information (use `public private, protected`).  
- Abstraction is done when we need to inherit from certain class but do not instantiate the objects of that class.

For example ::   
The user can change the channels, increase the volume, change the brightness etc. He does not have to know about the internal circuitry of the controller to operate. Abstraction is similar to that.

Programming languages such as Java support Abstraction.   
The programmer can implement abstraction using concepts such as `abstract` class and `interface`.     
- Firstly, an abstract class can consist of abstract and non-abstract methods. 
  > A class that extends an abstract class have to provide the implementations for the abstract methods. 
- Secondly, an interface is a collection of abstract methods. 
  > It does not have non-abstract methods. Therefore, the class that implements an interface has to provide the implementations or method definitions to all abstract methods in the interface. 

Overall, abstraction helps to reduce the complexity of the system.

#### Inheritances (相同的功能不用在重實作一遍)
The developer does not have to write the code from the beginning. Instead, he can use the already existing code and develop the rest. 
Therefore, **inheritance allows code reusability.**   

透過繼承的方式，可以複製父物件所有public/protected member (透過存取修飾子設定) 的功能，外界在存取子物件時也可以得到父物件的所有public/protected member的功能,而子物件本身也可以存取到父物件所開放的功能，或是進一步改變父物件的行為(override)

1. **Inheritance enables you to create new classes that re-use, extend and modify the behaviour that is defined in other classes**  
2. **Inheritance is the methodology of using properties and methods of an already existing class in a new class.**   
   > The existing class is the parent or superclass while the new class is the child or subclass. 

**The main difference between abstraction and inheritance is that abstraction allows hiding the internal details and displaying only the functionality to the users, while inheritance allows using properties and methods of an already existing class** [source](https://pediaa.com/what-is-the-difference-between-abstraction-and-inheritance/#:~:text=The%20main%20difference%20between%20abstraction,of%20an%20already%20existing%20class.&text=A%20class%20is%20a%20blueprint,an%20instance%20of%20a%20class.).  

![image](https://user-images.githubusercontent.com/68631186/128846000-d2ae0501-6980-4a35-ad51-91f1a1390918.png)  

一個良好的OOP/OOD會隱藏所有實現(implementations)的**DETAILS**,把它的API與它的implementation清楚地隔離開(decoupling)  
**模組之間只透過他們API進行溝通，一個模組不需要知道其他模組的内部工作情況，我們把概念稱作訊息隱藏或封装**  
> 例如我們利用instance提供的method(e.g setter/getter)來訪問某模組內的某個功能  

## [Inheritance](https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%9F%BA%E7%A1%80.md#%E5%85%AD%E7%BB%A7%E6%89%BF)  
**當sub class的方法Override了Base class，則sub中該方法的access control不能低於base的**, 原因是為了確保可以使用base實例的地方都可以使用sub實例去代替，也就是符合Liskov Substitution Principle
- [Details For Liskov Substitution](https://www.jyt0532.com/2020/03/22/lsp/)  
- [Behaviour and LSP](https://medium.com/@f40507777/%E9%87%8C%E6%B0%8F%E6%9B%BF%E6%8F%9B%E5%8E%9F%E5%89%87-liskov-substitution-principle-adc1650ada53)  
### `Is-A` Relationship Does Not Work In Reverse

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

## `static` keyword

- A `static` Field is shared by everyone in this class or it's subclass  
   > In thread, each thread create its own local cache to share `static` variable  
   > **EACH STATIC Field IN EACH THREAD IS ONLY SHARED BY THAT THREAD**    

- A `static` method can access `static` variable directly without allocating the object(instance) of that class  
  > FOR EXAMPLE
  ```java
  System.out.println();
  /**
    * <li> Class Name: System     </li> 
    * <li> Static Member: out     </li>
    * <li> Static Method: println </li>
    */
  ```
- Always Call static member with `.`
  ```java
  ClassName.staticMember;
  ```
- Static Field As global variable
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

- Non-Static Fields and methods in a class can be used only after `New` a object
  > Static methods can't access non-static member 
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
    * <p> For Static Method,  We dont have to new a object </p>
    */
  Employee.getCount(); 

  /**
    * <p> Else if obj of Employee exists 
    *     ,says e1 then </p>
    */
  e1.getCount();
  ```

## `finalize` keyword  
Like `delete` in Cpp

`finalize` to release the memory while doing Termination housekeeping in case of **Memory Leaks**  
1. method `finalize` returns `void`
2. method `finalize` may not be executed even program reachs it's end

## `final` keyword
Same as in Cpp program   

- `final` class : can't be a base class
- `final` method: can't be overriden
- `final` field : a constant

```java
final dataType VAR = value;
/**
  *{@code VAR} usually represents as UPPERCASE
  *{@code final} as {@code const} in c/cpp 
  */
```

## Package and Name Space 
Java Package ist wie `.h` in Cpp.  
Java Packages are namespaces.  
A Package contains needed class for a project (e.g. `java.util.*` to use `HashMap` ... etc ).   

To Creat a package in bash
```console
javac -d . filename.java
```

The Namespace(package)
```java
//LARGE-----------SMALL
tw.network.nkust.csie;
```

Import (namespace) the package to use
```java
import tw.network.nkust.csie;
iport java.util.*
```
- `*` all public classes in java.util can be used..

Compiler uses **class loader** to find the package and it search the package first from
1. search from JDK's standard liberary  , if not
2. search optional package , if not
3. search from classspath (package list)

There might having same class name existing in our project, For example  
```java
/**
  * For such case
  * we need to specificly declare the variable
  */
import com.method.practice;
import tw.practice;
pubic class test{
    //...
    tw.practice p1 = new tw.practice();
    practice p2 = new practice();
}
```

## Abstract

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


## Interface 
- Interface是Abstract的延伸，Before Java 8，它可以看成是一個完全ABCs(Class only with abstract methods)，也就是說它不能有任何的方法實現

- Since Java 8, Interface新增的`default`方法實作,減少未來程式擴充的維護Cost  
  > Before Java 8，當某個Interface想要新增新的方法，得要修改所有實作(Implement)該Interface的類別(class)，讓它們都實作新增的方法 [MORE DETAILS](https://matthung0807.blogspot.com/2017/09/java-interfacedefault-methods.html)  

- Member in the Interface : 
  > `public` by default，不允許定義為`private` 或者`protected`的成員Members. **Since Java 9，允許將方法定義為`private`，這樣就能定義某些復用的程式又不會把方法暴露出去[Example GeekForGeek](https://www.geeksforgeeks.org/private-methods-java-9-interfaces/)**     
  > Keyword `static` and `final` for fields by default    


### Java 9's interface

[Use Private Method](https://www.codewithjason.com/purpose-private-methods-use/#:~:text=Private%20methods%20are%20useful%20for,called%20outside%20of%20that%20class.)

[Why use Private Method IN OOP](https://stackoverflow.com/questions/2620699/why-private-methods-in-the-object-oriented#:~:text=Private%20methods%20are%20useful%20for,called%20outside%20of%20that%20class.)

Private methods can be implemented static or non-static.  
> This means that in an interface we are able to create private methods to encapsulate code from both `default` and `static` public method signatures.

`private` is what won't be **reimplemented and accessed** by future programmers using your code.  
But private also means things done on the side, so the user don't see it(encapsulation).   
That's why we call the public methods a public interface - it's all the user will see from the outside.   

```
Constant variables
Abstract methods
- Default methods (JAVA 8)
Static methods
+ Private methods
+ Private Static methods
```
These private methods will improve code re-usability inside interfaces and will provide choice to expose only our intended methods implementations to users.   
**THEY are only accessible within that interface only and cannot be accessed or inherited from an interface to another interface or class.**

- Private method in `interface` cannot be `abstract` and no `private` + `abstract` modifiers together.
- Private method can be used(called) only inside `interface` and other `static` and non-static methods in the `interface`.
- Private non-static methods cannot be used inside private static methods. (Same as `static` usage)
> We should use `private` modifier to define these methods and no lesser accessibility than private modifier.

### Benefits of Private Methods in Interfaces (使界面更有OOP精神=>封裝)

`interface`s are able to use private methods to hide details on implementation from classes that implement the interface. 
> As a result, one of the main benefits of having these in `interface`s is **encapsulation**.

Another benefit is (as with private methods in general) that there is less duplication and more re-usable code added to interfaces for methods with similar functionality.



## Interface Vs Abstract Class 
[More Details](https://stackoverflow.com/questions/1913098/what-is-the-difference-between-an-interface-and-abstract-class)   

- `abstract class`
  > inherit only from one class  
- `interface` 
  > have mutiple inheritations   

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
//Interface EXTENDS MULTIPLE INTERFACES
interface derived extends InterfaceBase1, InterfaceBase2{
  // create new static variable , new abstract methods
}

//Implementation 
class A implements interfacebase1, interfacebase2{
  // implements methods of intefacebase1 and interfacebase2 
}
```
- Abstract classes can have constants, members, method stubs(methods without a body) and defined methods, whereas interfaces can only have constants and methods stubs.
- **Methods and members of an abstract class can be defined with any visibility**, whereas all methods of an interface can be defined as `public`, `private`, and `default`.
- When inheriting an abstract class, a concrete child class must define the abstract methods, whereas **an abstract class can extend another abstract class and abstract methods from the parent class don't have to be defined.**
- Similarly, **an interface extending another interface is not responsible for implementing methods from the parent interface.** This is because interfaces cannot define any implementation.
- **A sub class can only extend a single class (abstract or concrete)**, whereas **an interface can extend or a class can implement multiple other interfaces**.

- **A child class can define abstract methods with the same or less restrictive visibility**, whereas a class implementing an interface must define the methods with the exact same visibility (`public`).

- `absract class` : `IS-A` Relationship，需滿Liskov Substitution Principle，即Sub Class's object必須能夠替換所有Base Class's Object    
- `intface`       : `LIKE-A(HAS-A)` Relationship，it contains abstract methods. There is no `IS-A` Relationship btw `interface` and implementations  

A Class can implement multiple `interface`s but it can not extend multiple `abstract class`es。
- `interface`'s Keyword has only `static` or `final`
  > `abstract class`'s has no constraint  
- `interface`'s Members have only `public`,`priave` or `default`  
  > `abstract class`'s Members have different Access Controls  

### 使用的判斷依據

Interface  
- 需要讓UNRELATED的Classes都實現一個某種(規範)方法
  > e.g. 不相關的Classes都可以實現Comparable界面中的`compareTo()`方法；
- 需要使用多重繼承
  > `Interface a extends b, c`

Abstract  
- RELATED : 需要被SHARED在幾個RELATED的CLASSES 
- ACCESS CONTROL : 需要能控制繼承來的成員的ACCESS CONTROL，而不是都為`public`
- `static` AND `final` : 需要繼承非STATIC和非FINAL FIELDS

IN MANY CASES，考慮`interface`應優先於`abstract class`, 因為`interface`沒有`abstract class`嚴格的CLASS層次結構要求，更彈性地為類別class添加更多BEHAIOURS
> Since Java 8 and Java 9，`interface`也可以有`default` method實作以及`private` membber，`interface`的維護COST也變的很低    



Just like `pure virtual function` in cpp
- It contains only abstract methods. 
- **An interface cannot be instantiated**

`interface` is used instead of the keyword `class`
- The methods that are specified in an interface have no bodies
- Only headers that are terminated by semicolons 

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

## `SUPER`
Sub class一定會call Constructor from baseclass來完成初始化(一般是call default Constructor of baseclass)

- 如果sub class需要呼叫base class其它Consturctors，就需要利用`super()`做呼叫   
- 或者當sub class `@override`了base class的某個方法，可以通過使用`super`來reference該overriden方法   

## `override` and `overload`

Override : 當Sub class實作了一個與base class在方法聲明上完全相同的一個方法。

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

指在某Class內存在多個相同的Method名稱，但是在該Method的Parameter上數量,類型或順序至少有一項不同    
**返回類型不同，其它都相同不能稱作Overload**   

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
[Interview Question](https://www.journaldev.com/2167/java-exception-interview-questions-and-answers)



#### Error和Exception的區別

![image](https://user-images.githubusercontent.com/68631186/128909452-5ebbd566-1aa4-4f7e-b396-d65f64dede5a.png)
- Error(uncheck exception)   : 無法處理的錯誤(unable to handle), 程式碼要改
- Exception(check exception) : 可以處理(able to handle)的Exception
  > 其中Exception又分為`RuntimeException`和`IOException`異常。

- **Error通常是災難性的致命的錯誤，是Program無法控制和處理的(unable to handle)，當出現這些異常時，JVM一般會選擇終止thread**
- Exception通常情況下是可以被Program處理的(able to handle)，並且可在Program中應盡可能以`try ... catch ...`去處理這些異常
  > 也就是說check exception是可以被預防的並且處理的,透過`throws` exception 或者 `try ... catch ... finally`

Compiler
- Uncheck exception : Compiler不要求強制處理的異常,但Runtime時會拋出  
- Check exception   : Compiler要求必須處置的異常  

## `throw new`

```java
if( exception condition){
 throw new EXCEPTION_NAME ;

```
- 當Program情況**符合異常情形(exception condition)**則會
  > 阻止當前Method或Scope繼續執行     
  > 其次把`Exception Condition`和`普通問題`相區分 **(`普通問題`是指在當前環境下能得到足夠的信息，總能處理這個錯誤)**  
  > `Exception Condition`，是program已經無法繼續下去了，因為在當前環境下無法獲得必要的信息來解決問題，Developer所能做的就是從當前環境中拋出(`throw`)，並返回(`return`)當前`exception`的問題給上一層環境   
  > (`thorw`)拋出異常後, 將使用`new Exception_Name`並push到Stack上；然後，當前的執行路徑（已經無法繼續下去了）被終止，並且從當前環境中彈出對異常對象的引用。此時，異常處理機制接管Program，並開始尋找一個恰當的地方繼續執行程序，這個恰當的地方就是異常處理程序或者異常處理器，它的任務是將程序從錯誤狀態中恢復，以使Program換一種方式運行或從錯誤之後繼續運行下去  
 
 
## `try ... catch` 

- 是Method拋出(`throw`)異常之後,系統將轉為尋找(`catch`)合適的異常處理器(exception handler)處理(`exception`)
  > **Exception Handler是異常發生時依序呼叫堆疊在Stack內方法的集合**。當異常處理器所能處理的異常類型與方法拋出的異常類型相符時，即為合適的Exception Handler  

- **Java Runtime System從方法拋出異常後，批次調呼叫Stack中的方法，直至找到有合適(Proper)的Exception Handler處理Exception並執行
  > 當Java Runtime System未能在Stack找到能對應Exception的Exception Handler的方法時，則終止(terminate) System**

- **`RuntimeException`及其子類的Unchecked性質發生時, Java Runtime System自動拋出, 允許Program忽略`RuntimeException`**

- 當運行中的方法不能catch時，Java允許該方法不做任何拋出聲明, 因為大多數Error(Exception)屬於永遠不被允許發生的狀況，一個合理的Application不該有被catch的Exception
  > 言下之意就是Programmer寫的Code不合語法以及邏輯,應直接修改程式碼    

對於所有的Check Exceptions，Java規定需規定進行`try ... catch ...`或者 `thorw new` 來處理這些exceptions, for example   
```java
public class TestException {  
    public static void main(String[] args) {  
        int a = 1;  
        int b = 0;  
        try { 
        -------------------listen--------------------------------------------+ 
            if (b == 0) throw new ArithmeticException(); //via throw         |  
        }--------------------------------------------------------------------+
        catch (ArithmeticException e) { -------------------------------------+  
            How you gonna handle this exception                              |  
        }--------------------------------------------------------------------+
        
        // other catchs ...
    }  
}  
```
- `try`     : LISTEN 可能會出現(`throw`)異常的程式碼, 放在`try` block內，當`try` block內發生異常時，異常就被拋出。
- `catch`   : **FETCH the Exception的類別FROM `try` block, exception順序：先子類後父類(也就是越底層exception的先寫)**
- `finally` : `finally` block則是表**總是會被執行**, **它主要用於回收在`try` block裡打開的物力資源(e.g. Database、Network和硬碟文件)**。
  > 當它執行完成之後，才會回來執行`try`或者`catch` block中的`return`或`throw new`，**如果`finally`中使用了`return`或者`throw new`等終止(terminate)方法的語句，則就不會跳回執行，直接停止**
- `throw`     : for blocks `{ .... }` that might throw exception
- `throws`    : for methods `access-specifier returnType method(...) throws ...` that might throw exception 

Exception執行的順序Exception in `catch`  -> Exception in `try` -> `Exception` in Last Layer 
```java
class TestThrow{
    static void proc(){
        try{
            throw new NullPointerException("demo");    // 2
        }catch(NullPointerException e){
            System.out.println("Caught inside proc");  // 1
            throw e;
        }
    }

    public static void main(String [] args){
        try{
            proc();
        }catch(NullPointerException e){
            System.out.println("Recaught: "+e);       //3 
        }
    }
}
/** If exceptions happens
  * <li> Caught inside proc
  * <li> Recaught: java.lang.NullPointerException: demo
  */
```

## `throws` of Method

- **如果是不受檢查異常（unchecked exception, 即Error、RuntimeException或它們的子類)，那麽可以不使用`throws` keyword來聲明要拋出的異常，編譯(Compiler)仍能順利通過，但在Runtime時會被系統拋出**

- **當聲明方法會拋出某些checked exceptions則表該方法在運行時可能會出現checked exceptions,必定得用`try ... catch ...`，或者`throws new`處理exception，否則會導致Compiler Error**

- 當方法有拋出異常時，該方法的呼叫者才必須處理或者重新拋出該異常  
  > 如果caller無力處理該異常的時候，應該繼續拋出，而不是囫圇吞棗(持續丟給上層,不是放棄擺爛)
- 要呼叫的方法,如果有需要用`thorws`聲明拋出的異常則必須要follow任何checked exception的處理和聲明
  > **若覆蓋(override)一個方法, 則不能宣告與覆蓋方法不同或不相關的Exception, 宣告的任何異常必須是被overriden method所宣告的異常之同類或子類**  

## `finally`

當異常發生時，通常方法的執行將做一個陡峭的非線性的轉向，它甚至會過早的導致方法返回, 例如，某方法打開了一個文件並關閉，然後退出，你不希望關閉文件的程式碼被異常處理機制旁路, 此時就需要`finally`處理這種情況  

- `finally` block在`try ... catch ...`完成之後另一個`try ... catch`出現之前執行
- `finally` block無論有沒有異常拋出(exception)都會執行。如果拋出異常，即使沒有`catch` block，`finally`也會執行    
   > 這在關閉文件和釋放(release)任何在方法開始時被分配的其他資源是很有用(像是因為exceptio而造成memory leak)     
- `finally` block是optional, 可有可無, **但是每個try至少需要一個`catch`或者`finally` block**

For Example 
```java
/**
  * `finally`會在`try`結束之前執行
  */
  
try{
 System.out.println("1");
 
 retrun ;     // print 1 -> ptint 2 -> do return
}
finally{
 System.out.println("2");
}
/**
  * 1 
  * 2
  */
```
- `finally` block不應該有返回值，再有`finally`時`try`中的`return`不會立馬返回caller，而是記錄返回值後開始執行`finally` block待執行完畢後才做`try`內的return,所以如果在`finally`中修改了返回值，就會返回修改後的值  


- `throws`、`throw new`、`try ... catch ... finally`分別如何使用？
  > 在Java中，每個Exception都是一個Object，它是`Throwable`類或其子類的實例  
  > Method : **當一個方法出現異常後便拋出(`throws`)一個異常對象，該對象中包含有異常訊息，呼叫該異常對象的方法則可以讓我們catch到這個異常並可以對其進行處理**  
  > 一般情況下是用`try`進行Listen，如果系統會拋出（`throw`）一個Exception，可以通過它的類型來捕獲（`catch`）它，或通過`finally`來處理；

**每當遇到一個`try`，異常的結構就會被PUSH到EXCEPTION STACK中，直到所有的`try` block都PUSH完畢,如果下一級的`try`沒有對某種異常進行處理，EXCEPTION STACK就會執行POP, 直到遇到有處理這種異常的`try`或者最終將異常拋給JVM**

- RuntimeException與unchecked exception
  > **RuntimeException表示JVM在**運行**時遇到的異常，是一種常見運行錯誤，只要程式設計得沒有問題通常就不會發生**   
  > **Checked Exception跟Program's Context環境有關，即使Program設計無誤，仍可因使用的問題而拋出異常**   
  > Java編譯器要求方法必須聲明拋出該方法執行時可能會發生的Checked Exception，但是並不要求必須宣告拋出未被catch的`RuntimeException`   

異常和繼承一樣，是OOP中經常被濫用的東西，在Effective Java中對異常的使用的幾點原則： 
- 不要將異常處理用於正常的控制流（設計良好的API不應該強迫它的呼叫者為了正常的控制流而使用異常） 
- 對可以恢覆的情況使用checked exception，對(Programmer寫的code) Program Error使用`RuntimeException` 
- 避免不必要的使用checked exception（透過一些狀態檢測機制） 
- 優先使用標準的exception 
- 每個方法拋出的異常都要有文檔 
- 保持異常的原子性(Purity) 
- 不要在`catch`中忽略掉`try`捕獲到的exception


- 常見的RuntimeException 
  > ArithmeticException
  > ClassCastException 
  > IllegalArgumentException （Parameter異常） 
  > IndexOutOfBoundsException 
  > NullPointerException 
  > SecurityException （安全異常）



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
