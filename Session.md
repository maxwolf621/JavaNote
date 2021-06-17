
[](https://ithelp.ithome.com.tw/articles/10246787)

## Crerate A Session

```java
public class SessionServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public SessionServlet() {
	}

	protected void getSession(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getHeader("Cookie"));
		
		// 第一個呼叫就是獲取一個新的Session。如果Session已經建立過。就獲取原來的會話。
		HttpSession session = request.getSession();
		// 輸出會話id號,和是否是新建立
		// session.getId()返回Session的唯一編號
		// session.isNew()返回當前Session是否是剛建立的
		response.getWriter().write(
				"session ID:" + session.getId() + "<br/>是否是新的:" + session.isNew());
	}
}
```

## Servlet's request and response attributes
```java
protected void setAttribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// set up the attribute of payload 
		session.setAttribute("A", "Avalue");
    
    // now response will include "A" : "Avalue"
}
protected void getAttribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  HttpSession session = request.getSession();
  // get the attribute from request
  String value = (String) session.getAttribute("A");
  
  // we can print out the value ... etc 
}
```


## Life time of the session

```java
HttpSession session = request.getSession();

session.setMaxInactiveInterval(3); // 設定過期時間為3秒 

session.setMaxInactiveInterval(60); // 設定過期時間為1分鐘

session.setMaxInactiveInterval(60 * 60); // 設定過期時間為1小時

session.setMaxInactiveInterval(60 * 60 * 24); // 設定過期時間為1天

session.setMaxInactiveInterval(60 * 60 * 24 * 7); // 設定過期時間為1周

session.setMaxInactiveInterval(-1); // 設定永遠不超時

session.invalidate(); // 讓Session物件立即過期
```

## How Cookie and Session works

[](http://aliyunzixunbucket.oss-cn-beijing.aliyuncs.com/csdn/41176c45-a016-4779-b312-12aeff91e3c8?x-oss-process=image/resize,p_100/auto-orient,1/quality,q_90/format,jpg/watermark,image_eXVuY2VzaGk=,t_100,g_se,x_0,y_0)
- first time client sends the request then servlet will creat a `session_id` and send back response with cookie whose name is `session_id`
- after client receives the cookie from response and save it so servlet can compare the `session_id` with the one that send from client within the expration of the cookie



