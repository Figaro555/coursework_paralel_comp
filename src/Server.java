import java.io.*;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;
//import java.util.HashMap;

public class Server {
static int THREAD_AMOUNT = 1;



    static private ArrayList<String> fileLexemesList(File file) throws IOException {

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        SortedSet<String> lexemes = new TreeSet<>();


        while (line != null) {
            System.out.println(line);


            Stream.of(line.split("[^A-Za-z]+"))
                    .map(String::toLowerCase)
                    .forEach( s->{
                        lexemes.add(s);
                    });

            line = bufferedReader.readLine();

        }
        return new ArrayList<>(lexemes);
    }



    public static void main(String[] args) throws IOException {
        File resourceFile = new File("./FilesToIndex");
        //System.out.println(file.getAbsolutePath());

        ArrayList arr = fileLexemesList(new File("./FilesToIndex/testNeg/5499_2.txt"));
        Stream.of(arr).forEach(s-> System.out.println(s+"\t"));

    }
}
