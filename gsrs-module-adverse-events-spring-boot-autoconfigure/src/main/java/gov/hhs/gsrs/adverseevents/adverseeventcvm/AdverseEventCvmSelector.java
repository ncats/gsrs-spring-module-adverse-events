package gov.hhs.gsrs.adverseevents.adverseeventcvm;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.controllers.AdverseEventCvmController;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.searcher.LegacyAdverseEventCvmSearcher;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.services.AdverseEventCvmEntityService;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class AdverseEventCvmSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                AdverseEventCvmEntityService.class.getName(),
                LegacyAdverseEventCvmSearcher.class.getName(),
                AdverseEventCvmController.class.getName()
        };
    }
}
