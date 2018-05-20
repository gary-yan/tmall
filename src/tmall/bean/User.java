package tmall.bean;

public class User {
	private String passowrd;
	private String name;
	private int id;
	
	public String getPassowrd() {
		return passowrd;
	}
	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAnonymousName() {
		if(null==name)
			return null;
		if(name.length()<=1)
			return "*";
		if(name.length()==2)
			return name.substring(0, 1)+"*";
		char[] cs = name.toCharArray();
		for(int i = 1; i <cs.length-1; i++) {
			cs[i] = '*';//单引号和双引号区别 单引号表示char 双引号string
		}
		return new String(cs);
	}
}
