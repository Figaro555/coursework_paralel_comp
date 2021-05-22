import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IndexingThread extends  Thread {

private File SOURCE_ROOT_FILE;
private int startPosition = 0;
private int endPosition = 0;
private Map<String, LinkedList<Integer>> indexDictionary;


public IndexingThread(Map<String, LinkedList<Integer>>indexDictionary, int startPosition, int endPosition, File SOURCE_ROOT_FILE) {
    this.indexDictionary = indexDictionary;
    this.startPosition = startPosition;
    this.endPosition = endPosition;
    this.SOURCE_ROOT_FILE = SOURCE_ROOT_FILE;

}

    static private List<String> fileTermsList(File file) throws IOException {

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = bufferedReader.readLine();

        List<String> terms = new ArrayList<>();


        while (line != null) {

            List<String> collect = Stream.of(line.split("[^A-Za-z]+"))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            terms.addAll(collect);

            line = bufferedReader.readLine();

        }
        return terms;
    }

    @Override
    public void run() {

        for (int docId = startPosition; docId < endPosition; docId++) {

            List<String> uniqueTerms;

            try {
                uniqueTerms = fileTermsList(new File(SOURCE_ROOT_FILE + "\\" +  SOURCE_ROOT_FILE.list()[docId]));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }


            for (int j = 0; j < uniqueTerms.size(); j++) {
                String word = uniqueTerms.get(j);

                if(!indexDictionary.containsKey(word)) {
                    indexDictionary.put(word, new LinkedList<Integer>());
                }

                indexDictionary.get(word).add(docId);

            }

        }

    }
}
