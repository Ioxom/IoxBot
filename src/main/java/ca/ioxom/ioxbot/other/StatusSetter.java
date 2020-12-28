package ca.ioxom.ioxbot.other;

import ca.ioxom.ioxbot.frame.Main;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.Presence;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class StatusSetter extends ListenerAdapter {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void onReady(ReadyEvent event) {
        StatusRunnable setStatus = new StatusRunnable(event.getJDA().getPresence());
        scheduler.scheduleAtFixedRate(setStatus, 0, 20, SECONDS);
    }

    public static class StatusRunnable implements Runnable {
        private final String[] statuses = {"prefix | " + Main.config.formattedPrefix, "help | " + Main.config.formattedPrefix + "help"};
        private int i;
        private final Presence presence;
        public StatusRunnable(Presence presence) {
            this.presence = presence;
            this.i = 0;
        }

        @Override
        public void run() {
            if (i == 0) {
                i = 1;
            } else {
                i = 0;
            }
            this.presence.setActivity(Activity.playing(this.statuses[i]));
        }
    }
}
