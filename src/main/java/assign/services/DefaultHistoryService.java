package assign.services;

import java.util.List;

public class DefaultHistoryService implements HistoryService {
	private List<String> history;
	@Override
	public List<String> getHistory() {
		return history;
	}

	@Override
	public void setHistory(List<String> history) {
		this.history = history;
	}
	
	public void addHistory(String historyLink)
	{
		if (!history.contains(historyLink))
			history.add(historyLink);
	}

}
