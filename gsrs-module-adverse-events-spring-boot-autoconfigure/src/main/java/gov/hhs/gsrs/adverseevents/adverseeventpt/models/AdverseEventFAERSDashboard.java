package gov.hhs.gsrs.adverseevents.adverseeventpt.models;

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
@Table(name="SRSCID_FAERS_DASHB_SUBSTANCE")
public class AdverseEventFAERSDashboard extends AbstractGsrsEntity {

    @Id
    @Column(name="ID")
    public int id;

    @Column(name="NAME", length=500)
    public String name;

    @Column(name="NAME_TRUNCATE_G_P", length=500)
    public String nameTruncateGP;

    @Column(name="NAME_TYPE")
    public String nameType;

    public AdverseEventFAERSDashboard () {}
}
