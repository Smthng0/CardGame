package dreamfactory.cardgame.io;

import dreamfactory.cardgame.cards.MinionCard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlainMinionLoader {
    public  List<MinionCard> createMinionListFromCSV() {
        List<MinionCard> minionList = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        String pathToFile = classLoader.getResource(
                "file/GenericMinions.csv").getPath();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(new File(pathToFile)))) {
            readLine(minionList, reader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return minionList;
    }

    private void readLine(List<MinionCard> minionList,
                          BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] minion = line.split(",");

            minionList.add(new MinionCard(minion[1],
                            Integer.parseInt(minion[0]),
                            Integer.parseInt(minion[2]),
                            Integer.parseInt(minion[3])
                    )
            );
        }
    }

}
