package com.eucolus.poll;

import com.eucolus.poll.Entities.Request;
import com.eucolus.poll.Entities.RequestOS;
import com.eucolus.poll.Repositories.RequestRepository;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class RequestTrackFilter implements Filter {

    @Autowired
    RequestRepository requestRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        HttpServletRequest servletRequest = (HttpServletRequest)request;

        String header = servletRequest.getHeader("User-Agent");

        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        Browser browser = userAgent.getBrowser();

        String browserName = browser.getName();
        String browserVersion = "";

        Version version = userAgent.getBrowserVersion();

        if(version != null) {
            browserVersion = version.getVersion();
        }

        OperatingSystem os = userAgent.getOperatingSystem();

        Request trackRequest = new Request();
        trackRequest.setRequestUrl(servletRequest.getRequestURI());
        trackRequest.setRequestType(servletRequest.getMethod());
        trackRequest.setDateTime(new Timestamp(new Date().getTime()));
        trackRequest.setIp(request.getRemoteAddr());

        RequestOS requestOS = new RequestOS();
        requestOS.setOsGroup(os.getGroup().getName());
        requestOS.setOsName(os.getName());
        //trackRequest.setOs(requestOS);

        System.out.println(browserName + " " + browserVersion);
    }

    @Override
    public void destroy() {

    }
}
