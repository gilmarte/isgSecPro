package com.isg.ifrend.model.bean;

/**/
public enum LetterCodeType {
	
	LETTERCODE1(1,"Letter Code 1"),
	LETTERCODE2(2,"Letter Code 2"),
	LETTERCODE3(3,"Letter Code 3");
	
	private int id;
	private String desc;
	
	private LetterCodeType(int id, String desc){
		this.id = id;
		this.desc = desc;
	}
	
	public int getId(){
		return id;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public static String getDesc(int id){
		for(LetterCodeType t: values()){
			if(t.getId() == id){
				return t.getDesc();
			}
		}
		return null;
	}
	
	public static int getId(String value){
		for(LetterCodeType t: values()){
			if(t.getDesc().equals(value)){
				return t.getId();
			}
		}
		return 0;
	}
}
