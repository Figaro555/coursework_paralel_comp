import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IndexingThread extends  Thread{

final File SOURCE_ROOT_FILE = new File("./FilesToIndex");

private int startPosition = 0;
private int endPosition = 0;
private HashMap<String, LinkedList<Integer>> indexDictionary;


public IndexingThread(HashMap<String, LinkedList<Integer>>indexDictionary, int startPosition, int endPosition){
    this.indexDictionary = indexDictionary;
    this.startPosition = startPosition;
    this.endPosition = endPosition;


}

    static private ArrayList<String> fileTermsList(File file) throws IOException {

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        SortedSet<String> terms = new TreeSet<>();


        while (line != null) {
            System.out.println(line);


            List<String> collection = Stream.of(line.split("[^A-Za-z]+"))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            line = bufferedReader.readLine();

            terms.addAll(collection);
        }
        return new ArrayList<>(terms);
    }

    @Override
    public void run(){
        for (int i = startPosition; i < endPosition; i++) {
            try {
                fileTermsList(new File(SOURCE_ROOT_FILE.list()[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
