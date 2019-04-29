package me.bucklb.simpleBootdemo.service;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.stereotype.Service;

/*
    Played with MixIn in test only.  Maybe look at more realistic implementation
 */
@Service
public class MixInHelper {

    /*
        Hook to hang the filtering off.  Make it PUBLIC so outside world can refer to it when calling.
        Can have multiple mixins for more complex filters, but no obvious need to as yet
      */
    @JsonFilter("DynamicMixIn")
    public class DynamicMixIn {    }

    /*
        Pass in the class so can illustrate the effect.  COULD always use DynamicMixIn regardless, so no need to pass in class
        NOTE : tests for de-serialisation REALLY need noNull to be true
     */
    public String applyMixIn(Object objToFilter, String[] fields ,
                              Class FilteredClass, Class MixInClass,
                              boolean inc, boolean noNull) throws Exception {

        // Need a filterProvider and a mapper
        ObjectMapper mapper = new ObjectMapper();

        // Deal with null MixInClass?  If nothing better then default to DynamicMixIn
        if( MixInClass == null) { MixInClass = DynamicMixIn.class; }

        if (noNull) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        } else {
            mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        }

        // Add a filter and a MixIn, but only if we get given one
        if ( FilteredClass != null && MixInClass!=null ) {
            mapper.addMixIn(FilteredClass, MixInClass);

            // Add a provider
            FilterProvider filterProvider = new SimpleFilterProvider();
            ((SimpleFilterProvider) filterProvider).addFilter(MixInClass.getSimpleName(),
                    inc ? SimpleBeanPropertyFilter.filterOutAllExcept(fields)
                            : SimpleBeanPropertyFilter.serializeAllExcept(fields));

            mapper.setFilterProvider(filterProvider);
        }

        // Do the mapping
        String jSon=mapper.writeValueAsString( objToFilter );
        System.out.println(jSon);
        return jSon;
    }

    /*
        Demonstrate NOT needing to pass in the mixIn class
     */
    public String applyMixIn(Object objToFilter, String[] fields ,
                             Class FilteredClass,
                             boolean inc, boolean noNull) throws Exception {

        return applyMixIn(objToFilter, fields, FilteredClass, DynamicMixIn.class, inc, noNull);
    }

}
