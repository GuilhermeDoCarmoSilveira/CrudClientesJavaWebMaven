create database CrudCliente

go

use CrudCliente

go

create table cliente (
	cpf					char(11) not null,
	nome				varchar(100) not null,
	email				varchar(200) not null,
	limiteDeCredito		decimal(7,2) not null,
	dtNascimento		date not null
	primary key(cpf)
)

go

create procedure sp_insertCliente(@cpf char(11), @nome varchar(100), @email varchar(100), @limitedeCredito decimal(7,2), @dtNascimento date, @saida varchar(100) output)
as 
	declare @out varchar(100)
	exec sp_consultaCpf @cpf, @out output
	if(@out like 'CPF Valido') begin
		insert into cliente values (@cpf, @nome, @email, @limitedeCredito, @dtNascimento)
	end

go

create procedure sp_UpdateCliente(@cpf char(11), @nome varchar(100), @email varchar(100), @limitedeCredito decimal(7,2), @dtNascimento date, @saida varchar(100) output)
as
		set @cpf = (select cpf from cliente where cpf = @cpf)
		if(@cpf is not null)begin
			update cliente set nome = @nome, email = @email, limiteDeCredito =  @limitedeCredito , dtNascimento = @dtNascimento where cpf = @cpf
		end else begin
			raiserror('CPF invalido na base de dados', 16, 1)
		end 
go

create procedure sp_DeleteCliente(@cpf char(11),  @saida varchar(100) output)
as
	set @cpf = (select cpf from cliente where cpf = @cpf)
		if(@cpf is not null)begin
			delete cliente where cpf = @cpf
		end else begin
			raiserror('CPF invalido na base de dados', 16, 1)
		end 

select * from cliente
delete from cliente
drop procedure sp_insertCliente


go

--verifica se cpf é invalido ou nao 

create procedure sp_consultaCpf(@cpf char(11), @saida varchar(100) output)
as
--VARIAVEIS
	declare @i int, @valor int, @status int, @x int
--VALORES DAS VARIAVEIS
	set @i = 0
	set @status = 0
	set @x = 2

--verifica se cpf tem 11 digitos
if(LEN(@cpf) = 11)begin
	--VERIFICAÇÃO DE DIGITOS REPETIDOS
	while(@i < 10) begin
		if(SUBSTRING(@cpf, 1,1) = SUBSTRING(@cpf, @x, 1)) begin
			set @status = @status + 1
		end 
	set @i = @i + 1
	set @x = @x + 1
	end
	--Descobrindo o digito 10
	If(@status < 10)begin
		declare @ValorMultiplicadoPor2 int
		set @valor = 10
		set @i = 0
		set @x = 1
		set @ValorMultiplicadoPor2 = 0
		
		while (@i < 9) begin
			set @ValorMultiplicadoPor2 = CAST(SUBSTRING(@cpf, @x, 1) as int) * @valor + @ValorMultiplicadoPor2  
			set @x = @x + 1
			set @i = @i + 1
			set @valor = @valor - 1
		end
		
		declare @valorDividido int, @primeiroDigito int 

		set @valorDividido = @ValorMultiplicadoPor2 % 11

		if(@valorDividido < 2)begin
			set @primeiroDigito = 0
		end else begin
			set @primeiroDigito = 11 - @valorDividido
		end

		-- verifica se o digito descoberto é igual o inserido

		if(CAST(SUBSTRING(@cpf, 10,1)as int) = @primeiroDigito) begin
			--descobrindo segundo digito
			set @valor = 11
			set @i = 0
			set @x = 1
			set @ValorMultiplicadoPor2 = 0

			while (@i < 10) begin
			set @ValorMultiplicadoPor2 =  CAST(SUBSTRING(@cpf, @x, 1) as int) * @valor + @ValorMultiplicadoPor2
			set @x = @x + 1
			set @i = @i + 1
			set @valor = @valor - 1
			end
			
			declare @segundoDigito int
			set @valorDividido = @ValorMultiplicadoPor2 % 11

			if(@valorDividido < 2)begin
				set @segundoDigito = 0
			end else begin
				set @segundoDigito = 11 - @valorDividido
			end

			if(CAST(SUBSTRING(@cpf, 11,1)as int) = @segundoDigito) begin
					set @saida = 'CPF Valido'
			end else begin
				raiserror('CPF invalido', 16, 1)
			end

		end else begin
			raiserror('CPF inexistente', 16, 1)
		end

	end else begin
		raiserror('CPF invalido, todos os digitos são iguais', 16, 1)
	end

end else begin
	raiserror('CPF invalido, número de caracteres incorreto', 16, 1)
end


-- execução da procedures


declare @out varchar(100)
exec sp_consultaCpf '83387371004', @out output
print @out

-- insert cliente
declare @out1 varchar(100)
exec sp_insertCliente '41707740860', 'Guilherme', 'gui@gmail.com', 5000, '28/01/2004', @out1 output

--update cliente
declare @out2 varchar(100)
exec sp_UpdateCliente '41707740860', 'Guilherme do Carmo Silveira', 'gui2004@gmail.com', 10000, '28/01/2004', @out2 output

--delete cliente
declare @out3 varchar(100)
exec sp_DeleteCliente '4170774086', @out3 output
			