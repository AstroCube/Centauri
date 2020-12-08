package net.astrocube.api.core.model;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import lombok.Getter;

/**
 * This class will be in charge of containing all the {@link Model} MetaData
 * in order to be obtained later.
 */
@SuppressWarnings("All")
@Getter
public class ModelMeta<Complete extends Model, Partial extends PartialModel> {

    private final JavaType completeType;
    private final JavaType partialType;
    private final String routeKey;
    private final int cache;

    @Inject public ModelMeta(ObjectMapper mapper, TypeLiteral<Complete> completeType, TypeLiteral<Partial> partialType) {
        this.completeType = mapper.getTypeFactory().constructType(completeType.getType());
        this.partialType = mapper.getTypeFactory().constructType(partialType.getType());
        final ModelProperties.RouteKey routeKey = completeType.getRawType().getAnnotation(ModelProperties.RouteKey.class);
        final ModelProperties.Cache cache = completeType.getRawType().getAnnotation(ModelProperties.Cache.class);
        this.routeKey = routeKey == null ? completeType.getRawType().getSimpleName() : routeKey.value();
        this.cache = cache == null ? 0 : cache.value();
    }

}