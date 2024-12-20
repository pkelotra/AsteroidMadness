import java.io.*;
import java.util.Comparator;
// import java.nio.file.Paths;
// import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ScoreManager {
    private LinkedHashMap<String,Integer> score=new LinkedHashMap<>();

    public ScoreManager(){
        //reading all data from the file to the score manager to maintain
        this.readScoresFromFile();
    }

    public void addscore(String usr,int usr_score){
        score.put(usr,usr_score);

    }



    public LinkedHashMap<String,Integer> sort(){

        //sorting the map according to the values to get the top 3
        LinkedHashMap<String, Integer> sortedMap = this.score.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        return sortedMap;
    }



    public void writeScore() {



        // LinkedHashMap<String,Integer> d= sort();
        String fileName="SCORE_FILE.txt";
        this.score = sort();
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write the content to the file
            for(String key: score.keySet()){
                writer.write(key+" : "+ String.valueOf(score.get(key))+"\n");
            }

        } catch (IOException e) {
            // Handle any errors
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public void readScoresFromFile(){
        String fileName="SCORE_FILE.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Read and add each line to map
            while ((line = reader.readLine()) != null) {
                String newline=line.replace("\n","");
                String[] parts=newline.split(" : ");
                this.score.put(parts[0],Integer.parseInt(parts[1]));

            }
        } catch (IOException e) {
            // Handle any errors
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }
}
