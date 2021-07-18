# Serialize and deSerialize
![](https://lh3.googleusercontent.com/pw/ACtC-3fWwV19Qwwj0FhNmsbCM-sO3s04TtHrfuARmKuamHBDQ7ao8ZwQHhmghqptiHQvhsMn5wB3hENTyXpFPACLKlLIIprzqkYOrXQ5umd4K6q_ZYZhH1b6SOiyTHb4Lj57Zc7-zM0j7F0pxLBdxNuH8hCh=w750-h300-no?authuser=0)  
Persitent : Data stores in devices such hard driver .. etc  

Usage of Serialization    
- Client and Server Transport (Cookie ... etc)  
- via IO stream write and flush the data as serial in the device or read out the data from device [code](https://matthung0807.blogspot.com/2019/01/java-serializedeserialize.html)


## [Object Casts](https://stackoverflow.com/questions/1555326/java-class-cast-vs-cast-operator)  
```java
/**
  * @Description
  *     Cast the object dataType as T
  */
@SuppressWarnings("unchecked")
<T> T doSomething() {
    Object o;
    // snip
    return (T) o;
}
/** 
  * @Description
  *  Using 
  *  {@code className.nameParmater.cast(To_this_instance_of_dataType)}
  */
<T> T doSomething(Class<T> cls) {
    Object o;
    // snip
    return cls.cast(o);
}
```

## Serialize
[more details](https://iter01.com/419189.html)

- **All objects that stored in the device** have a serial code id
- 當程式試圖序列化一個物件時，會先檢查此物件是否已經序列化過，只有此物件從未（在此虛擬機器）被序列化過，才會將此物件序列化為位元組序列輸出(**A instance will not be serialized if it have been serialized**)
    > 如果此物件已經序列化過，則直接輸出編號即可
- 由於java序利化演算法不會重複序列化同一個物件，只會記錄已序列化物件的編號。
  > **如果序列化一個可變物件（物件內的內容可更改）後，更改了物件內容，再次序列化，並不會再次將此物件轉換為位元組序列(byte[] byte)，而只是儲存序列化編號**
```java
/**
  * <p> 
  * To Serialize and DeSerialize the object
  * </p>
  */
public class WriteObject {
    public static void main(String[] args) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"));
             ObjectInputStream ios = new ObjectInputStream(new FileInputStream("person.txt"))) {
       
                Person person = new Person("Tom", 23);
                oos.writeObject(person); // write into device (serialize)
                person.setName("Jimmy"); // person's name changes from Tom to Jimmy
                System.out.println(person); 
                
                /**
                  *  {@code writeObject} will not serialize object person again
                  *  it only save the serial code
                  */
                oos.writeObject(person);  
                
                /** 
                  * deserialize (read out) via {@code readObject}
                  */
                Person p1 = (Person) ios.readObject();
                Person p2 = (Person) ios.readObject();
                /**
                  * <p> p1 and p2 has same serial code</p>
                  * @return {@code true}
                  *
                System.out.println(p1 == p2);  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## transient

it means **Ignoring these attribute to be serialized**

```java
@Data 
public class Person implements Serializable {
   
   /**
     * {@code transient} let 
     * Object T as {@code null} 
     * and boolean type T as {@code false}
     * if object is serialized
     */
   private transient String name; // set as null if this.object is serialized
   private transient int age;     // set as null if this.object is serialized
   private transient boolean singlehood;   // set false if this.object is serialized
   private int height;
   
   public Person(String name, int age) {
       this.name = name;
       this.age = age;
   }
}


public class TransientTest {
   public static void main(String[] args) throws Exception {
       try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"));
       ObjectInputStream ios = new ObjectInputStream(new FileInputStream("person.txt"))) {
           Person person = new Person("tom", 23);
           person.setHeight(185);
           /** 
             * @return 
             * {@code Person{name='tom', age=23', singlehood=true', height=185cm} }
             */
           System.out.println(person);
           oos.writeObject(person);  // transient activing
           /**
             * @return 
             * Person{name='null', age=0', singlehood=false', height=185cm}
             */
           Person p1 = (Person)ios.readObject(); 
       }
   }
}
```

## Customize De/Serialzier 

A custom Deserializer/Serializer via `Externalizable` interface

```java
/**
  * @apinote 
  *     To customize serializer/deserializer
  *     we need to implement this interface
  */
public interface Externalizable extends java.io.Serializable {
     void writeExternal(ObjectOutput out) throws IOException;
     void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
}

/** 
 * <p> Example </p> 
 * @apinote 
 *      reverse the {@code String} name and serialize 
 *      reverse the {@code String} name and deserialize
 */
public class ExPerson implements Externalizable {
    private String name;
    private int age;
 
    public ExPerson() {}
    public ExPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
      * <p>
      * Custom serializer/deserializer 
      * via Overriding writeExternal/readExternal
      * </p>
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        /**
          * {@code reverse} name and serialize 
          */
        StringBuffer reverse = new StringBuffer(name).reverse();
        System.out.println(reverse.toString());
        out.writeObject(reverse);
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = ((StringBuffer) in.readObject()).reverse().toString();
        System.out.println(name);
        this.age = in.readInt();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ExPerson.txt"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ExPerson.txt"))) {
            
            /**
              * @return {@code String} ydarb
              */
            oos.writeObject(new ExPerson("brady", 23));
            
            /**
              * @return {@code String} brady
              */
            ExPerson ep = (ExPerson) ois.readObject();
            
            /**
              * @return
              * <li> ExPerson{name='brady', age=23} </li>
              */
            System.out.println(ep);
        }
    }
}
```

## serialVersion UID

To set up the each serialize instance's version to allow same instance with different version existing in the device
```java
/**
  * <p> 
  * Modify Method ,Static and Transient varialbe do not affect @version
  * </p>
  */
@Data
public class ExPerson implements Serializable {
    /**
      * <p>
      * the instance of Experson 
      * @version 123456L
      * </p>
      */
    private static final serialVersionUID = 123456L;
  
    private String name;
    private int age;   
}
```



## SerializationUtils 

Using `SerializationUtils` class helping us to `serial`, `deserial` and `clone`

```java
/**
  * <p> {@code clone} 
  * </p>
  */
UserDO user = new User("example", 12); 
UserDO userClone = SerializationUtils.clone(user);

/** 
  *  <p> 
  *  serialize and deserialize 
  *  </p>
  */
byte[] bytes = SerializationUtils.serialize(user);
UserDO userDeserialize = SerializationUtils.deserialize(bytes);
```
- Implemntation of Clonable is better than `StringlizationUtils`'s clone method
- Protostuff、Hessian、Kryo's effinciency are better than `SerializationUtils` 












