package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.goal.Goal;
import net.astrocube.api.core.virtual.goal.GoalDoc;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.CoreModelService;

public class GoalModelModule extends ProtectedModule implements ModelBinderModule {

    @Override
    protected void configure() {
        bindModel(Goal.class, GoalDoc.Partial.class, model -> {
            TypeLiteral<CoreModelService<Goal, GoalDoc.Partial>> serviceTypeLiteral =
                    new ResolvableType<CoreModelService<Goal, GoalDoc.Partial>>() {};
            model.bind(serviceTypeLiteral);
        });
    }
}