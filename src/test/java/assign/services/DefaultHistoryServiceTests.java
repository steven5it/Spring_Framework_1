package assign.services;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class DefaultHistoryServiceTests extends TestCase {
	private DefaultHistoryService historyService;
	private List<String> history;
	
	private static int HISTORY_COUNT = 2;
	private static int HISTORY_ADD_COUNT = 4;
	private static String HISTORY_1 = "www.test.net";
	private static String HISTORY_2 = "www.example.com";
	private static String HISTORY_3 = "www.history3.com";
	private static String HISTORY_4 = "www.history4.com";
	
    public void setUp() throws Exception {
        historyService = new DefaultHistoryService();
        history = new ArrayList<String>();
        history.add(HISTORY_1);
        history.add(HISTORY_2);
        historyService.setHistory(history);
    }
	
    public void testGetHistoryWithNoHistory() {
        historyService = new DefaultHistoryService();
        assertNull(historyService.getHistory());
    }
    
    public void testGetHistory() {
    	List<String> history = historyService.getHistory();
    	assertNotNull(history);
    	assertEquals(HISTORY_COUNT, historyService.getHistory().size());
    	
    	String history_1 = history.get(0);
    	assertEquals(HISTORY_1, history_1);
    	
    	String history_2 = history.get(1);
    	assertEquals(HISTORY_2, history_2);
    }
    
    public void testAddHistory() {
    	historyService.addHistory(HISTORY_3);
    	historyService.addHistory(HISTORY_4);
    	
    	List<String> history = historyService.getHistory();
    	assertEquals(HISTORY_ADD_COUNT, historyService.getHistory().size());

    	String history_3 = history.get(2);
    	assertEquals(HISTORY_3, history_3);
    	
      	String history_4 = history.get(3);
    	assertEquals(HISTORY_4, history_4);
    }
}
