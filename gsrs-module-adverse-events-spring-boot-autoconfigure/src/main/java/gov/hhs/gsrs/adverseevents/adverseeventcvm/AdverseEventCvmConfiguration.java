package gov.hhs.gsrs.adverseevents.adverseeventcvm;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@AutoConfigureAfter({DataJpaRepositoriesAutoConfiguration.class})
@Import(AdverseEventCvmStarterEntityRegistrar.class)
public class AdverseEventCvmConfiguration {
}
