import java.util.HashMap;
import java.util.LinkedList;

public class IndexingThread extends  Thread{
private int startPosition = 0;
private int endPosition = 0;
private HashMap<String, LinkedList<Integer>> indexDictionary;

public IndexingThread(HashMap<String, LinkedList<Integer>>indexDictionary, int startPosition, int endPosition){
    this.indexDictionary = indexDictionary;
    this.startPosition = startPosition;
    this.endPosition = endPosition;


}


    @Override
    public void run() {
        super.run();
    }
}
