package hu.elte.projeszk.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Mock osztály a socket helyett. A konstruktorban megadott sztring lesz benne az adat.
 * @author betti-laptop
 *
 */
public class FakeSocket extends Socket {
	private InputStream data;
    private OutputStream output;
  
    public FakeSocket(byte[] data) {
        this.data = new ByteArrayInputStream(data);
        this.output = new ByteArrayOutputStream();
    }

	@Override
	public InputStream getInputStream() throws IOException {
		return data;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return output;
	}
    
	@Override
	public synchronized void close() throws IOException {
		data.close();
		output.close();
	}
}
