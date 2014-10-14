package assign.services;

import java.util.List;

public class DefaultLinksService implements LinksService {
	private List<String> links;
	@Override
	public List<String> getLinks() {
		return links;
	}

	@Override
	public void setLinks(List<String> links) {
		this.links = links;
	}
	

}
