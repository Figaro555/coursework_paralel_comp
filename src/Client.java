import java.io.*;
import java.net.Socket;

public class Client {
    private static final int  SERVER_PORT = 8000;
    private static String ipAddress = "localhost";

    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket(ipAddress, SERVER_PORT);
        } catch (IOException e) {
            System.out.println("socket failed");
            return;
        }

        MassageGetter massageGetter = new MassageGetter(socket);
        massageGetter.start();

        MassageSender massageSender = new MassageSender(socket);
        massageSender.start();


    }

}


class MassageGetter extends Thread{
    private BufferedReader in;
    public MassageGetter(Socket socket) {
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {

        }
    }
    @Override
    public void run(){
        while (true){
            String request;
            try {
                request = in.readLine();
                System.out.println(request);

            } catch (IOException e) {
                break;
            }

        }

    }
}


class MassageSender extends Thread{
    private BufferedReader inputUser;
    private BufferedWriter out;

    public MassageSender(Socket socket) {
        this.inputUser = new BufferedReader(new InputStreamReader(System.in));
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
        }
    }
    @Override
    public void run(){
        while (true){
            String clientRequest;
            try {
                clientRequest = inputUser.readLine();
                //зчитуємо з консолі
                if(clientRequest.equals("N")) break;
                //та відправляємо
                out.write(clientRequest+"\n");
                out.flush();
            } catch (IOException e) {
                break;
            }

        }

    }
}
