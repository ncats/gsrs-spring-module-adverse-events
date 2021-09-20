package gov.hhs.gsrs.adverseevents.adverseeventpt;

import gov.hhs.gsrs.adverseevents.adverseeventpt.controllers.AdverseEventPtController;
import gov.hhs.gsrs.adverseevents.adverseeventpt.searcher.LegacyAdverseEventPtSearcher;
import gov.hhs.gsrs.adverseevents.adverseeventpt.services.AdverseEventPtEntityService;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class AdverseEventPtSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                AdverseEventPtEntityService.class.getName(),
                LegacyAdverseEventPtSearcher.class.getName(),
                AdverseEventPtController.class.getName()
        };
    }
}
