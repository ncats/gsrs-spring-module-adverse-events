package gov.hhs.gsrs.adverseevents.adverseeventcvm.services;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.repository.*;

import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.module.substance.SubstanceEntityService;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidationResponse;
import ix.core.validator.ValidationResponseBuilder;
import ix.core.validator.ValidatorCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ix.utils.Util;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AdverseEventCvmEntityService extends AbstractGsrsEntityService<AdverseEventCvm, String> {
    public static final String  CONTEXT = "adverseeventcvm";

    public AdverseEventCvmEntityService() {
        super(CONTEXT,  IdHelpers.STRING_NO_WHITESPACE, null, null, null);
    }

    @Autowired
    private AdverseEventCvmRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<AdverseEventCvm> getEntityClass() {
        return AdverseEventCvm.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected AdverseEventCvm fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, AdverseEventCvm.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected AdverseEventCvm create(AdverseEventCvm adverse) {
        try {
            return repository.saveAndFlush(adverse);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected AdverseEventCvm update(AdverseEventCvm adverse) {
        return repository.saveAndFlush(adverse);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<AdverseEventCvm> newUpdateEvent(AdverseEventCvm updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<AdverseEventCvm> newCreationEvent(AdverseEventCvm createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(AdverseEventCvm entity) {
        return entity.id;
    }

    @Override
    protected List<AdverseEventCvm> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }

    /*
    @Override
    protected Application fixUpdatedIfNeeded(Application oldEntity, Application updatedEntity) {
        //force the "owner" on all the updated fields to point to the old version so the uuids are correct
        return updatedEntity;
    }
    */

    @Override
    protected AdverseEventCvm fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, AdverseEventCvm.class);
    }

    @Override
    protected List<AdverseEventCvm> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
        /*
        List<Application> substances = new ArrayList<>(list.size());
        for(JsonNode n : list){
            substances.add(fromUpdatedJson(n));
        }
        return substances;
         */
    }

    @Override
    protected JsonNode toJson(AdverseEventCvm adverse) throws IOException {
        return objectMapper.valueToTree(adverse);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<AdverseEventCvm> get(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<AdverseEventCvm> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<AdverseEventCvm> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().id);
        }
        return Optional.empty();
    }

}
