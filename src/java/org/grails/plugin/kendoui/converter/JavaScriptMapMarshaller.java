package org.grails.plugin.kendoui.converter;

import grails.converters.JSON;

import java.lang.reflect.Method;
import java.util.Map;

import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException;
import org.codehaus.groovy.grails.web.converters.marshaller.ObjectMarshaller;
import org.codehaus.groovy.grails.web.json.JSONWriter;

@SuppressWarnings("unchecked")
public class JavaScriptMapMarshaller implements ObjectMarshaller<JSON> {

	protected void forceWriterAppend(JSONWriter writer, String value) throws ConverterException {
		Method method = null;
		try {
			method = writer.getClass().getDeclaredMethod("append", String.class);
			method.setAccessible(true);
			method.invoke(writer, value);
		} catch (NoSuchMethodException e) {
			// ignore
		} catch (SecurityException e) {
			// ignore
		} catch (ReflectiveOperationException e) {
			// ignore
		} catch (IllegalArgumentException e) {
			// ignore
		}
	}

    public boolean supports(Object object) {
        return object instanceof Map;
    }

    public void marshalObject(Object o, JSON converter) throws ConverterException {
        JSONWriter writer = converter.getWriter();
        writer.object();
        Map<Object,Object> map = (Map<Object,Object>) o;
        for (Map.Entry<Object,Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (key != null) {
                writer.key(key.toString());

                Object val = entry.getValue();
                if (val != null && val instanceof CharSequence){
                	String str = ((CharSequence)val).toString();
                	if (str.startsWith("js:"))
                		forceWriterAppend(converter.getWriter(), str.substring(3));
                	else
                		converter.convertAnother(val);
                }
                else
                	converter.convertAnother(val);
            }
        }
        writer.endObject();
    }
}
