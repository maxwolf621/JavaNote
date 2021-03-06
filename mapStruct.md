# [MapStruct](https://mapstruct.org/documentation/stable/reference/html/#invoking-custom-mapping-method)
- mappers generated by MapStruct are stateless and thread-safe and thus can safely be accessed from several threads at the same time

`@Mapper` : This Annotation tells the Compiler to using `org.mapstruct`   
`@Mapping` : To Map fields of class A and fields of class B   

```java
/**
  * Dependency Injection Via Spring
  */
@Mapper(componentModel = "spring")
public interface BasicObjectMapper<SOURCE, TARGET> {

    @Mappings({
        @Mapping(...),
        @Mapping(...), 
        ...})
    @InheritConfiguration
    TARGET to(SOURCE var1);

    /**
      * <p> List </p>
      */
    @InheritConfiguration
    List<TARGET> to(List<SOURCE> var1);
 
    @InheritInverseConfiguration
    SOURCE from(TARGET var1);

    /**
      * <p> List </p>
      */
    @InheritInverseConfiguration
    List<SOURCE> from(List<TARGET> var1);
}
```

## Retrieving a mapper (Instance)

By convention, a mapper interface should define a member called `INSTANCE` which holds a single instance of the mapper type  
The following pattern makes it very easy for clients to use mapper objects without repeatedly instantiating new instances:
```java

@Mapper
public interface AMapper {

    AMapper INSTANCE = Mappers.getMapper( AMapper.class );

    ADto mapToADto(A a);
}

@Mapper
public abstract class AMapper {

    public static final AMapper INSTANCE = Mappers.getMapper( AMapper.class );

    ADto mapToADto(A a);
}

// To access the ampper
A a = ...;
ADto dto = AMapper.INSTANCE.mapToADto(a);
```

## [`@Mapper(componentModel = ...)` Dependency Injection](https://mapstruct.org/documentation/stable/reference/html/#using-dependency-injection)

To use Dependency Injection 
```java
@Mapper(componentModel = "cdi")
public interface CarMapper {

    CarDto carToCarDto(Car car);
}
```
- The generated mapper implementation will be marked with the `@ApplicationScoped` annotation and thus can be injected into fields, constructor arguments etc. 
  >　using the `@Inject` annotation:
```
@Inject
private CarMapper mapper;
```

When using dependency injection, you can choose between `field` and `constructor` injection.  

This can be done by either providing the injection strategy via `@Mapper` or `@MapperConfig` annotation.
```java
@Mapper(componentModel = "cdi", uses = EngineMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarMapper {
    CarDto carToCarDto(Car car);
}
```
The generated mapper will inject all classes defined in the `uses` attribute.   
- When `InjectionStrategy#CONSTRUCTOR` is used, the constructor will have the appropriate annotation and the fields won’t.   
- When `InjectionStrategy#FIELD` is used, the annotation is on the field itself. For now, the default injection strategy is field injection, but it can be configured with Configuration options. 
  > **It is recommended to use constructor injection to simplify testing.**

## Basic Mapping 

If A and B both fields have the same name
```java
@Mapper 
class interface Mapper_Class_Name{
    A toA(B b)
 }
```


###  Mapping Fields With Different Field Names

If A's `field_A` and B's `field_B` both have different name but need to map together.   

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
 
// or using dot notation
@Mapper 
class interface Mapper_Class_Name{
    // B maps to A
    @Mapping(target ="field_A", 
             source ="b.field_B")
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

## Mapping With an Abstract Class

To customize our mapper in a way that exceeds @Mapping capabilities.

```java
@Mapper
abstract class TransactionMapper{
    public abstract A toA(B b){    
        A a = new A();

        // a.setter = b.getter
        a.set1(b.get1() );
        a.set2(b.get2() );

        retrun a
    }
 
    public abstract list<A> maptoA(collecation<B> bs)
}
 
 
@Generated
class TransactionMapperImpl extends TransactionMapper {

    @Override
    public List<A> toA(Collection<b> bs) {
        if ( bs == null ) {
            return null;
        }

        List<A> listA = new ArrayList<>();
        for ( B b : bs ) {
            listA.add( toA( b ) );
        }

        return listA;
    }
}
 ```

## [before-after Mapping](https://www.baeldung.com/mapstruct#before-after-mapping)

Another way to customize `@Mapping` capabilities by using `@BeforeMapping` and `@AfterMapping` annotations.  
The annotations are used to mark methods that are invoked right before and after the mapping logic.
 
## [multiple objects mapping](https://mapstruct.org/documentation/stable/reference/html/#invoking-custom-mapping-method)
 
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
  @Mapping(source = "A.FieldA", target = "FieldC_A")
  @Mapping(source = "B.FieldB", target = "FieldC_B")
  public C ToC(A a,  B b)
}
```

## [Mapping Nested Bean](https://www.baeldung.com/mapstruct#Beans)

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

@Mapper
class interface mapper_Name{
  @Mapping(target = "B.name" ,source ="name")
  B mapToB(C c); 
  
  //...
}

```

