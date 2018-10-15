package org.firstinspires.ftc.teamcode.CompetitionRobot;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Competition Gyro Autonomous", group="Autonomous")
@Disabled
public class CompetitionGyroAuto extends LinearOpMode {

    /* Declare OpMode members. */
    CompetitionHardware robot = new CompetitionHardware();

    @Override
    public void runOpMode() {

//        robot.init(hardwareMap);
//
//        // Send telemetry message to alert driver that we are calibrating;
//        telemetry.addData(">", "Calibrating Gyro");    //
//        telemetry.update();
//
//        robot.gyro.calibrate();
//
//        // make sure the gyro is calibrated before continuing
//        while (!isStopRequested() && robot.gyro.isCalibrating())  {
//            sleep(50);
//            idle();
//        }
//
//        telemetry.addData(">", "Robot Ready.");    //
//        telemetry.update();
//
//        // Wait for the game to start (Display Gyro value), and reset gyro before we move..
//        while (!isStarted()) {
//            telemetry.addData(">", "Robot Heading = %d", robot.gyro.getIntegratedZValue());
//            telemetry.update();
//        }
//
//        robot.gyro.resetZAxisIntegrator();
//
//        double target = 0;
//        double absolute = robot.gyro.getIntegratedZValue();
//        double straightSpeed = 0.3;
//        double leftPower = straightSpeed + (absolute - target) / 100;
//        double rightPower = straightSpeed - (absolute - target) / 100;
//
//        robot.frontLeftDrive.setPower(leftPower);
//        robot.frontRightDrive.setPower(rightPower);

    }
}
