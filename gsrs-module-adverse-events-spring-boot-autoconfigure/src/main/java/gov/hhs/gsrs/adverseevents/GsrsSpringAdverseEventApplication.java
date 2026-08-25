package gov.hhs.gsrs.adverseevents;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.EnableAdverseEventCvm;
import gov.hhs.gsrs.adverseevents.adverseeventdme.EnableAdverseEventDme;
import gov.hhs.gsrs.adverseevents.adverseeventpt.EnableAdverseEventPt;
import gsrs.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAdverseEventPt
@EnableAdverseEventDme
@EnableAdverseEventCvm

@SpringBootApplication
@EnableGsrsApi(indexValueMakerDetector = EnableGsrsApi.IndexValueMakerDetector.CONF)
@EnableGsrsJpaEntities
@EnableGsrsLegacyAuthentication
@EnableGsrsLegacyCache
@EnableGsrsLegacyPayload
@EnableGsrsLegacySequenceSearch
@EnableGsrsLegacyStructureSearch
@EntityScan(basePackages ={"ix","gsrs", "gov.nih.ncats", "gov.hhs.gsrs"} )
@EnableJpaRepositories(basePackages ={"ix","gsrs","gov.hhs.gsrs", "gov.nih.ncats"} )
@EnableGsrsScheduler
@EnableGsrsBackup
@EnableAsync

public class GsrsSpringAdverseEventApplication {

    // Is this being used? Similar file is in service?
    public static void main(String[] args) {
        SpringApplication.run(GsrsSpringAdverseEventApplication.class, args);
    }
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**");
//            }
//        };
//    }
}
