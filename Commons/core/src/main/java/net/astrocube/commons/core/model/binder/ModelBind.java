package net.astrocube.commons.core.model.binder;

import com.google.inject.*;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.OptionalBinder;
import me.fixeddev.inject.ProtectedModule;
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

@SuppressWarnings("all")
public class ModelBind<Complete extends Model, Partial extends PartialModel> {

    private final TypeLiteral<Complete> completeTypeLiteral;
    private final TypeLiteral<Partial> partialTypeLiteral;
    private final Multibinder metas;

    private final OptionalBinder<CreateService<Complete, Partial>> createServiceBinder;
    private final OptionalBinder<DeleteService<Complete>> deleteServiceBinder;
    private final OptionalBinder<FindService<Complete>> findServiceBinder;
    private final OptionalBinder<PaginateService<Complete>> paginateServiceBinder;
    private final OptionalBinder<QueryService<Complete>> queryServiceBinder;
    private final OptionalBinder<UpdateService<Complete, Partial>> updateServiceBinder;

    public static <M extends Model & PartialModel> ModelBind<M, M> of(Binder binder, Class<M> M) {
        return of(binder, M, M);
    }

    public static <M extends Model & PartialModel> ModelBind<M, M> of(Binder binder, TypeLiteral<M> M) {
        return of(binder, M, M);
    }

    public static <M extends Model, P extends PartialModel> ModelBind<M, P> of(Binder binder, Class<M> M, Class<P> P) {
        return of(binder, TypeLiteral.get(M), TypeLiteral.get(P));
    }

    public static <M extends Model, P extends PartialModel> ModelBind<M, P> of(Binder binder, TypeLiteral<M> M, TypeLiteral<P> P) {
        return new ModelBind<>(binder, M, P);
    }

    private ModelBind(Binder binder, TypeLiteral<Complete> completeTypeLiteral, TypeLiteral<Partial> partialTypeLiteral) {
        this.completeTypeLiteral = completeTypeLiteral;
        this.partialTypeLiteral = partialTypeLiteral;

        this.metas = Multibinder.newSetBinder(binder, ModelMeta.class);
        this.createServiceBinder = OptionalBinder.newOptionalBinder(binder, new ResolvableType<CreateService<Complete, Partial>>(){}.with(new TypeArgument<Complete>(this.completeTypeLiteral) {}, new TypeArgument<Partial>(this.partialTypeLiteral) {}));
        this.deleteServiceBinder = OptionalBinder.newOptionalBinder(binder, new ResolvableType<DeleteService<Complete>>(){}.with(new TypeArgument<Complete>(this.completeTypeLiteral){}));
        this.findServiceBinder = OptionalBinder.newOptionalBinder(binder, new ResolvableType<FindService<Complete>>(){}.with(new TypeArgument<Complete>(this.completeTypeLiteral){}));
        this.paginateServiceBinder = OptionalBinder.newOptionalBinder(binder, new ResolvableType<PaginateService<Complete>>(){}.with(new TypeArgument<Complete>(this.completeTypeLiteral){}));
        this.queryServiceBinder = OptionalBinder.newOptionalBinder(binder, new ResolvableType<QueryService<Complete>>(){}.with(new TypeArgument<Complete>(this.completeTypeLiteral){}));
        this.updateServiceBinder = OptionalBinder.newOptionalBinder(binder, new ResolvableType<UpdateService<Complete, Partial>>(){}.with(
                new TypeArgument<Complete>(this.completeTypeLiteral){}, new TypeArgument<Partial>(this.partialTypeLiteral){}
        ));

        binder.install(new PerModel());
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
            UpdateService<Complete, Partial>> void bind(TypeLiteral<T> typeLiteral) {
        bind(typeLiteral, Scopes.SINGLETON);
    }

    public <T extends CreateService<Complete, Partial> &
            DeleteService<Complete> &
            FindService<Complete> &
            PaginateService<Complete> &
            QueryService<Complete> &
            UpdateService<Complete, Partial>> void bind(TypeLiteral<T> typeLiteral, Scope scope) {

        Scope finalScope = scope == null ? Scopes.NO_SCOPE : scope;

        bindCreateService().to(typeLiteral).in(finalScope);
        bindDeleteService().to(typeLiteral).in(finalScope);
        bindFindService().to(typeLiteral).in(finalScope);
        bindPaginateService().to(typeLiteral).in(finalScope);
        bindQueryService().to(typeLiteral).in(finalScope);
        bindUpdateService().to(typeLiteral).in(finalScope);
    }

    public TypeLiteral<Complete> getCompleteTypeLiteral() {
        return completeTypeLiteral;
    }

    public TypeLiteral<Partial> getPartialTypeLiteral() {
        return partialTypeLiteral;
    }

    private class PerModel extends ProtectedModule {
        @Override
        protected void configure() {
            final TypeLiteral<ModelMeta<Complete, Partial>> meta = new ResolvableType<ModelMeta<Complete, Partial>>(){}.with(
                    new TypeArgument<Complete>(ModelBind.this.completeTypeLiteral) {},
                    new TypeArgument<Partial>(ModelBind.this.partialTypeLiteral) {}
            );
            metas.addBinding().to(meta);
            bind(meta).in(Scopes.SINGLETON);
            bind(new ResolvableType<ModelMeta<Complete, ?>>(){}.with(new TypeArgument<Complete>(ModelBind.this.completeTypeLiteral){})).to(meta);
            bind(new ResolvableType<ModelMeta<?, Partial>>(){}.with(new TypeArgument<Partial>(ModelBind.this.partialTypeLiteral){})).to(meta);
            expose(new ResolvableType<DeleteService<Complete>>(){}.with(new TypeArgument<Complete>(ModelBind.this.completeTypeLiteral){}));
        }
    }
}