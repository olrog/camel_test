package test.messprocess.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


//fake finder
@Component
public class SQLFinder {
	private static final Map<String, String> query;
	static {
		query = new HashMap<>();
		query.put("INSERT_CATALOG", "INSERT INTO CATALOG (TITLE, ARTIST, COUNTRY, COMPANY, PRICE, YEAR) VALUES(?,?,?,?,?,?)");
		query.put("INSERT_HEADERS", "sql:INSERT INTO HEADERS (CD_ID, HEADERS) VALUES (:#cd_id, :#headers)");
	}
	
	public String find(String key) {
		return query.get(key);
	}
}
