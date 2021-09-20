package gov.hhs.gsrs.adverseevents.adverseeventpt;

import gov.hhs.gsrs.adverseevents.adverseeventpt.AdverseEventPtStarterEntityRegistrar;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(AdverseEventPtStarterEntityRegistrar.class)
public class AdverseEventPtConfiguration {
}
