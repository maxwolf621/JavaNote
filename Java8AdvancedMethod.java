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


import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) throws Exception {

         
    }
    
}

/**
  * <p> Reference Method </p>
  * <pre> ClassName :: method </p>
  */
public class Main {

    public static void main(String[] args) {

        List<String> strList = Arrays.asList("A","B","C");

        // Anonymous class
        strList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
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
