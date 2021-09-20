package gov.hhs.gsrs.adverseevents.adverseeventdme;

import gov.hhs.gsrs.adverseevents.adverseeventdme.AdverseEventDmeStarterEntityRegistrar;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(AdverseEventDmeStarterEntityRegistrar.class)
public class AdverseEventDmeConfiguration {
}
