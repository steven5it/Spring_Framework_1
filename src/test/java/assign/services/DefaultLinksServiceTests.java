package assign.services;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class DefaultLinksServiceTests extends TestCase {
	private DefaultLinksService linksService;
	private List<String> links;
	
	private static int LINK_COUNT = 2;
	private static String LINK_1 = "www.test.com";
	private static String LINK_2 = "www.example.net";
	
    public void setUp() throws Exception {
        linksService = new DefaultLinksService();
        links = new ArrayList<String>();
        links.add(LINK_1);
        links.add(LINK_2);
        linksService.setLinks(links);
    }
	
    public void testGetLinksWithNoLinks() {
        linksService = new DefaultLinksService();
        assertNull(linksService.getLinks());
    }
    
    public void testGetLinks() {
    	List<String> links = linksService.getLinks();
    	assertNotNull(links);
    	assertEquals(LINK_COUNT, linksService.getLinks().size());
    	
    	String link_1 = links.get(0);
    	assertEquals(LINK_1, link_1);
    	
    	String link_2 = links.get(1);
    	assertEquals(LINK_2, link_2);
    }
	
}
