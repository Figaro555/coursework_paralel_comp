import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;
//import java.util.HashMap;

public class Server {
static int THREAD_AMOUNT = 1;



    static private ArrayList<String> fileTermsList(File file) throws IOException {

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        SortedSet<String> terms = new TreeSet<>();


        while (line != null) {
            System.out.println(line);


            Stream.of(line.split("[^A-Za-z]+"))
                    .map(String::toLowerCase)
                    .forEach( s->{
                        terms.add(s);
                    });

            line = bufferedReader.readLine();

        }
        return new ArrayList<>(terms);
    }



    public static void main(String[] args) throws IOException {
        File sourceRootFile = new File("./FilesToIndex");


        ArrayList arr = fileTermsList(new File("./FilesToIndex/testNeg5500_3.txt"));
        //Stream.of(arr).forEach(s-> System.out.println(s+"\t"));
        System.out.println(sourceRootFile.list().length);

        for (String name : sourceRootFile.list()) {
            System.out.println(name);
        }
    }
}
