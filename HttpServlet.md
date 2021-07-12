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

## POST AND GET METHOD
![image](https://user-images.githubusercontent.com/68631186/125323654-9e34c480-e371-11eb-9367-2919630ef1a6.png)

### 敏感資訊
就 HTTP/1.1 對 GET 的規範來說，是從指定的 URI「取得」想要的資訊，指定的 URI 包括了請求查詢（Query）部份，例如 
`GET /?id=0093` 瀏覽器會將指定的 URI 顯示在網址列上。
> 固像是password、session_id 等敏感資訊，就不適合使用`GET`發送

另一個問題在於 HTTP 的 Referer 標頭，這是用來表示從哪兒連結到目前的網頁，如果你的網址列出現了敏感資訊，之後連接到另一個網站，該網址就有可能透過 Referer 標頭得到敏感資訊。
`HTTP/1.1` 對 `POST` 的規範，是要求指定的 `URI`「接受」請求中附上的實體（`Entity`），像是儲存為檔案、新增為資料庫中的一筆資料等  
由於要求伺服器接受的資訊是附在請求本體（`Body`）而不是在 `URI`，瀏覽器網址列不會顯示附上的資訊，**傳統上敏感資訊也因此常透過 POST 發送**

### 發送的資料長度

雖然`HTTP`標準中沒有限制`URI`長度，然而各家瀏覽器對網址列的長度限制不一，伺服器對`URI`長度也有限制，因此資料長度過長的話，就不適用`GET`請求。
- **`POST`的資料是附在請求本體（`Body`）而不是在`URI`，不會受到網址列長度限制，因而 POST 在過去常被用來發送檔案等大量資訊**

### 書籤設置考量

由於瀏覽器書籤功能是針對網址列，因此想讓使用者可以針對查詢結果設定書籤的話，可以使用 `GET`  
`POST`後新增的資源不一定會有個`URI`作為識別，基本上無法讓使用者設定書籤。

### 瀏覽器快取（Cache）

只要符合 `HTTP/1.1` (第 13 節)對Cache的要求，`GET`的回應是可以被快取的，最基本的就是指定的`URI`沒有變化時，許多瀏覽器會從快取中取得資料，不過，Server可以指定適當的Cache-Control Header來避免`GET`回應被快取的問題。
至於`POST`的Response，許多瀏覽器（但不是全部）並不會快取，不過`HTTP/1.1`中規範，如果伺服端指定適當的 Cache-Control 或 Expires Header，仍可以對`POST`的回應進行快取。

### 安全與等冪區分
由於傳統上發送敏感資訊時，並不會透過`GET`，因而會有人誤解為`GET`不安全，這其實是個誤會，或者說對安全的定義不同，就 HTTP 而言，在` HTTP/1.1` 第 9 節對 HTTP 方法的定義中，有區分了安全方法（Safe methods）與等冪方法（Idempotent methods） 

Safe methods是指在實作應用程式時，使用者採取的動作必須避免有他們非預期的結果  
慣例上,`GET`與`HEAD`(與`GET`同為取得資訊，不過僅取得回應Header)對使用者來說就是「取得」資訊，不應該被用來「修改」與使用者相關的資訊，像是進行轉帳之類的動作，它們是安全方法，這與傳統印象中 `GE`比較不安全相反。
相對之下，`POST`、`PUT` 與 `DELETE` 等其他方法就語義上來說，代表著對使用者來說可能會產生不安全的操作，像是刪除使用者的資料等。

安全與否並不是指方法對伺服端是否產生副作用（Side effect），而是指對使用者來說該動作是否安全,`GET`也有可能在伺服端產生Side Effect  
對於副作用的進一步規範是在方法的等冪特性，`GET、HEAD、PUT、DELETE` 是等冪方法，也就是單一請求產生的副作用，與同樣請求進行多次的副作用必須是相同的  
> For example 若使用`DELETE`的副作用就是某筆資料被刪除，相同請求再執行多次的結果就是該筆資料不存在，而不是造成更多的資料被刪除  

`OPTIONS`與`TRACE`本身就不該有副作用，所以他們也是等冪方法  
`HTTP/1.1`中的方法去除掉上述的等冪方法之後，換言之，只有`POST`不具有等冪特性  
這是使得它與`PUT`有所區別的特性之一，在`HTTP/1.1`規範中,`PUT`方法要求將附加的實體儲存於指定的`URI`，如果指定的`URI`下已存在資源，則附加的實體是用來進行資源的更新，如果資源不存在，則將實體儲存下來並使用指定的`URI`來代表它，這亦符合等冪特性，例如用`PUT`來更新使用者基本資料，只要附加於請求的資訊相同，一次或多次請求的副作用都會是相同，也就是使用者資訊保持為指定的最新狀態。

### REST Api

現在不少 Web 服務或框架支援 REST 風格的架構，REST 全名 REpresentational State Transfer，REST 架構由客戶端／伺服端組成，兩者間通訊機制是無狀態的（Stateless），在許多概念上，與 HTTP 規範不謀而合（REST 架構基於 HTTP 1.0，與 HTTP1.1 平行發展，但不限於HTTP）。
- 符合 REST 架構原則的系統稱其為 RESTful，
- 然而注意到以上的描述，並不是說`PUT`只能用於更新資源，也沒有說要新增資源只能用`POST`, 在等冪性時談過,`PUT`在指定的`URI`下不存在資源時，也會新建請求中附上的資源。等冪性是在選用`POST`或 `PUT`時考量的要素之一  
- 另一個重要的考量要性，在 `HTTP/1.1` 中也有規範，也就是請求時指定的`URI`之作用
- `POST`中請求的`URI`，是要求其背後資源必須處理附加的實體(`Entity`)，而不是代表處理後實體(`Entity`)的`URI`；然而`PUT`時請求的`URI`，就代表請求中附加實體(`Enitity`)的`URI`，無論是更新或是新增實體。

透過動詞(HTTP Methods)、名詞(URI/URL，代表目標資源)、內容型態(回應的內容，HTML、XML、JSON、etc.), **讓Stateless HTTP protocol能藉由 REST 的語意化設計，攜帶所有的狀態資訊降低對網路通訊的重複請求資源消耗
透過 RESTful API 的設計風格，每個資源(Resource)都會得到一個到對應的位置（URL），並能透過 HTTP 語意化的方法
```xml
[POST] /bookmarks  <!-- 是用來新增一筆資 -->
[GET] /bookmarks/1 <!-- 用來取得 ID 為 1 的書籤-->
[PUT] /bookmarks/1 <!-- 用來更新 ID 為 1 的書籤資料-->
[DELETE] /bookmarks/1  <!-- 用來刪除 ID 為 1 的書籤資料-->
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
[Servlet](https://openhome.cc/Gossip/ServletJSP/ServletGenericServletHttpServlet.html)

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

> Servlet最重要的工作在於處理request, 只會產生一個instance,由Container透過多個thread, 來處理一個Servlet所發出的多個request
 
### Servlet life cycle
```java
/**
  * @apinote
  * 在完成 Servlet 初始化{@code init}後，
  * 如果有請求將由某個 Servlet 處理，則容器會呼叫該 Servlet 的{@cpde service}方法，
  * 傳入 <pre> ServletRequest、ServletResponse <pre> 實例
  * <p> 沒有HttpServelt的原因 這是由於最初設計Servlet時，並不限定它只能用在 HTTP 上</p>
  * <p> 實作 Servlet 介面的類別是{@code GenericServlet}類別，這是一個abstract類別 </p>
  * <p> 
  * {@code GenericServlet} 對 Servlet 介面的 {@code service} 方法沒有實作，
  * 僅標示為 abstract，{@code service}方法的實作由子類別 {@code HttpServlet}來完成
  * </p>
  */
import java.io.IOException;

public interface Servlet {
    /**
      * @Description
      * Container建立Servlet instance後,會執行{@code init}進行初始化工作
      * 如果有需要其他初始工作(註冊物件或是DB設定),可以去Override,只會執行一次
      */
    public void init(ServletConfig config) throws ServletException;
    /**
      * @Description
      * 當client傳送第一個request時,Container會啟動一個新的thread,去執行此方法
      * 根據傳進的HTTP去呼叫對應的Servlet Method,通常{@code service}是不會去Override的,
      * {@code service} 只是讓HTTP找到該執行方法(for example {@code doGet})
      */
    public ServletConfig getServletConfig();
    public void service(ServletRequest req, ServletResponse res) 
                   throws ServletException, IOException;
    public String getServletInfo();
    /**
      * @Description
      * 同{@code init}只會執行一次
      * 當Servlet準備GC(垃圾回收機制)前,會進行相關的清理工作
      */
    public void destroy();
}

/**
  * @apinote
  * {@cpde HttpServlet}實作了{@code GenericServlet}未實作的{@code service}方法，
  * 將傳入的 {@code ServletRequest}、{@code ServletResponse} 轉換為 
  * {@code HttpServletRequest} and {@code HttpServletResponse}，
  * 並Call {@code HttpServlet}自己定義以HttpServletRequest、HttpServletResponse作為參數的{@code service}方法
  * 這個{@code service}方法中，根據HTTP請求METHOD決定該呼叫{@code doGet} or {@code doPost}等方法
  * For More details about Methods {@link https://openhome.cc/Gossip/ServletJSP/DoGetDoPost.html}
  */
public abstract class HttpServlet extends GenericServlet {
    public HttpServlet() {}

   //...
  
   /**
     * @Description
     *   根據HTTP請求METHOD決定該呼叫{@code doGet} or {@code doPost}等方法
     */
   protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                doGet(req, resp);
            } else {
                //...        
    }

    //...

    /**
      * @Description
      *   Convert(cast) ServletRequestRequest/ServletResponse to 
      *   HttpServletRequest/HttpServletResponse
      */
    @Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException {

        HttpServletRequest  request;
        HttpServletResponse response;

        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
        // call doGet or doPost method
        service(request, response);
    }
}

```

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

