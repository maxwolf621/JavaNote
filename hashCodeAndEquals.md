###### tags: `JAVA`
# equals(Object obj) and hashCode()
[TOC]

[CodeExample](https://www.jitendrazaa.com/blog/java/what-is-the-need-to-override-hashcode-and-equals-method/)

To override the equals(Object obj)
```java
public boolean equals(Object obj)
```

if `equals` is overridden then `hashCode` will be also need to be overridden.    
> In case there are two same keys existing
```java=
public int hashCode()
```

- this says **same object has the same hash code**
     > *It is not necessary that two different object must have different hashcode values, it might be possible that they share common hash bucket*

## equals(Object obj)
```java=
public boolean equals(Object obj) {  
    return (this == obj);  
}
```
- if `this` and `obj` both reference to the same address 


But if we want **to check the contents of two objects** is equal or not then
```java=
public boolean equals(Object anObject) {  
    if (this == anObject) { 
    // reference to same address ?  
        return true;  
    }  
    
    if (anObject instanceof String) { 
    // Both have same content ?  
        String anotherString = (String)anObject; 
        // this object's count
        int n = count;  
        if (n == anotherString.count) {  
            // compare each character 
            char v1[] = value;  
            char v2[] = anotherString.value;
            // start at the index 0
            int i = offset;  
            int j = anotherString.offset;  
            while (n-- != 0) {  
                if (v1[i++] != v2[j++])  
                    return false;  
            }  
            return true;  
        }  
    }  
    return false;  
```


## hashCode()

this method returns hash code (as a human ID)
> To put it simply,==same object has same hash code but same hash code has not 100% of the same object==



### Before realizing how hashCode() functions

Java has two collections with out using HashMap
1. List
2. Set

Properties of these two collections

- List allows
    >Sequence Elements  
    >Duplicate Elements  
- Set
    > Is not sequence    
    > Contains no duplicate elements

:::info  
Most important is that  
If we dont use hashMap to compare our object's content, we might have time complexity O(n) to compare each elements
::: 

## Hash Code for hashCode method 

**Hash Code is used to narrow the search result**

![](https://i.imgur.com/MDJt9WL.png)
- Every object is placed in Hash bucket depending on the hashcode they have

When a key is inserted in HashMap
first it checks if any other objects present with same hashcode (same bucket)  
- If yes then it checks for the `equal()` method  
- **if two objects are same then HashMap will not add that key instead it will replace the old value by new one**


## A class with without/with overriding hashCode()


### Without overriding
```java=
class movie{
    private String name, actor;
    private int releaseYr
    public movie(name,actor,releaseYr)
    {
        this.name = name;
        this.actor = actor;
        this.releaseYr = releaseYr;
    }
    // getters and setters
}
public class demo{
    public static void main(String[] args){
        HashMap<movie, String> maps ;
        movie m = new movie("A","james",2001);
        movie m1= new movie("B","Tom"  ,1995);
        // it wont update the old one
        movie m2= new movie("A","james",2001);
        maps.put(m,"James");
        maps.put(m1,"Tom");
        maps.put(m2,"Duplicate James");
        
        for(movie mm : map.keySet()){
            System,out.println(map.get(mm).toString);
        }
        /* prin out as the followings
         * James
         * Tom
         * Duplicate James
         */
        
        // Create new movie m3 
        //     is exactly same content as m
        movie m3= new movie("A","james",2001);
        if(map.get(m3) == null){
        // without override hashcode()
        //    we are not able to get object m
            system.out.println("obj not found");
        }else{
            //..
        }
    }
}
```

## with overriding the method

```java=
class Movie {
    // attributes , constructors

    @Override
    public boolean equals(Object o) {
    // this object compares cotent with o
        Movie m = (Movie) o;
        return m.actor.equals(this.actor) &&
               m.name.equals(this.name) && 
               m.releaseYr == this.releaseYr;
    }
    @Override
    public int hashCode() {
        return actor.hashCode() + 
                name.hashCode() + 
                releaseYr;
    }

    // setters and getters 
}

public class demo{   
    private satic void main(String[] args){
        HashMap<movie, String> maps ;
        movie m = new movie("A","james",2001);
        movie m1= new movie("B","Tom"  ,1995);
        movie m2= new movie("A","james",2001);
        maps.put(m,"James");
        maps.put(m1,"Tom");
        
        //** (m1,james) will be 
        //     update from James to Duplicate James
        maps.put(m2,"Duplicate James");
        
        for(movie mm : map.keySet()){
            System,out.println(map.get(mm).toString);
        } 
        
        // Our hashMap prints out
        /*
         * Tom
         * Duplicate James
         */
        
        
        movie m3= new movie("A","james",2001);
        
        // thanks for equals() and hashcode()
        //    java knows m3 is same as m2
        if(map.get(m3) == null){
            // ...
        }else{
            System.out.println(map.get(m3).toString());
        }
        
        /* print out 
         *    Duplicate James
         */ 
    }

}
```

