package gov.hhs.gsrs.adverseevents.adverseeventdme.models;

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
import ix.core.util.EntityUtils;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;

import ix.ginas.models.v1.Substance;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@IndexableRoot
@Backup
@Data
@Entity
@Table(name="SRSCID_ADVERSE_EVT_DME_ALL_MV")
public class AdverseEventDme extends AbstractGsrsEntity implements SubstanceAdverseEvent {

    @Id
    @Column(name = "AE_ID")
    public String id;

    @Column(name = "ID")
    public Long id2;

    @Column(name="SUBSTANCE_ID")
    public String substanceId;

    @Column(name="UNII")
    public String unii;

    @Indexable(facet = true, name = "Substance Key")
    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;

    @Indexable(suggest = true, facet = true, name = "Ingredient Name")
    @Column(name="NAME")
    public String name;

    @Indexable(suggest = true, facet = true, name = "DME Reactions")
    @Column(name="DME_REACTIONS")
    public String dmeReactions;

    @Indexable(suggest = true, facet = true, name = "PTTerm Meddra")
    @Column(name="PTTERM_MEDDRA")
    public String ptTermMeddra;

    @Column(name="CASE_COUNT")
    public Integer caseCount;

    @Column(name="DME_COUNT")
    public Integer dmeCount;

    @Column(name="DME_COUNT_PERCENT")
    public Double dmeCountPercent;

    @Column(name="WEIGHTED_AVG_PRR", columnDefinition="Decimal(10,3) default 0.0")
    public Double weightedAvgPrr = 0.0;

    @JsonIgnore
    @Indexable(facet=true, name="Deprecated")
    public String getDeprecated(){
        return "Not Deprecated";
    }

    public String getId() {
        return id;
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
    public Substance getSubstance(){
      //  if (!shouldIndexSubstance())return null;
        try{

        }catch(Exception e){//nothing found
            e.printStackTrace();
        }
        return null;
    }

    public AdverseEventDme() {}

}
