package com.isg.ifrend.model.bean;

public enum PriorityType {
	
	PRIORITY1(1, "Priority 1"), PRIORITY2(2,"Priority 2"),
	PRIORITY3(3,"Priority 3"), PRIORITY4(4,"Priority 4"),
	PRIORITY5(5,"Priority 5");
	
	private int id;
	private String desc;
	
	private PriorityType(int id, String desc){
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
		for(PriorityType t : values()){
			if(t.getId() == id){
				return t.getDesc();
			}
		}
		return null;
	}
	
	public static int getId(String value){
		for(PriorityType t: values()){
			if(t.getDesc().equals(value)){
				return t.getId();
			}
		}
		return 0;
	}
	
	
}
