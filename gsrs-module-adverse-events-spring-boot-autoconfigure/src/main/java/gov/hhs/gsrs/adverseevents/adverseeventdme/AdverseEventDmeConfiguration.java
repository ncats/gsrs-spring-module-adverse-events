package gov.hhs.gsrs.adverseevents.adverseeventdme;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@AutoConfigureAfter({DataJpaRepositoriesAutoConfiguration.class})
@Import(AdverseEventDmeStarterEntityRegistrar.class)
public class AdverseEventDmeConfiguration {
}
