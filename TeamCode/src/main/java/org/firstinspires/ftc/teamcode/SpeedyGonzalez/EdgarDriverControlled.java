package org.firstinspires.ftc.teamcode.SpeedyGonzalez;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="EdgarDriverControlled", group="TeleOp")

public class EdgarDriverControlled extends OpMode{

    //DECLARE OpMode MEMBERS
    EdgarHardware robot = new EdgarHardware();

    //RUN CODE ONCE ON init()
    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    //RUN ONCE ON start()
    @Override
    public void start() {
    }

    //LOOP ON start()
    @Override
    public void loop() {
        double leftTrigger = -gamepad1.left_trigger;
        double rightTrigger = gamepad1.right_trigger;
        double rightStickX = gamepad1.right_stick_x;
        double rightPower = leftTrigger + rightTrigger - rightStickX;
        double leftPower = leftTrigger + rightTrigger + rightStickX;


        robot.frontLeftDrive.setPower(leftPower);
        robot.rearLeftDrive.setPower(leftPower);
        robot.frontRightDrive.setPower(rightPower);
        robot.rearRightDrive.setPower(rightPower);
        
        //Send Telemetry Data
        telemetry.addData("LeftPower",  "%.2f", leftTrigger);
        telemetry.addData("RightPower", "%.2f", rightTrigger);
    }

    //RUN ONCE ON stop()
    @Override
    public void stop() {
        robot.frontLeftDrive.setPower(0);
        robot.rearLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.rearRightDrive.setPower(0);
    }
}
