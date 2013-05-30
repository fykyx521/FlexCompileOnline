package com.duapp.weifan;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flex2.tools.oem.Application;
import flex2.tools.oem.ApplicationCache;
import flex2.tools.oem.Message;
import flex2.tools.oem.Report;
import flex2.tools.oem.VirtualLocalFile;
import flex2.tools.oem.VirtualLocalFileSystem;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    /**
     * Default constructor. 
     */
    public Test() {
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {   

            File parent = new File(".").getCanonicalFile();  
            String src = "<?xml version='1.0'?>" +
            "<s:Application xmlns:fx='http://ns.adobe.com/mxml/2009' " +
            "xmlns:s='library://ns.adobe.com/flex/spark'" +
            " xmlns:mx='library://ns.adobe.com/flex/mx'>" + " " +
            		"<s:Button label='Click Me' click='hello(event)'/>" +
            		"<fx:Script>" +
            		"<![CDATA[ " +
            		" import mx.events.FlexEvent;" +
            		"import mx.controls.Alert;" +
            		"import flash.events.Event;" +
            		"protected function hello(e:Event):void " +
            		"{ " +
            		"	Alert.show('helloWorld'); " +
            		"} " +
            		"]]>" +
            		"</fx:Script>"
            + "</s:Application>";  
           
//            String src="public class A{}";
            //Target target = flex2.tools.API.compile(appPathVirtual, configuration.getFlexConfigConfiguration(), this.swcCache, licenseMap);

            //Movie movie = flex2.linker.API.link(target.units, new PostLink(configuration.getFlexConfigConfiguration()), configuration.getFlexConfigConfiguration());
           
            VirtualLocalFileSystem fs = new VirtualLocalFileSystem();  

            VirtualLocalFile lf =   

                fs.create(new File(parent,"VirtualApp.mxml").getCanonicalPath(),  

                    src, parent, System.currentTimeMillis());  

            Application app = new Application(lf);  
            flex2.tools.oem.Configuration config=app.getDefaultConfiguration();
           // config.setConfiguration(new File("./flex"));
            app.setConfiguration(config);
            response.setContentType("application/x-shockwave-flash");
            app.setApplicationCache(new ApplicationCache());
           // app.setOutput(new File("VirtualApp.swf"));  
            ByteArrayOutputStream br=new ByteArrayOutputStream();
            long result=app.build(br,true);
            if(result>0)//编译成功
            {
            	response.getOutputStream().write(br.toByteArray());
            	response.getOutputStream().flush();
            	response.getOutputStream().close();
            	//request.getRequestDispatcher("/show").include(request, response);
            }else
            {
            	//编译失败
            	response.setContentType("text/html");
            	
            	//读取错误报表
            	Report report=app.getReport();
            	Message[] msgs=report.getMessages();
            	for(Message msg:msgs)
        		{
        			 response.getOutputStream().write(("<div>行号:"+msg.getLine()+"级别:"+msg.getLevel()+"路径:"+msg.getPath()+"列:"+msg.getColumn()+"</div>").getBytes());
        		}
        		response.getOutputStream().flush();
        		response.getOutputStream().close();
//            	request.setAttribute("error", msgs);
//            	request.getRequestDispatcher("/error.jsp").include(request, response);
            	
            }
            	

        } catch (Exception ex) {  

            ex.printStackTrace();  

        }  
        
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
