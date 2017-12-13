package dreamfactory.cardgame.client;

import dreamfactory.cardgame.Client;
import dreamfactory.cardgame.api.FeignFactory;
import dreamfactory.cardgame.api.actions.Action;
import dreamfactory.cardgame.api.actions.Attack;
import dreamfactory.cardgame.api.actions.PlayCard;
import org.junit.Test;

import java.util.List;

public class ClientTest {

    @Test
    public void create_OK() {
        Client client = createClient();
        client.create();
        System.out.println(client.getStatus());
    }

    @Test
    public void gameReady_OK() {
        Client client = createClient();
        System.out.println(client.getStatus());
        System.out.println(client.gameReady());
        System.out.println(client.getStatus());
    }

    @Test
    public void gameStart_OK() {
        Client client = createClient();
        client.startGame();
        System.out.println(client.getStatus());
    }

    @Test
    public void getStatus_OK() {
        Client client = createClient();
        System.out.println(client.getStatus());

    }

    @Test
    public void endTurn_OK() {
        Client client = createClient();
        client.endTurn();
        System.out.println(client.getStatus());
    }

    @Test
    public void sendAction_OK() {
        Client client = createClient();
        System.out.println(client.sendAction(new Attack(0,0)));
    }

    @Test
    public void getActions_OK() {
        Client client = createClient();
        List<Action> actionList = client.getActions();
        System.out.println(actionList.toString());
        for (Action action : actionList) {
            System.out.println(action.getClass().getSimpleName());
        }
    }

    private Client createClient() {
        FeignFactory feign = new FeignFactory("localhost");
        return new Client("frane",
                feign.clientCreateGameBuilder(),
                feign.clientCommandsBuilder());
    }
}