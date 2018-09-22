package org.firstinspires.ftc.teamcode.SmallBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SmallBotHardware
{
    //INSTANTIATE MOTORS
    public DcMotor leftDrive;
    public DcMotor rightDrive;

    // Local OpMode members
    HardwareMap hardwareMap;

    //INITIALIZE hardwareMap
    public void init(HardwareMap hardwareMap) {

        //DEFINE AND INITIALIZE MOTORS
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        
        //SET MOTOR DIRECTION
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        
        //SET ALL MOTOR setPower(0)
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        //SET ALL MOTORS TO RUN_WITHOUT_ENCODER
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
