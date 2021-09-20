package gov.hhs.gsrs.adverseevents.adverseeventcvm.processors;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.controllers.*;

import gsrs.springUtils.AutowireHelper;
import ix.core.EntityProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class AdverseEventCvmProcessor implements EntityProcessor<AdverseEventCvm> {

    @Override
    public Class<AdverseEventCvm> getEntityClass() {
        return AdverseEventCvm.class;
    }

    @Override
    public void prePersist(final AdverseEventCvm obj) {
    }

    @Override
    public void preUpdate(AdverseEventCvm obj) {
    }

    @Override
    public void preRemove(AdverseEventCvm obj) {
    }

    @Override
    public void postLoad(AdverseEventCvm obj) throws FailProcessingException {
    }

}

