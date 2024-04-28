package org.easydto.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.easydto.proxy.Dto;
import org.easydto.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class DtoSerializer extends StdSerializer<Dto> implements Serializer {


    public DtoSerializer(){
        this(null);
    }

    protected DtoSerializer(Class<Dto> t) {
        super(t);
    }

    @Override
    public void serialize(Dto value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        serializers.findTypedValueSerializer(Map.class, true, null)
                .serialize(value.getValues(), gen, serializers);
    }
}
