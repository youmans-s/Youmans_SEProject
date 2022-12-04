import java.util.ArrayList;
import java.util.List;

public class MasterControl {
    private CommandValidator commandValidator;
    private CommandProcessor commandProcessor;
    private CommandStorage commandStorage;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor, CommandStorage commandStorage){
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
    }

    public List<String> start(List<String> input){
        List<String> output = new ArrayList<>();
        for(String command : input){
            if(this.commandValidator.validate(command)){
                this.commandProcessor.processCommand(command);
                commandStorage.validCommands.add(command);
            }
            else{
                this.commandStorage.invalidCommands.add(command);
            }
        }

        for(Account acc : this.commandProcessor.bank.accountList){
            output.add(acc.type + String.valueOf(acc.id) + acc.balance + acc.apr);
            for(String c : this.commandStorage.validCommands){
                String[] splitStr = c.split(" ");
                for(String x : splitStr){
                    if(x.equals(String.valueOf(acc.id))){
                        output.add(c);
                    }
                }
            }
        }

        for(String x : this.commandStorage.invalidCommands){
            output.add(x);
        }
        return output;
    }
}
