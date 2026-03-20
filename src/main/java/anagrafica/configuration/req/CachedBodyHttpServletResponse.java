package anagrafica.configuration.req;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper{
	private final ByteArrayOutputStream capture;
    private ServletOutputStream outputStream;
    private PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        this.capture = new ByteArrayOutputStream();
    }

    public String getCapturedBody() {
        return capture.toString(StandardCharsets.UTF_8);
    }

    public void copyBodyToResponse() throws IOException {
        getResponse().getOutputStream().write(capture.toByteArray());
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (outputStream == null) {
            outputStream = new ServletOutputStream() {
                @Override public void write(int b) { capture.write(b); }
                @Override public boolean isReady() { return true; }
                @Override public void setWriteListener(WriteListener listener) {}
            };
        }
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() {
        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(capture, StandardCharsets.UTF_8));
        }
        return writer;
    }
}
