/**
  * Usage of {@code asList}
  * {@link https://stackoverflow.com/questions/12030661/java-int-array-to-hashsetinteger}
  * {@link https://stackoverflow.com/questions/1073919/how-to-convert-int-into-listinteger-in-java}
  */
// The asList method signature
// Lists are immutable 
public static <T> List<T> asList(T... a)

// Array contain premitives in it  
int arr[] = {1,2,3,4,5,6,7};
List intlst = Arrays.asList(arr);

// Array has Object, not primitives 
List<Integer> list = Arrays.asList(1,2,3);
// or 
List<Integer> list = Arrays.asList(new Integer[] {1,2,3});

/** ERROR USAGE **/
List<Integer> list = Arrays.asList(new int[] {1,2,3});
// because primitive to wrapper coercion (ie. `int[]` to `Integer[]`) is not built into the language  
// As a result, each primitive type would have to be handled as it's own overloaded method, which is what the commons package does. 
// i.e. {@code public static List<Integer> asList(int i...);}

// vai `Arrays.Stream` or `IntStream.of` to make a stream of `int` array
int[] ints = {1,2,3};

//In java 8+ 
List<Integer> list = Arrays.stream(ints) //IntStream
                           .boxed()      //Stream<Integer>
                           .collect(Collectors.toList());
// equivalent
//In Java 16 and later:
List<Integer> list = Arrays.stream(ints)
                           .boxed()
                           .toList();
// via IntStream
IntStream.of(ints) // return IntStream
         .boxed() // Stream<Integer>
         .collect(Collectors.toList());
// via guava libraries
// {@code import com.google.common.primitives.Ints;} 
List<Integer> Ints.asList(ints);



/**
  * <p> Conver {@code List} to {@code Map}
  * {@link [List to Map](https://matthung0807.blogspot.com/2019/12/java-8-lambda-list-to-map.html)}
  * via {@code stream()}
  */
public class Item {

   private Long id;
   private String name;

   public Item(Long id, String name) {
       this.id = id;
       this.name = name;
   }

   public String toString() {
       return "{id=" + id + "," + "name=" + name +  "}";
   }

   // getter setters...
}

// In Main 
List itemList = Arrays.asList(
        new Item(1L, "Stone"),
        new Item(2L, "Grass"),
        new Item(3L, "Dirt"));

// for each key-value
itemMap.forEach((k, v) -> {
    System.out.println("key:" + k + ", value:" + v);
});

/**
  * <p> for each </p>
  */
public class Main {
     public static void main(String[] args) {
 
         List<String> list =  Arrays.asList("matt","john","gary");
 
        // for loop
         for(int i = 0 ; i < list.size() ; i++ ) {
             System.out.println(list.get(i));
         }
 
         // for-each loop
         for(String s : list) {
             System.out.println(s);
         }
 
         //Java 8 forEach()

Map<Long, Item> itemMap = itemList.stream()
                                  .collect(Collectors.toMap(Item::getId, Funct
         list.forEach(new Consumer<String>() {
             @Override
             public void accept(String s) {
                 System.out.println(s);
             }
         });
 
         // Java 8 forEach() Lambda
         list.forEach(s -> System.out.println(s));
 
         // Java 8 forEach() Lambda Method References
         list.forEach(System.out::println);
     }
 }
/**
  * {@link https://matthung0807.blogspot.com/2018/08/java-8-method-references.html}
  * <p> Reference Method </p>
  * <pre> ClassName :: method </p>
  */
public class Main {

    public static void main(String[] args) {

        List<String> strList = Arrays.asList("A","B","C");

        // Anonymous class
        strList.forEach(new Consumer<String>() {
            @Override
            public void accept(
             String s) {
                System.out.print(s); // ABC
            }
        });

        // Lambda expression
        strList.forEach(s -> System.out.print(s)); // ABC

        // Method reference
        strList.forEach(System.out::print); // ABC

        // Method reference(方法參考) 使用靜態方法
        strList.forEach(Main::static_printList); // ABC

        // Method reference(方法參考) 使用實例方法
        Main main = new Main();
        strList.forEach(main::instance_printList); // ABC

    }

    private static void static_printList(String s) {
        System.out.print(s);
    }

    private void instance_printList(String s) {
        System.out.print(s);
    }
}
