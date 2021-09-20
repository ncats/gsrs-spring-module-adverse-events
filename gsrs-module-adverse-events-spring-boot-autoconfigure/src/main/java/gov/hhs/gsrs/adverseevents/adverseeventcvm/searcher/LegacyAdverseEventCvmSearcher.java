package gov.hhs.gsrs.adverseevents.adverseeventcvm.searcher;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.repository.*;

import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.GsrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyAdverseEventCvmSearcher extends LegacyGsrsSearchService<AdverseEventCvm> {

    @Autowired
    public LegacyAdverseEventCvmSearcher(AdverseEventCvmRepository repository) {
        super(AdverseEventCvm.class, repository);
    }
}
