package dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Laptop implements Serializable {
	
	private static final long serialVersionUID = 1l;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_laptop;
	private String marca;
	private String modelo;
    private Integer carga;    
    
    public Laptop() {    	
    }

	public Laptop(Integer id_laptop, String marca, String modelo, Integer carga) {
		super();
		this.id_laptop = id_laptop;
		this.marca = marca;
		this.modelo = modelo;
		this.carga = carga;
	}

	public Integer getId_laptop() {
		return id_laptop;
	}

	public void setId_laptop(Integer id_laptop) {
		this.id_laptop = id_laptop;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getCarga() {
		return carga;
	}

	public void setCarga(Integer carga) {
		this.carga = carga;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Laptop [id_laptop=" + id_laptop + ", marca=" + marca + ", modelo=" + modelo + ", carga=" + carga + "]";
	} 
	
	public void carregar(Integer min) {
		this.carga += min;
		if(this.carga > 100) {
			this.carga = 100;
		}
	}
    
}
