package org.easydto.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.easydto.proxy.Dto;
import org.easydto.proxy.DtoFactory;
import org.easydto.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class DtoDeserializer extends StdDeserializer<Dto<?>> implements ContextualDeserializer, Deserializer {

    private final DtoFactory dtoFactory = DtoFactory.INSTANCE;
    private Class<?> dtoTargetClass;

    public DtoDeserializer() {
        this(Dto.class, null);
    }

    public DtoDeserializer(JavaType type) {
        this(Dto.class, type);
    }

    protected DtoDeserializer(Class vc, JavaType type) {
        super(vc);
        if(type != null && !type.getBindings().isEmpty()) {
            dtoTargetClass = type.getBindings().getBoundType(0).getRawClass();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Dto<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, Object> map = ctxt.readValue(p, Map.class);
        Dto<?> proxy = dtoFactory.createDtoFor(dtoTargetClass, null);
        map.forEach(proxy::putProperty);
        return proxy;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        JavaType type = ctxt.getContextualType() != null ? ctxt.getContextualType() : property.getMember().getType();
        return new DtoDeserializer(type);
    }
}
