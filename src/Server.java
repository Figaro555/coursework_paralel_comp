import java.io.*;
import java.util.*;
import java.util.stream.Stream;
//import java.util.HashMap;

public class Server {
static int THREAD_AMOUNT = 5;
static final File SOURCE_ROOT_FILE = new File("C:\\Users\\Nicolay\\Desktop\\IASA\\3 course\\2 semestr\\Paralel\\Coursework\\src\\FilesToIndex");
static final int FILES_AMOUNT = SOURCE_ROOT_FILE.list().length;



    private static int[] startEndGenerate(int arrLength, int parts, int currentIndex){
        int[] result = new int[2];
        result[0] = Math.round(((float)arrLength)/parts*currentIndex);
        result[1] = Math.round(((float)arrLength)/parts*(currentIndex+1));

        return result;
    }

    private static Map<String,LinkedList<Integer>> mergeHashMaps(HashMap<String,LinkedList<Integer>>[] hashMaps){
        HashMap<String,LinkedList<Integer>> finalHashMap = new HashMap<String, LinkedList<Integer>>();
        finalHashMap = (HashMap)hashMaps[0].clone();


        for (int i = 1; i< hashMaps.length;i++) {
            for (String word :hashMaps[i].keySet()) {

                if(!finalHashMap.containsKey(word)){
                    finalHashMap.put(word, new LinkedList<Integer>());
                }

                for (int j = 0; j < hashMaps[i].get(word).size(); j++) {
                    finalHashMap.get(word).add(hashMaps[i].get(word).get(j));
                }

            }

        }
        TreeMap<String, LinkedList<Integer>> finalSortedMap = new TreeMap<>(finalHashMap);
        return finalSortedMap;
    }

    public static void main(String[] args) {

        IndexingThread[] threadArray = new IndexingThread[THREAD_AMOUNT];
        HashMap<String, LinkedList<Integer>>[] dictionaries = new HashMap[THREAD_AMOUNT];


        for (int i = 0; i < THREAD_AMOUNT; i++) {
            dictionaries[i] = new HashMap<>();
            int[] startEndIndexes = startEndGenerate(FILES_AMOUNT,THREAD_AMOUNT, i);
            threadArray[i] = new IndexingThread(dictionaries[i],startEndIndexes[0],startEndIndexes[1]);
            threadArray[i].start();
        }
        Stream.of(threadArray).forEach((IndexingThread thread)->{
            try{
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        Map indexMap = mergeHashMaps(dictionaries);

    }
}
