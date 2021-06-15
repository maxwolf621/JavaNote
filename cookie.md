
[Same cookie name problem](https://stackoverflow.com/questions/3193163/is-calling-httpservletresponse-addcookie-with-the-same-cookie-name-safe)  
[java point cookie brief](https://www.javatpoint.com/cookies-in-servlet)  
[spring cookie](https://beginnersbook.com/2013/05/servlet-cookies/)  
[Session Token Cookie](https://blog.yyisyou.tw/5d272c64/)  
[Cookie Forms](https://www.geeksforgeeks.org/javax-servlet-http-cookie-class-java/)  

## How functions

![](https://media.geeksforgeeks.org/wp-content/uploads/cookies.jpg)  
![image](https://user-images.githubusercontent.com/68631186/122122215-18e7ee00-ce5f-11eb-8168-2f75bc710740.png)


For example  

![image](https://user-images.githubusercontent.com/68631186/122121919-bee72880-ce5e-11eb-8bef-13f04ef9300a.png)
```json
Alice -> Bob: Request
Bob --> Alice: Response: Set-Cookie: SID=31d4d96e407aad42
Alice -> Bob: Request: Cookie: SID=31d4d96e407aad42
Bob --> Alice: Response
```

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
- **Simplest technique of maintaining the state.**  
- Cookies are maintained at client side.  

## Disadvantage of Cookies
- It will not work if cookie is disabled (e.g forbidded by user ) from the browser.  
- **Only String type paramater (textual information) can be set in Cookie object.**  


## Form and Attributes

Each Cookie forms as the following  
`Name = value pair`
- This depicts the actual information stored within the cookie. 
- **Neither the name nor the value should contain white space or any of the following characters: `[ ] ( ) = , ” / ? @ : ;`**  

Example of valid cookie name-value pair in payload looks like  
```json
Set-Cookie:session-id = 187-4969589-3049309
```

#### Domain

```
請求的 Domain 與 Cookie Domain 一樣
請求的 Domain 是 Cookie Domain 的子網域
```

By default, a cookie applies to the server it came from.(ONLY ALLOWING SAME DOMAIN) 
> If a cookie is originally set by www.foo.example.com, the browser will only send the cookie back to www.foo.example.com.  
> However, a site can also indicate that a cookie applies within an entire subdomain, not just at the original server.  


For example, this request sets a user cookie for the entire foo.example.com domain:   
The browser will echo this cookie back not just to www.foo.example.com, but also to lothar.foo.example.com, eliza.foo.example.com, enoch.foo.example.com, and any other host somewhere in the foo.example.com domain. 
However, a server can only set cookies for domains it immediately belongs to. www.foo.example.com cannot set a cookie for www.geeksforgeeks.org, example.com, or .com, no matter how it sets the domain.  
```
Set-Cookie: user = geek ;Domain =.foo.example.com
```

#### Path
When (user/client) requesting a document in the subtree from the same server, the client echoes(returns) that cookie back. 
However, it does not use the cookie in other directories on the site.  

on the index `/` the user whose name called geek should return the cookies to server  
```
Set-Cookie: user = geek; Path =/ restricted
```

#### Expires 
The browser should remove the cookie from its cache after that date has passed. 
```json
Set-Cookie: user = geek; expires = Wed, 21-Feb-2017 15:23:00 IST
```
####  Max-Age 
This attribute sets the cookie to expire after a certain number of **seconds** have passed instead of at a specific moment.  
For instance, this cookie expires one hour (3,600 seconds) after it’s first set. 
```json
Set-Cookie: user = "geek"; Max-Age = 3600
```

## Create a Cookie object:

```java
// new Cookie(key , value) 
Cookie c = new Cookie("userName","Chaitanya");Specifies a path for the cookie to which the client should return the cookie.

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

## Stter Methods 
```java
/**
// Sets the domain in which this cookie is visible. Domains are explained in detail in the attributes of cookie part previously.
// pattern : string representing the domain in which this cookie is visible.
**/
setDomain(String Pattrn) 

/**
//Specifies the purpose of this cookie.
//purpose : string representing the purpose of this cookie.
**/
setComment(String purpose) 


/**
//Specifies a path for the cookie to which the client should return the cookie.
//path : path where this cookie is returned
**/
setPath(string path)

/**
// Indicated if secure protocol to be used while sending this cookie. Default value is false.
// secure - If true, the cookie can only be sent over a secure protocol like https. 
   If false, it can be sent over any protocol.
**/
setSecure(boolean secure)

/**
//Returns 0 if the cookie complies with the original Netscape specification; 
  1 if the cookie complies with RFC 2965/2109
**/
/*public int */ getVersion() 

/**
//Used to set the version of the cookie protocol this cookie uses.
// v - 0 for original Netscape specification; 1 for RFC 2965/2109
**/
setVersion(int v) 

/**
//returns a copy of this cookie.
**/
/* public Cookie */ clone()
