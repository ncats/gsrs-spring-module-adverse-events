package gov.hhs.gsrs.adverseevents.autoconfigure;

import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableGsrsJpaEntities
@EnableGsrsApi
@AutoConfiguration
@Import({
      //  SubstanceModuleService.class
})
public class GsrsAdverseEventsAutoConfiguration {
}
