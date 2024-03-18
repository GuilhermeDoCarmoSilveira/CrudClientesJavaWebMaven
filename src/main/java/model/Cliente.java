package model;



import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cliente {
	
	private String cpf;
	private String nome;
	private String email;
	private double limiteCredito;
	private LocalDate dtNascimento;
}
    
