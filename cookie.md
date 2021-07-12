
[Same cookiename problem](https://stackoverflow.com/questions/3193163/is-calling-httpservletresponse-addcookie-with-the-same-cookie-name-safe)  
[spring cookie](https://beginnersbook.com/2013/05/servlet-cookies/)  
[Session/Token/Cookie](https://blog.yyisyou.tw/5d272c64/)  
[Cookie Methods](https://www.geeksforgeeks.org/javax-servlet-http-cookie-class-java/)  
[Vorstellung von cookies](https://ithelp.ithome.com.tw/articles/10217955)

## How cookies functions

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

- Non-persistent cookie (**browser cache**)
  > It is valid for single session only.  
  > It is removed each time when user closes the browser.  
  >> which means the cookies do not have expiration time. It lives in the browser memory  
- Persistent cookie (**stored in user hard driver**)
  > It is valid for multiple sessions. 
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
`Name = value`
- This depicts the actual information stored within the cookie. 
- **Neither the name nor the value should contain white space or any of the following characters: `[ ] ( ) = , ” / ? @ : ;`**  

Example of valid cookie name-value pair in payload looks like  
```json
Set-Cookie: session-id = 187-4969589-3049309
```

#### Domain

By default, a cookie applies to the server it came from.
> If a cookie is originally set by `www.foo.example.com`, the browser will only send the cookie back to `www.foo.example.com`.  
> However, **a site can also indicate that a cookie applies within an entire subdomain, not just at the original server.**  

### 跨域訪問

For example, the request sets a user cookie as the following
```json
Set-Cookie: user = geek ;Domain = x.y.z.com
```
it can set a cookie domain to itself or parents `x.y.z.com`, `y.z.com`, `z.com`    
But not `com`, which is a public suffix(Examples of public suffixes - `com`, `edu`, `uk`, `co.uk`, `blogspot.com`, `compute.amazonaws.com`)    

a cookie with domain `y.z.com` is applicable to `y.z.com`, `x.y.z.com`, `a.x.y.z.com` etc.


#### Path

The scope of each cookie is limited to **a set of paths**, controlled by the Path attribute.   
If the server omits the Path attribute, the user agent will use the **directory** of the request-uri's path component as the default value. (See Section 5.1.4 for more details.)  
```
Set-Cookie: user = geek; Path =/ restricted
```

path表示cookie所在的目錄，asp.net默認為`/`，就是根目錄 
在同一個服務器上有目錄如下`/test/`,`/test/cd/`,`/test/dd/`， 現設一個cookie1的path為`/test/`，cookie2的path為`/test/cd/`，那麽test下的所有頁面都可以訪問(Access)到cookie1，而`/test/`和`/test/dd/`的子頁面不能訪問cookie2。這是因為cookie能讓其path路徑下的頁面訪問

#### Expires (確切的時間)
The browser should remove the cookie from its cache after that date has passed. 
```json
Set-Cookie: user = geek; expires = Wed, 21-Feb-2017 15:23:00 IST
```
####  Max-Age (秒數)
This attribute sets the cookie to expire after a certain number of **seconds** have passed instead of at a specific moment.  
For instance, this cookie expires one hour (3,600 seconds) after it’s first set. 
```json
Set-Cookie: user = "geek"; Max-Age = 3600
```

## Methods to set up the Cookies Attributes in java

### Create a Cookie object:
```java
// new Cookie(key , value)  
// Specifies a path for the cookie to which the client should return the cookie
Cookie c = new Cookie("userName","Chaitanya"); 
```
### Set the maximum Age:
```java
cookie.setMaxAge(-1); //expire this cookie as browser is closed
cookie.setMaxAge(0);  //expire this cookie onice when browser receives 
cookie.setMaxAge(1800);  // live (60*3*10) 1800 seconds, half an hour
```
### Place the Cookie in HTTP response header:
```java
response.addCookie(c);
```
### (servlet) Read cookies 
```java
Cookie c[]=request.getCookies(); 
// c.length returns total numbers of cookies 
for(int i=0;i<c.length;i++){  
 out.print("Name: "+c[i].getName()+" & Value: "+c[i].getValue());
}
```
### Client asks for Cookie and Send Cookie
MyServlet1.java
First time that a new client send a request (to require a cookie)
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

          // request from client
          String name = request.getParameter("userName");
          String password = request.getParameter("userPassword");
          pwriter.print("Hello "+name);
          pwriter.print("Your Password is: "+password);

          //Creating two cookies to client
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
Second time the client sends the request with cookie
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

## Setter Methods 
```java
/**
 * @Description
 *   Sets the domain in which this cookie is visible. 
 *   Domains are explained in detail in the attributes of cookie part previously.
 * @param pattern : the domain in which this cookie is visible.
**/
setDomain(String Pattrn) 

/**
  * @Description Specifies the purpose of this cookie.
  * @param purpose : the purpose of this cookie.
  */
setComment(String purpose) 

/**
  * @Description : 
  *   Specifies a path for the cookie to which the client should return the cookie.
  * @param path : path where this cookie is returned
  */
setPath(string path)

/**
  * @Description 
  *   Indicated if secure protocol (for example https) to be used while sending this cookie. 
  *   Default value is {@code false}.
  * @param secure 
  *    If {@code true}, the cookie can only be sent over a secure protocol like <pre> https <pre>. 
  *    If {@code false}, it can be sent over any protocol.
**/
setSecure(boolean secure)

/**
  * @Return
  *   0 if the cookie complies with the original Netscape specification; 
  *   1 if the cookie complies with RFC 2965/2109
  */
public int getVersion() 

/** 
  * @Description Used to set the version of the cookie protocol this cookie uses.
  * @param v : 
  *   0 for original Netscape specification
  *   1 for RFC 2965/2109
  */
setVersion(int v) 

/**
  * @return a copy of this cookie.
  */
public Cookie clone()
```

## HttpServletRquest and HttpServletResponse

Application(client) sends HttpServletRequest inlcuded cookies 

and Http Servlet can do these things
1. get Cookie from request
2. modify Cookie from request 
3. delete Cookie from request

### get Cookie
```java 
protected void createCookie(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

  /**
    * <p> create Cookie </p>
    */
  Cookie cookie = new Cookie("cookie-name", "cookie-Value");
  Cookie cookie2 = new Cookie("cookie-name2", "cookie-Value2");

  /**
    * <p> 
    * add the cookies in the response payload 
    * </p>
  response.addCookie(cookie);
  response.addCookie(cookie2);
  response.getWriter().write("Created……");
```

### modify cookie name's value

client sends the request with cookie named cookie-name then 
```java
protected void updateCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // create a cookie object whoses cookie name is cookie-name
  Cookie cookie = new Cookie("cookie-name", null);
  // modify cookie name's value
  cookie.setValue("this is new value");
  
  // add it in the response and the cookie data in client will be updated
  response.addCookie(cookie);
}
```

### delete cookie

[To remove the cookie](https://stackoverflow.com/questions/890935/how-do-you-remove-a-cookie-in-a-java-servlet)

```java
/**
  * @apinote
  *   set {@code setMaxAge}'s @param <pre> 0 </pre>
  *   set {@code setValue}'s @param <pre> "" </pre>
  */
private void eraseCookie(HttpServletRequest req, HttpServletResponse res) {
    Cookie[] cookies = req.getCookies();
    if (cookies != null)
        for (Cookie cookie : cookies) {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
        }
}
```











