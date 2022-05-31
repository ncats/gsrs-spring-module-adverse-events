package gov.hhs.gsrs.adverseevents.adverseeventpt.indexers;

import gov.hhs.gsrs.adverseevents.AdverseEventDataSourceConfig;
import gov.hhs.gsrs.adverseevents.adverseeventpt.models.AdverseEventPt;

import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;

import javax.persistence.*;
import java.util.List;
import java.util.function.Consumer;

public class AdverseEventIndexValueMaker implements IndexValueMaker<AdverseEventPt> {

    @PersistenceContext(unitName = AdverseEventDataSourceConfig.NAME_ENTITY_MANAGER)
    public EntityManager entityManager;

    @Override
    public Class<AdverseEventPt> getIndexedEntityClass() {
        return AdverseEventPt.class;
    }

    private static final long[] caseCountBuckets = new long[]{1000,10000,50000,100000,200000,300000,400000,500000,600000,700000};
    private static final double[] prrCountBuckets = new double[]{-1.0,0.0,0.5,1.0,5.0,10.0,50.0,100.0,500.00,1000.00,10000.00,50000.00};

    @Override
    public void createIndexableValues(AdverseEventPt adverseEvent, Consumer<IndexableValue> consumer) {
        try {
            if (adverseEvent.id != null) {
                if (adverseEvent.caseCount > 0) {
                    consumer.accept(IndexableValue.simpleFacetLongValue("Case Count", adverseEvent.caseCount, caseCountBuckets));
                }
                if (adverseEvent.prr > -2) {
                    consumer.accept(IndexableValue.simpleFacetDoubleValue("PRR", adverseEvent.prr, prrCountBuckets));
                }
                if (adverseEvent.name != null) {
                    consumer.accept(IndexableValue.simpleFacetStringValue("Ingredient Name (Case Count)", adverseEvent.name + " (" + adverseEvent.caseCount + ")"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
