-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 27/02/2025 às 03:18
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
  `cliente_id` int(11) NOT NULL,
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

INSERT INTO `carros` (`id`, `cliente_id`, `situacao`, `marca`, `modelo`, `anoFabricacao`, `placa`, `observacoes`) VALUES
(1, 1, 'Concluído', 'Volvo', 'March', '2010', 'KCKF-SAS', NULL),
(2, 1, 'Em andamento', 'Toyota', 'Corolla', '2020', 'XYZ-1234', 'Veículo em revisão'),
(3, 1, 'Em andamento', '1', '1', '1', '1', '1'),
(4, 2, 'Em andamento', 'Fiat', 'Uno', '2023', 'ODOD', 'Tem uma bela coisa no fundo!'),
(5, 2, 'Em andamento', 'Fiat', 'Mobi', '2013', 'PPK-1234', '');

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
(3, 'CNPJ', '12.345.678/0001-99', 'Empresa XYZ Ltdaa', 'Ativo', 'COMERCIAL', 'Av. Paulista', '2000', 'Bela Vista', 'São Paulo', 'SP', '01310-000', 'Sala 305', 'Carlos Mendes', '(11) 4002-8922', '(11) 95555-8888'),
(4, 'CPF', '987.654.321-10', 'Ana Pereira', 'Inativo', 'RESIDENCIAL', 'Rua dos Pinheiros', '45', 'Jardins', 'Rio de Janeiro', 'RJ', '22050-010', '', 'Pedro Henrique', '(21) 98877-6655', ''),
(5, 'CNPJ', '98.765.432/0001-55', 'Tech Solutions Ltda', 'Ativo', 'COMERCIAL', 'Rua XV de Novembro', '500', 'Centro', 'Curitiba', 'PR', '80020-310', 'Bloco B', 'Fernanda Lima', '(41) 3322-4455', '(41) 98866-7766'),
(6, 'CPF', '456.123.789-99', 'Marcos Almeida', 'Suspenso', 'RURAL', 'Estrada das Palmeiras', 'KM20', 'Zona Rural', 'Belo Horizonte', 'MG', '30320-567', '', 'Roberto Silva', '(31) 98899-5544', '(31) 96666-3322'),
(11, 'CPF', '00.000.000/0000-00', 'Artur', 'Ativo', 'COMERCIAL', 'asdf', '22', 'ce', 'asdf', 'CE', '', 'asd', '', '', '');

-- --------------------------------------------------------

--
-- Estrutura para tabela `ordens_servico`
--

CREATE TABLE `ordens_servico` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `carro_id` int(11) NOT NULL,
  `descricao` text NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `situacao` varchar(50) DEFAULT NULL,
  `data_entrada` timestamp NOT NULL DEFAULT current_timestamp(),
  `tipoOS` varchar(50) NOT NULL,
  `data_previsao` date DEFAULT '2099-12-31'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `ordens_servico`
--

INSERT INTO `ordens_servico` (`id`, `cliente_id`, `carro_id`, `descricao`, `valor`, `situacao`, `data_entrada`, `tipoOS`, `data_previsao`) VALUES
(5, 1, 2, 'Revisão completa teste 222', 180.00, 'Aberto', '2025-02-18 03:00:00', 'Manutenção', '2025-02-20'),
(6, 2, 2, 'Substituição do freio traseiro', 780.00, 'Indefinido', '2025-02-20 04:00:31', 'Reparo mecânico', '2024-02-24'),
(50, 1, 1, 'teste com três peças', 520.00, 'Aberto', '2025-02-25 03:00:00', 'teste', '2025-02-26');

-- --------------------------------------------------------

--
-- Estrutura para tabela `ordens_servico_pecas`
--

