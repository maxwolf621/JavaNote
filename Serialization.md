# Serialize and deSerialize
![](https://lh3.googleusercontent.com/pw/ACtC-3fWwV19Qwwj0FhNmsbCM-sO3s04TtHrfuARmKuamHBDQ7ao8ZwQHhmghqptiHQvhsMn5wB3hENTyXpFPACLKlLIIprzqkYOrXQ5umd4K6q_ZYZhH1b6SOiyTHb4Lj57Zc7-zM0j7F0pxLBdxNuH8hCh=w750-h300-no?authuser=0)  
Persitent : Data stores in devices such hard driver .. etc  

Usage 
- Client and Server Transport (Cookie ... etc)
How ?
- via IO stream write and flush the data as serial in the device or read out the data from device [code](https://matthung0807.blogspot.com/2019/01/java-serializedeserialize.html)


[object cast](https://stackoverflow.com/questions/1555326/java-class-cast-vs-cast-operator)   
```
@SuppressWarnings("unchecked")
<T> T doSomething() {
    Object o;
    // snip
    return (T) o;
}
// using className.nameParmater.cast(To_this_instance_of_variable)
<T> T doSomething(Class<T> cls) {
    Object o;
    // snip
    return cls.cast(o);
}
```


## Serialize
[more details](https://iter01.com/419189.html)

- A instance will not be serialized if it have been serialized 
- All objects that stored in the device have a serial code id
- 當程式試圖序列化一個物件時，會先檢查此物件是否已經序列化過，只有此物件從未（在此虛擬機器）被序列化過，才會將此物件序列化為位元組序列輸出。 如果此物件已經序列化過，則直接輸出編號即可。

由於java序利化演算法不會重複序列化同一個物件，只會記錄已序列化物件的編號。
  > 如果序列化一個可變物件（物件內的內容可更改）後，更改了物件內容，再次序列化，並不會再次將此物件轉換為位元組序列(byte[] byte)，而只是儲存序列化編號。
```java
public class WriteObject {
    public static void main(String[] args) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"));
             ObjectInputStream ios = new ObjectInputStream(new FileInputStream("person.txt"))) {
       
                Person person = new Person("Tom", 23);
                // write into device (serialize)
                oos.writeObject(person); //Person{name='9龍', age=23}


               
                person.setName("Jimmy");    // modify instance person
                System.out.println(person); //Person{name='海賊王', age=23}
                /*****************************
                *   write the same object person
                oos.writeObject(person);  
                
                // deserialize (read out)
                Person p1 = (Person) ios.readObject();
                Person p2 = (Person) ios.readObject();
                System.out.println(p1 == p2);  // same serial code id
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## transient

it means **Ignoring these attribute to be serialized**

@Data 
public class Person implements Serializable {
   private transient String name; // set as null if this.object is serialized
   private transient int age;  // set as null if this.object is serialized
   private int height;
   private transient boolean singlehood; // set false if this.object is serialized
   public Person(String name, int age) {
       this.name = name;
       this.age = age;
   }
}

public class TransientTest {
   public static void main(String[] args) throws Exception {
       try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.txt"));
            ObjectInputStream ios = new ObjectInputStream(new FileInputStream("person.txt"))) {
           Person person = new Person("9龍", 23);
           person.setHeight(185);
           System.out.println(person);  //Person{name='9龍', age=23', singlehood=true', height=185cm}
           oos.writeObject(person);     // transient activing
           Person p1 = (Person)ios.readObject();  //Person{name='null', age=0', singlehood=false', height=185cm}
       }
       
       //....
   }
}


## Externalizable interface
```java
public interface Externalizable extends java.io.Serializable {
     void writeExternal(ObjectOutput out) throws IOException;
     void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
}

/* Example */
@Data
public class ExPerson implements Externalizable {
    private String name;
    private int age;
 
    public ExPerson() {
    }
    public ExPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //將name反轉後寫入二進位制流
        StringBuffer reverse = new StringBuffer(name).reverse();
        System.out.println(reverse.toString());
        out.writeObject(reverse);
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //將讀取的字串反轉後賦值給name例項變數
        this.name = ((StringBuffer) in.readObject()).reverse().toString();
        System.out.println(name);
        this.age = in.readInt();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ExPerson.txt"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ExPerson.txt"))) {
            oos.writeObject(new ExPerson("brady", 23));
            ExPerson ep = (ExPerson) ois.readObject();
            System.out.println(ep);
        }
    }
}
//輸出結果
//ydarb
//brady
//ExPerson{name='brady', age=23}
```

## serialVersionUID

To set up the each serialize instance's version to allow same instance with different version existing in the device
```java
@Data
public class ExPerson implements Externalizable {
    private static final versionUID = 123456L;
  
    private String name;
    private int age;   
}
```

## SerializationUtils
Model needs to implement `Serializable` interface
```java
@Data
UserDO userClone = SerializationUtils.clone(user);
    //private static final long serialVersionUID = 1L;
    private String name;
    private Integer age;
}
```

Using SerializationUtils class helping us to `serial`, `deserial` and `clone`
```java

/*****************************
*           clone            *
*****************************/
UserDO user = new User().builder().name("example").ange(12).build();
UserDO userClone = SerializationUtils.clone(user);

/*****************************
*  serialize and deserialize *
*****************************/
byte[] bytes = SerializationUtils.serialize(user);
UserDO userDeserialize = SerializationUtils.deserialize(bytes);
```
- Implemntation of Clonable is better than StringlizationUtils's clone method
- Protostuff、Hessian、Kryo's effinciency are better than SerializationUtils 












