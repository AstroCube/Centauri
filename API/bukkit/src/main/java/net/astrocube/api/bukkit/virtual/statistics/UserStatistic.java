package net.astrocube.api.bukkit.virtual.statistics;

import net.astrocube.api.core.model.ModelProperties;

@ModelProperties.RouteKey("stats")
@ModelProperties.Cache
public interface UserStatistic<T> extends UserStatisticDoc.Complete<T> {}
