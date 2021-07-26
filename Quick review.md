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

### Storage
1. static 
2. final
3. abstract

## Access Control of a class
1. public    : Accessed By Any classes
2. default   : Accessed By the Classes Within the same package 
3. protected : Accessed By the classes and the Sub-classes IN THE SAME PACAKAGE  
4. private   : Accessed only By members(fields and method) in same class

## Access Specifier Within pakacge
| Access Specifier |  Accessible to a subclass inside A same package | Accessible to all other classes in the same package |
| ----             |     ---                                         |     ----                                            |
|  default         |     Yes                                         |     Yes                                             |
|  public          |     Yes                                         |     Yes                                             |
|  protected       |     Yes                                         |     Yes                                             |
|  private         |     No                                          |     No                                              | 

-  using `default` access specifier when classes and main inside the same package  


## Outside the class's package
| Access Specifier |  Accessible to a subclass inside A same package | Accessible to all other classes in the same package |
| ----             |     ---                                         |     ----                                            |
|  default         |     No                                          |     No                                             |
|  public          |     Yes                                         |     Yes                                             |
|  protected       |     Yes                                         |     No                                              |
|  private         |     No                                          |     No                                              | 


- The derived can not access base's  private member but it can be accessed via public method or protected method of base class
- a `static` member can be shared by all class A's obj and obj who inherit from A

1. Do not declare more than one `public` class in same file
2. `pubic` class name should be the same as file name e.g. `name.java` , `public class name` 
3. **non-public class only can be accessed by same package's classes**.  
- `public` class A: Any Classes can inherit from it and uses it 
- `private` class A: Members(Fields and Methods) can only be accessed by method **in the same class**, cant not be the base class 
- `protected` class A: **Members only can be accessed by **same package**'s classes, and other classes from other packages can inherit from it, but can't access the protected members in class A**
4. No access-specifier : Only classes  in same package can inherit from it


#### Protected Member in the Base Class

Protected members in a class may ony be accessed by methods in a subclass and the methods in the same package.
```java
public class GradedActivity2
{
    /**
      * <p> protected member 
      *     {@code score}  can be 
      *     accessed by subclass  </p>
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

### Is-A Relationship Does Not Work In Reverse

```java
// GradeActivity
//     '--extends---->FinalExam
    
GradedActivity activity = new GradedActivity();
FinalExam exam = activity;    // ERROR!

// The Compiler Works but run-time fials
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

static member As global variable
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
- es ist wie `.h` in Cpp
Creat a package in bash
```console
javac -d . filename.java
```

name of package
```
LARGE-----------SMALL
tw.network.nkust.csie;
```

import the package
```bash
import java.util.*;
```
> `*` all public class in java.util can be used..

### namespace of JAVA
To avoid name conflit in different files

```java
package practice;
public class Increment 
{
  //.....
}
```

to create package via bash command
```bash
javac -d . Increment.java
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

Compiler use **class loader** to find the package and it search the package first from
1. search from JDK's standard liberary  , if not
2. search optional package , if not
3. search from classspath (package list)

## Dynamic Binding 

When a superclass variable references a subclass object, a potential problem exists. 

- What if the subclass has overridden a method in the superclass, and the variable makes a call to that method?
    > Does the variable call the superclass’s version of the method, or the subclass’s version?

```java
class Employee{
  public static void main(String args[])
  {
    Employee EmployeeA = new Employee();
    Employee EngineerB = new Engineer();
    EmployeeA.getSalary(); 
    // getSalary() in Class Engineer or in Class Employee?
    // Dynamic Binding would check that for us 
    EngineerB.getSalary();
  }
}
```

#### Static binding 
(In c prog) Call function at compiler-time

#### Dynamic binding
(In Java prog) Depend on reference type at run-time
At comipler time it would only check the type of object if it can be called or not

So at Compiler Time
```java
class Employee{
  public static void main()
  {
    Employee e = new Manager();
    e.getSalary();
    e.doAccounting(); // Compiler Error
  }
}
```
At Comiple-Time Checks ONLY
1. Class `Employee` exists
2. `Manager` is an `Employee`;
3. `Employee e` has `getSalary()` method
4. ERROR `Employee e` doesn't have `doAccounting()` method

At Run-Time (Check Overriden Function and Reference to the object)
1. `e` is(references to) an `Manager` class;
2. (Check the Overriden Function) `Manager` class has its own `getSalary()` method
   > Invoke Manager's `getSalary()` method

## Abstract Classes and Abstract Methods


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


When an `abstract` method appears in the class, the method must be overridden in the subclass

When a Class Contains an abstract method, you cannot create an instance of the class.

==An abstract class is not instantiated, but other classes extend it.==
==An abstract class method has no body and must be overriden in a subclass==


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


## Implementing Multiple Interface

```java
public class ACLASS implements INTERFACE1,
                               INTERFACE2,
                               INTERFACE3
```

```java
/**
  * Despoit implements Exchange and BackAccount
  * (I)Exchange              BackAccount(I)
  *  '---------> Deposit <-------'
  */
interface BankAccount
{
      //fields in interface are {@code final} type 
      double savRate = 2.2.5;
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


## Anonymous Inner Classes

**an anonymous inner class is an inner class that has no name. **
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


## Interface Vs Abstract Class 
[More Details](https://stackoverflow.com/questions/1913098/what-is-the-difference-between-an-interface-and-abstract-class)  

- abstract : only inherit from one class
- interface: mutiple inheritations 

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
interface derived extends InterfaceBase1, InterfaceBase2{
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
- **A child class can define abstract methods with the same or less restrictive visibility**, whereas a class implementing an interface must define the methods with the exact same visibility (public).
