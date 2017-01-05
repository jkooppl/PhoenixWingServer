package com.pizza73.webapp.base.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;


/**
 * Wraps Response for GZipFilter
 *
 * @author  Matt Raible, cmurphy@intechtual.com
 */
public class GZIPResponseWrapper extends HttpServletResponseWrapper {
    protected transient final Logger log = Logger.getLogger(GZIPResponseWrapper.class);
    protected HttpServletResponse origResponse = null;
    protected ServletOutputStream stream = null;
    protected PrintWriter writer = null;
    protected int error = 0;

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
        origResponse = response;
    }

    public ServletOutputStream createOutputStream() throws IOException {
        return (new GZIPResponseStream(origResponse));
    }

    public void finishResponse() {
        try {
            if (writer != null) {
                writer.close();
            } else {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
        }
    }

    @Override
   public void flushBuffer() throws IOException {
        if (stream != null) {
            stream.flush();
        }
    }

    @Override
   public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called!");
        }

        if (stream == null) {
            stream = createOutputStream();
        }

        return (stream);
    }

    @Override
   public PrintWriter getWriter() throws IOException {
        // From cmurphy@intechtual.com to fix:
        // https://appfuse.dev.java.net/issues/show_bug.cgi?id=59
        if (this.origResponse != null && this.origResponse.isCommitted()) {
            return super.getWriter();
        }

        if (writer != null) {
            return (writer);
        }

        if (stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }

        stream = createOutputStream();
        writer =
            new PrintWriter(new OutputStreamWriter(stream,
                                                   origResponse.getCharacterEncoding()));

        return (writer);
    }

    @Override
   public void setContentLength(int length) {
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
     */
    @Override
   public void sendError(int error, String message) throws IOException {
        super.sendError(error, message);
        this.error = error;

        if (log.isDebugEnabled()) {
            log.debug("sending error: " + error + " [" + message + "]");
        }
    }
}
