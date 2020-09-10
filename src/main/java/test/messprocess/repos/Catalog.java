package test.messprocess.repos;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "CATALOG")
@XmlAccessorType (XmlAccessType.FIELD)
@Setter
@Getter
public class Catalog {
	@XmlElement(name = "CD")
	private List<Cd> cd;
}
