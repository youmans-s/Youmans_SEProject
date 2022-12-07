package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MasterControl {
    private CommandValidator commandValidator;
    private CommandProcessor commandProcessor;
    private CommandStorage commandStorage;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor, CommandStorage commandStorage) {
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
    }

    public List<String> start(List<String> input) {
        List<String> output = new ArrayList<>();
        for (String command : input) {
            if (this.commandValidator.validate(command)) {
                this.commandProcessor.processCommand(command);
                commandStorage.validCommands.add(command);
            } else {
                this.commandStorage.invalidCommands.add(command);
            }
        }

        for (Account acc : this.commandProcessor.bank.accountList) {
            DecimalFormat f = new DecimalFormat("##.00");
            DecimalFormat g = new DecimalFormat("0.00");

            output.add(typeOutput(acc) + " " + acc.id + " " + f.format(acc.balance) + " " + g.format(acc.apr));
            for (String c : this.commandStorage.validCommands) {
                List<String> temp = Arrays.asList(c.split(" "));
                List<String> splitStr = temp.stream().map(String::toLowerCase).collect(Collectors.toList());
                for (String x : splitStr) {
                    if (x.equals(String.valueOf(acc.id)) && !splitStr.contains("create")) {
                        output.add(c);
                    }
                }
            }
        }
        for (String x : this.commandStorage.invalidCommands) {
            output.add(x);
        }
        return output;
    }

    public String typeOutput(Account acc) {
        if (acc.type == AccountType.CHECKING) {
            return "Checking";
        } else if (acc.type == AccountType.SAVINGS) {
            return "Savings";
        } else if (acc.type == AccountType.CD) {
            return "Cd";
        }
        return null;
    }
}
