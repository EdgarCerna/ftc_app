package org.firstinspires.ftc.teamcode.SmallBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SpeedyGonzalez.EdgarHardware;

@TeleOp(name="SmallBotDriverControlled", group="TeleOp")

public class SmallBotDriverControlled extends OpMode{

    //DECLARE OpMode MEMBERS
    SmallBotHardware robot = new SmallBotHardware();

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


        robot.leftDrive.setPower(leftPower);
        robot.rightDrive.setPower(rightPower);
        
        /*
        if (rightTrigger >= 0.05) {
            //Drive Forwards
            robot.frontLeftDrive.setPower(rightTrigger);
            robot.rearLeftDrive.setPower(rightTrigger);
            robot.frontRightDrive.setPower(rightTrigger);
            robot.rearRightDrive.setPower(rightTrigger);
        }
        else if (leftTrigger <= -0.05) {
            //Drive Backwards
            robot.frontLeftDrive.setPower(leftTrigger);
            robot.rearLeftDrive.setPower(leftTrigger);
            robot.frontRightDrive.setPower(leftTrigger);
            robot.rearRightDrive.setPower(leftTrigger);
        }
        else if (rightStickX * rightStickX > 0.01) {
            //Turn Robot
            robot.frontLeftDrive.setPower(rightStickX);
            robot.rearLeftDrive.setPower(rightStickX);
            robot.frontRightDrive.setPower(-rightStickX);
            robot.rearRightDrive.setPower(-rightStickX);
        }
        else {
            robot.frontLeftDrive.setPower(0);

            robot.rearLeftDrive.setPower(0);
            robot.frontRightDrive.setPower(0);
            robot.rearRightDrive.setPower(0);
        }
        */
        
        //Send Telemetry Data
        telemetry.addData("LeftPower",  "%.2f", leftTrigger);
        telemetry.addData("RightPower", "%.2f", rightTrigger);
    }

    //RUN ONCE ON stop()
    @Override
    public void stop() {
    }
}
