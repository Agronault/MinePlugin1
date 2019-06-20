package com.agronault.testeplugin;



import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        e.getPlayer().sendMessage(ChatColor.AQUA+""+ChatColor.BOLD+ "Ola', " + e.getPlayer().getDisplayName()+ ", Bem vindo a um servidor patrocinado pelo Agronault");
    }

    @EventHandler
    public void onDropClock(PlayerDropItemEvent e){


        if(e.getItemDrop().getItemStack().getType().name()== "COMPASS" && e.getItemDrop().getItemStack().getAmount() == 64) {
            //e.getPlayer().sendMessage("Plugin NPC em desenvolvimento");
            e.getPlayer().setResourcePack("https://sapixcraft.com/downloads/sapixcraft-java/114/sc-114-256");
            if (e.getPlayer().getGameMode().name() == "SURVIVAL") {
                e.getPlayer().setGameMode(GameMode.CREATIVE);

                e.getPlayer().sendTitle("Modo de jogo:", ChatColor.GREEN+"CRIATIVO", 1, 10, 1);
                e.getItemDrop().setCustomName(ChatColor.GREEN + "modo: CRIATIVO");
                e.getItemDrop().setCustomNameVisible(true);

            } else {
                e.getPlayer().setGameMode(GameMode.SURVIVAL);

                e.getPlayer().sendTitle("Modo de jogo:", ChatColor.RED+"SOBREVIVENCIA", 1, 10, 1);
                e.getItemDrop().setCustomName(ChatColor.RED + "modo: SOBREVIVENCIA");
                e.getItemDrop().setCustomNameVisible(true);
            }

            e.setCancelled(true);


        }

        if(e.getItemDrop().getItemStack().getType().name()== "CLOCK" && e.getItemDrop().getItemStack().getAmount() == 64) {
            if(e.getPlayer().getWorld().getTime()==13000) {
                e.getPlayer().sendTitle("Tempo:", ChatColor.GREEN+"DIA", 1, 10, 1);
                e.getPlayer().getServer().getWorld(e.getPlayer().getWorld().getName()).setTime(0);
            }
            else {
                e.getPlayer().sendTitle("Tempo:", ChatColor.RED+"NOITE", 1, 10, 1);
                e.getPlayer().getServer().getWorld(e.getPlayer().getWorld().getName()).setTime(13000);
            }

            e.setCancelled(true);
        }

        //e.getPlayer().sendMessage(ChatColor.AQUA+ "debug(itemTypeName):" + e.getItemDrop().getItemStack().getType().name());
        //e.getPlayer().sendMessage(ChatColor.AQUA+ "debug(displayName):" + e.getItemDrop().getItemStack().getItemMeta().getDisplayName());

        if(e.getItemDrop().getItemStack().getType().name()== "WRITTEN_BOOK"){

            BookMeta metaAuxiliar= (BookMeta) e.getItemDrop().getItemStack().getItemMeta();

            String mob= metaAuxiliar.getPage(1);
            //e.getPlayer().sendMessage(metaAuxiliar.getPage(1));
            //e.getPlayer().performCommand( "summon " +mob+" ~ ~ ~ {CustomName:\"\\\""+metaAuxiliar.getTitle()+"\\\"\"}");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon " +mob+" "+String.valueOf(e.getPlayer().getLocation().getX())+" "+String.valueOf(e.getPlayer().getLocation().getY())+" " +String.valueOf(e.getPlayer().getLocation().getZ())+" {CustomName:\"\\\""+metaAuxiliar.getTitle()+"\\\"\"}" );
            //e.getPlayer().performCommand("summon skeleton_horse ~ ~ ~ {Passengers:[{id:villager,VillagerData:{profession:farmer,level:2,type:plains},Invulnerable:1,PersistenceRequired:1,Silent:1,NoAI:1,CustomName:\"\\\""+ metaAuxiliar.getTitle() +"\\\"\",ActiveEffects:[{Id:24,Amplifier:0,Duration:999999}]}]}");
            e.getPlayer().sendTitle("NPC Criado:", ChatColor.BLUE+ metaAuxiliar.getTitle(), 1, 10, 1);




            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Mob && e.getDamager() instanceof Player) {
            Mob mob = (Mob) e.getEntity();
            Player jogador = (Player) e.getDamager();

            if(jogador.getItemInHand().getType().name()== "WRITTEN_BOOK") {
                mob.remove();
                e.setCancelled(true);
            }
        }
    }




}
