package org.easydto.conversion.converter.impl;

import org.easydto.conversion.converter.DtoConverter;
import org.easydto.domain.ConversionContext;
import org.easydto.domain.PropertyConfiguration;
import org.easydto.domain.ReadProperty;
import org.easydto.enums.PropertyType;
import org.easydto.exception.DtoConversionException;
import org.easydto.proxy.Dto;
import org.easydto.proxy.DtoFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Domain to DTO
public class DefaultDtoConverter implements DtoConverter {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Dto<T> convert(ConversionContext<T> cc) throws DtoConversionException {
        T obj = cc.getDomainObject();

        if (obj == null) return null;

        String profile = cc.getProfile();
        DtoFactory dtoFactory = DtoFactory.INSTANCE;
        Dto<T> dto = (Dto<T>) dtoFactory.createDtoFor(obj.getClass(), profile);

        while (cc.nextProperty()) {
            PropertyConfiguration pc = cc.getCurrentPropertyConfiguration();
            Object readValue = ((ReadProperty) pc.property).read(obj);

            if (pc.property.propertyType() == PropertyType.COMPLEX) {
                ConversionContext<?> childContext = cc.createChildContext(readValue);
                dto.putProperty(pc.targetName, convert(childContext));
            } else if (pc.property.propertyType() == PropertyType.LIST) {
                List<Object> list = (List<Object>) readValue;
                dto.putProperty(pc.targetName, list);
            } else if (pc.property.propertyType() == PropertyType.MAP) {
                Map<Object, Object> map = (Map<Object, Object>) readValue;
                dto.putProperty(pc.targetName, map);
            } else {
                dto.putProperty(pc.targetName, readValue);
            }
        }

        return dto;
    }

}
