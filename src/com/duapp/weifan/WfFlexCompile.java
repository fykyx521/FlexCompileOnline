package com.duapp.weifan;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class WfFlexCompile
 */
public class WfFlexCompile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WfFlexCompile() {
		super();
		// TODO Auto-generated constructor stub
	}
	class ErrorLog
	{
		Message msg;
		int errorCode;
		String errorSource;
		public ErrorLog(Message msg,int errorCode,String errorSource)
		{
			this.msg=msg;
			this.errorCode=errorCode;
			this.errorSource=errorSource;
		}
	}
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		File parent = new File(".").getCanonicalFile();
		String src = request.getParameter("text");
		//src = src.replaceAll("[\\s&&[^\r\n]]*(?:[\r\n][\\s&&[^\r\n]]*)+", "");// 替换xml标签间的空格

		VirtualLocalFileSystem fs = new VirtualLocalFileSystem();
		VirtualLocalFile lf = fs.create(
				new File(parent, "VirtualApp.mxml").getCanonicalPath(), src,
				parent, System.currentTimeMillis());
		Application app = new Application(lf);
		flex2.tools.oem.Configuration config = app.getDefaultConfiguration();

		app.setConfiguration(config);
		response.setContentType("application/x-shockwave-flash");
		app.setApplicationCache(new ApplicationCache());
		// app.setOutput(new File("VirtualApp.swf"));
		ByteArrayOutputStream br = new ByteArrayOutputStream();//存储编译后的二进制数据
		
		final List<ErrorLog> logs=new ArrayList<ErrorLog>();
		app.setLogger(new flex2.tools.oem.Logger() {

			public void log(Message arg0, int arg1, String arg2) {
				logs.add(new ErrorLog(arg0,arg1,arg2));
			}

		});
		long result = app.build(br, true);
		if (result > 0)// 编译成功
		{
			response.getOutputStream().write(br.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			// request.getRequestDispatcher("/show").include(request, response);
		} else {
			// 编译失败
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			// 读取错误报表
			for(ErrorLog log:logs)
			{
				Message msg=log.msg;
				String out="\n\nline:" + msg.getLine() + "\nlevel:" + msg.getLevel()
						+ "\npath:" + msg.getPath() + "\ncolumn:"
						+ msg.getColumn() + "\nErrorCode:"+log.errorCode+"\nSource:"+log.errorSource;
				response.getWriter().write(out);		
			}
			response.getWriter().flush();
			response.getWriter().close();

		}

	}

}
