package gov.hhs.gsrs.adverseevents.indexers;

import gov.hhs.gsrs.adverseevents.AdverseEventDataSourceConfig;
import gov.hhs.gsrs.adverseevents.SubstanceAdverseEvent;

import gsrs.DefaultDataSourceConfig;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import ix.core.util.EntityUtils;
import ix.ginas.models.v1.Substance;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import gsrs.springUtils.AutowireHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AEATCIndexValueMaker implements IndexValueMaker<SubstanceAdverseEvent> {

    @PersistenceContext(unitName =  DefaultDataSourceConfig.NAME_ENTITY_MANAGER)
    public EntityManager entityManager;

    @Override
    public Class<SubstanceAdverseEvent> getIndexedEntityClass() {
        return SubstanceAdverseEvent.class;
    }

    @Override
    public void createIndexableValues(SubstanceAdverseEvent ae, Consumer<IndexableValue> consumer) {

        if(entityManager==null) {
            AutowireHelper.getInstance().autowire(this);
        }

        try {
            if (ae.getSubstanceId() != null) {
                Substance s = (Substance) entityManager.find(Substance.class, UUID.fromString(ae.getSubstanceId()));

                /*
                EntityUtils.Key skey = EntityUtils.Key.of(Substance.class, ae.getSubstanceId());
                Optional<EntityUtils.EntityWrapper<?>> opt = skey.fetch(entityManager);
                if (opt.isPresent()) {
                    Substance s = (Substance) opt.get().getValue();
                    if (s != null) {
                        new ATCIndexValueMaker().createIndexableValues(s, consumer);
                    }
                }
                 */
                if (s != null) {
                    new ATCIndexValueMaker().createIndexableValues(s, consumer);
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
