package ca.ioxom.ioxbot.other;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.Presence;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class StatusSetter extends ListenerAdapter {
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    @Override
    public void onReady(ReadyEvent event) {
        Presence presence = event.getJDA().getPresence();

        Runnable setStatusPrefix = () -> presence.setActivity(Activity.playing("prefix | " + Config.prefix));
        Runnable setStatusHelp = () -> presence.setActivity(Activity.playing("help | " + Config.prefix + "help"));
        scheduler.scheduleAtFixedRate(setStatusPrefix, 0, 20, SECONDS);
        scheduler.scheduleAtFixedRate(setStatusHelp, 20, 20, SECONDS);
    }
}
