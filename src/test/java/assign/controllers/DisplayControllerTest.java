package assign.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import assign.services.DefaultHistoryService;
import assign.services.DefaultLinksService;
import assign.services.HistoryService;
import assign.services.LinksService;

public class DisplayControllerTest extends TestCase {
	
	DisplayController controller = null;
	HistoryService mockHistoryService = null;
	LinksService mockLinksService = null;
	
	@Before
	public void setUp() {
		controller = new DisplayController();
		mockHistoryService = new DefaultHistoryService();
		mockLinksService = new DefaultLinksService();
		controller.setHistoryService(mockHistoryService);
		controller.setLinksService(mockLinksService);
	}
	
	public void testGetEavesdropLink(@RequestParam("type") String type, @RequestParam("project") String project, 
    		@RequestParam("year") String year, HttpServletRequest request) {
		
	}
	
	public void testHome() {
		View view = controller.home(new HashMap<String, Object>());
		assertNotNull(view);
	}
	
	public void testCloseSession() {
	}
	
	public void testValidations() {
		String typeNull = controller.validateType("invalid type");
		assertEquals(typeNull, null);
		String type = controller.validateType("irclogs");
		assertEquals(type, "irclogs/");
		
		String project = controller.validateProject("meetings", "project");
		assertEquals(project, "project/");
		project = controller.validateProject("irclogs", "project");
		assertEquals(project, "%23project/");

		String year = controller.validateYear("meetings", "2013");
		assertEquals(year, "2013/");
		year = controller.validateYear("irclogs", "2013");
		assertEquals(year, "");

	}
	
	


}
