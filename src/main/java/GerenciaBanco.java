import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Scanner;

enum Mensagens {
    CONSULTA,
    SACA,
    SAQUE_SUCESSO,
    DEPOSITA,
    DEPOSITO_SUCESSO,
    INFORMACOES,
    PARA
}
@Data
@AllArgsConstructor
@ToString(exclude = {"cpf"})
class Cliente {
    private String nome;
    private String sobrenome;
    private String cpf;
}

@Getter
@AllArgsConstructor
@ToString
class Conta {
    double saldo;
    Cliente cliente;

    public void deposito(double valorDeposito) {
        try {
            this.saldo += valorDeposito;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Não é possível adicionar 0 ao deposito");
        }
    }

    public String saca(double valorSaque) {
        if (valorSaque == 0) {
            return "Não é possível sacar 0";
        } else if (valorSaque > this.saldo) {
            return "Não é possível sacar uma quantia superior ao valor na conta";
        }

        this.saldo -= valorSaque;
        return String.format("Valor sacado: %s. \t Valor restante na conta: %s", valorSaque, this.getSaldo());
    }
}

class CliInterface {
    public static void entrada() {
        System.out.println("----------------------------");
        System.out.println("Bem vindo ao Gerencia Banco!");
    }

    public static void opcoes() {
        System.out.println("=========");
        System.out.println("Escolha uma opção");
        System.out.println("Opções:");
        System.out.println("1. Consultar saldo:");
        System.out.println("2. Depositar");
        System.out.println("3. Saque");
        System.out.println("4. Informações da conta");
        System.out.println("5. Finalizar programa");
    }

    public static void mensagens(Mensagens mensagens) {
        switch (mensagens){
            case CONSULTA -> System.out.println("Seu saldo atual disponível é de: ");
            case DEPOSITA -> System.out.println("Informe um valor para depositar em sua conta: ");
            case DEPOSITO_SUCESSO -> System.out.println("Deposito realizado com sucesso!");
            case SACA -> System.out.println("Informe um valor para sacar de sua conta");
            case SAQUE_SUCESSO -> System.out.println("Saque realizado com sucesso!");
            case INFORMACOES -> System.out.println("Informações gerais da conta");
            case PARA -> System.out.println("Sistema \"Gerencia Bando\" finalizado. Obrigado e volte sempre! ");
        }
    }

    public static void saida() {
        System.out.println("-----------");
        System.out.println("Obrigado por utilizar o gerencia banco!!");
        System.out.println("Sistema finalizado!");
        System.out.println("xxxxxxxxx");
    }

    public static Cliente retornarNovoCliente() {
        var leitura = new Scanner(System.in);
        System.out.println("Digite seu nome:");
        var nome = leitura.nextLine();
        System.out.println("Digite seu sobrenome");
        var sobrenome = leitura.nextLine();
        System.out.println("Digite seu CPF");
        var cpf = leitura.nextLine();

        return new Cliente(nome, sobrenome, cpf);
    }

}

public class GerenciaBanco {
    public static void main(String[] args) {
        CliInterface.entrada();

        var leitura = new Scanner(System.in);
        var clienteNovo = CliInterface.retornarNovoCliente();
        var contaCliente = new Conta(0, clienteNovo);
        boolean aplicacaoRodando = true;

        while (aplicacaoRodando) {
            CliInterface.opcoes();
            int entrada = leitura.nextInt();
            System.out.println("=========");
            switch (entrada){
                case 1:
                    var saldo = contaCliente.getSaldo();
                    CliInterface.mensagens(Mensagens.CONSULTA);
                    System.out.println(saldo);
                    break;
                case 2:
                    CliInterface.mensagens(Mensagens.DEPOSITA);
                    var deposito = leitura.nextDouble();
                    contaCliente.deposito(deposito);
                    CliInterface.mensagens(Mensagens.DEPOSITO_SUCESSO);
                    break;
                case 3:
                    CliInterface.mensagens(Mensagens.SACA);
                    var valorSaque = leitura.nextDouble();
                    String isSacado = contaCliente.saca(valorSaque);
                    System.out.println(isSacado);
                    CliInterface.mensagens(Mensagens.SAQUE_SUCESSO);
                    break;
                case 4:
                    CliInterface.mensagens(Mensagens.INFORMACOES);
                    System.out.println(contaCliente);
                    break;
                default:
                    CliInterface.mensagens(Mensagens.PARA);
                    aplicacaoRodando = false;
                    CliInterface.saida();
                    break;
            }
        }
    }
}
