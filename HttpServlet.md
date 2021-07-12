# HTTP
[http, session and cookie](https://tw.alphacamp.co/blog/cookie-session-difference)   
[http security](https://tw.alphacamp.co/blog/http-https-difference)  

### HTTP is a stateless protocol. 
[HTTP METHODS](https://ithelp.ithome.com.tw/articles/10227433)  
**A stateless protocol does not require the server to retain information or status about each user for the duration of multiple requests.**  
But some web applications may have to track the user's progress from page to page for example   
- when a web server is required to customize the content of a web page for a user. 
 - Solutions for these cases include (these are important for Oauth2 concept):
   > the use of HTTP cookies. (重點)
   > server side sessions (重點)
   > hidden variables (when the current page contains a form)
   > URL-rewriting using URI-encoded parameters, e.g., `/index.php?session_id=some_unique_session_code`.

## Session for stateless HTTP

讓stateless的HTTP能得知使用者狀態的方法；簡單來說，就是Servlet透過Header的屬性`Set-Cookie`，把使用者的狀態紀錄成儲存在使用者電腦裡的Cookie，而**Browser在每一次發送Request時，都在 Header中設定Cookie屬性，把Cookie帶上，Servlet就能藉由檢視Cookie的內容，得知瀏覽器使用者的State**；而像是「從登入到登出」、「從開始瀏覽網頁到 Cookie 失效」，或是**任何伺服器能認出使用者狀態的時間區間，就叫做Session**

Browser's response
```json 
HTTP/1.0 200 OK
Content-type: text/html
Set-Cookie: yummy_cookie=choco
Set-Cookie: tasty_cookie=strawberry
[page content]
```

After storing the cookie in Browser.  
The furture Request sent by it as the following 
```josn 
GET /sample_page.html HTTP/1.1
Host: www.example.org
Cookie: yummy_cookie=choco; tasty_cookie=strawberry
```

### REST Api
透過動詞(HTTP Methods)、名詞(URI/URL，代表目標資源)、內容型態(回應的內容，HTML、XML、JSON、etc.), **讓Stateless HTTP protocol能藉由 REST 的語意化設計，攜帶所有的狀態資訊降低對網路通訊的重複請求資源消耗。
**
透過 RESTful API 的設計風格，每個資源(Resource)都會得到一個到對應的位置（URL），並能透過 HTTP 語意化的方法
```xml
[GET] http://mytube.com/v1/videos/            <!-- [GET] 取得 video list -->
[POST] http://mytube.com/v1/videos/           <!-- [POST]新增 video -->
[GET] http://mytube.com/v1/videos/MgphHyGgeQU <!-- [GET]取得 指定ID[MgphHyGgeQU] 的video -->
[PUT] http://mytube.com/v1/videos/MgphHyGgeQU <!-- [PUT]修改 指定ID[MgphHyGgeQU] 的video -->
[DELETE] http://mytube.com/v1/videos/MgphHyGgeQU <!--[DELETE]刪除 指定ID[MgphHyGgeQU] 的video -->
```
[OAuth2User Endpoints with HTTP methods](https://darutk.medium.com/diagrams-and-movies-of-all-the-oauth-2-0-flows-194f3c3ade85)
[HTTP SEO](https://ithelp.ithome.com.tw/articles/10225117)

## [SPA and MVC Structure](https://ithelp.ithome.com.tw/articles/10224772)  
#### MVC
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380cEB9Gr0Y4q.png)  
#### SPA
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380vhVNWYCggj.png)   

## Java Methods for Servlet
![image](https://user-images.githubusercontent.com/68631186/125268238-18495700-e33a-11eb-9503-0ac4d33835fc.png)
- HttpServlet took care cotent of HttpServletRequest and HttpServletResponse  
- Servlet Container _creates_ HttpServletRequest/Response Instance,*resolves*,*encapsulates* HttpServletRequest and *sends* HttpServletResponse back to client 
### FLOW
1. Web Client **SENDS** HttpServletRequest to Servlet Container
2. Servlet Container **RESOLVES** Web client's HttpServletRequest
   > 2-1 Servlet Container **CREATES** an instance of HttpServletRequest to encapsulate the HttServletRequest provided by web client  
   > 2-2 Servlet Container **CREATES** an instance of HttpServletResponse  
   > 2-3 Servlet Container **CALLS** HttpServlet's service methods，Pass HttpRequest/HttpResponse instance as parameterS of HttpServlet's methods  
3. HttpServlet **CALLS** HttpServletRequest's methods to get HTTP request's information ( header, attribute, ... etc )  
   > 3-1 HttpServlet **CALLS** HttpServletResponse's methods to configure response payload that should send back to web client  
4. Servlet Container **SENDS** HttpServletResponse to Web Client  

[HttpServlet](https://blog.csdn.net/qq_41007534/article/details/99696559)
[HttpServletRequest](https://blog.csdn.net/jiangyu1013/article/details/56840191)
[HttpServletResponse](https://blog.csdn.net/q547550831/article/details/50445516)
[HttpServletRequest/Response](https://blog.csdn.net/Jsagacity/article/details/80143842)

## Response Mehods
```java
/**
  * @return Staus
  * <p> for example 404, 401 ... etc. </p>
  */
setStatus(int sc)

/**
  * @Description
  *   Handle for Header for response payload
  *   <pre> add </pre> put attributes in header
  *   <pre> set </pre> set up attributes
  */
addHeader(String name, String value)
addIntHeader(String name, int value)
addDateHeader(String name, long date)

setHeader(String name, String value)
setDateHeader(String name, long date)
setIntHeader(String name, int value)

/**
  * @Description 
  *   Via {@code Write(Stirng str)} writes {@param str} in String buffer
  *   Tomcat will 
  *   put {@param str} from String buffer into {@code HttpServeltResponse}
  *   and send to Browser
  */
PrintWriter getWriter()
/** 
  * @Description
  *   via {@code write(byte[] bytes)} in BufferedStream
  *   Tomcat will put @{param bytes} from BufferedStream into {@code HttpServeltResponse}
  *   and send to Browser
  */
ServletOutputStream getOutputStream()

/**
  * @Description
  *   In case we use the language like chinese (to avoid error)
  *   then we can use this method to set up Encode as UTF-8
  */
setCharacterEncoding(String charset) 

/**
  * @Description
  *   To set up the browser's Encode
  *   this is recommended that uses 
  *   {@code setContentType} instead of {@code setCharacterEncoding}
  */
response.setContentType(“text/html;charset=UTF-8”);
```

### HttpServletRequest Method
```java
/**
  * @Description
  *   ask for [GET, POST, DELETE ... ETC] methods in this request
  */
String getMethod()

/**
  * @Description
  *   ask for URL from client's request
  */
String getRequestURI()
StringBuffer getRequestURL()
String getContextPath() 

/**
  * @Description
  *   Get QueryParameter in URL
  *   For example 
  *   <pre> ?username=zhangsan&password=123 </pre>
  */
String getQueryString()

/**
  * @Description
  *  get client's ip addr
  */
getRemoteAddr() 

/**
  * @Description
  *   get header's attributes
  */
long getDateHeader(String name)
String getHeader(String name)
Enumeration getHeaderNames()
Enumeration getHeaders(String name)
int getIntHeader(String name)

/** 
  * <p> 
  * Get Queryparameter in {@code getRequestURL} 
  * For example <li> username=zhangsan&password=123&hobby=football&hobby=basketball </li>
  * |Parameter|Parameter Value       |
  * |---------|----------------------|
  * | username|[zhangsan]            |
  * | password|[123]                 |
  * | hobby	  |[football,basketball] |
  * </p>
  * <p> We can get queryparameters by use the following methods </p>
  */
String getParameter(String name)
String[] getParameterValues(String name)
Enumeration getParameterNames()
Map< String,String[]> getParameterMap()

/**
  * @Description
  *  Encode for GET METHOD
  */
request.setCharacterEncoding(“UTF-8”);
/**
  * @Description
  *  Encode for POST METHOD
  */
parameter = new String(parameter.getbytes(“iso8859-1”),”utf-8”);

setAttribute(String name, Object o)
getAttribute(String name)
removeAttribute(String name)

/**
  * @Description 
  *  {@code forward(req,res)} is used by server side
  *  <li> 定義在RequestDispatcher的介面,由request.getRequestDispatcher呼叫 </li>
  *  <li> 因是內部轉址,可以透過setAttribute傳遞參數 </li>
  *  <li> 內部轉址,URL不會顯示程式名稱(可設定成參數) </li>
  *  <li> 適用於權限管理轉頁時使用 </li>
  *  <li> 由於不用在ASK一次request from client 故效率比{@code sendRedirect(String path)}高 </li>
  */
RequestDispatcher getRequestDispatcher(String path)
request.getRequestDispatcher.forward(ServletRequest request, ServletResponse response)

/**
  * @Description 
  *   {@code sendRedirect(req,res)} is used by server side
  *   <li> it will be called by <pre> HttpServletResponse </pre> </li>
  *   <li> 效率較低(cilent will send the request again) </li>
  *   <li> 適用於跳至外部網站或回主畫面使用 </li>
  *
  */
response.sendRedirect("/Longin.jsp");
```

### LIFE TIME OF SERVERLETCONTEXT AND REQUEST
ServletContext：
- INITIALIZED：SNICE SERVER STARTS
- DESTRUCTION：WHEN SERVER CLOSES 
- SCOPE : WHOLE WEB APPLICATION

request：
- INITIALIZED：ASK FOR A ACCESS TO A SERVER
- DESTRUCTION：RECEIVE A RESPONSE
- SCOPE : BTW CLIENT SENDS REQUEST AND RECEIVE RESPONSE FROM SERVER

