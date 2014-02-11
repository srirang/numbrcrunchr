package au.com.numbrcrunchr.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class MobileClientRedirectionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        String pageName = url.substring(url.lastIndexOf("/"));
        if (!isClientIPhone(((HttpServletRequest) request)
                .getHeader("User-Agent"))) {
            request.getRequestDispatcher(pageName).forward(request, response);
            chain.doFilter(request, response);
            return;
        }
        request.getRequestDispatcher("mobile/" + pageName).forward(request,
                response);
        chain.doFilter(request, response);
    }

    public static boolean isClientIPhone(String userAgent) {
        return userAgent.toUpperCase().contains("IPHONE");
    }

    public static String getMobileUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        int p = url.lastIndexOf("/");
        if (p == -1) {
            return url;
        }
        return url.substring(0, p).concat("/mobile/")
                .concat(url.substring(p + 1));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Starting filter MobileClientRedirectionFilter");
    }

    @Override
    public void destroy() {
        System.out
                .println("Destroying the filter: MobileClientRedirectionFilter");
    }
}
