###### tags: `JAVA` `Spring`
### [Note From Here](https://kucw.github.io/blog/2020/3/java-lombok/)
# LomBok
[TOC]

`LomBok` makes code elegant  

For example
![](https://i.imgur.com/inZrWpM.png)



## Maven

[Maven Version](https://mvnrepository.com/artifact/org.projectlombok/lombok)

```xml=
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version> <!--look up office page to pick up--> </version>
</dependency>
```



## Annotations


![](https://i.imgur.com/H0vjt5y.png)


If there is a class
```java=
public class User{
    private Integer id  ;
    private String name ;
    
    // setter and getter
    // ToString
}
```


### @Tostring

```java=
public String toString{
    return "User(id = " this.id + ", name = " this.name ")";
}
```

### @EqualAndHashCode
#### `@EqualAndHashCode` equals

```java=
public boolean equals(Object o)
{
    //...
}
public int hashCode
{
    return Object.hash(id,name)
}
```

#### `@EqualAndHashCode(exclude = "id")` equals
```java=
public boolean equals(Object o)
{
    //...
}
public int hashCode
{
    return Object.hash(name)
}
```

## @NoArgsConstructor

equals
```java=
// ...
public User(){}
```


##  @AllArgsConstructor


equals
```java=
//..
public User(id, name){
    this.id = id;
    this.name = name;
}
```

:::info
if we dont create a constructor, java will create a No args Constructor by itself 

With Spring Data JPA , do make sure add annotation `@NoArgsConstructor` while there is `@AllArgsConstrcutor`
:::

## @RequiredArgsConstructor

Specified Attribute will be `final` 

![](https://i.imgur.com/hcyzMlD.png)


## @Data


`@Data` represents
> @Getter/@Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor


## @Value

All the Attributes will be `final` type


:::danger
上面那個 @Data 適合用在 POJO 或 DTO 上，而這個 @Value 注解，則是適合加在值不希望被改變的類上，像是某個類的值當創建後就不希望被更改，只希望我們讀它而已，就適合加上 @Value 注解，也就是 @Value for immutable class

另外注意一下，此 lombok 的注解 @Value 和另一個 Spring 的注解 @Value 撞名，在 import 時不要 import 錯了
:::


## @Builder

To represent with setters

![](https://i.imgur.com/P9u4632.png)


## @Slf4j

![](https://i.imgur.com/rGbxUUo.png)
