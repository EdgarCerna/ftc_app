package org.firstinspires.ftc.teamcode.CompetitionRobot;

import android.media.MediaPlayer;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.R;

@Autonomous(name="GoldAlign Example", group="Autonomous")

public class GoldAlignExample extends OpMode {
    private GoldAlignDetector detector;
    CompetitionHardware robot = new CompetitionHardware();

    static final double DRIVE_SPEED = 0.3;
    boolean newCommand = true;

    @Override
    public void init() {
        robot.init(hardwareMap);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");

        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        // Optional Tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable();

        MediaPlayer sonicSong = MediaPlayer.create(hardwareMap.appContext, R.raw.sonicsong);
        sonicSong.start();

    }

    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }


    @Override
    public void loop() {
        resetStartTime();

        final int target = 320;
        //int centerCnt = 0;
        double x = detector.getXPosition();
        double e, eOld, de, ie = 0;
        double kp = 0.1, kd = 0, ki = 0;
        double leftPower, rightPower, turnSpeed;
        int cnt = 0;

        e = x - target;
        eOld = e;
        if (newCommand == true)
        {
            de = 0;
            newCommand = false;
        }
        else
        {
            de = e - eOld;  //difference
        }
        ie += e;            //integration

        turnSpeed = kp * e + kd * de + ki * ie;
        if (turnSpeed > 0.3)
        {
            turnSpeed = 0.3;
        }
        else if (turnSpeed < -0.3)
        {
            turnSpeed = -0.3;
        }
        if (turnSpeed > 0) {
            leftPower = DRIVE_SPEED + turnSpeed;
            rightPower = DRIVE_SPEED;
        }
        else
        {
            leftPower = DRIVE_SPEED;
            rightPower = DRIVE_SPEED - turnSpeed;
        }

        robot.frontLeftDrive.setPower(leftPower);
        robot.rearLeftDrive.setPower(leftPower);
        robot.frontRightDrive.setPower(rightPower);
        robot.rearRightDrive.setPower(rightPower);

        cnt++;
        if (cnt == 100)
        {
            telemetry.addData("Time After 100", getRuntime());
            telemetry.update();
        }

        telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral
        telemetry.addData("X Pos", detector.getXPosition()); // Gold X pos.
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        detector.disable();
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
    }
}