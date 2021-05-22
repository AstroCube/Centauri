package net.astrocube.commons.core.model.binder;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import java.util.function.Consumer;

/**
 * Will act as custom binder for {@link Model}
 */
public interface ModelBinderModule extends Binder {

	default <M extends Model> ModelBind<M, M> bindModel(Class<M> M) {
		return ModelBind.of(this, M, M);
	}

	default <M extends Model> ModelBind<M, M> bindModel(TypeLiteral<M> M) {
		return ModelBind.of(this, M, M);
	}

	default <M extends Model, P extends PartialModel> ModelBind<M, P> bindModel(Class<M> M, Class<P> P) {
		return ModelBind.of(this, M, P);
	}

	default <M extends Model, P extends PartialModel> ModelBind<M, P> bindModel(TypeLiteral<M> M, TypeLiteral<P> P) {
		return ModelBind.of(this, M, P);
	}

	default <M extends Model> void bindModel(Class<M> M, Consumer<ModelBind<M, M>> block) {
		block.accept(ModelBind.of(this, M, M));
	}

	default <M extends Model> void bindModel(TypeLiteral<M> M, Consumer<ModelBind<M, M>> block) {
		block.accept(ModelBind.of(this, M, M));
	}

	default <M extends Model, P extends PartialModel> void bindModel(Class<M> M, Class<P> P, Consumer<ModelBind<M, P>> block) {
		block.accept(ModelBind.of(this, M, P));
	}

	default <M extends Model, P extends PartialModel> void bindModel(TypeLiteral<M> M, TypeLiteral<P> P, Consumer<ModelBind<M, P>> block) {
		block.accept(ModelBind.of(this, M, P));
	}

}