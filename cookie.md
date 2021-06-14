
[Same cookie name problem](https://stackoverflow.com/questions/3193163/is-calling-httpservletresponse-addcookie-with-the-same-cookie-name-safe)

[java point cookie brief](https://www.javatpoint.com/cookies-in-servlet)
[spring cookie](https://beginnersbook.com/2013/05/servlet-cookies/)
[Session Token Cookie](https://blog.yyisyou.tw/5d272c64/)
## Types of Cookie

- Non-persistent cookie (browser cache)
  > It is valid for single session only.  
  > It is removed each time when user closes the browser.
  >> which means the cookies do not have expiration time. It lives in the browser memory
- Persistent cookie (stored in user hard driver)
  > It is valid for multiple session. 
  > It is not removed each time when user closes the browser. 
  > **It is removed only if user logout or signout.** or **get destroyed based on the expiry time**


## Advantage of Cookies
**Simplest technique of maintaining the state.**
Cookies are maintained at client side.

## Disadvantage of Cookies
It will not work if cookie is disabled (e.g forbidded by user ) from the browser.
**Only String type paramater (textual information) can be set in Cookie object.**

## Create a Cookie object:

```java
// new Cookie(key , value) 
Cookie c = new Cookie("userName","Chaitanya");
```

## Set the maximum Age:
```java
c.setMaxAge(1800); 
// live (60*3*10) 1800 seconds, half min
```
## Place the Cookie in HTTP response header:
```java
response.addCookie(c);
```

## Read cookies
```java
Cookie c[]=request.getCookies(); 
//c.length gives the cookie count 
for(int i=0;i<c.length;i++){  
 out.print("Name: "+c[i].getName()+" & Value: "+c[i].getValue());
}
```



```java 
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MyServlet1 extends HttpServlet 
{
   public void doGet(HttpServletRequest request, 
      HttpServletResponse response) {
      try{
          response.setContentType("text/html");
          PrintWriter pwriter = response.getWriter();

          String name = request.getParameter("userName");
          String password = request.getParameter("userPassword");
          pwriter.print("Hello "+name);
          pwriter.print("Your Password is: "+password);

          //Creating two cookies
          Cookie c1=new Cookie("userName",name);
          Cookie c2=new Cookie("userPassword",password);

          //Adding the cookies to response header
          response.addCookie(c1);
          response.addCookie(c2);
          pwriter.print("<br><a href='welcome'>View Details</a>");
          pwriter.close();
   }catch(Exception exp){
       System.out.println(exp);
    }
  }
}
```
MyServlet2.java
```java
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MyServlet2 extends HttpServlet {
 public void doGet(HttpServletRequest request, 
    HttpServletResponse response){
    try{
       response.setContentType("text/html");
       PrintWriter pwriter = response.getWriter();
 
       //Reading cookies
       Cookie c[]=request.getCookies(); 
       //Displaying User name value from cookie
       pwriter.print("Name: "+c[1].getValue()); 
       //Displaying user password value from cookie
       pwriter.print("Password: "+c[2].getValue());
 
       pwriter.close();
    }catch(Exception exp){
       System.out.println(exp);
     }
  }
}
```