CREATE TABLE `ordens_servico_pecas` (
  `id` int(11) NOT NULL,
  `os_id` int(11) NOT NULL,
  `peca_id` int(11) NOT NULL,
  `valor_unitario` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `ordens_servico_pecas`
--

INSERT INTO `ordens_servico_pecas` (`id`, `os_id`, `peca_id`, `valor_unitario`) VALUES
(17, 50, 3, 150.00),
(18, 50, 10, 350.00),
(19, 50, 6, 20.00),
(29, 5, 1, 30.00),
(30, 5, 3, 150.00);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pecas`
--

CREATE TABLE `pecas` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `marca` varchar(100) NOT NULL,
  `quantidade` int(11) NOT NULL DEFAULT 0,
  `valorVenda` decimal(10,2) NOT NULL,
  `custo` decimal(10,2) NOT NULL,
  `margemLucro` decimal(5,2) NOT NULL DEFAULT 12.00,
  `dataEntrada` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pecas`
--

INSERT INTO `pecas` (`id`, `nome`, `marca`, `quantidade`, `valorVenda`, `custo`, `margemLucro`, `dataEntrada`) VALUES
(1, 'Filtro de óleo', 'Bosch', 100, 30.00, 15.50, 20.00, '2023-01-01'),
(2, 'Pastilha de freio', 'TRW', 150, 80.00, 50.00, 30.00, '2023-02-01'),
(3, 'Amortecedor', 'Monroe', 120, 150.00, 100.00, 25.00, '2023-03-01'),
(4, 'Bateria', 'Heliar', 80, 300.00, 200.00, 40.00, '2023-04-01'),
(5, 'Disco de freio', 'Zimmermann', 70, 180.00, 120.00, 30.00, '2023-05-01'),
(6, 'Lâmpada de farol', 'Philips', 200, 20.00, 10.00, 50.00, '2023-06-01'),
(7, 'Correia dentada', 'Gates', 60, 65.00, 45.00, 35.00, '2023-07-01'),
(8, 'Velas de ignição', 'NGK', 150, 40.00, 25.00, 30.00, '2023-08-01'),
(9, 'Dispositivo de suspensão', 'Sachs', 90, 200.00, 150.00, 20.00, '2023-09-01'),
(10, 'Kit de embreagem', 'LUK', 110, 350.00, 250.00, 45.00, '2023-10-01'),
(11, 'Radiador', 'Valeo', 75, 400.00, 300.00, 50.00, '2023-11-01'),
(12, 'Alternador', 'Bosch', 60, 280.00, 220.00, 30.00, '2023-12-01'),
(13, 'Cabo de vela', 'NGK', 180, 10.00, 5.00, 50.00, '2023-01-01'),
(14, 'Bujão de óleo', 'Genuine', 300, 6.00, 3.50, 10.00, '2023-02-01'),
(15, 'Embreagem', 'Valeo', 110, 270.00, 200.00, 35.00, '2023-03-01'),
(16, 'Filtro de ar', 'Mann', 200, 20.00, 12.00, 50.00, '2023-04-01'),
(17, 'Kit de corrente', 'SKF', 85, 180.00, 150.00, 20.00, '2023-05-01'),
(18, 'Escapamento', 'Bosal', 50, 169.40, 121.00, 40.00, '2023-06-01'),
(19, 'Sensor de oxigênio', 'Denso', 65, 100.00, 75.00, 25.00, '2023-07-01'),
(20, 'Injetores', 'Delphi', 40, 350.00, 250.00, 30.00, '2023-08-01'),
(21, 'Pastilha de freio', 'TRW', 120, 70.00, 45.00, 25.00, '2023-09-01'),
(22, 'Filtro de combustível', 'Mann', 150, 30.00, 18.00, 20.00, '2023-10-01'),
(23, 'Suspensão', 'KYB', 80, 140.00, 100.00, 40.00, '2023-11-01'),
(24, 'Farol', 'Osram', 130, 70.00, 50.00, 35.00, '2023-12-01'),
(25, 'Correia de alternador', 'Dayco', 150, 80.00, 60.00, 20.00, '2023-01-01'),
(26, 'Escapamento', 'Walker', 80, 220.00, 170.00, 30.00, '2023-02-01'),
(27, 'Sensor de pressão', 'Bosch', 100, 130.00, 100.00, 25.00, '2023-03-01'),
(28, 'Coxim de motor', 'Febi Bilstein', 60, 160.00, 120.00, 45.00, '2023-04-01'),
(29, 'Bomba de combustível', 'Facet', 75, 200.00, 150.00, 30.00, '2023-05-01'),
(30, 'Rolamento', 'SKF', 100, 120.00, 80.00, 40.00, '2023-06-01');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `carros`
--
ALTER TABLE `carros`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `placa` (`placa`),
  ADD KEY `codigo` (`cliente_id`);

--
-- Índices de tabela `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `inscricao_numero` (`inscricao_numero`);

--
-- Índices de tabela `ordens_servico`
--
ALTER TABLE `ordens_servico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`),
  ADD KEY `carro_id` (`carro_id`);

--
-- Índices de tabela `ordens_servico_pecas`
--
ALTER TABLE `ordens_servico_pecas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `os_id` (`os_id`),
  ADD KEY `peca_id` (`peca_id`);

--
-- Índices de tabela `pecas`
--
ALTER TABLE `pecas`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `carros`
--
ALTER TABLE `carros`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `clientes`
--
ALTER TABLE `clientes`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=155;

--
-- AUTO_INCREMENT de tabela `ordens_servico`
--
ALTER TABLE `ordens_servico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de tabela `ordens_servico_pecas`
--
ALTER TABLE `ordens_servico_pecas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de tabela `pecas`
--
ALTER TABLE `pecas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `carros`
--
ALTER TABLE `carros`
  ADD CONSTRAINT `carros_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`codigo`) ON DELETE CASCADE;

--
-- Restrições para tabelas `ordens_servico`
--
ALTER TABLE `ordens_servico`
  ADD CONSTRAINT `ordens_servico_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`codigo`) ON DELETE CASCADE,
  ADD CONSTRAINT `ordens_servico_ibfk_2` FOREIGN KEY (`carro_id`) REFERENCES `carros` (`id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `ordens_servico_pecas`
--
ALTER TABLE `ordens_servico_pecas`
  ADD CONSTRAINT `ordens_servico_pecas_ibfk_1` FOREIGN KEY (`os_id`) REFERENCES `ordens_servico` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ordens_servico_pecas_ibfk_2` FOREIGN KEY (`peca_id`) REFERENCES `pecas` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
