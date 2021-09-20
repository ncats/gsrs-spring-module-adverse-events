package gov.hhs.gsrs.adverseevents.adverseeventdme;

import gov.hhs.gsrs.adverseevents.adverseeventdme.controllers.AdverseEventDmeController;
import gov.hhs.gsrs.adverseevents.adverseeventdme.searcher.LegacyAdverseEventDmeSearcher;
import gov.hhs.gsrs.adverseevents.adverseeventdme.services.AdverseEventDmeEntityService;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class AdverseEventDmeSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                AdverseEventDmeEntityService.class.getName(),
                LegacyAdverseEventDmeSearcher.class.getName(),
                AdverseEventDmeController.class.getName()
        };
    }
}
