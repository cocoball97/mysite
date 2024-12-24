package mysite.vo;

public class BoardVo {
	private Long id;
	private String title;
	private String contents;
	private Long hit;
	private String reg_date;
	private Long g_no;
	private Long o_no;
	private Long dept;
	private Long user_id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public Long getG_no() {
		return g_no;
	}
	public void setG_no(Long g_no) {
		this.g_no = g_no;
	}
	public Long getO_no() {
		return o_no;
	}
	public void setO_no(Long o_no) {
		this.o_no = o_no;
	}
	public Long getDept() {
		return dept;
	}
	public void setDept(Long dept) {
		this.dept = dept;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}