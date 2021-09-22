package gov.hhs.gsrs.adverseevents.adverseeventpt.services;

import gov.hhs.gsrs.adverseevents.adverseeventpt.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventpt.repository.AdverseEventPtRepository;

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
public class AdverseEventPtEntityService extends AbstractGsrsEntityService<AdverseEventPt, Long> {
    public static final String  CONTEXT = "adverseeventpt";

    public AdverseEventPtEntityService() {
        super(CONTEXT, IdHelpers.NUMBER, null, null, null);
    }

    @Autowired
    private AdverseEventPtRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<AdverseEventPt> getEntityClass() {
        return AdverseEventPt.class;
    }

    @Override
    public Long parseIdFromString(String idAsString) {
        return Long.parseLong(idAsString);
    }

    @Override
    protected AdverseEventPt fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, AdverseEventPt.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected AdverseEventPt create(AdverseEventPt adverse) {
        try {
            return repository.saveAndFlush(adverse);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected AdverseEventPt update(AdverseEventPt adverse) {
        return repository.saveAndFlush(adverse);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<AdverseEventPt> newUpdateEvent(AdverseEventPt updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<AdverseEventPt> newCreationEvent(AdverseEventPt createdEntity) {
        return null;
    }

    @Override
    public Long getIdFrom(AdverseEventPt entity) {
        return entity.id;
    }

    @Override
    protected List<AdverseEventPt> fromNewJsonList(JsonNode list) throws IOException {
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
    protected AdverseEventPt fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, AdverseEventPt.class);
    }

    @Override
    protected List<AdverseEventPt> fromUpdatedJsonList(JsonNode list) throws IOException {
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
    protected JsonNode toJson(AdverseEventPt adverse) throws IOException {
        return objectMapper.valueToTree(adverse);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<AdverseEventPt> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<AdverseEventPt> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(Long.parseLong(someKindOfId));
    }

    @Override
    protected Optional<Long> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<AdverseEventPt> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().id);
        }
        return Optional.empty();
    }

    public AdverseEventFAERSDashboard findFaersDashboardRecordByName(String name) {
        AdverseEventFAERSDashboard adv = repository.findFaersDashboardRecordByName(name);
        return adv;
    }
}
