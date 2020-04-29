package me.kingcjy.ezcommand.command.definition.support;


import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.command.CommandArgument;
import me.kingcjy.ezcommand.command.definition.AbstractCommandTypeDefinition;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Component
public class LocationTypeDefinition extends AbstractCommandTypeDefinition {

    public final static String PATTERN = "[0-9] [0-9] [0-9]";

    public final static Class<?> TYPE = Location.class;
    public final static String TYPE_STRING = "Location";

    public LocationTypeDefinition() {
        super(TYPE, TYPE_STRING, PATTERN);
    }

    @Override
    public Object transform(CommandArgument commandArgument, String command) {

        if(commandArgument.getCommandSender() instanceof Player) {
            Player sender = (Player) commandArgument.getCommandSender();

            Loc loc = getLoc(command);
            Location location = new Location(sender.getWorld(), loc.getX(), loc.getY(), loc.getZ());

            return location;
        }
        return null;
    }

    private Loc getLoc(String command) {
        try {
            int[] xyz = Arrays.asList(command.split(" ")).stream().mapToInt(Integer::parseInt).toArray();
            Loc loc = new Loc(xyz[0], xyz[1], xyz[2]);
            return loc;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Loc {
        private int x;
        private int y;
        private int z;

        public Loc(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
