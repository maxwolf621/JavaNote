# Iterator 

It can be used in the enhanced `For-each loop` (also in Iterator pattern) by implementing iterable interface 

Syntax for Iterator 
```java
import java.util.Iterator;
class iterator_ptr<TYPE> implements Iterator<TYPE> 
{        
    CustomIterator<>(CustomDataStructure obj) { 
        // initialize cursor 
    } 
      
    public boolean hasNext() { 
      //...
    } 
      
    public T next() { 
      //..
    } 
      
    // Used to remove an element. Implement only if needed 
    public void remove() { 
        // Default throws UnsupportedOperationException. 
    } 
} 
```


## Linked List with iterator

A Node 
```java
class Node<T> { 
    T data; 
    Node<T> next; 
    public Node(T data, Node<T> next) 
    { 
        this.data = data; 
        this.next = next; 
    } 
      
    // Setter getter methods for Data and Next Pointer 
    public void setData(T data) 
    { 
        this.data = data; 
    } 
      
    public void setNext(Node<T> next) 
    { 
        this.next = next; 
    } 
      
    public T getData() 
    { 
        return data; 
    } 
      
    public Node<T> getNext() 
    { 
        return next; 
    } 
}
```

Initialize a iterator 
```java
/* you will have three iterator_ptrs
1. head ptr 
2. tail ptr 
3. current ptr
*/
class List<T> implements Iterable<T>
{
  Node<T> head, tail;
  
  public void add(T data) 
  { 
      Node<T> node = new Node<>(data, null); 
      // | data | next | 
      if (head == null) 
          tail = head = node; 
      else {
      // | Linked List | -> |data | next | --> null
          tail.setNext(node); 
          tail = node; 
      } 
  } 

  // return Head 
  public Node<T> getHead() 
  { 
      return head; 
  } 

  // return Tail 
  public Node<T> getTail() 
  { 
      return tail; 
  } 

  // return Iterator of Linked List
  public Iterator<T> iterator() 
  { 
      // create a iterator points to head
      return new ListIterator<T>(this);
      /* this means this obj of List<T> list */
  } 
}
```

A class Iterator for List
```java
class ListIterator<T> implements Iterator<T> 
{ 
    Node<T> current; 
      
    // initialize pointer to head of the list for iteration 
    public ListIterator(List<T> list) 
    { 
        current = list.getHead(); 
    } 
      
    // returns false if next element does not exist 
    public boolean hasNext() 
    { 
        return current != null; 
    } 
      
    // return current data and update pointer 
    public T next() 
    { 
        T data = current.getData(); 
        // pointer to next
        current = current.getNext(); 
        return data; 
    } 
      
    // implement if needed 
    public void remove() 
    { 
        throw new UnsupportedOperationException(); 
    } 
} 
```

```java
class Main { 
    public static void main(String[] args) 
    { 
        // Create Linked List 
        List<String> myList = new List<>(); 
          
        // Add Elements 
        myList.add("abc"); 
        myList.add("mno"); 
        myList.add("pqr"); 
        myList.add("xyz"); 
          
        // Iterate through the list using For Each Loop 
        for (String string : myList) 
            System.out.println(string); 
    } 
} 

```
