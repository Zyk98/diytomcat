package cn.diy.diytomcat.util;

import cn.diy.diytomcat.http.Request;
import cn.diy.diytomcat.http.Response;
import cn.diy.diytomcat.catalina.HttpProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 服务器跳转的思路：修改request的uri，然后在HttpProcessor的execute在执行一次，相当于在服务器内访问某个页面
 */
public class ApplicationRequestDispatcher implements RequestDispatcher {

    private String uri;

    public ApplicationRequestDispatcher(String uri) {
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        this.uri = uri;
    }

    @Override
    public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        cn.diy.diytomcat.http.Request request = (Request) servletRequest;
        cn.diy.diytomcat.http.Response response = (Response) servletResponse;
        request.setUri(uri);

        //新建一个HttpProcessor
        HttpProcessor processor = new HttpProcessor();
        //httpProcessor执行跳转
        processor.execute(request.getSocket(), request, response);
        //请求跳转
        request.setForwarded(true);
    }

    @Override
    public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }
}
