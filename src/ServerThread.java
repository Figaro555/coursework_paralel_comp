import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Map;

public class ServerThread extends Thread {
    private Map<String, LinkedList<Integer>> index;
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    public ServerThread(Map<String,LinkedList<Integer>> index, Socket socket) throws IOException {
        this.index = index;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }


    @Override
    public void run() {

    }
}
