package mst.eventtools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EffectNearTabCompleter implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
            ArrayList<String> data = new ArrayList<>();
            if (args.length == 2){
                for (PotionEffectType pet: PotionEffectType.values())
                    data.add(pet.getName());
            }
            return data;
        }
}
