package mst.eventtools.commands;

import com.github.puregero.multilib.MultiLib;
import mst.eventtools.EventTools;
import mst.eventtools.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ClearNear implements CommandExecutor {
    private FileConfiguration lang = EventTools.instance.getConfigurationManage().getConfig("lang.yml").getFile();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (p == null) {
            p.sendMessage(Utils.chat(lang.getString("onlyPlayerUse")));
            return true;
        }

        if (args.length < 1){
            p.sendMessage(command.getUsage());
            return true;
        }

        double radius  =   Double.valueOf(args[0]);
        if (radius == 0){
            p.sendMessage(command.getUsage());
            return true;
        }

        Location center = p.getLocation();
        Collection<? extends Player> players = MultiLib.getAllOnlinePlayers();
        for (Player p2: players){
            if (p2.getLocation().distance(center) <= radius){
                for (ItemStack is: p2.getInventory().getContents()){
                    p.getInventory().remove(is);
                }
            }
            p2.sendMessage(Utils.chat(lang.getString("clearNearClear")
                    .replace("%admin%" ,p.getDisplayName())));
        }
        p.sendMessage(Utils.chat(lang.getString("clearNearSuccess")
                .replace("%radius%",String.valueOf(radius))));

        return true;
    }
}
