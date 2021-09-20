package gov.hhs.gsrs.adverseevents.adverseeventcvm;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.AdverseEventCvmStarterEntityRegistrar;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@Import(AdverseEventCvmStarterEntityRegistrar.class)
public class AdverseEventCvmConfiguration {
}
