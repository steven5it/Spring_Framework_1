package assign.controllers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import assign.services.DefaultHistoryService;
import assign.services.DefaultLinksService;
import assign.services.HistoryService;
import assign.services.LinksService;


@RequestMapping("/")
public class DisplayController {
	protected static Log logger = LogFactory.getLog(DisplayController.class.getName());
	private HistoryService historyService = new DefaultHistoryService();
	private LinksService linksService = new DefaultLinksService();
	
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}
	
    @RequestMapping(value = "/")
    public View home(Map<String, Object> model)
    {
		logger.info("Opening homepage");
		model.put("displayUrl", "displayController.jsp");
		return new RedirectView("/{displayUrl}", true);
    }
	
	@ResponseBody
    @RequestMapping(value = "/", params = {"type", "project", "year"}, method=RequestMethod.POST)
    public ModelAndView getEavesdropLink(@RequestParam("type") String type, @RequestParam("project") String project, 
    		@RequestParam("year") String year, HttpServletRequest request)
    {
		logger.info("Type is: " + type + ", Project is: " + project + ", Year is: " + year);
		HttpSession session = prepareSession (request);

		String validatedType = validateType(type);
		String validatedProject = validateProject(type, project);
		String validatedYear = validateYear(type, year);
		if (validatedType == null) {
			logger.warn("Type input is wrong format");
			return new ModelAndView("/displayController");
		}

		EavesdropLink link = new EavesdropLink(validatedType, validatedProject, validatedYear);
		logger.info(link);
		
		Map<String, Object> myModel = new HashMap<String, Object>();
		historyService = (HistoryService) session.getAttribute("historyService");
		if (historyService == null) {
			logger.warn("historyservice nnull");
		}
		historyService.addHistory(link.getLink());
		myModel.put("history", historyService.getHistory());
		populateLinks(link);
		myModel.put("links", linksService.getLinks());
		myModel.put("eavesdropLink", link);
		session.setAttribute("historyService", historyService);
		return new ModelAndView("/displayController", "model", myModel);
    }
	
	private HttpSession prepareSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("historyService") == null)
		{
			logger.info("Session history was null.");
			historyService.setHistory(new ArrayList<String>());
			session.setAttribute("historyService", historyService);
		}
		return session;
	}

	private void populateLinks(EavesdropLink eavesdropLink) {
		List<String> linksList = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect(eavesdropLink.getLink()).get();
			Elements links = doc.select("a[href]");
			int i = 0;
	        for (Element link : links) {
	        	if (i++ < 5)
	        		continue;
	        	linksList.add("<a href = " + link.attr("abs:href") + ">" + link.attr("abs:href") + "</a>");
	            logger.info("Page link is: " + link.attr("abs:href"));
	        }
			linksService.setLinks(linksList);
		} catch (IOException e) {
			logger.error("IO Exception error connection Jsoup to document");
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/closeSession", method=RequestMethod.POST)
	public View closeSession()
	{
		historyService.setHistory(new ArrayList<String>());
		return new RedirectView("/", true); 
	}

	protected String validateType(String type) {
		if (type.equals("meetings") || type.equals("irclogs")) {
			return type + "/";
		}
		return null;
	}

	protected String validateProject(String type, String project) {
		if (type.equals("irclogs")) {
			return "%23" + project + "/";
		}
		return project + "/";
	}

	protected String validateYear(String type, String year) {
		if (type.equals("irclogs"))
		{
			return "";
		}
		return year + "/";
	}


}
