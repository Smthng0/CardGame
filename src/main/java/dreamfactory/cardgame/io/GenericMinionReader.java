package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenericMinionReader {
    public  List<MinionCard> createMinionListFromCSV() {

        List<MinionCard> minionList = new ArrayList<>();
        File minionCSVFile = new File("src/main/resources/file/GenericMinions.csv");
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(minionCSVFile))) {
            while ((line = reader.readLine()) != null) {
                String[] minion = line.split(",");

                minionList.add(new MinionCard(minion[1],
                                Integer.parseInt(minion[0]),
                                Integer.parseInt(minion[2]),
                                Integer.parseInt(minion[3])
                        )
                );
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return minionList;
    }

}
