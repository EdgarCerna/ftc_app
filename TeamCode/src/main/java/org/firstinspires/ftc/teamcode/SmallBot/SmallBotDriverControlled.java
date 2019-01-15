package org.firstinspires.ftc.teamcode.SmallBot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SpeedyGonzalez.EdgarHardware;

@TeleOp(name="SmallBotDriverControlled", group="TeleOp")
@Disabled
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
        
        //Send Telemetry Data
        telemetry.addData("LeftPower",  "%.2f", leftTrigger);
        telemetry.addData("RightPower", "%.2f", rightTrigger);
    }

    //RUN ONCE ON stop()
    @Override
    public void stop() {
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
    }
}
