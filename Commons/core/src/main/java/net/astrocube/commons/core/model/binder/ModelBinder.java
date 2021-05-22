package net.astrocube.commons.core.model.binder;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

/**
 * Will act as custom binder for {@link Model}
 */
public interface ModelBinder extends Binder {

	default <M extends Model> ModelBindingBuilder<M, M> bindModel(Class<M> modelType) {
		TypeLiteral<M> modelTypeLiteral = TypeLiteral.get(modelType);
		return new ModelBindingBuilder<>(this, modelTypeLiteral, modelTypeLiteral);
	}

	default <M extends Model, P extends PartialModel> ModelBindingBuilder<M, P> bindModel(
		Class<M> modelType,
		Class<P> partialType
	) {
		return new ModelBindingBuilder<>(
			this,
			TypeLiteral.get(modelType),
			TypeLiteral.get(partialType)
		);
	}

}