package me.kingcjy.ezcommand.minecraft.command.definition;


import me.kingcjy.ezcommand.minecraft.command.CommandArgument;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocationTypeDefinitionTest {

    private LocationTypeDefinition locationTypeDefinition;

    @Mock
    private Player player;

    @Mock
    private World world;

    @Before
    public void before() {
        locationTypeDefinition = new LocationTypeDefinition();
    }

    @Test
    public void Location_캐스팅_테스트() {
        when(player.getWorld()).thenReturn(world);
        CommandArgument commandArgument = new CommandArgument(player, null, null, null, null);

        Location location = new Location(world, 0, 0, 0);
        Location transformLocation = (Location) locationTypeDefinition.transform(commandArgument, "0 0 0");

        Assert.assertEquals(location, transformLocation);
    }
}
