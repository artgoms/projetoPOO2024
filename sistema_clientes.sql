-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 06/02/2025 às 17:13
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistema_clientes`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `carros`
--

CREATE TABLE `carros` (
  `id` int(11) NOT NULL,
  `codigo` int(11) NOT NULL,
  `situacao` enum('Concluído','Em andamento','Aguardando peça','Outro') NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `anoFabricacao` varchar(4) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `observacoes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `carros`
--

INSERT INTO `carros` (`id`, `codigo`, `situacao`, `marca`, `modelo`, `anoFabricacao`, `placa`, `observacoes`) VALUES
(1, 1, 'Concluído', 'Volvo', 'March', '2010', 'KCKF-SAS', NULL),
(2, 1, 'Em andamento', 'Toyota', 'Corolla', '2020', 'XYZ-1234', 'Veículo em revisão'),
(3, 1, 'Em andamento', '1', '1', '1', '1', '1'),
(4, 2, 'Em andamento', 'Fiat', 'Uno', '2023', 'ODOD', 'Tem uma bela coisa no fundo!'),
(5, 2, 'Em andamento', 'Fiat', 'Mobi', '2013', 'PPK-1234', '');

-- --------------------------------------------------------

--
-- Estrutura para tabela `car_parts`
--

CREATE TABLE `car_parts` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `modelo` varchar(255) NOT NULL,
  `data_entrada` date NOT NULL,
  `quantidade` int(11) NOT NULL,
  `custo` decimal(10,2) NOT NULL,
  `margem` decimal(5,2) NOT NULL,
  `valor_venda` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `car_parts`
--

INSERT INTO `car_parts` (`id`, `nome`, `modelo`, `data_entrada`, `quantidade`, `custo`, `margem`, `valor_venda`) VALUES
(1, 'Filtro de Óleo', 'Modelo A', '2024-02-01', 50, 10.00, 20.00, 12.00),
(2, 'Pastilha de Freio', 'Modelo B', '2024-02-05', 30, 25.00, 30.00, 32.50),
(3, 'Bateria 60Ah', 'Modelo C', '2024-01-25', 20, 200.00, 15.00, 230.00);

-- --------------------------------------------------------

--
-- Estrutura para tabela `clientes`
--

CREATE TABLE `clientes` (
  `codigo` int(11) NOT NULL,
  `tipo_inscricao` varchar(10) NOT NULL,
  `inscricao_numero` varchar(20) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `situacao` varchar(20) NOT NULL,
  `tipo_endereco` varchar(20) NOT NULL,
  `logradouro` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `bairro` varchar(50) NOT NULL,
  `municipio` varchar(50) NOT NULL,
  `uf` char(2) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `complemento` varchar(100) DEFAULT NULL,
  `responsavel` varchar(100) DEFAULT NULL,
  `telefone1` varchar(15) DEFAULT NULL,
  `telefone2` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `clientes`
--

INSERT INTO `clientes` (`codigo`, `tipo_inscricao`, `inscricao_numero`, `nome`, `situacao`, `tipo_endereco`, `logradouro`, `numero`, `bairro`, `municipio`, `uf`, `cep`, `complemento`, `responsavel`, `telefone1`, `telefone2`) VALUES
(1, 'CNPJ', '123123', 'Artur Barboza', 'Inativo', 'RESIDENCIAL', 'asdfadf', '111', 'Centro', 'Eunapolis', 'BA', '42232-32', 'aaaaaaa', 'aaa', '(22) 22', NULL),
(2, 'CPF', '123.456.789-00', 'João da Silva', 'Ativo', 'RESIDENCIAL', 'Rua das Flores', '123', 'Centro', 'São Paulo', 'SP', '01234-567', 'Apto 101', 'Maria Souza', '(11) 98765-4321', '(11) 91234-5678'),
(3, 'CNPJ', '12.345.678/0001-99', 'Empresa XYZ Ltda', 'Ativo', 'COMERCIAL', 'Av. Paulista', '2000', 'Bela Vista', 'São Paulo', 'SP', '01310-000', 'Sala 305', 'Carlos Mendes', '(11) 4002-8922', '(11) 95555-8888'),
(4, 'CPF', '987.654.321-10', 'Ana Pereira', 'Inativo', 'RESIDENCIAL', 'Rua dos Pinheiros', '45', 'Jardins', 'Rio de Janeiro', 'RJ', '22050-010', '', 'Pedro Henrique', '(21) 98877-6655', ''),
(5, 'CNPJ', '98.765.432/0001-55', 'Tech Solutions Ltda', 'Ativo', 'COMERCIAL', 'Rua XV de Novembro', '500', 'Centro', 'Curitiba', 'PR', '80020-310', 'Bloco B', 'Fernanda Lima', '(41) 3322-4455', '(41) 98866-7766'),
(6, 'CPF', '456.123.789-99', 'Marcos Almeida', 'Suspenso', 'RURAL', 'Estrada das Palmeiras', 'KM20', 'Zona Rural', 'Belo Horizonte', 'MG', '30320-567', '', 'Roberto Silva', '(31) 98899-5544', '(31) 96666-3322'),
(11, 'CPF', '00.000.000/0000-00', 'Artur', 'Ativo', 'COMERCIAL', 'asdf', '22', 'ce', 'asdf', 'CE', '', 'asd', '', '', '');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `carros`
--
ALTER TABLE `carros`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `placa` (`placa`),
  ADD KEY `codigo` (`codigo`);

--
-- Índices de tabela `car_parts`
--
ALTER TABLE `car_parts`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `inscricao_numero` (`inscricao_numero`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `carros`
--
ALTER TABLE `carros`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `car_parts`
--
ALTER TABLE `car_parts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `clientes`
--
ALTER TABLE `clientes`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=155;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `carros`
--
ALTER TABLE `carros`
  ADD CONSTRAINT `carros_ibfk_1` FOREIGN KEY (`codigo`) REFERENCES `clientes` (`codigo`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
