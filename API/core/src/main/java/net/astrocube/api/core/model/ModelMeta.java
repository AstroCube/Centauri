package net.astrocube.api.core.model;

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
public class ModelMeta <Complete extends Model, Partial extends PartialModel> {

    private final TypeToken<Complete> completeType;
    private final TypeToken<Partial> partialType;
    private final String routeKey;
    private final int cache;

    @Inject public ModelMeta(TypeLiteral<Complete> completeType, TypeLiteral<Partial> partialType) {
        this.completeType = (TypeToken<Complete>) TypeToken.of(completeType.getType());
        this.partialType = (TypeToken<Partial>) TypeToken.of(partialType.getType());
        final ModelProperties.RouteKey routeKey = completeType.getRawType().getAnnotation(ModelProperties.RouteKey.class);
        final ModelProperties.Cache cache = completeType.getRawType().getAnnotation(ModelProperties.Cache.class);
        this.routeKey = routeKey == null ? completeType.getRawType().getSimpleName() : routeKey.value();
        this.cache = cache == null ? 0 : cache.value();

        System.out.println("Complete shit: " + this.completeType.getType().getTypeName());
    }

}