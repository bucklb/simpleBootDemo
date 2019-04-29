package me.bucklb.simpleBootdemo.Controller;

import me.bucklb.simpleBootdemo.model.MixIn_A;
import me.bucklb.simpleBootdemo.model.MixIn_B;
import me.bucklb.simpleBootdemo.service.MixInHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RequestMapping("mixin/")
@RestController
public class MixInController {

    @Autowired
    MixInHelper mixInHelper;

    @Autowired
    HttpServletRequest httpServletRequest;

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    // TODO : Need to allow fields to be passed at some point.  Headers or parameters (or even request body)?
    private static String[] fields = {"id","name","size", "bclass"};
    private String mixinField;


    // TODO : allow a list of fields to be passed in, somehow, to check filtering
    //

    /*
        Original demo end point.  Hard wired as INCLUDE.  No point getting fancier if now allowing fields to be passed in
     */
    @GetMapping("")
    public String doMixIn() {
        return getMixIn(fields, true);
    }

    /*
        Allow caller to pass in a list of fields to an include/exclude endpoint
     */
    @GetMapping("include")
    public String doMixInInclude(@RequestParam(value="fields") String[] mixinFields) {
        return getMixIn( mixinFields,true);
    }

    @GetMapping("exclude")
    public String doMixInExclude(@RequestParam(value="fields") String[] mixinFields) {
        return getMixIn( mixinFields,false);
    }

    /*
        The various controller end points will all need much the same call handling

        Note : always applying the "filter" to every object level.
            could apply the filter to just one specific class and with some rework use a set of class & fields (need extra mixIn class(es))
     */
    private String getMixIn(String[] fieldList, boolean include){

        String ansa = null;
        String mixin = null;

        try {
            // Various options for MixIn. 1=no attempt to pass, 2=pass null (to get default), 3=pass explicitly
            int mode = 3;
            if(mode>1) {
                if(mode>2){
                    mixin = mixInHelper.applyMixIn(getTestAClass(), fieldList,
                            Object.class, MixInHelper.DynamicMixIn.class,
                            include, true);
                } else {
                    mixin = mixInHelper.applyMixIn(getTestAClass(), fieldList,
                            Object.class, null,
                            include, true);
                }
            } else {
                // Use call that needs NO mixin class
                mixin = mixInHelper.applyMixIn(getTestAClass(), fieldList,
                        Object.class, include, true);
            }

        } catch (Exception e) {
            // rethrow to pick up any interactionId
            System.out.println("mixInHelper threw exception. See log" );
            logger.info("mixInHelper threw exception",e);
            mixin = "exception thrown!";
        }

        ansa = include ? "Including " : "Excluding ";
        ansa = ansa + Arrays.toString(fieldList) + " : ";

        return ansa + mixin;

    }


    private MixIn_A getTestAClass() {
        MixIn_B[] bclass = {
                new MixIn_B("ego","big","small"),
                new MixIn_B("super-ego","little","tall")};
        String[] color = {"black", "blue"};

        MixIn_A aClass = new MixIn_A("69","naughty", color,69, bclass);

        return aClass;
    }


}
