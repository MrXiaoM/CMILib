package net.Zrips.CMILib.Shadow;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.Zrips.CMILib.CMILib;
import net.Zrips.CMILib.Locale.Snd;

public class ShadowCommandListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onShadowCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();

        String message = event.getMessage();

        if (!message.startsWith("/" + ShadowCommand.prefix + " shadowcmd "))
            return;

        event.setCancelled(true);
        String id = message.substring(("/" + ShadowCommand.prefix + " shadowcmd ").length(), message.length()).split(" ")[0];

        ShadowCommandInfo cmd = ShadowCommand.getShadowCommand(player, id);
                
        if (cmd != null) {
            String command = cmd.getCmd();
            Snd snd = new Snd(player, player);
            command = CMILib.getInstance().getLM().updateSnd(snd, command);

            if (cmd.getType() == ShadowCommandType.Player) {
                command = CMILib.getInstance().getPlaceholderAPIManager().translateOwnPlaceHolder(player, command);
                PlayerCommandPreprocessEvent ev = new PlayerCommandPreprocessEvent(player, "/" + command);
                CMILib.getInstance().getServer().getPluginManager().callEvent(ev);
                if (ev.isCancelled())
                    return;
                player.performCommand(cmd.getCmd());
            }
        }
    }
}
