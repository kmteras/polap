package com.eucolus.poll.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.bitwalker.useragentutils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");

        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        Browser browser = userAgent.getBrowser();

        String browserName = browser.getName();
        String browserVersion = "";

        Version version = userAgent.getBrowserVersion();

        if(version != null) {
            browserVersion = version.getVersion();
        }

        OperatingSystem os = userAgent.getOperatingSystem();
        String osName = os.getName();
        String osGroup = os.getGroup().getName();

        System.out.println(browserName + " " + browserVersion);
        System.out.println(osName + " " + osGroup);
        System.out.println(request.getRemoteAddr());
        System.out.println(new Date());
        return "index";
    }
}
