# MapStruct

[Reference](https://www.itread01.com/content/1559145662.html)  

`@Mapper` : This Annotation tells the Compiler to using `org.mapstruct` 
`@Mapping` : To Map fields of class A and fields of class B 

## Basic Mapping 
If A and B both fields have the same name
```java
@Mapper 
class interface Mapper_Class_Name{
    A toA(B b)
 }
```

If A's field_A and B's field_B both have different name but need to map together.   
For such case, we use attributes `target` and `source` of `@Mapping` annotation

```java
/* A.java */
public class A{
    private object<?> fieldA;
    //....
}

/* B.java */
public class B{
    private object<?> fieldB;
    // ...
 }

```

```java
@Mapper 
class interface Mapper_Class_Name{
    // B maps to A
    @Mapping(target ="field_A", 
             source ="field_B")
    A toA(B b)
 }
```

Declaration
```java
public static void main(String[] args)
{
  private Mapper_Class_Name mapperInstance = Mappers.getMapper(StudentMapper.class);
}
```

## Custom Mapping 

```java
@Mapper
class interface Mapper_Class_Name{
  default A toA(B b){
    
    A a = new A();
    a.set1(b.get1() );
    a.set2(b.get2() );
    
    // a.setter = b.getter
    retrun a
 }
 ```
 
## multiple objects mapping
 
To map >2 classes  
 
For example 
If C maps to A and B
```java 
/* A */
public class A{
  private FieldA;
  //...
}

/* B */
public class B{
  private FieldB;
  //...
}

/* C */
public classC{
  private FieldC_B;
  private FieldC_A;
  //...
}

/* Map Struct */
@Mapper
class interface Mapper_Name{
  @Mapping(target = "A.FieldA", source = "FieldC_A")
  @Mapping(target = "B.FieldB", source = "FieldC_B")
  public C ToC(A a,  B b)
}
```

## Mapping Nested Bean

Es gibt A,B und C klasses

When Class A contains object of class B as the field  

If C needs to map to A then 
```java
public class B{
  //...
  private ? fieldB
}

// A contains B as field
pubic class A{
  //...
  private B b;
  //...
}

public class C{
  //....
  private ? fieldC
}

/* C to C */
@Mapper
class interface mapper_Name{
  @Mapping(target = "B.name" ,source ="name")
  B CtoB(C c); 
  //...
}

```

## Direct Field

```java
@Mapper
class interface mapper_Name{
  @Mapping(target = "B.name" ,source ="name")
  B CtoB(C c);
  
  @InheritInverseConfiguration
  C BtoC(B b);
}
```

## Mapping mit `.Builder()` 


## Conversion 

Map two different data type fields

### numberFormat

`@mapping(numberformat = "$#.00")`

for example
```java
/* A */
pubic class A{
    private double price;
    //...
}
/* B */
public class B{
    private string price;
    //..
}

/* Mapper */
@Mapper
public interface Mapper{
    @Mapping(target = "price" , source= = "price" , numberformat = "$@.00")
    B AtoB(A a);
}
```

### dataFormat

Conversion of data to `String`

```java
/* A */
public class A{
    private String Date; // String type data
    //...
}
/* B */
import java.util.GregoriaCalender;
public class B{
    private GregoriaCalender Date;
    // ... 
}

/* Mapper */
@Mapper
public interface Mapper{
    // GregoriaCalender maps to String
    @Mapping(source = "Date" , target = "Date" , dataFormat = "dd.MM.yyyy")
    A maptoA(B b);
}
```

 
### Constant
 
Map a constant value to target
```java
mapping(target = "ConstantMapMe" , constant = "MeMapTarget") 
// assign MeMapTarget to ConstantMapMe
```
 
### defaultValue
To pass the default value in case source property is `null` using `defaultValue` attribute of `@Mapping` annotation.
 
```java
@Mapping( target = "target-property", 
          source="source-property",
          defaultValue = "default-value")
```

### expression

```java
@Mapping(target = "TargetProperty", 
   expression = "java(TargetMethod( .... ))")
```
- or we can use `qualifiedByName` attribute which makes code more flexible

### defaultValueExpression 

Use java method to set up defaultValue

```java
@Mapping(target = "target-property", 
         source="source-property" 
         defaultExpression = "java(default-value-method)")
```

For example
```java
/* A */
public class A{
    private String name;
    //..
}
/* B */
pubic class B{
    private String name;
    //..
}
/* Mapper */
public interface mapper{
    @Mapping(target = "name" , 
             source = "name" , 
             defaultExpression = "java(UUID.randomUUID().toString())")
    B AtoB(A a);
}
```

## Mapping Collections 

### list

```java
@Mapper
public interface Mapper{
    //..
    List<objectB> ToObjectB(List<objectA> As)
}

/* In Main */
objectA a = //...
objectA b = //...
objectA c = //...

List<objectA> listA = Array.asList(a,b,c);
List<objectB> listB = Mapper.toObjectB(listA);
```

### [`@MapMapping`](https://mapstruct.org/documentation/stable/api/org/mapstruct/MapMapping.html)

Configures the mapping between two `Map<?,?>` data types, e.g. `Map<String, String>` and `Map<Long, Date>`.

```java
@Mapper
public interface UtilityMapper {
   @MapMapping(valueDateFormat = "dd.MM.yyyy")
   Map<String, String> getMap(Map<Long, GregorianCalendar> source);
}
```


## [Enum](https://mapstruct.org/documentation/stable/api/org/mapstruct/ValueMapping.html)

Map two Enum data types via `@ValueMapping`

```java
public enum OrderType { 
    RETAIL, 
    B2B, 
    EXTRA, 
    STANDARD, 
    NORMAL 
}
public enum ExternalOrderType { 
    RETAIL, 
    B2B, 
    SPECIAL, 
    DEFAULT 
}

// OderType maps to ExternalOrderType
public interface MAPPER{
     @ValueMapping( source = MappingConstants.NULL, target = "DEFAULT" ),
     @ValueMapping( source = "STANDARD", target = MappingConstants.NULL ),
     // All rest fields in OrderType will map to 'SEPCIAL' of ExternalOrderType
     @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "SPECIAL" )
     ExternalOrderType orderType_TO_ExternalOrderType(OrderType orderType);
}
```

```
 Mapping result:
 +---------------------+----------------------------+
 | OrderType           | ExternalOrderType          |
 +---------------------+----------------------------+
 | null                | ExternalOrderType.DEFAULT  |
 | OrderType.STANDARD  | null                       |
 | OrderType.RETAIL    | ExternalOrderType.RETAIL   |
 | OrderType.B2B       | ExternalOrderType.B2B      |
 | OrderType.NORMAL    | ExternalOrderType.SPECIAL  |
 | OrderType.EXTRA     | ExternalOrderType.SPECIAL  |
 +---------------------+----------------------------+
```

source's valid value
```
enum constant name
MappingConstants.NULL
MappingConstants.ANY_REMAINING
MappingConstants.ANY_UNMAPPED
```

target's valid value
```java
enum constant name
MappingConstants.NULL
```

## Map Stream

```java
@Mapper
public interface UtilityMapper {
   Stream<String> getStream(Stream<Integer> source);
}

// build a stream<Integer>
Stream<Integer> numbers = Arrays.asList(1, 2, 3, 4).stream();

// Stream<integer> maps to Stream<String>
Stream<String> strings = utilityMapper.getStream(numbers);
assertEquals(4, strings.count());	
```
