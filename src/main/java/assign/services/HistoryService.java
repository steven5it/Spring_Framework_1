package assign.services;

import java.util.List;

public interface HistoryService {
	public List<String> getHistory();
	public void setHistory (List<String> history);
	public void addHistory (String historyLink);
}
