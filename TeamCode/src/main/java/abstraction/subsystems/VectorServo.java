package abstraction.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class VectorServo {
    public static CRServo vectorServo;

    public VectorServo(OpMode opmode){
        vectorServo = opmode.hardwareMap.get(CRServo.class, "vectorServo");
    }
    public void vector_intake(){
        vectorServo.setPower(-1.0);
    }
    public void vector_outtake(){
        vectorServo.setPower(1.0);
    }
    public void vector_stop(){
        vectorServo.setPower(0);
    }
}
