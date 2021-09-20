package gov.hhs.gsrs.adverseevents.adverseeventpt.processors;

import gov.hhs.gsrs.adverseevents.adverseeventpt.models.AdverseEventPt;
import gov.hhs.gsrs.adverseevents.adverseeventpt.controllers.*;

import gsrs.springUtils.AutowireHelper;
import ix.core.EntityProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class AdverseEventPtProcessor implements EntityProcessor<AdverseEventPt> {

    @Override
    public Class<AdverseEventPt> getEntityClass() {
        return AdverseEventPt.class;
    }

    @Override
    public void prePersist(final AdverseEventPt obj) {
    }

    @Override
    public void preUpdate(AdverseEventPt obj) {
    }

    @Override
    public void preRemove(AdverseEventPt obj) {
    }

    @Override
    public void postLoad(AdverseEventPt obj) throws FailProcessingException {
    }

}

