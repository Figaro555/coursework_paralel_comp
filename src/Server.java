import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Stream;


public class Server {

static final int THREAD_AMOUNT = 1;
static final File SOURCE_ROOT_FILE = new File("C:\\Users\\Nicolay\\Desktop\\IASA\\3 course\\2 semestr\\Paralel\\Coursework\\src\\FilesToIndex");
static final int FILES_AMOUNT = SOURCE_ROOT_FILE.list().length;
static final int PORT = 8000;


    private static int[] startEndGenerate(int arrLength, int parts, int currentIndex) {
        int[] result = new int[2];
        result[0] = Math.round(((float)arrLength)/parts*currentIndex);
        result[1] = Math.round(((float)arrLength)/parts*(currentIndex+1));

        return result;
    }


    private static Map<String,LinkedList<Integer>> mergeHashMaps(HashMap<String,LinkedList<Integer>>[] hashMapsToMerge){

        Map<String,LinkedList<Integer>> finalHashMap;
        finalHashMap = (HashMap)hashMapsToMerge[0].clone();


        for (int i = 1; i< hashMapsToMerge.length;i++) {
            for (String word :hashMapsToMerge[i].keySet()) {

                if(!finalHashMap.containsKey(word)) {
                    finalHashMap.put(word, new LinkedList<Integer>());
                }

                for (int j = 0; j < hashMapsToMerge[i].get(word).size(); j++) {
                    finalHashMap.get(word).add(hashMapsToMerge[i].get(word).get(j));
                }

            }

        }

        for (String term: finalHashMap.keySet()) {
            Collections.sort(finalHashMap.get(term));
        }

        return new TreeMap<>(finalHashMap);
    }


    static Map<String,LinkedList<Integer>> createIndex() {
        IndexingThread[] threadArray = new IndexingThread[THREAD_AMOUNT];
        HashMap<String, LinkedList<Integer>>[] dictionaries = new HashMap[THREAD_AMOUNT];


        for (int i = 0; i < THREAD_AMOUNT; i++) {
            dictionaries[i] = new HashMap<>();
            int[] startEndIndexes = startEndGenerate(FILES_AMOUNT,THREAD_AMOUNT, i);
            threadArray[i] = new IndexingThread(dictionaries[i],startEndIndexes[0],startEndIndexes[1], SOURCE_ROOT_FILE);
            threadArray[i].start();
        }
        Stream.of(threadArray).forEach((IndexingThread thread)->{
            try {
                thread.join();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return mergeHashMaps(dictionaries);
    }


    public static void main(String[] args) throws IOException {

        Map<String,LinkedList<Integer>> index = createIndex();

        LinkedList<ServerThread> list = new LinkedList<>();
        ServerSocket server = null;

        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try{
            while (true) {
                Socket socket = server.accept();
                ServerThread serverThread = new ServerThread(index, socket, SOURCE_ROOT_FILE);
                list.add(serverThread);
                serverThread.start();

            }
        }finally {
            server.close();
        }


    }
}
