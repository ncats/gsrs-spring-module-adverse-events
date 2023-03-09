package gov.hhs.gsrs.adverseevents.adverseeventcvm.controllers;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.services.*;
import gov.hhs.gsrs.adverseevents.adverseeventcvm.searcher.*;
import gov.hhs.gsrs.adverseevents.AdverseEventDataSourceConfig;

import gsrs.controller.*;
import gsrs.controller.hateoas.HttpRequestHolder;
import gov.nih.ncats.common.util.Unchecked;
import gsrs.autoconfigure.GsrsExportConfiguration;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.ETagRepository;
import gsrs.repository.EditRepository;
import gsrs.service.EtagExportGenerator;
import gsrs.service.ExportService;
import gsrs.service.GsrsEntityService;

import ix.core.models.ETag;
import ix.core.search.SearchResult;
import ix.ginas.exporters.ExportMetaData;
import ix.ginas.exporters.ExportProcess;
import ix.ginas.exporters.Exporter;
import ix.ginas.exporters.ExporterFactory;
import ix.ginas.models.v1.Substance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

@ExposesResourceFor(AdverseEventCvm.class)
@GsrsRestApiController(context = AdverseEventCvmEntityService.CONTEXT, idHelper = IdHelpers.STRING_NO_WHITESPACE)
public class AdverseEventCvmController extends EtagLegacySearchEntityController<AdverseEventCvmController, AdverseEventCvm, String> {

    @Autowired
    private ETagRepository eTagRepository;

    @PersistenceContext(unitName =  AdverseEventDataSourceConfig.NAME_ENTITY_MANAGER)
    private EntityManager entityManager;

    @Autowired
    private GsrsControllerConfiguration gsrsControllerConfiguration;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ExportService exportService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private GsrsExportConfiguration gsrsExportConfiguration;


    @Autowired
    private AdverseEventCvmEntityService adverseEventEntityService;

    @Autowired
    private LegacyAdverseEventCvmSearcher legacyAdverseEventCvmSearcher;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public GsrsEntityService<AdverseEventCvm, String> getEntityService() {
        return adverseEventEntityService;
    }

    @Override
    protected LegacyGsrsSearchService<AdverseEventCvm> getlegacyGsrsSearchService() {
        return legacyAdverseEventCvmSearcher;
    }

    @Override
    protected Stream<AdverseEventCvm> filterStream(Stream<AdverseEventCvm> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }

    @PreAuthorize("isAuthenticated()")
    @GetGsrsRestApiMapping("/export/{etagId}/{format}")
    public ResponseEntity<Object> createExport(@PathVariable("etagId") String etagId,
                                               @PathVariable("format") String format,
                                               @RequestParam(value = "publicOnly", required = false) Boolean publicOnlyObj,
                                               @RequestParam(value ="filename", required= false) String fileName,
                                               Principal prof,
                                               @RequestParam Map<String, String> parameters,
                                               HttpServletRequest request

    ) throws Exception {
        /*
        Optional<ETag> etagObj = this.eTagRepository.findByEtag(etagId);
        boolean publicOnly = publicOnlyObj == null ? true : publicOnlyObj;
        if (!etagObj.isPresent()) {
            return new ResponseEntity("could not find etag with Id " + etagId, this.gsrsControllerConfiguration.getHttpStatusFor(HttpStatus.BAD_REQUEST, parameters));
        } else {
            ExportMetaData emd = new ExportMetaData(etagId, ((ETag) etagObj.get()).uri, "admin", publicOnly, format);
            Stream<AdverseEventCvm> mstream = new EtagExportGenerator<AdverseEventCvm>(entityManager, transactionManager, HttpRequestHolder.fromRequest(request)).generateExportFrom(getEntityService().getContext(), etagObj.get()).get();

            Stream<AdverseEventCvm> effectivelyFinalStream = this.filterStream(mstream, publicOnly, parameters);

            if (fileName != null) {
                emd.setDisplayFilename(fileName);
                System.out.println("FILE NAME: " + fileName);
            }

            ExportProcess<AdverseEventCvm> p = this.exportService.createExport(emd, () -> {
                return effectivelyFinalStream;
            });
            p.run(this.taskExecutor, (out) -> {
                return (Exporter) Unchecked.uncheck(() -> {

                    return this.getExporterFor(format, out, publicOnly, parameters);
                });
            });
            return new ResponseEntity(p.getMetaData(), HttpStatus.OK);
        }
    }
    */

        Optional<ETag> etagObj = eTagRepository.findByEtag(etagId);

        boolean publicOnly = publicOnlyObj==null? true: publicOnlyObj;

        if (!etagObj.isPresent()) {
            return new ResponseEntity<>("could not find etag with Id " + etagId,gsrsControllerConfiguration.getHttpStatusFor(HttpStatus.BAD_REQUEST, parameters));
        }

        ExportMetaData emd=new ExportMetaData(etagId, etagObj.get().uri, prof.getName(), publicOnly, format);

        //Not ideal, but gets around user problem
        Stream<AdverseEventCvm> mstream = new EtagExportGenerator<AdverseEventCvm>(entityManager, transactionManager, HttpRequestHolder.fromRequest(request)).generateExportFrom(getEntityService().getContext(), etagObj.get()).get();

        //GSRS-699 REALLY filter out anything that isn't public unless we are looking at private data
//        if(publicOnly){
//            mstream = mstream.filter(s-> s.getAccess().isEmpty());
//        }

        Stream<AdverseEventCvm> effectivelyFinalStream = filterStream(mstream, publicOnly, parameters);

        if(fileName!=null){
            emd.setDisplayFilename(fileName);
        }

        ExportProcess<AdverseEventCvm> p = exportService.createExport(emd,
                () -> effectivelyFinalStream);

        p.run(taskExecutor, out -> Unchecked.uncheck(() -> getExporterFor(format, out, publicOnly, parameters)));

        return new ResponseEntity<>(GsrsControllerUtil.enhanceWithView(p.getMetaData(), parameters), HttpStatus.OK);
    }

