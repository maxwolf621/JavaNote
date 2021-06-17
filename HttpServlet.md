
# HTTP
[http, session and cookie](https://tw.alphacamp.co/blog/cookie-session-difference)
[http security](https://tw.alphacamp.co/blog/http-https-difference)


### HTTP is a stateless protocol. 
A stateless protocol does not require the server to retain information or status about each user for the duration of multiple requests.

But some web applications may have to track the
# HTTP
[http, session and cookie](https://tw.alphacamp.co/blog/cookie-session-difference)
[http security](https://tw.alphacamp.co/blog/http-https-difference)


### HTTP is a stateless protocol. 
A stateless protocol does not require the server to retain information or status about each user for the duration of multiple requests.

But some web applications may have to track the user's progress from page to page, 
for example when a web server is required to customize the content of a web page for a user. 

Solutions for these cases include:
- the use of HTTP cookies.
- server side sessions
- hidden variables (when the current page contains a form)
- URL-rewriting using URI-encoded parameters, e.g., `/index.php?session_id=some_unique_session_code`.

[HTTP METHODS](https://ithelp.ithome.com.tw/articles/10227433)

讓無狀態的 HTTP 能得知使用者狀態的方法；簡單來說，就是伺服器透過 Header 的屬性 Set-Cookie，把使用者的狀態紀錄成儲存在使用者電腦裡的 Cookie，而瀏覽器在每一次發送請求時，都在 Header 中設定 Cookie屬性，把 Cookie 帶上，伺服器就能藉由檢視 Cookie 的內容，得知瀏覽器使用者的狀態；而像是「從登入到登出」、「從開始瀏覽網頁到 Cookie 失效」，或是任何伺服器能認出使用者狀態的時間區間，就叫做 Session  


Browser's response
```
HTTP/1.0 200 OK
Content-type: text/html
Set-Cookie: yummy_cookie=choco
Set-Cookie: tasty_cookie=strawberry
[page content]

After storing the cookie in Browser, The furture Request sent by it as the following 
```
GET /sample_page.html HTTP/1.1
Host: www.example.org
Cookie: yummy_cookie=choco; tasty_cookie=strawberry
```
### REST Api
- 透過動詞（HTTP Methods）、名詞（URI/URL，代表目標資源）、內容型態（回應的內容，HTML、XML、JSON、etc.），讓無狀態的網路通訊能藉由 REST 的語意化設計，攜帶所有的狀態資訊，降低對網路通訊的重複請求資源消耗。

透過 RESTful API 的設計風格，每個資源都會得到一個到對應的位置（URL），並能透過 HTTP 語意化的方法

```
[GET] http://mytube.com/v1/videos/ -> 取得 video 列表
[POST] http://mytube.com/v1/videos/ -> 新增 video
[GET] http://mytube.com/v1/videos/MgphHyGgeQU -> 取得指定 ID 的 video
[PUT] http://mytube.com/v1/videos/MgphHyGgeQU -> 修改指定 ID 的 video
[DELETE] http://mytube.com/v1/videos/MgphHyGgeQU -> 刪除指定 ID 的 video
```
- PLUS : [OAuth2User Endpoints with HTTP methods](https://darutk.medium.com/diagrams-and-movies-of-all-the-oauth-2-0-flows-194f3c3ade85)

[HTTP SEO](https://ithelp.ithome.com.tw/articles/10225117)


[SPA MVC Structure](https://ithelp.ithome.com.tw/articles/10224772)  
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380cEB9Gr0Y4q.png)
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380vhVNWYCggj.png) user's progress from page to page, 
for example when a web server is required to customize the content of a web page for a user. 

Solutions for these cases include:
- the use of HTTP cookies.
- server side sessions
- hidden variables (when the current page contains a form)
- URL-rewriting using URI-encoded parameters, e.g., `/index.php?session_id=some_unique_session_code`.

[HTTP METHODS](https://ithelp.ithome.com.tw/articles/10227433)

讓無狀態的 HTTP 能得知使用者狀態的方法；簡單來說，就是伺服器透過 Header 的屬性 Set-Cookie，把使用者的狀態紀錄成儲存在使用者電腦裡的 Cookie，而瀏覽器在每一次發送請求時，都在 Header 中設定 Cookie屬性，把 Cookie 帶上，伺服器就能藉由檢視 Cookie 的內容，得知瀏覽器使用者的狀態；而像是「從登入到登出」、「從開始瀏覽網頁到 Cookie 失效」，或是任何伺服器能認出使用者狀態的時間區間，就叫做 Session  


Browser's response
```
HTTP/1.0 200 OK
Content-type: text/html
Set-Cookie: yummy_cookie=choco
Set-Cookie: tasty_cookie=strawberry
[page content]

After storing the cookie in Browser, The furture Request sent by it as the following 
```
GET /sample_page.html HTTP/1.1
Host: www.example.org
Cookie: yummy_cookie=choco; tasty_cookie=strawberry
```
### REST Api
- 透過動詞（HTTP Methods）、名詞（URI/URL，代表目標資源）、內容型態（回應的內容，HTML、XML、JSON、etc.），讓無狀態的網路通訊能藉由 REST 的語意化設計，攜帶所有的狀態資訊，降低對網路通訊的重複請求資源消耗。

透過 RESTful API 的設計風格，每個資源都會得到一個到對應的位置（URL），並能透過 HTTP 語意化的方法

```
[GET] http://mytube.com/v1/videos/ -> 取得 video 列表
[POST] http://mytube.com/v1/videos/ -> 新增 video
[GET] http://mytube.com/v1/videos/MgphHyGgeQU -> 取得指定 ID 的 video
[PUT] http://mytube.com/v1/videos/MgphHyGgeQU -> 修改指定 ID 的 video
[DELETE] http://mytube.com/v1/videos/MgphHyGgeQU -> 刪除指定 ID 的 video
```
- PLUS : [OAuth2User Endpoints with HTTP methods](https://darutk.medium.com/diagrams-and-movies-of-all-the-oauth-2-0-flows-194f3c3ade85)

[HTTP SEO](https://ithelp.ithome.com.tw/articles/10225117)


[SPA MVC Structure](https://ithelp.ithome.com.tw/articles/10224772)  
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380cEB9Gr0Y4q.png)
![](https://ithelp.ithome.com.tw/upload/images/20200908/20111380vhVNWYCggj.png)
