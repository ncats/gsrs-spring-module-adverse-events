package gov.hhs.gsrs.adverseevents.adverseeventpt.repository;

import gov.hhs.gsrs.adverseevents.adverseeventpt.models.AdverseEventPt;

import gsrs.repository.GsrsRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AdverseEventPtRepository extends GsrsRepository<AdverseEventPt, Long> {

    Optional<AdverseEventPt> findById(Long id);
}
