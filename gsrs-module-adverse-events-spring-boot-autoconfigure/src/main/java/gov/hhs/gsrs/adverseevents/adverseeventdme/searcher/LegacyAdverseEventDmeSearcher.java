package gov.hhs.gsrs.adverseevents.adverseeventdme.searcher;

import gov.hhs.gsrs.adverseevents.adverseeventdme.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventdme.respository.*;

import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.GsrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyAdverseEventDmeSearcher extends LegacyGsrsSearchService<AdverseEventDme> {

    @Autowired
    public LegacyAdverseEventDmeSearcher(AdverseEventDmeRepository repository) {
        super(AdverseEventDme.class, repository);
    }
}
