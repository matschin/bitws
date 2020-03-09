package ch.admin.bit.bitws.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.inject.Inject;
import javax.inject.Named;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = Resource.class,
defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL,
resourceType="bitws/components/content/sample")
@Exporter(extensions = { "json" }, name = "jackson", options = {
		@ExporterOption(name="MapperFeature.SORT_PROPERTIES_ALPHABETICALLY",value="true"),
		@ExporterOption(name="SerializationFeature.WRITE_DATES_AS_TIMESTAMPS",value="false")
})
public class SampleModel {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @OSGiService
    private SlingSettingsService settings;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;
    
    @Inject @Named("jcr:title") @Optional
    private String title;

    @Inject @Named("jcr:created") 
    private Calendar createdAt;
    
    @Inject @Optional
    private String type;

    private String message;

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(currentResource);

        message = "\tSAMPLE!\n"
            + "\tThis is instance: " + settings.getSlingId() + "\n"
            + "\tResource type is: " + resourceType + "\n"
            + "\tCurrent page is: " + (currentPage != null ? currentPage.getPath() : "") + "\n";
    }

    public String getMessage() {
        return message;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public String getType() {
    	return type;
    }
    
    public String getResourceType() {
    	return resourceType;
    }
    
    public String goodbyeWorld() {
    	return "Goodbye World!";
    }
    
    public Calendar getCreatedAt() {
    	return createdAt;
    }

}
