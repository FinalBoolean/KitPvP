package cf.strafe.event.events;

import cf.strafe.data.PlayerData;
import cf.strafe.event.Event;

import java.util.ArrayList;

public class Sumo extends Event{

    public int round;
    public ArrayList<PlayerData> roundPlayers = new ArrayList<>();

    public Sumo() {
        maxPlayers = 50;
        state = State.WAITING;
        gameTime = 30;
    }

    @Override
    public void update() {
    }

}
