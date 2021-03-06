# Garbage Collection and Copy

## Copy Object

[Reference](https://www.techiedelight.com/copy-objects-in-java/)
[Reference](https://www.techiedelight.com/clone-method-in-java/)

###  Shallow copy (field-by-field copy)
Shallow Copy llocates a new, uninitialized object and copy all fields (class' attributes) from the original object in it.  

**But if the field is a reference to an object, as it copies the reference, hence referring to the same object as an original object does.** 
> **The referenced objects are shared, so if one object is modified, the change is visible in the other.**  

![image](https://user-images.githubusercontent.com/68631186/126028142-8e136aca-b393-41e9-a8ab-a7e23a71d710.png)

### Deep Copy
An alternative to shallow copy is a deep copy, where **new objects are created for any referenced objects rather than references to objects being copied**.  

> **A deep copy is a preferable approach over a shallow copy.**  

![image](https://user-images.githubusercontent.com/68631186/126028137-434a5370-62eb-484b-a987-e9fed44ce8ac.png)


> Shallow Copy : Reference to same object (reference is shared)
> Deep Copy    : Create the new object for any referenced objects

### Approach To Copy

1. Copy Constructor
2. clone method
3. Serialization/Utils

#### Copy Consturctor

```java
class Student
{
    private String name;
    private int age;
    /**
      * <p> mutable </p>
      */
    private Set<String> subjects;
 
    public Student(String name, int age, Set<String> subjects)
    {
        this.name = name;
        this.age = age;
        this.subjects = subjects;
    }
 
    /**
      * <p> Copy constructor </p>
      */
    public Student(Student student)
    {
        this.name = student.name;
        this.age = student.age;
 
        /**
          * <p> shallow copy </p>
          */
        // this.subjects = student.subjects;
 
        /**
          * <p> deep copy 
          *     creates a new instance of {@code HashSet} 
	  * </p>
          */
        this.subjects = new HashSet<>(student.subjects);
    }
 
    /**
      * Copy factory with {@code static} keyword
      */
    public static Student newInstance(Student student) {
        return new Student(student);
    }
 
    @Override
    public String toString() {
        return Arrays.asList(name, String.valueOf(age),
                        subjects.toString()).toString();
    }
 
    public Set<String> getSubjects() {
        return subjects;
    }
}
 
class Main
{
    public static void compare(Object ob1, Object ob2)
    {
        if (ob1 == ob2) {
	    /**
	      * <p> reference to the same object </p>
	      */
            System.out.println("Shallow Copy");
        }
        else {
            System.out.println("Deep Copy");
        }
    };
 
    public static void main(String[] args)
    {
        Student student = new Student("Jon Snow", 22, new HashSet<String>(
                            Arrays.asList("Maths", "Science", "English"))
                        );
 
        Student clone = new Student(student);
        System.out.println("Calling Copy Constructor: Clone is " + clone);
 
        compare(student.getSubjects(), clone.getSubjects());
 
        clone = Student.newInstance(student);
        System.out.println("\nCalling Copy Factory: Clone is " + clone);
 
        compare(student.getSubjects(), clone.getSubjects());
    }
}
```

#### `clone` method from `Cloneable` interface
If the concrete type of the object to be cloned is known in advance, we can use the `Object.clone()` method, which creates and returns a copy of the object.  

All classes involved must implement the `Cloneable` interface, otherwise `CloneNotSupportedException` will be thrown. 
```java
/**
  * @Description
  *   The prototype of {@code clone}
  *   
  * @throws ClineNotSupportedException
  */
protected Object clone() throws CloneNotSupportedException

// For example 

/**
  * <p> Shallow Copy </p>
  */
class StudentShallow implements Cloneable {
 
    private String name;                // immutable field
    private int age;                    // primitive field
    private Map<String, Integer> map;   // mutable field
 
    public Student(String name, int age)
    {
        this.name = name;
        this.age = age;
        this.map = new HashMap<String, Integer>() {{ put(name, age); }};
    }
 
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();    // return shallow copy
    }
}

/**
  * <p> Deep Copy </p>
  */
class StudentDeep implements Cloneable {
 
    private String name;               
    private int age;                   
    private Map<String, Integer> map;   
  
    public Student(String name, int age)
    {
        this.name = name;
        this.age = age;
	
        this.map = new HashMap<String, Integer>() {{ put(name, age); }};
    }
 
    @Override
    public Student clone() throws CloneNotSupportedException {
        
	/**
	  * <p> primitive fields are ignored, as their content is already copied </p>
          * <p> immutable fields like {@code String} are ignored </p> 
	  */
	Student studentDeepCopy = (Student) super.clone();
 
        /** 
	  * <p> Create new objects for any 
	  *     non-primitive, mutable fields 
	  * </p> 
          */
	studentDeepCopy.map = new HashMap<>(map);
 
        return studentDeepCopy ;        // return deep copy
    }
}
 
// in the main method
Student student = new StudentDeep("Jon Snow", 22);
 
try {
    Student clone = student.clone();
} catch (CloneNotSupportedException ex) {
    ex.printStackTrace();
}

```

#### Using Serialization
Serialization is the process of converting an object into a sequence of bytes and rebuilding those bytes later into a new object.

> Deep copy using `Object.clone()` is very tedious to implement, error-prone, and difficult to maintain.  
> `Object.clone()` method also will not work if we assign a value to a `final` field.

- Java provides automatic serialization, which requires that the object be marked by implementing the `java.io.Serializable interface`. 
  > Implementing the interface marks the class as ???okay to serialize???, and Java then handles serialization internally. 
  > Serialization won???t work on transient fields.


```java
/**
  * <p> There are no serialization methods 
  *      defined on the Serializable interface </p>
  * <p> so we will use {@code writeObject} and {@code readObject} 
  *      to serialize/deserialize the data </p>
  */
  
/**
 * {@code ByteArrayOutputStream} creates a byte array output stream 
 * {@code ObjectOutputStream(byteArray OutputStreams} 
 * 	      creates an object output output stream 
 * {@code objectOutputStream.writeObject} converts the object into a serialized form
 * {@code objectOutputStream.flush} flush the data in the device/storage
 */
ByteArrayOutputStream bos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(bos);
oos.writeObject(student);        
oos.flush();

/**
 * {@code toByteArray()} creates and returns a copy of the stream's byte array
 * {@code ByteArrayInputStream} creates an object input stream
 * {@code readObject()} deserialize the {@code ByteArrayInputStream} object
 */
byte[] bytes = bos.toByteArray(); 
ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
ObjectInputStream ois = new ObjectInputStream(bis);
clone = (Student) ois.readObject();        // deserialize & typecast
```

#### Apache commons `SerializationUtils`
It provides `serialize()` and `deserialize()` methods to serialize and deserialize an object, respectively. Code is shown below:

```java
// Serializes `Student` object to a `byte[]` array
byte[] bytes = SerializationUtils.serialize(student);

// Deserializes `Student` object from an array of bytes
Student clone = (Student) SerializationUtils.deserialize(bytes);
```

## Java Garbage collection 
???Java???????????????????????????????????????????????????
- ??????Garbage collection??????????????????????????????(Object)??????????????????????????????????????????
- **Garbage collection????????????Heap???????????????**

???????????????????????????(Reference)??????Trigger Garbage collection?????????,reallocate Object?????????????????????
> ??????????????????????????????????????????????????????????????????memory leak???????????????????????????????????????

For Example
```java
public class GarbageCollectionExample {

	  int results1;
	  String results2;
	 
	  /**
      * @Description
      *   Set the Content up
      */ 
    public void setData(int results3,String results4){
      results1=results3;
      results2=results4;
    }
	  /**
      * @Description
      *   Display the Content
      */
	  public void showData(){
	    System.out.println("Value of results1 = " + results1);
	    System.out.println("Value of results2 = " + results2);
	  }
	  
	  public static void main(String args[]){
      /**
        * new a {@code GarbageCollectionExample} instance
        */
      GarbageCollectionExample object1 = new GarbageCollectionExample(); 
      GarbageCollectionExample object2 = new GarbageCollectionExample(); 
      
      /**
        * <p> set and show the content of the object </p>
        */
      object1.setData(100,"A"); 
      object2.setData(50,"B");
      object1.showData(); 
	    object2.showData(); 
	    
      /**
        * new a {@code GarbageCollectionExample} instance
        */
	    GarbageCollectionExample object3; 
	    
      /**
        * <p> copy {@code object2}
        *     to {@code object3} ,/p>
        */
	    object3=object2; 
	    object3.showData();
	    
      /**
        * <p> Remove all the reference </p>
        */
	    object2=null; 
	    
	    object3.showData();
    
	    object3=null; 
	    
      object1.showData();
      
      /**
        * @throws NullPointerException
        */
	    object3.showData();
	  }
}
```
