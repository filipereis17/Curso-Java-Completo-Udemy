package com.educandoWeb.course.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.educandoWeb.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

	//Anotação para indicar que esta classe é uma entidade mapeada no banco de dados
	@Entity
	//Define o nome da tabela no banco de dados para esta entidade
	@Table(name = "tb_order")
	public class Order implements Serializable {
	
	 // Identificador para serialização da classe
	 private static final long serialVersionUID = 1L;
	
	 // Campo de identificação único do pedido
	 @Id
	 // Configura a estratégia de geração automática do valor da chave primária (auto-incremento)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	 // Campo que armazena a data e hora do pedido no formato específico
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	 private Instant moment;
	
	 // Campo que representa o status do pedido
	 private Integer orderStatus;
	
	 // Estabelece um relacionamento muitos-para-um com a entidade 'User' e define a chave estrangeira 'client_id'
	 @ManyToOne
	 @JoinColumn(name = "client_id")
	 private User client;
	
	 // Estabelece um relacionamento um-para-muitos com a entidade 'OrderItem' usando a propriedade 'items'
	 @OneToMany(mappedBy = "id.order")
	 private Set<OrderItem> items = new HashSet<>();
	
	 // Estabelece uma relação um-para-um com a entidade 'Payment', mapeada pela propriedade 'order'
	 @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	 // Campo que representa o pagamento associado a este pedido
	 private Payment payment;

	
	public Order() {
	}

	public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
		super();
		this.id = id;
		this.moment = moment;
		setOrderStatus(orderStatus);
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}
	
	public OrderStatus getOrderStatus() {
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		if (orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public Set<OrderItem> getItems() {
		return items;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}
}
