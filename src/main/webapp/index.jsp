<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/styles1.css">
<title>Crud Cliente</title>
</head>
<body>
	<div align="center" class="container">
		<form action="cliente" method="post">
			<p class="title">
				<b>Cliente</b>
			</p>
			<table>
				<tr>
					<td colspan="3"><input class="id_input_data" type="text"
						id="cpf" name="cpf" placeholder="CPF - 000.000.000-00" 
						value='<c:out value="${cliente.cpf }"></c:out>'>
					</td>
					
					<td><input type="submit" id="botao" name="botao"
						value="Buscar">
					</td>
				</tr>

				<tr>
					<td colspan="4"><input class="input_data" type="text"
						id="nome" name="nome" placeholder="Nome"
						value='<c:out value="${cliente.nome }"></c:out>'>
					</td>
				</tr>

				<tr>
					<td colspan="4"><input class="input_data" type="email"
						id="email" name="email" placeholder="Email"
						value='<c:out value="${cliente.email}"></c:out>'>
					</td>
				</tr>
				
				<tr>
					<td colspan="4"><input class="input_data" type="text"
						id="limiteCredito" name="limiteCredito" placeholder="Limite de Credito"
						value='<c:out value="${cliente.limiteCredito}"></c:out>'>
					</td>
				</tr>
				
				<tr>
					<td colspan="4"><input class="input_data" type="date"
						id="dtNascimento" name="dtNascimento" placeholder="Data de Nascimento"
						value='<c:out value="${cliente.dtNascimento}"></c:out>'>
					</td>
				</tr>

				<tr>
					<td><input type="submit" id="botao" name="botao"
						value="Cadastrar"></td>

					<td><input type="submit" id="botao" name="botao"
						value="Alterar"></td>

					<td><input type="submit" id="botao" name="botao"
						value="Excluir"></td>

					<td><input type="submit" id="botao" name="botao"
						value="Listar"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div align="center">
		<c:if test="${not empty erro }">
			<h3><b><c:out value="${erro }" /></b></h3>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida }">
			<h3><b><c:out value="${saida }" /></b></h3>
		</c:if>
	</div>
	
	<br />
	
	<div align="center">
		<c:if test="${not empty clientes }">
			<table class="table_round">
				<thead>
					<tr>
						<th>Cpf</th>
						<th>Nome</th>
						<th>Email</th>
						<th>Limite de Credito</th>
						<th>Data de Nascimento</th>
					</tr>	
				</thead>
				<tbody>
					<c:forEach var="c" items="${clientes }">
						<tr>
							<td><c:out value="${c.cpf }" /></td>
							<td><c:out value="${c.nome }" /></td>
							<td><c:out value="${c.email}" /></td>
							<td><c:out value="${c.limiteCredito}" /></td>
							<td><c:out value="${c.dtNascimento}" /></td>
						</tr>
					</c:forEach>
				</tbody>		
			</table>
		</c:if>
	</div> 
</body>
</html>