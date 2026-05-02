package com.fiap.br;

import com.fiap.br.dao.*;
import com.fiap.br.exception.EntidadeNaoEcontradaException;
import com.fiap.br.models.account.Conta;
import com.fiap.br.models.enums.StatusConta;
import com.fiap.br.models.enums.TipoConta;
import com.fiap.br.models.enums.TipoCriptoativo;
import com.fiap.br.models.register.Corretora;
import com.fiap.br.models.register.Endereco;
import com.fiap.br.models.register.Usuario;
import com.fiap.br.utils.Art;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.fiap.br.utils.InputUtils.*;

public class Main {
    public static void main(String[] args) {


        //Init.SQL
        try {
            CheckTablesExistence checkTablesExistence = new CheckTablesExistence();
            for (String tableName: checkTablesExistence.tablesToCheck) {
                if (!checkTablesExistence.checkTableExists(tableName)) {
                    try {
                        InitSQL initSQL = new InitSQL();
                        initSQL.initSQL();
                        initSQL.closeConection();
                        System.out.println("Init SQL Executado com Sucesso");
                    } catch (SQLException | IOException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                } else {
                    System.out.println("Tables already exist");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // Scanner Opening
        Scanner scanner = new Scanner(System.in);

        // ASCII Art
        Art.getWelcome();

        // Disclaimer
        System.out.println("- Disclaimer: \n" +
                "  - Projeto adaptado a uma lógica de CLI, ou seja, nem todos os elementos estão conforme a documentação\n" +
                "  - Não foi possível implementar os métodos delete, put : por questão de tempo, não de desafio técnico");
        String continuar = "S";
        do {
            // Interaction Loop 1
            int classe = -10;
            do {
                try {
                    System.out.println("\nEscolha uma das classes:\n" +
                            "1 - Usuário\n" +
                            "2 - Endereço\n" +
                            "3 - Corretora\n" +
                            "4 - ContaInvestimento\n" +
                            "-1 - Sair\n"
                    );
                    classe = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Digite um dos números indicados.");
                }
            } while (classe != 1 && classe != 2 && classe != 3 && classe != 4 && classe != -1);

            if (classe == -1) {
                break;
            }
            // Interaction Loop 2
            int option = -1;
            do {
                try {
                    System.out.println("\nEscolha uma das opções:\n" +
                            "1 - Cadastrar um registro \n" +
                            "2 - Exibir um registro \n" +
                            "3 - Exibir todos os registros \n" +
                            "4 - Atualizar um registro \n" +
                            "5 - Deletar um registro \n");
                    option = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Digite um dos números indicados.");
                }
            } while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5);

            try {
                switch (option) {
                    case 1 -> {

                        try {
                            if (classe == 1) {
                                userInput(scanner);
                            } else if (classe == 2) {
                                addressInput(scanner);
                            } else if (classe == 3) {
                                brokerInput(scanner);
                            } else if (classe == 4) {
                                accountInput(scanner);
                            } else {
                                System.out.println("A classe foi tratada de forma errada !!!");
                            }
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 2 -> {
                        try {
                            if (classe == 1) {
                                userExibir(scanner);
                            } else if (classe == 2) {
                                addressExibir(scanner);
                            } else if (classe == 3) {
                                brokerExibir(scanner);
                            } else if (classe == 4) {
                                accountExibir(scanner);
                            } else {
                                System.out.println("A classe foi tratada de forma errada !!!");
                            }
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 3 -> {
                        try {
                            if (classe == 1) {
                                userListar(scanner);

                            } else if (classe == 2) {
                                addressListar(scanner);
                            } else if (classe == 3) {
                                brokerListar(scanner);
                            } else if (classe == 4) {
                                accountListar(scanner);
                            } else {
                                System.out.println("A classe foi tratada de forma errada !!!");
                            }
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 4 -> {
                        try {
                            if (classe == 1) {
                                userAtualizar(scanner);
                            } else if (classe == 2) {
                                addressAtualizar(scanner);
                            } else if (classe == 3) {
                                brokerAtualizar(scanner);
                            } else if (classe == 4) {
                                accountAtualizar(scanner);
                            } else {
                                System.out.println("A classe foi tratada de forma errada !!!");
                            }
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }

                    }
                    case 5 -> {
                        try {
                            if (classe == 1) {
                                userRemover(scanner);
                            } else if (classe == 2) {
                                addressRemover(scanner);
                            } else if (classe == 3) {
                                brokerRemover(scanner);
                            } else if (classe == 4) {
                                accountRemover(scanner);
                            } else {
                                System.out.println("A classe foi tratada de forma errada !!!");
                            }
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                        }
                    }

                    default -> {
                        System.out.println("Valor Inválido");
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            System.out.println("\nVocê deseja continuar? S/N");
            continuar = scanner.next();

        }while(Objects.equals(continuar, "S") || Objects.equals(continuar, "s"));

        //System.out.println("\nObrigado e até a próxima!!!");
        Art.getGoodbye();
    }


    private static void accountInput(Scanner scanner) throws SQLException {
        Conta conta = new Conta();
        AccountDAO accountDAO = new AccountDAO();

        System.out.println("\n ### Cadastro de Conta. ### ");

        accountNumberInput(scanner, conta);
        accountPasswordInput(scanner, conta);
        accountTypeInput(scanner, conta);
        /*accountCryptoTypeInput(scanner, conta);*/
        accountStatusInput(scanner, conta);
        accountAddressInput(scanner, conta);

        accountDAO.registerAccount(conta);

        System.out.println("\nDados Cadastrados!!!!");
        System.out.println(conta.getResumoConta());
    }

    private static void brokerInput(Scanner scanner) throws SQLException {
        Corretora corretora = new Corretora();
        CorretoraDAO corretoraDAO = new CorretoraDAO();
        System.out.println("\n### Cadastro de Corretora. ###");

        brokerNameInput(scanner, corretora);
        brokerCNPJInput(scanner, corretora);
        brokerTelNumberInput(scanner, corretora);
        brokerEmailInput(scanner, corretora);
        brokerCriptoActivesInput(scanner, corretora); //still need to make it a list
        brokerAddressInput(scanner, corretora);

        corretoraDAO.registerCorretora(corretora);

        System.out.println("\nDados cadastrados!!!!");
        System.out.println(corretora.getResumoCorretora());
    }

    private static void addressInput(Scanner scanner) throws SQLException {
        Endereco endereco = new Endereco();
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        System.out.println("\n### Cadastro de Endereço. ###");

        int numberInput = -1;
        addressStreetInput(scanner, endereco);
        addressNumberInput(numberInput, scanner, endereco);
        addressExtraInfoInput(scanner, endereco);
        addressNeighborhoodInput(scanner, endereco);
        addressCityInput(scanner, endereco);
        addressStateCodeInput(scanner, endereco);
        addressZipCodeInput(scanner, endereco);
        addressCountryInput(scanner, endereco);

        enderecoDAO.registerEndereco(endereco);

        System.out.println("\nDados cadastrados!!!!");
        System.out.println(endereco.getResumoEndereco());
    }

    private static void userInput(Scanner scanner) throws SQLException {
        Usuario usuario = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        System.out.println("\n### Cadastro de Usuário ###");

        userNameInput(scanner, usuario);
        userEmailInput(scanner, usuario);
        userPasswordInput(scanner, usuario);
        userCPFInput(scanner, usuario);
        userTelNumberInput(scanner, usuario);

        usuarioDAO.registerUsuario(usuario);

        System.out.println("\nDados cadastrados!!!!");
        System.out.println(usuario.getResumoUsuario());
    }

    private static void userExibir(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            System.out.println("\n### Exibição de um registro de usuário ###");
            System.out.println("\nDigite o id do usuário: ");
            String id_usuario_pesquisar = scanner.next();

            Usuario usuario = usuarioDAO.getUsuario(id_usuario_pesquisar);
            System.out.println(usuario.getResumoUsuario());

        }catch(SQLException e){
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Codigo não existe na tabela");
        }
    }

    private static void addressExibir(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            EnderecoDAO enderecoDAO = new EnderecoDAO();

            System.out.println("\n### Exibição de um registro de endereço ###");
            System.out.println("\nDigite o id do endereço: ");
            String id_endereco_pesquisar = scanner.next();

            Endereco endereco = enderecoDAO.getEndereco(id_endereco_pesquisar);
            System.out.println(endereco.getResumoEndereco());

        }catch(SQLException e){
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Codigo não existe na tabela");
        }
    }

    private static void brokerExibir(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            CorretoraDAO corretoraDAO = new CorretoraDAO();

            System.out.println("\n### Exibição de um registro de corretora ###");
            System.out.println("\nDigite o id da corretora: ");
            String id_corretora_pesquisar = scanner.next();

            Corretora corretora = corretoraDAO.getCorretora(id_corretora_pesquisar);
            System.out.println(corretora.getResumoCorretora());

        }catch(SQLException e){
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Codigo não existe na tabela");
        }
    }

    private static void accountExibir(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            AccountDAO accountDAO = new AccountDAO();

            System.out.println("\n### Exibição de um registro de conta de investimento ###");
            System.out.println("\nDigite o número da conta de investimento: ");
            String nr_account_pesquisar = scanner.next();

            Conta conta = accountDAO.getAccount(nr_account_pesquisar);
            System.out.println(conta.getResumoConta());

        }catch(SQLException e){
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Codigo não existe na tabela");
        }
    }



    private static void userListar(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Usuario> usuarios = usuarioDAO.listar();
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getResumoUsuario());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addressListar(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            List<Endereco> enderecos = enderecoDAO.listar();
            for (Endereco endereco : enderecos) {
                System.out.println(endereco.getResumoEndereco());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void brokerListar(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            CorretoraDAO corretoraDAO = new CorretoraDAO();
            List<Corretora> corretoras = corretoraDAO.listar();
            for (Corretora corretora : corretoras) {
                System.out.println(corretora.getResumoCorretora());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void accountListar(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            AccountDAO accountDAO = new AccountDAO();
            List<Conta> contas = accountDAO.listar();
            for (Conta conta : contas) {
                System.out.println(conta.getResumoConta());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



    private static void userAtualizar(Scanner scanner) throws SQLException {
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(System.in);

            UsuarioDAO usuarioDao = new UsuarioDAO();

            System.out.println("\n### Atualização de um usuário ###");
            System.out.println("Digite id do usuário: ");

            String id_usuario_pesquisar = scanner2.nextLine();

            Usuario usuario = usuarioDao.getUsuario(id_usuario_pesquisar);

            System.out.println("Digite o novo nome do usuário: ");
            String novo_nome = scanner2.nextLine();
            usuario.setNomeCompleto(novo_nome);

            System.out.println("Digite o novo email do usuário: ");
            String novo_email = scanner2.nextLine();
            usuario.setEmail(novo_email);

            System.out.println("Digite a nova senha do usuário: ");
            String nova_senha = scanner2.nextLine();
            usuario.setSenha(nova_senha);

            System.out.println("Digite o novo cpf do usuário: ");
            String novo_cpf = scanner2.nextLine();
            usuario.setCpf(novo_cpf);

            System.out.println("Digite o novo telefone do usuário: ");
            String novo_telefone = scanner2.nextLine();
            usuario.setTelefone(novo_telefone);

            usuarioDao.updateUsuario(usuario);
            System.out.println("Usuário atualizado!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Usuário não encontrado");
        }
    }

    private static void addressAtualizar(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(System.in);

            EnderecoDAO enderecoDao = new EnderecoDAO();

            System.out.println("\n### Atualização de um endereço ###");
            System.out.println("\nDigite id do endereço: ");
            String id_endereco_pesquisar = scanner2.nextLine();

            Endereco endereco = enderecoDao.getEndereco(id_endereco_pesquisar);

            System.out.println("Digite a nova rua do endereço: ");
            String nova_rua = scanner2.nextLine();
            endereco.setRua(nova_rua);

            System.out.println("Digite o novo número do endereço: ");
            int novo_numero = scanner2.nextInt();
            scanner2.nextLine();
            endereco.setNumero(novo_numero);

            System.out.println("Digite o novo complemento do endereco: ");
            String novo_complemento = scanner2.nextLine();
            endereco.setComplemento(novo_complemento);

            System.out.println("Digite o novo bairro do endereco: ");
            String novo_bairro = scanner2.nextLine();
            endereco.setBairro(novo_bairro);

            System.out.println("Digite a nova cidade do endereco: ");
            String nova_cidade = scanner2.nextLine();
            endereco.setCidade(nova_cidade);

            System.out.println("Digite o novo código de estado do endereco: ");
            String novo_cd_estado = scanner2.nextLine();
            endereco.setCdEstado(novo_cd_estado);

            System.out.println("Digite o novo cep do endereco: ");
            String novo_cep = scanner2.nextLine();
            endereco.setCep(novo_cep);

            System.out.println("Digite o novo país do endereco: ");
            String novo_pais = scanner2.nextLine();
            endereco.setPais(novo_pais);

            enderecoDao.updateEndereco(endereco);
            System.out.println("Endereço atualizado!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Endereço não encontrado");
        }
    }

    private static void brokerAtualizar(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(System.in);

            CorretoraDAO corretoraDao = new CorretoraDAO();

            System.out.println("\n### Atualização de uma corretora ###");
            System.out.println("\nDigite id da corretora: ");
            String id_corretora_pesquisar = scanner2.nextLine();

            Corretora corretora = corretoraDao.getCorretora(id_corretora_pesquisar);

            System.out.println("Digite o novo nome da corretora: ");
            String novo_nome_corretora = scanner2.nextLine();
            corretora.setNomeCorretora(novo_nome_corretora);

            System.out.println("Digite o novo cnpj da corretora: ");
            String novo_cnpj_corretora = scanner2.nextLine();
            corretora.setCnpj(novo_cnpj_corretora);

            System.out.println("Digite o novo email da corretora: ");
            String novo_email_corretora = scanner2.nextLine();
            corretora.setEmail(novo_email_corretora);

            System.out.println("Digite o novo telefone da corretora: ");
            String novo_telefone_corretora = scanner2.nextLine();
            corretora.setTelefone(novo_telefone_corretora);

            System.out.println("Digite o novo tipo de criptoativo da corretora: ");
            TipoCriptoativo[] criptoativos = TipoCriptoativo.values();
            for (int i = 0; i < criptoativos.length; i++) {
                TipoCriptoativo cripto = criptoativos[i];
                System.out.println("Index: " + i + ", Criptoativo: " + cripto.getNome() + ", Símbolo: " + cripto.getSimbolo());
            }

            int tiposCriptoativosInput = scanner.nextInt();
            scanner.nextLine();

            if (tiposCriptoativosInput >= 0 && tiposCriptoativosInput <= TipoCriptoativo.values().length) {
                corretora.setTiposCriptoativosSuportados(criptoativos[tiposCriptoativosInput]);
            } else {
                tiposCriptoativosInput = -1;
            }


            System.out.println("Digite o novo endereço de carteira da corretora: ");
            String novo_endereco_corretora = scanner2.nextLine();
            corretora.setEnderecoCarteiraCorretora(novo_endereco_corretora);

            corretoraDao.updateCorretora(corretora);
            System.out.println("Corretora atualizada!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Corretora não encontrada");
        }
    }

    private static void accountAtualizar(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(System.in);

            Conta contaInvestimento = new Conta();
            AccountDAO accountDao = new AccountDAO();

            System.out.println("\n### Atualização de uma conta de investimento ###");
            System.out.println("\nDigite número da conta: ");
            String nr_conta_pesquisar = scanner2.nextLine();

            Conta conta = accountDao.getAccount(nr_conta_pesquisar);

            System.out.println("Digite a nova senha da conta: ");
            String novo_senha_conta = scanner2.nextLine();
            conta.setSenhaConta(novo_senha_conta);

            int tipoContaInput;
            System.out.println("Digite o novotipo da sua conta:\n" +
                    "1 - Conta Corrente\n" +
                    "2 - Conta Poupança\n ");
            try {
                tipoContaInput = scanner2.nextInt();
                scanner2.nextLine();
            } catch (Exception e) {
                tipoContaInput = -1;
            }
            TipoConta tipoConta;
            if (tipoContaInput == 2) {
                tipoConta = TipoConta.CONTA_CORRENTE;
            } else if (tipoContaInput == 1) {
                tipoConta = TipoConta.CONTA_POUCANCA;
            } else {
                tipoConta = null;
                tipoContaInput = -1;
            }
            if (tipoConta != null) {
                contaInvestimento.setTipoConta(tipoConta);
            }

            System.out.println("Digite o novo saldo da conta: ");
            double novo_saldo_conta = scanner2.nextDouble();
            conta.setSaldo(novo_saldo_conta);

            int statusContaInput;
            System.out.println("Digite o novo status da sua conta: \n" +
                    "1 - Ativo\n" +
                    "2 - Inativo\n");
            try {
                statusContaInput = scanner2.nextInt();
                scanner2.nextLine();
            } catch (Exception e) {
                statusContaInput = -1;
            }
            StatusConta statusConta;
            if (statusContaInput == 1) {
                statusConta = StatusConta.ATIVA;
            } else if (statusContaInput == 2) {
                statusConta = StatusConta.INATIVA;
            } else {
                statusContaInput = -1;
                statusConta = null;
            }
            if (statusConta != null) {
                contaInvestimento.setStatusConta(statusConta);
            }

            System.out.println("Digite o novo endereço de carteira da conta: ");
            String novo_endereco_conta = scanner2.nextLine();
            conta.setEnderecoCarteira(novo_endereco_conta);

            accountDao.updateAccount(conta);
            System.out.println("Conta atualizada!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEcontradaException e) {
            System.err.println("Conta não encontrada");
        }
    }



    private static void userRemover(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(System.in);
            UsuarioDAO usuarioDao = new UsuarioDAO();

            System.out.println("\n### Remoção de um usuário ###");
            System.out.println("\nDigite o id do usuário: ");
            String id_usuario_pesquisar = scanner2.nextLine();

            usuarioDao.deleteUsuario(id_usuario_pesquisar);
            System.out.println("Usuário Removido!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addressRemover(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            EnderecoDAO enderecoDao = new EnderecoDAO();

            System.out.println("\n### Remoção de um endereço ###");
            System.out.println("\nDigite o id do endereço: ");
            String id_endereco_pesquisar = scanner.next();

            enderecoDao.deleteEndereco(id_endereco_pesquisar);
            System.out.println("Endereço Removido!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void brokerRemover(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            CorretoraDAO corretoraDao = new CorretoraDAO();

            System.out.println("\n### Remoção de uma corretora ###");
            System.out.println("\nDigite o id da corretora: ");
            String id_corretora_pesquisar = scanner.next();

            corretoraDao.deleteCorretora(id_corretora_pesquisar);
            System.out.println("Corretora Removida!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void accountRemover(Scanner scanner) throws SQLException, EntidadeNaoEcontradaException {
        try {
            AccountDAO accountDao = new AccountDAO();

            System.out.println("\n### Remoção de uma conta de investimento ###");
            System.out.println("\nDigite o número da conta: ");
            String nr_conta_pesquisar = scanner.next();

            accountDao.deleteAccount(nr_conta_pesquisar);
            System.out.println("Conta Removida!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}