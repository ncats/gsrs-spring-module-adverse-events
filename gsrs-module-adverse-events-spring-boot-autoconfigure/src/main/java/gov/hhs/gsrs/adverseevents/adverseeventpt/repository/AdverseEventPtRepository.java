package gov.hhs.gsrs.adverseevents.adverseeventpt.repository;

import gov.hhs.gsrs.adverseevents.adverseeventpt.models.*;

import gsrs.repository.GsrsRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AdverseEventPtRepository extends GsrsRepository<AdverseEventPt, String> {

    Optional<AdverseEventPt> findById(String id);

    @Query("SELECT a FROM AdverseEventFAERSDashboard a where lower(a.nameTruncateGP) = lower(?1)")
    AdverseEventFAERSDashboard findFaersDashboardRecordByName(String name);
}