## Mappings with direct field access

MapStruct also supports mappings of `public` fields that have no `getters/setters`.   

MapStruct will use the fields as `read/write` accessor if it cannot find suitable `getter/setter` methods for the property.

A field is considered as a `read` accessor if it is `public` or `public final`. If a field is `static` it is not considered as a read accessor.

A field is considered as a `write` accessor only if it is `public`. If a field is `final` and/or `static` it is not considered as a write accessor.

```
public class A {

    private Long id;
    private String name;

    //getters and setter omitted for brevity
}

public class ADto {

    public Long id;
    public String ADtoName;
}

@Mapper
public interface Mapper {

    AMapper INSTANCE = Mappers.getMapper( AMapper.class );

    @Mapping(source = "ADtoName", target = "name")
    A toA(ADto aDto);

    @InheritInverseConfiguration
    ADto fromA(A a);
}

/** It generates the code like this **/
public class AMapperImpl implements AMapper {

    @Override
    public A toA(ADto aDto) {
        // ...
        a.setId( aDto.id );
        a.setAname( aDto.ADtoName );
        // ...
    }

    @Override
    public ADto fromA(A a) {
        // ...
        aDto.id = a.getId();
        aDto.AdtoName = a.getName();
        // ...
    }
}
```

## [Using builders](https://mapstruct.org/documentation/stable/reference/html/#mapping-with-builders)
MapStruct also supports mapping of `immutable` types via builders.   

When performing a mapping MapStruct checks if there is a builder for the type being mapped. This is done via the BuilderProvider SPI.   

If a Builder exists for a certain type, then that builder will be used for the mappings.

```java
public interface PersonMapper {

    Person map(PersonDto dto);
}

// GENERATED CODE
public class PersonMapperImpl implements PersonMapper {

    public Person map(PersonDto dto) {
        if (dto == null) {
            return null;
        }

        Person.Builder builder = Person.builder();

        builder.name( dto.getName() );

        return builder.create();
    }
}
```

## Using Constructor

MapStruct supports using `constructor`s for mapping target types.  
When doing a mapping MapStruct checks if there is a builder for the type being mapped. 

- If there is no builder, then MapStruct looks for a single accessible constructor.

MapStruct will pick these constructors
- with `@Default`
- parameterless  Constructor 
- A single `public` Constructor

```java
public class Person{
    //..
    
    Public Person(String name, Stirng surname){
        //...
    }
}

// GENERATED CODE
public class PersonMapperImpl implements PersonMapper {

    public Person map(PersonDto dto) {
        if (dto == null) {
            return null;
        }

        String name;
        String surname;
        name = dto.getName();
        surname = dto.getSurname();

        Person person = new Person( name, surname );

        return person;
    }
}
```

## [Conversion](https://mapstruct.org/documentation/stable/reference/html/#implicit-type-conversions)

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

## Default 

### defaultValue
To pass the default value in case source property is `null` using `defaultValue` attribute of `@Mapping` annotation.
 
```java
@Mapping( target = "target-property", 
          source="source-property",
          defaultValue = "default-value")
```


### [defaultValueExpression](https://www.baeldung.com/mapstruct#Conclusion-1)

Use java method to set up defaultValue

```java
// if If the source-property field of the source entity is null
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



## [expression](https://mapstruct.org/documentation/stable/reference/html/#expressions)
```java
@Mapping(target = "TargetProperty", 
   expression = "java(TargetMethod( .... ))")
```
- or we can use `qualifiedByName` attribute which makes code more flexible

```java
@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    // fully qualified package name is specified
    @Mapping(target = "timeAndFormat",
         expression = "java( new org.sample.TimeAndFormat( s.getTime(), s.getFormat() ) )")
    Target sourceToTarget(Source s);
}


imports org.sample.TimeAndFormat;

@Mapper( imports = TimeAndFormat.class )
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    /**
      * with {@code i,ports = TimeAndFormat.class}
      */
    @Mapping(target = "timeAndFormat",
         expression = "java( new TimeAndFormat( s.getTime(), s.getFormat() ) )")
    Target sourceToTarget(Source s);
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
