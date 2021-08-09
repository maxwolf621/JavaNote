# java.util.*
[Colletion](https://www.geeksforgeeks.org/collections-in-java-2/)  
[Util methods](https://jax-work-archive.blogspot.com/2015/02/java-setlistmap.html)  
![](https://i.imgur.com/B0TSLRj.png)  


# Collection interface
**A class method** used for Set interface, List interface
Set and List both inheritance collection
```java
/**
  * <p> 
  * add a new ref into the collection
  * </p>
  */
boolean add(Object o)      

/**
  * <h#> 
  * 刪除集合中所有的物件，即不再持有這些物件的引用 
  * </h#>
  */
void clear()              

/**
  * <p> 
  * 判斷集合是否為{@code null}
  * </p>
  */
boolean isEmpty()         

/**
  * <p>
  * 判斷集合中是否持有特定物件的引用
  * </p>
  */
boolean contains(Object o)  

/**
  * <p> 
  * delete a ref in collection 
  * </p>
  */
boolean remove(Object o)

/** 
  * @return Iterator 物件，可以用來遍歷集合中的元素 
  */
Iterartor iterator()  

/**
  * @return Length of the Collection
  */
int size() 

/**
  * @return an {@code Object[]}
  */
Object[] toArray() 

/**
  * @return {@code true} if {@code itr.next != NULL}
  */
itr.hasNext() 

/**
  * @return 返回下一個元素<object>
  */
itr.next() 

/**
  * <p> 
  * 從集合中刪除上一個有 {@code next()} 方法返回的元素
  * </p>
  */
itr.remove() 
```

# List Interface
list interface is implemented by various classes like `ArrayList`, `Vector`, `Stack`.
So we can have such declarations 
```java
/** 
 * <p> {@code T} is the date type of the object </p>
 */
List <T> arraylist  =  new ArrayList<> ();
List <T> linkelist  =  new LinkedList<> ();
List <T> vector     =  new Vector<> ();
```
## ArrayList(Dynamic arrays)
`ArrayList` provides us with dynamic arrays in Java.
1. It can not be used for primitive types, 
2. To Increase Capacity we need to call `Arrays.copyOf()` (copy older Array to a new one) which makes lot of costs
3. `remove()` also costs

## Vector(Dynamic arrays)
- It is identical to ArrayList in terms of implementation. 
- The primary **difference between a vector and an ArrayList is that a Vector is synchronized and an ArrayList is non-synchronized**.

### PLUGS
- **ArrayList中的資料是以連續**的方式儲存在記憶體，可支援隨機存取及循序存取，**所以循序讀取或排序(sort)時的效能好**。
  > 每個節點不用另外儲存下一個節點的指標，也因此，每個節點所占用的記憶體較少，但也**因為是線性儲存於記憶體，所以在如果要對之中的元素進行插入或刪除節點等動作時效能較差**，因為我們必須移動大量節點(當刪掉/新增一個元素，其他元素的位置都會被迫改變)。

- **LinkedList中的資料以不連續**的方式儲存，因此不需使用連續的記憶體空間，每個節點都會記錄著下個節點的指標，所以**在串列中插入或刪除節點時只需修改相關節點的指標，所以插入或刪除特定元素時的效能較好**。但它的缺點是**因非線性儲存，所以我們在讀取時無法快速索引到該節點(因為不知道位置)，只能以循序存取讀取每一個節點的指標**，所以讀取的效能較差。另外，**因為每個元素還要儲存下一個元素的指標，因此占記憶體容量比較大。**

# Map Interface 
[HashMap](https://techmastertutorial.in/java-collection-internal-hashmap.html)  
[HashMap SourceCode](https://www.gushiciku.cn/pl/gVdy/zh-hk)  
[Calcuate hascode and index of bucket](https://www.geeksforgeeks.org/internal-working-of-hashmap-java/)  

## HashMap
- HashMap stores the entries(key-value pairs) using a hash table.  
  > Entry : The Set of key-value pairs
- In the hash table each key-value pair is mapped corresponding to the hashcode derived from the key.  
- HashMap's key can be  `null` 
  > HashMap insert `null` key-value pair only at the index 0 (bucket) 
- Insert New entry at the head of the List of the bucket not at the tail.
- A bucket(index of bucket) : `int i = indexFor(hash, table.length);` 
- [Capacity Expansion](https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%AE%B9%E5%99%A8.md#5-%E6%89%A9%E5%AE%B9-%E5%9F%BA%E6%9C%AC%E5%8E%9F%E7%90%86)
- Since JDK 1.8，if number of entries in a bucket > 8 then convert the linked-lists to black red tree.
- HashMap's internal hash table is is created only after the first use.   
  > Initially the hash table is `null`.    
  > Table is initialized only when the first insertion call is made for the HashMap instance.    
  > ```java
  > transient Node<K,V>[] table;
  > ```


![image](https://user-images.githubusercontent.com/68631186/125174975-b71b6980-e1fb-11eb-8803-54ca5fe54bb9.png)
- First hash code is calculated for the keys.  
- Using the hashcode, index is identified in the bucket array.  
- In the array, **each array index is mapped to the hashcode derived from the key**. 
    > Each array index ( or we can say each bucket ) contains a reference to a linked list in which we store the key-value pair entry.

![image](https://user-images.githubusercontent.com/68631186/125176665-dfa96080-e207-11eb-8b2e-15088e3b721e.png)  
- For a key-value pair, first identify the hashode for the key using the `hashcode` method.  (`index = hashCode(key) & (n-1).`)
    > Once we have the hash code, index (of the array) is calculated to identify the bucket in which the key-value pair will be stored.  
    >>  Once we have the bucket index (of the array) , check for the entry (linked) list.
- Check For entry(list), IF
    1. we do not have the already existing (linked)list corresponding to that (bucket) index then we create a (linked)list and add the key-value pair entry to this (linked)list. 
    2. there is already a (linked)list of the key-value entries then we go through(search) the list check for key object 
- Check For Key , IF
    1. the key already exist then we override(update) the value.    
    2. **the key is not there then a new entry(key-value pair) is created**
        > New entry is then added to head of the (linked)list

### capacity and load factor 
**The two parameters that affect the performance of HashMap are – initial capacity and load factor.**   
- Initial capacity is the initial size of the hash table 
- load factor is is a measure of how full the hash table is allowed to get before its capacity is automatically increased. 
```java
/**
  * @apinote Using the initial capacity and load factor, threshold is calculated. 
  *          Threshold is the size limit after which table is resized/rehashed. 
  *          The value of the threshold is {@code (int)(capacity * loadFactor).)}
  */
public HashMap(int initialCapacity, float loadFactor) {  
       if (initialCapacity < 0)  
           throw new IllegalArgumentException("Illegal initial capacity: " +  
                                              initialCapacity);  
       if (initialCapacity > MAXIMUM_CAPACITY)  
           initialCapacity = MAXIMUM_CAPACITY;  
       if (loadFactor <= 0 || Float.isNaN(loadFactor))  
           throw new IllegalArgumentException("Illegal load factor: " +  
                                              loadFactor);  
       this.loadFactor = loadFactor;  
       this.threshold = tableSizeFor(initialCapacity);  
   }  
```

## [ConcurrentHashMap](https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%AE%B9%E5%99%A8.md#concurrenthashmap)

```java
static final class HashEntry<K,V> {
    final int hash;
    final K key;
    volatile V value;
    volatile HashEntry<K,V> next;
}
```

## [Linked Hash Map](https://github.com/CyC2018/CS-Notes/blob/master/notes/Java%20%E5%AE%B9%E5%99%A8.md#linkedhashmap)
```java 
public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>
```

# Set Interface 
- **A set is an unordered collection of objects** in which **duplicate values cannot be stored**.

This `Set` interface is implemented by various classes like `HashSet`, `TreeSet`, `LinkedHashSet`
```java
Set<T> hs = new HashSet<> ();
Set<T> lhs = new LinkedHashSet<> ();
Set<T> ts = new TreeSet<> ();
```

TreeSet：基于红黑树实现，支持有序性操作，例如根据一个范围查找元素的操作。但是查找效率不如 HashSet，HashSet 查找的时间复杂度为 O(1)，TreeSet 则为 O(logN)。

HashSet：基于哈希表实现，支持快速查找，但**不支持有序性操作**。并且失去了元素的插入顺序信息，也就是说使用 Iterator 遍历 HashSet 得到的结果是不确定的。

LinkedHashSet：具有 HashSet 的查找效率, 且使用**doubly linked list** to store the data and **retains the ordering of the elements**

## HashSet
**The objects that we insert into the HashSet do not guarantee to be inserted in the same order**  

> HashSet uses the **unique key concept of the HashMap** and **inserts the values of the HashSet** in the underlying HashMap as keys and just put some dummy object corresponding to each key. Which makes it a O(1) insertion data structure and keeps the values unique in a HashSet.

- HashSet allows `null` value.
- HashSet class is non `synchronized`.
- HashSet doesn't maintain the insertion order. 
  > Elements are inserted on the basis of their hashcode.
- **HashSet is the best approach for search operations**.   
![image](https://user-images.githubusercontent.com/68631186/125178853-19d02d80-e21b-11eb-87d8-dd34361b438c.png)  


# Optional
`Optional` gives the alternatives (e.g. if-else ... etc) to make code cleaner

## To initialize Optional instance

There are three methods to initialize optional object
1. `.empty()`
2. `.of(object)`
3. `.ofNullable(object)`
 
`empty()` create a **null optional instance**
```java
/**
 * <p> 
 * Creating a {@code Option<String> null} elements 
 * </p>
 */
Optional<String> empty = Optional.empty();
/**
  * @return {@code Optional.empty}
  */
System.out.println(empty); 
```
 
`of(parameter)` creates a **non null object**
```java
Optional<String> opt = Optional.of("mtk");
/**
 * @return {@code Optional[mtk]}
 */
System.out.println(opt); 
```

If the object inside `of(object)` is null then throws NullPointerException
```java
String name = null;
/** 
  * <p> if object inside {@code of} is {@code null} 
  *        @throws NullPointerException </p>
  */
Optional<String> optnull = Optional.of(name);
```
 
`.ofNullable()` allows creating the object as null or not null 
```java
String name = null;
Optional<String> optOrNull = Optional.ofNullable(name);

/**
 * @return {@code Optional.empty}
 */
System.out.println(optOrNull); 
```

## Existing Checking

`.isPresent()` if exists then returns true
```java
Optional<String> opt = Optional.of("hey"); 
if(opt.isPresent())  System.out.println("true");
```
 - `.ifPresentOrElse()` is recommanded insted of `isPresent()` 

`.isEmpty()` if object is non-existent then returns false
```java
String object = null;
Optional<String> opt = Optinal.ofNullable(object);
if(opt.isEmpty()) System.out.println("true");
```

### Optional With lambda 

```java
Optional<String> optOrNull = Optional.ofNullable("WhatsUp");

/**
 * <p> Without lambda </p>
 */
if (optOrNull.isPresent()) {
    System.out.println(optOrNull.get().length());
}

/**
  * <p> With lambda </p>
  * Varaible {@code str} will be {@code String} type
  * depending on {@code Optional<String> optOrNull}'s type
  */
optOrNull.isPresent(str - > System.out.println(str.length()));
```

> Since Java 9

Using `.ifPresentOrElse(object parameter -> if_expression , else_expression )` instead
```java
optOrNull.ifPresentOrElse(
 (str) ->  {System.out.println(str.length();}, 
 ()    ->  {System.out.println("empty");    });
```

## default value (for if not situattion)
A default `optional` instance's value via `orElse` and `orElseGet` method

```java
/**
  * {@code orElse} and 
  * {@code orElseGet(param -> method)} 
  */
String name = null;
String opt = Optional.ofNullable(name).orElse("Im_default_value")
/**
  * @return {@code String} Im_default_value
  */
System.out.println(opt);    
String opt_2 = Optional.ofNullable(name).orElseGet(()->"Im_default_value" );


/**
  * With Lambda 
  */
public class OrElseOptionalDemo{ 
   //...
  public static String getDefaultValue(){
    System.out.println("calling getDefaultValue");
    retrun "Im_default_value";
  }
}

/**
 * In <pre> public static void main() </pre>
 */
String name = null;
String name2 = Optional.ofNullable(name).orElse(getDefaultValue());

// or using {@code orElseGet(class::method)}
String name3 = Optional.ofNullable(name).orElseGet(OrElseOptionalDemo::getDefaultValue);
```

### Different btw `.orELse` and `.orElseGet`
```java

String name = "Im_not_Null";
/**
  * {@code orElse(Method())} 
  */
String name2 = Optional
               .ofNullable(name)
                /**
                 * {@code getDefaultValue()} always will be called
                 * {@code ofNullable(name)} is null whether or not
                 */
               .orElse(getDefaultValue());

/**
 * {@code orElseGet(class::method)} 
 */
String name3 = Optional
               .ofNullable(name)
               /**
                 * {@code orElseGet} with {@code OrElseOptionalDemmo::getDefaultValue} 
                 * means this method may not be called 
                 */
               .orElseGet(OrElseOptionalDemo::getDefaultValue);
```


## `.filter( para -> condition )`
```java
String password = "12345";
Optional<String> opt = Optional.ofNullable(password);
System.out.println(opt.filter(pwd -> pwd.length() > 6).isPresent());

/**
  * <p> 
  * {@code Predicate<T>} as the paramters
  * for {@code filter} and {@code and}
  * </p>
  */
Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;

/**
 * <p> 
 * Use {@code and } 
 * To filter multiple conditions
 * </p>
 */
password = "1234567";
opt = Optional.ofNullable(password);
boolean result = opt.filter(len6.and(len10)).isPresent();
```

## `map(class::method)` and `map(para->method)`
```java
String name = "ToMap";
/**
 * <p> 
 * Creating a {@code Optional<String>} instance 
 * named nameOptional 
 * </p>
 */
Optional<String> nameOptional = Optional.of(name);

/**
 * <p>
 * Use {@code map(String:length)}
 * instead of {@code nameOptional.get().length()} 
 * </p>
 */
Optional<Integer> intOpt = nameOptional.map(String::length);
System.out.println(intOpt.orElse(0));
```

### filter and map 
```java
/**
 * <p> 
 * The use of {@code filter} and {@code map} 
 * </p>
 */
String password = "password";
Optional<String>  opt = Optional.ofNullable(password);

Predicate<String> len6 = pwd -> pwd.length() > 6;
Predicate<String> len10 = pwd -> pwd.length() < 10;
Predicate<String> eq = pwd -> pwd.equals("password");

/**
  * <p> 
  * {@code map} the {@code opt} to lowerCase 
  * and {@code filter} the length btw 6 and 10
  * check if it equals <pre> password </pre>
  * </p>
  */
boolean result = opt.map(String::toLowerCase).filter(len6.and(len10 ).and(eq)).isPresent();
```
