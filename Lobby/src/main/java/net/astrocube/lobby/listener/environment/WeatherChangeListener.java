package net.astrocube.lobby.listener.environment;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

}
