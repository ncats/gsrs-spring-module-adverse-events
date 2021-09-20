package gov.hhs.gsrs.adverseevents.adverseeventdme.processors;

import gov.hhs.gsrs.adverseevents.adverseeventdme.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventdme.controllers.*;

import gsrs.springUtils.AutowireHelper;
import ix.core.EntityProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class AdverseEventDmeProcessor implements EntityProcessor<AdverseEventDme> {

    @Override
    public Class<AdverseEventDme> getEntityClass() {
        return AdverseEventDme.class;
    }

    @Override
    public void prePersist(final AdverseEventDme obj) {
    }

    @Override
    public void preUpdate(AdverseEventDme obj) {
    }

    @Override
    public void preRemove(AdverseEventDme obj) {
    }

    @Override
    public void postLoad(AdverseEventDme obj) throws FailProcessingException {
    }

}

