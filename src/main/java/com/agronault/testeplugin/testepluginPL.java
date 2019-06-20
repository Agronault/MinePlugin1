package com.agronault.testeplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class testepluginPL extends JavaPlugin {

    PlayerListener listener = new PlayerListener();
    HashMap <Player, Integer> KitSelecionado= new HashMap<Player, Integer>();

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(listener, this);
        getLogger().info("Plugin InfinityWars do Agronault carregado");
    }

    @Override
    public void onDisable(){
        getLogger().info("Plugin InfinityWars");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("agro")){
            sender.sendMessage(ChatColor.AQUA+"Obrigado por usar o plugin AgronaultUtils, contate o desenvolvedor para total uso das funcionalidades:");
            return true;
        }else if (command.getName().equalsIgnoreCase("kit")){
            switch (args[0]){
                case "":
                    if(sender instanceof Player){
                        sender.sendMessage(ChatColor.RED+"Selecione algum kit");
                    }
                    break;
                case "thor":
                    if(sender instanceof Player){
                        KitSelecionado.put((Player)sender, 1);
                        darKitThor((Player)sender);
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.RED+"O kit escolhido ainda nao existe!");
                    break;
            }
            return true;
        }

        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void StartGame(){
        if(this.getServer().getOnlinePlayers().size()==1){
            Player[] jogadores =(Player[]) this.getServer().getOnlinePlayers().toArray();
            for(int i=0; i< jogadores.length; i++){
                int kit= KitSelecionado.get(jogadores[i]);
                switch (kit){
                    case 1:
                        darKitThor(jogadores[i]);
                }
            }
        }
    }

    public void darKitThor(Player jogador){
        ItemStack machado= new ItemStack(Material.IRON_AXE);
        machado.getItemMeta().setDisplayName("Rompe Tormentas");
        jogador.getInventory().addItem(machado);
    }
}
