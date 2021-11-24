package gov.hhs.gsrs.adverseevents.adverseeventdme.respository;

import gov.hhs.gsrs.adverseevents.adverseeventdme.models.*;

import gsrs.repository.GsrsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AdverseEventDmeRepository extends GsrsRepository<AdverseEventDme, String> {

    Optional<AdverseEventDme> findById(String id);
}
