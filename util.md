# java.util.*
[TOC]

![](https://i.imgur.com/B0TSLRj.png)
## reference
[1](https://www.geeksforgeeks.org/collections-in-java-2/)
[2](https://jax-work-archive.blogspot.com/2015/02/java-setlistmap.html)
# List Interface
list interface is implemented by various classes like `ArrayList`, `Vector`, `Stack`.
So we can have such declarations 
```java=
List <T> arraylist  =  new ArrayList<> ();
List <T> linkelist  =  new LinkedList<> ();
List <T> vector   =  new Vector<> ();
// Where T is the type of the object
```

## ArrayList

`ArrayList` provides us with dynamic arrays in Java.
> It can not be used for primitive types, 

## Vector
A vector provides us with dynamic arrays in Java
> This is identical to ArrayList in terms of implementation. 
>> However, the primary difference between a vector and an ArrayList is that a Vector is synchronized and an ArrayList is non-synchronized. Let’s understand the Vector with an example:

# Set Interface 
A set is an unordered collection of objects in which duplicate values cannot be stored.

>  This set interface is implemented by various classes like HashSet, TreeSet, LinkedHashSet
```java=
Set<T> hs = new HashSet<> ();
Set<T> lhs = new LinkedHashSet<> ();
Set<T> ts = new TreeSet<> ();
// Where T is the type of the object.
```

## HashSet
**The objects that we insert into the HashSet do not guarantee to be inserted in the same order**

Traversing Element using `iterator`
```java
 // Traversing elements
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
 

## To initialize Optional

`empty()` create a null instance
```java
Optional<String> empty = Optional.empty();
System.out.println(empty); 
// output：Optional.empty
```

`of(parameter)` creates not null instance
> If `if(parameter) is not null the throws NullPointerException
```java=
Optional<String> opt = Optional.of("mtk");
System.out.println(opt); 
// output : Optional[mtk]
String name = null;
Optional<String> optnull = Optional.of(name);
// throw NullPointerException
```

`.ofNullable()` allows null or not null
```java
String name = null;
Optional<String> optOrNull = Optional.ofNullable(name);
System.out.println(optOrNull); // 输出：Optional.empty
```

## if optional instance exists ?

`.isPresent()` if exist returns true
```java=
Optional<String> opt = Optional.of("hey"); 
if(opt.isPresent())  System.out.println("true");
```

`.isEmpty()` if non-exist returns false
```java=
Optional<String> opt = Optinal.ofNullable(null);
if(opt.isEmpty()) System.out.println("true");
```

### With lambda 

A example without lambda
```java=
Optional<String> optOrNull = Optional.ofNullable("WhatsUp");
if (optOrNull.isPresent()) {
    System.out.println(optOrNull.get().length());
}
```
Lambda makes code clear
```java=
optOrNull.isPresent(str - > System.out.println(str.length()));
```
Since Java 9
using `.ifPresentOrElse(parameter -> if_expression , else_expression )`
```java=
opt.ifPresentOrElse(str -> System.out.println(str.length()), () -> System.out.println("empty"));
```

## default value

A default `optional` instance's value

`.orElse` and `.orElseGet` method
```java=
String name = null;
String opt = Optional.ofNullable(name).orElse("Im_default_value")
System.out.println(opt); // output : Im_default_value
String opt_2 = Optional.ofNullable(name).orElseGet( ()->"Im_default_value" );
```

function as parameter of `.orELse` and `.orElseGet` method
```java=
// function
public static String getDefaultValue(){
    System.out.println("calling getDefaultValue");
    retrun "Im_default_value"
}

// in public static void main()
String name = null;

String name2 = Optional.ofNullable(name).orElse(getDefaultValue());
// print 

String name3 = Optional.ofNullable(name).orElseGet(OrElseOptionalDemo::getDefaultValue);
```


### Different btw `.orELse` and `.orElseGet`

```java=
String name = "Im_not_Null";
String name2 = Optional
               .ofNullable(name)
                // getDefaultValue will be called         
               .orElse(getDefaultValue());
String name3 = Optional
               .ofNullable(name)
               // getDefaultValue will not be called
               .orElseGet(OrElseOptionalDemo::getDefaultValue);
```
> `Class :: Method` without `()` : this method may not be called


## filter

Filter the condition

```java=
String password = "12345";
Optional<String> opt = Optional.ofNullable(password);
System.out.println(opt.filter(pwd -> pwd.length() > 6).isPresent());
```

using `.and(condition)` to filter multiple conditions
```java=
Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;

password = "1234567";
opt = Optional.ofNullable(password);
boolean result = opt.filter(len6.and(len10)).isPresent();
System.out.println(result);
```

## map


```java=
String name = "ToMap";
// a instance
Optional<String> nameOptional = Optional.of(name);
Optional<Integer> intOpt = nameOptional
        .map(String::length);
System.out.println( intOpt.orElse(0));
```

```java=
String password = "password";
Optional<String>  opt = Optional.ofNullable(password);

Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;
Predicate<String> eq = pwd -> pwd.equals("password");

boolean result = opt.map(String::toLowerCase).filter(len6.and(len10 ).and(eq)).isPresent();
System.out.println(result);
```