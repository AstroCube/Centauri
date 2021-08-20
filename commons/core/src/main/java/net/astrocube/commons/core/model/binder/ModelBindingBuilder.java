package net.astrocube.commons.core.model.binder;

import com.google.inject.Binder;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.OptionalBinder;
import com.google.inject.util.Types;
import net.astrocube.inject.ProtectedBinder;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.delete.DeleteService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.utils.TypeArgument;
import net.astrocube.api.core.utils.Typing;

public class ModelBindingBuilder<Complete extends Model, Partial extends PartialModel> {

	private final OptionalBinder<CreateService<Complete, Partial>> createServiceBinder;
	private final OptionalBinder<DeleteService<Complete>> deleteServiceBinder;
	private final OptionalBinder<FindService<Complete>> findServiceBinder;
	private final OptionalBinder<PaginateService<Complete>> paginateServiceBinder;
	private final OptionalBinder<QueryService<Complete>> queryServiceBinder;
	private final OptionalBinder<UpdateService<Complete, Partial>> updateServiceBinder;

	public ModelBindingBuilder(
		Binder binder,
		TypeLiteral<Complete> completeType,
		TypeLiteral<Partial> partialType
	) {

		// specifies a CreateService<Complete, Partial>
		TypeLiteral<CreateService<Complete, Partial>> createServiceType = Typing.getUnchecked(
			Types.newParameterizedType(
				CreateService.class,
				completeType.getType(),
				partialType.getType()
			)
		);

		// specifies a DeleteService<Complete>
		TypeLiteral<DeleteService<Complete>> deleteServiceType = Typing.getUnchecked(
			Types.newParameterizedType(
				DeleteService.class,
				completeType.getType()
			)
		);

		// specifies a FindService<Complete>
		TypeLiteral<FindService<Complete>> findServiceType = Typing.getUnchecked(
			Types.newParameterizedType(
				FindService.class,
				completeType.getType()
			)
		);

		// specifies a PaginateService<Complete>
		TypeLiteral<PaginateService<Complete>> paginateServiceType = Typing.getUnchecked(
			Types.newParameterizedType(
				PaginateService.class,
				completeType.getType()
			)
		);

		// specifies a QueryService<Complete>
		TypeLiteral<QueryService<Complete>> queryServiceType = Typing.getUnchecked(
			Types.newParameterizedType(
				QueryService.class,
				completeType.getType()
			)
		);

		// specifies an UpdateService<Complete, Partial>
		TypeLiteral<UpdateService<Complete, Partial>> updateServiceType = Typing.getUnchecked(
			Types.newParameterizedType(
				UpdateService.class,
				completeType.getType(),
				partialType.getType()
			)
		);

		// creating the optional binders
		this.createServiceBinder = OptionalBinder.newOptionalBinder(binder, createServiceType);
		this.deleteServiceBinder = OptionalBinder.newOptionalBinder(binder, deleteServiceType);
		this.findServiceBinder = OptionalBinder.newOptionalBinder(binder, findServiceType);
		this.paginateServiceBinder = OptionalBinder.newOptionalBinder(binder, paginateServiceType);
		this.queryServiceBinder = OptionalBinder.newOptionalBinder(binder, queryServiceType);
		this.updateServiceBinder = OptionalBinder.newOptionalBinder(binder, updateServiceType);

		// meta type, ModelMeta<Complete, Partial>
		TypeLiteral<ModelMeta<Complete, Partial>> meta = Typing.getUnchecked(
			Types.newParameterizedType(
				ModelMeta.class,
				completeType.getType(),
				partialType.getType()
			)
		);

		// binding the model meta
		Multibinder.newSetBinder(binder, ModelMeta.class)
			.addBinding()
			.to(meta);

		binder.bind(meta).in(Scopes.SINGLETON);
		binder.bind(
			new ResolvableType<ModelMeta<Complete, ?>>() {}
				.with(new TypeArgument<Complete>(completeType) {})
		).to(meta);

		binder.bind(new ResolvableType<ModelMeta<?, Partial>>() {}
			.with(new TypeArgument<Partial>(partialType) {})
		).to(meta);

		// exposing the services
		if (binder instanceof ProtectedBinder) {
			ProtectedBinder protectedBinder = (ProtectedBinder) binder;
			protectedBinder.expose(deleteServiceType);
			protectedBinder.expose(findServiceType);
			protectedBinder.expose(paginateServiceType);
			protectedBinder.expose(queryServiceType);
			protectedBinder.expose(createServiceType);
			protectedBinder.expose(updateServiceType);
		}
	}

	public LinkedBindingBuilder<CreateService<Complete, Partial>> bindCreateService() {
		return createServiceBinder.setBinding();
	}

	public LinkedBindingBuilder<DeleteService<Complete>> bindDeleteService() {
		return deleteServiceBinder.setBinding();
	}

	public LinkedBindingBuilder<FindService<Complete>> bindFindService() {
		return findServiceBinder.setBinding();
	}

	public LinkedBindingBuilder<PaginateService<Complete>> bindPaginateService() {
		return paginateServiceBinder.setBinding();
	}

	public LinkedBindingBuilder<QueryService<Complete>> bindQueryService() {
		return queryServiceBinder.setBinding();
	}

	public LinkedBindingBuilder<UpdateService<Complete, Partial>> bindUpdateService() {
		return updateServiceBinder.setBinding();
	}

	public <T extends CreateService<Complete, Partial> &
		DeleteService<Complete> &
		FindService<Complete> &
		PaginateService<Complete> &
		QueryService<Complete> &
		UpdateService<Complete, Partial>> void toSingleService(TypeLiteral<T> typeLiteral) {
		toSingleService(typeLiteral, Scopes.SINGLETON);
	}

	public <T extends CreateService<Complete, Partial> &
		DeleteService<Complete> &
		FindService<Complete> &
		PaginateService<Complete> &
		QueryService<Complete> &
		UpdateService<Complete, Partial>> void toSingleService(TypeLiteral<T> typeLiteral, Scope scope) {

		Scope finalScope = scope == null ? Scopes.SINGLETON : scope;

		bindCreateService().to(typeLiteral).in(finalScope);
		bindDeleteService().to(typeLiteral).in(finalScope);
		bindFindService().to(typeLiteral).in(finalScope);
		bindPaginateService().to(typeLiteral).in(finalScope);
		bindQueryService().to(typeLiteral).in(finalScope);
		bindUpdateService().to(typeLiteral).in(finalScope);
	}

}