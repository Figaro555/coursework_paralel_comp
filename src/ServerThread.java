import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Map;

public class ServerThread extends Thread {
    private final File SOURCE_ROOT_FILE;
    private Map<String, LinkedList<Integer>> index;
    private BufferedWriter out;
    private BufferedReader in;

    public ServerThread(Map<String,LinkedList<Integer>> index, Socket socket, File SOURCE_ROOT_FILE) throws IOException {
        this.index = index;
        this.SOURCE_ROOT_FILE = SOURCE_ROOT_FILE;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }


    @Override
    public void run() {
        try{
            while(true){
                String request;

                out.write("What word would you like to find?\n");
                out.flush();

                request =in.readLine();

                if(!index.containsKey(request)){
                    out.write("There's no such word " + request + "\n");
                    out.flush();
                    continue;
                }
                for (Integer docId: index.get(request)) {
                    out.write(  SOURCE_ROOT_FILE.list()[docId] + "\n");
                    out.flush();
                }
                out.write( "---------------------\n");
                out.flush();

            }
        }catch(IOException e){}

    }
}
