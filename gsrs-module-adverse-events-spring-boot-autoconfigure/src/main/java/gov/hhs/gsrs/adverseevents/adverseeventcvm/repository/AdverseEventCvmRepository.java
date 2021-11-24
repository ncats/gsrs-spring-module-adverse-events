package gov.hhs.gsrs.adverseevents.adverseeventcvm.repository;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.models.AdverseEventCvm;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.models.AdverseEventCvm;

import gsrs.repository.GsrsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AdverseEventCvmRepository extends GsrsRepository<AdverseEventCvm, String> {

    Optional<AdverseEventCvm> findById(String id);
}
