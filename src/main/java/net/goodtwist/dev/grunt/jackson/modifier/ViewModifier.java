package net.goodtwist.dev.grunt.jackson.modifier;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.jaxrs.cfg.EndpointConfigBase;
import com.fasterxml.jackson.jaxrs.cfg.ObjectWriterModifier;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

public class ViewModifier extends ObjectWriterModifier {

    private final Class<?> view;

    public ViewModifier(Class<?> view) {
        this.view = view;
    }

    @Override
    public ObjectWriter modify(EndpointConfigBase<?> endpoint,
                               MultivaluedMap<String, Object> responseHeaders,
                               Object valueToWrite,
                               ObjectWriter objectWriter,
                               JsonGenerator g) throws IOException {
        return objectWriter.withView(view);
    }
}