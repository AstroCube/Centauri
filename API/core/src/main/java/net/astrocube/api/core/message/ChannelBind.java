package net.astrocube.api.core.message;

import com.google.common.reflect.TypeToken;
import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import net.astrocube.api.core.utils.Methods;
import net.astrocube.api.core.utils.Types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@SuppressWarnings("All")
public class ChannelBind<T extends Message> {

    private final Multibinder<ChannelMeta> channels;
    private final Multibinder<MessageHandler> handlers;
    private final ChannelMeta<T> channelMeta;

    public ChannelBind(Binder binder, Class<T> type, String name) {
        this.channels = Multibinder.newSetBinder(binder, ChannelMeta.class);
        this.handlers = Multibinder.newSetBinder(binder, MessageHandler.class);
        this.channelMeta = new ChannelMeta<>(type, name);
        this.channels.addBinding().toInstance(channelMeta);
    }

    public void registerListener(MessageListener listener) {
        final TypeToken<? extends MessageListener> listenerType = TypeToken.of(listener.getClass());
        Methods.declaredMethodsInAncestors(listener.getClass()).forEach(method -> {
            final MessageListener.HandleMessage handleMessage = method.getAnnotation(MessageListener.HandleMessage.class);
            if (handleMessage != null) {
                final TypeToken<? extends Message> messageType = getMessageType(listenerType, method);
                if (messageType.getRawType() == this.channelMeta.type()) {
                    MessageHandler<T> messageHandler = new MessageHandler<T>() {
                        @Override
                        public Class<T> type() {
                            return channelMeta.type();
                        }

                        @Override
                        public void handleDelivery(T message, Metadata properties) {
                            try {
                                final Class<?>[] paramTypes = method.getParameterTypes();
                                Object[] params = new Object[paramTypes.length];
                                for (int i = 0; i < paramTypes.length; i++) {
                                    if(paramTypes[i].isAssignableFrom(message.getClass())) {
                                        params[i] = message;
                                    } else if(paramTypes[i].isAssignableFrom(Metadata.class)) {
                                        params[i] = properties;
                                    }
                                }
                                method.invoke(listener, params);
                            } catch (IllegalAccessException exception) {
                                throw new IllegalStateException(exception);
                            } catch (InvocationTargetException exception) {
                                if (exception.getCause() instanceof RuntimeException) {
                                    throw (RuntimeException) exception.getCause();
                                } else {
                                    throw new IllegalStateException(exception);
                                }
                            }
                        }
                        @Override
                        public String toString() {
                            return listener + "." + method.getName();
                        }
                    };
                    this.handlers.addBinding().toInstance(messageHandler);
                }
            }
        });
    }

    public void registerHandler(MessageHandler<T> handler) {
        this.handlers.addBinding().toInstance(handler);
    }

    private TypeToken<? extends Message> getMessageType(TypeToken decl, Method method) {
        if (method.getParameterTypes().length < 1 || method.getParameterTypes().length > 2) {
            throw new IllegalStateException("Message handler method must take 1 to 2 parameters");
        }

        final TypeToken<Message> base = new TypeToken<Message>(){};

        for (Type param : method.getGenericParameterTypes()) {
            final TypeToken paramToken = decl.resolveType(param);
            Types.assertFullySpecified(paramToken);
            if(base.getClass().isAssignableFrom(paramToken.getRawType())) {
                return paramToken;
            }
        }

        throw new IllegalStateException("Message handler has no message parameter");
    }
}