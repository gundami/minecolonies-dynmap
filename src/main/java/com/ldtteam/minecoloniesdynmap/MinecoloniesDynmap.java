package com.ldtteam.minecoloniesdynmap;

import com.ldtteam.minecoloniesdynmap.integration.DynmapIntegration;
import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.eventbus.events.colony.ColonyCreatedModEvent;
import com.minecolonies.api.eventbus.events.colony.ColonyNameChangedModEvent;
import com.minecolonies.api.eventbus.events.colony.citizens.CitizenAddedModEvent;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;


@Mod(Constants.MOD_ID)
public class MinecoloniesDynmap
{
    public static final Logger LOGGER = LogUtils.getLogger();
    public MinecoloniesDynmap(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Minecolonies Dynmap");
        DynmapIntegration.startInstance();
        MinecoloniesEventBus.register();
    }
}