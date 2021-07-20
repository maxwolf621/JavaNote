###### tags: `JAVA`

# Inheritance 
[TOC]
## Modifier 
#
access-specifier storage returnType function(parameters)

access-specifier
1. public
2. protected
3. private
4. default (No modifier)

Storage
1. static 
2. final
3. abstract


## Within the class's pakacge
| Access Specifier |  Accessible to a subclass inside A same package | Accessible to all other classes in the same package |
| ----             |     ---                                         |     ----                                            |
|  default         |     Yes                                         |     Yes                                             |
|  public          |     Yes                                         |     Yes                                             |
|  protected       |     Yes                                         |     Yes                                             |
|  private         |     No                                          |     No                                              | 


:::info
using default as classes and main inside the same package
:::

### Outside the class's package
| Access Specifier |  Accessible to a subclass inside A same package | Accessible to all other classes in the same package |
| ----             |     ---                                         |     ----                                            |
|  default         |     No                                          |     No                                             |
|  public          |     Yes                                         |     Yes                                             |
|  protected       |     Yes                                         |     No                                              |
|  private         |     No                                          |     No                                              | 


### Is-A Relationship Does Not Work In Reverse

```java=
// GradeActivity
//     '--extends---->FinalExam
    
GradedActivity activity = new GradedActivity();
FinalExam exam = activity;    // ERROR!

// The Compiler Work but fail at run-time
GradedActivity activity = new GradedActivity();
FinalExam exam = (FinalExam) activity;    // ERROR!
```

## The `instanceof` Operator
To determine whether an object is an instance of a particular class.
```java=
//USAGE
refObj instanceof ClassName
```

```java
GradedActivity activity = new GradedActivity();
FinalExam exam = new FinalExam();
if (exam instanceof GradedActivity) // it will return true
   System.out.println("Yes, activity is a GradedActivity.");
else
   System.out.println("No, activity is not a GradedActivity.");

```

## using `static`
A `static` variable is shared by everyone in this class or it's subclass
A `static` method access static variable directly without allocating the object of that class

```java
System.out.println();
```
>1. System 
>>is a class Name 
>2. out 
>>static member
>3. println
>>static method

:::info
Always call static member with `.`
such as ...
```java
className.staticMember;
```
:::


static member As global variable
```java
class Account{
  static long count = 0;
  private long SID;
  Account(){
    count++;
    SID=count
  }
  // count would never reset, and shared by account's objs
  // keep increasing wihle Account is called
  // but every different account objs have its sunique SID
}
```
:::danger
1. instance variables and methods can be used only after `new`
2. static methods can't access non-static member (instance methods and instance variables)
:::


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
//If obj not exists then
Employee.getCount(); 
//Else if obj exists say e1 then
e1.getCount();
```

## Garbage Collection 
call method `finalize` to release the memory while doing Termination housekeeping 

1. method finalize return void
2. method finalize may not be executed even program reachs end

Memory Leaks in Java
- It Could happen to the File stream

## `final` keyword
final dataType VAR = value;
VAR   : usually represents as UPPERCASE
final : as `const` in c 

Just like Cpp
final class : can't be a base class
final method: can't be overriden

### Usage of `final`
priciple of least privilege ( ME is DA BOSS )

final variable should always explictly declared
```java
class increment
{
  private int total = 0;
  private final int INCREMENT;
  public Increment(int incrementValue){
    INCREMENT = incrementValue; // declared by constructor
  }
}
```

### private , protected and public

- The derived class can not access base class's private member but it can be accessed via public method or protected method of base class
- The derived class can call base class's constructor
:::danger
a `static` member can be shared by all class A's obj and obj who inherit from A
:::

## Package 
- es ist wie .h in Cpp
Creat a package in bash
```bash
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
to create package
```bash
javac -d . Increment.java
```

import to test.java
```java
import practice.Increment;
pubic class test{
  //...
}
```

## How Compiler find package

The compiler would use ==class loader== to find the package
1. search from JDK's standard liberary if not
2. search optional package if not
3. search from classspath (package list)


## Constraint of the package

1. Do not declare more than one public class in same file
2. pubic class name should be the same as name.java
3. non-public class only can be accessed by same package's classes, however public class can be accessed by any classes 
  1. public class A:
  > Other classes can inherit from it and use it 
  2. private class A:
  >  members can only be accessed by method in the same class, cant not be the base class 
  3. protected class A:
  >  only can be accessed by same package's classes, and <font color=red>other classes from other packages can inherit from it, but can't access the protected members in class A</font>
  4. No access-specifier
  >  The Classes Only in same package can inherit from it
### Access Control
  1. public    : Any classes can access
  2. default   : Classes Within the same package 
  3. protected : Only Allow class , Subclass IN THE SAME PACAKAGE  
  4. private   : members in same class

### Protected Member in the Base Class

> Concept
> : Protected members of a class may be accessed by methods in a subclass, and by methods in the same package as the class.


1. `private double balance` change `protected double balance` in the base class then derived class can access it
```java

// In the Score.java
public class GradedActivity2
{
protected double score;  // Numeric score
public void setScore(double s)
{
   score = s;
}
// In the Exam.java
public class Exam extends GradeActivity2
{
//...
public Exam(int question, int miss)
{
    //...
    int s = (question - miss)*2;
    setScore(s);
    // The derived class Exam access the portect member of 
    // The Base class GradeActivity2
}
}

```
## Dynamic Binding 

When a superclass variable references a subclass object, a potential problem exists. 
What if the subclass has overridden a method in the superclass, and the variable makes a call to that method? 
Does the variable call the superclass’s version of the method, or the subclass’s version?
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
>static binding 
> : (c prog) calls function at compiler-time

