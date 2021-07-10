# java.util.*
[TOC]  

![](https://i.imgur.com/B0TSLRj.png)  
## reference
[1](https://www.geeksforgeeks.org/collections-in-java-2/)  
[2](https://jax-work-archive.blogspot.com/2015/02/java-setlistmap.html)  

# List Interface
list interface is implemented by various classes like `ArrayList`, `Vector`, `Stack`.
So we can have such declarations 
```java
/** 
 *
 * @Param T is the date type of the object
 */
List <T> arraylist  =  new ArrayList<> ();
List <T> linkelist  =  new LinkedList<> ();
List <T> vector     =  new Vector<> ();
```
## ArrayList

`ArrayList` provides us with dynamic arrays in Java.
> It can not be used for primitive types, 

## Vector
A vector provides us with dynamic arrays in Java
> This is identical to ArrayList in terms of implementation. 

- However, the primary **difference between a vector and an ArrayList is that a Vector is synchronized and an ArrayList is non-synchronized**.

# Set Interface 
A set is an unordered collection of objects in which **duplicate values cannot be stored**.

This `Set` interface is implemented by various classes like `HashSet`, `TreeSet`, `LinkedHashSet`
```java
Set<T> hs = new HashSet<> ();
Set<T> lhs = new LinkedHashSet<> ();
Set<T> ts = new TreeSet<> ();
```

## HashSet
**The objects that we insert into the HashSet do not guarantee to be inserted in the same order**

Traversing Element using `iterator`
```java
/**
 *
 * Traversing elements
 */
Iterator<String> itr = hs.iterator();
while (itr.hasNext()) {
    System.out.println(itr.next());
}
```

## LinkedHashSet

Use a **doubly linked list** to store the data and retains the ordering of the elements

# Map Interface 

## HashMap
It stores the data in (Key, Value) pairs. 
To access a value in a HashMap

```java
HashMap<Integer, String> hm
    = new HashMap<Integer, String>();

hm.put(1, "index1");
hm.put(2, "index2");

// hashtable.get(index)
System.out.println("Value for 1 is " + hm.get(1));

// .entrySet() , .Entry<?,?> to traverse 
for (Map.Entry<Integer, String> e : hm.entrySet())
    System.out.println(e.getKey() + " " + e.getValue());
```
# Collection interface
**A class method** used for Set interface, List interface
Set and List both inheritance collection



# Optional

`Optional` gives the alternatives (e.g. if-else ... etc) to make code clear
 
 ## To initialize Optional instance

 There are three methods to initialize optional object
 1. `.empty()`
 2. `.of(object)`
 3. `ofNullable(object)`
 
 
`empty()` create a null optional instance
```java
 //Creating a Opetion<String> null elements
Optional<String> empty = Optional.empty();
System.out.println(empty); 
// output：Optional.empty
```
 
`of(parameter)` creates a not null object
```java=
// creating a Optional<String> 
Optional<String> opt = Optional.of("mtk");
System.out.println(opt); 
// output : Optional[mtk]
```

If the object inside `of(object)` is null then throws NullPointerException
```java=
// object name is null
String name = null;
Optional<String> optnull = Optional.of(name);
// throw NullPointerException
```
 
`.ofNullable()` allows creating the object as null or not null 
```java
// object name is null
String name = null;
Optional<String> optOrNull = Optional.ofNullable(name);
System.out.println(optOrNull); 
 // Output：Optional.empty
```

## if optional instance exists ?

`.isPresent()` if exists then returns true
```java=
Optional<String> opt = Optional.of("hey"); 
if(opt.isPresent())  System.out.println("true");
```
 > `ifPresentOrElse()` is recommanded insted of `isPresent()` 

`.isEmpty()` if object is non-existent then returns false
```java=
String object = null;
Optional<String> opt = Optinal.ofNullable(object);
if(opt.isEmpty()) System.out.println("true");
```

### Opetional With lambda 

```java=
//  Without lambda 
Optional<String> optOrNull = Optional.ofNullable("WhatsUp");
if (optOrNull.isPresent()) {
    System.out.println(optOrNull.get().length());
}
// with lambda instead 
 optOrNull.isPresent(str - > System.out.println(str.length()));
```

Since Java 9
using `.ifPresentOrElse(parameter -> if_expression , else_expression )` instead
```java=
opt.ifPresentOrElse(
 (str) ->  {System.out.println(str.length();}, 
 ()    ->  {System.out.println("empty");    });
```

## default value

A default `optional` instance's value

`.orElse` and `.orElseGet` method
```java=
String name = null;
String opt = Optional.ofNullable(name).orElse("Im_default_value")
System.out.println(opt);    // output : Im_default_value

String opt_2 = Optional.ofNullable(name).orElseGet( ()->"Im_default_value" );
```

function as parameter of `.orELse` and `.orElseGet` method
```java=
// function
public static String getDefaultValue(){
    System.out.println("calling getDefaultValue");
    retrun "Im_default_value"
}

/**
 * In public static void main()
*/
String name = null;
String name2 = Optional.ofNullable(name).orElse(getDefaultValue());
// or
String name3 = Optional.ofNullable(name).orElseGet(OrElseOptionalDemo::getDefaultValue);
```

### Different btw `.orELse` and `.orElseGet`

```java=
String name = "Im_not_Null";
String name2 = Optional
               .ofNullable(name)
                /**
                 * getDefaultValue() always will be called
                 * ofNullable is null whether or not
                 */
               .orElse(getDefaultValue());

 /* usingElseGet() */
String name3 = Optional
               .ofNullable(name)
               // getDefaultValue will not be called
               .orElseGet(OrElseOptionalDemo::getDefaultValue);
```
> `Class :: Method` (the above `OrElseOptionalDemmo::getDefaultValue` means this method may not be called


## `.filter( para -> condition )`

```java=
String password = "12345";
Optional<String> opt = Optional.ofNullable(password);
// if parameter pwd's length > 6 exists
System.out.println(opt.filter(pwd -> pwd.length() > 6).isPresent());
```

`.and(condition)` to filter multiple conditions
```java=
Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;

password = "1234567";
opt = Optional.ofNullable(password);
boolean result = opt.filter(len6.and(len10)).isPresent();
```

## `map(class::method)` and `map(para->method)`
```java=
String name = "ToMap";
// creating a option<string> instance named nameOptional
Optional<String> nameOptional = Optional.of(name);

 // nameOptional.get().length();
Optional<Integer> intOpt = nameOptional.map(String::length);
System.out.println( intOpt.orElse(0));
```

### filter and map together 
```java=
String password = "password";
Optional<String>  opt = Optional.ofNullable(password);

Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;
Predicate<String> eq = pwd -> pwd.equals("password");

boolean result = opt.map(String::toLowerCase).filter(len6.and(len10 ).and(eq)).isPresent();
```
