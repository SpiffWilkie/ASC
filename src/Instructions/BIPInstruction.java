/**
 *  @author Adam
 */
package Instructions;

import EmulatorProcess.Emulator;
import EmulatorProcess.EmulatorControl;
import EmulatorProcess.EmulatorError;
import GUI.ASCView;

public class BIPInstruction extends Instruction
{

    /**
     *  Checks if the value of the acc is positive.
     */
    public int checkValue(String code)
    {
        String accValue = ASCView.getAccField();

        int value = 0;

        if (accValue.substring(0, 1).equalsIgnoreCase("F")) {
            value = getIntValueOfTwosComp(accValue);
        } else {
            value = Integer.parseInt(accValue, 16);
        }
        if (value > 0) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean execute(String code)
    {
        seperateCode(code);
        
        try {
            int value = checkValue(code);
            
            // If it is positive, branch to the label.
            if (value == 1) {
                Emulator.setNewLocation(Integer.parseInt(this.address, 16));
                return true;
            }
            
            // Else increment and continue.
            Emulator.incrementProgramCounter();
            return true;

        } catch (Exception e) {
            EmulatorControl.addError(new EmulatorError(">>ERROR<< AN ERROR OCCURED WITH THE BIP OPERATION.", 0));
        }
        return false;
    }
}
