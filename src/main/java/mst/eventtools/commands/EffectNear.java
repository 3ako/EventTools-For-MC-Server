package mst.eventtools.commands;

import com.github.puregero.multilib.MultiLib;
import mst.eventtools.EventTools;
import mst.eventtools.files.Configuration;
import mst.eventtools.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class EffectNear implements CommandExecutor {
    private FileConfiguration lang = EventTools.instance.getConfigurationManage().getConfig("lang.yml").getFile();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;
        if (player == null) {
            player.sendMessage(Utils.chat(lang.getString("onlyPlayerUse")));
            return true;
        }

        if (args.length < 4){
            sender.sendMessage(command.getUsage());
            return true;
        }

        double radius  =   Double.valueOf(args[0]);
        int time       = Integer.parseInt(args[2]);
        int power      = Integer.parseInt(args[3]);
        PotionEffectType effect = PotionEffectType.getByName(args[1]);
        if (effect == null){
            effect = PotionEffectType.getById(Integer.parseInt(args[1]));
        }

        if (effect == null && !args[1].equalsIgnoreCase("clear")){
            player.sendMessage(command.getUsage());
            return true;
        }

        Location center = player.getLocation();
        Collection<? extends Player> players = MultiLib.getAllOnlinePlayers();

        for (Player p: players){
            if (p.getLocation().distance(center) <= radius){
                if (effect == null){
                    for (PotionEffect e : p.getActivePotionEffects()){
                        p.removePotionEffect(e.getType());
                    }

                    p.sendMessage(Utils.chat(lang
                            .getString("effectNearClear")
                            .replace("%admin%",player.getDisplayName())));
                }else{
                    p.addPotionEffect(new PotionEffect(effect,time*20,power));

                    p.sendMessage(Utils.chat(lang
                            .getString("effectNearGive")
                            .replace("%admin%" ,player.getDisplayName())
                            .replace("%effect%",effect.getName())));
                }
            }
        }

        player.sendMessage(Utils.chat(lang
                .getString("effectNearSuccess")
                .replace("%radius%",String.valueOf(radius))
                .replace("%effect%",effect!=null?effect.getName():"CLEAR")));
        return true;
    }
}
