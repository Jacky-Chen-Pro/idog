package org.jackyonline.idog.bean;

import java.io.Serializable;

/**
 * 数据库dog_info.db中的表dogs对应的javabean封装对象
 * @author Jacky_Chen
 *
 */
public class DogBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 数据库中狗的顺序编号 */
	private String num;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public String getChinese_name() {
		return chinese_name;
	}

	public void setChinese_name(String chinese_name) {
		this.chinese_name = chinese_name;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getShoulder_height() {
		return shoulder_height;
	}

	public void setShoulder_height(String shoulder_height) {
		this.shoulder_height = shoulder_height;
	}

	public String getBody_type() {
		return body_type;
	}

	public void setBody_type(String body_type) {
		this.body_type = body_type;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getFeeding() {
		return feeding;
	}

	public void setFeeding(String feeding) {
		this.feeding = feeding;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getIq() {
		return iq;
	}

	public void setIq(String iq) {
		this.iq = iq;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/** 这些都是狗的属性 */
	private String english_name;
	private String chinese_name;
	private String weight;
	private String shoulder_height;
	private String body_type;
	private String function;
	private String feeding;
	private String price;
	private String characteristic;
	private String iq;
	private String pic;
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	private String url;

	public DogBean() {
	}

	public DogBean(String num, String english_name, String chinese_name,
			String weight, String shoulder_height, String body_type,
			String function, String feeding, String price,
			String characteristic, String iq, String url) {
		super();
		this.num = num;
		this.english_name = english_name;
		this.chinese_name = chinese_name;
		this.weight = weight;
		this.shoulder_height = shoulder_height;
		this.body_type = body_type;
		this.function = function;
		this.feeding = feeding;
		this.price = price;
		this.characteristic = characteristic;
		this.iq = iq;
		this.url = url;
	}

	@Override
	public String toString() {
		return "DogBean [num=" + num + ", english_name=" + english_name
				+ ", chinese_name=" + chinese_name + ", weight=" + weight
				+ ", shoulder_height=" + shoulder_height + ", body_type="
				+ body_type + ", function=" + function + ", feeding=" + feeding
				+ ", price=" + price + ", characteristic=" + characteristic
				+ ", iq=" + iq + ", url=" + url + "]";
	}

}
