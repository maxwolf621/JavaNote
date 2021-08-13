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

