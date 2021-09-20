package gov.hhs.gsrs.adverseevents.autoconfigure;

import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableGsrsJpaEntities
@EnableGsrsApi
@Configuration
@Import({
      //  SubstanceModuleService.class
})
public class GsrsAdverseEventsAutoConfiguration {
}
