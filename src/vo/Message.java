package vo;

/**
 * @Description 与数据库中离线信息表对应
 * @author Jason
 * 
 *
 */
public class Message {
	private String fname;
	private String message;
	private String tname;

	public Message(String fname, String tname, String message) {
		this.setFname(fname);
		this.setTname(tname);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "outlineMessage [fname=" + fname + ",tname=" + tname + ", message=" + message + "]";
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
}
