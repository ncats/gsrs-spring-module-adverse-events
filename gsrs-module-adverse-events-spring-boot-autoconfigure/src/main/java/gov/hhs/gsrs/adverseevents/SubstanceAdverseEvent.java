package gov.hhs.gsrs.adverseevents;

import ix.core.util.EntityUtils;
import ix.core.utils.executor.ProcessListener;
import ix.ginas.models.v1.Substance;
import gsrs.springUtils.AutowireHelper;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Objects;

public interface SubstanceAdverseEvent {

    public String getSubstanceId();

    public Substance getSubstance();
        /*
            {
        if(entityManager==null) {
            AutowireHelper.getInstance().autowire(this);
        }

        if(!shouldIndexSubstance())return null;
        try{
            EntityUtils.Key skey= EntityUtils.Key.of(Substance.class, getSubstanceId());
            Substance s = (Substance) skey.fetch(entityManager);
            if (s != null) return s;
        }catch(Exception e){//nothing found
            e.printStackTrace();
        }
        return null;
    }
    */

    public boolean shouldIndexSubstance();
    
}
