package variables;

import java.util.ArrayList;

import utils.Utils;

public class Variable {
	private static int count = 0;
	private String name;
	private ArrayList<String> type;
	private int id;
	
	public Variable(String name) {
		this.name = name;
		this.type = new ArrayList<String>();
		id = count;
		count++;
	}
	
	// Info
	public boolean hasType() { return (type != null); }
	
	// Get
	public String getName() { return name; }
	
	public ArrayList<String> getTypes() { return type; }
	
	// Get actual type this variable is going to be
	public String getType() {
		int number = 0, bool = 0, str = 0, ch = 0, array = 0;
		
		String type_name = Utils.UNDEFINED;
		int priority_status = -1;
		for (String s: type) {
			if (s.equals(Utils.STRING)) {
				str = 1;
				type_name = s;
			}
			else if (s.equals(Utils.BOOLEAN)) {
				bool = 1;
				type_name = s;
			}
			else if (s.equals(Utils.CHAR)) {
				ch = 1;
				type_name = s;
			}			
			else if (Utils.NUMERIC.contains(s) && Utils.NUMERIC.indexOf(s) > priority_status) {
				number = 1;
				type_name = s;
				priority_status = Utils.NUMERIC.indexOf(s);
			}
			else if (s.contains("array")) {
				array = 1;
				String[] array_type_def = s.split("=");
				String array_type = array_type_def[1];
				
				if (!Utils.NUMERIC.contains(array_type)) {
					type_name = s;
				}
				else if (Utils.NUMERIC.contains(array_type) && Utils.NUMERIC.indexOf(array_type) > priority_status) {
					type_name = s;
					priority_status = Utils.NUMERIC.indexOf(s);
				}
			}
		}
		
		if (number + bool + str + ch + array> 1) {
			return Utils.DYNAMIC;
		}
		else {
			return type_name;
		}
	}
	
	
	// Set
	public void addType(String type) { this.type.add(type); }
	
	// Utils
	public String toString() { return name + "(" + getType() + ")" + "-" + id; }
	@Override
    public boolean equals(Object o) {
        if (o == this) {
        	return true;
        }
        if (o instanceof String) {
        	return name.equals((String) o);
        }
        if (!(o instanceof Variable)) {
            return false;
        }
        
        return name.equals(((Variable) o).getName());        
    }
}
