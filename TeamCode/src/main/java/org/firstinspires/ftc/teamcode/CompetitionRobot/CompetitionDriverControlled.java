package org.firstinspires.ftc.teamcode.CompetitionRobot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="CompetitionDriverControlled", group="TeleOp")

public class CompetitionDriverControlled extends OpMode{

    //DECLARE OpMode MEMBERS
    CompetitionHardware robot = new CompetitionHardware();

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
        double rightPowerT = leftTrigger + rightTrigger - rightStickX;
        double leftPowerT = leftTrigger + rightTrigger + rightStickX;

        robot.frontLeftDrive.setPower(leftPowerT);
        robot.rearLeftDrive.setPower(leftPowerT);
        robot.frontRightDrive.setPower(rightPowerT);
        robot.rearRightDrive.setPower(rightPowerT);

        if (gamepad1.right_bumper)
            robot.liftMotor.setPower(1);
        else if (gamepad1.left_bumper)
            robot.liftMotor.setPower(-1);
        else
            robot.liftMotor.setPower(0);
        
        //Send Telemetry Data
        telemetry.addData("LeftPower",  "%.2f", leftTrigger);
        telemetry.addData("RightPower", "%.2f", rightTrigger);
        telemetry.update();
    }

    //RUN ONCE ON stop()
    @Override
    public void stop() {
        robot.frontLeftDrive.setPower(0);
        robot.rearLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.rearRightDrive.setPower(0);
        robot.liftMotor.setPower(0);
    }
}
