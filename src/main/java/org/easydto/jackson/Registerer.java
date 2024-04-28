package org.easydto.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.easydto.proxy.Dto;
import org.easydto.proxy.ValueMapDto;

public class Registerer {

    public static void registerModules(ObjectMapper mapper){
        SimpleModule module = new SimpleModule()
                .addDeserializer(Dto.class, new DtoDeserializer())
                .addSerializer(ValueMapDto.class, new DtoSerializer());
        mapper.registerModule(module);
    }

}
