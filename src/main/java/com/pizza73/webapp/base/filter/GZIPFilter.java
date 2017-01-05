package com.pizza73.webapp.base.filter;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that compresses output with gzip (assuming that browser supports gzip).
 * Code from <a href="http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html">
 * http://www.onjava.com/pub/a/onjava/2003/11/19/filters.html</a>.
 *
 * &copy; 2003 Jayson Falkner You may freely use the code both commercially
 * and non-commercially.
 *
 * @author  Matt Raible
 *
 * @web.filter name="compressionFilter"
 */
public class GZIPFilter extends OncePerRequestFilter {
    protected final transient Logger log = Logger.getLogger(GZIPFilter.class);

    @Override
   public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain)
    throws IOException, ServletException {

        if (isGZIPSupported(request)) {

            GZIPResponseWrapper wrappedResponse =
                new GZIPResponseWrapper(response);

            chain.doFilter(request, wrappedResponse);
            wrappedResponse.finishResponse();

            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Convenience method to test for GZIP cababilities
     * @param req The current user request
     * @return boolean indicating GZIP support
     */
    private boolean isGZIPSupported(HttpServletRequest req) {

        // disable gzip filter for exporting from displaytag
//        String exporting = req.getParameter(TableTagParameters.PARAMETER_EXPORTING);
//
//        if (exporting != null) {
//            log.debug("detected excel export, disabling filter...");
//            return false;
//        }

        String browserEncodings = req.getHeader("accept-encoding");
        boolean supported = ((browserEncodings != null) &&
                             (browserEncodings.indexOf("gzip") != -1));

        String userAgent = req.getHeader("user-agent");

        if ((userAgent != null) && userAgent.startsWith("httpunit")) {
            log.debug("httpunit detected, disabling filter...");

            return false;
        } else {
            return supported;
        }
    }
}
