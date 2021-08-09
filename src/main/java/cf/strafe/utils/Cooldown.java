package cf.strafe.utils;

import lombok.Getter;
@Getter
public class Cooldown {
    private long cooldown;
    private long time;

    public boolean hasCooldown(long seconds) {
        this.cooldown = seconds;
        if(time == 0 || getSeconds() < 1) {
            time = System.currentTimeMillis();
            return true;
        }
        return false;

    }

    public long getSeconds() {
        return ((time / 1000) + cooldown) - (System.currentTimeMillis() / 1000);
    }
}