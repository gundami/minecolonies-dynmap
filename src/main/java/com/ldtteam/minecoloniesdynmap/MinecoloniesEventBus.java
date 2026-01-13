package com.ldtteam.minecoloniesdynmap;

import com.ldtteam.minecoloniesdynmap.integration.DynmapIntegration;
import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.eventbus.EventBus;
import com.minecolonies.api.eventbus.events.colony.buildings.BuildingConstructionModEvent;
import com.minecolonies.api.eventbus.events.colony.citizens.CitizenAddedModEvent;
import com.minecolonies.api.eventbus.events.colony.ColonyCreatedModEvent;
import com.minecolonies.api.eventbus.events.colony.ColonyDeletedModEvent;
import com.minecolonies.api.eventbus.events.colony.ColonyNameChangedModEvent;
import com.minecolonies.api.eventbus.events.colony.ColonyTeamColorChangedModEvent;
import com.minecolonies.api.eventbus.events.ColonyManagerLoadedModEvent;

import java.util.function.Consumer;

/**
 * Event bus for receiving events from Minecolonies.
 */

public class MinecoloniesEventBus
{
    public static void register()
    {
        EventBus eventBus = IMinecoloniesAPI.getInstance().getEventBus();
        eventBus.subscribe(ColonyManagerLoadedModEvent.class,MinecoloniesEventBus::onColonyManagerLoaded);
        eventBus.subscribe(ColonyCreatedModEvent.class,MinecoloniesEventBus::onColonyCreated);
        eventBus.subscribe(ColonyDeletedModEvent.class,MinecoloniesEventBus::onColonyDeleted);
        eventBus.subscribe(ColonyNameChangedModEvent.class,MinecoloniesEventBus::onColonyInformationChanged);
        eventBus.subscribe(ColonyTeamColorChangedModEvent.class,MinecoloniesEventBus::onColonyColorChanged);
        eventBus.subscribe(BuildingConstructionModEvent.class,MinecoloniesEventBus::onColonyBuildingConstruction);
        eventBus.subscribe(CitizenAddedModEvent.class,MinecoloniesEventBus::onCitizenAdded);
    }

    public static void onColonyManagerLoaded(ColonyManagerLoadedModEvent event)
    {
        MinecoloniesDynmap.LOGGER.info("Colony Manager Loaded");
        event.getColonyManager().getAllColonies().forEach(colony -> run(integration -> integration.createColony(colony)));
    }

    /**
     * Util method that wraps the optional chain for getting the integration.
     *
     * @param callback the code to execute if the Dynmap integration is active.
     */
    private static void run(Consumer<DynmapIntegration> callback)
    {
        DynmapIntegration.getInstance().ifPresent(callback);
    }

    public static void onColonyCreated(ColonyCreatedModEvent event)
    {
        MinecoloniesDynmap.LOGGER.info("Colony Created");
        run(integration -> integration.createColony(event.getColony()));
    }

    public static void onColonyDeleted(ColonyDeletedModEvent event)
    {
        MinecoloniesDynmap.LOGGER.info("Colony Deleted");
        run(integration -> integration.deleteColony(event.getColony()));
    }

    public static void onColonyInformationChanged(ColonyNameChangedModEvent event)
    {
        MinecoloniesDynmap.LOGGER.info("Colony Information Changed");
        run(integration -> integration.updateName(event.getColony()));
    }

    public static void onColonyColorChanged(ColonyTeamColorChangedModEvent event)
    {
        MinecoloniesDynmap.LOGGER.info("Colony Color Changed");
        run(integration -> integration.updateTeamColor(event.getColony()));
    }

    public static void onColonyBuildingConstruction(BuildingConstructionModEvent event)
    {
        MinecoloniesDynmap.LOGGER.info("Colony Building Construction");
        run(integration -> integration.updateBuildings(event.getColony()));
    }


    public static void onCitizenAdded(CitizenAddedModEvent event)
    {
        MinecoloniesDynmap.LOGGER.info("Citizen Added");
        run(integration -> integration.updateCitizenCount(event.getColony()));
    }

}