package db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Database implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8622844350073126272L;
	protected Map<Object, Object> db;
	
	protected Database() {
		db = Collections.synchronizedMap(new HashMap<Object, Object>());
	}
	
	protected Object find(Object key) {
		return db.get(key);
	}
	
	protected void remove(Object key) {
		db.remove(key);
	}
	
	protected void update(Object key, Object value) {
		db.put(key, value);
	}
	
	protected List<?> getAllDisconnected() {
		Object[] objs = db.entrySet().toArray();
		ArrayList<Object> list = new ArrayList<Object>();
		for (Object obj : objs) {
			@SuppressWarnings("unchecked")
			Entry<Object, Object> entry = (Entry<Object, Object>) obj;
			list.add(entry.getValue());
		}
		return list;
	}
}
