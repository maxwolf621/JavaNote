
[Reference](https://ithelp.ithome.com.tw/articles/10246787)

# Crerate A Session
```java
public class SessionServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public SessionServlet() {
	}

	protected void getSession(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getHeader("Cookie"));
		
		/**
		  * <p>
		  * <p> If call this {@code getSession} first time 
		  *     then create a new session </p>
		  * <p> Else if the session already exists 
		  *     Get the Session </p>
		  */
		HttpSession session = request.getSession();
		
		/**
		  * @return 
		  * {@code session.getId()} : Unique ID of session
		  * {@code session.isNew()} : Is a fresh new session ?
		  */
		response.getWriter().write(
				"session ID:" + session.getId() + "<br/>is a fresh new session ? \n" + session.isNew());
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
  
  // get the attribute A from session
  String value = (String) session.getAttribute("A");
  // we can print out the value ... etc 
}
```


## Life time of the session

```java
HttpSession session = request.getSession();

/**
  * <p> After get the session from the request 
  * 	Uses {@code setMaxInactiveInterval} 
  * 	to set up life time of this seesion </p>
  * @param : seconds for life cycle of session 
  */
session.setMaxInactiveInterval(3); 		    //  3 seconds 
session.setMaxInactiveInterval(60); 	            // One minute
session.setMaxInactiveInterval(60 * 60); 	    // One hours
session.setMaxInactiveInterval(60 * 60 * 24);       // One day
session.setMaxInactiveInterval(60 * 60 * 24 * 7);   // One week

session.setMaxInactiveInterval(-1); 	            // LONG LIVE FOREVER
session.invalidate(); 			            // Set session InActive 
```

## [How Cookie and Session works](http://aliyunzixunbucket.oss-cn-beijing.aliyuncs.com/csdn/41176c45-a016-4779-b312-12aeff91e3c8?x-oss-process=image/resize,p_100/auto-orient,1/quality,q_90/format,jpg/watermark,image_eXVuY2VzaGk=,t_100,g_se,x_0,y_0)

1. Client sent the request
2. servlet created a `session_id` and sent back a response contained with cookie whose name is `session_id`
```diff
+ client --  request xxx.example.com/[queryparams] ---> servlet
- servlet -- response ( contained cookie for session_id ) --> client
+ client -- request ( contained cookie for session_id ) --> servlet
```
After client receives the cookie from response and saves it in local storage/browser   
so then the servlet can compare the `session_id` with the one sent by client within the expiration of the cookie



