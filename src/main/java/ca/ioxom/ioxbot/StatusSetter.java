package ca.ioxom.ioxbot;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.Presence;

public class StatusSetter extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        Presence presence = event.getJDA().getPresence();
        presence.setActivity(Activity.playing(Config.status));
    }
}
