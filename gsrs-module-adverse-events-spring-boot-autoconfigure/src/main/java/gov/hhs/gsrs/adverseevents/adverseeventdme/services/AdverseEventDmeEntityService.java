package gov.hhs.gsrs.adverseevents.adverseeventdme.services;

import gov.hhs.gsrs.adverseevents.adverseeventdme.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventdme.respository.*;

import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
// import gsrs.module.substance.events.SubstanceCreatedEvent;
// import gsrs.module.substance.events.SubstanceUpdatedEvent;
// import gsrs.module.substance.repository.SubstanceRepository;
import gsrs.module.substance.SubstanceEntityService;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidationResponse;
import ix.core.validator.ValidationResponseBuilder;
import ix.core.validator.ValidatorCallback;
// import ix.ginas.models.v1.Substance;
// import ix.ginas.utils.GinasProcessingStrategy;
// import ix.ginas.utils.JsonSubstanceFactory;

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
public class AdverseEventDmeEntityService extends AbstractGsrsEntityService<AdverseEventDme, Long> {
    public static final String  CONTEXT = "adverseeventdme";

    public AdverseEventDmeEntityService() {
        super(CONTEXT,  IdHelpers.NUMBER, null, null, null);
    }

    @Autowired
    private AdverseEventDmeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<AdverseEventDme> getEntityClass() {
        return AdverseEventDme.class;
    }

    @Override
    public Long parseIdFromString(String idAsString) {
        return Long.parseLong(idAsString);
    }

    @Override
    protected AdverseEventDme fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, AdverseEventDme.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected AdverseEventDme create(AdverseEventDme adverse) {
        try {
            return repository.saveAndFlush(adverse);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected AdverseEventDme update(AdverseEventDme adverse) {
        return repository.saveAndFlush(adverse);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<AdverseEventDme> newUpdateEvent(AdverseEventDme updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<AdverseEventDme> newCreationEvent(AdverseEventDme createdEntity) {
        return null;
    }

    @Override
    public Long getIdFrom(AdverseEventDme entity) {
        return entity.id;
    }

    @Override
    protected List<AdverseEventDme> fromNewJsonList(JsonNode list) throws IOException {
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
    protected AdverseEventDme fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, AdverseEventDme.class);
    }

    @Override
    protected List<AdverseEventDme> fromUpdatedJsonList(JsonNode list) throws IOException {
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
    protected JsonNode toJson(AdverseEventDme product) throws IOException {
        return objectMapper.valueToTree(product);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<AdverseEventDme> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<AdverseEventDme> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(Long.parseLong(someKindOfId));
    }

    @Override
    protected Optional<Long> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<AdverseEventDme> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().id);
        }
        return Optional.empty();
    }

}
