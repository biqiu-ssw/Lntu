package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineCountListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("监听器开始工作");
		ServletContext context = se.getSession().getServletContext();
        Integer onLineCount = (Integer) context.getAttribute("onLineCount");
         if(onLineCount==null){
             context.setAttribute("onLineCount", 1);
         }else{
             onLineCount++;
             context.setAttribute("onLineCount", onLineCount);
         }

	}

	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext context = se.getSession().getServletContext();
         Integer onLineCount = (Integer) context.getAttribute("onLineCount");
         if(onLineCount==null){
             context.setAttribute("onLineCount", 1);
         }else{
             onLineCount--;
            context.setAttribute("onLineCount", onLineCount);
         }
     }

	

}