    private Exporter<AdverseEventCvm> getExporterFor(String extension, OutputStream pos, boolean publicOnly, Map<String, String> parameters) throws IOException {
        ExporterFactory.Parameters params = this.createParameters(extension, publicOnly, parameters, JsonNodeFactory.instance.objectNode());
        ExporterFactory<AdverseEventCvm> factory = this.gsrsExportConfiguration.getExporterFor(this.getEntityService().getContext(), params);
        if (factory == null) {
            throw new IllegalArgumentException("could not find suitable factory for " + params);
        } else {
            return factory.createNewExporter(pos, params);
        }
    }

    public Optional<AdverseEventCvm> injectSubstanceDetails(Optional<AdverseEventCvm> application) {

        try {
            if (application.isPresent()) {

                /*
                if (application.get().applicationProductList.size() > 0) {
                    for (int j = 0; j < application.get().applicationProductList.size(); j++) {
                        ApplicationProduct prod = application.get().applicationProductList.get(j);
                        if (prod != null) {

                            if (prod.applicationIngredientList.size() > 0) {
                                for (int i = 0; i < prod.applicationIngredientList.size(); i++) {
                                    ApplicationIngredient ingred = prod.applicationIngredientList.get(i);
                                    if (ingred != null) {
                                        if (ingred.substanceKey != null) {

                                            // ********* Get Substance Module/Details by Substance Code ***********
                                            // Using this for local Substance Module:  0017298AA
                                            // Use this for NCAT FDA URL API:   0126085AB
                                            ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(ingred.substanceKey);

                                            String jsonString = response.getBody();
                                            if (jsonString != null) {
                                                ObjectMapper mapper = new ObjectMapper();
                                                JsonNode actualObj = mapper.readTree(jsonString);

                                                ingred._substanceUuid = actualObj.path("uuid").textValue();
                                                ingred._approvalID = actualObj.path("approvalID").textValue();
                                                ingred._name = actualObj.path("_name").textValue();
                                            }
                                        }

                                        if (ingred.basisOfStrengthSubstanceKey != null) {

                                            // ********** Get Substance Module/Details by Basis of Strength by Substance Code **********
                                            // Optional<Substance> objSub = this.substanceModuleService.getSubstanceDetails("0017298AA");

                                            ResponseEntity<String> response = this.substanceModuleService.getSubstanceDetailsFromSubstanceKey(ingred.basisOfStrengthSubstanceKey);

                                            String jsonString = response.getBody();
                                            if (jsonString != null) {
                                                ObjectMapper mapper = new ObjectMapper();
                                                JsonNode actualObj = mapper.readTree(jsonString);

                                                ingred._basisOfStrengthSubstanceUuid = actualObj.path("uuid").textValue();
                                                ingred._basisOfStrengthApprovalID = actualObj.path("approvalID").textValue();
                                                ingred._basisOfStrengthName = actualObj.path("_name").textValue();
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                 */
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return application;
    }

}