> dynamic binding
> : (Java) depends on reference type at run-time
> : At comipler time it would only check the type of object if it can be called or not

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
At Comiple-Time Do Check ONLY
1. Class Employee exists
2. Manager is an Employee;
3. Employee e has getSalary() method
4. ERROR Employee e doesn't have doAccounting() method
At Run-Time (A overriden Function ? )
1. e is an Manager();
2. Manager has its own getSalary()
3. Invoke Manager's getSalary() method

## Abstract Classes and Abstract Methods

```java=
AccessSpecifier abstract class ClassName
```

A Concept of A thing that can polymorphically apply for different way
such as ...
```
There is A abstract method in class Encryption called encrypt()
There are numbers way encrypt() methods (polymorphism)
Encryption
  '---------> Caser 
  '---------> ASE 
  '---------> RSA
so Caser , ASE and RSA have their own encryption, that is why we must override the method encrypt() from class Encryption
```


When an `abstract` method appears in the class, the method must be overridden in the subclass

When a Class Contains an abstract method, you cannot create an instance of the class.

==An abstract class is not instantiated, but other classes extend it.==
==An abstract class method has no body and must be overriden in a subclass==


```java
// A Abstract Class 
/*public/protected*/ abstract class Name{
  
  // A Abstract Method
  /*public/protected*/ abstract returnType methods(parameters);
  
  // A Abstract VARIABLE
  /*final*/ dataType VAR = VALUE ;
```


A Example In UML
```
A_shape
 L----> Circle
 L----> Rectangle
          L------->_Rectangle5x5

_Recangle5x5 must contain three constructors :
A_Shape : Base Class
  '
Rectangle : super is A_Shape()
 '--------> _Rectangle5x5 super is (A_Shape() + Rectangle())
```




## Implementing Multiple Interface

```java=
public class ACLASS implements INTERFACE1,
                               INTERFACE2,
                               INTERFACE3
```

```
Exhange                  BackAccount
 '---------> Deposit <-------'
```

```java
interface BankAccount
{
    //saveRate is FINAL double 
  double savRate = 2.2.5;
  abstract void getInterest();
}

interface Exchange
{
 abstract void setExchange(double e);
}

// implementation
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
    public void setExchange(){
        //..
    }
}

public class Do{
  public static void main(String[] args){
    int balance = 5000;
    int TousaDollar = 33.2;
    Deposit account = new Deposit(balance);
    account.getInterest();
    account.setExchange(TousaDollar);
    }
}
```

## interface and inheritance together 
Just like `pure virtual function` in cpp

It contains only abstract methods.
**An interface cannot be instantiated**

`interface` is used instead of the keyword `class`
- The methods that are specified in an interface have no bodies
- Only headers that are terminated by semicolons 

```java
public interface p{
  //prototype ONLY
  typeName method(parameters); 
  // VAR in interface class
  final dataType VAR = value;
}

/* Class implements from interface class*/
public class p1 implements face{
  typName method(parameters){
    //...
  }
}
```

## abstract with polymorphism
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
class Rectangle extends GraphicsObj{
  //..
  /*Must implement from the bast class*/
  public void draw(){/*..*/}
}
class Circle extends GraphicsObj{
  public int Radius; 
  public Circle(int a, int b, int r){
    Origin.x = a;
    Origin.y =b ;
    Radius = r  ;
  }
  /* Must Implement it from the base class */
  public void draw(){
    //...
  }
}

// import a drawDemo
import java.awt.Point
public class test{
  public static void main(String[] args)
  {
    Rectangle rectObj = new Retangle(/*..codes..*/);
    Circle cirObj     = new Circle(/*..codes..*/)  ;
    //...
    rectObj.draw();
    cirObj.draw() ;
    //...
  }
}

/* 
  A interface
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


methods in interface class are
1. public   returnType methods
2. abstract returnType methods
attributs in interface class are
1. public returnType attributs
2. static returnType attributs
3. final  returnType attributs

:::danger
All declarations in interface class are public abstract methods
also variables are public , static , final by default
so we dont have write down these keywords or specifiers in interface class
:::


## Default Methods since Java 8
A default method is an interface method that has a body.
```java=
public interface Displayable
{
    default void display()
    {
        System.out.println("This ...");
    }
}
```


## Anonymous Inner Classes

CONCEPT: 
An inner class is a class that is defined inside another class. 
**an anonymous inner class is an inner class that has no name. **


#### When To USE?
Sometimes you need a class that is simple, and to be instantiated only once in your code. 

An anonymous inner class is a class that has no name. 
It is called an inner class because it is defined inside another class. 
You use the new operator to simultaneously define an anonymous inner class and create an instance of it.

```java=
new ClassOrInterfaceName(){
    //...
}
```


1. An anonymous inner class must either implement an interface, or extend another class.

2. If the anonymous inner class extends a superclass, the superclass’s no-arg constructor is called when the object is created.

3. An anonymous inner class must override all of the abstract methods specified by the interface it is implementing, or the superclass it is extending.

4. Because an anonymous inner class’s definition is written inside a method, it can access that method’s local variables, but only if they are declared final, or if they are effectively final. (An effectively final variable is a variable whose value is never changed.) A compiler error will result if an anonymous inner class tries to use a variable that is not final, or not effectively final.


```java=
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

abstract : only inherit from one class
interface: mutiple inheritations 

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


## Inherit from a interface class

A inteface class can inherit from mutiple interface classes
```java
interface derived extends InterfaceBase1, InterfaceBase2{
  // create new static variable , new abstract methods
}

// implementation 
class A implements interfacebase1, interfacebase2{
  // implements methods of intefacebase1 and interfacebase2 
}
```


