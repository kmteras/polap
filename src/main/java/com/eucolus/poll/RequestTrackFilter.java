package com.eucolus.poll;

import com.eucolus.poll.entities.Request;
import com.eucolus.poll.entities.RequestBrowser;
import com.eucolus.poll.entities.RequestLocation;
import com.eucolus.poll.entities.RequestOS;
import com.eucolus.poll.repositories.RequestBrowserRepository;
import com.eucolus.poll.repositories.RequestLocationRepository;
import com.eucolus.poll.repositories.RequestOSRepository;
import com.eucolus.poll.repositories.RequestRepository;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

@Component
public class RequestTrackFilter implements Filter {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestOSRepository requestOSRepository;
    @Autowired
    RequestBrowserRepository requestBrowserRepository;
    @Autowired
    RequestLocationRepository requestLocationRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        String header = servletRequest.getHeader("User-Agent");

        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        Browser browser = userAgent.getBrowser();

        String browserName = browser.getName();
        String browserVersion = "";

        Version version = userAgent.getBrowserVersion();

        if (version != null) {
            browserVersion = version.getVersion();
        }

        RequestBrowser requestBrowser = requestBrowserRepository.find(browserName, browserVersion);

        if (requestBrowser == null) {
            requestBrowser = new RequestBrowser();
            requestBrowser.setName(browserName);
            requestBrowser.setVersion(browserVersion);
            try {
                requestBrowser = requestBrowserRepository.save(requestBrowser);
            } catch (DataIntegrityViolationException error) {
                //Two requests were made too quickly at first and it tried to save a os twice
                requestBrowser = requestBrowserRepository.find(browserName, browserVersion);
            }

        }

        OperatingSystem os = userAgent.getOperatingSystem();

        RequestOS requestOS = requestOSRepository.find(os.getName(), os.getGroup().getName());
        if (requestOS == null) {
            requestOS = new RequestOS();
            requestOS.setName(os.getName());
            requestOS.setGroup(os.getGroup().getName());
            try {
                int id = requestOSRepository.add(requestOS);
                requestOS.setId(id);
            } catch (DataIntegrityViolationException error) {
                //Two requests were made too quickly at first and it tried to save a os twice
                requestOS = requestOSRepository.find(os.getName(), os.getGroup().getName());
            }
        }

        RequestLocation requestLocation = requestLocationRepository.find(request.getRemoteAddr());

        if (requestLocation == null) {
            URL url = new URL("http://freegeoip.net/json/" + request.getRemoteAddr());
            Scanner scanner = new Scanner(url.openStream());

            JSONObject jsonObject = new JSONObject(scanner.next());
            requestLocation = new RequestLocation();
            requestLocation.setIp(jsonObject.getString("ip"));
            requestLocation.setCountry(jsonObject.getString("country_name"));
            requestLocation.setCity(jsonObject.getString("city"));
            requestLocation.setLongitude(jsonObject.getDouble("longitude"));
            requestLocation.setLatitude(jsonObject.getDouble("latitude"));

            try {
                requestLocation = requestLocationRepository.save(requestLocation);
            } catch (DataIntegrityViolationException error) {
                //Two requests were made too quickly at first and it tried to save a os twice
                requestLocation = requestLocationRepository.find(request.getRemoteAddr());
            }
        }

        Request trackRequest = new Request();
        trackRequest.setRequestUrl(servletRequest.getRequestURI());
        trackRequest.setRequestType(servletRequest.getMethod());
        trackRequest.setDateTime(new Timestamp(new Date().getTime()));
        trackRequest.setLocation(requestLocation);
        trackRequest.setOs(requestOS);
        trackRequest.setBrowser(requestBrowser);

        requestRepository.save(trackRequest);
    }

    @Override
    public void destroy() {

    }
}
