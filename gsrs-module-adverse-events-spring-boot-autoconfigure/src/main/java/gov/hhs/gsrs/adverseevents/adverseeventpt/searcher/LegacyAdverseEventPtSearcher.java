package gov.hhs.gsrs.adverseevents.adverseeventpt.searcher;

import gov.hhs.gsrs.adverseevents.adverseeventpt.models.AdverseEventPt;
import gov.hhs.gsrs.adverseevents.adverseeventpt.repository.AdverseEventPtRepository;

import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.GsrsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyAdverseEventPtSearcher extends LegacyGsrsSearchService<AdverseEventPt> {

    @Autowired
    public LegacyAdverseEventPtSearcher(AdverseEventPtRepository repository) {
        super(AdverseEventPt.class, repository);
    }
}
