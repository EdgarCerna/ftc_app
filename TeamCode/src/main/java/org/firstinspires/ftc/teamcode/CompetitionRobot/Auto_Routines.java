package org.firstinspires.ftc.teamcode.CompetitionRobot;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

abstract public class Auto_Routines extends LinearOpMode {
    CompetitionHardware robot = new CompetitionHardware();
    public SamplingOrderDetector detector;
    //public GoldAlignDetector detector;

    //GLOBAL VARIABLES
    static final double DRIVE_SPEED = 0.3;
    boolean newCommand = true;

    public void Auto_Init() {
        robot.init(hardwareMap);

//        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");
//
//        detector = new GoldAlignDetector();
//        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
//        detector.useDefaults();
//
//        // Optional Tuning
//        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
//        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
//        detector.downscale = 0.4; // How much to downscale the input frames
//
//        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
//        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
//        detector.maxAreaScorer.weight = 0.005;
//
//        detector.ratioScorer.weight = 5;
//        detector.ratioScorer.perfectRatio = 1.0;
//
//        detector.enable();

        telemetry.addData("Status", "DogeCV 2018.0 - Sampling Order Example");

        detector = new SamplingOrderDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        detector.downscale = 0.4; // How much to downscale the input frames

        // Optional Tuning
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.001;

        detector.ratioScorer.weight = 15;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable();

        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
    }

    public void moveLiftMotor(int ticks, double speed){
        int lmPos = robot.liftMotor.getCurrentPosition() + ticks;
        robot.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.liftMotor.setTargetPosition(lmPos);
        robot.liftMotor.setPower(speed);
    }
    
    public void raiseLiftMotor() {
        moveLiftMotor(10690, 1);
        while(robot.liftMotor.isBusy() && !isStopRequested()){
            telemetry.addData("Status", "liftMotor Raising");
            telemetry.update();
        }
        robot.liftMotor.setPower(0);
    }
    
    public void lowerLiftMotor() {
        moveLiftMotor(-10690, 1);
        while(robot.liftMotor.isBusy() && !isStopRequested()){
            telemetry.addData("Status", "liftMotor Lowering");
            telemetry.update();
        }
        robot.liftMotor.setPower(0);
    }

    public void moveDriveEncoder(int ticksLeft, int ticksRight, double speed){
        int lfPose = robot.frontLeftDrive.getCurrentPosition() + ticksLeft;
        int lrPose = robot.rearLeftDrive.getCurrentPosition() + ticksLeft;
        int rfPos = robot.frontRightDrive.getCurrentPosition() + ticksRight;
        int rrPos = robot.rearRightDrive.getCurrentPosition() + ticksRight;

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rearLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rearRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeftDrive.setTargetPosition(lfPose);
        robot.rearLeftDrive.setTargetPosition(lrPose);
        robot.frontRightDrive.setTargetPosition(rfPos);
        robot.rearRightDrive.setTargetPosition(rrPos);

        robot.frontLeftDrive.setPower(speed);
        robot.rearLeftDrive.setPower(speed);
        robot.frontRightDrive.setPower(speed);
        robot.rearRightDrive.setPower(speed);
    }

    public boolean driveMotorsBusy(){
        return robot.frontLeftDrive.isBusy() && robot.rearLeftDrive.isBusy() && robot.frontRightDrive.isBusy() && robot.rearRightDrive.isBusy();
    }

    public void stopResetDriveEncoders(){
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setDriveMotors(double speed){
        robot.frontLeftDrive.setPower(speed);
        robot.rearLeftDrive.setPower(speed);
        robot.frontRightDrive.setPower(speed);
        robot.rearRightDrive.setPower(speed);
    }

    //NEED TO ADJUST THE ENCODER TICKS TO PROPERLY TURN
    public void turnToFaceGold() {
        if (detector.getCurrentOrder() == SamplingOrderDetector.GoldLocation.CENTER) {
            telemetry.addData("Gold Order", "CENTER");
            telemetry.update();
        }
        else if (detector.getCurrentOrder() == SamplingOrderDetector.GoldLocation.LEFT) {
            telemetry.addData("Gold Order", "LEFT");
            telemetry.update();
            moveDriveEncoder(-200, 200, DRIVE_SPEED);
        }
        else if (detector.getCurrentOrder() == SamplingOrderDetector.GoldLocation.RIGHT) {
            telemetry.addData("Gold Order", "RIGHT");
            telemetry.update();
            moveDriveEncoder(200, -200, DRIVE_SPEED);
        }
        else {
            telemetry.addData("Gold Order", "UNKNOWN");
            telemetry.update();
        }

    }

    public void craterHitGold() {
        // LOCATES THE ORDER WHERE GOLD MINERAL IS AND TURNS ROBOT BASED ON LEFT, CENTER, OR RIGHT
        turnToFaceGold();

        // DRIVE ROBOT FORWARD 2500 TICKS TO HIT GOLD ELEMENT
        moveDriveEncoder(2500, 2500, .5);
        while(driveMotorsBusy() && !isStopRequested()){
            telemetry.addData("Status", "Driving Forward To Gold");
            telemetry.update();
        }
        setDriveMotors(0);
    }

    public void deployMarker() {
        // DO SOMETHING TO DEPLOY TEAM MARKER
        // WHENEVER WE ADD TEAM MARKER MECHANISM ONTO ROBOT
    }

      // OUT OF USE CURRENTLY
//    public void pidDriveTowardGold(){
//        //VARIABLES
//        final int target = 320;
//        //int centerCnt = 0;
//        double x = detector.getXPosition();
//        double e, eOld, de, ie = 0;
//        double kp = 0.1, kd = 0, ki = 0;
//        double leftPower, rightPower, turnSpeed;
//        int cnt = 0;
//
//        e = x - target;
//        eOld = e;
//        if (newCommand == true)
//        {
//            de = 0;
//            newCommand = false;
//        }
//        else
//        {
//            de = e - eOld;  //difference
//        }
//        ie += e;            //integration
//
//        turnSpeed = kp * e + kd * de + ki * ie;
//        if (turnSpeed > 0.3)
//        {
//            turnSpeed = 0.3;
//        }
//        else if (turnSpeed < -0.3)
//        {
//            turnSpeed = -0.3;
//        }
//        if (turnSpeed > 0) {
//            leftPower = DRIVE_SPEED + turnSpeed;
//            rightPower = DRIVE_SPEED;
//        }
//        else
//        {
//            leftPower = DRIVE_SPEED;
//            rightPower = DRIVE_SPEED - turnSpeed;
//        }
//
//        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.rearLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        robot.rearRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//        robot.frontLeftDrive.setPower(leftPower);
//        robot.rearLeftDrive.setPower(leftPower);
//        robot.frontRightDrive.setPower(rightPower);
//        robot.rearRightDrive.setPower(rightPower);
//
//        telemetry.addData("leftPow", leftPower);
//        telemetry.addData("rightPow", rightPower);
//        telemetry.addData("e", e);
//        telemetry.update();
//    }
}
