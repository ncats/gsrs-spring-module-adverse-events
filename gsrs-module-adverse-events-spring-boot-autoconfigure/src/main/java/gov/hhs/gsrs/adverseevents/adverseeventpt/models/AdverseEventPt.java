package gov.hhs.gsrs.adverseevents.adverseeventpt.models;

import gov.hhs.gsrs.adverseevents.SubstanceAdverseEvent;

import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.IndexableRoot;
import ix.core.models.IxModel;
import ix.core.search.text.TextIndexerEntityListener;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import ix.core.util.EntityUtils;
import ix.core.utils.executor.ProcessListener;
import ix.ginas.models.v1.Substance;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.springUtils.AutowireHelper;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@IndexableRoot
@Backup
@Data
@Entity
@Table(name="SRSCID_ADVERSE_EVT_PT_ALL_MV")
public class AdverseEventPt extends AbstractGsrsEntity implements SubstanceAdverseEvent {

    @Id
    @Column(name = "AE_ID", length=500)
    public String id;

    @Column(name = "ID")
    public Long id2;

    @Column(name="SUBSTANCE_ID")
    public String substanceId;

    @Column(name="UNII")
    public String unii;

    @Indexable(facet = true, name = "Substance Key")
    @Column(name = "SUBSTANCE_KEY")
    public String substanceKey;

    @Indexable(suggest = true, facet = true, name = "Ingredient Name", sortable = true)
    @Column(name = "NAME", length=500)
    public String name;

    @Indexable(suggest = true, facet = true, name = "PT Term", sortable = true)
    @Column(name = "PT_TERM", length=500)
    public String ptTerm;

    @Indexable(suggest = true, facet = true, name = "Prim SOC", sortable = true)
    @Column(name = "PRIM_SOC")
    public String primSoc;

    @Indexable(sortable = true)
    @Column(name = "CASE_COUNT")
    public Integer caseCount;

    @Column(name = "SOC_COUNT")
    public Integer socCount;

    @Indexable(sortable = true)
    @Column(name = "PT_COUNT")
    public Integer ptCount;

    @Column(name = "SOC_COUNT_PERCENT")
    public Double socCountPercent;

    @Column(name = "PT_COUNT_PERCENT")
    public Double ptCountPercent;

    @Column(name = "PT_COUNT_TOTAL_VS_THIS_DRUG")
    public Integer ptCountTotalVsDrug;

    @Indexable(facet = true, name = "PRR", sortable = true)
    @Column(name = "PRR")
    public Double prr;

    public String getId() {
        return id;
    }

    @JsonIgnore
    @Indexable(facet = true, name = "Deprecated")
    public String getDeprecated() {
        return "Not Deprecated";
    }

    @Override
    public String getSubstanceId(){
        return this.substanceId;
    }

    @Override
    public boolean shouldIndexSubstance() {
        return true;
    }

    @Override
    public Substance getSubstance() {
      //  if(!shouldIndexSubstance())return null;
        try{
        //    EntityUtils.Key skey= EntityUtils.Key.of(Substance.class, getSubstanceId());
     //       Substance s = (Substance) entityManager.find(Substance.class, getSubstanceId());
          //  Optional<EntityUtils.EntityWrapper<?>> opt = skey.fetch(entityManager);
            /*
            if (opt.isPresent()) {
                Substance s = (Substance) opt.get().getValue();
            if (s != null) return s;
            }
            */
        }catch(Exception e){//nothing found
            e.printStackTrace();
        }
        return null;
    }

    public AdverseEventPt () {}

}