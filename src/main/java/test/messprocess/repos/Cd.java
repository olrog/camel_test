package test.messprocess.repos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "CD")
@XmlAccessorType (XmlAccessType.FIELD)
@Setter
@Getter
public class Cd {
	@XmlElement(name = "TITLE")
	private String title;
	@XmlElement(name = "ARTIST")
	private String artist;
	@XmlElement(name = "COUNTRY")
	private String country;
	@XmlElement(name = "COMPANY")
	private String company;
	@XmlElement(name = "PRICE")
	private String price;
	@XmlElement(name = "YEAR")
	private String year;
}
